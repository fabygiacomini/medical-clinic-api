package med.vespa.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.vespa.api.domain.appointment.AppointmentRepository;
import med.vespa.api.domain.appointment.AppointmentScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorAlreadyWithAppointmentValidator implements AppointmentScheduleValidator {

    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentScheduleDTO data) {
        var doctorAlreadyWithAppointment = repository.existsByDoctorIdAndDate(
                data.doctorId(),
                data.date()
        );

        if (doctorAlreadyWithAppointment) {
            throw new ValidationException("The doctor already has another appointment scheduled at the same time.");
        }
    }
}
