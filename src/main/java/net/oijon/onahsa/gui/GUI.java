package net.oijon.onahsa.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.oijon.olog.Log;
import net.oijon.onahsa.console.Console;
import net.oijon.onahsa.console.Functions;


/**
 * @author N3ther
 *
 */
public class GUI extends Application {

	static Log log = Console.getLog();
	static File logFile = new File(log.getLogFile());
	TextArea console = new TextArea();
	ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	/**
	 * Creates all GUI elements. Might throw exceptions when handling TRM.
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Console.init();
		
		updateLog();
		console.setPadding(Insets.EMPTY);
		
		//fonts
		Font mono = Font.loadFont(GUI.class.getResourceAsStream("/font/NotoSansMono-Regular.ttf"), 10);
		console.setFont(mono);
		
		//backgrounds
		BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
		Background white = new Background(backgroundFill);
		
		//pre-startup
		VBox mainBox = new VBox();
		mainBox.setAlignment(Pos.CENTER);
		mainBox.setSpacing(5);
		
		
		ObservableList<String> options = 
		    FXCollections.observableArrayList(
		        "Classic",
		        "TRM (highly experimental)"
		    );
		@SuppressWarnings("rawtypes")
		final ComboBox synthType = new ComboBox(options);
		synthType.setValue("Classic");
		
		Background sheetMetal = new Background(new BackgroundImage(new Image(GUI.class.getResourceAsStream("/img/sheet-metal.png")),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
    	        BackgroundSize.DEFAULT));
		
		HBox pronounceBox = new HBox();
		pronounceBox.setAlignment(Pos.CENTER);
		GridPane pronounceGrid = new GridPane();
		pronounceGrid.setAlignment(Pos.CENTER);
		pronounceGrid.setHgap(10);
		pronounceGrid.setVgap(10);
		pronounceGrid.setPadding(new Insets(25, 25, 25, 25));
		
		Button consoleLabel = new Button();
		consoleLabel.setGraphic(new ImageView(new Image(GUI.class.getResourceAsStream("/img/console.png"))));
		consoleLabel.setBackground(null);
		consoleLabel.setPadding(new Insets(5, 0, -5, 0));
		
		TextField consoleInput = new TextField();
		
		Button sendToConsole = new Button("Enter");
		sendToConsole.setPadding(new Insets(5, 10, 5, 10));
		sendToConsole.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Console.parse(consoleInput.getText());
			}
			
		});
		
		HBox inputBox = new HBox(consoleInput, sendToConsole);
		inputBox.setHgrow(consoleInput, Priority.ALWAYS);
		
		Button insertIPA = new Button();
		insertIPA.setGraphic(new ImageView(new Image(GUI.class.getResourceAsStream("/img/insert-ipa.png"))));
		insertIPA.setBackground(null);
		insertIPA.setPadding(new Insets(5, 0, 0, 0));
		TextArea insert = new TextArea("hɛloʊ ænd wɛlkəm tu oʔnæhsæʔ tɛkst tu spitʃ");
		
		Button fileNameLabel = new Button();
		fileNameLabel.setGraphic(new ImageView(new Image(GUI.class.getResourceAsStream("/img/file-name.png"))));
		fileNameLabel.setBackground(null);
		fileNameLabel.setPadding(new Insets(5, 0, 0, 0));
		TextField fileNameField = new TextField("output");
		VBox fileName = new VBox(fileNameLabel, fileNameField);
		Button packLabel = new Button();
		packLabel.setGraphic(new ImageView(new Image(GUI.class.getResourceAsStream("/img/sound-pack.png"))));
		packLabel.setBackground(null);
		packLabel.setPadding(new Insets(5, 0, 0, 0));
		
		
		File packsDirFile = new File(System.getProperty("user.home") + "/Onahsa/packs/");
		String[] packnames = packsDirFile.list();
		
		CheckBox logDebug = new CheckBox("Show debug messages");
		logDebug.setTextFill(Paint.valueOf(Color.WHITE.toString()));
		logDebug.setIndeterminate(false);
		logDebug.setSelected(true);
		logDebug.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				log.setDebug(logDebug.selectedProperty().get());
			}
			
		});
		
		VBox packListVBox = new VBox();
				
		packListVBox.setBackground(white);
		
		ScrollPane packListScroll = new ScrollPane(packListVBox);
		packListScroll.setMinHeight(220);
		
		packnames = packsDirFile.list();
		
		packListVBox.getChildren().clear();
		
		for (int i = 0; i < packnames.length; i++) {
            HBox mainPackBox = new HBox();

            Image icon = new Image(GUI.class.getResourceAsStream("/img/no-image.png"));
        	ImageView iconView = new ImageView(icon);
            
        	File iconFile = new File(System.getProperty("user.home") + "/Onahsa/packs/" + packnames[i] + "/icon.png");
        	
        	if (iconFile.exists()) {
        		try {
					icon = new Image(new FileInputStream(iconFile));
					iconView = new ImageView(icon);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	
            VBox detailsBox = new VBox();
            Label name = new Label(packnames[i]);
            Button button = new Button("Select");
            button.setPrefHeight(20);
            button.setPrefWidth(50);
            
            final String actionName = packnames[i]; //this is stupid, but the java compiler has forced me to do this
            button.setOnAction(new EventHandler<ActionEvent>() {
            	
				@Override
				public void handle(ActionEvent event) {
					Console.parse("setpack " + actionName);
				}
            	
            });
            
            detailsBox.getChildren().addAll(name, button);
            mainPackBox.getChildren().addAll(iconView, detailsBox);
            packListVBox.getChildren().add(mainPackBox);
        }
		
		Button refreshPacksButton = new Button("Refresh");
		refreshPacksButton.setPadding(new Insets(5, 10, 5, 10));
		refreshPacksButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String[] packnames = Functions.getPacks();				
				
				packListVBox.getChildren().clear();
				
				for (int i = 0; i < packnames.length; i++) {
		            HBox mainBox = new HBox();

		            Image icon = new Image(GUI.class.getResourceAsStream("/img/no-image.png"));
		        	ImageView iconView = new ImageView(icon);
		            
		        	File iconFile = new File(System.getProperty("user.home") + "/Onahsa/packs/" + packnames[i] + "/icon.png");
		        	
		        	if (iconFile.exists()) {
		        		try {
							icon = new Image(new FileInputStream(iconFile));
							iconView = new ImageView(icon);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        	}
		        	
		            VBox detailsBox = new VBox();
		            Label name = new Label(packnames[i]);
		            Button button = new Button("Select");
		            button.setPrefHeight(20);
		            button.setPrefWidth(50);
		            
		            final String actionName = packnames[i]; //this is stupid, but the java compiler has forced me to do this
		            button.setOnAction(new EventHandler<ActionEvent>() {
		            	
						@Override
						public void handle(ActionEvent event) {
							Console.parse("setpack " + actionName);
						}
		            	
		            });
		            
		            detailsBox.getChildren().addAll(name, button);
		            mainBox.getChildren().addAll(iconView, detailsBox);
		            packListVBox.getChildren().add(mainBox);
		        }
				
			}
			
		});
		VBox packVBox = new VBox(packLabel, packListScroll, refreshPacksButton);
		
		Button pronounceButton = new Button("Pronounce!");
		pronounceButton.setDefaultButton(true);
		
		ExecutorService es = Executors.newFixedThreadPool(1);
		
		pronounceButton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	        	if (synthType.getValue().equals("Classic")) {
	        		es.submit(new Runnable() {
	        		    @Override
	        		    public void run() {
	        		    	Console.parse("setname " + fileNameField.getText());
	        		    	Console.parse("pronounce " + insert.getText());
	        		    	log.debug(insert.getText());
	        		    }
	        		});
	        		
	        	} else if (synthType.getValue().equals("TRM (highly experimental)")) {
	        		Thread t1 = new Thread(new Runnable() {
	        		    @Override
	        		    public void run() {
	        		    	Console.parse("trmtest");
	        		    }
	        		});
	        		t1.setDaemon(true);
	        		t1.start();		
	        	} else {
	        		log.err("Unsupported synthesis type \'" + synthType.getValue() + "\'.");
	        	}
	        	System.gc();
	        }
	    });
		
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
		
		
		VBox leftSide = new VBox(insertIPA, insert, pronounceButton);
		VBox rightSide = new VBox(synthType, fileName, packVBox);
		
		pronounceGrid.add(leftSide, 0, 1);
		//pronounceGrid.add(ipaChart, 0, 2);
		pronounceGrid.add(rightSide, 1, 1);
		
		pronounceBox.getChildren().addAll(pronounceGrid);
		
		
		mainBox.setBackground(sheetMetal);
		mainBox.getChildren().addAll(pronounceBox, logDebug, consoleLabel, console, inputBox);
		mainBox.setVgrow(console, Priority.ALWAYS);
		
		Scene scene = new Scene(mainBox, 750, 600);
		primaryStage.getIcons().add(new Image(GUI.class.getResourceAsStream("/img/onahsa-logo.png")));
		primaryStage.setScene(scene);
		primaryStage.setTitle("Onahsa Text-to-Speach");
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
        launch(args);
    }
	
	private static String readLog() {
		String content = "";
		try {
			Scanner sc = new Scanner(logFile);
			while(sc.hasNextLine()) {
				content += sc.nextLine() + "\n";
			}
			sc.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return content;
	}
	
	private void updateLog() {
		
		final Runnable updater = () -> {
			Platform.runLater(() -> {
				double scrollTop = console.getScrollTop();
				console.setText(readLog());
				console.setScrollTop(scrollTop);
				console.positionCaret(console.getText().length());
			});
		};
		scheduler.scheduleAtFixedRate(updater, 10, 10, TimeUnit.MILLISECONDS);
	}
	
	

}
