package kotelnikov.axamitdemo.restservice;

import kotelnikov.axamitdemo.tasks.ExpressionEvaluator;
import kotelnikov.axamitdemo.tasks.NumSystemsConverter;
import kotelnikov.axamitdemo.tasks.post.PostOfficeSimulator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/task")
public class MainController {

    @PostMapping("/1")
    public String transformToNewBase(@RequestParam int number, @RequestParam int base){
        return NumSystemsConverter.convertToNewBase( number, base );
    }

    @PostMapping("/2")
    public String evaluateExpressions(@RequestParam(defaultValue = "expressions.txt") String file ){
        return ExpressionEvaluator.evaluateFromFile(file);
    }

    @PostMapping("/3")
    public String runPostDepartment(){
        if ( PostOfficeSimulator.postIsRunning ){
            return "Time elapsed: " + PostOfficeSimulator.elapsedTime + "; "
                    + "Parcels at Dept1: " + PostOfficeSimulator.currentParcelsAtDept1 + "; "
                    + "Parcels at Dept2: " + PostOfficeSimulator.currentParcelsAtDept2 + "; ";
        } else {
            new Thread(PostOfficeSimulator::runPost).start();
            return "OK";
        }
    }

}
