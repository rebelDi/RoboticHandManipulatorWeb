package roboticHand.Controllers;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import roboticHand.DAO.ActionRepository;
import roboticHand.DAO.UserRepository;
import roboticHand.Model.Action;
import roboticHand.Model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/*
Class for managing all actions of admin
 */

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private UserRepository userRepository;

    /*
    This method get the list of all users in system
     */
    @GetMapping(value = "/users")
    public String getAllUsers(HttpServletRequest request){
        String rights = (String) request.getSession().getAttribute("rights");

        //If current rights of user is S(superAdmin), he gets every user
        //except himself
        if(rights.equals("S")) {
            userRepository.getEveryoneExceptSuperAdmin(request);
            return "adminUsers";
        }else if(rights.equals("A")) {
            //if the user is A(Admin), he gets the list of usual users
            userRepository.getAllUsers(request);
            return "adminUsers";
        }else{
            //If user is usual user, system redirects him to the main page
            return "/";
        }
    }

    /*
    This method gets the user and his new rights and tries to change them
     */
    @PostMapping(value = "/userRightsEdit")
    public String changeUserRights(@RequestBody String[] user, HttpServletRequest request){
        User user1 = new User();
        user1.setLogin(user[0]);
        user1.setRights(user[1].charAt(0));

        String rights = (String) request.getSession().getAttribute("rights");

        //If user is not usual user, he can change rights
        if(rights.equals("S") || rights.equals("A")) {
            userRepository.editRights(user1);
            return "adminUsers";
        }else{
            return "/";
        }
    }

    /*
    This method gets the list of all users waiting for an approval to control imitator
     */
    @GetMapping(value = "/waitingList")
    public String showUsersInWaitingList(HttpServletRequest request){
        String rights = (String) request.getSession().getAttribute("rights");

        //Only superAdmin can do that
        if(rights.equals("S") || rights.equals("A")) {
            userRepository.getUsersInWaitingList(request);
            return "adminUsers";
        }else{
            return "/";
        }
    }

    /*
    This method redirects the user to the page of editing imitator's actions
     */
    @GetMapping(value = "/actions")
    public String changeActions(){
        return "adminImitator";
    }

    /*
    This method gets every action and its parameters from the form and edits them,
    then it gets new list of actions and save is to session
     */
    @RequestMapping(value = "/actionEdit", method = RequestMethod.POST)
    public String editAction(@RequestParam String actions, HttpServletRequest request){
        ArrayList<Action> actionsToEdit = new ArrayList<>();
        JsonObject jsonObject = new Gson().fromJson(actions, JsonObject.class);

        //Get actions name
        JsonArray jsonActionsName = jsonObject.get("actionsName").getAsJsonArray();
        JsonArray jsonServoNumbers= jsonObject.get("servosNum").getAsJsonArray();
        JsonArray jsonLeapMin = jsonObject.get("leapsMin").getAsJsonArray();
        JsonArray jsonLeapMax = jsonObject.get("leapsMax").getAsJsonArray();
        JsonArray jsonServoDirections = jsonObject.get("servosD").getAsJsonArray();
        JsonArray jsonServoMins = jsonObject.get("servosMin").getAsJsonArray();
        JsonArray jsonServoMax = jsonObject.get("servosMax").getAsJsonArray();
        JsonArray jsonAvailabilities = jsonObject.get("avails").getAsJsonArray();

        for(int i = 0; i < jsonActionsName.size(); i++){
            Action action = new Action();
            action.setActionLeap(jsonActionsName.get(i).getAsString());
            action.setHandAction(Integer.parseInt(jsonServoNumbers.get(i).getAsString()));
            action.setLeapMin(Integer.parseInt(jsonLeapMin.get(i).getAsString()));
            action.setLeapMax(Integer.parseInt(jsonLeapMax.get(i).getAsString()));
            action.setServoDirection(Integer.parseInt(jsonServoDirections.get(i).getAsString()));
            action.setServoMin(Integer.parseInt(jsonServoMins.get(i).getAsString()));
            action.setServoMax(Integer.parseInt(jsonServoMax.get(i).getAsString()));
            action.setAvailability(Integer.parseInt(jsonAvailabilities.get(i).getAsString()));
            actionsToEdit.add(action);
        }
        actionRepository.edit(actionsToEdit);
        actionRepository.getAllActions(request);
        return "admin/actionEdit";
    }
}
