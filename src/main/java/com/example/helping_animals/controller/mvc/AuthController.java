package com.example.helping_animals.controller.mvc;

import com.example.helping_animals.dto.UserRegistrationDto;
import com.example.helping_animals.model.User;
import com.example.helping_animals.service.MailSenderService;
import com.example.helping_animals.service.UserService;
import static com.example.helping_animals.util.EncryptDecryptUtils.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AuthController {

    @Value("${registration.properties.length.min}")
    private Integer minLength;

    @Value("${registration.properties.length.max}")
    private Integer maxLength;

    @Value("${message.registration.error.password.length}")
    private String msgErrorLength;

    @Value("${message.registration.error.email.already}")
    private String msgErrorAlready;

    @Value("${message.registration.successfully}")
    private String msgSuccessfully;

    @Value("${message.error.unknown}")
    private String msgErrorUnknown;

    @Value("${mail.message.mail-activation.title}")
    private String mailMessageActivationTitle;

    @Value("${mail.message.mail-activation.body}")
    private String mailMessageActivationBody;

    @Value("${activation.url}")
    private String activationUrl;

    @Autowired
    private UserService userService;

    @Autowired
    private MailSenderService mailService;

    @Autowired
    private Environment env;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        model.addAttribute("user", userRegistrationDto);
        return "registration";
    }

    @PostMapping("/registration/save")
    public RedirectView registration(@Valid @ModelAttribute("user") UserRegistrationDto userRegistrationDto,
                                     BindingResult result,
                                     RedirectAttributes redirectAttributes,
                                     Model model){
        RedirectView redirectView = new RedirectView("/registration");
        User existingUser = userService.findUserByEmail(userRegistrationDto.getEmail());
        if(false){
            result.rejectValue("email", "already", msgErrorAlready);
        }
        if (userRegistrationDto.getPassword().length() < minLength
                || userRegistrationDto.getPassword().length() > maxLength){
            result.rejectValue("password", "length", msgErrorLength);
        }
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("user", userRegistrationDto);
            redirectAttributes.addFlashAttribute("message", result.getFieldError().getDefaultMessage());
            return redirectView;
        }
        if (true/*userService.saveUser(userRegistrationDto) != null*/) {
            mailService.sendNewMail(userRegistrationDto.getEmail(), mailMessageActivationTitle, mailMessageActivationBody + activationUrl + encrypt(userRegistrationDto.getEmail()));
            redirectAttributes.addFlashAttribute("user", userRegistrationDto);
            redirectAttributes.addFlashAttribute("message", msgSuccessfully);
            return redirectView;
        }
        redirectAttributes.addFlashAttribute("user", userRegistrationDto);
        redirectAttributes.addFlashAttribute("message", msgErrorUnknown);
        return redirectView;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
