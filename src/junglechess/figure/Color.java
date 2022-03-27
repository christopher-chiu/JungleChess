package junglechess.figure;

import java.io.Serializable;

public enum Color implements Serializable {

    RED("red"),
    BLACK("black");

    private String name;

    Color(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Color revert() {
        return this == Color.BLACK ? Color.RED : Color.BLACK;
    }
}
