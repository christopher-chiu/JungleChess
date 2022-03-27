package junglechess;

import javafx.scene.layout.GridPane;
import junglechess.figure.Color;
import junglechess.figure.Figure;
import junglechess.figure.TileType;

import java.io.File;
import java.util.*;

public class Board extends GridPane {

    private Field[] fields = new Field[63];
    private Map<Color, Set<Field>> attackedFields = new HashMap<>();
    private int currentTurn = 1;
    private Map<String, GameIO> io = new LinkedHashMap<>();

    Board() {
        resetAttackedFields();
        for(int i = 0; i < 63; i++) {
            int x = getX(i);
            int y = getY(i);
            Field field = new Field(this, x, y);
            add(field, x, y);
            fields[i] = field;
        }
        recalculateAttackedFields();
    }

    private void resetAttackedFields() {
        attackedFields.put(Color.BLACK, new HashSet<>());
        attackedFields.put(Color.RED, new HashSet<>());
    }

    private int getX(int index) {
        return index % 7;
    }

    private int getY(int index) {
        return (index - getX(index)) / 7;
    }

    public Field getField(int x, int y) {
        if (x < 0 || x > 6 || y < 0 || y > 8) {
            return null;
        } else {
            return fields[y*7 + x];
        }
    }

    public List<Figure> getFigures() {
        return getFigures(null);
    }

    public List<Figure> getFigures(Color color) {
        List<Figure> figures = new ArrayList<>();
        Arrays.stream(fields)
                .filter(f -> f.figure != null && (color == null || f.figure.getColor() == color))
                .forEach(f -> figures.add(f.figure));
        return figures;
    }

    public void setFigure(Figure figure) {
        getField(figure.getX(), figure.getY()).setFigure(figure, true);
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    void nextTurn() {
        currentTurn++;
        recalculateAttackedFields();
        gameStateTest();
    }

    public Color getTurn() {
        return currentTurn % 2 == 0 ? Color.BLACK : Color.RED;
    }

    void gameStateTest() {
        // check if any figure is in the 3rd of 59th field
        if(fields[2].getFigure() != null) {
            // black won
            System.out.println("black won");
        } else if (fields[58].getFigure() != null) {
            //red won
            System.out.println("red won");
        }

    }

    public void recalculateAttackedFields() {
        resetAttackedFields();
        Arrays.stream(fields)
                .filter(f -> f.figure != null && f.figure.getColor() == Color.RED)
                .forEach(f -> attackedFields.get(Color.RED).addAll(f.getFigure().getAccessibleFields()));
        Arrays.stream(fields)
                .filter(f -> f.figure != null && f.figure.getColor() == Color.BLACK)
                .forEach(f -> attackedFields.get(Color.BLACK).addAll(f.getFigure().getAccessibleFields()));
    }

    public Set<Field> getAllAccessibleFields(Color color) {
        return attackedFields.get(color);
    }

    public Map<String, GameIO> getIO() {
        return io;
    }

    public void setIO(GameIO io) {
        this.io.put(io.getFileExtension(), io);
    }

    public void clear() {
        for (Field field : fields) {
            field.setFigure(null, true);
        }
        recalculateAttackedFields();
        currentTurn = 1;
    }





//    public void load(File file) {
//        load(getFileExtension(file.getName()), Helper.loadDataFromFile(file));
//    }

    void loadFromResource(String resource) {
        load(getFileExtension(resource), Helper.loadDataFromResource(resource));
    }

    private String getFileExtension(String name) {
        return name.substring(name.lastIndexOf('.') + 1);
    }

    private void load(String type, byte[] s) {
        clear();
        io.get(type).load(s, this);
        recalculateAttackedFields();
        gameStateTest();
    }


}
