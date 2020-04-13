package com.tsystems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/dispatcher")
public class DispatcherController {

    /**
     * Get manager cabinet
     *
     * @return dispatcher/cabinet.jsp
     */
    @GetMapping("/cabinet")
    public String getDispatcherCabinet(){
        return "dispatcher/cabinet";
    }
}
