/*
 * Name: Sean Castillo
 * Class: CS141 Java
 * Teacher: Jankly
 * Assignment: Final Project
 * Date: 11/30/16
 */
package cs141_final_project_sean_castillo;

import java.io.Serializable;

public class MainClassBookz implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Library library;
	private TextConsole console;
	private String libPath = "library.141";
	
	public static void main(String[] args) {
		
		MainClassBookz libManager = new MainClassBookz();
		libManager.setLibrary(new Library(libManager));
		Menu menu = new Menu(libManager);
		menu.ShowMainOptions();
	}
	
	MainClassBookz()
	{
		console = new TextConsole();
	}
	
	public TextConsole getConsole()
	{
		return console;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public String getLibPath() {
		return libPath;
	}

	public void setLibPath(String libPath) {
		this.libPath = libPath;
	}


}
