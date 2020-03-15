package com.tsystems.controller;

import com.tsystems.entity.User;
import com.tsystems.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns admin page.
     *
     * @return admin.jsp
     */
    @GetMapping("/")
    public String adminPage(){
        return "admin/admin";
    }

    /**
     * Returns the allUsers page.
     *
     * @return allUsers.jsp
     */
    @GetMapping("/users/all")
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/allUsers";
    }

    /**
     * Delete user by id.
     *
     * @return allUsers.jsp
     */
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable long id){
        User user =userService.findById(id);
        userService.delete(user);
        return "redirect:/admin/users/all";
    }

}
