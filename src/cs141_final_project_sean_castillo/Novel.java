/*
 * Name: Sean Castillo
 * Class: CS141 Java
 * Teacher: Jankly
 * Assignment: Final Project
 * Date: 11/30/16
 */
package cs141_final_project_sean_castillo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Novel extends Book {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Novel(MainClassBookz libManager)
	{
		super(libManager);	
	}
	
	@Override public void generateBook() {
		
		// using a dictionary file of all common English words, generate a random novel.
		String [] dict;
		if(Novel.dictionary != null)
			dict = Novel.dictionary;
		else{
			Novel.loadDictionary();
			dict = Novel.dictionary;
		}
		// now I have an array of all the words I could want, so I will randomly put them together into a novel.
		
		// create title
		int randomWordIndex;
		String curWord;
		int titleLength = ThreadLocalRandom.current().nextInt(1, 5);
		for(int i = 0; i < titleLength; i++)
		{
			randomWordIndex = ThreadLocalRandom.current().nextInt(0, dict.length);
			curWord = capitalizeString(dict[randomWordIndex].toLowerCase());
			if(i != 0) curWord = " " + curWord;
			super.setTitle(super.getTitle() + curWord);
		}
		
		// create novel pages
		final int MINPAGES = 150;
		final int MAXPAGES = 1500;
		final int MAXCHARPERPAGE = 2000;
		int numberOfPages = ThreadLocalRandom.current().nextInt(MINPAGES, MAXPAGES + 1);
		ArrayList<String> pages = new ArrayList<String>();
		String curPageText;
		int wordsLeftInSentence = 0;
		int sentencesLeftInParagraph = 0;
		for(int i = 0; i < numberOfPages; i++)
		{
			//super.getConsole().println((i+1) + " / " + numberOfPages);
			
			curPageText = "";
			// create all text for one whole page
			while(curPageText.length() < MAXCHARPERPAGE)
			{
				// create a new word
				randomWordIndex = ThreadLocalRandom.current().nextInt(0, dict.length);
				curWord = dict[randomWordIndex].toLowerCase();
				
				
				// capitalize the word if it's the first word of book
				if(curPageText.length() == 0 && i == 0)
					curWord = capitalizeString(curWord);
				
				// am I starting a new sentence?
				if(wordsLeftInSentence <= 0) 
				{
					// capitalize first letter of new sentence
					curWord = capitalizeString(curWord);
					wordsLeftInSentence =ThreadLocalRandom.current().nextInt(5, 20);
					
					// am i starting a new paragraph?
					if(sentencesLeftInParagraph <= 0)
					{
						// indent new paragraph
						sentencesLeftInParagraph = ThreadLocalRandom.current().nextInt(3, 6);
						curWord = "     " + curWord;
					}
					sentencesLeftInParagraph--;
				}
				
				// add new word to page
				curPageText += curWord;
				wordsLeftInSentence--;
				
				// add a period and space to the end of a sentence
				if(wordsLeftInSentence <= 0)
					curPageText += ". ";
				else // just add a space to the word if it's not the end of a sentence
					curPageText += " ";
				
			}
			super.getPages().add(curPageText);
		}
	}
	
	@Override public void readBook()
	{
		super.readBook();

	}
	
	
	// save the book to library file.
	@Override public void saveBook() 
	{
		super.saveBook();
		super.getLibrary().getNovels().add(this);
	}
	
	public static String capitalizeString(String word)
	{
		return ("" + word.charAt(0)).toUpperCase() + word.substring(1, word.length());
	}
	
	public static String [] dictionary;
	public static void loadDictionary()
	{
		if(Novel.dictionary == null)
		{
			File dictFile = new File("resources/english_dict.txt");
			if(!dictFile.exists())
			{
				System.out.println("Dictionary not found");
				return;
			}
			try {
				
				// count how many words are in the text dictionary so I can allocate for String array
				Scanner f = new Scanner(dictFile);
				int numLines = 0;
				while(f.hasNextLine())
				{
					f.nextLine();
					numLines++;
				}
				
				
				// now that I have the exact number of words in the file, allocate string array of words
				String [] dict = new String [numLines];
				f  = new Scanner(dictFile);
				int k = 0;
				while(f.hasNextLine())
				{
					dict[k++] = f.nextLine();
					numLines++;
				}
				Novel.dictionary = dict;
				//System.out.println(dict.length);
			}
			 catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}			
	}
	
	@Override public String typeOfBook()
	{
		return "Novel";
	}	
	
}
