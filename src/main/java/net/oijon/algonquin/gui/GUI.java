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
		
		Label consoleLabel = new Label("Console");
		TextArea console = new TextArea();
		
		Label insertIPA = new Label("Insert IPA:");
		TextArea insert = new TextArea("hɛloʊ ænd wɛlkəm tu ælgonkwɪn tɛkst tu spitʃ");
		Button pronounceButton = new Button("Pronounce!");
		pronounceButton.setDefaultButton(true);
		
		pronounceButton.setOnAction(new EventHandler<ActionEvent>() {

	        @Override
	        public void handle(ActionEvent event) {
	        	console.setText(IPA.createAudio(IPA.getFileNames(insert.getText())));
	        }
	    });
		/**
		GridPane ipaChart = new GridPane();
		ipaChart.setAlignment(Pos.CENTER);
		ipaChart.setHgap(5);
		ipaChart.setVgap(5);
		ipaChart.setPadding(new Insets(15, 15, 15, 15));
		
		Label plosive = new Label("Plosive");
		ipaChart.add(plosive, 0, 1);
		Label nasal = new Label("Nasal");
		ipaChart.add(nasal, 0, 2);
		Label trill = new Label("Trill");
		ipaChart.add(trill, 0, 3);
		Label tapOrFlap = new Label("Tap or Flap");
		ipaChart.add(tapOrFlap, 0, 4);
		Label fricative = new Label("Fricative");
		ipaChart.add(fricative, 0, 5);
		Label lateralFricative = new Label("Lateral fricative");
		ipaChart.add(lateralFricative, 0, 6);
		Label approximant = new Label("Approximant");
		ipaChart.add(approximant, 0, 7);
		Label lateralApproximant = new Label("Lateral Approximant");
		ipaChart.add(lateralApproximant, 0, 8);
		
		Label bilabial = new Label("Bilabial");
		ipaChart.add(bilabial, 1, 0);
		Button pButton = new Button("p");
		Button bButton = new Button("b");
		HBox bilabialPlosives = new HBox(pButton, bButton);
		ipaChart.add(bilabialPlosives, 1, 1);
		Button m̥Button = new Button("m̥");
		Button mButton = new Button("m");
		HBox bilabialNasals = new HBox(m̥Button, mButton);
		ipaChart.add(bilabialNasals, 1, 2);
		Button ʙ̥Button = new Button("ʙ̥");
		Button ʙButton = new Button("ʙ");
		HBox bilabialTrills = new HBox(ʙ̥Button, ʙButton);
		
		ipaChart.add(bilabialTrills, 1, 3);
		Button ⱱ̥̟Button = new Button("ⱱ̥̟");
		Button ⱱ̟Button = new Button("ⱱ̟");
		HBox bilabialTaps = new HBox(ⱱ̥̟Button, ⱱ̟Button);
		ipaChart.add(bilabialTaps, 1, 4);
		Button ɸButton = new Button("ɸ");
		Button βButton = new Button("β");
		HBox bilabialFricatives = new HBox(ɸButton, βButton);
		ipaChart.add(bilabialFricatives, 1, 5);
		Button impossible1 = new Button(" ");
		impossible1.setDisable(true);
		Button impossible2 = new Button(" ");
		impossible2.setDisable(true);
		HBox bilabialLateralFricatives = new HBox(impossible1, impossible2);
		ipaChart.add(bilabialLateralFricatives, 1, 6);
		Button ʋ̟̥Button = new Button("ʋ̟̥");
		Button ʋ̟Button = new Button("ʋ̟");
		HBox bilabialApproximants = new HBox(ʋ̟̥Button, ʋ̟Button);
		ipaChart.add(bilabialApproximants, 1, 7);
		Button impossible3 = new Button(" ");
		impossible3.setDisable(true);
		Button impossible4 = new Button(" ");
		impossible4.setDisable(true);
		HBox bilabialLateralApproximants = new HBox(impossible3, impossible4);
		ipaChart.add(bilabialLateralApproximants, 1, 8);
		
		
		
		Label labiodental = new Label("Labiodental");
		ipaChart.add(labiodental, 2, 0);
		Button p̪Button = new Button("p̪");
		Button b̪Button = new Button("b̪");
		HBox labiodentalPlosives = new HBox(p̪Button, b̪Button);
		ipaChart.add(labiodentalPlosives, 2, 1);
		Button ɱ̥Button = new Button("ɱ̥");
		Button ɱButton = new Button("ɱ");
		HBox labiodentalNasals = new HBox(ɱ̥Button, ɱButton);
		ipaChart.add(labiodentalNasals, 2, 2);
		Button voicelessLabiodentalTrillButton = new Button("̥ʙ̪̥");
		Button voicedLabiodentalTrillButton = new Button("̥ʙ");
		HBox labiodentalTrills = new HBox(voicelessLabiodentalTrillButton, voicedLabiodentalTrillButton);
		ipaChart.add(labiodentalTrills, 2, 3);
		Button ⱱ̥Button = new Button("ⱱ̥");
		Button ⱱButton = new Button("ⱱ");
		HBox labiodentalTaps = new HBox(ⱱ̥Button, ⱱButton);
		ipaChart.add(labiodentalTaps, 2, 4);
		Button fButton = new Button("f");
		Button vButton = new Button("v");
		HBox labiodentalFricatives = new HBox(fButton, vButton);
		ipaChart.add(labiodentalFricatives, 2, 5);
		Button impossible5 = new Button(" ");
		impossible5.setDisable(true);
		Button impossible6 = new Button(" ");
		impossible6.setDisable(true);
		HBox labiodentalLateralFricatives = new HBox(impossible5, impossible6);
		ipaChart.add(labiodentalLateralFricatives, 2, 6);
		Button ʋ̥Button = new Button("ʋ̥");
		Button ʋButton = new Button("ʋ");
		HBox labiodentalApproximants = new HBox(ʋ̥Button, ʋButton);
		ipaChart.add(labiodentalApproximants, 2, 7);
		Button impossible7 = new Button(" ");
		impossible7.setDisable(true);
		Button impossible8 = new Button(" ");
		impossible8.setDisable(true);
		HBox labiodentalLateralApproximants = new HBox(impossible7, impossible8);
		ipaChart.add(labiodentalLateralApproximants, 2, 8);
		
		Label dental = new Label("Dental");
		ipaChart.add(dental, 3, 0);
		Button t̪Button = new Button("t̪");
		Button d̪Button = new Button("d̪");
		HBox dentalPlosives = new HBox(t̪Button, d̪Button);
		ipaChart.add(dentalPlosives, 3, 1);
		Button n̥̪Button = new Button("n̥̪");
		Button n̪Button = new Button("n̪");
		HBox dentalNasals = new HBox(n̥̪Button, n̪Button);
		ipaChart.add(dentalNasals, 3, 2);
		Button r̥̪Button = new Button("r̥̪");
		Button r̪Button = new Button("r̪");
		HBox dentalTrills = new HBox(r̥̪Button, r̪Button);
		ipaChart.add(dentalTrills, 3, 3);
		Button ɾ̥̪Button = new Button("ɾ̥̪");
		Button ɾ̪Button = new Button("ɾ̪");
		HBox dentalTaps = new HBox(ɾ̥̪Button, ɾ̪Button);
		ipaChart.add(dentalTaps, 3, 4);
		Button θButton = new Button("θ");
		Button ðButton = new Button("ð");
		HBox dentalFricatives = new HBox(θButton, ðButton);
		ipaChart.add(dentalFricatives, 3, 5);
		Button ɬ̪Button = new Button("ɬ̪");
		Button ɮ̪Button = new Button("ɮ̪");
		HBox dentalLateralFricatives = new HBox(ɬ̪Button, ɮ̪Button);
		ipaChart.add(dentalLateralFricatives, 3, 6);
		Button ɹ̥̪Button = new Button("ɹ̥̪");
		Button ɹ̪Button = new Button("ɹ̪");
		HBox dentalApproximants = new HBox(ɹ̥̪Button, ɹ̪Button);
		ipaChart.add(dentalApproximants, 3, 7);
		Button l̥̪Button = new Button("l̥̪");
		Button l̪Button = new Button("l̪");
		HBox lateralApproximants = new HBox(l̥̪Button, l̪Button);
		ipaChart.add(lateralApproximants, 3, 8);
		
		Label alveolar = new Label("Alveolar");
		ipaChart.add(alveolar, 4, 0);
		Label postalveolar = new Label("Postalveolar");
		ipaChart.add(postalveolar, 5, 0);
		Label retroflex = new Label("Retroflex");
		ipaChart.add(retroflex, 6, 0);
		Label palatal = new Label("Palatal");
		ipaChart.add(palatal, 7, 0);
		Label velar = new Label("Velar");
		ipaChart.add(velar, 8, 0);
		Label uvular = new Label("Uvular");
		ipaChart.add(uvular, 9, 0);
		Label pharyngeal = new Label("Pharyngeal");
		ipaChart.add(pharyngeal, 10, 0);
		Label glottal = new Label("Glottal");
		ipaChart.add(glottal, 11, 0);
		**/
		
		pronounceGrid.add(insertIPA, 0, 1);
		pronounceGrid.add(insert, 0, 2);
		pronounceGrid.add(pronounceButton, 0, 3);
		
		pronounceBox.getChildren().addAll(pronounceGrid);
		
		
		
		mainBox.getChildren().addAll(pronounceBox, consoleLabel, console);
		
		Scene scene = new Scene(mainBox, 750, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Algonquin Text-to-Speach");
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
        launch(args);
    }

}
