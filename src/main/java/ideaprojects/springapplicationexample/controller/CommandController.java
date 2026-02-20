package ideaprojects.springapplicationexample.controller;

import ideaprojects.springapplicationexample.entity.DTO.RecordDTO;
import ideaprojects.springapplicationexample.entity.RecordStatus;
import ideaprojects.springapplicationexample.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommandController {
    private final RecordService recordService;

    @Autowired
    public CommandController(RecordService recordService) {
        this.recordService = recordService;
    }

    @RequestMapping("/")
    public String redirectToHomePage(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String getMainPage(){
        return "main-page";
    }

    @RequestMapping("/records")
    public String getRecords(Model model, @RequestParam(name="filter", required = false) String filterMode){
        RecordDTO container = recordService.findAllRecords(filterMode);
        model.addAttribute("numberOfDone", container.getNumberOfDone());
        model.addAttribute("numberOfActive", container.getNumberOfActive());
        model.addAttribute("records", container.getRecords());

        return "main-page";
    }

    @RequestMapping(value = "/add-record", method = RequestMethod.POST)
    public String addRecord(@RequestParam(name = "title") String title){
        recordService.saveRecord(title);
        return "redirect:/records";
    }


    @RequestMapping(value = "/delete-record", method = RequestMethod.POST)
    public String deleteRecord(@RequestParam(name = "id") int id,
                               @RequestParam(name = "filter", required = false) String filterMode){
        recordService.deleteRecord(id);
        return "redirect:/records"  + (!filterMode.isBlank() && filterMode != null ? "?filter=" + filterMode : "");
    }

    @RequestMapping(value="/make-record-done", method = RequestMethod.POST)
    public String makeRecordDone(@RequestParam(name = "id") int id,
                                 @RequestParam(name = "filter", required = false) String filterMode){
        recordService.updateRecordStatus(id, RecordStatus.DONE);
        return "redirect:/records" + (!filterMode.isBlank() && filterMode != null ? "?filter=" + filterMode : "");
    }


}
