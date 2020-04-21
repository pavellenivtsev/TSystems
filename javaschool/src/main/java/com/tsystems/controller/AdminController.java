package com.tsystems.controller;

import com.tsystems.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Returns the allUsers page.
     *
     * @return allUsers.jsp
     */
    @GetMapping("/users")
    public String userList(Model model) {
        model.addAttribute("users", adminService.findAll());
        return "admin/allUsers";
    }

    /**
     * Delete user by id.
     *
     * @return allUsers.jsp
     */
    @PostMapping("/user/delete")
    public String deleteUser(@RequestParam long id) {
        adminService.deleteById(id);
        return "redirect:/admin/users";
    }

    /**
     * Appoint user as admin.
     *
     * @return allUsers.jsp
     */
    @PostMapping("/appoint/admin")
    public String appointAsAdmin(@RequestParam long id) {
        adminService.appointAsAdmin(id);
        return "redirect:/admin/users";
    }

    /**
     * Appoint user as manager.
     *
     * @return allUsers.jsp
     */
    @PostMapping("/appoint/manager")
    public String appointAsManager(@RequestParam long id) {
        adminService.appointAsManager(id);
        return "redirect:/admin/users";
    }

    /**
     * Appoint user as driver.
     *
     * @return allUsers.jsp
     */
    @PostMapping("/appoint/driver")
    public String appointAsDriver(@RequestParam("id") long id) {
        adminService.appointAsDriver(id);
        return "redirect:/admin/users";
    }
}
