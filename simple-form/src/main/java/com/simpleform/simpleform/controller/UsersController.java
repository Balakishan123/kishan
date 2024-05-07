package com.simpleform.simpleform.controller;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.simpleform.simpleform.model.UserModel;
import com.simpleform.simpleform.repository.UserJpaRepository;

import jakarta.servlet.http.HttpServletRequest;



@Controller
public class UsersController {

    @Autowired
    private UserJpaRepository userService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/")
    public String showHomePage() {
        return "home"; // Return the view name for home page
    }

    @GetMapping("/register-page")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserModel());
        return "register-page"; // returns the view register
    }

    @PostMapping("/register-page")
    public String registerUser(@ModelAttribute("user") UserModel user, Model model) {
        // To Check if the email already exists
        if (userService.existsByEmailId(user.getEmailId())) {
            model.addAttribute("error", "User with this email already exists!");
            return "register-page"; // Return back to the registration form with error message
        }

        // If email doesn't exist, save the user
        userService.save(user);
        return "redirect:/register-page?success"; // Redirect to the register page with a success parameter
    }


    @GetMapping("/login-page")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserModel());
        return "login-page"; // Return the view name for login form
    }

    @PostMapping("/login-page")
    public String loginUser(@ModelAttribute("user") UserModel user, Model model) {
        UserModel existingUser = userService.findByEmailId(user.getEmailId());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return "redirect:/login-page?success"; // Redirect to the login page after successful login
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login-page"; // Return back to the login form with error message
        }
    }
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPasswordForm(@RequestParam("email") String email, HttpServletRequest request) {
        UserModel user = userService.findByEmailId(email);
        if (user != null) {
            // Generate a unique token
            String token = UUID.randomUUID().toString();
            // Save the token in the database for the user
            user.setResetToken(token);
            userService.save(user);
            // Construct the reset password link
            String resetLink = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/reset-password?token=" + token;
            // Send an email with the reset link
            sendResetPasswordEmail(email, resetLink);
            return "forgot-password-success";
        } else {
            return "forgot-password-error";
        }
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        UserModel user = userService.findByResetToken(token);
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("token", token);
            return "reset-password";
        } else {
            return "reset-password-error";
        }
    }

    @PostMapping("/reset-password")
    public String processResetPasswordForm(@RequestParam("token") String token,
                                           @RequestParam("password") String password,
                                           @RequestParam("confirmPassword") String confirmPassword) {
        UserModel user = userService.findByResetToken(token);
        if (user != null) {
            // Check if password and confirm password match
            if (!password.equals(confirmPassword)) {
                // If passwords do not match, return to reset-password form with an error
                return "reset-password-error";
            }

            // If passwords match, update the user's password and reset token
            user.setPassword(password);
            user.setResetToken(null);
            userService.save(user);
            return "reset-password-success";
        } else {
            // If user not found with the provided token, return error
            return "reset-password-error";
        }
    }


    private void sendResetPasswordEmail(String email, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset Your Password");
        message.setText("To reset your password, please click the link below:\n\n" + resetLink);
        mailSender.send(message);
    }
}




