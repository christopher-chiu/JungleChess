
package junglechess.figure;

import junglechess.Field;
import java.util.*;


public class Elephant extends Figure {

    public Elephant(Color color, Field field) {
        super(color, "Elephant", 3, field);
    }

    @Override
    public List<Field> getAccessibleFields() {
        return null;
    }
}