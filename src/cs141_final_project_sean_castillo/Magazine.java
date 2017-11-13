/*
 * Name: Sean Castillo
 * Class: CS141 Java
 * Teacher: Jankly
 * Assignment: Final Project
 * Date: 11/30/16
 */
package cs141_final_project_sean_castillo;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Magazine extends Book {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Magazine(MainClassBookz libManager)
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
			curWord = Novel.capitalizeString(dict[randomWordIndex].toLowerCase());
			if(i != 0) curWord = " " + curWord;
			super.setTitle(super.getTitle() + curWord);
		}
		
		// create magazine pages. a magazine is comprised of a block of text on the left
		// and an ASCII "image" on the right
		
		final int minPages = 4;
		final int maxPages = 25;
		final int maxCharsPerPage = 2000;
		int numberOfPages = ThreadLocalRandom.current().nextInt(minPages, maxPages + 1);
		String curWordsOfPage;
		String curPageCharacters;
		
		// first, generate the text for the left half of magazine
		int wordsLeftInSentence = 0;
		for(int i = 0; i < numberOfPages; i++)
		{
			
			curPageCharacters = "";
			//super.getConsole().println((i+1) + " / " + numberOfPages);
			
			curWordsOfPage = "";
			while(curWordsOfPage.length() < maxCharsPerPage)
			{
				randomWordIndex = ThreadLocalRandom.current().nextInt(0, dict.length);
				curWord = dict[randomWordIndex].toLowerCase();
				
				
				// capitalize first word of book
				if(curWordsOfPage.length() == 0 && i == 0)
					curWord = Novel.capitalizeString(curWord);
				
				// am I starting a new sentence?
				if(wordsLeftInSentence <= 0) 
				{
					curWord = Novel.capitalizeString(curWord);
					wordsLeftInSentence =ThreadLocalRandom.current().nextInt(5, 20);
				}
				
				curWordsOfPage += curWord;
				wordsLeftInSentence--;
				
				// add a period to the end of a sentence, or just add a space
				if(wordsLeftInSentence <= 0)
					curWordsOfPage += ". ";
				else
					curWordsOfPage += " ";
				
			}
			
			// next, generate the image for the right half of the magazine
			final int IMGHEIGHT = 30;
			char [][] image = Comic.generateImage(Book.PAGEWIDTH/2, IMGHEIGHT, 10, 10, 10);
			int curRowCharIndex = 0;
			int curRow = 0;
			int curBookIndex = 0;
			int col = 0;
			while(curRow < IMGHEIGHT)
			{
				while(curRowCharIndex < Book.PAGEWIDTH - 1)
				{
					if(curRowCharIndex < Book.PAGEWIDTH /2 && curBookIndex < curWordsOfPage.length())
					{
						curPageCharacters += curWordsOfPage.charAt(curBookIndex++);
						
					}
					else
					{
						int column = curRowCharIndex - Book.PAGEWIDTH/2;
						if(column < Book.PAGEWIDTH - 1)
							curPageCharacters += image[curRow][column]; 
						
					}
					
					curRowCharIndex++;
				}
				if(curPageCharacters.charAt(curPageCharacters.length() - 1) != ' ')
					curPageCharacters += " ";
				curRowCharIndex = 0;
				curRow++;
				
			}	
			
			super.getPages().add(curPageCharacters);
			
		}
	}
	
	// save the magazine to library file.
	@Override public void saveBook() 
	{
		super.saveBook();
		super.getLibrary().getMagazines().add(this);
	}
	@Override public String typeOfBook()
	{
		return "Magazine";
	}		

}
