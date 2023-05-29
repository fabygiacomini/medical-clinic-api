package med.vespa.api.controller;

import jakarta.validation.Valid;
import med.vespa.api.domain.patient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid CreatePatientDTO data, UriComponentsBuilder uriBuilder) {
        var patient = new Patient(data);
        repository.save(patient);

        var uri = uriBuilder.path("/patient/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(new PatientDetailsDTO(patient));
    }

    @GetMapping
    public ResponseEntity list(@PageableDefault(size = 10, sort = "name") Pageable pageable) {
        var page = repository.findAllByActiveTrue(pageable).map(ListPatientsDTO::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity details(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);

        return ResponseEntity.ok(new PatientDetailsDTO(patient));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid UpdatePatientDTO data) {
        var patient = repository.getReferenceById(data.id());
        patient.update(data);

        return ResponseEntity.ok(new PatientDetailsDTO(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        patient.delete();

        return ResponseEntity.noContent().build();
    }
}
