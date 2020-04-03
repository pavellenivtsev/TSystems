package com.tsystems.controller;

import com.tsystems.dto.TruckDto;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.service.api.TruckService;
import com.tsystems.service.api.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/order")
public class UserOrderController {
    private final UserOrderService userOrderService;

    private final TruckService truckService;

    @Autowired
    public UserOrderController(UserOrderService userOrderService, TruckService truckService) {
        this.userOrderService = userOrderService;
        this.truckService = truckService;
    }

    /**
     * Returns the allOrders page.
     *
     * @return order/allOrders.jsp
     */
    @GetMapping(value = "/all")
    public ModelAndView getOrders() {
        List<UserOrderDto> userOrderDtoList = userOrderService.findAllSortedByDate();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/allOrders");
        modelAndView.addObject("orders", userOrderDtoList);
        return modelAndView;
    }

    /**
     * Delete order by id.
     *
     * @return order/allOrders.jsp
     */
    @PostMapping(value = "/delete")
    public String deleteOrder(@RequestParam long id,
                              Model model) {
        if (!userOrderService.deleteById(id)) {
            model.addAttribute("deleteOrderError", "Cannot delete this order");
            return "redirect:/order/all";
        }
        return "redirect:/order/all";
    }

    /**
     * Add new order.
     *
     * @return addOrder.jsp
     */
    @GetMapping(value = "/add")
    public String addOrderPage() {
        return "order/addOrder";
    }

    /**
     * Add new order.
     *
     * @return order/allOrders.jsp
     */
    @PostMapping(value = "/add")
    public String addOrder(@RequestParam("locationFromCity") String locationFromCity,
                           @RequestParam("locationToCity") String locationToCity,
                           @RequestParam("latitudeFrom") double latitudeFrom,
                           @RequestParam("longitudeFrom") double longitudeFrom,
                           @RequestParam("latitudeTo") double latitudeTo,
                           @RequestParam("longitudeTo") double longitudeTo,
                           @RequestParam("distance") double distance,
                           Model model) {
        if (!userOrderService.addOrder(locationFromCity, locationToCity, latitudeFrom, longitudeFrom, latitudeTo, longitudeTo, distance)) {
            model.addAttribute("addOrderError", "Can't create this order");
            return "redirect:/order/add";
        }
        return "redirect:/order/all";
    }

    /**
     * Edit the order
     *
     * @return order/editOrder.jsp
     */
    @GetMapping(value = "/edit")
    public String editOrderPage(@RequestParam("orderId") long orderId, Model model) {
        model.addAttribute("order", userOrderService.findById(orderId));
        return "order/editOrder";
    }

    /**
     * Edit the order
     *
     * @return order/allOrders.jsp
     */
    @PostMapping(value = "/edit")
    public String editOrder(@RequestParam("orderId") long orderId,
                            @RequestParam("locationFromCity") String locationFromCity,
                            @RequestParam("locationToCity") String locationToCity,
                            @RequestParam("latitudeFrom") double latitudeFrom,
                            @RequestParam("longitudeFrom") double longitudeFrom,
                            @RequestParam("latitudeTo") double latitudeTo,
                            @RequestParam("longitudeTo") double longitudeTo,
                            @RequestParam("distance") double distance,
                            Model model) {
        if (!userOrderService.update(orderId, locationFromCity, locationToCity, latitudeFrom, longitudeFrom, latitudeTo, longitudeTo, distance)) {
            model.addAttribute("editOrderError", "Can't edit this order");
            return "redirect:/order/all";
        }
        return "redirect:/order/all";
    }

    /**
     * Add truck to order.
     *
     * @return order/addTruck.jsp
     */
    @GetMapping(value = "/add/truck")
    public ModelAndView addTruckToOrderPage(@RequestParam long id) {
        UserOrderDto userOrderDto = userOrderService.findById(id);
        List<TruckDto> truckDtoList = truckService.findAllAvailable(userOrderDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/addTruck");
        modelAndView.addObject("order", userOrderDto);
        modelAndView.addObject("trucks", truckDtoList);
        return modelAndView;
    }

    /**
     * Add truck to order.
     *
     * @return order/allOrders.jsp
     */
    @PostMapping(value = "/add/truck")
    public String addTruckToOrder(@RequestParam("orderId") long orderId,
                                  @RequestParam("truckId") long truckId,
                                  Model model) {
        if (!userOrderService.addTruck(userOrderService.findById(orderId), truckId)) {
            model.addAttribute("addTruckToOrderError", "Cant add truck to this order");
            return "redirect:/order/all";
        }
        return "redirect:/order/all";
    }
}
