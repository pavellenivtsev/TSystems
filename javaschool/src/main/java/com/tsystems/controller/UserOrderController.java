package com.tsystems.controller;

import com.tsystems.service.api.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/order")
public class UserOrderController {
    @Autowired
    private UserOrderService userOrderService;

    /**
     * Returns the allOrders page.
     *
     * @return order/allOrders.jsp
     */
    @GetMapping(value = "/all")
    public String getOrders(Model model) {
        model.addAttribute("orders", userOrderService.findAllSortedByDate());
        return "order/allOrders";
    }

    /**
     * Delete order by id.
     *
     * @return order/allOrders.jsp
     */
    @PostMapping(value = "/delete")
    public String deleteOrder(@RequestParam long id) {
        userOrderService.deleteById(id);
        return "redirect:/order/all";
    }

    /**
     * Add new order.
     *
     * @return order/allOrders.jsp
     */
    @PostMapping(value = "/add")
    public String addOrder() {
        userOrderService.addOrder();
        return "redirect:/order/all";
    }

//    /**
//     * Add truck to order.
//     *
//     * @return order/addTruck.jsp
//     */
//    @GetMapping(value = "/add/truck")
//    public ModelAndView addTruckToOrderPage(@RequestParam long id) {
//        UserOrderDto userOrderDto = userOrderService.findById(id);
//        List<TruckDto> truckDtoList = truckService.findAllAvailable(userOrderDto);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("order/addTruck");
//        modelAndView.addObject("order", userOrderDto);
//        modelAndView.addObject("trucks", truckDtoList);
//        return modelAndView;
//    }
//
//    /**
//     * Add truck to order.
//     *
//     * @return order/allOrders.jsp
//     */
//    @PostMapping(value = "/add/truck")
//    public String addTruckToOrder(@RequestParam("orderId") long orderId,
//                                  @RequestParam("truckId") long truckId,
//                                  Model model) {
//        if (!userOrderService.addTruck(userOrderService.findById(orderId), truckId)) {
//            model.addAttribute("addTruckToOrderError", "Cant add truck to this order");
//            return "redirect:/order/all";
//        }
//        return "redirect:/order/all";
//    }
}
