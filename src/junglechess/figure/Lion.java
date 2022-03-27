
package junglechess.figure;

import junglechess.Field;
import java.util.*;


public class Lion extends Figure {

    public Lion(Color color, Field field) {
        super(color, "lion", 7, field);
    }

    @Override
    public List<Field> getAccessibleFields() {
        return null;
    }
}