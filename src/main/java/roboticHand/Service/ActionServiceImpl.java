package roboticHand.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import roboticHand.Controllers.ArduinoController;
import roboticHand.DAO.ActionRepository;
import roboticHand.Model.Action;
import roboticHand.Model.ArduinoData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/*
Class for the realization of methods of ActionRepository
 */
public class ActionServiceImpl implements ActionRepository {

    /*
    This method gets the list of all actions available for imitator and saves it to session
     */
    @Override
    public void getAllActions(HttpServletRequest request) {
        //Opens session for operations on database
        Session session = openSession();
        Query q = session.createQuery("SELECT _action FROM Action _action");
        List<Action> actions = q.list();
        //saves to the current session the list
        saveToSession(actions, request);
        session.close();
    }

    /*
    This method edits all information of the action in database
     */
    @Override
    public void edit(ArrayList<Action> actionsToEdit) {
        //Opens session for operations on database
        Session session = openSession();
        for(int i = 0; i < actionsToEdit.size(); i++){
            if(getActionByName(actionsToEdit.get(i).getActionLeap()) != null){
                try {
                    session.beginTransaction();
                    session.update(actionsToEdit.get(i));
                    session.getTransaction().commit();
                }catch (Exception e){
                    session.close();
                }
            }
        }
        session.close();
    }

    /*
    This method gets the action object by its name
     */
    @Override
    public Action getActionByName(String name) {
        //Opens session for operations on database
        Session session = openSession();
        Query q = session.createQuery("SELECT _action FROM Action _action WHERE actionLeap='" + name + "'");
        List<Action> actions = q.list();
        session.close();
        if(actions.size() >= 1) {
            return actions.get(0);
        }else {
            return null;
        }
    }

    /*
    This method opens the session for database management
     */
    private Session openSession(){
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory.openSession();
    }

    /*
    This method saves data to the session
     */
    private void saveToSession(List actions, HttpServletRequest request){
        ArrayList<Action> actionNormals = new ArrayList<>();
        for(Object action : actions){
            Action actionNormal = (Action) action;
            actionNormals.add(actionNormal);
        }

        HttpSession session = request.getSession();
        Gson gson = new GsonBuilder().create();
        String actionsToJson = gson.toJson(actionNormals);
        session.setAttribute("actions", actionsToJson);
    }

    /*
    This method sends data to the Arduino
     */
    @Override
    public void sendData(String[] actions, String[] values, HttpServletRequest request) {
        String actionsJson = (String) request.getSession().getAttribute("actions");
        Gson gson = new Gson();
        ArrayList<Action> action = gson.fromJson(actionsJson, new TypeToken<List<Action>>() {}.getType());
        ArduinoController arduinoController = new ArduinoController(action);

        //Check if correct data was received
        int[] arduinoActions = arduinoController.getNormalActions(actions);
        boolean flag = arduinoController.checkActions(arduinoActions);

        try{
            for (String value : values) {
                Double num = Double.parseDouble(value);
            }
        }catch (NumberFormatException e){
            flag = false;
        }

        //If everything's alright, app should keep going
        if(flag) {
            int[] vals = arduinoController.processDataForArduino(actions, values);
            gson = new GsonBuilder().create();
            for (int i = 0; i < vals.length; i++) {
                String sendData = gson.toJson(new ArduinoData(arduinoActions[i], vals[i]));
                arduinoController.sendData(sendData);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Action " + actions[i] + " do " + vals[i] + " actual " + values[i]);
            }
            System.out.println("=========================");
        }
    }
}
