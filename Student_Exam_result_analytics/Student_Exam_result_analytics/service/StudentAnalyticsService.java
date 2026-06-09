package com.Student Exam.resultanalytics.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.training.resultanalytics.model.Student;
import com.training.resultanalytics.util.MarksFilter;

public class StudentAnalyticsService {

    public void processData(List<Student> students) {

        // 1. Unique Courses using Set
        Set<String> courses = students.stream()
                .map(Student::getCourse)
                .collect(Collectors.toSet());

        System.out.println("\nUnique Courses");
        System.out.println(courses);

        // 2. Group Students by Course
        Map<String, List<Student>> groupedStudents =
                students.stream()
                        .collect(Collectors.groupingBy(Student::getCourse));

        System.out.println("\nStudents Grouped by Course");

        groupedStudents.forEach((course, stuList) -> {
            System.out.print(course + " -> ");
            System.out.println(
                    stuList.stream()
                            .map(Student::getStudentName)
                            .collect(Collectors.toList()));
        });

        // 3. Passed Students
        List<Student> passedStudents =
                students.stream()
                        .filter(Student::isPassed)
                        .collect(Collectors.toList());

        System.out.println("\nPassed Students");

        passedStudents.forEach(
                s -> System.out.println(s.getStudentName()));

        // 4. Total Marks
        int totalMarks =
                passedStudents.stream()
                        .mapToInt(Student::getMarks)
                        .sum();

        System.out.println("\nTotal Marks of Passed Students");
        System.out.println(totalMarks);

        // 5. Average Marks
        double averageMarks =
                passedStudents.stream()
                        .mapToInt(Student::getMarks)
                        .average()
                        .orElse(0);

        System.out.println("\nAverage Marks");
        System.out.printf("%.1f\n", averageMarks);

        // 6. Highest Marks Scorer
        Student topper =
                passedStudents.stream()
                        .max(Comparator.comparing(Student::getMarks))
                        .orElse(null);

        System.out.println("\nHighest Marks Scorer");
        System.out.println(
                topper.getStudentName() + " - " + topper.getMarks());

        // 7. Sort Passed Students
        List<Student> sortedStudents =
                passedStudents.stream()
                        .sorted(
                                Comparator.comparing(Student::getMarks)
                                        .reversed()
                                        .thenComparing(Student::getStudentName)
                        )
                        .collect(Collectors.toList());

        System.out.println("\nSorted Passed Students");

        sortedStudents.forEach(
                s -> System.out.println(
                        s.getStudentName() + " - " + s.getMarks()));

        // 8. Course-wise Total Marks
        Map<String, Integer> courseWiseMarks =
                students.stream()
                        .collect(Collectors.groupingBy(
                                Student::getCourse,
                                Collectors.summingInt(Student::getMarks)
                        ));

        System.out.println("\nCourse Wise Total Marks");

        courseWiseMarks.forEach(
                (course, marks) ->
                        System.out.println(course + " -> " + marks));

        // 9. Optional Search
        Optional<Student> result =
                students.stream()
                        .filter(s -> s.getStudentId() == 110)
                        .findFirst();

        System.out.println("\nOptional Search Result");

        result.ifPresent(
                s -> System.out.println(
                        "Student Found : "
                                + s.getStudentName()
                                + " - "
                                + s.getCourse()
                                + " - "
                                + s.getMarks()));

        // 10. Functional Interface
        MarksFilter filter = student -> student.getMarks() >= 75;

        System.out.println("\nStudents with Marks >= 75");

        students.stream()
                .filter(filter::filter)
                .forEach(
                        s -> System.out.println(
                                s.getStudentName()
                                        + " - "
                                        + s.getMarks()));

        // 11. Method Reference Printing
        System.out.println("\nFinal Report Using Method Reference");

        sortedStudents.forEach(System.out::println);
    }
}