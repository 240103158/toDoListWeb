package ideaprojects.springapplicationexample.controller.Secure;

import ideaprojects.springapplicationexample.Repository.UserRepository;
import ideaprojects.springapplicationexample.entity.User;
import ideaprojects.springapplicationexample.entity.UserRole;
import ideaprojects.springapplicationexample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/super-admin")
public class PrivateSuperAdminController {
    UserService userService;

    @Autowired
    public PrivateSuperAdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/change-status-to-admin")
    public String changeStatusToAdmin(@RequestParam int id) {
        User currUser = userService.getCurrentUser();
        User userToUpgrade = userService.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("not found with id: " + id));

        if(userToUpgrade.isSimpleUser() && !userToUpgrade.isSuperAdmin()){
            userService.updateRole(id, UserRole.ADMIN);
        }
        return "redirect:/admin/management";
    }
}
