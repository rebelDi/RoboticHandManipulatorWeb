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
    public ResponseEntity<?> checkConnection(@RequestBody String ipAddress){
        System.out.println(ipAddress);
        String flag;
        if (ipAddress.length() > 0 && !ipAddress.equals("no=")) {
            ipAddress = ipAddress.substring(0, ipAddress.length() - 1);
            if(checkIp(ipAddress)){
                flag = new ArduinoController().sendData("Hello", ipAddress);
            }else {
                flag = new ArduinoController().sendData("Hello", "");
            }
        }else {
            flag = new ArduinoController().sendData("Hello", "");
        }

        System.out.println(ipAddress);

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
    public void sendData(@RequestParam String queryData, @RequestParam String ip, HttpServletRequest request){
        if (ip.length() > 0 && !ip.equals("no")) {
            if(!checkIp(ip)){
                ip = "";
            }
        }else {
            ip = "";
        }

        actionRepository.getAllActions(request);

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

        actionRepository.sendData(actions, values, request, ip);
    }

    private boolean checkIp(String ipAddress){
        String[] tokens = ipAddress.split("\\.");
        if (tokens.length != 4) {
            return false;
        } for (String str : tokens) {
            int i = Integer.parseInt(str);
            if ((i < 0) || (i > 255)) {
                return false; }
        }
        return true;
    }
}
