package com.anirudh.controller;

import com.anirudh.entity.Complaint;
import jakarta.persistence.Column;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {
    @GetMapping("/")
    public String home()
    {
        return "index";
    }

    @GetMapping("/main")
    public String showComplaintForm(Model model) {
        model.addAttribute("complaint", new Complaint());
        return "main";
    }
}
