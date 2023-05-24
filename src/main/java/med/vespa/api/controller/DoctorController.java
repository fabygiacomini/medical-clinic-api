package med.vespa.api.controller;

import jakarta.validation.Valid;
import med.vespa.api.domain.doctor.*;
import med.vespa.api.domain.doctor.Doctor;
import med.vespa.api.domain.doctor.DoctorRepository;
import med.vespa.api.domain.doctor.ListDoctorsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired // dependency injection
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid CreateDoctorDTO data, UriComponentsBuilder uriBuilder) {
        var doctor = new Doctor(data);
        repository.save(doctor);

        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DoctorDetailsDTO(doctor));
    }

    @GetMapping
    public ResponseEntity list(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        return ResponseEntity.ok(BCrypt.gensalt("123456"));

//        var page = repository.findAllByActiveTrue(pageable).map(ListDoctorsDTO::new);
//        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity details(@PathVariable Long id) {
        var doctor = repository.getReferenceById(id);

        return ResponseEntity.ok(new DoctorDetailsDTO(doctor));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid UpdateDoctorDTO data) {
        var doctor = repository.getReferenceById(data.id());
        doctor.update(data); // automatically changed by JPA entity inside the transaction

        return ResponseEntity.ok(new DoctorDetailsDTO(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        var doctor = repository.getReferenceById(id);
        doctor.delete();

        return ResponseEntity.noContent().build();
    }
}
