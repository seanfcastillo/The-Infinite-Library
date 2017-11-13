/*
 * Name: Sean Castillo
 * Class: CS141 Java
 * Teacher: Jankly
 * Assignment: Final Project
 * Date: 11/30/16
 */
package cs141_final_project_sean_castillo;

import java.util.concurrent.ThreadLocalRandom;

public class Poem extends Book {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Poem(MainClassBookz libManager)
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
		// now I have an array of all the words I could want, so I will randomly put them together into a poem.
		
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
		
		// create poem pages
		final int MINPAGES = 1;
		final int MAXPAGES = 2;
		final int MAXWORDSPERLINE = 5;
		final int MAXLINES = 15;
		int curLine = 0;
		int numberOfPages = ThreadLocalRandom.current().nextInt(MINPAGES, MAXPAGES + 1);

		String curPageText;
		for(int i = 0; i < numberOfPages; i++)
		{
			//super.getConsole().println((i+1) + " / " + numberOfPages);
			
			curPageText = "";
			while(curLine < MAXLINES)
			{
				// determine how many words will be in this line and generate them
				int wordsInThisLine = ThreadLocalRandom.current().nextInt(1, MAXWORDSPERLINE);
				curWord = "";
				for(int k=0;k<wordsInThisLine;k++)
				{
					// generate a new word and add it to the line
					randomWordIndex = ThreadLocalRandom.current().nextInt(0, dict.length);
					if(k == 0)
						curWord += dict[randomWordIndex].toLowerCase();
					else
						curWord += " " + dict[randomWordIndex].toLowerCase();
				}
				
				// create spacing to center the text
				String spacing = "";
				for(int j=0;j<Book.PAGEWIDTH/2-curWord.length()/2;j++)
					spacing += " ";
				
				// add the spacing and words to current line
				curPageText += spacing + curWord;
				
				// should I add a period to this line? random chance
				if(ThreadLocalRandom.current().nextInt(0,3) == 2)
					curPageText += ".\n";
				curPageText += "\n";
				curLine++;
				
			}
			super.getPages().add(curPageText);
			curLine = 0;
		}
	}
	
	@Override public String typeOfBook()
	{
		return "Poem";
	}	
	
	// save the book to library file.
	@Override public void saveBook() 
	{
		super.saveBook();
		super.getLibrary().getPoems().add(this);
	}
	
}
