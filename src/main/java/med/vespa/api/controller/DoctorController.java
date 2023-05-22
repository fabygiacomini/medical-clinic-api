package med.vespa.api.controller;

import jakarta.validation.Valid;
import med.vespa.api.doctor.CreateDoctorDTO;
import med.vespa.api.doctor.Doctor;
import med.vespa.api.doctor.DoctorRepository;
import med.vespa.api.doctor.ListDoctorsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Page<ListDoctorsDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(ListDoctorsDTO::new);
    }
}
