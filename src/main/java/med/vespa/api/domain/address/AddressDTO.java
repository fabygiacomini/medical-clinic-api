package med.vespa.api.domain.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressDTO(
        @NotBlank
        String street,

        String number,
        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String zip,
        @NotBlank
        String state,
        @NotBlank
        String city,
        String complement
) {
}
