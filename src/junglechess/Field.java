package junglechess;

import javafx.scene.control.Label;
import junglechess.figure.Color;
import junglechess.figure.Figure;
import junglechess.figure.TileType;

import java.util.List;

public class Field extends Label {
    Figure figure;
    TileType tileType;
    private int x, y;
    private Board board;
    private static final String defaultStyle = "-fx-background-color: none";
    private static final String highlightStyleBlack = "-fx-background-color: forestgreen;";
    private static final String highlightStyleRed = "-fx-background-color: palegreen;";
    private static final String highlightKillStyleBlack = "-fx-background-color: darkred;";
    private static final String highlightKillStyleRed = "-fx-background-color: #ff5555;";

    Field(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
        setOnMouseEntered(e -> onMouseEntered());
        setOnMouseExited(e -> onMouseExited());
        if(x == 3 && (y == 0 || y == 8)) {
            this.tileType = TileType.DEN;
        } else if ((x == 2 && y == 0) || (x == 4 && y == 0) || (x == 3 && y == 1) ||
                (x == 2 && y == 8) || (x == 4 && y == 8) || (x == 3 && y == 7)) {
            this.tileType = TileType.TRAP;
        } else if (((1 <= x && x <= 2) || (4 <= x && x <= 5)) && (3 <= y && y <= 5)) {
            this.tileType = TileType.WATER;
        } else if (((x == 0 || x == 3 || x == 6) && (3 <= y && y <= 5)) ||
                    ((x == 1 || x == 2 || x == 4 || x == 5) && (y == 2 || y == 6))) {
            this.tileType = TileType.SHORE;
        } else {
            this.tileType = TileType.LAND;
        }

    }


    // TODO - change these four methods to consider tile type instead of color
    private void setHighlightEmpty() {
        setStyle(getColor() == Color.BLACK ? highlightStyleBlack : highlightStyleRed);
    }
    private void setHighlightKill() {
        setStyle(getColor() == Color.BLACK ? highlightKillStyleBlack : highlightKillStyleRed);
    }
    private void resetBackgroundColor() {
        setStyle(defaultStyle);
    }

    private Color getColor() {
        return Color.BLACK;
    }



    public Board getBoard() {
        return board;
    }

    public TileType getTileType() { return tileType; }

    public void setFigure(Figure figure, boolean graphic) {
        this.figure = figure;
        if (graphic) {
            if (figure == null) {
                setGraphic(null);
            } else {
                setGraphic(figure.getImageView());
            }
        }
    }

    public Figure getFigure() {
        return figure;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void onMouseEntered() {
        List<Field> trueFields;
        if(figure != null && figure.canMove() && (trueFields = figure.getAllAccessibleFields()).size() > 0) {
            for(Field field : trueFields) {
                if (field.figure != null) {
                    field.setHighlightKill();
                } else {
                    field.setHighlightEmpty();
                }
            }
        }
    }

    private void onMouseExited() {
        List<Field> trueFields;
        if (figure != null && figure.canMove() && (trueFields = figure.getAllAccessibleFields()).size() > 0) {
            trueFields.forEach(Field::resetBackgroundColor);
        }
    }

    @Override
    public String toString() {
        return "<" + x + "," + y + ">";
    }


}
