package com.tsystems.controller;

import com.tsystems.dto.DriverDto;
import com.tsystems.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns the allUsers page.
     *
     * @return allUsers.jsp
     */
    @GetMapping("/user/all")
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/allUsers";
    }

    /**
     * Delete user by id.
     *
     * @return allUsers.jsp
     */
    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam long id){
        userService.deleteById(id);
        return "redirect:/admin/user/all";
    }

    /**
     * Appoint user as admin.
     *
     * @return allUsers.jsp
     */
    @PostMapping("/appoint/admin")
    public String appointAsAdmin(@RequestParam long id){
        userService.appointAsAdmin(id);
        return "redirect:/admin/user/all";
    }

    /**
     * Appoint user as manager.
     *
     * @return allUsers.jsp
     */
    @PostMapping("/appoint/manager")
    public String appointAsManager(@RequestParam long id){
        userService.appointAsManager(id);
        return "redirect:/admin/user/all";
    }

    /**
     * Appoint user as driver.
     *
     * @return appointAsDriver.jsp
     */
    @GetMapping("/appoint/driver")
    public String appointAsDriverPage(@RequestParam long id, Model model){
        model.addAttribute("userId",id);
        return "admin/appointAsDriver";
    }

    /**
     * Appoint user as driver.
     *
     * @return allUsers.jsp
     */
    @PostMapping("/appoint/driver")
    public String appointAsDriver(@RequestParam("userId") long userId,
                                  @RequestParam("personalNumber") String personalNumber,
                                  @RequestParam("currentCity") String currentCity,
                                  Model model){
        if(!userService.appointAsDriver(userId, personalNumber, currentCity)){
            model.addAttribute("personalNumberError","This personal number is already in use");
            return "admin/appointAsDriver";

        }
        return "redirect:/admin/user/all";
    }

}
