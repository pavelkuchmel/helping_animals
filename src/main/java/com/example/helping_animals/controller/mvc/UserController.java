package com.example.helping_animals.controller.mvc;

import com.example.helping_animals.dto.UserDto;
import com.example.helping_animals.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping()
    public String showIndex(Model model) {
        UserDto userDto = null;
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) {
            userDto = userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("authorized", userDto);
        }
        model.addAttribute("user", userDto);
        return "user/user-page";
    }

}
