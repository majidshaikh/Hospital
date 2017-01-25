/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.hospital;

import com.main.model.Patient;

/**
 *
 * @author webwerks
 */
public class Hospital {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // TODO code application logic here
    Patient patient1 = new Patient("ajit", "cold");
    Patient patient2 = new Patient("crystal", "broken hand");
    Patient patient3 = new Patient("danial", "accident");
    Patient patient4 = new Patient("eddie", "heart attack");
    Patient patient5 = new Patient("bosco", "dengue");
    Patient patient6 = new Patient("max", "cold and cough");
    Patient patient7 = new Patient("john", "stomach pain");

    Nurse nurse = new Nurse();
    nurse.addPatient(patient1, Priority.PATIENT);
    nurse.addPatient(patient2, Priority.EMERGENCY);
    nurse.addPatient(patient3, Priority.PATIENT);
    nurse.addPatient(patient4, Priority.EMERGENCY);
    nurse.addPatient(patient5, Priority.PATIENT);
    nurse.addPatient(patient6, Priority.PATIENT);
    nurse.addPatient(patient7, Priority.PATIENT);

    Thread doctor1 = new Thread(new Doctor("A"));
    Thread doctor2 = new Thread(new Doctor("B"));
    Thread doctor3 = new Thread(new Doctor("C"));
    doctor1.start();
    nurse.changePatientPriority(patient6, Priority.PATIENT);
    doctor2.start();
    nurse.changePatientPriority(patient7, Priority.EMERGENCY);
    doctor3.start();

  }

}
