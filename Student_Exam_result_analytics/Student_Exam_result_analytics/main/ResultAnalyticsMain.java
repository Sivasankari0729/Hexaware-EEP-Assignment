package com.Student Exam.resultanalytics.main;

import java.util.List;

import com.training.resultanalytics.model.Student;
import com.training.resultanalytics.repository.StudentRepository;
import com.training.resultanalytics.service.StudentAnalyticsService;

public class ResultAnalyticsMain {

    public static void main(String[] args) {

        StudentRepository repository =
                new StudentRepository();

        List<Student> students =
                repository.getStudents();

        StudentAnalyticsService service =
                new StudentAnalyticsService();

        service.processData(students);
    }
}