package roboticHand.DAO;

import org.springframework.stereotype.Repository;
import roboticHand.Model.Question;
import javax.servlet.http.HttpServletRequest;

@Repository
public interface QuestionRepository {
    void getUserQuestions(String login, HttpServletRequest request);
    void getAllQuestions(HttpServletRequest request);
    void getQuestionsForAdmin(HttpServletRequest request);
    Question getQuestion(Question question);
    void updateAnswer(Question question, String answer, String status);
    void createQuestion(String userLogin, String question);
    void updateUser(String oldLogin, String newDatum);
}