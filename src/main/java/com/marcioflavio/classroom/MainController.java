package com.marcioflavio.classroom;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller
public class MainController {

    @GetMapping("/")
    public String getForm(Model model) {
        return "inventory";
    }

    @GetMapping("/inventory")
    public String getInventory(Model model) {
        return "form";
    }
    
}
