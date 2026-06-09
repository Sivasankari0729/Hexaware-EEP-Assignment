package com.training.resultanalytics.repository;

import java.util.ArrayList;
import java.util.List;

import com.Student Exam.resultanalytics.model.Student;

public class StudentRepository {

    public List<Student> getStudents() {

        List<Student> students = new ArrayList<>();

        students.add(new Student(101,"Anu","Java",82,true));
        students.add(new Student(102,"Bala","Java",45,false));
        students.add(new Student(103,"Charan","Python",91,true));
        students.add(new Student(104,"Divya","Java",67,true));
        students.add(new Student(105,"Esha","Python",38,false));
        students.add(new Student(106,"Farhan","DevOps",74,true));
        students.add(new Student(107,"Gokul","DevOps",88,true));
        students.add(new Student(108,"Hari","Java",53,true));
        students.add(new Student(109,"Isha","Python",79,true));
        students.add(new Student(110,"John","DevOps",62,true));
        students.add(new Student(111,"Kavya","Java",95,true));
        students.add(new Student(112,"Lokesh","Python",49,false));

        return students;
    }
}