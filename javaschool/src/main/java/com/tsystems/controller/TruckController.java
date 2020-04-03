package com.tsystems.controller;

import com.tsystems.dto.TruckDto;
import com.tsystems.service.api.DriverService;
import com.tsystems.service.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
    public String deleteTruck(@RequestParam long id, Model model) {
        if(!truckService.deleteById(id)){
            model.addAttribute("deleteTruckError","Can't delete this truck");
            return "redirect:/truck/all";
        }
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
    public String editTruck(@ModelAttribute("truck") @Valid TruckDto truckDto,
                            @RequestParam("locationCity") @NotNull String locationCity,
                            @RequestParam("latitude") @NotNull double latitude,
                            @RequestParam("longitude") @NotNull double longitude,
                            Model model) {
        if(!truckService.update(truckDto, locationCity, latitude,longitude)){
            model.addAttribute("editTruckError","The truck with registration number " + truckDto.getRegistrationNumber()+" already exists.");
            return "truck/editTruck";
        }
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
        modelAndView.setViewName("truck/addTruck");
        modelAndView.addObject("newTruck", truckDto);
        return modelAndView;
    }

    /**
     * Add new truck.
     *
     * @return allTruck.jsp
     */
    @PostMapping(value = "/add")
    public String addTruck(@ModelAttribute("newTruck") @Valid TruckDto truckDto,
                           @RequestParam("locationCity") @NotNull String locationCity,
                           @RequestParam("latitude") @NotNull double latitude,
                           @RequestParam("longitude") @NotNull double longitude,
                           Model model) {
        if(!truckService.save(truckDto, locationCity, latitude, longitude)){
            model.addAttribute("addTruckError","The truck with registration number " + truckDto.getRegistrationNumber()+" already exists.");
            return "truck/addTruck";
        }
        return "redirect:/truck/all";
    }

    /**
     * Returns the truck page.
     *
     * @return truck.jsp
     */
    @GetMapping(value = "/{id}")
    public ModelAndView getTruck(@PathVariable long id) {
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
    public String addDriverPage(@RequestParam("id") long id, Model model) {
        TruckDto truckDto=truckService.findById(id);
        model.addAttribute("truck", truckDto);
        model.addAttribute("drivers", driverService.findAllAvailable(truckDto));
        return "truck/addDriver";
    }

    /**
     * Add driver.
     *
     * @return truck.jsp
     */
    @PostMapping(value = "/add/driver")
    public String addDriver(@RequestParam("id") long id,
                            @RequestParam("driverId") long driverId,
                            Model model) {
        TruckDto truckDto=truckService.findById(id);
        if (!truckService.addDriver(truckDto, driverId)) {
            model.addAttribute("cantAddDriver", "Cat't add this driver to this truck");
            return "redirect:/truck/" + truckDto.getId();
        }

        return "redirect:/truck/" + truckDto.getId();
    }

    /**
     * Delete driver.
     *
     * @return truck.jsp
     */
    @PostMapping(value = "/delete/driver")
    public String deleteDriver(@ModelAttribute("truck") TruckDto truckDto,
                               @RequestParam long driverId,
                               Model model) {
        if (!truckService.deleteDriver(truckDto, driverId)) {
            model.addAttribute("cantDeleteDriver", "Can't delete driver because this truck have an order");
            return "redirect:/truck/" + truckDto.getId();
        }
        return "redirect:/truck/" + truckDto.getId();
    }

}
