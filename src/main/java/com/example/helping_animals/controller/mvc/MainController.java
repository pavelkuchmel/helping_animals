package com.example.helping_animals.controller.mvc;

import com.example.helping_animals.dto.AnimalDto;
import com.example.helping_animals.dto.UserDto;
import com.example.helping_animals.model.User;
import com.example.helping_animals.service.AnimalService;
import com.example.helping_animals.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private AnimalService animalService;

    @Value("${message.activation.notification}")
    private String msgActivationNotification;

    @GetMapping()
    public String showIndex(Model model) {
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) {
            UserDto userDto = userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("authorized", userDto);
            if (!userDto.getActivated()) model.addAttribute("message", msgActivationNotification);
        }
        List<AnimalDto> animalDtos = animalService.getAllAnimals();
        Collections.shuffle(animalDtos);
        model.addAttribute("animals", animalDtos);
        return "index";
    }

    @GetMapping("/dogs")
    public String showDogs(Model model) {
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser"))
            model.addAttribute("authorized", userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("animals", animalService.findAnimalsByAnimalType("DOG"));
        return "index";
    }

    @GetMapping("/cats")
    public String showCats(Model model) {
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser"))
            model.addAttribute("authorized", userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("animals", animalService.findAnimalsByAnimalType("CAT"));
        return "index";
    }

    @GetMapping("/id/{id}")
    public String showDetails(@PathVariable String id, Model model){
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser"))
            model.addAttribute("authorized", userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("animal", animalService.getAnimalById(Long.parseLong(id)));
        return "details";
    }


}
