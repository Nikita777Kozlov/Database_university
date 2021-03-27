package entities.fields;

import lombok.Getter;

@Getter
public enum Grade {
    LOW("Low"),
    MIDDLE("Middle"),
    HIGH("High"),
    MAXIMUM("Maximum");

    private String name;

    Grade(String name) {
        this.name = name;
    }
}
