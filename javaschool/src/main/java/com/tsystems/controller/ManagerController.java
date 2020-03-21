package com.tsystems.controller;

import com.tsystems.dto.DriverDto;
import com.tsystems.dto.TruckDto;
import com.tsystems.service.api.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/manager")
public class ManagerController {

    private final DriverService driverService;

    @Autowired
    public ManagerController(DriverService driverService) {
        this.driverService = driverService;
    }

    /**
     * Returns the allDrivers page.
     *
     * @return allTrucks.jsp
     */
    @GetMapping(value = "/driver/all")
    public ModelAndView getDrivers() {
        List<DriverDto> driverDtoList=driverService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("manager/allDrivers");
        modelAndView.addObject("drivers", driverDtoList);
        return modelAndView;
    }

    /**
     * Delete truck by id.
     *
     * @return allTruck.jsp
     */
    @PostMapping(value = "/driver/delete")
    public String deleteTruck(@RequestParam long id){
        driverService.deleteById(id);
        return "redirect:/manager/driver/all";
    }

    /**
     * Adds a truck to the model.
     *
     * @return editTruck.jsp
     */
    @GetMapping(value = "/driver/edit")
    public ModelAndView editTruckPage(@RequestParam long id) {
        DriverDto driverDto=driverService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("manager/editDriver");
        modelAndView.addObject("driver", driverDto);
        return modelAndView;
    }

    /**
     * Edit a truck.
     *
     * @return allTrucks.jsp
     */
    @PostMapping(value = "/driver/edit")
    public String editTruck(@ModelAttribute("truck") TruckDto truckDto,
                            @RequestParam("locationCity") String locationCity) {

        return "redirect:/manager/driver/all";
    }

}
