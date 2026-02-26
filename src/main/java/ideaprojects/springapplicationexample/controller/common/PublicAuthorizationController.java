package ideaprojects.springapplicationexample.controller.common;


import ideaprojects.springapplicationexample.entity.User;
import ideaprojects.springapplicationexample.entity.UserRole;
import ideaprojects.springapplicationexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Set;

@Controller
public class PublicAuthorizationController {
    private final UserService service;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public PublicAuthorizationController(UserService service, BCryptPasswordEncoder passwordEncoder) {
        this.service = service;
        this.encoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(required = false) String error){
        if(error != null){
            model.addAttribute("isAuthenticatedFailed", true);
        }
        return "public/authorization/login-page";
    }

    @PostMapping("/login")
    public String getLoginPage(@RequestParam String email, @RequestParam String password){
        return "public/authorization/login-page";
    }

    @GetMapping("/registration") // todo work only when we need get page
    public String getRegistrationPage(){
        return "public/authorization/registration-page";
    }

    @PostMapping("/registration") // todo work only when create user
    public String createUserAccount(@RequestParam String name,
                                    @RequestParam String email,
                                    @RequestParam String password){
        String encodedPassword = encoder.encode(password);
        User user = new User(name, email, encodedPassword, UserRole.USER);
        service.saveUser(user);
        forceAutoLogin(email, encodedPassword);
        return "redirect:/account";
    }

    private void forceAutoLogin(String email, String password){
        Set<SimpleGrantedAuthority> roles = Collections.singleton(UserRole.USER.toAuthority());
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password, roles);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
