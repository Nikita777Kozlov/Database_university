package entities.main;


import entities.fields.Grade;
import entities.fields.Student;
import entities.fields.Subject;
import entities.fields.Teacher;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

@Getter
@Setter
@EqualsAndHashCode
public class University {
    private Grade grade;
    private Subject subject;
    private Student student;
    private Teacher teacher;
    private double countOfStudents;
    private double totalGrade;

    public University(Grade grade, Subject subject, Student student, Teacher teacher, double countOfStudents) {
        this.grade = grade;
        this.subject = subject;
        this.student = student;
        this.teacher = teacher;
        this.countOfStudents = countOfStudents;
        this.totalGrade = this.countOfStudents * this.student.getAvg_grade();
    }

    public static University fromText(String text) {
        text = text.substring(1, text.length() - 1).replaceAll("\\.", ",");

        String grade = text.substring(0, text.indexOf(", "));
        text = text.substring(text.indexOf(", ") + 2);

        String subject = text.substring(1, text.indexOf("}"));
        text = text.substring(text.indexOf("}, ") + 3);

        String student = text.substring(1, text.indexOf("}"));
        text = text.substring(text.indexOf("}, ") + 3);

        String teacher = text.substring(0, text.indexOf(", "));
        text = text.substring(text.indexOf(", ") + 2);

        double countOfStudents = new Scanner(text).nextDouble();

        return new University(
                Grade.valueOf(grade),
                Subject.fromText(subject),
                Student.fromText(student),
                Teacher.valueOf(teacher),
                countOfStudents);
    }

    @Override
    public String toString() {
        return "grade=" + grade.getName() +
                ", subject=" + subject.getName() +
                ", student=" + student.getName() +
                ", teacher=" + teacher.getName() +
                ", countOfStudents=" + countOfStudents;
    }

    public String toText() {
        return '{' +
                grade.toString() + ", " +
                subject.toText() + ", " +
                student.toText() + ", " +
                teacher.toString() + ", " +
                countOfStudents +
                '}';
    }
}
