package med.vespa.api.domain.appointment.validations;

import med.vespa.api.domain.appointment.AppointmentScheduleDTO;

public interface AppointmentScheduleValidator {
    public void validate(AppointmentScheduleDTO data);
}
