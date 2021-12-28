package hospitalIT.apiIT;

import com.my.project.petclinic.hospital.HospitalApplication;
import com.my.project.petclinic.hospital.domain.model.Patient;
import com.my.project.petclinic.hospital.domain.service.PatientService;
import com.my.project.petclinic.hospital.persistence.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = HospitalApplication.class)
@ActiveProfiles("failsafe")
class PatientMockMvcIT {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private PatientRepository repository;

    @SpyBean
    private PatientService patientService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    }

    @Test
    @DisplayName("when send request to get all patients then should return json list and status ok")
    void getAllShouldReturnPatientListAsJsonTest() throws Exception {
        //given
        final Patient patient1 = Patient.builder().id(1L).name("First").surName("SurName1").age(51).doctors(Collections.emptyList()).build();
        final Patient patient2 = Patient.builder().id(2L).name("Second").surName("SurName2").age(55).doctors(Collections.emptyList()).build();

        final List<Patient> patients = List.of(patient1, patient2);
        when(repository.findAll()).thenReturn(patients);
        //then
        mockMvc.perform(get("/hospital/patients").
                contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(status().is(200))
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));

    }

    @Test
    @DisplayName("when send post request to save new patient than should return status Created and id")
    void shouldReturnStatusCreatedAndSavedIDWhenSaveNewPatientTest() throws Exception {
        //given
        final Patient saved = Patient.builder().id(25L).name("First").surName("SurName").age(51).build();
        when(repository.save(any(Patient.class))).thenReturn(saved);
        //then
        mockMvc.perform(post("/hospital/patients").
                contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"First\", \"surName\": \"SurName\", \"age\": \"51\"}"))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$", is(25)));


        verify(patientService, atLeastOnce()).save(any(Patient.class));

    }

    @Test
    @DisplayName("should update doctor")
    void shouldUpdateDoctorTest() throws Exception {
        //given
        final Patient updated = Patient.builder().id(25L).name("FirstUpdated").surName("SurName").age(51).doctors(Collections.emptyList()).build();
        when(repository.update(any(Patient.class))).thenReturn(updated);

        //then
        mockMvc.perform(put("/hospital/patients/{id}", "25").
                contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"FirstUpdated\", \"surName\": \"SurName\", \"age\": \"51\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(25)))
                .andExpect(jsonPath("$.name", is("FirstUpdated")))
                .andExpect(jsonPath("$.surName", is("SurName")))
                .andExpect(jsonPath("$.age", is(51)));


        verify(patientService, atLeastOnce()).update(any(Patient.class));
    }

}
