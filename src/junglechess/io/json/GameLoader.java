package junglechess.io.json;

import com.google.gson.*;
import junglechess.Board;
import junglechess.FigureFactory;
import junglechess.GameIO;
import junglechess.figure.Color;
import junglechess.figure.Figure;

public class GameLoader implements GameIO {

    @Override
    public void load(byte[] json, Board board) {
        JsonObject element = new JsonParser().parse(new String(json)).getAsJsonObject();
        loadFigures(Color.BLACK, element.getAsJsonArray("black"), board);
        loadFigures(Color.RED, element.getAsJsonArray("red"), board);
        board.setCurrentTurn(element.get("current_turn").getAsInt());
    }

    private void loadFigures(Color color, JsonArray jsonFigures, Board board) {
        for (JsonElement jsonFigure : jsonFigures) {
            String type = jsonFigure.getAsJsonObject().get("type").getAsString();
            String pos = jsonFigure.getAsJsonObject().get("pos").getAsString();
            int firstTurn = jsonFigure.getAsJsonObject().get("first_turn").getAsInt();
            Figure figure = FigureFactory.createFigure(color, type, pos, firstTurn);
            board.setFigure(figure);
        }
    }

    @Override
    public byte[] save(Board board) {
        JsonObject main = new JsonObject();
        main.addProperty("current_turn", board.getCurrentTurn());
        JsonArray black = new JsonArray();
        JsonArray red = new JsonArray();
        for (Figure figure : board.getFigures()) {
            JsonObject jsonFigure = new JsonObject();
            jsonFigure.addProperty("type", figure.getName());
            jsonFigure.addProperty("pos", figure.getPos());
            jsonFigure.addProperty("first_turn", figure.getFirstTurn());
            if (figure.getColor() == Color.BLACK) {
                black.add(jsonFigure);
            } else {
                red.add(jsonFigure);
            }
        }
        main.add("black", black);
        main.add("red", red);
        return new GsonBuilder().create().toJson(main).getBytes();
    }

    @Override
    public String getFileTypeDescription() {
        return "JSON files (*.json)";
    }

    @Override
    public String getFileExtension() {
        return "json";
    }
}