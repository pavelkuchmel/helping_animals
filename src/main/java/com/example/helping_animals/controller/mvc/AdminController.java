package com.example.helping_animals.controller.mvc;

import com.example.helping_animals.dto.AnimalDto;
import com.example.helping_animals.dto.UserDto;
import com.example.helping_animals.service.AnimalService;
import com.example.helping_animals.service.IncomeService;
import com.example.helping_animals.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
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
        if (model.getAttribute("found-animals") != null){
            model.addAttribute("animals", (List<AnimalDto>)model.getAttribute("found-animals"));
            return "admin/admin-animals";
        }
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
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) model.addAttribute("authorized", userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("animals", animalService.getAllAnimals());
        return "admin/admin-animals";
    }

    @GetMapping("/animals/search")
    public RedirectView searchAnimals(Model model, RedirectAttributes attributes, @RequestParam(required = false) Map<String, Object> params){
        List<AnimalDto> animalDtos = animalService.getAllAnimals();
        List<AnimalDto> result = new ArrayList<>();
        attributes.addFlashAttribute("found-animals", animalDtos.stream().filter(
                animalDto -> {
                    if (params.get("type") != null) {
                        return animalDto.getAnimalType().getName().trim().equalsIgnoreCase(params.get("type").toString().trim());
                    }else {
                        return true;
                    }
                }
        ).filter(animalDto -> {
                    if (params.get("gender") != null) {
                        if (params.get("gender").toString().trim().equalsIgnoreCase("UNKNOWN")) {
                            return true;
                        }
                        return animalDto.getGender().getName().trim().equalsIgnoreCase(params.get("gender").toString().trim());
                    }else {
                        return true;
                    }
                }
        ).filter(animalDto -> {
            if (params.get("sterilization") != null) {
                return animalDto.getSterilization();
            }else {
                return true;
            }
        }).filter(animalDto -> {
            if (params.get("vaccination") != null) {
                return animalDto.getVaccinated() != null;
            }else {
                return true;
            }
        }).filter(animalDto -> {
            if (params.get("chipped") != null) {
                return animalDto.getChipped();
            }else {
                return true;
            }
        }).filter(animalDto -> {
            if (params.get("toilet") != null) {
                return animalDto.getToiletOutside();
            }else {
                return true;
            }
        }).filter(animalDto -> {
            if (animalDto.getAge() >= Integer.parseInt((String)params.get("age-min")) && animalDto.getAge() <= Integer.parseInt((String)params.get("age-max"))){
                return true;
            }
            return false;
        }).filter(animalDto -> {
            if (animalDto.getHeight() >= Integer.parseInt((String) params.get("height-min")) && animalDto.getHeight() <= Integer.parseInt((String) params.get("height-max"))) {
                return true;
            }
            return false;
        }).filter(animalDto ->
                animalDto.getName().trim().toLowerCase().contains(params.get("name").toString().trim().toLowerCase())
        ).collect(Collectors.toList()));
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) attributes.addFlashAttribute("authorized", userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        return new RedirectView("/admin/animals");
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        if (model.getAttribute("found-users") != null){
            model.addAttribute("users", (List<UserDto>)model.getAttribute("found-users"));
            return "admin/admin-users";
        }
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) model.addAttribute("authorized", userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("users", userService.getAllUsers());
        return "admin/admin-users";
    }

    @GetMapping("/users/search")
    public RedirectView searchUsers(Model model, RedirectAttributes attributes, @RequestParam(required = false) Map<String, Object> params){
        List<UserDto> userDtos = userService.getAllUsers();
        attributes.addFlashAttribute("found-users", userDtos.stream().filter(
                userDto -> {
                    if (params.get("role") != null) {
                        if (params.get("role").toString().trim().equalsIgnoreCase("UNKNOWN")) {
                            return true;
                        }
                        return userDto.getRole().getName().trim().equalsIgnoreCase(params.get("role").toString().trim());
                    }else {
                        return true;
                    }
                }
        ).filter(
                userDto -> {
                    if (params.get("firstName") != null) {
                        return userDto.getFirstName().trim().toLowerCase().contains(params.get("firstName").toString().trim().toLowerCase());
                    }else {
                        return true;
                    }
                }
        ).filter(
                userDto -> {
                    if (params.get("lastName") != null) {
                        return userDto.getLastName().trim().toLowerCase().contains(params.get("lastName").toString().trim().toLowerCase());
                    }else {
                        return true;
                    }
                }
        ).filter(
                userDto -> {
                    if (params.get("email") != null) {
                        return userDto.getEmail().trim().toLowerCase().contains(params.get("email").toString().trim().toLowerCase());
                    }else {
                        return true;
                    }
                }
        ).filter(
                userDto -> {
                    if (params.get("city") != null) {
                        return userDto.getAddress().trim().toLowerCase().contains(params.get("city").toString().trim().toLowerCase());
                    }else {
                        return true;
                    }
                }
        ).filter(userDto -> {
            if (params.get("activated") != null) {
                return userDto.getActivated();
            }else {
                return true;
            }
        }).filter(userDto -> {
            if (params.get("incomes") != null) {
                return userDto.getIncomes() != null;
            }else {
                return true;
            }
        }).collect(Collectors.toList()));
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) attributes.addFlashAttribute("authorized", userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        return new RedirectView("/admin/users");
    }

}
