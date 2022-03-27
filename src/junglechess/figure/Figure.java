package junglechess.figure;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import junglechess.Field;
import junglechess.Main;

import java.util.*;

public abstract class Figure {

    private static Map<String, Image> imageCache = new HashMap<>();

    int x, y = -1;
    transient Field field;
    Color color;
    private String name;
    private int rank;

    private String imageFileName;

    private int firstTurn = -1;

    Figure(Color color, String name, int rank, Field field) {
        this.color = color;
        this.name = name;
        this.rank = rank;
        setField(field);

        imageFileName = "img/" + name + "-" + color.getName() + ".png";
        if(!imageCache.containsKey(imageFileName)) {
            imageCache.put(imageFileName, new Image(imageFileName));
        }

        if (field != null) {
            this.x = field.getX();
            this.y = field.getY();
            field.setFigure(this, true);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getPos() {
        return ((char) (x + 48)) + "" + ((char) (y + 48));
    }


    //returns whether the Figure was successfully moved
    public Figure move(Field field, boolean graphic) {
        Figure killedFigure = field.getFigure();
        field.setFigure(this, graphic);
        if (this.field != null) {
            this.field.setFigure(null, graphic);
        }
        int oldX = x;
        int oldY = y;
        x = field.getX();
        y = field.getY();
        setField(field);
        Figure postFigure = postTurnAction(Main.getBoard().getField(oldX, oldY), field, graphic);
        if (graphic && firstTurn < 0) {
            firstTurn = field.getBoard().getCurrentTurn();
        }
        return killedFigure == null ? postFigure : killedFigure;
    }



    //returns a list of all accessible fields of this figure, including fields with opponent's figures
    //that can be beaten
    public abstract List<Field> getAccessibleFields();





    public boolean canMoveTo(Field field) {
        return canMove() && getAllAccessibleFields().contains(field);
    }

    public boolean canMove() {
        return getField().getBoard().getTurn() == color;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public ImageView getImageView() {
        return new ImageView(imageCache.get(imageFileName));
    }

    public Image getImage() {
        return imageCache.get(imageFileName);
    }

    public Figure postTurnAction(Field oldField, Field newField, boolean graphic) {
        return null;
    }

    public List<Field> getAllAccessibleFields() {
        List<Field> fields = getAccessibleFields();
        return fields;
    }


    public int getFirstTurn() {
        return firstTurn;
    }

    public void setFirstTurn(int firstTurn) {
        this.firstTurn = firstTurn;
    }

    @Override
    public String toString() {
        return "Figure:<" + color.getName() +
                " " + name + " " +
                "x=" + x + " y=" + y +
                " Field=" + field + ">";
    }
}
