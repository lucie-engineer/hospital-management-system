package org.example.dao;

import org.example.db.Databaseconnection;
import org.example.models.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // ADD a new appointment
    public void addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (doctor_id, patient_id, appointment_date, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = Databaseconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, appointment.getDoctorId());
            stmt.setInt(2, appointment.getPatientId());
            stmt.setString(3, appointment.getAppointmentDate());
            stmt.setString(4, appointment.getStatus());
            stmt.executeUpdate();
            System.out.println("Appointment added successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding appointment: " + e.getMessage());
        }
    }

    // GET all appointments
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments";

        try (Connection conn = Databaseconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                appointments.add(new Appointment(
                        rs.getInt("id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("patient_id"),
                        rs.getString("appointment_date"),
                        rs.getString("status"),
                        rs.getString("created_at")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching appointments: " + e.getMessage());
        }
        return appointments;
    }

    // GET one appointment by ID
    public Appointment getAppointmentById(int id) {
        String sql = "SELECT * FROM appointments WHERE id = ?";

        try (Connection conn = Databaseconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Appointment(
                        rs.getInt("id"),
                        rs.getInt("doctor_id"),
                        rs.getInt("patient_id"),
                        rs.getString("appointment_date"),
                        rs.getString("status"),
                        rs.getString("created_at")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error finding appointment: " + e.getMessage());
        }
        return null;
    }

    // UPDATE an appointment
    public void updateAppointment(Appointment appointment) {
        String sql = "UPDATE appointments SET doctor_id=?, patient_id=?, appointment_date=?, status=? WHERE id=?";

        try (Connection conn = Databaseconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, appointment.getDoctorId());
            stmt.setInt(2, appointment.getPatientId());
            stmt.setString(3, appointment.getAppointmentDate());
            stmt.setString(4, appointment.getStatus());
            stmt.setInt(5, appointment.getId());
            stmt.executeUpdate();
            System.out.println("Appointment updated successfully!");

        } catch (SQLException e) {
            System.out.println("Error updating appointment: " + e.getMessage());
        }
    }

    // UPDATE appointment status only
    public void updateAppointmentStatus(int id, String status) {
        String sql = "UPDATE appointments SET status=? WHERE id=?";

        try (Connection conn = Databaseconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Appointment status updated successfully!");

        } catch (SQLException e) {
            System.out.println("Error updating status: " + e.getMessage());
        }
    }

    // DELETE an appointment
    public void deleteAppointment(int id) {
        String sql = "DELETE FROM appointments WHERE id = ?";

        try (Connection conn = Databaseconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Appointment deleted successfully!");

        } catch (SQLException e) {
            System.out.println("Error deleting appointment: " + e.getMessage());
        }
    }

    // GET total appointments per doctor
    public void getTotalAppointmentsPerDoctor() {
        String sql = "SELECT doctor_id, COUNT(*) AS total FROM appointments GROUP BY doctor_id";

        try (Connection conn = Databaseconnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Total appointments per doctor:");
            while (rs.next()) {
                System.out.println("Doctor ID: " + rs.getInt("doctor_id") +
                        " | Total: " + rs.getInt("total"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching appointments: " + e.getMessage());
        }
    }
}
