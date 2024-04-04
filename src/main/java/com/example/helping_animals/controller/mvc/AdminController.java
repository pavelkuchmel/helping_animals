package com.example.helping_animals.controller.mvc;

import com.example.helping_animals.service.AnimalService;
import com.example.helping_animals.service.IncomeService;
import com.example.helping_animals.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String redirectWithUsingRedirectPrefix() {
        return "redirect:/admin/animals";
    }


    @GetMapping("/animals")
    public String showAnimals(Model model, @RequestParam(required = false) String type) {
        if (type != null){
            if (type.trim().equalsIgnoreCase("DOG")){
                model.addAttribute("animals", animalService.getAllAnimals().stream()
                        .filter(animalDto -> animalDto.getAnimalType().getName().trim().equalsIgnoreCase("DOG")).collect(Collectors.toList()));
                return "admin/admin-animals";
            }
            if (type.trim().equalsIgnoreCase("CAT")){
                model.addAttribute("animals", animalService.getAllAnimals().stream()
                        .filter(animalDto -> animalDto.getAnimalType().getName().trim().equalsIgnoreCase("CAT")).collect(Collectors.toList()));
                return "admin/admin-animals";
            }
        }
        model.addAttribute("animals", animalService.getAllAnimals());
        return "admin/admin-animals";
    }

    @GetMapping("/animals/search")
    public String findAnimalsPage(Model model, @RequestParam(required = false) Map<String, String> params){
        System.out.println(params);
        model.addAttribute("animals", animalService.getAllAnimals());
        model.addAttribute("marker_of_search", true);
        return "admin/admin-animals";
    }
}
