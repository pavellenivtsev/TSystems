package com.tsystems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

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
    @GetMapping("/403")
    public String deniedPage(Principal user, Model model) {
        if (user != null) {
            model.addAttribute("msg", "Hi " + user.getName()
                    + ", you do not have permission to access this page!");
        }  else{
            model.addAttribute("msg",
                    "You do not have permission to access this page!");
        }
        return "error/403";
    }
}
