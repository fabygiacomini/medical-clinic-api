package med.vespa.api.domain.appointment.validations.appointment;

import jakarta.validation.ValidationException;
import med.vespa.api.domain.appointment.AppointmentScheduleDTO;
import med.vespa.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivePatientValidator implements AppointmentScheduleValidator {

    @Autowired
    private PatientRepository repository;

    public void validate(AppointmentScheduleDTO data) {
        var activePatient = repository.findActiveById(data.patientId());

        if (!activePatient) {
            throw new ValidationException("appointment cannot be scheduled for an inactive patient.");
        }
    }
}
