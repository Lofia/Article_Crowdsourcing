import java.awt.Component;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JTextField;

//Class that constructs the dictionary from the GUI 
public class BuildDictionary 
{
	private HashTable table;								//stores the hash table
	private LinkedList<String> categories;					//stores the categories of the dictionary

	public BuildDictionary(Component [][] itemLayout)
	{
		int x = 0;
		int y = 3;		//starts at beginning of dictionary
		String category = "";
		table = new HashTable();
		categories = new LinkedList<String>();
		//runs through each horizontal line of components and adds them to the dictionary
		while(y < itemLayout[0].length)
		{
			if(itemLayout[x][y] == null)			//if nothing is there, resets the x value
				x = 0;
			else if(itemLayout[x][y] instanceof JLabel)		//is a category 
			{
				x = x + 1;
				JTextField categ = (JTextField) itemLayout[x][y];
				category = categ.getText();
				categories.add(category);		//adds category title to category list
			}
			else if(itemLayout[x][y] instanceof JTextField)		//is a phrase or word
			{
				JTextField word = (JTextField) itemLayout[x][y];
				if(word.getColumns() == 20)	//is a phrase
				{
					String phrase = word.getText();
					StringBuilder build = new StringBuilder(); 
					String word1 = "";
					LinkedList<String> others = new LinkedList<String>(); 	//adds other words of the phrase to the other list
					boolean firstFound = false;
					// runs through the phrase, finds the first word and adds the others to a list 
					for(int i = 0; i < phrase.length(); i++)
					{
						if(phrase.charAt(i) != ' ' && i != phrase.length() -1)		//adds a letter to the building string
							build.append(phrase.charAt(i));
						else if(!firstFound)				//adds the last letter of the first word and stores the first word
						{
							if(i == phrase.length() -1)
								build.append(phrase.charAt(i));
							word1 = build.toString();
							word1 = normalize(word1);
							firstFound = true;
							build = new StringBuilder(); 
						}
						else									//adds the last letter of another word and adds the word to the others list
						{
							if(i == phrase.length() -1)
								build.append(phrase.charAt(i));
							others.add(normalize(build.toString()));
							build = new StringBuilder(); 
						}
					}
					table.add(new HashEntry(word1, category, others, y));			//adds a new phrase HashEntry	
				}
				else if(word.getColumns() == 8)	//is a word
				{
					LinkedList<String> words = new LinkedList<String>();			//stores all the first words
					words.add(word.getText());
					int numBet = 0;
					LinkedList<String> others = new LinkedList<String>();			//stores all the extension words
					x++;
					if(itemLayout[x][y] instanceof JLabel)		//if not end of line
					{
						JLabel filler = (JLabel) itemLayout[x][y];
						boolean isDone = false;
						//runs through or list adding words to first words list. stops when or is done
						while(filler.getText().equals("or") && !isDone)
						{
							x++;
							JTextField text = (JTextField) itemLayout[x][y];
							words.add(text.getText());
							x++;
							if(itemLayout[x][y] instanceof JLabel)
								filler = (JLabel) itemLayout[x][y];
							else
								isDone = true;
						}
						if(!isDone)		//if there is a within_ words of
						{
							x++;
							JTextField num = (JTextField) itemLayout[x][y];
							String number = num.getText();
							boolean isNum = true;
							int i = 0;
							//runs through the number provided. If it is not a number,a default is used 
							while(i < number.length() && isNum)
							{
								if(!(number.charAt(i) >= '0' && number.charAt(i) <= '9') && !number.equals("0"))
								{
									System.out.println("Not a number. Default value 1 is used.");
									isNum = false;
								}
								i++;
							}
							if(!isNum || number.equals("0"))		//sets the default
								numBet = 1;
							else
								numBet = Integer.parseInt(number);
							x = x + 2;
							others = new LinkedList<String>();
							JTextField ext = (JTextField) itemLayout[x][y];
							others.add(ext.getText());
							x++;
							//adds the other words to the extension list
							while(itemLayout[x][y] instanceof JLabel)
							{
								x++;
								ext = (JTextField) itemLayout[x][y];
								others.add(ext.getText());
								x++;
							}
						}
					}
					Iterator<String> run = words.iterator();
					//runs through all the first words and adds them to the HashTable
					while(run.hasNext())
					{
						table.add(new HashEntry(run.next(), category, numBet, others, y));			
					}	
				}
			}
			y++;
			x = 0;
		}
	}
	
	//returns the HashTable
	public HashTable getTable()
	{
		return table;
	}
	
	//returns the category names
	public LinkedList<String> getCategories()
	{
		return categories;
	}
	
	//normalizes the words in the dictionary
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
}
