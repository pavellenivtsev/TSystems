package com.tsystems.controller;

import com.tsystems.dto.UserDto;
import com.tsystems.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/cabinet")
    public String userCabinet(Model model, Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", userService.findByUsername(userDetails.getUsername()));
        return "/user/cabinet";
    }

    @GetMapping("/edit")
    public String getEditPage(@RequestParam("id") long id, Model model ){
        model.addAttribute("user", userService.findById(id));
        return "user/edit";
    }

    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user")UserDto userDto){
        userService.update(userDto);
        return "redirect:/user/cabinet";
    }
}
