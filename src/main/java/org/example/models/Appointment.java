package org.example.models;

public class Appointment {
    private int id;
    private int doctorId;
    private int patientId;
    private String appointmentDate;
    private String status;
    private String createdAt;

    public Appointment(int id, int doctorId, int patientId, String appointmentDate, String status, String createdAt) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Appointment{id=" + id + ", doctorId=" + doctorId + ", patientId=" + patientId +
                ", date=" + appointmentDate + ", status=" + status + "}";
    }
}