package net.oijon.algonquin.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import net.oijon.algonquin.tts.IPA;

public class GUI extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		//pre-startup
		VBox mainBox = new VBox();
		mainBox.setAlignment(Pos.CENTER);
		mainBox.setSpacing(5);
		
		HBox pronounceBox = new HBox();
		pronounceBox.setAlignment(Pos.CENTER);
		GridPane pronounceGrid = new GridPane();
		pronounceGrid.setAlignment(Pos.CENTER);
		pronounceGrid.setHgap(10);
		pronounceGrid.setVgap(10);
		pronounceGrid.setPadding(new Insets(25, 25, 25, 25));
		
		Label insertIPA = new Label("Insert IPA:");
		TextArea insert = new TextArea("hɛloʊ ænd wɛlkəm tu ælgonkwɪn tɛkst tu spitʃ");
		Button pronounceButton = new Button("Pronounce!");
		
		pronounceButton.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	        	IPA.createAudio(IPA.getFileNames(insert.getText()));
	        }
	    });
		
		pronounceGrid.add(insertIPA, 0, 1);
		pronounceGrid.add(insert, 0, 2);
		pronounceGrid.add(pronounceButton, 0, 3);
		
		pronounceBox.getChildren().addAll(pronounceGrid);
		
		Label consoleLabel = new Label("Console");
		TextArea console = new TextArea();
		
		
		mainBox.getChildren().addAll(pronounceBox, consoleLabel, console);
		
		Scene scene = new Scene(mainBox, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Algonquin Text-to-Speach");
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
        launch(args);
    }

}
