package com.example.helping_animals.controller.mvc;

import com.example.helping_animals.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private AnimalService animalService;

    @GetMapping()
    public String showIndex(Model model) {
        model.addAttribute("animals", animalService.getAllAnimals());
        return "index";
    }

    @GetMapping("/id/{id}")
    public String showDetails(@PathVariable String id, Model model){
        model.addAttribute("animal", animalService.getAnimalById(Long.parseLong(id)));
        return "details";
    }
}
