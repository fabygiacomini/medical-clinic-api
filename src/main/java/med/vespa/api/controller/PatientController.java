package med.vespa.api.controller;

import jakarta.validation.Valid;
import med.vespa.api.domain.patient.CreatePatientDTO;
import med.vespa.api.domain.patient.Patient;
import med.vespa.api.domain.patient.PatientDetailsDTO;
import med.vespa.api.domain.patient.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private PatientRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid CreatePatientDTO data, UriComponentsBuilder uriBuilder) {
        var patient = new Patient(data);
        repository.save(patient);

        var uri = uriBuilder.path("/patient/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(new PatientDetailsDTO(patient));
    }
}
