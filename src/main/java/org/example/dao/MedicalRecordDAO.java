package org.example.dao;

import org.example.db.Databaseconnection;
import org.example.models.MedicalRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecordDAO {

    // ADD a new medical record
    public void addMedicalRecord(MedicalRecord record) {
        String sql = "INSERT INTO medical_records (patient_id, diagnosis, treatment, doctor_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = Databaseconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, record.getPatientId());
            stmt.setString(2, record.getDiagnosis());
            stmt.setString(3, record.getTreatment());
            stmt.setInt(4, record.getDoctorId());
            stmt.executeUpdate();
            System.out.println("Medical record added successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding medical record: " + e.getMessage());
        }
    }

    // GET all medical records
    public List<MedicalRecord> getAllMedicalRecords() {
        List<MedicalRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM medical_records";

        try (Connection conn = Databaseconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                records.add(new MedicalRecord(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getInt("doctor_id"),
                        rs.getString("created_at")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching medical records: " + e.getMessage());
        }
        return records;
    }

    // GET one medical record by ID
    public MedicalRecord getMedicalRecordById(int id) {
        String sql = "SELECT * FROM medical_records WHERE id = ?";

        try (Connection conn = Databaseconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new MedicalRecord(
                        rs.getInt("id"),
                        rs.getInt("patient_id"),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getInt("doctor_id"),
                        rs.getString("created_at")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error finding medical record: " + e.getMessage());
        }
        return null;
    }

    // UPDATE a medical record
    public void updateMedicalRecord(MedicalRecord record) {
        String sql = "UPDATE medical_records SET patient_id=?, diagnosis=?, treatment=?, doctor_id=? WHERE id=?";

        try (Connection conn = Databaseconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, record.getPatientId());
            stmt.setString(2, record.getDiagnosis());
            stmt.setString(3, record.getTreatment());
            stmt.setInt(4, record.getDoctorId());
            stmt.setInt(5, record.getId());
            stmt.executeUpdate();
            System.out.println("Medical record updated successfully!");

        } catch (SQLException e) {
            System.out.println("Error updating medical record: " + e.getMessage());
        }
    }

    // DELETE a medical record
    public void deleteMedicalRecord(int id) {
        String sql = "DELETE FROM medical_records WHERE id = ?";

        try (Connection conn = Databaseconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Medical record deleted successfully!");

        } catch (SQLException e) {
            System.out.println("Error deleting medical record: " + e.getMessage());
        }
    }

    // GET patients diagnosed more than once
    public void getPatientsDiagnosedMoreThanOnce() {
        String sql = "SELECT patient_id, COUNT(*) AS total_diagnoses " +
                "FROM medical_records " +
                "GROUP BY patient_id " +
                "HAVING COUNT(*) > 1";

        try (Connection conn = Databaseconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Patients diagnosed more than once:");
            while (rs.next()) {
                System.out.println("Patient ID: " + rs.getInt("patient_id") +
                        " | Total Diagnoses: " + rs.getInt("total_diagnoses"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching data: " + e.getMessage());
        }
    }
}
