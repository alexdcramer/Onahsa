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
		
		for (int i = 0; i < 40; i++) {
			System.out.print("#");
		}
		System.out.println();
		
		Scanner userInput = new Scanner(System.in);
		System.out.println("Welcome to the AlgonquinTTS command line!");
		System.out.println("v0.3.2-SNAPSHOT");
		System.out.println("Type \"help\" for a list of all commands!");
		while(loop) {
			System.out.print(">");
			String command[] = parse(userInput.nextLine(), debug);
			
			for (int i = 0; i < command.length; i++) {
				command[i].toLowerCase();
			}
			
			if (command[0].equals("help")) {
				//Reads out all commands, console only
				System.out.println(getTime() + "List of all commands:");
				try (InputStream in = Console.class.getResourceAsStream("/help.txt"); 
						BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
					String line;
					while ((line = br.readLine()) != null) {
						System.out.println(line);
					   }
				} catch (IOException e) {
					System.err.println(getTime() + "Help file unreadable: " + e.toString());
					e.printStackTrace();
				}
			}
			else if (command[0].equals("copyfiles")) {
				try {
					System.out.println(getTime() + "Copying default files from jar...");
					Functions.copyFiles();
					System.out.println(getTime() + "Files successfully copied!");
				} catch (URISyntaxException | IOException e) {
					System.err.println(getTime() + "Error when creating files! " + e.toString());
					e.printStackTrace();
				}
			}
			else if (command[0].equals("exit")) {
				System.out.println(getTime() + "Exiting...");
				loop = false;
			}
			else if (command[0].equals("getpacks")) {
				String[] packs = Functions.getPacks();
				System.out.println(getTime() + "Installed soundpacks:");
				for (int i = 0; i < packs.length; i++) {
					System.out.println("Pack " + (i + 1) + " - " + packs[i]);
				}
			}
			else if (command[0].equals("pronounce")) {
				
				String pronounceString = "";
				
				if (command.length > 2) {
					//Gets the string being pronounced
					for (int i = 1; i < command.length; i++) {
						pronounceString += command[i] + " ";
					}
					System.out.println(pronounceString);
					Functions.pronounce(selectedPack, pronounceString, outputName);
				} else {
					System.err.println(getTime() + "No input given to pronounce!");
					System.out.println(getTime() + "Usage: pronounce [IPA string]");
				}
			}
			else if (command[0].equals("selectpack")) {
				if (command.length > 2) {
					selectedPack = Functions.selectPack(command[1]);
				} else {
					System.err.println(getTime() + "No name given to change pack to!");
					System.out.println(getTime() + "Usage: selectpack [pack name]");
				}
			}
			else if (command[0].equals("setoutput")) {
				if (command.length > 2) {
					selectedPack = Functions.selectPack(command[1]);
				} else {
					System.err.println(getTime() + "No name given to change pack to!");
					System.out.println(getTime() + "Usage: selectpack [pack name]");
				}
			}
			else {
				System.out.println(getTime() + "Unknown command. Use \"help\" to see a list of all commands.");
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
			if (command[i].charAt(0) == '-') {
				output.add(command[i] + " " + command[i + 1]);
				//skips the next part as it is part of the argument
				i++;
			}
		}
		
		String realOutput[] = new String[output.size()];
		for (int i = 0; i < realOutput.length; i++) {
			realOutput[i] = output.get(i);
		}
		
		return realOutput;
	}
	
}
