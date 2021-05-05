package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Notepad");
        FlowPane pane = new FlowPane(10,10);
        primaryStage.setScene(new Scene(pane, 1005, 650));
        MenuBar menu = new MenuBar();
        Menu fileMenu = new Menu("MENU");
        MenuItem open = new MenuItem("Open file");
        MenuItem save = new MenuItem("Save file");
        FileChooser fileCH = new FileChooser();
        fileMenu.getItems().addAll(open, new SeparatorMenuItem(), save);
        menu.getMenus().add(fileMenu);
        TextArea textArea = new TextArea();
        textArea.setPrefWidth(1000);
        textArea.setMaxWidth(1000);
        textArea.setPrefHeight(600);
        textArea.setMaxHeight(600);
        textArea.setScrollLeft(10);
        pane.getChildren().addAll(menu, textArea);
        primaryStage.show();

        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                File file = fileCH.showOpenDialog(primaryStage);

                if (file != null) {

                    String text = "";
                    try (Scanner in = new Scanner(new File(String.valueOf(file)))) {
                        while (in.hasNext()) {
                            text += in.nextLine() + "\r\n";
                        }
                        in.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    textArea.setText(text);
                }

            }
        });

        save.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                File fileSave = fileCH.showSaveDialog(primaryStage);

                try(FileWriter writer = new FileWriter(new File(String.valueOf(fileSave)), false))
                {
                    writer.write(textArea.getText());
                    writer.flush();
                } catch(IOException ex){

                  }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
