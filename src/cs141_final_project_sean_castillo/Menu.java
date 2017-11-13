/*
 * Name: Sean Castillo
 * Class: CS141 Java
 * Teacher: Jankly
 * Assignment: Final Project
 * Date: 11/30/16
 */

package cs141_final_project_sean_castillo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

	MainClassBookz libManager;
	// output vars
	private TextConsole console;
	private String menuText;
	final static int MENUWIDTH = 80;
	
	Menu(MainClassBookz libManager)
	{
		this.libManager = libManager;
		this.console = libManager.getConsole();
	}
	// Show the menu loop
	public void ShowMainOptions()
	{
		//console = new TextConsole();
		
		Scanner s = new Scanner(System.in);
		int option = -1;
		do
		{		
			option = -1;
			menuText = "";
			menuText += menuTop();
		    menuText += menuLine("Welcome to the library!",false);
		    menuText += menuLine();
		    menuText += menuLine("Please choose an option.",false);
		    menuText += menuLine("       1) Browse Library",false);
		    menuText += menuLine("       2) Discover New Book",false);
		    menuText += menuLine("       3) Leave the Library and Save",false);
		    menuText += menuBottom();
		    console.println(menuText);
		   
		   // handle bad input
		   while(option == -1)
		   {
			   try{
				option = Integer.parseInt(s.nextLine());
				//s.nextLine();
			   }
			   catch(InputMismatchException e)
			   {
				   option = -1;
				   console.println(menuText);
			   }
			   catch(NumberFormatException e)
			   {
				   option = -1;
				   console.println(menuText);
			   }
		   } 
		   
			switch(option)
			{
			case 1:
				ShowBrowseOptions();
				break;
			case 2:
				showGenerateOptions();
				break;
			case 3:
				console.println("Goodbye.");
				libManager.getLibrary().saveLibrary(libManager.getLibPath());
				s.close();
				System.exit(0);
				break;
			}
		}
		while(option != 3);
		s.close();
	}
	
	// Show browse options
	public void ShowBrowseOptions()
	{
		Scanner s = new Scanner(System.in);
		int option = -1;
		do
		{	
			option = -1;
			menuText = "";
			menuText += menuTop();
			menuText += menuLine("Browse Library",false);
			menuText += menuLine();
			menuText += menuLine("What section would you like?",false);
			menuText += menuLine("       0) Back to Entrance",false);
			menuText += menuLine("       1) Magazine Rack",false);
			menuText += menuLine("       2) Comic Books",false);
			menuText += menuLine("       3) Novels",false);
			menuText += menuLine("       4) Poems",false);
			menuText += menuBottom();
			console.println(menuText);
			
			// handle bad input
			while(option == -1)
			{
			   try{
				   option = Integer.parseInt(s.nextLine());
				   //s.nextLine();
			   }
			   catch(InputMismatchException e)
			   {
				   option = -1;
				   console.println(menuText);
			   }
			   catch(NumberFormatException e)
			   {
				   option = -1;
				   console.println(menuText);
			   }
			} 
			// handle options
			String header;
			try
			{
				switch(option)
				{
				case 1:
					header = "Magazine Rack";
					showBooks(header,(ArrayList<Book>)(Object)libManager.getLibrary().getMagazines());
					
					break;
				case 2:
					header = "Comic Book Rack";
					showBooks(header,(ArrayList<Book>)(Object)libManager.getLibrary().getComics());
					break;
				case 3:
					header = "Shelves of Novels";
					showBooks(header,(ArrayList<Book>)(Object)libManager.getLibrary().getNovels());
					break;
	
				case 4:
					header = "Shelves of Poems";
					showBooks(header,(ArrayList<Book>)(Object)libManager.getLibrary().getPoems());
					break;
				}
			}
			catch(EmptyLibraryException e)
			{
				menuText = menuTop();
				header = "Browsing Sections";
				menuText += menuLine(header, false);
				console.print(menuText);
				menuText = "";
				   menuText += menuLine();
				   menuText += menuLine("There are no books in this section.",false);
				   menuText += menuLine("Press enter to go back",false);
				   menuText += menuBottom();
				   
				console.println(menuText);
				
				s.nextLine();
			}
			
		}
		while(option != 0);
		
		ShowMainOptions();
	}
	
	// Show generate options
		public void showGenerateOptions()
		{
			String header = "Discover a New Book";
			Scanner s = new Scanner(System.in);
			int option = -1;
			do
			{	
				option = -1;
				menuText = "";
				menuText += menuTop();
				menuText += menuLine(header,false);
				menuText += menuLine();
				menuText += menuLine("What kind of book do you want?",false);
				menuText += menuLine("       0) Back to Entrance",false);
				menuText += menuLine("       1) New Magazine",false);
				menuText += menuLine("       2) New Comic",false);
				menuText += menuLine("       3) New Novel",false);
				menuText += menuLine("       4) New Poem",false);
				menuText += menuBottom();
				console.println(menuText);
				
				// handle bad input
			   while(option == -1)
			   {
				   try{
					option = Integer.parseInt(s.nextLine());
					//s.nextLine();
				   }
				   catch(InputMismatchException e)
				   {
					   option = -1;
					   console.println(menuText);
				   }
				   catch(NumberFormatException e)
				   {
					   option = -1;
					   console.println(menuText);
				   }
			   } 
				
				Book b = new Book(libManager);
				switch(option)
				{
				case 1:
					Magazine m = new Magazine(libManager);
					m.generateBook();
					b = m;
					break;
				case 2:
					Comic c = new Comic(libManager);
					c.generateBook();
					b = c;
					break;
				case 3:
					Novel n = new Novel(libManager);
					n.generateBook();
					b = n;
					break;
				case 4:
					Poem p = new Poem(libManager);
					p.generateBook();
					b = p;
					break;
				}
				
				if(option >= 1 && option <= 4)
				{
					menuText = "";
					   menuText += menuTop();
					   menuText += menuLine("A new " +b.typeOfBook() + " was discovered!",false);
					   menuText += menuLine();
					   menuText += menuLine("It is titled " + b.getTitle(),false);
					   menuText += menuLine("What would you like to do with it?",false);
					   menuText += menuLine("       0) Discard it",false);
					   menuText += menuLine("       1) Save it to the library - you can read it later",false);
					   menuText += menuLine("       2) Save and read it now",false);
					   menuText += menuBottom();
					
					console.println(menuText);
					

					int suboption = -1;
					// handle bad input
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
					
					switch(suboption)
					{
					case 0:
						//console.println(b.typeOfBook() + " discarded. It was not added to your library.");
						header = b.typeOfBook() + " discarded. It was not added to your library.";
						break;
					case 1:
						b.saveBook();
						//console.println(b.typeOfBook() + " saved. " + b.getTitle() + " was added to your library.");
						header = b.typeOfBook() + " saved. It was added to your library.";
						break;
					case 2:
						b.readBook();
						b.saveBook();
						//console.println(b.typeOfBook() + " saved. " + b.getTitle() + " was added to your library.");
						header = b.typeOfBook() + " saved. It was added to your library.";
						
						break;
					}

				}
			}
			while(option != 0);
			
			ShowMainOptions();
			
			s.close();
		}
		
		public void showBooks(String header,ArrayList<Book> books) throws EmptyLibraryException
		{
			EmptyLibraryException emptyEx = new EmptyLibraryException("There are no books in this section.");
			
			Scanner s = new Scanner(System.in);
			int option = -1;
			if(books.size() == 0)
			{
				throw emptyEx;
			}
			
			
			do
			{	
				option = -1;
				menuText = menuTop();
				menuText += menuLine(header, false);
				menuText += menuLine();
				console.print(menuText);
				int count = 0;
				for(Book b : books)
				{
					String titleString = ++count + ") " + b.getTitle();
					menuText = menuLine(titleString,false);
					char checkedOut = ' ';
					if(b.isCheckedOut()) checkedOut = 'X';
					menuText += menuLine("          Checked out: [" + checkedOut + "]",false);
					console.print(menuText);
				}
				menuText = menuLine("0) Back to options",false);
				menuText += menuBottom();
				console.print(menuText);
				
				// handle bad input
			    while(option == -1)
			    {
				   try{
					option = Integer.parseInt(s.nextLine());
				   }
				   catch(InputMismatchException e)
				   {
					   option = -1;
					   console.println(menuText);
				   }
				   catch(NumberFormatException e)
				   {
					   option = -1;
					   console.println(menuText);
				   }
			    } 

				
				if(option != 0 && option <= count)
				{
					books.get(option-1).readBook();
				}
			}
			while(option != 0);
			
			
		}
		
		public static String menuLine(String message, boolean centered)
		{
			String line = "";
			
			String decorationL = "#||";
			String decorationR = "||#";
			String spaces = "";
			for(int i = 0; i < MENUWIDTH - message.length() - (decorationR.length() + decorationL.length()); i++)
				spaces += " ";
			line = decorationL + message + spaces + decorationR;
			
			return line + "\n";
		}
		   
		public static String menuTop()
		{
			String fill = "";
			char fillChar = '#';
			String decoration1 = "   ";
			for(int i = 0; i < MENUWIDTH - decoration1.length()*2;i++)
				fill += fillChar;
			fill = decoration1 + fill + decoration1;
			
			String decorationL = "#//";
			String decorationR = "//#";
			String fill2 = "";
			char fillChar2 = '=';
			for(int i = 0; i < MENUWIDTH - (decorationL.length() + decorationR.length()); i++)
				fill2 += fillChar2;
			fill2 = decorationL + fill2 + decorationR;
			return fill + "\n" + fill2  + "\n";
		}
		public static String menuBottom()
		{
			String fill = "";
			char fillChar = '#';
			String decoration1 = "   ";
			for(int i = 0; i < MENUWIDTH - decoration1.length()*2;i++)
				fill += fillChar;
			fill = decoration1 + fill + decoration1;
			
			String decorationL = "#//";
			String decorationR = "//#";
			String fill2 = "";
			char fillChar2 = '=';
			for(int i = 0; i < MENUWIDTH - (decorationL.length() + decorationR.length()); i++)
				fill2 += fillChar2;
			fill2 = decorationL + fill2 + decorationR;
			return fill2 + "\n" + fill  + "\n";
		}
		public static String menuLine()
		{
			String decorationL = "#||";
			String decorationR = "||#";
			String fill = "";
			char fillChar = '-';
			for(int i = 0; i < MENUWIDTH - (decorationL.length() + decorationR.length());i++)
				fill += fillChar;
			return decorationL + fill + decorationR  + "\n";
		}

}
