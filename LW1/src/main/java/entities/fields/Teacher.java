package entities.fields;

import lombok.Getter;

@Getter
public enum Teacher {
    READER("Доцент"),
    MASTER("Магистр"),
    PROFESSOR("Профессор");

    private String name;

    Teacher (String name) {
        this.name = name;
    }
}
