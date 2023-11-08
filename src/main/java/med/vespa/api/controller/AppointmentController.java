package med.vespa.api.controller;

import jakarta.validation.Valid;
import med.vespa.api.domain.appointment.AppointmentCancellationDTO;
import med.vespa.api.domain.appointment.AppointmentDetailsDTO;
import med.vespa.api.domain.appointment.AppointmentScheduleDTO;
import med.vespa.api.domain.appointment.AppointmentScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentScheduleService service;

    @PostMapping
    @Transactional
    public ResponseEntity schedule(@RequestBody @Valid AppointmentScheduleDTO data) {
        var dto = service.schedule(data);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancel(@RequestBody @Valid AppointmentCancellationDTO data) {
        service.cancel(data);
        return ResponseEntity.noContent().build();
    }
}
