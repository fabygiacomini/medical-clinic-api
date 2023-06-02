package med.vespa.api.domain.appointment;

import jakarta.validation.ValidationException;
import med.vespa.api.domain.doctor.Doctor;
import med.vespa.api.domain.doctor.DoctorRepository;
import med.vespa.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentScheduleService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public void schedule(AppointmentScheduleDTO data) {
        if (!patientRepository.existsById(data.patientId())) {
            throw new ValidationException("Patient id does not exists!");
        }

        if (data.doctorId() != null && !doctorRepository.existsById(data.doctorId())) {
            throw new ValidationException("Doctor id does not exists!");
        }

        var patient = patientRepository.getReferenceById(data.patientId());
        var doctor = chooseDoctor(data);
        var appointment = new Appointment(null, doctor, patient, data.date(), null);
        appointmentRepository.save(appointment);
    }

    private Doctor chooseDoctor(AppointmentScheduleDTO data) {
        if (data.doctorId() != null) {
            return doctorRepository.getReferenceById(data.doctorId());
        }

        if (data.specialty() == null) {
            throw new ValidationException("Specialty is required when doctor id has not been submitted");
        }

        return doctorRepository.chooseRandomDoctorForSpecialtyAnDate(data.specialty(), data.date());
    }

    public void cancel(AppointmentCancellationDTO data) {
        if (!appointmentRepository.existsById(data.id())) {
            throw new ValidationException("Appointment id does not exists!");
        }

        var appointment = appointmentRepository.getReferenceById(data.id());
        appointment.cancel(data.reason());
    }
}
