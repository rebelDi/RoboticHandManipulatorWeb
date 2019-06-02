package roboticHand.DAO;

import org.springframework.stereotype.Repository;
import roboticHand.Model.Action;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Repository
public interface ActionRepository {
    void getAllActions(HttpServletRequest request);
    void sendData(String[] actions, String[] values, HttpServletRequest request, String ip);
    void edit(ArrayList<Action> actionsToEdit);
    Action getActionByName(String name);
}