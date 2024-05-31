package com.example.ium_gb;

public class Exam {
    private String name;
    private int cfu;
    private int grade;

    public Exam(String name, int cfu, int grade) {
        this.name = name;
        this.cfu = cfu;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCfu() {
        return cfu;
    }

    public void setCfu(int cfu) {
        this.cfu = cfu;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
