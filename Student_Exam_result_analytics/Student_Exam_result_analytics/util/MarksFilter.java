package com.Student Exam.resultanalytics.util;

import com.Student Exam.resultanalytics.model.Student;

@FunctionalInterface
public interface MarksFilter {

    boolean filter(Student student);
}