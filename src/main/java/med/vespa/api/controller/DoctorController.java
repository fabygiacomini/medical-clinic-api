package med.vespa.api.controller;

import med.vespa.api.doctor.CreateDoctorDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @PostMapping
    public void create(@RequestBody CreateDoctorDTO data) {
        System.out.println(data);
    }
}
