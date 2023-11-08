package med.vespa.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.vespa.api.domain.appointment.AppointmentRepository;
import med.vespa.api.domain.appointment.AppointmentScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientAlreadyWithAppointment implements AppointmentScheduleValidator {

    private final int OPENING_HOUR = 7;
    private final int CLOSING_HOUR = 18;

    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentScheduleDTO data) {
        var firstHour = data.date().withHour(this.OPENING_HOUR);
        var lastHour = data.date().withHour(this.CLOSING_HOUR);

        var patientAlreadyWithAppointmentInSameDay = repository.existsByPatientIdAndDateBetween(
                data.patientId(),
                firstHour,
                lastHour
        );

        if (patientAlreadyWithAppointmentInSameDay) {
            throw new ValidationException("The patient already has another appointment scheduled at the same time.");
        }
    }

}
