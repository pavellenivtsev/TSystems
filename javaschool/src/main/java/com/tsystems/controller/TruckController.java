package com.tsystems.controller;

import com.tsystems.dto.TruckDto;
import com.tsystems.service.api.TruckService;
import com.tsystems.service.impl.TruckServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/truck")
public class TruckController {
    private TruckService truckService = new TruckServiceImpl();

    @Autowired
    public void setTruckService(TruckService truckService) {
        this.truckService = truckService;
    }

    /**
     * Returns the allTrucks page.
     *
     * @return allTrucks.jsp
     */
    @GetMapping(value = "/all")
    public ModelAndView getTrucks() {
        List<TruckDto> trucksDto =truckService.findAll();
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
    public String deleteTruck(@RequestParam long id){
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
        TruckDto truckDto=truckService.findById(id);
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
        TruckDto truckDto=new TruckDto();
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("/truck/addTruck");
        modelAndView.addObject("newTruck",truckDto);
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
                           ){
        truckService.save(truckDto,locationCity);
        return "redirect:/truck/all";
    }
}
