package com.example.helping_animals.controller.mvc;

import com.example.helping_animals.dto.UserDto;
import com.example.helping_animals.model.User;
import com.example.helping_animals.service.AnimalService;
import com.example.helping_animals.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private AnimalService animalService;

    @GetMapping()
    public String showIndex(HttpServletResponse response, Model model) {
        response.setHeader("X-Frame-Options", "allow");
        response.setHeader("Content-Security-Policy", "frame-ancestors 'self'");
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) model.addAttribute("authorized", userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("animals", animalService.getAllAnimals());
        return "index";
    }

    @GetMapping("/dogs")
    public String showDogs(Model model) {
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) model.addAttribute("authorized", userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("animals", animalService.findAnimalsByAnimalType("DOG"));
        return "index";
    }

    @GetMapping("/cats")
    public String showCats(Model model) {
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) model.addAttribute("authorized", userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("animals", animalService.findAnimalsByAnimalType("CAT"));
        return "index";
    }

    @GetMapping("/id/{id}")
    public String showDetails(@PathVariable String id, Model model){
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) model.addAttribute("authorized", userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("animal", animalService.getAnimalById(Long.parseLong(id)));
        return "details";
    }


}
