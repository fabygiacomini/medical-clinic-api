package med.vespa.api.controller;

import med.vespa.api.domain.address.Address;
import med.vespa.api.domain.address.AddressDTO;
import med.vespa.api.domain.doctor.*;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class DoctorControllerTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private JacksonTester<CreateDoctorDTO> createDoctorJson;

    @Autowired
    private JacksonTester<DoctorDetailsDTO> doctorDetailsJson;

    @MockBean
    private DoctorRepository doctorRepository;

    @Test
    @DisplayName("should return http status code 400 when use invalid info")
    @WithMockUser
    void createDoctorCase1() throws Exception {
        var response = mock.perform(post("/doctors"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("should return http status code 200 when use valid info")
    @WithMockUser
    void createCase2() throws Exception {
        var createDoctorData = new CreateDoctorDTO(
                "Doctor",
                "doctor@email.com",
                "12999999999",
                "123456",
                Specialty.CARDIOLOGY,
                addressData()
        );

        when(doctorRepository.save(any())).thenReturn(new Doctor(createDoctorData));

        var response = mock
                .perform(
                        post("/doctors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(createDoctorJson.write(createDoctorData).getJson())
                )
                .andReturn().getResponse();

        var detailsData = new DoctorDetailsDTO(
                null,
                createDoctorData.name(),
                createDoctorData.email(),
                createDoctorData.crm(),
                createDoctorData.phone(),
                createDoctorData.specialty(),
                new Address(createDoctorData.address())
        );

        var expectedJson = doctorDetailsJson.write(detailsData).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    private AddressDTO addressData() {
        return new AddressDTO(
                "Rua XPTO",
                "Bairro",
                "00000000",
                "SP",
                "Marilia",
                null
        );
    }
}
