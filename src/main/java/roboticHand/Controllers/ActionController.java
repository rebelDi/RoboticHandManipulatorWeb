package roboticHand.Controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roboticHand.DAO.ActionRepository;

import javax.servlet.http.HttpServletRequest;

/*
Class for actions of imitator management
 */

@Controller
@RequestMapping("/action")
public class ActionController {
    @Autowired
    private ActionRepository actionRepository;

    /*
    This method gets all actions available for the imitator, writes
    them to the current session and redirects to the main page
     */
    @GetMapping(value = "/panel")
    public String controlPanelRedirect(HttpServletRequest request){
        actionRepository.getAllActions(request);
        return "main";
    }

    /*
    This method checks connection to the imitator and tell
    the result as good message or error, uploading it to
    the page without reloading it
     */
    @PostMapping(value = "/checkConnection")
    public ResponseEntity<?> checkConnection(){
        String flag = new ArduinoController().sendData("Hello");

        //If imitator is connected, it sends an empty message
        if(flag.equals("Connected")) {
            return ResponseEntity.ok("");
        }else{
            //If imitator is not connected, it sends an error
            return ResponseEntity.badRequest().body("");
        }
    }


    /*
    This method get all the data from client (from the sensor),
    processes it into Json and sends to Action repository for
    future procession and transmission
     */
    @RequestMapping(value = "/sendData", method = RequestMethod.POST)
    public void sendData(@RequestParam String queryData, HttpServletRequest request){
        JsonObject jsonObject = new Gson().fromJson(queryData, JsonObject.class);

        //Get all action names and values as string array
        JsonArray jsonArrayActions = jsonObject.get("actions").getAsJsonArray();
        String[] actions = new String[jsonArrayActions.size()];

        JsonArray jsonArrayValues = jsonObject.get("values").getAsJsonArray();
        String[] values = new String[jsonArrayValues.size()];

        for(int i = 0; i < actions.length; i++){
            actions[i] = jsonArrayActions.get(i).getAsString();
            values[i] = jsonArrayValues.get(i).getAsString();
        }

        actionRepository.sendData(actions, values, request);
    }
}
