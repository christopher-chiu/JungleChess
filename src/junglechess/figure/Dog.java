
package junglechess.figure;

import junglechess.Field;
import java.util.*;


public class Dog extends Figure {

    public Dog(Color color, Field field) {
        super(color, "dog", 4, field);
    }

    @Override
    public List<Field> getAccessibleFields() {
        return null;
    }
}