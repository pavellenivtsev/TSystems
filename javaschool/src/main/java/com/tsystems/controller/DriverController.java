package com.tsystems.controller;

import com.tsystems.service.api.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/driver")
public class DriverController {
    @Autowired
    private DriverService driverService;

    /**
     * Get driver cabinet
     *
     * @param authentication authentication
     * @param model model
     * @return driver/cabinet.jsp
     */
    @GetMapping("/cabinet")
    public String getDriverCabinet(Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("driver", driverService.findByUsername(userDetails.getUsername()));
        return "driver/cabinet";
    }

    /**
     * Get driver truck
     *
     * @param authentication authentication
     * @param model model
     * @return driver/truck.jsp
     */
    @GetMapping("/truck")
    public String getDriverTruck(Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("driver", driverService.findByUsername(userDetails.getUsername()));
        return "driver/truck";
    }

    /**
     * Get driver order
     *
     * @param authentication authentication
     * @param model model
     * @return driver/order.jsp
     */
    @GetMapping("/order")
    public String getDriverOrder(Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("driver", driverService.findByUsername(userDetails.getUsername()));
        return "driver/order";
    }

    /**
     * Change driver status to ON_SHIFT
     *
     * @param driverId driver id
     * @return driver/cabinet.jsp
     */
    @PostMapping("/status/start")
    public String changeDriverStatusToOnShift(@RequestParam long driverId) {
        driverService.changeDriverStatusToOnShift(driverId);
        return "redirect:/driver/cabinet";
    }

    /**
     * Change driver status to REST
     *
     * @param driverId driver id
     * @return driver/cabinet.jsp
     */
    @PostMapping("/status/finish")
    public String changeDriverStatusToRest(@RequestParam long driverId) {
        driverService.changeDriverStatusToRest(driverId);
        return "redirect:/driver/cabinet";
    }

    /**
     * Change truck status to ON_DUTY
     *
     * @param driverId driver id
     * @return driver/truck.jsp
     */
    @PostMapping("/truck/status/onDuty")
    public String changeTruckStatusToOnDuty(@RequestParam long driverId) {
       driverService.changeTruckStatusToOnDuty(driverId);
        return "redirect:/driver/truck";
    }

    /**
     * Change truck status to FAULTY
     *
     * @param driverId driver id
     * @return driver/truck.jsp
     */
    @PostMapping("/truck/status/faulty")
    public String changeTruckStatusToFaulty(@RequestParam long driverId) {
       driverService.changeTruckStatusToFaulty(driverId);

        return "redirect:/driver/truck";
    }

    /**
     * Change truck status to FAULTY
     *
     * @param userOrderId order id
     * @return driver/cabinet.jsp
     */
    @PostMapping("/order/status/completed")
    public String completeOrder(@RequestParam long userOrderId) {
        driverService.completeOrder(userOrderId);
        return "redirect:/driver/cabinet";
    }
}
