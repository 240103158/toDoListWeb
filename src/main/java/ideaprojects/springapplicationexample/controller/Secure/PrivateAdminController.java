package ideaprojects.springapplicationexample.controller.Secure;

import ideaprojects.springapplicationexample.entity.User;
import ideaprojects.springapplicationexample.entity.UserRole;
import ideaprojects.springapplicationexample.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class PrivateAdminController {
    private final UserService userService;

    @Autowired
    public PrivateAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/management")
    public String getManagementPage(HttpServletRequest request, Model model, @RequestParam(name="filter", required = false) String filterMode){
        User user = userService.getCurrentUser();
        model.addAttribute("userName", user.getName());

        if(user.isSuperAdmin()){
            List<User> deleteUsers = userService.findAllByRoleIn(Arrays.asList(UserRole.USER, UserRole.ADMIN));
            List<User> upgradeUsers = deleteUsers.stream()
                    .filter(User::isSimpleUser)
                    .collect(Collectors.toList());

            model.addAttribute("candidatesToDelete", deleteUsers);
            model.addAttribute("candidatesToUpgrade", upgradeUsers);
        }else {
            List<User> deleteUsers = userService.findAllByRoleIn(Arrays.asList(UserRole.USER));
            model.addAttribute("candidatesToDelete", deleteUsers);
        }

        return "private/admin/management-page";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam int id){
        User currUser = userService.getCurrentUser();
        User user = userService.findById(id).orElseThrow(() -> new UsernameNotFoundException("User this id: " + id + "not found" ));
        if(user == null){
            return "redirect:/admin/management";
        }

        if(user.isSuperAdmin()){
            return "redirect:/admin/management";
        }
        else if (user.IsAdmin() && !currUser.isSuperAdmin()){
            return "redirect:/admin/management";
        }
        userService.deleteById(id);

        return "redirect:/admin/management";
    }
}
