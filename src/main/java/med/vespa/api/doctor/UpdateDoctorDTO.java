package med.vespa.api.doctor;

import jakarta.validation.constraints.NotNull;
import med.vespa.api.address.AddressDTO;

public record UpdateDoctorDTO(
        @NotNull
        Long id,
        String name,
        String phone,
        AddressDTO address
) {
}
