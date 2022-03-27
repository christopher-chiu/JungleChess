
package junglechess.figure;

import junglechess.Field;
import java.util.*;


public class Rat extends Figure {

    public Rat(Color color, Field field) {
        super(color, "rat", 1, field);
    }

    @Override
    public List<Field> getAccessibleFields() {
        return null;
    }
}