package net.oijon.algonquin.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import com.diogonunes.jcolor.AnsiFormat;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class Console {
	
	public static String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String time = "[" + dateFormat.format(date) + "] ";
		return time;		
	}
	
	public static void run() {
		boolean loop = true;
		String selectedPack = "newclassic";
		String outputName = "output";
		String input;
		boolean debug = true;
		
		AnsiFormat fError = new AnsiFormat(YELLOW_TEXT(), RED_BACK());
		AnsiFormat fWarning = new AnsiFormat(YELLOW_TEXT(), BOLD());
		AnsiFormat fInfo = new AnsiFormat(CYAN_TEXT());
		AnsiFormat fSuccess = new AnsiFormat(GREEN_TEXT());
		
		String error = "[ERROR] ";
		String warning = "[WARNING] ";
		String info = "[INFO] ";
		String success = "[SUCCESS] ";
		
		for (int i = 0; i < 40; i++) {
			System.out.print("#");
		}
		System.out.println();
		
		Scanner userInput = new Scanner(System.in);
		System.out.println(colorize("Welcome to the AlgonquinTTS command line!", fInfo));
		System.out.println(colorize("v0.3.2-SNAPSHOT", fInfo));
		System.out.println(colorize("Type \"help\" for a list of all commands!", fSuccess));
		for (int i = 0; i < 40; i++) {
			System.out.print("#");
		}
		System.out.println();
		
		while(loop) {
			System.out.print(">");
			String command[] = parse(userInput.nextLine(), debug);
			
			for (int i = 0; i < command.length; i++) {
				command[i].toLowerCase();
			}
			
			if (command[0].equals("help")) {
				//Reads out all commands, console only
				System.out.println(colorize(success + getTime() + "List of all commands:", fSuccess));
				try (InputStream in = Console.class.getResourceAsStream("/help.txt"); 
						BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
					String line;
					while ((line = br.readLine()) != null) {
						System.out.println(colorize(info + line, fInfo));
					   }
				} catch (IOException e) {
					System.err.println(colorize(error + getTime() + "Help file unreadable: " + e.toString(), fError));
					e.printStackTrace();
				}
			}
			else if (command[0].equals("copyfiles")) {
				try {
					System.out.println(colorize(info + getTime() + "Copying default files from jar...", fInfo));
					Functions.copyFiles();
					System.out.println(colorize(success + getTime() + "Files successfully copied!", fSuccess));
				} catch (URISyntaxException | IOException e) {
					System.err.println(colorize(error + getTime() + "Error when creating files! " + e.toString(), fError));
					e.printStackTrace();
				}
			}
			else if (command[0].equals("exit")) {
				System.out.println(colorize(info + getTime() + "Exiting...", fInfo));
				loop = false;
			}
			else if (command[0].equals("getpacks")) {
				String[] packs = Functions.getPacks();
				System.out.println(colorize(info + getTime() + "Installed soundpacks:", fInfo));
				for (int i = 0; i < packs.length; i++) {
					System.out.println(colorize(info + "Pack " + (i + 1) + " - " + packs[i], fInfo));
				}
			}
			else if (command[0].equals("printcommand")) {
				System.out.println(colorize(info + "This is a debug command!"));
				for (int i = 0; i < command.length; i++) {
					System.out.println(colorize(info + "Argument " + i + ": " + command[i], info));
				}
			}
			else if (command[0].equals("pronounce")) {
				
				String pronounceString = "";
				
				if (command.length > 2) {
					//Gets the string being pronounced
					for (int i = 1; i < command.length; i++) {
						if (command[i].charAt(0) != '-') {
							pronounceString += command[i] + " ";
						}
					}
					System.out.println(pronounceString);
					Functions.pronounce(selectedPack, pronounceString, outputName);
				} else {
					System.err.println(colorize(getTime() + "No input given to pronounce!", fError));
					System.out.println(colorize(getTime() + "Usage: pronounce [IPA string]", fInfo));
				}
			}
			else if (command[0].equals("selectpack")) {
				if (command.length > 2) {
					selectedPack = Functions.selectPack(command[1]);
				} else {
					System.err.println(colorize(error + getTime() + "No name given to change pack to!", fError));
					System.out.println(colorize(info + getTime() + "Usage: selectpack [pack name]", fInfo));
				}
			}
			else if (command[0].equals("setoutput")) {
				if (command.length > 2) {
					selectedPack = Functions.selectPack(command[1]);
				} else {
					System.err.println(colorize(error + getTime() + "No name given to change pack to!", fError));
					System.out.println(colorize(info + getTime() + "Usage: selectpack [pack name]", fInfo));
				}
			}
			else {
				System.out.println(colorize(error + getTime() + "Unknown command. Use \"help\" to see a list of all commands.", fError));
			}
		}
	}
	
	//Gets parameters and command as two separate things, then return as an ArrayList
	//Structure:
	//0 - main command, used in main if statement (yes i know thats a bad idea but idk what else to do here)
	//1 - non-args, used for inputs
	//2+ - arguments, self-explanatory
	public static String[] parse(String input, boolean debug) {
		String command[] = input.split(" ");
		
		ArrayList<String> output = new ArrayList<String>();
		
		output.add(command[0]); //the command will always be in the front
		//add non-parameters to output
		String nonArgs = "";
		for (int i = 1; i < command.length; i++) {
			if (command[i].charAt(1) != '-') {
				nonArgs += command[i] + " ";
			}
		}
		output.add(nonArgs); //should always be in position 1
		
		//add parameters to output
		for (int i = 0; i < command.length; i++) {
			if (command[i].length() >= 2 && command[i].substring(0, 2) == "--") {
				output.add(command[i] + " " + command[i + 1]);
				//skips the next part as it is part of the argument
				i++;
			} else if (command[i].length() >= 1 && command[i].charAt(0) == '-') {
				output.add(command[i]);
			}
		}
		
		String realOutput[] = new String[output.size()];
		for (int i = 0; i < realOutput.length; i++) {
			realOutput[i] = output.get(i);
		}
		
		return realOutput;
	}
	
}
