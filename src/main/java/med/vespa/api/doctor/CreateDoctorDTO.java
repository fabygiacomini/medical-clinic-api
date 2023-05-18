package med.vespa.api.doctor;

import med.vespa.api.address.AddressDTO;

public record CreateDoctorDTO(
        String name,
        String email,
        String crm,
        Specialty specialty,
        AddressDTO address
) {
}
