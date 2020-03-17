package com.tsystems.controller;

import com.tsystems.dto.WaypointDto;
import com.tsystems.service.api.WaypointService;
import com.tsystems.service.impl.WaypointServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

//@Controller
public class WaypointController {
    private WaypointService waypointService=new WaypointServiceImpl();

    @Autowired
    public void setWaypointService(WaypointService waypointService) {
        this.waypointService = waypointService;
    }

    /**
     * Returns the allOrders page.
     *
     * @return allOrders.jsp
     */
    @GetMapping(value = "/order/all")
    public ModelAndView getOrders() {
        List<WaypointDto> waypointDtoList = waypointService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/allOrders");
        modelAndView.addObject("waypoints", waypointDtoList);
        return modelAndView;
    }

    /**
     * Delete order by id.
     *
     * @return allOrders.jsp
     */
    @PostMapping(value = "/order/delete")
    public String deleteOrder(@RequestParam long id){
        waypointService.deleteById(id);
        return "redirect:/order/all";
    }

    /**
     * Adds an order to the model.
     *
     * @return editOrder.jsp
     */
    @GetMapping(value = "/order/edit")
    public ModelAndView editOrderPage(@RequestParam long id) {
        WaypointDto waypointDto =waypointService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/editOrder");
        modelAndView.addObject("waypoint", waypointDto);
        return modelAndView;
    }

    /**
     * Edit an order.
     *
     * @return allOrders.jsp
     */
    @PostMapping(value = "/order/edit")
    public String editOrder(@ModelAttribute("waypoint") WaypointDto waypointDto){
 //       waypointService.update(?);
        return "redirect:/order/all";
    }

    /**
     * Add new order.
     *
     * @return addOrder.jsp
     */
    @GetMapping(value = "/order/add")
    public ModelAndView addPage() {
        WaypointDto waypointDto=new WaypointDto();
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("order/addOrder");
        modelAndView.addObject("waypoint",waypointDto);
        return modelAndView;
    }

    /**
     * Add new order.
     *
     * @return allOrders.jsp
     */
    @PostMapping(value = "/order/add")
    public String addOrder(@ModelAttribute("waypoint")WaypointDto waypointDto

                           ){
   //     waypointService.save(?);
        return "redirect:/order/all";
    }
}
