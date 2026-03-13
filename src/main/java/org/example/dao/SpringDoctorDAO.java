package org.example.dao;

import org.example.models.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SpringDoctorDAO {

    @Autowired
   private JdbcTemplate jdbcTemplate;

    // RowMapper - converts database row to Doctor object
    private final RowMapper<Doctor> doctorMapper = (rs, rowNum) -> new Doctor(
            rs.getInt("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("specialty"),
            rs.getString("phone_number"),
            rs.getString("email"),
            rs.getString("created_at"));


    // ADD a new doctor
    @Transactional
    public void addDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctors (first_name, last_name, specialty, phone_number, email) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                doctor.getFirstname(),
                doctor.getLastname(),
                doctor.getSpecialty(),
                doctor.getPhoneNumber(),
                doctor.getEmail());
        System.out.println("Doctor added successfully!");
    }

    // GET all doctors
    public List<Doctor> getAllDoctors() {
        String sql = "SELECT * FROM doctors";
        return jdbcTemplate.query(sql, doctorMapper);

    }

    // GET one doctor by ID
    public Doctor getDoctorById(int id) {
        String sql = "SELECT * FROM doctors WHERE id = ?";

        List<Doctor> doctors = jdbcTemplate.query(sql, doctorMapper, id);
        return doctors.isEmpty() ? null : doctors.get(0);
    }

    // UPDATE a doctor
    @Transactional
    public void updateDoctor(Doctor doctor) {
        String sql = "UPDATE doctors SET first_name=?, last_name=?, specialty=?, phone_number=?, email=? WHERE id=?";

        jdbcTemplate.update(sql,
                doctor.getFirstname(),
                doctor.getLastname(),
                doctor.getSpecialty(),
                doctor.getPhoneNumber(),
                doctor.getEmail(),
                doctor.getId());
        System.out.println("Doctor updated successfully!");
    }

    // DELETE a doctor
    @Transactional
    public void deleteDoctor(int id) {
        String sql = "DELETE FROM doctors WHERE id = ?";

        jdbcTemplate.update(sql, id);
        System.out.println("Doctor deleted successfully!");
    }

    // GET all patients for a specific doctor
    public void getDoctorPatients(int doctorId) {
        String sql = "SELECT patients.first_name, patients.last_name FROM patients " +
                "JOIN appointments ON patients.id = appointments.patient_id " +
                "WHERE appointments.doctor_id = ?";

        List<String> patients = jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getString("first_name") + " " + rs.getString("last_name"),
                doctorId);

            System.out.println("Patients for doctor " + doctorId + ":");
        patients.forEach(p -> System.out.println("- " + p));
    }
}