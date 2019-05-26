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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void signUpRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/signUpRedirect"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/signUp.jsp"));
    }

    @Test
    public void logInRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/logInRedirect"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/index.jsp"));
    }

    @Test
    public void userInfoRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/userInfoRedirect")
                .sessionAttr("login", "Dianaa"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/userInfo.jsp"));
    }
}