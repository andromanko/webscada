package webscada.rest.controllers;

import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import webscada.api.dto.DataDto;
import webscada.api.dto.DevDto;
//import webscada.api.dto.UserPetIdsDto;
import webscada.api.services.IDataService;
import webscada.api.services.IDevService;

@RestController
@RequestMapping("/monitor")
public class DataController {

	@Autowired
	private IDevService devService;
	
	@Autowired
	private IDataService dataService;

//	@Autowired
//	Principal principal;//=getPrincipal();
	
	//Principal principal;
	
	@GetMapping
	public ModelAndView viewData() {
		
		
		List<DataDto> data = new LinkedList<DataDto>();
		List<DevDto> devices=devService.getDevs();
				data = dataService.readAllData(devices);
		
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("monitor");
		modelAndView.addObject("title", "Data");
		modelAndView.addObject("dataList", data);
		return modelAndView;
	}

//	@GetMapping(value = "/{id}")
//	public ModelAndView findDev(@PathVariable long id) {
//		DevDto dev = devService.findDev(id);
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("devPage");
//		modelAndView.addObject("dev", dev);
//		return modelAndView;
//	}

	// ===============create=================

//	@GetMapping(value = "/add")
//	public ModelAndView createDev() {
//		//TODO create dev
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("devsFormPage");
//		modelAndView.addObject("dev", new DevDto());
//		return modelAndView;
//	}

//	@PostMapping(value = "/add")
//	public DevDto createUserSubmit(DevDto dev) {
//		return this.devService.createDev(dev);
//	}

	// ===============update=================

//	@GetMapping(value = "/upd")
//	public ModelAndView updateDev() {
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("devsFormPageUpd");
//		modelAndView.addObject("device", new DevDto());
//		return modelAndView;
//	}

//    @PostMapping(value = "/upd")
//    public void updateUser(DevDto dev) {
//        this.devService.updateDev(dev.getId(), dev);
//    }

	// ================================

//	@DeleteMapping(value = "/{id}")
//	public void deleteDev(@PathVariable int id) {
//		this.devService.deleteDev(id);
//	}

//	@PatchMapping()
//	public void assingPetToUser(@RequestBody UserPetIdsDto ids) {
//		this.userService.assingPetToUser(ids);
//	}

//	@GetMapping("/book")
//	public ModelAndView search(@RequestParam(value = "isbn", required = false) String isbn) {
//		ModelAndView modelAndView = new ModelAndView();
//		userService.getBookByIsbn(isbn);
//		return modelAndView;
//	}
}
