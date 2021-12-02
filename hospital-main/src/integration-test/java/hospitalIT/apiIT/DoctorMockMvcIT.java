package hospitalIT.apiIT;

import com.my.project.petclinic.hospital.HospitalApplication;
import com.my.project.petclinic.hospital.api.config.WebMvcConfig;
import com.my.project.petclinic.hospital.api.controller.DoctorController;
import com.my.project.petclinic.hospital.domain.model.Doctor;
import com.my.project.petclinic.hospital.domain.service.DoctorService;
import com.my.project.petclinic.hospital.persistence.DoctorRepository;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = HospitalApplication.class)
public class DoctorMockMvcIT {

    @MockBean
    private DoctorRepository repository;

    @SpyBean
    private DoctorService doctorService;

    @Autowired
    @Qualifier("controllerOrikaMapper")
    MapperFacade mapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DoctorController(doctorService, mapper)).build();

    }

    @Test
    @DisplayName("when send request to get all doctors then should return json list and status ok")
    public void getAllShouldReturnDoctorListAsJsonTest() throws Exception {
        //given
        final Doctor doctor1 = Doctor.builder().id(1L).name("First").surName("SurName1").position("dentist").patients(Collections.emptyList()).build();
        final Doctor doctor2 = Doctor.builder().id(2L).name("Second").surName("SurName2").position("dentist").patients(Collections.emptyList()).build();
        final List<Doctor> doctors = List.of(doctor1, doctor2);
        when(repository.findAll()).thenReturn(doctors);
        //then
        mockMvc.perform(get("/hospital/doctors").
                contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().is(200))
                .andExpect(header().string("Content-Type", "application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));

    }

    @Test
    @DisplayName("when send post request to save new doctor than should return status Created and id")
    public void shouldReturnStatusCreatedAndSavedIDWhenSaveNewDoctorTest() throws Exception {
        //given
        final Doctor saved = Doctor.builder().id(55L).name("John").surName("GoodMan").position("terapist").build();
        when(repository.save(any(Doctor.class))).thenReturn(saved);
        //then
        mockMvc.perform(post("/hospital/doctors").
                contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\": \"John\", \"surName\": \"GoodMan\", \"position\": \"terapist\"}"))
                .andDo(print())
                .andExpect(status().is(201))
                .andReturn().getResponse().getContentAsString();


        verify(doctorService, atLeastOnce()).save(any(Doctor.class));

    }

    @Test
    @DisplayName("should update doctor")
    public void shouldUpdateDoctorTest() throws Exception {
        //given
        final Doctor updated = Doctor.builder().id(55L).name("JohnUpdated").surName("GoodMan").position("terapist").patients(Collections.emptyList()).build();
        when(repository.update(any(Doctor.class))).thenReturn(updated);

        //then
        mockMvc.perform(put("/hospital/doctors/{id}", "55").
                contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"JohnUpdated\", \"surName\": \"GoodMan\", \"position\": \"terapist\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(55)))
                .andExpect(jsonPath("$.name", is("JohnUpdated")))
                .andExpect(jsonPath("$.surName", is("GoodMan")))
                .andExpect(jsonPath("$.position", is("terapist")));


        verify(doctorService, atLeastOnce()).update(any(Doctor.class));
    }

}


