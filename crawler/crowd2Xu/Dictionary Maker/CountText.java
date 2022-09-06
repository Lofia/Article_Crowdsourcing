import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

//counts the word occurrences in the dictionary
public class CountText 
{
	private ArrayList<String> wordList;		//an arrayList of Strings that holds the normalized word
	private HashTable dictionary;		//stores the dictionary

	//Constructor that takes a filename and converts it into an ArrayList of normalized words
	public CountText(String file, HashTable dictionary) throws IOException
	{
		this.dictionary = dictionary;
		FileReader inputFile = new FileReader(file);
		BufferedReader reader = new BufferedReader(inputFile);
		String line = reader.readLine();			//stores the current file being read 
		wordList = new ArrayList<String>(50);
		//reads the lines in the document and converts them to an ArrayList of words
		while(line != null)
		{
			String[] words = line.split(" ");		//splits the words in the line by spaces
			addWords(words);
			line = reader.readLine();
		}	
		reader.close();
		traverseWords();
	}

	//helper method that takes a String of words, normalizes them, and adds them to the ArrayList
	private void addWords(String [] words)
	{
		///reads the words in the array 
		for(int index = 0; index < words.length; index++)
		{
			String word = normalize(words[index]);

			//if there was no words in the line, nothing is added
			if(word.length() != 0)
				wordList.add(word);
		}
	}

	//Normalizes the words in the text
	private String normalize(String word)
	{
		StringBuilder build = new StringBuilder();			//builds each normalized word
		//reads characters in the words and adds the character when appropriate
		for(int j = 0; j < word.length(); j++)
		{
			//if the letter is lower case it is added to the word
			if((word.charAt(j) <= 'z' && word.charAt(j) >= 'a') || (word.charAt(j) <= '9' && word.charAt(j) >= '0') || word.charAt(j) == '-')
				build.append(word.charAt(j));
			//if the character is upper case it is converted to lower case
			else if(word.charAt(j) <= 'Z' && word.charAt(j) >= 'A')
			{
				build.append((char) (word.charAt(j) - ('A' - 'a')));
			}
		}
		return build.toString();
	}

	//runs through every word in the text incrementing the count when appropriate
	private void traverseWords()
	{
		//runs through all the words in the text 
		for(int index = 0; index < wordList.size(); index++)
		{
			LinkedList<HashEntry> entries = dictionary.getEntries(wordList.get(index));	//gets the hash table value for the location
			if(entries != null && entries.size() != 0)
			{
				Iterator<HashEntry> run = entries.iterator();
				//runs through the entries in the row and increments accordingly.
				while(run.hasNext())
				{
					HashEntry entry = run.next();
					if(entry.numWordsBetween == 0)		//phrase or singular word
					{
						if(entry.follows != null && entry.follows.size() != 0)			//phrase
						{
							Iterator<String> phrase = entry.follows.iterator();	//stores the next words
							boolean found = true;
							int numExt = 1;
							while(found && phrase.hasNext())
							{
								String word = phrase.next();
								if(index + numExt < wordList.size())
								{
									if(!wordList.get(index + numExt).equals(word))
										found = false;
								}
								else
									found = false;
								numExt++;
							}
							if(found)
								entry.count++;								
						}
						else		//singular word
							entry.count++;								 
					}
					else						//word
					{
						boolean found = false;
						for(int i = 0; i <= entry.numWordsBetween; i++)
						{
							if(index + i < wordList.size())
							{
								Iterator<String> between = entry.follows.iterator();	//stores the next words
								while(between.hasNext())
								{
									String word = between.next();
									if(wordList.get(index + i).equals(word))
										found = true;
								}
							}
						}	
						if(found)
							entry.count++;								   
					}
				}
			}
		}
	}
	
	//returns the number of words in the text 
	public int numWordsInText()
	{
		return wordList.size();
	}
}
