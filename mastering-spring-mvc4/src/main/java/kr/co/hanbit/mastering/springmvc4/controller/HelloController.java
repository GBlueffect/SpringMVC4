package kr.co.hanbit.mastering.springmvc4.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller 
public class HelloController {
	@Autowired
	private	Twitter twitter;
	
	@RequestMapping("/")
	public String hello() {
		return "searchPage";
	} 
	
	@RequestMapping("/test")
	public String test() {
		return "test";
	} 

	@RequestMapping("/twitter")
	public String helloTwitter(@RequestParam(defaultValue="masterSpringMVC4") String search, Model model){
//		SearchResults searchResults = twitter.searchOperations().search(search);
//		String text = searchResults.getTweets().get(0).getText();
//		model.addAttribute("message", text);
		
//		System.out.println(searchResults.getTweets().size());
//		String text = searchResults.getTweets().get(0).getText();
//		model.addAttribute("message", text);
		
		SearchResults searchResults = twitter.searchOperations().search(search);
		List<String> tweets = searchResults.getTweets().stream().map(Tweet::getText).collect(Collectors.toList());
		model.addAttribute("tweets", tweets);
//		return "tweets";		
		return "resultPage";
	}
	
	@RequestMapping("/result")
	public String hello(@RequestParam(defaultValue = "masterSpringMVC4") String search, Model model) {
		SearchResults searchResults = twitter.searchOperations().search(search);
		List<Tweet> tweets = searchResults.getTweets();
		model.addAttribute("tweets", tweets);
		model.addAttribute("search", search);
		return "resultPage";
	} 
	
	@RequestMapping(value="/postSearch", method=RequestMethod.POST)
	public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String search = request.getParameter("search");
		if(search.toLowerCase().contains("struts")){
			redirectAttributes.addFlashAttribute("error", "Try using spring instead!!");
			return "redirect:/";
		}
		
		redirectAttributes.addAttribute("search", search);
		return "redirect:result";
	} 
} 