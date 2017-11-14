package com.demo.controller;

import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.model.CommentModel;
import com.demo.service.CommentService;
import com.demo.service.TopicService;

@Controller
public class CommentController {
	private CommentService commentService;
	private TopicService topicService;
	@Autowired(required=true)
	@Qualifier(value="commentService")
	public void setCommentService(CommentService cs){
		this.commentService = cs;
	}
	
	@RequestMapping(value = "/comments/{tId}", method = RequestMethod.GET)
	public String listComments(@PathVariable("tId") int tId, Model model) {
		model.addAttribute("comment", new CommentModel());
		model.addAttribute("listComments", this.commentService.listComments(tId));
		model.addAttribute("topicId", tId);
		return "comment";
	}
	
	//For add and update comment both
	@RequestMapping(value= "/comment/add/{tId}", method = RequestMethod.POST)
	public String addComment(@PathVariable("tId") int tId, @ModelAttribute("comment") CommentModel c){
//		int tId = this.commentService.listComments(c.getTid()).get(0).getTid();
//		String name = request.getParameter("qwe");
		if(c.getCid() == 0){
			//new comment, add it
			this.commentService.addComment(c, tId);
		}else{
			//existing comment, call update
			
			this.commentService.updateComment(c, tId);
		}
		
		return "redirect:/comments/{tId}";
		
	}
	
	@RequestMapping("comment/remove/{tId}/{cId}")
    public String removeComment(@PathVariable("tId") int tId, @PathVariable("cId") int cId){
		
        this.commentService.removeComment(cId);
        return "redirect:/comments/{tId}";
    }
 
    @RequestMapping("comment/edit/{tId}/{cId}")
    public String editComment(@PathVariable("tId") int tId, @PathVariable("cId") int cId, Model model){
        model.addAttribute("comment", this.commentService.getCommentById(cId));
        model.addAttribute("listComments", this.commentService.listComments(tId));
        model.addAttribute("topicId", tId);
        return "comment";
    }
    

}
