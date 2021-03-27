package entities.fields;

import lombok.Getter;

import java.util.Scanner;

@Getter
public class Student {
    private String name;
    private double avg_grade;
    private int age;

    public Student(String name, double avg_grade, int age) {
        this.name = name;
        this.avg_grade = avg_grade;
        this.age = age;
    }

    public static Student fromText(String text) {
        String[] s = text.split(", ");
        return new Student(
                s[0],
                new Scanner(s[1]).nextDouble(),
                new Scanner(s[2]).nextInt());
    }

    public String toText() {
        return "{" +
                name + ", " +
                avg_grade + ", " +
                age +
                "}";
    }
}
