package com.my.project.petclinic.hospital.persistence.jdbcrepository.resultsetextractor;

import com.my.project.petclinic.hospital.domain.model.Doctor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListDoctorsResultSetExtractorTest {

    @InjectMocks
    private ListDoctorsResultSetExtractor subject;

    @Mock
    private ResultSet rs;

    @Test
    @DisplayName("should return list of doctors when map resultSet")
    void shouldReturnDoctorWhenExtractDataTest() throws SQLException {
        //given
        when(rs.next()).thenReturn(true).
                thenReturn(false);
        when(rs.getLong("d_id")).thenReturn(1L);
        when(rs.getString("d_name")).thenReturn("doctorName");
        when(rs.getString("d_pos")).thenReturn("dentist");
        when(rs.getString("d_sn")).thenReturn("surName");

        when(rs.getLong("p_id")).thenReturn(5L);
        when(rs.getString("p_name")).thenReturn("patientName");
        when(rs.getString("p_sn")).thenReturn("patientSurName");
        when(rs.getInt("p_age")).thenReturn(35);

        //when
        final List<Doctor> result = subject.extractData(rs);

        //then
        assertThat(result).isNotNull();
        assertThat(result.get(0)).extracting("id", "name", "surName", "position")
                .contains(1L, "doctorName", "surName", "dentist");
        assertThat(result.get(0).getPatients()).hasSizeGreaterThan(0);


    }

}
