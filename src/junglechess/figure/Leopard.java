
package junglechess.figure;

import junglechess.Field;
import java.util.*;


public class Leopard extends Figure {

    public Leopard(Color color, Field field) {
        super(color, "leopard", 5, field);
    }

    @Override
    public List<Field> getAccessibleFields() {
        return null;
    }
}