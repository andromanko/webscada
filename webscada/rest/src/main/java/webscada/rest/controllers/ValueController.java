package webscada.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import webscada.api.dto.ValueDto;
import webscada.api.dto.ValDevIdsDto;
import webscada.api.services.IValueService;

@RestController
@RequestMapping("/values")
public class ValueController {

	@Autowired
	private IValueService valueService;

	@GetMapping
	public ModelAndView findValues() {
		List<ValueDto> vals = valueService.findValues();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("valuesPage");
		modelAndView.addObject("title", "Values:");
		modelAndView.addObject("valsList", vals);
		return modelAndView;
	}

	@GetMapping(value = "/{id}")
	public ModelAndView findValue(@PathVariable long id) {
		ValueDto val = valueService.findValue(id);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("valuePage");
		modelAndView.addObject("val", val);
		return modelAndView;
	}

	// ===============create=================

	@GetMapping(value = "/add")
	public ModelAndView createValue() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ValFormPage");
		modelAndView.addObject("val", new ValueDto());
		return modelAndView;
	}

	@PostMapping(value = "/add")
	public ValueDto createUserSubmit(ValueDto val) {
		return this.valueService.createValue(val);
	}

	// ===============update=================

	@GetMapping(value = "/upd")
	public ModelAndView updateVal() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("valsFormPageUpd");
		modelAndView.addObject("val", new ValueDto());
		return modelAndView;
	}

    @PostMapping(value = "/upd")
    public void updateUser(ValueDto val) {
        this.valueService.updateValue(val.getId(), val);
    }

	// ================================

	@DeleteMapping(value = "/{id}")
	public void deleteDev(@PathVariable int id) {
		this.valueService.deleteValue(id);
	}

	@PatchMapping()
	public void assignValToDev(@RequestBody ValDevIdsDto ids) {
		this.valueService.assignValueToDev(ids);
	}
}
