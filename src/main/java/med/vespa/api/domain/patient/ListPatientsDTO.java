package med.vespa.api.domain.patient;

public record ListPatientsDTO(
        Long id,
        String name,
        String email,
        String cpf
) {

    public ListPatientsDTO(Patient patient) {
        this(patient.getId(), patient.getName(), patient.getEmail(), patient.getCpf());
    }
}
