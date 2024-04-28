package com.example.helping_animals.controller.mvc;

import com.example.helping_animals.dto.UserDto;
import com.example.helping_animals.dto.UserRegistrationDto;
import com.example.helping_animals.model.User;
import com.example.helping_animals.service.MailSenderService;
import com.example.helping_animals.service.UserService;
import static com.example.helping_animals.util.EncryptDecryptUtils.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Value("${message.activation.successfully}")
    private String msgActivationSuccessfully;

    @Value("${message.activation.send}")
    private String msgActivationSend;

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
        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
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
        if (userService.saveUser(userRegistrationDto) != null) {
            mailService.sendNewMail(userRegistrationDto.getEmail(), mailMessageActivationTitle, mailMessageActivationBody + activationUrl + encrypt(userRegistrationDto.getEmail()));
            redirectAttributes.addFlashAttribute("user", userRegistrationDto);
            redirectAttributes.addFlashAttribute("message", msgSuccessfully);
            redirectView.setUrl("/login");
            return redirectView;
        }
        redirectAttributes.addFlashAttribute("user", userRegistrationDto);
        redirectAttributes.addFlashAttribute("message", msgErrorUnknown);
        return redirectView;
    }

    @GetMapping("/activation")
    public RedirectView activation(@RequestParam(required = false) String token, RedirectAttributes redirectAttributes){
        if (token == null || token.length() == 0){
            if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")){
                UserDto userDto = userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
                if (!userDto.getActivated()){
                    mailService.sendNewMail(userDto.getEmail(), mailMessageActivationTitle, mailMessageActivationBody + activationUrl + encrypt(userDto.getEmail()));
                    redirectAttributes.addFlashAttribute("message", msgActivationSend);
                }
                return new RedirectView("/user");
            }
            return new RedirectView("/login");
        }
        if (token != null && token.length() > 0){
            String email = decrypt(token);
            try{
                if (userService.activationUser(email)) {
                    redirectAttributes.addFlashAttribute("message", msgActivationSuccessfully);
                }/*else {
                    redirectAttributes.addFlashAttribute("message", msgErrorUnknown);
                }*/
            }catch (Exception e){
                redirectAttributes.addFlashAttribute("message", e.getMessage());
            }
        }
        return new RedirectView("/");
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}
