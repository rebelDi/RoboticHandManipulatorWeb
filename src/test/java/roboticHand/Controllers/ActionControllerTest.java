package roboticHand.Controllers;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(ActionController.class)
public class ActionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void checkConnectionWhenMCTurnedOff() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/action/checkConnection"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void sendData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/action/sendData")
                .param("queryData", "{\"actions\": [\"Thumb\"], \"values\": [\"111\"]}")
                .param("ip", "no"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void controlPanelRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/action/panel"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/main.jsp"));
    }
}