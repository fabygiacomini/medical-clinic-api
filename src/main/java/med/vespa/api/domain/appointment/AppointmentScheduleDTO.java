package med.vespa.api.domain.appointment;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.vespa.api.domain.doctor.Specialty;

import java.time.LocalDateTime;

public record AppointmentScheduleDTO(
        @JsonAlias("doctor_id")
        Long doctorId,

        @NotNull
        @JsonAlias("patient_id")
        Long patientId,

        Specialty specialty,

        @NotNull
        @Future
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime date
) {
}
