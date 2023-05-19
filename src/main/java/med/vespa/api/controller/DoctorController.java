package med.vespa.api.controller;

import jakarta.validation.Valid;
import med.vespa.api.doctor.CreateDoctorDTO;
import med.vespa.api.doctor.Doctor;
import med.vespa.api.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired // dependency injection
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public void create(@RequestBody @Valid CreateDoctorDTO data) {
        repository.save(new Doctor(data));
    }
}
