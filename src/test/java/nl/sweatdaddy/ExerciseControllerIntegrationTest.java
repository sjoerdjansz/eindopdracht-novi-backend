package nl.sweatdaddy;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.sweatdaddy.exercise.dto.CreateExerciseRequestDto;
import nl.sweatdaddy.exercise.repository.ExerciseRepository;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
@SpringBootTest()
public class ExerciseControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    void getExerciseById_shouldReturnExercise_whenExerciseExistsInDatabase() throws Exception {
        mockMvc.perform(get("/exercises/{id}", 1L).accept(MediaType.APPLICATION_JSON)).andExpect(
                        status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Exercise found"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("bench press"))
                .andExpect(jsonPath("$.data.muscles").value("pectoralis major, triceps brachii, anterior deltoid"))
                .andExpect(jsonPath("$.data.movement").value("horizontal press"));
    }

    @Test
    void createExercise_shouldCreateAndReturnExercise() throws Exception {
        CreateExerciseRequestDto request = new CreateExerciseRequestDto();
        request.setName("Low Bar Squat");
        request.setMuscles("Quadriceps");
        request.setMovement("Knee dominant");

        mockMvc.perform(post("/exercises").contentType(MediaType.APPLICATION_JSON).accept(
                        MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Exercises added to library"))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.name").value("Low Bar Squat"))
                .andExpect(jsonPath("$.data.muscles").value("Quadriceps"))
                .andExpect(jsonPath("$.data.movement").value("Knee dominant"));
    }
}
