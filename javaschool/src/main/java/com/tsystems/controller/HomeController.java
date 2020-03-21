package com.tsystems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    /**
     * Returns the home page.
     *
     * @return home.jsp
     */
    @GetMapping(value="/")
    public ModelAndView getHomePage(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("title","Welcome");
        modelAndView.addObject("message", "This is welcome page!");
        return modelAndView;
    }

    /**
     * Returns the 403 page.
     *
     * @return error/403.jsp
     */
    @GetMapping("/denied")
    public String deniedPage() {
        return "error/403";
    }
}
