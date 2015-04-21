package com.aimprosoft;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("VIEW")
public class ViewController {

    @RequestMapping  // default (action=list)
    public String showPetSites() {
        return "index";
    }

}
