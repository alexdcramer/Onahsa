package net.oijon.algonquin.console;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
		Scanner userInput = new Scanner(System.in);
		System.out.println("Welcome to the AlgonquinTTS commandline!");
		System.out.println("v0.3.2-SNAPSHOT");
		System.out.println("Type \"help\" for a list of all commands!");
		while(loop) {
			String command = userInput.nextLine();
			command.toLowerCase();
			
			if (command.equals("help")) {
				System.out.println(getTime() + "List of all commands:");
				System.out.println("copyfiles - Copies over default sound packs from jar.");
				System.out.println("exit - Exits the commandline.");
				System.out.println("getpacks - Returns list of all installed sound packs.");
			}
			else if (command.equals("copyfiles")) {
				try {
					Functions.copyFiles();
				} catch (URISyntaxException | IOException e) {
					System.err.println(getTime() + "Error when creating files! " + e.toString());
					e.printStackTrace();
				}
			}
			else if (command.equals("exit")) {
				System.out.println(getTime() + "Exiting...");
				loop = false;
			}
			else if (command.equals("getpacks")) {
				String[] packs = Functions.getPacks();
				System.out.println(getTime() + "Installed soundpacks:");
				for (int i = 0; i < packs.length; i++) {
					System.out.println("Pack " + (i + 1) + " - " + packs[i]);
				}
			}
			else {
				System.out.println(getTime() + "Unknown command. Use \"help\" to see a list of all commands.");
			}
		}
	}
	
}
