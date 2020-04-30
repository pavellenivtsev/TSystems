package com.tsystems.controller;

import com.tsystems.service.api.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class UserOrderController {
    @Autowired
    private UserOrderService userOrderService;

    /**
     * Returns the completedOrders page.
     *
     * @param model - model
     * @return order/completedOrders.jsp
     */
    @GetMapping("/orders/completed")
    public String getCompletedOrders(Model model) {
        model.addAttribute("orders", userOrderService.findAllCompletedSortedByDate());
        return "order/completedOrders";
    }

    /**
     * Returns the takenOrders page.
     *
     * @param model - model
     * @return order/takenOrders.jsp
     */
    @GetMapping("/orders/in-progress")
    public String getTakenOrders(Model model) {
        model.addAttribute("orders", userOrderService.findAllTakenSortedByDate());
        return "order/takenOrders";
    }

    /**
     * Returns the notTakenOrders page.
     *
     * @param model - model
     * @return order/notTakenOrders.jsp
     */
    @GetMapping("/orders/not-taken")
    public String getNotTakenOrders(Model model) {
        model.addAttribute("orders", userOrderService.findAllNotTakenSortedByDate());
        return "order/notTakenOrders";
    }

    /**
     * Delete order by id.
     *
     * @param id      - order id
     * @param request - Instance of {@link HttpServletRequest}
     * @return order/notTakenOrders.jsp
     */
    @PostMapping("/order/delete")
    public String deleteOrder(@RequestParam long id, HttpServletRequest request) {
        userOrderService.deleteById(id);
        return getPreviousPageByRequest(request).orElse("redirect:/orders/not-taken");
    }

    /**
     * Add new order.
     *
     * @return order/notTakenOrders.jsp
     */
    @PostMapping("/order")
    public String addOrder() {
        userOrderService.addOrder();
        return "redirect:/orders/not-taken";
    }

    /**
     * Returns the viewName to return for coming back to the sender url
     *
     * @param request - Instance of {@link HttpServletRequest}
     * @return Optional with the view name
     */
    protected Optional<String> getPreviousPageByRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
    }
}
