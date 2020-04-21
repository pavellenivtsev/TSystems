package com.tsystems.controller;

import com.tsystems.dto.UserDto;
import com.tsystems.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns registration page.
     *
     * @param model model
     * @return sign/registration.jsp
     */
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserDto());
        return "sign/registration";
    }

    /**
     * Register users.
     *
     * @param userDto user
     * @param bindingResult binding result
     * @param model model
     * @return cabinet.jsp or sign/registration.jsp
     */
    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid UserDto userDto,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "sign/registration";
        }
        if (!userDto.getPassword().equals(userDto.getPasswordConfirm())) {
            model.addAttribute("passwordError", "Passwords don't match");
            return "sign/registration";
        }
        if (!userService.save(userDto)) {
            model.addAttribute("usernameError", "A user with this name already exists");
            return "sign/registration";
        }
        return "redirect:/cabinet";
    }

    /**
     * Returns login page.
     *
     * @return login.jsp
     */
    @GetMapping(value = "/login")
    public String getLoginPage() {
        return "sign/login";
    }

    /**
     * Returns cabinet
     *
     * @param authentication authentication
     * @return cabinet.jsp
     */
    @GetMapping("/cabinet")
    public String getCabinet(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                return "redirect:/admin/users";
            }
            if (grantedAuthority.getAuthority().equals("ROLE_MANAGER")) {
                return "redirect:/dispatcher/cabinet";
            }
            if (grantedAuthority.getAuthority().equals("ROLE_DRIVER")) {
                return "redirect:/driver/cabinet";
            }
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                return "redirect:/user/cabinet";
            }
        }
        return "redirect:/login";
    }
}
