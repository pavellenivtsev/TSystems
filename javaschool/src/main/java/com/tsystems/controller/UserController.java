package com.tsystems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserController {

    @GetMapping("/user/cabinet")
    public String userCabinet(){
        return "/user/cabinet";
    }

}
