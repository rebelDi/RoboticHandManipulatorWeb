package roboticHand.Controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void questionRedirectWithUsualRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/question//")
                .sessionAttr("rights", "U"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/questionU.jsp"));
    }

    @Test
    public void questionRedirectWithAdminRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/question//")
                .sessionAttr("rights", "A"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/questionA.jsp"));
    }

    @Test
    public void questionRedirectWithSuperAdminRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/question//")
                .sessionAttr("rights", "S"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/questionA.jsp"));
    }

    @Test
    public void answerQuestion() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/question/answer")
                .param("userLogin", "Dianaa")
                .param("userQ", "Wow?")
                .param("answer", "Thank you for your message!"))
                .andDo(print())
                .andExpect(view().name("redirect:/question/"));
    }

    @Test
    public void askQuestion() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/question/ask")
                .param("newQuestion", "How to log off?")
                .sessionAttr("login", "Dianaa"))
                .andDo(print())
                .andExpect(view().name("redirect:/question/"));
    }
}