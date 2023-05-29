package med.vespa.api.domain.patient;

import med.vespa.api.domain.address.Address;

public record PatientDetailsDTO(
        Long id,
        String name,
        String email,
        String phone,
        String cpf,
        Address address
) {

    public PatientDetailsDTO(Patient patient) {
        this(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getPhone(),
                patient.getCpf(),
                patient.getAddress()
        );
    }
}
