package med.vespa.api.domain.doctor;

import jakarta.validation.constraints.NotNull;
import med.vespa.api.domain.address.AddressDTO;

public record UpdateDoctorDTO(
        @NotNull
        Long id,
        String name,
        String phone,
        AddressDTO address
) {
}
