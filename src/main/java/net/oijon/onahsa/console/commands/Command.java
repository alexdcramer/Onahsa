package net.oijon.onahsa.console.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import net.oijon.olog.Log;
import net.oijon.onahsa.console.Console;

public abstract class Command {

	String name = "";
	String description = "This command has no description...";
	Log log;
	
	/**
	 * Creates a command with log to which it should send messages
	 * @param log The log to use
	 */
	public Command(Log log) {
		this.log = log;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void execute(String[] args) {
		if (args.length > 1 && args[1].equals("?")) {
			helpPage();
		} else {
			run(args);
		}
	}
	
	public void helpPage() {
		try (InputStream in = Console.class.getResourceAsStream("/helppages/" + name + ".txt"); 
			BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String line;
			while ((line = br.readLine()) != null) {
				log.info(line);
			}
		} catch (IOException e) {
			log.err("Help file unreadable, " + e.toString() + ".");
			e.printStackTrace();
		}
	}
	
	protected String getUserInput() {
		Scanner userInput = new Scanner(System.in);
		String input = userInput.nextLine();
		userInput.close();
		return input;
	}
	
	public abstract void run(String[] args);
	
}
