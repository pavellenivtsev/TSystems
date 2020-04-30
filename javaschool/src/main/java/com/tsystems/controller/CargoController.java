package com.tsystems.controller;

import com.tsystems.dto.CargoDto;
import com.tsystems.service.api.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/cargo")
public class CargoController {
    @Autowired
    private CargoService cargoService;

    /**
     * Add new cargo
     *
     * @param orderId - order id
     * @param model   - model
     * @return cargo/addCargo.jsp
     */
    @GetMapping(value = "/add")
    public String getAddPage(@RequestParam("orderId") long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        model.addAttribute("cargo", new CargoDto());
        return "cargo/addCargo";
    }

    /**
     * Add new cargo
     *
     * @param cargoDto - cargo
     * @param orderId  - order id
     * @return order/notTakenOrders.jsp
     */
    @PostMapping(value = "/add")
    public String addCargo(@ModelAttribute("cargo") @Valid CargoDto cargoDto,
                           @RequestParam("orderId") long orderId) {
        cargoService.addCargoToOrder(cargoDto, orderId);
        return "redirect:/orders/not-taken";
    }

    /**
     * Edit cargo
     *
     * @param cargoId - cargo id
     * @param model   - model
     * @return cargo/edit.jsp
     */
    @GetMapping(value = "/edit")
    public String editCargoPage(@RequestParam("cargoId") long cargoId, Model model) {
        model.addAttribute("cargo", cargoService.findById(cargoId));
        return "cargo/edit";
    }

    /**
     * Edit cargo
     *
     * @param cargoDto - cargo
     * @return order/notTakenOrders.jsp
     */
    @PostMapping(value = "/edit")
    public String editCargo(@ModelAttribute("cargo") @Valid CargoDto cargoDto) {
        cargoService.editCargo(cargoDto);
        return "redirect:/orders/not-taken";
    }

    /**
     * Delete cargo
     *
     * @param cargoId - cargo id
     * @return order/notTakenOrders.jsp
     */
    @PostMapping(value = "/delete")
    public String deleteCargo(@RequestParam("cargoId") long cargoId) {
        cargoService.deleteCargo(cargoId);
        return "redirect:/orders/not-taken";
    }

    /**
     * Change cargo status to delivered
     *
     * @param id - cargo id
     * @return driver/order.jsp
     */
    @PostMapping("/delivered")
    public String cargoIsDelivered(@RequestParam("id") long id) {
        cargoService.setStatusDelivered(id);
        return "redirect:/driver/order";
    }

    /**
     * Change cargo status to shipped
     *
     * @param id - cargo id
     * @return driver/order.jsp
     */
    @PostMapping("/shipped")
    public String cargoIsShipped(@RequestParam("id") long id) {
        cargoService.setStatusShipped(id);
        return "redirect:/driver/order";
    }
}
