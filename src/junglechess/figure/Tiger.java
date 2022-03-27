
package junglechess.figure;

import junglechess.Field;
import java.util.*;


public class Tiger extends Figure {

    public Tiger(Color color, Field field) {
        super(color, "tiger", 6, field);
    }

    @Override
    public List<Field> getAccessibleFields() {
        return null;
    }
}