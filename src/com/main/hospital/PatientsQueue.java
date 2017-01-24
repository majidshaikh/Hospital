/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.hospital;

import com.main.exception.NotFoundException;
import com.main.model.Patient;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author webwerks
 */
public class PatientsQueue {

  public static final int APPOINTMENT_LIMIT = 50;
  public static AtomicInteger appointmentNumber = new AtomicInteger();
  public static AtomicInteger emergencyAppointmentNumber = new AtomicInteger();
  private static Queue<Patient> patients;
  private static Queue<Patient> emergencyPatients;

  private PatientsQueue() {
  }

  public static Queue<Patient> getQueue(Priority type) {

    if (patients == null) {
      synchronized (PatientsQueue.class) {
        if (patients == null) {
          patients = new PriorityQueue<Patient>();
        }
      }
    }

    if (Priority.PATIENT.equals(type)) {
      return patients;
    }

    if (emergencyPatients == null) {
      synchronized (PatientsQueue.class) {
        if (emergencyPatients == null) {
          emergencyPatients = new PriorityQueue<Patient>();
        }
      }
    }

    if (Priority.EMERGENCY.equals(type)) {
      return emergencyPatients;
    } else {
      throw new NotFoundException(String.format("No queue found of type %s", type));
    }

  }

}
