/*
 * Name: Sean Castillo
 * Class: CS141 Java
 * Teacher: Jankly
 * Assignment: Final Project
 * Date: 11/30/16
 */
package cs141_final_project_sean_castillo;

import java.io.Serializable;

public class TextConsole implements Serializable{
	
	// The TextConsole class takes data from the text output buffer and displays it to the screen.
	// I added this class because it will make it easier to port the program later to use
	// something other than System.out.print to display the program's outputs.
	
	// I can just alter this class to display in a JPanel or some other output using the 
	// string buffer that is managed by this program.
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String buffer;
	
	TextConsole()
	{
		buffer = "";
	}
	
	
	public void flush()
	{
		System.out.print(buffer);
	}
	
	public void clear()
	{
		buffer = "";
	}
	
	public void print(String message)
	{
		buffer = message;
		flush();
	}
	public void println(String message)
	{
		buffer = message + "\n";
		flush();
	}

}
