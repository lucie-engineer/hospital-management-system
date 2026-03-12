package org.example;

import org.example.dao.*;
import org.example.models.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    static DoctorDAO doctorDAO = new DoctorDAO();
    static SpringPatientDAO patientDAO = new SpringPatientDAO();
    static AppointmentDAO appointmentDAO = new AppointmentDAO();
    static MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String choice = "";

        while (!choice.equals("0")) {
            System.out.println("\n===== HOSPITAL MANAGEMENT SYSTEM =====");
            System.out.println("--- DOCTORS ---");
            System.out.println("1.  Add Doctor");
            System.out.println("2.  View All Doctors");
            System.out.println("3.  View Doctor by ID");
            System.out.println("4.  Update Doctor");
            System.out.println("5.  Delete Doctor");
            System.out.println("6.  View Doctor's Patients");
            System.out.println("--- PATIENTS ---");
            System.out.println("7.  Add Patient");
            System.out.println("8.  View All Patients");
            System.out.println("9.  View Patient by ID");
            System.out.println("10. Update Patient");
            System.out.println("11. Delete Patient");
            System.out.println("12. View Patient Medical Records");
            System.out.println("--- APPOINTMENTS ---");
            System.out.println("13. Add Appointment");
            System.out.println("14. View All Appointments");
            System.out.println("15. View Appointment by ID");
            System.out.println("16. Update Appointment");
            System.out.println("17. Update Appointment Status");
            System.out.println("18. Delete Appointment");
            System.out.println("19. View Total Appointments Per Doctor");
            System.out.println("--- MEDICAL RECORDS ---");
            System.out.println("20. Add Medical Record");
            System.out.println("21. View All Medical Records");
            System.out.println("22. View Medical Record by ID");
            System.out.println("23. Update Medical Record");
            System.out.println("24. Delete Medical Record");
            System.out.println("25. View Patients Diagnosed More Than Once");
            System.out.println("--- ");
            System.out.println("0.  Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addDoctor();
                case "2" -> viewAllDoctors();
                case "3" -> viewDoctorById();
                case "4" -> updateDoctor();
                case "5" -> deleteDoctor();
                case "6" -> viewDoctorPatients();
                case "7" -> addPatient();
                case "8" -> viewAllPatients();
                case "9" -> viewPatientById();
                case "10" -> updatePatient();
                case "11" -> deletePatient();
                case "12" -> viewPatientMedicalRecords();
                case "13" -> addAppointment();
                case "14" -> viewAllAppointments();
                case "15" -> viewAppointmentById();
                case "16" -> updateAppointment();
                case "17" -> updateAppointmentStatus();
                case "18" -> deleteAppointment();
                case "19" -> viewTotalAppointmentsPerDoctor();
                case "20" -> addMedicalRecord();
                case "21" -> viewAllMedicalRecords();
                case "22" -> viewMedicalRecordById();
                case "23" -> updateMedicalRecord();
                case "24" -> deleteMedicalRecord();
                case "25" -> viewPatientsDiagnosedMoreThanOnce();
                case "0" -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // ===== DOCTOR METHODS =====
    static void addDoctor() {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Specialty: ");
        String specialty = scanner.nextLine();
        System.out.print("Phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        doctorDAO.addDoctor(new Doctor(0, firstName, lastName, specialty, phone, email, null));
    }

    static void viewAllDoctors() {
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors found.");
        } else {
            doctors.forEach(System.out::println);
        }
    }

    static void viewDoctorById() {
        System.out.print("Enter doctor ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Doctor d = doctorDAO.getDoctorById(id);
        System.out.println(d != null ? d : "Doctor not found.");
    }

    static void updateDoctor() {
        System.out.print("Enter doctor ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("New first name: ");
        String firstName = scanner.nextLine();
        System.out.print("New last name: ");
        String lastName = scanner.nextLine();
        System.out.print("New specialty: ");
        String specialty = scanner.nextLine();
        System.out.print("New phone number: ");
        String phone = scanner.nextLine();
        System.out.print("New email: ");
        String email = scanner.nextLine();
        doctorDAO.updateDoctor(new Doctor(id, firstName, lastName, specialty, phone, email, null));
    }

    static void deleteDoctor() {
        System.out.print("Enter doctor ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        doctorDAO.deleteDoctor(id);
    }

    static void viewDoctorPatients() {
        System.out.print("Enter doctor ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        doctorDAO.getDoctorPatients(id);
    }

    // ===== PATIENT METHODS =====
    static void addPatient() {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Date of birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();
        System.out.print("Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        patientDAO.addPatient(new Patient(0, firstName, lastName, dob, gender, phone, email, null));
    }

    static void viewAllPatients() {
        List<Patient> patients = patientDAO.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
        } else {
            patients.forEach(System.out::println);
        }
    }

    static void viewPatientById() {
        System.out.print("Enter patient ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Patient p = patientDAO.getPatientById(id);
        System.out.println(p != null ? p : "Patient not found.");
    }

    static void updatePatient() {
        System.out.print("Enter patient ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("New first name: ");
        String firstName = scanner.nextLine();
        System.out.print("New last name: ");
        String lastName = scanner.nextLine();
        System.out.print("New date of birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine();
        System.out.print("New gender: ");
        String gender = scanner.nextLine();
        System.out.print("New phone number: ");
        String phone = scanner.nextLine();
        System.out.print("New email: ");
        String email = scanner.nextLine();
        patientDAO.updatePatient(new Patient(id, firstName, lastName, dob, gender, phone, email, null));
    }

    static void deletePatient() {
        System.out.print("Enter patient ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        patientDAO.deletePatient(id);
    }

    static void viewPatientMedicalRecords() {
        System.out.print("Enter patient ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        patientDAO.getPatientMedicalRecords(id);
    }

    // ===== APPOINTMENT METHODS =====
    static void addAppointment() {
        System.out.print("Doctor ID: ");
        int doctorId = Integer.parseInt(scanner.nextLine());
        System.out.print("Patient ID: ");
        int patientId = Integer.parseInt(scanner.nextLine());
        System.out.print("Appointment date (YYYY-MM-DD HH:MM:SS): ");
        String date = scanner.nextLine();
        System.out.print("Status (Scheduled/Completed/Canceled): ");
        String status = scanner.nextLine();
        appointmentDAO.addAppointment(new Appointment(0, doctorId, patientId, date, status, null));
    }

    static void viewAllAppointments() {
        List<Appointment> appointments = appointmentDAO.getAllAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments found.");
        } else {
            appointments.forEach(System.out::println);
        }
    }

    static void viewAppointmentById() {
        System.out.print("Enter appointment ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Appointment a = appointmentDAO.getAppointmentById(id);
        System.out.println(a != null ? a : "Appointment not found.");
    }

    static void updateAppointment() {
        System.out.print("Enter appointment ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("New doctor ID: ");
        int doctorId = Integer.parseInt(scanner.nextLine());
        System.out.print("New patient ID: ");
        int patientId = Integer.parseInt(scanner.nextLine());
        System.out.print("New date (YYYY-MM-DD HH:MM:SS): ");
        String date = scanner.nextLine();
        System.out.print("New status: ");
        String status = scanner.nextLine();
        appointmentDAO.updateAppointment(new Appointment(id, doctorId, patientId, date, status, null));
    }

    static void updateAppointmentStatus() {
        System.out.print("Enter appointment ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("New status (Scheduled/Completed/Canceled): ");
        String status = scanner.nextLine();
        appointmentDAO.updateAppointmentStatus(id, status);
    }

    static void deleteAppointment() {
        System.out.print("Enter appointment ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        appointmentDAO.deleteAppointment(id);
    }

    static void viewTotalAppointmentsPerDoctor() {
        appointmentDAO.getTotalAppointmentsPerDoctor();
    }

    // ===== MEDICAL RECORD METHODS =====
    static void addMedicalRecord() {
        System.out.print("Patient ID: ");
        int patientId = Integer.parseInt(scanner.nextLine());
        System.out.print("Diagnosis: ");
        String diagnosis = scanner.nextLine();
        System.out.print("Treatment: ");
        String treatment = scanner.nextLine();
        System.out.print("Doctor ID: ");
        int doctorId = Integer.parseInt(scanner.nextLine());
        medicalRecordDAO.addMedicalRecord(new MedicalRecord(0, patientId, diagnosis, treatment, doctorId, null));
    }

    static void viewAllMedicalRecords() {
        List<MedicalRecord> records = medicalRecordDAO.getAllMedicalRecords();
        if (records.isEmpty()) {
            System.out.println("No medical records found.");
        } else {
            records.forEach(System.out::println);
        }
    }

    static void viewMedicalRecordById() {
        System.out.print("Enter medical record ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        MedicalRecord r = medicalRecordDAO.getMedicalRecordById(id);
        System.out.println(r != null ? r : "Medical record not found.");
    }

    static void updateMedicalRecord() {
        System.out.print("Enter medical record ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("New patient ID: ");
        int patientId = Integer.parseInt(scanner.nextLine());
        System.out.print("New diagnosis: ");
        String diagnosis = scanner.nextLine();
        System.out.print("New treatment: ");
        String treatment = scanner.nextLine();
        System.out.print("New doctor ID: ");
        int doctorId = Integer.parseInt(scanner.nextLine());
        medicalRecordDAO.updateMedicalRecord(new MedicalRecord(id, patientId, diagnosis, treatment, doctorId, null));
    }

    static void deleteMedicalRecord() {
        System.out.print("Enter medical record ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        medicalRecordDAO.deleteMedicalRecord(id);
    }

    static void viewPatientsDiagnosedMoreThanOnce() {
        medicalRecordDAO.getPatientsDiagnosedMoreThanOnce();
    }
}