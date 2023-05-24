package med.vespa.api.domain.doctor;

public record ListDoctorsDTO(
        Long id,
        String name,
        String email,
        String crm,
        Specialty specialty
) {
    public ListDoctorsDTO(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getSpecialty());
    }
}
