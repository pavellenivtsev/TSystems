package com.tsystems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/dispatcher")
public class DispatcherController {
//
//    private final DriverService driverService;
//
//    @Autowired
//    public DispatcherController(DriverService driverService) {
//        this.driverService = driverService;
//    }

    @GetMapping("/cabinet")
    public String getDispatcherCabinet(){
        return "dispatcher/cabinet";
    }


//    /**
//     * Returns the allDrivers page.
//     *
//     * @return allDrivers.jsp
//     */
//    @GetMapping(value = "/driver/all")
//    public ModelAndView getDrivers() {
//        List<DriverDto> driverDtoList=driverService.findAll();
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("dispatcher/allDrivers");
//        modelAndView.addObject("drivers", driverDtoList);
//        return modelAndView;
//    }

}
