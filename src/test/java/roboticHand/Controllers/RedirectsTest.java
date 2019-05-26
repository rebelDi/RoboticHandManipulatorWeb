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

@RunWith(SpringRunner.class)
@WebMvcTest(Redirects.class)
public class RedirectsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void index() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/"))
                .andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/pages/index.jsp"));
    }

    @Test
    public void redirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/redirect/"))
                .andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/pages/index.jsp"));
    }
}