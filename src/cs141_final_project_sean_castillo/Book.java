/*
 * Name: Sean Castillo
 * Class: CS141 Java
 * Teacher: Jankly
 * Assignment: Final Project
 * Date: 11/30/16
 */
package cs141_final_project_sean_castillo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Book implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numPages;
	private String title;
	private String isbn; // unique key used in database
	private boolean checkedOut;
	private ArrayList<String> pages;
	private TextConsole console;
	private Library library;
	
	Book(MainClassBookz libManager)
	{
		numPages = 0;
		title = "";
		isbn = Integer.toString(ThreadLocalRandom.current().nextInt(100, 2000));
		checkedOut = false;
		pages = new ArrayList<String>();
		console = libManager.getConsole();
		library = libManager.getLibrary();
	}
	
	public void linkConsole(MainClassBookz libManager)
	{
		console = libManager.getConsole();
	}
	
	public void readBook()
	{
		Scanner s = new Scanner(System.in);
		
		if(checkedOut)
		{
			int suboption = -1;
			do
			{
				suboption = -1;
				String menuText = Menu.menuTop();
				menuText += Menu.menuLine(title + " is already checked out. Sorry!",false);
				menuText += Menu.menuLine();
				menuText += Menu.menuLine("   0) Return to menu",false);
				menuText += Menu.menuLine("   1) Delete",false);
				menuText += Menu.menuBottom();
				   
				console.println(menuText);
				
				//s.nextLine();
				while(suboption == -1)
				   {
					   try{
						   suboption = Integer.parseInt(s.nextLine());
						//s.nextLine();
					   }
					   catch(InputMismatchException e)
					   {
						   suboption = -1;
						   console.println(menuText);
					   }
					   catch(NumberFormatException e)
					   {
						   suboption = -1;
						   console.println(menuText);
					   }
				   } 
				if(suboption == 1)
				{
					if(typeOfBook() == "Novel")
						library.getNovels().remove(this);
					else if(typeOfBook() == "Comic")
						library.getComics().remove((Comic)this);
					else if(typeOfBook() == "Poem")
						library.getPoems().remove(this);
					else if(typeOfBook() == "Magazine")
						library.getMagazines().remove(this);
					suboption = 0;
				}
				//console.println(title + " is already checked out. Sorry!");
				
			} while(suboption != 0);
			return;
		}
		if(getNumPages() <= 0) 
		{
			String menuText = Menu.menuTop();
			menuText += Menu.menuLine(title + " has no pages.",false);
			menuText += Menu.menuLine();
			menuText += Menu.menuLine("Press enter to go back.",false);
			menuText += Menu.menuBottom();
			   
			console.println(menuText);
			
			s.nextLine();
			return;
		}
		
		int curPage = 0;
		int choice = -1;
		boolean tabbed = false;
		
		do{
			choice = -1;
			console.println(title);
			StringBuilder sb = new StringBuilder(pages.get(curPage));
			
			final int maxCharPerLine = PAGEWIDTH;
			int curCharIndex = 0;
			int endLineOffset = 0;
			while(curCharIndex < sb.length())
			{
				if(tabbed)
					endLineOffset = -5;
				else
					endLineOffset = 0;
				while( typeOfBook() == "Novel" && curCharIndex + maxCharPerLine + endLineOffset - 1 < sb.length()  &&
						curCharIndex + maxCharPerLine + endLineOffset - 1 >= 0 &&
						sb.charAt(curCharIndex + maxCharPerLine + endLineOffset - 1) != ' ')
				{
					endLineOffset--;
				}
				for(int i = 0; i < maxCharPerLine + endLineOffset; i++)
				{
					if(curCharIndex < sb.length())
					{
						if(typeOfBook() == "Novel" && curCharIndex + 1 < sb.length() && sb.charAt(curCharIndex) == ' ' && sb.charAt(curCharIndex + 1) == ' ')
						{
							tabbed = true;
							break;
						}
						else tabbed = false;
						console.print("" + sb.charAt(curCharIndex));
						curCharIndex++;
					}
					else break;
				}
				if(typeOfBook() != "Poem")console.print("\n");
				if(typeOfBook() == "Novel" && tabbed){
					while(curCharIndex < sb.length() && sb.charAt(curCharIndex) == ' ')
					{
						console.print("" + sb.charAt(curCharIndex));
						curCharIndex++;
					}
				}
				
			}
			
			console.println("       Page " + (curPage + 1) + " / " + getNumPages());
			console.println("-----------------------------------------------------------");
			console.println("1) Next Page     2) Previous Page    3) Delete     0) Back");
			console.println("------------------------------------------------------------");
			console.println("[Or type any page number to jump there.]");
			
			// handle bad input
		    while(choice == -1)
		    {
			   try{
				   choice = Integer.parseInt(s.nextLine());
				//s.nextLine();
			   }
			   catch(InputMismatchException e)
			   {
				   choice = -1;
			   }
			   catch(NumberFormatException e)
			   {
				   choice = -1;
			   }
		    } 
			
			switch(choice)
			{
			case 0:
				break;
			case 1:
				if(curPage < pages.size() - 1)
					curPage++;
				break;
			case 2:
				if(curPage > 0)
					curPage--;
				break;
			case 3:
				if(typeOfBook() == "Novel")
					library.getNovels().remove(this);
				else if(typeOfBook() == "Comic")
					library.getComics().remove(this);
				else if(typeOfBook() == "Poem")
					library.getPoems().remove(this);
				else if(typeOfBook() == "Magazine")
					library.getMagazines().remove(this);
				choice = 0;
				break;
			default:
				if(choice > 2 && choice <= getNumPages())
				{
					curPage = choice - 1;
				}
				break;
			}
			
		} while (choice != 0);
		
	}
	
	// save the book to library file.
	public void saveBook()
	{
		// random chance for book to be checked out
		final int CHANCECHECKOUT = 3;
		if(ThreadLocalRandom.current().nextInt(0,CHANCECHECKOUT) == CHANCECHECKOUT-1)
			checkedOut = true;
	}
	
	// this will fill the pages of the book with words and use the given border style. 
	// if it already has pages, they will be overwritten.
	public void generateBook()
	{

	}

	// GETTERS AND SETTERS
	public int getNumPages() {
		return pages.size();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public boolean isCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
	}

	public ArrayList<String> getPages() {
		return pages;
	}

	public void setPages(ArrayList<String> pages) {
		this.pages = pages;
	}

	public TextConsole getConsole() {
		return console;
	}

	public void setConsole(TextConsole console) {
		this.console = console;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}
	
	public String typeOfBook()
	{
		return "Book";
	}
	
	public static final int PAGEWIDTH = 80;

}
