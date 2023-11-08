package med.vespa.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.vespa.api.domain.appointment.AppointmentScheduleDTO;
import med.vespa.api.domain.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveDoctorValidator implements AppointmentScheduleValidator {

    @Autowired
    private DoctorRepository repository;

    public void validate(AppointmentScheduleDTO data) {
        if (data.doctorId() == null) {
            return;
        }

        var isDoctorActive = repository.findActiveById(data.doctorId());
        if (!isDoctorActive) {
            throw  new ValidationException("Appointment cannot be scheduled with an inactive doctor.");
        }
    }
}
