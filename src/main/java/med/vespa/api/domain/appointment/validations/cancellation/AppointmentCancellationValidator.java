package med.vespa.api.domain.appointment.validations.cancellation;

import med.vespa.api.domain.appointment.AppointmentCancellationDTO;

public interface AppointmentCancellationValidator {

    void validate(AppointmentCancellationDTO data);

}
