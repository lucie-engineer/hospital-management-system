package org.example.dao;

import org.example.db.Databaseconnection;
import org.example.models.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



@Repository
public class SpringPatientDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

  // RowMapper
    private final RowMapper<Patient> patientMapper = (rs, rowNum) -> new Patient(
            rs.getInt("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("date_of_birth"),
            rs.getString("gender"),
            rs.getString("phone_number"),
            rs.getString("email"),
            rs.getString("created_at")
    );


    // ADD a new patient
    public void addPatient(Patient patient) {
        String sql = "INSERT INTO patients (first_name, last_name, date_of_birth, gender, phone_number, email) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                patient.getFirstname(),
                patient.getLastname(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getPhoneNumber(),
                patient.getEmail());
        System.out.println("Patient added successfully!");
    }

    // GET all patients
    public List<Patient> getAllPatients() {
        String sql = "SELECT * FROM patients";

        return jdbcTemplate.query(sql, patientMapper);
    }

    // GET one patient by ID
    public Patient getPatientById(int id) {
        String sql = "SELECT * FROM patients WHERE id = ?";

        List<Patient> patients = jdbcTemplate.query(sql, patientMapper, id);
        return patients.isEmpty() ? null : patients.get(0);
    }

    // UPDATE a patient
    public void updatePatient(Patient patient) {
        String sql = "UPDATE patients SET first_name=?, last_name=?, date_of_birth=?, gender=?, phone_number=?, email=? WHERE id=?";

        jdbcTemplate.update(sql,
                patient.getFirstname(),
                patient.getLastname(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getPhoneNumber(),
                patient.getEmail(),
                patient.getId());
        System.out.println("Patient updated successfully!");
    }

    // DELETE a patient
    public void deletePatient(int id) {
        String sql = "DELETE FROM patients WHERE id = ?";

        jdbcTemplate.update(sql, id);
        System.out.println("Patient deleted successfully!");
    }

    // GET all medical records for a specific patient
    public void getPatientMedicalRecords(int patientId) {
        String sql = "SELECT * FROM medical_records WHERE patient_id = ?";

        List<String> records = jdbcTemplate.query(sql,
                (rs, rowNum) -> "Diagnosis: " + rs.getString("diagnosis") +
                        " | Treatment: " + rs.getString("treatment"),
                patientId);
        System.out.println("Medical records for patient " + patientId + ":");
        records.forEach(r -> System.out.println("- " + r));
    }
}