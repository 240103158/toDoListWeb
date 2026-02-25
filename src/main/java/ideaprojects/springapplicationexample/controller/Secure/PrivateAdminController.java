package ideaprojects.springapplicationexample.controller.Secure;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class PrivateAdminController {

    @GetMapping("management")
    public String getAdminPage(){
        return "private/admin/management-page";
    }
}
