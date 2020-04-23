package net.gentledot.springcodeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/v1")
@Controller
public class MainController {

    @RequestMapping(value = "/")
    public String index(){
        return "home";
    }
}
