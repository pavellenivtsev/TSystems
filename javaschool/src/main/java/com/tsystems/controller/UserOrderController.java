package com.tsystems.controller;

import com.tsystems.dto.UserOrderDto;
import com.tsystems.service.api.UserOrderService;
import com.tsystems.service.impl.UserOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/order")
public class UserOrderController {
    private UserOrderService userOrderService = new UserOrderServiceImpl();

    @Autowired
    public void setUserOrderService(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    /**
     * Returns the allOrders page.
     *
     * @return allOrders.jsp
     */
    @GetMapping(value = "/all")
    public ModelAndView getOrders() {
        List<UserOrderDto> userOrderDtoList=userOrderService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/allOrders");
        modelAndView.addObject("orders", userOrderDtoList);
        return modelAndView;
    }

    /**
     * Delete order by id.
     *
     * @return allOrders.jsp
     */
    @PostMapping(value = "/delete")
    public String deleteOrder(@RequestParam long id){
        userOrderService.deleteById(id);
        return "redirect:/order/all";
    }

    /**
     * Adds an order to the model.
     *
     * @return editOrder.jsp
     */
    @GetMapping(value = "/edit")
    public ModelAndView editOrderPage(@RequestParam long id) {
        UserOrderDto userOrderDto=userOrderService.findById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order/editOrder");
        modelAndView.addObject("order", userOrderDto);
        return modelAndView;
    }

    /**
     * Edit an order.
     *
     * @return allOrders.jsp
     */
    @PostMapping(value = "/edit")
    public String editOrder(@ModelAttribute("order") UserOrderDto userOrderDto,
                            @RequestParam("cargoName") String cargoName,
                            @RequestParam("cargoWeight") String cargoWeight,
                            @RequestParam("locationFromCity") String locationFromCity,
                            @RequestParam("locationToCity") String locationToCity
    ){
        userOrderService.update(userOrderDto, cargoName, cargoWeight, locationFromCity, locationToCity);
        return "redirect:/order/all";
    }

    /**
     * Add new order.
     *
     * @return addOrder.jsp
     */
    @GetMapping(value = "/add")
    public ModelAndView addPage() {
        UserOrderDto userOrderDto=new UserOrderDto();
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("order/addOrder");
        modelAndView.addObject("order",userOrderDto);
        return modelAndView;
    }

    /**
     * Add new order.
     *
     * @return allOrders.jsp
     */
    @PostMapping(value = "/add")
    public String addOrder(@ModelAttribute("order") UserOrderDto userOrderDto,
                           @RequestParam("cargoName") String cargoName,
                           @RequestParam("cargoWeight") String cargoWeight,
                           @RequestParam("locationFromCity") String locationFromCity,
                           @RequestParam("locationToCity") String locationToCity
    ){
        userOrderService.save(userOrderDto, cargoName, cargoWeight, locationFromCity, locationToCity);
        return "redirect:/order/all";
    }

}
