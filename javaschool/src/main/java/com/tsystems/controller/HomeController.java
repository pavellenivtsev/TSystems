package com.tsystems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    /**
     * Returns the home page.
     *
     * @return home.jsp
     */
    @GetMapping(value = "/")
    public String getHomePage() {
        return "home";
    }

    /**
     * Returns the 403 page.
     *
     * @return error/403.jsp
     */
    @GetMapping("/403")
    public String deniedPage() {
        return "error/403";
    }
}
