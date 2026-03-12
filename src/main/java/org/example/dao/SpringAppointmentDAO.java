package org.example.dao;

import org.example.db.Databaseconnection;
import org.example.models.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SpringAppointmentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper
    private final RowMapper<Appointment> appointmentMapper = (rs, rowNum) -> new Appointment(
            rs.getInt("id"),
            rs.getInt("doctor_id"),
            rs.getInt("patient_id"),
            rs.getString("appointment_date"),
            rs.getString("status"),
            rs.getString("created_at"));

    // ADD a new appointment
    public void addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (doctor_id, patient_id, appointment_date, status) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                appointment.getDoctorId(),
                appointment.getPatientId(),
                appointment.getAppointmentDate(),
                appointment.getStatus());
        System.out.println("Appointment added successfully!");
    }

    // GET all appointments
    public List<Appointment> getAllAppointments() {
        String sql = "SELECT * FROM appointments";

        return jdbcTemplate.query(sql, appointmentMapper);
    }

    // GET one appointment by ID
    public Appointment getAppointmentById(int id) {
        String sql = "SELECT * FROM appointments WHERE id = ?";

        List<Appointment> appointments = jdbcTemplate.query(sql, appointmentMapper, id);
        return appointments.isEmpty() ? null : appointments.get(0);
    }

    // UPDATE an appointment
    public void updateAppointment(Appointment appointment) {
        String sql = "UPDATE appointments SET doctor_id=?, patient_id=?, appointment_date=?, status=? WHERE id=?";

        jdbcTemplate.update(sql,
                appointment.getDoctorId(),
                appointment.getPatientId(),
                appointment.getAppointmentDate(),
                appointment.getStatus(),
                appointment.getId());
        System.out.println("Appointment updated successfully!");
    }

    // UPDATE appointment status only
    public void updateAppointmentStatus(int id, String status) {
        String sql = "UPDATE appointments SET status=? WHERE id=?";

        jdbcTemplate.update(sql, status, id);
        System.out.println("Appointment status updated successfully!");
    }

    // DELETE an appointment
    public void deleteAppointment(int id) {
        String sql = "DELETE FROM appointments WHERE id = ?";

        jdbcTemplate.update(sql, id);
        System.out.println("Appointment deleted successfully!");
    }

    // GET total appointments per doctor
    public void getTotalAppointmentsPerDoctor() {
        String sql = "SELECT doctor_id, COUNT(*) AS total FROM appointments GROUP BY doctor_id";

        List<String> results = jdbcTemplate.query(sql,
                (rs, rowNum) -> "Doctor ID: " + rs.getInt("doctor_id") +
                        " | Total: " + rs.getInt("total"));
        System.out.println("Total appointments per doctor:");
        results.forEach(System.out::println);
    }
}
