package junglechess.figure;

import junglechess.Field;

import java.util.*;


public class Cat extends Figure {

    public Cat(Color color, Field field) {
        super(color, "cat", 2, field);
    }

    @Override
    public List<Field> getAccessibleFields() {
        List<Field> fields = new ArrayList<>();
        Set<Field> attackedFields = field.getBoard().getAllAccessibleFields(color.revert());
        //check for fields that are not attacked and not occupied by own figures or enemy king
        for (int kx = -1; kx <= 1; kx++) {
            for (int ky = -1; ky <= 1; ky++) {
                Field f;
                if ((f = field.getBoard().getField(field.getX() + kx, field.getY() + ky)) != null
                        && f != field
                        && !attackedFields.contains(f)
                        && (f.getFigure() == null
                        || f.getFigure() != null && f.getFigure().color != color)) {
                    fields.add(f);
                }
            }
        }

        return fields;
    }


    @Override
    public Figure postTurnAction(Field oldField, Field newField, boolean graphic) {
        if (graphic && getFirstTurn() < 0) {
            if (x == 2) {
                field.getBoard().getField(0, y).getFigure().move(field.getBoard().getField(3, y), true);
            } else if (x == 6) {
                field.getBoard().getField(7, y).getFigure().move(field.getBoard().getField(5, y), true);
            }
        }
        return null;
    }
}
