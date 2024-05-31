package com.example.ium_gb;

import java.util.ArrayList;

public class User {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String password;
    private ArrayList<Exam> exams;

    public User(String firstName, String lastName, String dateOfBirth, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.exams = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public void addExam(Exam exam) {
        exams.add(exam);
    }

    public void removeExam(Exam exam) {
        exams.remove(exam);
    }

    public double getWeightedAverage() {
        int totalCfu = 0;
        int weightedSum = 0;
        for (Exam exam : exams) {
            if (exam.getGrade() != -1) {
                totalCfu += exam.getCfu();
                weightedSum += exam.getGrade() * exam.getCfu();
            }
        }
        return totalCfu == 0 ? 0 : (double) weightedSum / totalCfu;
    }

    public double getArithmeticMean() {
        int totalExams = 0;
        int sumGrades = 0;
        for (Exam exam : exams) {
            if (exam.getGrade() != -1) {
                totalExams++;
                sumGrades += exam.getGrade();
            }
        }
        return totalExams == 0 ? 0 : (double) sumGrades / totalExams;
    }

    public double getGraduationGrade() {
        double weightedAverage = getWeightedAverage();
        return weightedAverage * 110 / 30;
    }
}
