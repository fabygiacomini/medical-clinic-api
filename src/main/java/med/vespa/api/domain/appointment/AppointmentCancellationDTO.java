package med.vespa.api.domain.appointment;

import jakarta.validation.constraints.NotNull;

public record AppointmentCancellationDTO(
        @NotNull
        Long id,

        @NotNull
        CancellationReason reason
) {
}
