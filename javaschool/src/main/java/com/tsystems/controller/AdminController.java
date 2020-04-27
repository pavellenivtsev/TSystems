package com.tsystems.controller;

import com.tsystems.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
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
    public ModelAndView userList() {
        ModelAndView model = new ModelAndView("admin/allUsers");
        model.addObject("users", adminService.findAll());
        return model;
    }

    /**
     * Delete user by id.
     *
     * @param id - user id
     * @return allUsers.jsp
     */
    @PostMapping("/user/delete")
    public ModelAndView deleteUser(@RequestParam long id) {
        adminService.deleteById(id);
        return new ModelAndView("redirect:/admin/users");
    }

    /**
     * Appoint user as admin.
     *
     * @param id - user id
     * @return allUsers.jsp
     */
    @PostMapping("/appoint/admin")
    public boolean appointAsAdmin(@RequestParam long id) {
        return adminService.appointAsAdmin(id);
    }

    /**
     * Appoint user as manager.
     *
     * @param id - user id
     * @return allUsers.jsp
     */
    @PostMapping("/appoint/manager")
    public boolean appointAsManager(@RequestParam long id) {
        return adminService.appointAsManager(id);
    }

    /**
     * Appoint user as driver.
     *
     * @param id - user id
     * @return allUsers.jsp
     */
    @PostMapping("/appoint/driver")
    public boolean appointAsDriver(@RequestParam long id) {
        return adminService.appointAsDriver(id);
    }

    /**
     * Appoint user as user.
     *
     * @param id - user id
     * @return allUsers.jsp
     */
    @PostMapping("/appoint/user")
    public boolean appointAsUser(@RequestParam long id) {
        return adminService.appointAsUser(id);
    }
}
