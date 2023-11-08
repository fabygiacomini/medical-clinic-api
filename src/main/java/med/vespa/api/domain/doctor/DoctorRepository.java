package med.vespa.api.domain.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // following this name pattern, dynamically spring will mount the correct query
    // in this case, find all where field "active" is true
    Page<Doctor> findAllByActiveTrue(Pageable pageable);

    // JPQL
    @Query("""
            select d from Doctor d
            where
            d.active = 1
            and
            d.specialty = :specialty
            and
            d.id not in (
                select a.doctor.id from Appointment a
                where
                a.date = :date
            and
                a.cancellationReason is null
            )
            order by rand()
            limit 1
            """)
    Doctor chooseRandomDoctorForSpecialtyAnDate(Specialty specialty, LocalDateTime date);

    @Query("""
            select d.active
            from Doctor d
            where
            d.id = :id
            """)
    Boolean findActiveById(Long id);
}
