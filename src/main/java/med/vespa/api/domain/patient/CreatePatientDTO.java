package med.vespa.api.domain.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.vespa.api.domain.address.AddressDTO;

public record CreatePatientDTO(
        @NotBlank
        String name,

        @NotBlank
        String email,

        @NotBlank
        String phone,

        @NotBlank
        String cpf,

        @NotNull
        @Valid
        AddressDTO address
) {
}
