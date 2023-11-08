package med.vespa.api.domain.appointment.validations.cancellation;

import jakarta.validation.ValidationException;
import med.vespa.api.domain.appointment.AppointmentCancellationDTO;
import med.vespa.api.domain.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("AdvanceHourValidatorCancellation")
public class AdvanceHourValidator implements AppointmentCancellationValidator {

    @Autowired
    private AppointmentRepository repository;

    @Override
    public void validate(AppointmentCancellationDTO data) {
        var appointment = repository.getReferenceById(data.id());
        var now = LocalDateTime.now();
        var diffInHours = Duration.between(now, appointment.getDate()).toHours();

        if (diffInHours < 24) {
            throw new ValidationException("Appointment can be cancelled only with 2 hours of advance.");
        }
    }
}
