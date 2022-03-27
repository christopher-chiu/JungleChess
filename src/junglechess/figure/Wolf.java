
package junglechess.figure;

import junglechess.Field;
import java.util.*;


public class Wolf extends Figure {

    public Wolf(Color color, Field field) {
        super(color, "wolf", 3, field);
    }

    @Override
    public List<Field> getAccessibleFields() {
        return null;
    }
}