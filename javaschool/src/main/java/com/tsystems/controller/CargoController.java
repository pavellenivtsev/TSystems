package com.tsystems.controller;

import com.tsystems.dto.CargoDto;
import com.tsystems.dto.UserOrderDto;
import com.tsystems.enumaration.UserOrderStatus;
import com.tsystems.service.api.CargoService;
import com.tsystems.service.api.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/cargo")
public class CargoController {

    private final UserOrderService userOrderService;

    private final CargoService cargoService;

    @Autowired
    public CargoController(UserOrderService userOrderService, CargoService cargoService) {
        this.userOrderService = userOrderService;
        this.cargoService = cargoService;
    }

    /**
     * Add new cargo
     *
     * @param orderId
     * @param model
     * @return addCargo.jsp
     */
    @GetMapping(value = "/add")
    public String getAddPage(@RequestParam("orderId") long orderId, Model model) {
        CargoDto cargoDto = new CargoDto();
        model.addAttribute("order", userOrderService.findById(orderId));
        model.addAttribute("cargo", cargoDto);
        return "cargo/addCargo";
    }

    /**
     * Add new cargo
     *
     * @param cargoDto
     * @param orderId
     * @param model
     * @return allOrders.jsp
     */
    @PostMapping(value = "/add")
    public String addCargo(@ModelAttribute("cargo") CargoDto cargoDto,
                           @RequestParam("orderId") long orderId,
                           Model model) {
        if (!cargoService.addCargoToOrder(cargoDto, orderId)) {
            model.addAttribute("addCargoError", "Can't add cargo because this order is taken");
        }
        return "redirect:/order/all";
    }

    /**
     * Edit cargo
     *
     * @param cargoId
     * @param orderId
     * @param model
     * @return edit.jsp
     */
    @GetMapping(value = "/edit")
    public String editCargoPage(@RequestParam("cargoId") long cargoId,
                                @RequestParam("orderId") long orderId,
                                Model model) {
        UserOrderDto userOrderDto = userOrderService.findById(orderId);
        if (userOrderDto.getStatus().equals(UserOrderStatus.TAKEN)) {
            model.addAttribute("editCargoError", "Can't edit this cargo because this order is taken");
        }
        model.addAttribute("order", userOrderDto);
        model.addAttribute("cargo", cargoService.findById(cargoId));
        return "cargo/edit";
    }

    /**
     * Edit cargo
     *
     * @param cargoDto
     * @param orderId
     * @param model
     * @return allOrders.jsp
     */
    @PostMapping(value = "/edit")
    public String editCargo(@ModelAttribute("cargo") CargoDto cargoDto,
                            @RequestParam("orderId") long orderId,
                            Model model) {
        if (!cargoService.editCargo(cargoDto, orderId)) {
            model.addAttribute("editCargoError", "Can't edit this cargo");
        }
        return "redirect:/order/all";
    }

    /**
     * Delete cargo
     *
     * @param cargoId
     * @param orderId
     * @param model
     * @return allOrders.jsp
     */
    @PostMapping(value = "/delete")
    public String deleteCargo(@RequestParam("cargoId") long cargoId,
                              @RequestParam("orderId") long orderId,
                              Model model) {
        if (!cargoService.deleteCargo(cargoId, orderId)) {
            model.addAttribute("deleteCargoError", "Can't delete this cargo");
        }
        return "redirect:/order/all";
    }

    @PostMapping("/delivered")
    public String cargoIsDelivered(@RequestParam("id") long id, Model model) {
        if (!cargoService.setStatusDelivered(id)){
            model.addAttribute("setStatusDeliveredError", "You cannot set this status for this cargo");
        }
        return "redirect:/driver/cabinet";
    }

    @PostMapping("/shipped")
    public String cargoIsShipped(@RequestParam("id") long id, Model model) {
        if (!cargoService.setStatusShipped(id)){
            model.addAttribute("setStatusShippedError", "You cannot set this status for this cargo");
        }
        return "redirect:/driver/cabinet";
    }
}
