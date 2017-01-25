/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.hospital;

import com.main.model.Patient;
import java.util.Iterator;
import java.util.Queue;

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
    synchronized (patient) {
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
            System.out.println(String.format("All the patients before the appointment number : %d please move one backward this person needs immediate checkup", patient.getAppointmentNumber()));
            patient.setAppointmentNumber(1);
          }
        }

      }
    }
  }

  public static synchronized Patient sendNext() {
    Queue<Patient> patients = PatientsQueue.getQueue(Priority.EMERGENCY);

    if (!patients.isEmpty()) {
      Patient patient = patients.poll();
      System.out.println("Emergency Case!");
      return patient;

    } else {
      patients = PatientsQueue.getQueue(Priority.PATIENT);
      if (!patients.isEmpty()) {
        Patient patient = patients.poll();
        return patient;
      }

    }
    return null;

  }

}