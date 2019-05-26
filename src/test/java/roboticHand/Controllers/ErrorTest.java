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
@WebMvcTest(Error.class)
public class ErrorTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void redirectError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/error//"))
                .andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/pages/error.jsp"));
    }
}