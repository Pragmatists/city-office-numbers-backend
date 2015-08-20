package pl.pragmatists.cityofficenumbers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CityOfficesBackendApplication.class)
@WebAppConfiguration
public class CityOfficesBackendApplicationTests {

    public static final String OFFICE_ID = "5d2e698a-9c31-456b-8452-7ce33e7deb94";

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private FavoriteOffices favoriteOffices;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
	public void load_all_offices_for_new_user() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/users/new-user/offices");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[0].name", is("USC Andersa")))
                .andExpect(jsonPath("$[0].id", Is.is(OFFICE_ID)))
                .andExpect(jsonPath("$[0].favorite", is(false)))
        ;

    }

    @Test
    public void load_offices_for_existing_user() throws Exception {
        favoriteOffices.addFavorite(userId("old-user"), officeId(OFFICE_ID));

        MockHttpServletRequestBuilder requestBuilder = get("/users/old-user/offices");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[0].favorite", is(true)))
                .andExpect(jsonPath("$[1].favorite", is(false)))
        ;
    }

    @Test
    public void makes_an_office_a_favorite() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post("/users/a-user/offices/" + OFFICE_ID + "/favorite")
                .contentType(MediaType.APPLICATION_JSON).content("{ \"favorite\": true }");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());

        assertThat(favoriteOffices.isFavorite(userId("a-user"), officeId(OFFICE_ID))).isTrue();
    }

    @Test
    public void makes_an_office_not_a_favorite() throws Exception {
        favoriteOffices.addFavorite(userId("old-user"), officeId(OFFICE_ID));

        MockHttpServletRequestBuilder requestBuilder = post("/users/a-user/offices/" + OFFICE_ID + "/favorite")
                .contentType(MediaType.APPLICATION_JSON).content( "{ \"favorite\": false }");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());

        assertThat(favoriteOffices.isFavorite(userId("a-user"), officeId(OFFICE_ID))).isFalse();
    }

    private OfficeId officeId(String officeIdAsString) {
        return new OfficeId(officeIdAsString);
    }

    private UserId userId(String userIdAsString) {
        return new UserId(userIdAsString);
    }

}
