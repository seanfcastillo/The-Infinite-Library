/*
 * Name: Sean Castillo
 * Class: CS141 Java
 * Teacher: Jankly
 * Assignment: Final Project
 * Date: 11/30/16
 */
package cs141_final_project_sean_castillo;

import java.util.concurrent.ThreadLocalRandom;

public class Comic extends Book {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Comic(MainClassBookz libManager)
	{
		super(libManager);
	}
	
	public void generateBook()
	{
		// using a dictionary file of all common English words
		String [] dict;
		if(Novel.dictionary != null)
			dict = Novel.dictionary;
		else{
			Novel.loadDictionary();
			dict = Novel.dictionary;
		}
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
		
		// create novel pages
		final int MINPAGES = 3;
		final int MAXPAGES = 20;
		final int WIDTH = Book.PAGEWIDTH-1;
		final int HEIGHT = 30;
		int numberOfPages = ThreadLocalRandom.current().nextInt(MINPAGES, MAXPAGES + 1);
		String curPageText;
		char [][] comicPage;
		
		// create all pages of the comic
		for(int i = 0; i < numberOfPages; i++)
		{
			curPageText = "";
			
			// generate an ASCII "image" that will comprise the whole page
			comicPage = generateImage(WIDTH,HEIGHT,10,8,12);
			
			// print the image to the page
			for(int row = 0; row < comicPage.length; row++)
			{
				for(int col = 0; col < comicPage[0].length; col++)
				{
					curPageText += comicPage[row][col];
				}
				
				// a space is added to the end of each line. this will
				// be interpreted as a newline character in the Book.readBook method
				curPageText += " ";
			}
			super.getPages().add(curPageText);
			
			//super.getConsole().println((i+1) + " / " + numberOfPages);
		}
	}
	
	// save the book to library file.
	@Override public void saveBook() 
	{
		super.saveBook();
		super.getLibrary().getComics().add(this);
	}
	
	// generates a cool looking ASCII image at a given width and height 
	public static char [][] generateImage(int width, int height, int maxLines, int maxCircles, int maxRadius)
	{
		char [][] comicPage = new char[height][width];
		final int HEIGHT = height;
		final int WIDTH = width;
		
		
		// bg fill
		for(int row = 0; row < comicPage.length; row++)
		{
			for(int col = 0; col < comicPage[0].length; col++)
			{
				comicPage[row][col] = 66;
			}
		}
		
		// lines
		final int MAXLINES = maxLines;
		final int NUMDIRECTIONS = 4;
		int numLines = ThreadLocalRandom.current().nextInt(0, MAXLINES + 1);
		for(int lineCounter = 0; lineCounter < numLines; lineCounter++)
		{
			int direction = ThreadLocalRandom.current().nextInt(0, NUMDIRECTIONS + 1);
			switch(direction)
			{
			case 1: // vertical
				int lineCol = ThreadLocalRandom.current().nextInt(0, WIDTH);
				for(int row = 0; row < comicPage.length; row++)
				{
					for(int col = 0; col < comicPage[0].length; col++)
					{
						if(col == lineCol)
							comicPage[row][col] = '|';
					}
				}
				break;
			case 2: // horizontal
				int lineRow = ThreadLocalRandom.current().nextInt(0, HEIGHT);
				for(int row = 0; row < comicPage.length; row++)
				{
					for(int col = 0; col < comicPage[0].length; col++)
					{
						if(row == lineRow)
							comicPage[row][col] = '=';
					}
				}
				break;
			case 3: // diagonal left
				int currentCol = ThreadLocalRandom.current().nextInt(2, WIDTH-2);
				for(int row = 0; row < comicPage.length; row++)
				{
					if(currentCol < 0) break;
					comicPage[row][currentCol--] = '-';
				}
				break;
			case 4: // diagonal right
				int currentColR = ThreadLocalRandom.current().nextInt(2, WIDTH-2);
				for(int row = 0; row < comicPage.length; row++)
				{
					if(currentColR > WIDTH-2) break;
					comicPage[row][currentColR++] = '+';
				}
				break;
			}
		}
		
		// circles
		final int MAXCIRCLES = maxCircles;
		final int MAXRADIUS = maxRadius;
		int numCircles = ThreadLocalRandom.current().nextInt(0, MAXCIRCLES + 1);
		for(int circleCount = 0; circleCount < numCircles; circleCount++)
		{
			int radius = ThreadLocalRandom.current().nextInt(1, MAXRADIUS);
			int x = ThreadLocalRandom.current().nextInt(0 - (radius+1), WIDTH-1 + (radius-1));
			int y = ThreadLocalRandom.current().nextInt(0 - (radius+1), HEIGHT-1 + (radius-1));
			
			int fillRad = 3;
			for(int r = 0; r < radius; r++)
			{
				
				if(r == 0 && (x >= 0 && x < WIDTH - 1 && y >= 0 && y < HEIGHT))
					comicPage[y][x] = '*';
				else if (r >= 1)
				{
					if(y+r >= 0 && y+r <= HEIGHT - 1 && x >= 0 && x <= WIDTH-2)
						comicPage[y+r][x] = '*';
					if(y -r >= 0 && y-r <= HEIGHT - 1 && x >= 0 && x <= WIDTH-2)
						comicPage[y-r][x] = '*';
					if(x+r >= 0 && x+r <= WIDTH-2 && y >= 0 && y <= HEIGHT-1)
						comicPage[y][x+r] = '*';
					if(x-r >= 0 && x-r <= WIDTH-2 && y >= 0 && y <= HEIGHT-1)
						comicPage[y][x-r] = '*';
				}
				if (r > 1)
				{
					// fill left col
					for( int fill = 0; fill < fillRad; fill++)
					{
						if(y-r+1 < 0) continue;
						else if (y-r+1 > HEIGHT-1) continue;
						else if(x+fill > WIDTH-2) continue;
						else if(x-fill < 0) continue;
						if(fill <= fill/2)
							comicPage[y-r+1][x+fill] = '*';
						else
							comicPage[y-r+1][x-fill] = '*';
					}
					// fill right col
					for( int fill = 0; fill < fillRad; fill++)
					{
						if(y+r-1 < 0) continue;
						if(y+r-1 > HEIGHT-1) continue;
						else if(x+fill > WIDTH-2) continue;
						else if(x-fill < 0) continue;
						else if(fill <= fill/2)
							comicPage[y+r-1][x+fill] = '*';
						else
							comicPage[y+r-1][x-fill] = '*';
					}
					// fill top row
					for( int fill = 0; fill < fillRad; fill++)
					{
						if(x+r-1 < 0) continue;
						if(x+r-1 > WIDTH-2) continue;
						else if(y+fill > HEIGHT-1) continue;
						else if(y-fill < 0) continue;
						else if(fill <= fill/2)
							comicPage[y+fill][x+r-1] = '*';
						else
							comicPage[y-fill][x+r-1] = '*';
					}
					// fill bot row
					for( int fill = 0; fill < fillRad; fill++)
					{
						if(x-r+1 < 0) continue;
						if(x-r+1 > WIDTH-2) continue;
						else if(y+fill > HEIGHT-1) continue;
						else if(y-fill < 0) continue;
						else if(fill <= fill/2)
							comicPage[y+fill][x-r+1] = '*';
						else
							comicPage[y-fill][x-r+1] = '*';
					}
					
					
					fillRad += 2;
				}
			}
		}
		
		return comicPage;
	}
	
	@Override public String typeOfBook()
	{
		return "Comic";
	}	
}
