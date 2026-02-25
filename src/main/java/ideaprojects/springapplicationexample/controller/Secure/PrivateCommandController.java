package ideaprojects.springapplicationexample.controller.Secure;

import ideaprojects.springapplicationexample.entity.DTO.RecordDTO;
import ideaprojects.springapplicationexample.entity.RecordStatus;
import ideaprojects.springapplicationexample.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
public class PrivateCommandController {
    private final RecordService recordService;

    @Autowired
    public PrivateCommandController(RecordService recordService) {
        this.recordService = recordService;
    }


    @GetMapping
    public String getRecords(Model model, @RequestParam(name="filter", required = false) String filterMode){
        RecordDTO container = recordService.findAllRecords(filterMode);
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

