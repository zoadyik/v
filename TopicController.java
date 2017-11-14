package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.model.TopicModel;
import com.demo.service.TopicService;

@Controller
public class TopicController {
	private TopicService topicService;
	private static final int limitResultsPerPage = (int) 20;

	@Autowired(required=true)
	@Qualifier(value="topicService")
	public void setTopicService(TopicService ts){
		this.topicService = ts;
	}
	
	@RequestMapping(value = "/topics", method = RequestMethod.GET)
	public String listTopics(@RequestParam(value = "page", required = false) Long page, Model model) {
		if(page == null) page = (long) 1;
		int startpage = (int) (page - 5 > 0?page - 5:1);
		int endpage = (this.topicService.listTopics().size() + limitResultsPerPage -1) / limitResultsPerPage;
		model.addAttribute("startpage",startpage);
	    model.addAttribute("endpage",endpage);
		model.addAttribute("topic", new TopicModel());
		model.addAttribute("listTopics", this.topicService.listTopics(page));
		return "topic";
	}
	
	//For add and update topic both
	@RequestMapping(value= "/topic/add", method = RequestMethod.POST)
	public String addTopic(@ModelAttribute("topic") TopicModel t){
		
		if(t.getTid() == 0){
			//new topic, add it
			this.topicService.addTopic(t);
		}else{
			//existing topic, call update
			this.topicService.updateTopic(t);
		}
		
		return "redirect:/topics";
		
	}
	
	@RequestMapping("/remove/{tId}")
    public String removeTopic(@PathVariable("tId") int tId){
		
        this.topicService.removeTopic(tId);
        return "redirect:/topics";
    }
 
    @RequestMapping("/edit/{tId}")
    public String editTopic(@PathVariable("tId") int tId, Model model){
        model.addAttribute("topic", this.topicService.getTopicById(tId));
        Long page = null;
		if(page == null) page = (long) 1;
		int startpage = (int) (page - 5 > 0?page - 5:1);
		int endpage = (this.topicService.listTopics().size() + limitResultsPerPage -1) / limitResultsPerPage;
		model.addAttribute("startpage",startpage);
	    model.addAttribute("endpage",endpage);
		model.addAttribute("listTopics", this.topicService.listTopics(page));


        return "topic";
    }
}
