/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.model;

/**
 *
 * @author webwerks
 */
public class Patient implements Comparable<Patient> {

  private String name;
  private String medicalProblem;
  private Integer appointmentNumber;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMedicalProblem() {
    return medicalProblem;
  }

  public void setMedicalProblem(String medicalProblem) {
    this.medicalProblem = medicalProblem;
  }

  public Integer getAppointmentNumber() {
    return appointmentNumber;
  }

  public void setAppointmentNumber(Integer appointmentNumber) {
    this.appointmentNumber = appointmentNumber;
  }

  public Patient(String name, String medicalProblem) {
    this.name = name;
    this.medicalProblem = medicalProblem;
  }

  public Patient() {
  }

  @Override
  public int compareTo(Patient o) {
    return this.appointmentNumber.compareTo(o.getAppointmentNumber());
  }

  @Override
  public String toString() {
    return this.appointmentNumber.toString(); //To change body of generated methods, choose Tools | Templates.
  }

}
