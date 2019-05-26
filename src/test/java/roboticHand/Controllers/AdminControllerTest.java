package roboticHand.Controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllUsersWithUsualRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/users")
                .sessionAttr("rights", "U"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages//.jsp"));
    }

    @Test
    public void getAllUsersWithAdminRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/users")
                .sessionAttr("rights", "A"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/adminUsers.jsp"));
    }

    @Test
    public void getAllUsersWithSuperAdminRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/users")
                .sessionAttr("rights", "S"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/adminUsers.jsp"));
    }

    @Test
    public void changeUserRightsWithUsualRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/userRightsEdit")
                .sessionAttr("rights", "U")
                .param("user", "{\"login\": \"Dianaa\", \"rights\": \"B\"}"))
                .andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/pages//.jsp"));
    }

    @Test
    public void changeUserRightsWithAdminRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/userRightsEdit")
                .sessionAttr("rights", "A")
                .param("user", "{\"login\": \"Dianaa\", \"rights\": \"B\"}"))
                .andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/pages/adminUsers.jsp"));
    }

    @Test
    public void changeUserRightsWithSuperAdminRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/userRightsEdit")
                .sessionAttr("rights", "S")
                .param("user", "{\"login\": \"Dianaa\", \"rights\": \"B\"}"))
                .andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/pages/adminUsers.jsp"));
    }

    @Test
    public void showUsersInWaitingListWithUsualRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/waitingList")
                .sessionAttr("rights", "U"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages//.jsp"));
    }

    @Test
    public void showUsersInWaitingListWithAdminRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/waitingList")
                .sessionAttr("rights", "A"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/adminUsers.jsp"));
    }

    @Test
    public void showUsersInWaitingListWithSuperAdminRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/waitingList")
                .sessionAttr("rights", "S"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/adminUsers.jsp"));
    }

    @Test
    public void changeActionsWithUsualRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/actions")
                .sessionAttr("rights", "U"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages//.jsp"));
    }

    @Test
    public void changeActionsWithAdminRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/actions")
                .sessionAttr("rights", "A"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/adminImitator.jsp"));
    }

    @Test
    public void changeActionsWithSuperAdminRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/actions")
                .sessionAttr("rights", "S"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/adminImitator.jsp"));
    }

    @Test
    public void editActionWithUsualRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/actionEdit")
                .sessionAttr("rights", "U")
                .param("actions", "{\"actionsName\": [\"Thumb\"], \"servosNum\": [\"10\"]," +
                        "\"leapsMin\": [\"0\"], \"leapsMax\": [\"40\"]," +
                        "\"servosD\": [\"0\"], \"servosMin\": [\"0\"]," +
                        "\"servosMax\": [\"30\"], \"avails\": [\"1\"]}"))
                .andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/pages//.jsp"));
    }

    @Test
    public void editActionWithAdminRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/actionEdit")
                .sessionAttr("rights", "A")
                .param("actions", "{\"actionsName\": [\"Thumb\"], \"servosNum\": [\"10\"]," +
                        "\"leapsMin\": [\"0\"], \"leapsMax\": [\"40\"]," +
                        "\"servosD\": [\"0\"], \"servosMin\": [\"0\"]," +
                        "\"servosMax\": [\"30\"], \"avails\": [\"1\"]}"))
                .andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/pages//.jsp"));
    }

    @Test
    public void editActionWithSuperAdminRights() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/admin/actionEdit")
                .sessionAttr("rights", "S")
                .param("actions", "{\"actionsName\": [\"Thumb\"], \"servosNum\": [\"10\"]," +
                        "\"leapsMin\": [\"0\"], \"leapsMax\": [\"40\"]," +
                        "\"servosD\": [\"0\"], \"servosMin\": [\"0\"]," +
                        "\"servosMax\": [\"30\"], \"avails\": [\"1\"]}"))
                .andDo(print())
                .andExpect(forwardedUrl("/WEB-INF/pages/admin/actionEdit.jsp"));
    }
}