package entities.fields;

import lombok.Getter;

import java.util.Scanner;

@Getter
public class Subject {
    private String name;
    private int period;

    public Subject(String name, int period) {
        this.name = name;
        this.period = period;
    }

    public static Subject fromText(String text) {
        String[] s = text.split(", ");
        return new Subject(s[0], new Scanner(s[1]).nextInt());
    }

    public String toText() {
        return "{" +
                name + ", " +
                period +
                '}';
    }
}
