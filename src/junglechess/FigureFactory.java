package junglechess;
import junglechess.figure.*;

public class FigureFactory {
    private FigureFactory() {}

    public static Figure createFigure(Color color, String type, String pos, int firstTurn) {
        if (pos.length() != 2) {
            throw new IllegalArgumentException("Invalid position: \"" + pos + "\"");
        }
        int x = pos.charAt(0) - 48;
        int y = pos.charAt(1) - 48;
        if (x < 0 || x > 6 || y < 0 || y > 8) {
            throw new IllegalArgumentException("Position not on board: \"" + pos + "\"");
        }
        Figure f;
        switch (type.toLowerCase()) {
            case "lion":
                f = new Lion(color, Main.getBoard().getField(x, y));
                break;
            case "tiger":
                f = new Tiger(color, Main.getBoard().getField(x, y));
                break;
            case "dog":
                f = new Dog(color, Main.getBoard().getField(x, y));
                break;
            case "cat":
                f = new Cat(color, Main.getBoard().getField(x, y));
                break;
            case "rat":
                f = new Rat(color, Main.getBoard().getField(x, y));
                break;
            case "leopard":
                f = new Leopard(color, Main.getBoard().getField(x, y));
                break;
            case "wolf":
                f = new Wolf(color, Main.getBoard().getField(x, y));
                break;
            case "elephant":
                f = new Elephant(color, Main.getBoard().getField(x, y));
                break;
            default:
                throw new IllegalArgumentException("Unknown Figure: \"" + type + "\"");
        }
        f.setFirstTurn(firstTurn);
        return f;
    }

//    public static Figure createFigure(Color color, int type, int x, int y, int firstTurn) {
//        if (x < 0 || x > 6 || y < 0 || y > 8) {
//            throw new IllegalArgumentException("Position not on board: \"" + x + "/" + y + "\"");
//        }
//        Figure f;
//        switch (type) {
//            case 0b0:
//                f = new Pawn(color, Main.getBoard().getField(x, y));
//                break;
//            case 0b100:
//                f = new Rook(color, Main.getBoard().getField(x, y));
//                break;
//            case 0b101:
//                f = new Knight(color, Main.getBoard().getField(x, y));
//                break;
//            case 0b110:
//                f = new Bishop(color, Main.getBoard().getField(x, y));
//                break;
//            case 0b1110:
//                f = new Queen(color, Main.getBoard().getField(x, y));
//                break;
//            case 0b1111:
//                f = new King(color, Main.getBoard().getField(x, y));
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown Figure: \"" + type + "\"");
//        }
//        f.setFirstTurn(firstTurn);
//        return f;
//    }

//    public static int getFigureID(Figure f) {
//        if (f instanceof Pawn)
//            return 0b0;
//        if (f instanceof Rook)
//            return 0b100;
//        if (f instanceof Knight)
//            return 0b101;
//        if (f instanceof Bishop)
//            return 0b110;
//        if (f instanceof Queen)
//            return 0b1110;
//        if (f instanceof King)
//            return 0b1111;
//        return 0;
//    }
}
