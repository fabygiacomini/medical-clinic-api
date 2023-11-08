package med.vespa.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.vespa.api.domain.appointment.AppointmentScheduleDTO;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AdvanceHourValidator implements AppointmentScheduleValidator {

    public void validate(AppointmentScheduleDTO data) {
        var appointmentDate = data.date();
        var now = LocalDateTime.now();

        var diffInMinutes = Duration.between(now, appointmentDate).toMinutes();

        if (diffInMinutes < 30) {
            throw new ValidationException("Appointment must be scheduled at least 30 minutes in advance.");
        }
    }
}
