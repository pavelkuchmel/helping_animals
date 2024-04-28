package com.example.helping_animals.controller.mvc;

import com.example.helping_animals.dto.AnimalDto;
import com.example.helping_animals.dto.AnimalRegistrationDto;
import com.example.helping_animals.dto.UserDto;
import com.example.helping_animals.dto.UserEditDto;
import com.example.helping_animals.service.AnimalService;
import com.example.helping_animals.service.AnimalTypeService;
import com.example.helping_animals.service.MailSenderService;
import com.example.helping_animals.service.UserService;
import com.example.helping_animals.util.ImageSaver;

import com.example.helping_animals.util.QRUtilAndSaver;
import com.google.zxing.WriterException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.IOException;
import java.text.ParseException;

import static com.example.helping_animals.util.EncryptDecryptUtils.encrypt;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AnimalTypeService animalTypeService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private MailSenderService mailService;

    @Autowired
    private ImageSaver imageSaver;

    @Autowired
    private QRUtilAndSaver qrSaver;
    @Value("${mail.message.mail-activation.title}")
    private String mailMessageActivationTitle;
    @Value("${mail.message.mail-activation.body}")
    private String mailMessageActivationBody;
    @Value("${activation.url}")
    private String activationUrl;

    @Value("${message.error.unknown}")
    private String msgErrorUnknown;

    @Value("${message.animal.added.successfully}")
    private String msgAnimalAddedSuccessfully;

    @Value("${message.error.not-authorization}")
    private String msgErrorAuthorization;

    @Value("${message.delete.error.id-not-match}")
    private String msgDeleteErrorIdNotMatch;

    @Value("${message.delete.successfully}")
    private String msgDeleteSuccessfully;
    @Value("${message.edit.successfully}")
    private String msgEditSuccessfully;
    @Value("${message.registration.error.email.already}")
    private String msgErrorAlready;
    @Value("${message.edit.successfully-but-need-activated-email}")
    private String msgEditSuccessfullyButNeedActivatedEmail;
    @Value("${message.error.incorrect-id}")
    private String msgErrorId;

    @Value("${message.error.dont-access}")
    private String msgErrorDontAccess;

    @Value("${message.curator.successfully}")
    private String msgCuratorSuccessfully;

    @Value("${message.error.email-not-confirmed}")
    private String msgErrorEmailNotConfirmed;

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

    @GetMapping("/add-animal")
    public String addAnimalShow(Model model, RedirectAttributes redirectAttributes){
        UserDto userDto = null;
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) {
            userDto = userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("authorized", userDto);
        }
        if (userDto != null && !userDto.getActivated()) {
            redirectAttributes.addFlashAttribute("message", msgErrorEmailNotConfirmed);
            return "redirect:/user";
        }
        model.addAttribute("animal", new AnimalRegistrationDto());
        model.addAttribute("animalTypes", animalTypeService.findAllAnimalTypes());
        return "user/add-animal";
    }

    @GetMapping("/animals")
    public String showUserAnimals(@RequestParam(value = "image",required = false) MultipartFile image,
                                  @Valid @ModelAttribute("animal") AnimalRegistrationDto animalRegistrationDto, Model model){
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) {
            UserDto userDto = userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("authorized", userDto);
            model.addAttribute("animals", animalService.findAnimalsByUser(userDto));
        }
        return "user/animals-page";
    }

    @PostMapping("/add-animal")
    public String addAnimalSave(@RequestParam(value = "image",required = false) MultipartFile image,
                                @Valid @ModelAttribute("animal") AnimalRegistrationDto animalRegistrationDto,
                                Model model,
                                RedirectAttributes redirectAttributes){
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) {
            UserDto userDto = userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            if (userDto != null && userDto.getActivated()){
                model.addAttribute("authorized", userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
                animalRegistrationDto.setUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
                try {
                    animalRegistrationDto.setPhotoUrl(imageSaver.save(image));
                } catch (IOException e) {
                    model.addAttribute("message", e.getMessage());
                    return "user/add-animal";
                }
                try {
                    if (animalService.saveAnimalRegistrationDto(animalRegistrationDto) != null) {
                        model.addAttribute("message", msgAnimalAddedSuccessfully);
                        return "user/add-animal";
                    }
                } catch (ParseException e) {
                    model.addAttribute("message", e.getMessage());
                    return "user/add-animal";
                }
                model.addAttribute("message", msgErrorUnknown);
                return "user/add-animal";
            }else {
                redirectAttributes.addFlashAttribute("message", msgErrorEmailNotConfirmed);
                return "redirect:/user";
            }
        }
        return "redirect:/login";
    }
    @GetMapping("/delete-animal")
    public String deleteAnimal(@RequestParam Long id, RedirectAttributes redirectAttributes){
        UserDto userDto = null;
        AnimalDto animalDto = null;
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) {
            animalDto = animalService.getAnimalById(id);
            userDto = userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            if (animalDto.getUser().getId() == userDto.getId() || userDto.getRole().getName().equals("ROLE_ADMIN") || userDto.getRole().getName().equals("ROLE_MODERATOR")){
                    imageSaver.delete(animalDto.getPhoto());
                    animalService.deleteAnimalById(id);
                    redirectAttributes.addFlashAttribute("message", msgDeleteSuccessfully);
            }else if (animalDto.getUser().getId() != userDto.getId()){
                redirectAttributes.addFlashAttribute("message", msgDeleteErrorIdNotMatch);
            }else if (animalDto == null){
                redirectAttributes.addFlashAttribute("message", msgErrorId);
            }
        }else {
            redirectAttributes.addFlashAttribute("message", msgErrorAuthorization);
        }
        redirectAttributes.addFlashAttribute("authorized", userDto);
        return "redirect:/user/animals";
    }

    @GetMapping("/edit-profile")
    public String showFormEditProfile(Model model){
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")){
            UserDto userDto = userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("authorized", userDto);
            model.addAttribute("userform", new UserEditDto(userDto));
            return "user/edit-profile-page";
        }
        return "/login";
    }

    @PostMapping("/edit-profile")
    public String editProfile(@Valid @ModelAttribute("animal") UserEditDto userDtoFromEditForm,
                              Model model, RedirectAttributes redirectAttributes){
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")){
            UserDto currentUserDto = userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            UserDto newUserDto = null;
            if (currentUserDto != null){
                if (currentUserDto.getId() == userDtoFromEditForm.getId()/* || currentUserDto.getRole().getName().equals("ROLE_ADMIN") || currentUserDto.getRole().getName().equals("ROLE_MODERATOR")*/){
                    if (userDtoFromEditForm.getEmail() != null && !userDtoFromEditForm.getEmail().isBlank()){
                        newUserDto = userService.findUserDtoByEmail(userDtoFromEditForm.getEmail());
                        if (newUserDto == null){
                            userService.deactivationUser(currentUserDto.getEmail());
                            mailService.sendNewMail(userDtoFromEditForm.getEmail(), mailMessageActivationTitle, mailMessageActivationBody + activationUrl + encrypt(userDtoFromEditForm.getEmail()));
                            redirectAttributes.addFlashAttribute("message", msgEditSuccessfullyButNeedActivatedEmail);
                        }else {
                            if (!newUserDto.getEmail().trim().equalsIgnoreCase(currentUserDto.getEmail().trim())){
                                redirectAttributes.addFlashAttribute("message", msgErrorAlready);
                                return "redirect:/user/edit-profile";
                            }else {
                                redirectAttributes.addFlashAttribute("message", msgEditSuccessfully);
                            }
                        }
                    }else {
                        redirectAttributes.addFlashAttribute("message", msgEditSuccessfully);
                    }
                    userService.updateUser(userDtoFromEditForm);
                    return "redirect:/user";
                }else {
                    redirectAttributes.addFlashAttribute("message", msgErrorDontAccess);
                    return "redirect:/";
                }
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/curator-form")
    public String showCuratorForm(Model model, RedirectAttributes redirectAttributes){
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")){
            UserDto userDto = userService.findUserDtoByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            if (userDto.getRole().getName().equals("ROLE_GENERAL")) {
                model.addAttribute("authorized", userDto);
                return "user/curator-form";
            }
            redirectAttributes.addFlashAttribute("message", msgCuratorSuccessfully);
        }
        return "redirect:/login";
    }

    @PostMapping("/curator-form")
    public String curatorForm(@RequestParam(value = "image") MultipartFile image,
                              @RequestParam(value = "id") String id,
                              @RequestParam(value = "email") String email, Model model){
        return null;
    }

    @GetMapping("/test")
    public String test(){
        try {
            qrSaver.createQRandSave("google.com", "google");
        } catch (WriterException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "/";
    }
}
