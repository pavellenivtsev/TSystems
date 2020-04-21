package com.tsystems.controller;

import com.tsystems.dto.DriverDto;
import com.tsystems.service.api.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/driver")
public class DriverController {
    @Autowired
    private DriverService driverService;

    /**
     * Get driver cabinet
     *
     * @param principal - principal
     * @param model     - model
     * @return driver/cabinet.jsp
     */
    @GetMapping("/cabinet")
    public String getDriverCabinet(Principal principal, Model model) {
        model.addAttribute("driver", driverService.findByUsername(principal.getName()));
        return "driver/cabinet";
    }

    /**
     * Get driver truck
     *
     * @param principal - principal
     * @param model     - model
     * @return driver/truck.jsp
     */
    @GetMapping("/truck")
    public String getDriverTruck(Principal principal, Model model) {
        model.addAttribute("driver", driverService.findByUsername(principal.getName()));
        return "driver/truck";
    }

    /**
     * Get driver order
     *
     * @param principal - principal
     * @param model     - model
     * @return driver/order.jsp
     */
    @GetMapping("/order")
    public String getDriverOrder(Principal principal, Model model) {
        DriverDto driverDto = driverService.findByUsername(principal.getName());
        model.addAttribute("driver", driverDto);
        model.addAttribute("truck", driverService.getTruckJson(driverDto));
        return "driver/order";
    }

    /**
     * Change driver status to ON_SHIFT
     *
     * @param driverId - driver id
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
     * @param driverId - driver id
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
     * @param driverId - driver id
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
     * @param driverId - driver id
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
     * @param userOrderId - order id
     * @return driver/cabinet.jsp
     */
    @PostMapping("/order/status/completed")
    public String completeOrder(@RequestParam long userOrderId) {
        driverService.completeOrder(userOrderId);
        return "redirect:/driver/cabinet";
    }
}
