package med.vespa.api.domain.doctor;

import med.vespa.api.domain.address.AddressDTO;
import med.vespa.api.domain.appointment.Appointment;
import med.vespa.api.domain.patient.CreatePatientDTO;
import med.vespa.api.domain.patient.Patient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test") // using application-test.properties
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("should return null when no doctors are available in chosen date")
    void chooseRandomDoctorForSpecialtyAnDateCase1() {
        // Arrange
        var nextMondeyOn10am = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var doctor = createDoctor("Doctor", "doctor@email.com", "12345", Specialty.CARDIOLOGY);
        var patient = createPatient("Patient", "patient@email.com", "000000000000");
        createAppointment(doctor, patient, nextMondeyOn10am);

        // Act
        var doctorAvailable = repository.chooseRandomDoctorForSpecialtyAnDate(Specialty.CARDIOLOGY, nextMondeyOn10am);

        // Assert
        assertThat(doctorAvailable).isNull();
    }

    @Test
    @DisplayName("should return doctor when is available")
    void chooseRandomDoctorForSpecialtyAnDateCase2() {
        // Arrange
        var nextMondeyOn10am = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var doctor = createDoctor("Doctor", "doctor@email.com", "12345", Specialty.CARDIOLOGY);

        // Act
        var doctorAvailable = repository.chooseRandomDoctorForSpecialtyAnDate(Specialty.CARDIOLOGY, nextMondeyOn10am);

        // Assert
        assertThat(doctorAvailable).isEqualTo(doctor);
    }

    private void createAppointment(Doctor doctor, Patient patient, LocalDateTime date) {
        entityManager.persist(new Appointment(null, doctor, patient, date, null));
    }

    private Doctor createDoctor(String name, String email, String crm, Specialty specialty) {
        var doctor = new Doctor(createDoctorDTO(name, email, crm, specialty));
        entityManager.persist(doctor);
        return doctor;
    }

    private Patient createPatient(String name, String email, String cpf) {
        var patient = new Patient(createPatientDTO(name, email, cpf));
        entityManager.persist(patient);
        return patient;
    }

    private CreateDoctorDTO createDoctorDTO(String name, String email, String crm, Specialty specialty) {
        return new CreateDoctorDTO(
                name,
                email,
                "14999999999",
                crm,
                specialty,
                addressDTO()
        );
    }

    private CreatePatientDTO createPatientDTO(String name, String email, String cpf) {
        return new CreatePatientDTO(
                name,
                email,
                "11999999999",
                cpf,
                addressDTO()
        );
    }

    private AddressDTO addressDTO() {
        return new AddressDTO(
                "Rua XPTO",
                "Bairro",
                "000000000",
                "SP",
                "Marília",
                null
        );
    }
}