package com.tsystems.controller;

import com.tsystems.dto.TruckDto;
import com.tsystems.service.api.DriverService;
import com.tsystems.service.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/truck")
public class TruckController {
    private final TruckService truckService;

    private final DriverService driverService;

    @Autowired
    public TruckController(TruckService truckService, DriverService driverService) {
        this.truckService = truckService;
        this.driverService = driverService;
    }

    /**
     * Returns the allTrucks page.
     *
     * @return allTrucks.jsp
     */
    @GetMapping(value = "/all")
    public ModelAndView getTrucks() {
        List<TruckDto> trucksDto = truckService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("truck/allTrucks");
        modelAndView.addObject("trucks", trucksDto);
        return modelAndView;
    }

    /**
     * Delete truck by id.
     *
     * @return allTruck.jsp
     */
    @PostMapping(value = "/delete")
    public String deleteTruck(@RequestParam long id) {
        truckService.deleteById(id);
        return "redirect:/truck/all";
    }

    /**
     * Adds a truck to the model.
     *
     * @return editTruck.jsp
     */
    @GetMapping(value = "/edit")
    public ModelAndView editTruckPage(@RequestParam long id) {
        TruckDto truckDto = truckService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("truck/editTruck");
        modelAndView.addObject("truck", truckDto);
        return modelAndView;
    }

    /**
     * Edit a truck.
     *
     * @return allTrucks.jsp
     */
    @PostMapping(value = "/edit")
    public String editTruck(@ModelAttribute("truck") TruckDto truckDto,
                            @RequestParam("locationCity") String locationCity) {
        truckService.update(truckDto, locationCity);
        return "redirect:/truck/all";
    }

    /**
     * Add new truck.
     *
     * @return addTruck.jsp
     */
    @GetMapping(value = "/add")
    public ModelAndView addPage() {
        TruckDto truckDto = new TruckDto();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/truck/addTruck");
        modelAndView.addObject("newTruck", truckDto);
        return modelAndView;
    }

    /**
     * Add new truck.
     *
     * @return allTruck.jsp
     */
    @PostMapping(value = "/add")
    public String addTruck(@ModelAttribute("newTruck") TruckDto truckDto,
                           @RequestParam("locationCity") String locationCity
    ) {
        truckService.save(truckDto, locationCity);
        return "redirect:/truck/all";
    }

    /**
     * Returns the truck page.
     *
     * @return truck.jsp
     */
    @GetMapping(value = "/")
    public ModelAndView getTruck(@RequestParam long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("truck/truck");
        modelAndView.addObject("truck", truckService.findById(id));
        return modelAndView;
    }

    /**
     * Add driver.
     *
     * @return addDriver.jsp
     */
    @GetMapping(value = "/add/driver")
    public String addDriverPage(@ModelAttribute TruckDto truckDto, Model model) {
        model.addAttribute("truck", truckDto);
        model.addAttribute("drivers", driverService.findAllAvailable());
        return "truck/addDriver";
    }

    /**
     * Add driver.
     *
     * @return truck.jsp
     */
    @PostMapping(value = "/add/driver")
    public String addDriver(@ModelAttribute TruckDto truckDto,
                            @RequestParam long driverId){
        truckService.addDriver(truckDto, driverId);
        return "redirect:/truck/";
    }

    /**
     * Delete driver.
     *
     * @return truck.jsp
     */
    @PostMapping(value = "/delete/driver")
    public String deleteDriver(@ModelAttribute TruckDto truckDto,
                               @RequestParam long driverId){
        truckService.deleteDriver(truckDto, driverId);
        return "redirect:/truck/";
    }

}
