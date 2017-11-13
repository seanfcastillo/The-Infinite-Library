/*
 * Name: Sean Castillo
 * Class: CS141 Java
 * Teacher: Jankly
 * Assignment: Final Project
 * Date: 11/30/16
 */
package cs141_final_project_sean_castillo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Library implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MainClassBookz libManager;
	
	// containers for book objects
	private ArrayList<Magazine> magazines;
	private ArrayList<Comic> comics;
	private ArrayList<Novel> novels;
	private ArrayList<Poem> poems;
	private String path;
	private TextConsole console;
	
	Library(MainClassBookz libManager)
	{
		this.libManager = libManager;
		
		magazines = new ArrayList<Magazine>();
		comics = new ArrayList<Comic>();
		novels = new ArrayList<Novel>();
		poems = new ArrayList<Poem>();
		
		path = libManager.getLibPath();
		
		loadLibrary(path);
		
	}
	
	public void saveLibrary(String path)
	{
		//.141 FILE TYPE SPECIFICATION
		////////////////////////////////////
		/*
			int - number of magazines
			List<Magazine> - magazine objects
			int - number of comics
			List<Comic> - comic objects
			int - number of novels
			List<Novel> - novel objects
			int - number of poems
			List<Poem> - poem objects
		*/
		//////////////////////////////////////////
		
		try {
			ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream(path));
			
			// save mags
			outFile.writeInt(magazines.size());
			for(Magazine mag : magazines)
			{
				outFile.writeObject(mag);
			}
			// save comics
			outFile.writeInt(comics.size());
			for(Comic comic : comics)
			{
				outFile.writeObject(comic);
			}
			// save novels
			outFile.writeInt(novels.size());
			for(Novel novel : novels)
			{
				outFile.writeObject(novel);
			}
			// save poems
			outFile.writeInt(poems.size());
			for(Poem poem : poems)
			{
				outFile.writeObject(poem);
			}
			
			outFile.close();
	
			
		} 
		catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadLibrary(String path)
	{

		try {
			FileInputStream fileStream = new FileInputStream(path);
			ObjectInputStream inFile = new ObjectInputStream(fileStream);
			
			int count;
			
			// read magazines
			count = inFile.readInt();
			magazines = new ArrayList<Magazine>();
			for(int i = 0; i < count; i++)
				magazines.add( (Magazine)inFile.readObject() );
			
			// read comics
			count = inFile.readInt();
			comics = new ArrayList<Comic>();
			for(int i = 0; i < count; i++)
				comics.add( (Comic)inFile.readObject() );
			
			// read novels
			count = inFile.readInt();
			novels = new ArrayList<Novel>();
			for(int i = 0; i < count; i++)
				novels.add( (Novel)inFile.readObject() );
			
			// read poems
			count = inFile.readInt();
			poems = new ArrayList<Poem>();
			for(int i = 0; i < count; i++)
				poems.add( (Poem)inFile.readObject() );
			
			for(Book b : magazines)
			{
				b.linkConsole(libManager);
				b.setLibrary(this);
			}
			for(Book b : comics)
			{
				b.linkConsole(libManager);
				b.setLibrary(this);
			}
			for(Book b : novels)
			{
				b.linkConsole(libManager);
				b.setLibrary(this);
			}
			for(Book b : poems)
			{
				b.linkConsole(libManager);
				b.setLibrary(this);
			}
			
			libManager.setLibrary(this);
				
			inFile.close();
			
			
		} catch (FileNotFoundException e) {
			
			libManager.getConsole().println("File not found");
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Magazine> getMagazines() {
		return magazines;
	}

	public void setMagazines(ArrayList<Magazine> magazines) {
		this.magazines = magazines;
	}

	public ArrayList<Comic> getComics() {
		return comics;
	}

	public void setComics(ArrayList<Comic> comics) {
		this.comics = comics;
	}

	public ArrayList<Novel> getNovels() {
		return novels;
	}

	public void setNovels(ArrayList<Novel> novels) {
		this.novels = novels;
	}

	public ArrayList<Poem> getPoems() {
		return poems;
	}

	public void setPoems(ArrayList<Poem> poems) {
		this.poems = poems;
	}

}
