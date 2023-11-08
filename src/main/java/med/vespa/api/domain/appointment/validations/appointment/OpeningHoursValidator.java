package med.vespa.api.domain.appointment.validations.appointment;

import jakarta.validation.ValidationException;
import med.vespa.api.domain.appointment.AppointmentScheduleDTO;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class OpeningHoursValidator implements AppointmentScheduleValidator {

    private final int OPENING_HOUR = 7;
    private final int CLOSING_HOUR = 18;

    public void validate(AppointmentScheduleDTO data) {
        var appointmentDate =  data.date();
        var sunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);

        var beforeOpeningHours = appointmentDate.getHour() < this.OPENING_HOUR;
        var afterClosingHour = appointmentDate.getHour() > this.CLOSING_HOUR;

        if (sunday || beforeOpeningHours || afterClosingHour) {
            throw new ValidationException("Appointment outside clinic opening hours.");
        }
    }
}
