package com.tsystems.controller;

import com.tsystems.dto.UserDto;
import com.tsystems.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Get user cabinet
     *
     * @param model - model
     * @param authentication - authentication
     * @return user/cabinet.jsp
     */
    @GetMapping("/cabinet")
    public String userCabinet(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", userService.findByUsername(userDetails.getUsername()));
        return "user/cabinet";
    }

    /**
     * Edit user
     *
     * @param id - user id
     * @param model - model
     * @return user/edit.jsp
     */
    @GetMapping("/edit")
    public String getEditPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user/edit";
    }

    /**
     * Edit user information
     *
     * @param userDto - user
     * @return user/cabinet.jsp
     */
    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") UserDto userDto) {
        userService.update(userDto);
        return "redirect:/user/cabinet";
    }
}
