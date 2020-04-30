package com.tsystems.controller;

import com.tsystems.dto.TruckDto;
import com.tsystems.service.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(value = "/truck")
public class TruckController {
    @Autowired
    private TruckService truckService;

    /**
     * Delete truck by id.
     *
     * @param id - truck id
     * @return office/allOffices.jsp
     */
    @PostMapping("/delete")
    public String deleteTruck(@RequestParam long id, HttpServletRequest request) {
        truckService.deleteById(id);
        return getPreviousPageByRequest(request).orElse("redirect:/office/all");
    }

    /**
     * Add new truck.
     *
     * @param officeId - office id
     * @param model    - model
     * @return truck/addTruck.jsp
     */
    @GetMapping("/add")
    public String addPage(@RequestParam("officeId") long officeId, Model model) {
        model.addAttribute("officeId", officeId);
        model.addAttribute("newTruck", new TruckDto());
        return "truck/addTruck";
    }

    /**
     * Add new truck.
     *
     * @param truckDto - truck
     * @param officeId - office id
     * @param model    - model
     * @return previous page
     */
    @PostMapping("/add")
    public String addTruck(@ModelAttribute("newTruck") @Valid TruckDto truckDto,
                           @RequestParam("officeId") long officeId, Model model) {
        if (!truckService.save(officeId, truckDto)) {
            model.addAttribute("addTruckError",
                    "The truck with registration number " + truckDto.getRegistrationNumber() + " already exists.");
            return "truck/addTruck";
        }
        return "redirect:/office/" + officeId;
    }

    /**
     * Edit the truck
     *
     * @param id    - truck id
     * @param model - model
     * @return truck/editTruck.jsp
     */
    @GetMapping("/edit")
    public String editTruckPage(@RequestParam long id, Model model) {
        model.addAttribute("truck", truckService.findById(id));
        return "truck/editTruck";
    }

    /**
     * Edit the truck
     *
     * @param truckDto - truck
     * @param model    - model
     * @return view
     */
    @PostMapping("/edit")
    public String editTruck(@ModelAttribute("truck") @Valid TruckDto truckDto, Model model) {
        if (!truckService.update(truckDto)) {
            model.addAttribute("editTruckError",
                    "The truck with registration number " + truckDto.getRegistrationNumber() + " already exists.");
            return "truck/editTruck";
        }
        return "redirect:/truck/" + truckDto.getId();
    }

    /**
     * Returns the truck page.
     *
     * @return truck.jsp
     */
    @GetMapping("/{id}")
    public String getTruck(@PathVariable long id, Model model) {
        model.addAttribute("truck", truckService.findById(id));
        return "truck/truck";
    }

    /**
     * Get add driver to truck page
     *
     * @param truckId - truck id
     * @param model   - model
     * @return truck/addDriver.jsp
     */
    @GetMapping("/add/driver")
    public String addDriverToTruckPage(@RequestParam("truckId") long truckId, Model model) {
        model.addAttribute("truckId", truckId);
        model.addAttribute("drivers", truckService.findAllAvailableDrivers(truckId));
        return "truck/addDriver";
    }

    /**
     * Add driver to truck
     *
     * @param truckId  - truck id
     * @param driverId - driver id
     * @return truck/truck.jsp
     */
    @PostMapping("/add/driver")
    public String addDriverToTruck(@RequestParam("truckId") long truckId,
                                   @RequestParam("driverId") long driverId) {
        truckService.addDriver(truckId, driverId);
        return "redirect:/truck/" + truckId;
    }

    /**
     * Remove the driver from the truck
     *
     * @param truckId  - truck id
     * @param driverId - driver id
     * @return truck/truck.jsp
     */
    @PostMapping("/remove/driver")
    public String removeDriver(@RequestParam("truckId") long truckId,
                               @RequestParam("driverId") long driverId) {
        truckService.removeDriver(truckId, driverId);
        return "redirect:/truck/" + truckId;
    }

    /**
     * Find all available trucks for this order
     *
     * @param orderId - order id
     * @param model   -  model
     * @return truck/allAvailable.jsp
     */
    @GetMapping("/add/order")
    public String addTruckToOrderPage(@RequestParam long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        model.addAttribute("truckPairs", truckService.findAllAvailable(orderId));
        return "truck/allAvailable";
    }

    /**
     * Add truck to order
     *
     * @param orderId - order id
     * @param truckId - truck id
     * @return order/notTakenOrders.jsp
     */
    @PostMapping("add/order")
    public String addTruckToOrder(@RequestParam long orderId,
                                  @RequestParam long truckId) {
        truckService.addOrder(truckId, orderId);
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
