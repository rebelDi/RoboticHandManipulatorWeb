package roboticHand.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Query;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import roboticHand.DAO.UserRepository;
import roboticHand.Model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/*
Class for the realization of methods of UserRepository
 */
public class UserServiceImpl implements UserRepository {

    /*
    This method gets the user from database by login and password
     */
    @Override
    public boolean login(String login, String password) {
        //Opens session for operations on database
        Session session = openSession();
        Query q = session.createQuery("SELECT login FROM User WHERE login = '" + login +
                "' AND password = '" + password +"'");
        List users = q.list();
        session.close();
        return !users.isEmpty();
    }

    /*
    This method saves new user into the database
     */
    @Override
    public boolean signUp(User user) {
        //Opens session for operations on database
        Session session = openSession();
        session.beginTransaction();
        try {
            session.save(user);
            session.getTransaction().commit();
            session.close();
            return true;
        }catch (Exception e){
            session.close();
            return false;
        }
    }

    /*
    This method checks if there is a user with this login and password
     */
    @Override
    public boolean checkPassword(String password, String oldLogin) {
        //Opens session for operations on database
        Session session = openSession();
        Query q = session.createQuery("SELECT _user FROM User _user WHERE login = '" + oldLogin +
                "' AND password = '" + password +"'");
        List users = q.list();
        session.close();
        return !users.isEmpty();
    }

    /*
    This method checks if there is a user in database with this login and secret answer
     */
    @Override
    public boolean checkAnswer(String answer, String oldLogin) {
        //Opens session for operations on database
        Session session = openSession();
        Query q = session.createQuery("SELECT _user FROM User _user WHERE login = '" + oldLogin +
                "' AND secretAnswer = '" + answer +"'");
        List users = q.list();
        session.close();
        return !users.isEmpty();
    }

    /*
    This method edits the information if user in database
     */
    @Override
    public String edit(String oldLogin, User user) {
        if(!oldLogin.equals(user.getLogin())) {
            if (getUserInfo(user.getLogin()) != null) {
                return "Login is taken";
            }
        }else {
            //Opens session for operations on database
            Session session = openSession();
            try {
                User tempUser = getUserInfo(oldLogin);
                tempUser.setLogin(user.getLogin());
                tempUser.setPassword(user.getPassword());
                tempUser.setName(user.getName());
                tempUser.setSurname(user.getSurname());
                tempUser.setSecretQuestion(user.getSecretQuestion());
                session.beginTransaction();
                session.update(tempUser);
                session.getTransaction().commit();
                session.close();
            }catch (Exception e){
                session.close();
                return "Something went wrong";
            }
        }
        return "";
    }

    /*
    This method edits rights of user
     */
    @Override
    public void editRights(User user) {
        if(getUserInfo(user.getLogin()) != null){
            //Opens session for operations on database
            Session session = openSession();
            try {
                User tempUser = getUserInfo(user.getLogin());
                tempUser.setRights(user.getRights());
                session.beginTransaction();
                session.update(tempUser);
                session.getTransaction().commit();
                session.close();
            }catch (Exception ignored){
                session.close();
            }
        }
    }

    /*
    This method get all information about the user by login
     */
    @Override
    public User getUserInfo(String login) {
        //Opens session for operations on database
        Session session = openSession();
        Query q = session.createQuery("SELECT _user FROM User _user WHERE login = '" + login +"'");
        List<User> users = q.list();
        session.close();
        if(users.size() >= 1) {
            return users.get(0);
        }else {
            return null;
        }
    }

    /*
    This method gets the rights of the user by login
     */
    @Override
    public String getRights(String login){
        //Opens session for operations on database
        Session session = openSession();
        Query q = session.createQuery("SELECT rights FROM User WHERE login = '" + login + "'");
        List rights = q.list();
        session.close();
        return rights.get(0).toString();
    }

    /*
    This method gets the list of all users except superAdmin and saves it to the session
     */
    @Override
    public void getEveryoneExceptSuperAdmin(HttpServletRequest request) {
        //Opens session for operations on database
        Session session = openSession();
        Query q = session.createQuery("SELECT _user FROM User _user WHERE rights != 'S'");
        List users = q.list();
        //saves to the current session the list
        saveToSession(users, request);
        session.close();
    }

    /*
    This method gets the list of all users waiting to control the imitator and saves it to the session
     */
    @Override
    public void getUsersInWaitingList(HttpServletRequest request) {
        //Opens session for operations on database
        Session session = openSession();
        Query q = session.createQuery("SELECT _user FROM User _user WHERE rights = '0'");
        List users = q.list();
        //saves to the current session the list
        saveToSession(users, request);
        session.close();
    }

    /*
    This method gets the list of all usual users and saves it to the session
     */
    @Override
    public void getAllUsers(HttpServletRequest request) {
        //Opens session for operations on database
        Session session = openSession();
        Query q = session.createQuery("SELECT _user FROM User _user WHERE rights = 'U' OR rights = 'B' OR rights = '0'");
        List users = q.list();
        //saves to the current session the list
        saveToSession(users, request);
        session.close();
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
    private void saveToSession(List dbUsers, HttpServletRequest request){
        ArrayList<User> users = new ArrayList<>();
        for(Object user : dbUsers){
            User actionNormal = (User) user;
            users.add(actionNormal);
        }

        HttpSession session = request.getSession();
        Gson gson = new GsonBuilder().create();
        String usersToJson = gson.toJson(users);
        session.setAttribute("users", usersToJson);
    }
}
