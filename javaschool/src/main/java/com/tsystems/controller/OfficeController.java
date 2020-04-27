package com.tsystems.controller;

import com.tsystems.dto.OfficeDto;
import com.tsystems.service.api.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/office")
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    /**
     * Get all offices
     *
     * @param model - model
     * @return office/allOffices.jsp
     */
    @GetMapping("/all")
    public String getAllOffices(Model model) {
        model.addAttribute("offices", officeService.findAll());
        return "office/allOffices";
    }

    /**
     * Get add office page
     *
     * @param model - model
     * @return office/addOffice.jsp
     */
    @GetMapping("/add")
    public String addOfficePage(Model model) {
        model.addAttribute("office", new OfficeDto());
        return "office/addOffice";
    }

    /**
     * Add new office
     *
     * @param officeDto - office
     * @return office/allOffices.jsp
     */
    @PostMapping("/add")
    public String addOffice(@ModelAttribute("office") @Valid OfficeDto officeDto) {
        officeService.save(officeDto);
        return "redirect:/office/all";
    }

    /**
     * Returns the office page.
     *
     * @param id    - office id
     * @param model - model
     * @return office/office.jsp
     */
    @GetMapping(value = "/{id}")
    public String getOffice(@PathVariable long id,
                            Model model) {
        model.addAttribute("office", officeService.findById(id));
        return "office/office";
    }

    /**
     * Delete office
     *
     * @param id - office id
     * @return office/allOffices.jsp
     */
    @PostMapping("/delete")
    public String deleteOffice(@RequestParam("id") long id) {
        officeService.deleteById(id);
        return "redirect:/office/all";
    }
}
