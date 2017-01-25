/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.hospital;

import com.main.model.Patient;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author webwerks
 */
public class Doctor implements Runnable {

  String name;

  public Doctor(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    while (!PatientsQueue.getQueue(Priority.PATIENT).isEmpty()) {
      operatePatient(getPatient());
    }
  }

  public Patient getPatient() {
    return Nurse.sendNext();
  }

  public void operatePatient(Patient patient) {
    try {
      System.out.println(String.format("Dr.%s is operating patient %s with medical problem %s ", name, patient.getName(), patient.getMedicalProblem()));
      Thread.sleep(5000); //this is just for demonstration purpose
      System.out.println(String.format("Dr.%s Calling next patient", name));
    } catch (InterruptedException ex) {
      Logger.getLogger(Doctor.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

}