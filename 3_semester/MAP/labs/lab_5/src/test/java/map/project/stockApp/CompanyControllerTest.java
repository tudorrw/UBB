package map.project.stockApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import map.project.stockApp.model.Company;
import map.project.stockApp.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CompanyService companyService;
    @Test
    void shouldExpectOk() throws Exception {
        this.mockMvc.perform(get("/companies")).andDo(print()).andExpect(status().isOk());
    }
    @Test
    void addNewCompany() throws Exception {
        Company company = new Company();
        company.setName("Microsoft");
        company.setCapitalization(100);
        company.setNumber_shares(3);
        company.setNumber_shares_outstanding(3);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(company);
        this.mockMvc.perform(post("/companies/addTest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/companies"));
//                .andExpect(flash().attribute("message", "The patient has been saved successfully."));
    }

    @Test
    void getCompanyById() throws Exception {
        // Assuming there is a company with id 1 in your database

        Company company = new Company(5, "Microsoft", 100, 3, 3);

        given(companyService.getCompanyByName(company.getName())).willReturn(Optional.of(company));
        System.out.println(companyService.getCompanyByName(company.getName()));
        this.mockMvc.perform(get("/companies/getByName/" + company.getName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(company.getName()));

    }
}
