package com.endava.internship.collections;

import java.time.LocalDate;
import java.util.Objects;

/**
 * The class that defines the element that will be contained by your collection
 */
public class Student implements Comparable<Student> {
    private final String name;
    private final LocalDate dateOfBirth;
    private final String details;

    public Student(String name, LocalDate dateOfBirth, String details) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return name.equals(student.name) && dateOfBirth.equals(student.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth);
    }

    @Override
    public int compareTo(Student o) {
        if (!this.name.equals(o.name)) {
            return this.dateOfBirth.compareTo(o.dateOfBirth);
        }
        return this.name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return "\nName: " + name +
                "\nDate of birth: " + dateOfBirth +
                "\nDetails: " + details;
    }
}
