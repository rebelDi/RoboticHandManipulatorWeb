package roboticHand.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import roboticHand.DAO.QuestionRepository;
import roboticHand.Model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/*
Class for the realization of methods of QuestionRepository
 */
public class QuestionServiceImpl implements QuestionRepository {

    /*
    This method gets the list of all user's questions and saves it to the session
     */
    @Override
    public void getUserQuestions(String login, HttpServletRequest request){
        formList("SELECT _question FROM Question _question WHERE user = '" + login + "'", request);
    }

    /*
    This method gets the list of all users' questions and saves it to the session
     */
    @Override
    public void getAllQuestions(HttpServletRequest request){
        formList("SELECT _question FROM Question _question", request);
    }

    /*
    This method gets the list of all unanswered questions
     */
    @Override
    public void getQuestionsForAdmin(HttpServletRequest request){
        formList("SELECT  _question FROM Question _question WHERE status = 'N'", request);
    }

    /*
    This method forms the list by query and saves it to the session
     */
    private void formList(String query, HttpServletRequest request){
        //Opens session for operations on database
        Session session = openSession();
        Query q = session.createQuery(query);
        List<Question> dbQuestions = q.list();

        ArrayList<Question> questions = new ArrayList<>();
        for (Question dbQuestion : dbQuestions) {
            String answer;
            Question question = new Question();
            if (dbQuestion.getAnswer() == null) {
                answer = "No answer yet";
            } else {
                answer = dbQuestion.getAnswer();
            }
            question.setQuestion(dbQuestion.getQuestion());
            question.setAnswer(answer);
            question.setUser(dbQuestion.getUser());
            question.setStatus(dbQuestion.getStatus());
            questions.add(question);
        }
        session.close();
        //saves to the current session the list
        saveToSession(questions, request);
    }

    /*
    This method gets the question object from database by login and question
     */
    @Override
    public Question getQuestion(Question question){
        //Opens session for operations on database
        Session session = openSession();
        Query q = session.createQuery("SELECT  _question FROM Question _question WHERE user = '" +
                question.getUser() + "' AND question = '" + question.getQuestion() + "'");
        List<Question> dbQuestions = q.list();
        session.close();
        if(dbQuestions.size() != 0){
            return dbQuestions.get(0);
        }else{
            return null;
        }
    }

    /*
    This method updates the answer for question
     */
    @Override
    public void updateAnswer(Question question, String answer, String status) {
        //Opens session for operations on database
        Session session = openSession();
        try {
            Question tempQuestion = getQuestion(question);
            tempQuestion.setAnswer(answer);
            tempQuestion.setStatus(status);
            session.beginTransaction();
            session.update(tempQuestion);
            session.getTransaction().commit();
        }catch (Exception ignored){
            session.close();
        }
    }

    /*
    This method add new question to database
     */
    @Override
    public void createQuestion(String userLogin, String question) {
        Question newQuestion = new Question();
        newQuestion.setUser(userLogin);
        newQuestion.setQuestion(question);
        newQuestion.setStatus("N");

        Session session = openSession();
        session.beginTransaction();
        try {
            //saves to the current session the list
            session.save(newQuestion);
            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println(e);
            session.close();
        }
        session.close();
    }

    /*
    This method updates the data of question in database, when user changes the login
     */
    @Override
    public void updateUser(String oldLogin, String newLogin) {
        //Opens session for operations on database
        Session session = openSession();
        Query q = session.createQuery("SELECT _question FROM Question _question WHERE user = '" + oldLogin + "'");
        List<Question> dbQuestions = q.list();
        for (Question dbQuestion : dbQuestions) {
            dbQuestion.setUser(newLogin);
            session.beginTransaction();
            session.update(dbQuestion);
            session.getTransaction().commit();
        }
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
    private void saveToSession(List questions, HttpServletRequest request){
        ArrayList<Question> sessionQuestions = new ArrayList<>();
        for(Object q : questions){
            Question question = (Question) q;
            sessionQuestions.add(question);
        }

        HttpSession session = request.getSession();
        Gson gson = new GsonBuilder().create();
        String questionsToJson = gson.toJson(sessionQuestions);
        session.setAttribute("questions", questionsToJson);
    }
}
