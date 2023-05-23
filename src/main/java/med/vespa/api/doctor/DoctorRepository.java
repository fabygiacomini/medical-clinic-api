package med.vespa.api.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // following this name pattern, dynamically spring will mount the correct query
    // in this case, find all where field "active" is true
    Page<Doctor> findAllByActiveTrue(Pageable pageable);
}
