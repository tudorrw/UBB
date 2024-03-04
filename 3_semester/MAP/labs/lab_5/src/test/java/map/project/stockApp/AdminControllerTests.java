package map.project.stockApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import map.project.stockApp.model.Admin;
import map.project.stockApp.controller.requests.AdminRequest;
import map.project.stockApp.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdminControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminService adminService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    @Rollback
    void createAdmin() throws Exception {
        // Create
        Admin admin = new Admin(1, "adminUser", "adminPass", "Admin");

        String requestBody = objectMapper.writeValueAsString(admin);
//        "{\"username\":\"adminUser\",\"password\":\"adminPass\",\"role\":\"Admin\"}"
        when(adminService.createAdmin(admin)).thenReturn(admin);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/admins/addTest")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(admin.getUsername()));

        // Read
//        Iterable<Admin> admins = adminService.showAllAdmins();
//        assertNotNull(admins);
//        assertTrue(admins.iterator().hasNext());
    }

    @Test
    @Transactional
    @Rollback
    void getAdmin() throws Exception {
        // Create
//        Admin admin = new Admin();
//        admin.setUsername("adminUser");
//        admin.setPassword("adminPass");
//        admin.setAdmin_role("ROLE_ADMIN");
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/admins/add")
//                        .content("{\"username\":\"adminUser\",\"password\":\"adminPass\",\"admin_role\":\"ROLE_ADMIN\"}")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/admins")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk()) // Expect OK status
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
//        // Read
//        Iterable<Admin> admins = adminService.showAllAdmins();
//        assertNotNull(admins);
//        assertTrue(admins.iterator().hasNext());
    }

    @Test
    @Transactional
    @Rollback
    void deleteAdmin() throws Exception {
        // Create an admin first
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/admins/add")
//                        .content("{\"username\":\"adminUser\",\"password\":\"adminPass\",\"admin_role\":\"ROLE_ADMIN\"}")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
//
//        // Delete
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/admins/delete/{id}", 1)) // Assuming ID is 1
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
//
//        // Verify Delete
//        Iterable<Admin> admins = adminService.showAllAdmins();
//
//        assertNotNull(admins);
//    }
    }

}