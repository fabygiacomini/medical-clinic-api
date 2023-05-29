package med.vespa.api.domain.patient;

import jakarta.validation.constraints.NotNull;
import med.vespa.api.domain.address.AddressDTO;

public record UpdatePatientDTO(
        @NotNull
        Long id,
        String name,
        String phone,
        AddressDTO address
) {
}
