package med.vespa.api.controller;

import med.vespa.api.domain.appointment.AppointmentDetailsDTO;
import med.vespa.api.domain.appointment.AppointmentScheduleDTO;
import med.vespa.api.domain.appointment.AppointmentScheduleService;
import med.vespa.api.domain.doctor.Specialty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private JacksonTester<AppointmentScheduleDTO> appointmentScheduleDataJson;

    @Autowired
    private JacksonTester<AppointmentDetailsDTO> appointmentDetailsDataJson;

    @MockBean
    private AppointmentScheduleService appointmentScheduleService;

    @Test
    @DisplayName("should return http status 400 when use invalid info")
    @WithMockUser
    void scheduleCase1() throws Exception {
        var response = mock.perform(post("/appointment"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("should return http status 200 when use valid info")
    @WithMockUser
    void scheduleCase2() throws Exception {
        var date = LocalDateTime.now().plusHours(1);
        var specialty = Specialty.GYNECOLOGY;

        var appointmentDetails = new AppointmentDetailsDTO(null, 2l, 5l, date);
        when(appointmentScheduleService.schedule(any())).thenReturn(appointmentDetails);

        var response = mock
                .perform(
                        post("/appointment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(appointmentScheduleDataJson.write(
                                        new AppointmentScheduleDTO(2l, 5l, specialty, date)
                                ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = appointmentDetailsDataJson.write(
                appointmentDetails
        ).getJson();
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}
