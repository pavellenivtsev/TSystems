package com.tsystems.controller;

import com.tsystems.service.api.DriverService;
import com.tsystems.service.api.TruckService;
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
    private final DriverService driverService;

    private final TruckService truckService;

    @Autowired
    public DriverController(DriverService driverService, TruckService truckService) {
        this.driverService = driverService;
        this.truckService = truckService;
    }

    /**
     * Get driver cabinet
     *
     * @param authentication
     * @param model
     * @return driver/cabinet.jsp
     */
    @GetMapping("/cabinet")
    public String getDriverCabinet(Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("driver", driverService.findByUsername(userDetails.getUsername()));
        return "driver/cabinet";
    }
//
//    /**
//     * Get driver information
//     *
//     * @param authentication
//     * @param model
//     * @return driver/information.jsp
//     */
//    @GetMapping("/information")
//    public String getDriverInformation(Authentication authentication, Model model) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        model.addAttribute("driver", driverService.findByUsername(userDetails.getUsername()));
//        return "driver/information";
//    }

    /**
     * Get driver truck
     *
     * @param authentication
     * @param model
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
     * @param authentication
     * @param model
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
     * @param id
     * @return driver/information.jsp
     */
    @PostMapping("/status/start")
    public String changeDriverStatusToOnShift(@RequestParam("driverId") long id, Model model) {
        if (!driverService.changeDriverStatusToOnShift(id)) {
            model.addAttribute("changeDriverStatusToOnShiftError", "You can't take a shift");
        }
        return "redirect:/driver/information";
    }

    /**
     * Change driver status to REST
     *
     * @param id
     * @return driver/information.jsp
     */
    @PostMapping("/status/finish")
    public String changeDriverStatusToRest(@RequestParam("driverId") long id, Model model) {
        if (driverService.changeDriverStatusToRest(id)) {
            model.addAttribute("changeDriverStatusToRestError", "Changing status error");
        }
        return "redirect:/driver/information";
    }

    /**
     * Change truck status to ON_DUTY
     *
     * @param id
     * @return driver/cabinet.jsp
     */
    @PostMapping("/truck/status/onDuty")
    public String changeTruckStatusToOnDuty(@RequestParam("driverId") long id, Model model) {
        if (driverService.changeTruckStatusToOnDuty(id)) {
            model.addAttribute("changeTruckStatusToOnDutyError", "Changing status error");
        }
        return "redirect:/driver/cabinet";
    }

    /**
     * Change truck status to FAULTY
     *
     * @param id
     * @return driver/cabinet.jsp
     */
    @PostMapping("/truck/status/faulty")
    public String changeTruckStatusToFaulty(@RequestParam("driverId") long id, Model model) {
        if (driverService.changeTruckStatusToFaulty(id)) {
            model.addAttribute("changeTruckStatusToOnDutyError", "Changing status error");
        }
        return "redirect:/driver/cabinet";
    }

    /**
     * Change truck status to FAULTY
     *
     * @param userOrderId
     * @return driver/cabinet.jsp
     */
    @PostMapping("/order/status/completed")
    public String completeOrder(@RequestParam("userOrderId") long userOrderId, Model model) {
        if (driverService.completeOrder(userOrderId)) {
            model.addAttribute("completeOrderError", "Can't complete order");
        }
        return "redirect:/driver/cabinet";
    }
}
