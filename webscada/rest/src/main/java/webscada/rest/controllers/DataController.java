package webscada.rest.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import webscada.api.dto.EventStringDto;
import webscada.api.dto.ValueReal;
import webscada.api.services.IDataService;
import webscada.api.services.IEventService;
import webscada.api.services.IProfilePrinter;

@RestController
@RequestMapping("/monitor")
public class DataController {

	@Autowired
	private IProfilePrinter profile;

	@Autowired
	private IDataService dataService;

	@Autowired
	private IEventService eventService;

	@GetMapping("/s")
	@Scheduled(fixedDelay = 60000, initialDelay = 30000)
	public void viewDataSch() {
		dataService.readAllData();
	}

	@GetMapping
	public ModelAndView viewData() {
		Map<Long, ValueReal> data = dataService.readAllData();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("monitor");
		modelAndView.addObject("title", "Data");
		modelAndView.addObject("dataList", data);
		List<EventStringDto> events = eventService.getEventsString(1, 3);
		modelAndView.addObject("eventList", events);
		modelAndView.addObject("profile", this.profile.print());
		return modelAndView;
	}

	@GetMapping(value = "/events")
	public ModelAndView viewEvents(
			@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "size", required = false, defaultValue = "20") int size) {
		List<EventStringDto> data = eventService.getEventsString(pageNumber, size);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("eventsPage");
		modelAndView.addObject("eventList", data);
		return modelAndView;
	}

}
