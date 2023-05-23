package med.vespa.api.controller;

import jakarta.validation.Valid;
import med.vespa.api.doctor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired // dependency injection
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public void register(@RequestBody @Valid CreateDoctorDTO data) {
        repository.save(new Doctor(data));
    }

    @GetMapping
    public Page<ListDoctorsDTO> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        return repository.findAllByActiveTrue(pageable).map(ListDoctorsDTO::new);
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid UpdateDoctorDTO data) {
        var doctor = repository.getReferenceById(data.id());
        doctor.update(data); // automatically changed by JPA entity inside the transaction
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        var doctor = repository.getReferenceById(id);
        doctor.delete();
    }
}
