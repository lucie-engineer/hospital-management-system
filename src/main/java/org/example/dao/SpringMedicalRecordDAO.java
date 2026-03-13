package org.example.dao;

import org.example.models.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SpringMedicalRecordDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper
    private final RowMapper<MedicalRecord> medicalRecordMapper = (rs, rowNum) -> new MedicalRecord(
            rs.getInt("id"),
            rs.getInt("patient_id"),
            rs.getString("diagnosis"),
            rs.getString("treatment"),
            rs.getInt("doctor_id"),
            rs.getString("created_at")
    );

    // ADD medical record
    @Transactional
    public void addMedicalRecord(MedicalRecord record) {
        String sql = "INSERT INTO medical_records (patient_id, diagnosis, treatment, doctor_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                record.getPatientId(),
                record.getDiagnosis(),
                record.getTreatment(),
                record.getDoctorId());
        System.out.println("Medical record added successfully!");
    }

    // GET all medical records
    public List<MedicalRecord> getAllMedicalRecords() {
        String sql = "SELECT * FROM medical_records";
        return jdbcTemplate.query(sql, medicalRecordMapper);
    }

    // GET medical record by ID
    public MedicalRecord getMedicalRecordById(int id) {
        String sql = "SELECT * FROM medical_records WHERE id = ?";
        List<MedicalRecord> records = jdbcTemplate.query(sql, medicalRecordMapper, id);
        return records.isEmpty() ? null : records.get(0);
    }

    // UPDATE medical record
    @Transactional
    public void updateMedicalRecord(MedicalRecord record) {
        String sql = "UPDATE medical_records SET patient_id=?, diagnosis=?, treatment=?, doctor_id=? WHERE id=?";
        jdbcTemplate.update(sql,
                record.getPatientId(),
                record.getDiagnosis(),
                record.getTreatment(),
                record.getDoctorId(),
                record.getId());
        System.out.println("Medical record updated successfully!");
    }

    // DELETE medical record
    @Transactional
    public void deleteMedicalRecord(int id) {
        String sql = "DELETE FROM medical_records WHERE id = ?";
        jdbcTemplate.update(sql, id);
        System.out.println("Medical record deleted successfully!");
    }

    // GET patients diagnosed more than once
    public void getPatientsDiagnosedMoreThanOnce() {
        String sql = "SELECT patient_id, COUNT(*) AS total_diagnoses " +
                "FROM medical_records " +
                "GROUP BY patient_id " +
                "HAVING COUNT(*) > 1";
        List<String> results = jdbcTemplate.query(sql,
                (rs, rowNum) -> "Patient ID: " + rs.getInt("patient_id") +
                        " | Total Diagnoses: " + rs.getInt("total_diagnoses"));
        System.out.println("Patients diagnosed more than once:");
        results.forEach(System.out::println);
    }
}