package junglechess;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ServiceLoader;

public class Main extends Application {

    private Label status;
    private Board board;
    private static Main instance;

    @Override
    public void start(Stage primaryStage) throws IOException {
        instance = this;

        primaryStage.setTitle("Dou Shou Qi (Jungle Chess)");
        primaryStage.getIcons().add(new Image("resources/img/icon.png"));

        //main BorderPane
        StackPane root = FXMLLoader.load(getClass().getResource("layout.fxml"));

        //chess board with column and row markings
        GridPane table = new GridPane();
        table.add(board = new Board(), 0, 0, 6, 8);
        table.setAlignment(Pos.CENTER);
        root.getChildren().add(table);


        //scene
//        Scene scene = new Scene(pane, 440, 490);
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
//        primaryStage.setMinWidth(primaryStage.getWidth());
//        primaryStage.setMinHeight(primaryStage.getHeight());

        //set IO and load initial setup
        ServiceLoader.load(GameIO.class).forEach(board::setIO);
        board.loadFromResource("init.json");
    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        instance = this;
//
//
//        StackPane root = FXMLLoader.load(getClass().getResource("layout.fxml"));
//        primaryStage.setTitle("Dou Shou Qi (Jungle Chess)");
//        primaryStage.getIcons().add(new Image("resources/img/icon.png"));
//
////        board = new Board(board_table);
////
////        //set IO and load initial setup
////        ServiceLoader.load(GameIO.class).forEach(board::setIO);
////        board.loadFromResource("init.json");
//
//        primaryStage.setScene(new Scene(root));
//        primaryStage.setResizable(false);
//        primaryStage.show();
//    }

    public static Board getBoard() {
        return instance.board;
    }

    static void displayStatusText(String text) {
        instance.status.setText(text);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
