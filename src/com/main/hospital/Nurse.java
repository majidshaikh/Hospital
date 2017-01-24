/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.hospital;

import com.main.model.Patient;
import java.util.Iterator;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author webwerks
 */
public class Nurse {

  public void addPatient(Patient patient, Priority type) {

    if (Priority.PATIENT.equals(type) && PatientsQueue.getQueue(Priority.PATIENT).size() < PatientsQueue.APPOINTMENT_LIMIT) {
      patient.setAppointmentNumber(PatientsQueue.emergencyAppointmentNumber.incrementAndGet());
      PatientsQueue.getQueue(Priority.PATIENT).offer(patient);
    } else if (Priority.EMERGENCY.equals(type)) {
      patient.setAppointmentNumber(PatientsQueue.appointmentNumber.incrementAndGet());
      PatientsQueue.getQueue(Priority.EMERGENCY).offer(patient);
    }

  }

  public void changePatientPriority(Patient patient, Priority priority) {
    if (Priority.EMERGENCY.equals(priority)) {
      Iterator<Patient> patients = PatientsQueue.getQueue(Priority.EMERGENCY).iterator();
      while (patients.hasNext()) {
        Patient next = patients.next();
        if (next.getAppointmentNumber() > patient.getAppointmentNumber()) {
          next.setAppointmentNumber(next.getAppointmentNumber() - 1);
        } else if (next.getAppointmentNumber().equals(patient.getAppointmentNumber())) {
          patients.remove();
        }
      }
      PatientsQueue.getQueue(Priority.EMERGENCY).offer(patient);
      System.out.println(String.format("All the patients after the appointment number : %d please move one ahead this person is moved to emergency", patient.getAppointmentNumber()));
    } else {
      Iterator<Patient> patients = PatientsQueue.getQueue(Priority.PATIENT).iterator();
      while (patients.hasNext()) {
        Patient next = patients.next();
        if (next.getAppointmentNumber() < patient.getAppointmentNumber()) {
          next.setAppointmentNumber(next.getAppointmentNumber() + 1);
        } else if (next.getAppointmentNumber().equals(patient.getAppointmentNumber())) {
          patient.setAppointmentNumber(1);
        }
      }
      System.out.println(String.format("All the patients before the appointment number : %d please move one backward this person needs immediate checkup", patient.getAppointmentNumber()));
    }

  }

  public void checkAvailability() {
    while (sendNext()) {
    }
  }

  public synchronized boolean sendNext() {
    Queue<Patient> patients = PatientsQueue.getQueue(Priority.EMERGENCY);

    if (patients != null && patients.size() > 0) {
      try {
        Patient patient = patients.poll();
        System.out.println("Emergency Case!");
        System.out.println(String.format("Operating patient %s with medical problem %s ", patient.getName(), patient.getMedicalProblem()));
        Thread.sleep(5000); //this is just for demonstration purpose
        System.out.println("Next patient");
        return true;
      } catch (InterruptedException ex) {
        Logger.getLogger(Nurse.class.getName()).log(Level.SEVERE, null, ex);
      }
    } else {
      patients = PatientsQueue.getQueue(Priority.PATIENT);
      if (patients != null && patients.size() > 0) {
        try {
          System.out.println(String.format("Next patient %s please be available", patients.peek().getName()));
          Patient patient = patients.poll();
          System.out.println(String.format("Checking patient %s with medical problem %s ", patient.getName(), patient.getMedicalProblem()));

          Thread.sleep(5000); //this is just for demonstration purpose
          System.out.println("Next patient");
          return true;
        } catch (InterruptedException ex) {
          Logger.getLogger(Nurse.class.getName()).log(Level.SEVERE, null, ex);
        }

      }

    }
    return false;

  }

}
