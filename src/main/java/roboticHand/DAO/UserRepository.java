package roboticHand.DAO;

import org.springframework.stereotype.Repository;
import roboticHand.Model.User;

import javax.servlet.http.HttpServletRequest;

@Repository
public interface UserRepository{
    boolean login(String login, String password);
    boolean signUp(User user);
    String getRights(String login);
    User getUserInfo(String login);
    boolean checkAnswer(String answer, String oldLogin);
    boolean checkPassword(String password, String oldLogin);
    String edit(String oldLogin, User user);
    void editRights(User user);
    void getEveryoneExceptSuperAdmin(HttpServletRequest request);
    void getAllUsers(HttpServletRequest request);
    void getUsersInWaitingList(HttpServletRequest request);
}