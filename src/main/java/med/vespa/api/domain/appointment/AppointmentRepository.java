package med.vespa.api.domain.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDoctorIdAndDate(Long doctorId, LocalDateTime date);

    boolean existsByPatientIdAndDateBetween(Long patientId, LocalDateTime firstHour, LocalDateTime lastHour);
}
