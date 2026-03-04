package ideaprojects.springapplicationexample.controller.Secure;

import ideaprojects.springapplicationexample.entity.DTO.RecordDTO;
import ideaprojects.springapplicationexample.entity.RecordStatus;
import ideaprojects.springapplicationexample.entity.User;
import ideaprojects.springapplicationexample.service.RecordService;
import ideaprojects.springapplicationexample.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
public class PrivateCommandController {
    private final RecordService recordService;
    private final UserService userService;

    @Autowired
    public PrivateCommandController(RecordService recordService, UserService userService) {
        this.recordService = recordService;
        this.userService = userService;
    }

    @GetMapping
    public String getRecords(HttpServletRequest request, Model model, @RequestParam(name="filter", required = false) String filterMode){
        HttpSession session = request.getSession();
        Object counter = session.getAttribute("visitsCounter");
        if(counter != null){
            model.addAttribute("visitsCounter", (Integer) counter);
            session.setAttribute("visitsCounter",  ((Integer) counter + 1)) ;
        }else{
            model.addAttribute("visitsCounter", 0);
            session.setAttribute("visitsCounter",  1) ;
        }

        RecordDTO container = recordService.findAllRecords(filterMode);

        model.addAttribute("userName", container.getUserName());
        model.addAttribute("numberOfDone", container.getNumberOfDone());
        model.addAttribute("numberOfActive", container.getNumberOfActive());
        model.addAttribute("records", container.getRecords());

        return "private/account-page";
    }

    @PostMapping("/add-record")
    public String addRecord(@RequestParam(name = "title") String title){
        recordService.saveRecord(title);
        return "redirect:/account";
    }


    @PostMapping("delete-record")
    public String deleteRecord(@RequestParam(name = "id") int id,
                               @RequestParam(name = "filter", required = false) String filterMode){
        recordService.deleteRecord(id);
        return "redirect:/account"  + (!filterMode.isBlank() && filterMode != null ? "?filter=" + filterMode : "");
    }

    @PostMapping("make-record-done")
    public String makeRecordDone(@RequestParam(name = "id") int id,
                                 @RequestParam(name = "filter", required = false) String filterMode){
        recordService.updateRecordStatus(id, RecordStatus.DONE);
        return "redirect:/account" + (!filterMode.isBlank() && filterMode != null ? "?filter=" + filterMode : "");
    }


}

