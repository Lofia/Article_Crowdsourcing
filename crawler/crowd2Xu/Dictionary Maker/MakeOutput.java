import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;

/*Class that creates the output file
 *  Katie Pezzot
 */
public class MakeOutput 
{
	public MakeOutput(String folderName, LinkedList<String> categories, HashTable dictionary, String outType, int yMax, String output) throws IOException
	{
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
	    FileWriter writer = new FileWriter(s + "/output/" + output + ".csv");  //creates file to be written in
	    writer.append("filename" + ',');
		String [] catNames = new String[categories.size()];
		//list categories on first line of text for most output types
		if(!outType.equals("number of occurrences per word"))
		{
			Iterator<String> run = categories.iterator();
			int j = 0;
			while(run.hasNext())
			{
				String title = run.next();
				catNames[j] = title;
				writer.append(title + ',');		//writes to csv file
				j++;
			}
		}
		//list every word/phrase on first line of text for number of occurrences per word output type
		else
		{
			LinkedList<HashEntry> [] table = dictionary.getTable();
			//this loop runs through each row of the table
			for(int i = 0; i < table.length; i++)
			{
				if(table[i] != null)
				{
					Iterator<HashEntry> runTable = table[i].iterator();
					//this loop runs through each entry on each row of the data
					while(runTable.hasNext())
					{
						HashEntry entry = runTable.next();
						writer.append(entry.word1 + ',');		//writes words/phrase csv file
					}
				}
			}
		}
		writer.append("numWord");
		writer.append('\n');	//completion of first line

		File dir = new File(folderName);
		//runs through all files in the specified folder
		for (File file : dir.listFiles())
		{
			if (file.getName().endsWith((".txt"))) 				//only uses text files
			{
				dictionary.clearCounts();
				CountText runsText = new CountText(folderName + "/" + file.getName(), dictionary);		//gets the counts of the words
				int numWords = runsText.numWordsInText();		//the number of words in the text
				int [] catCount = new int[categories.size()];		//stores the counts for the categories
				LinkedList<HashEntry> [] table = dictionary.getTable();			//stores hash table
				boolean [] lineUsed = new boolean [yMax];	//can count up to one value for each line
				writer.append(file.getName() + ',');
				//gets counts for categories
				for(int i = 0; i < table.length; i++)
				{
					if(table[i] != null)
					{
						Iterator<HashEntry> runTable = table[i].iterator();
						while(runTable.hasNext())
						{
							HashEntry entry = runTable.next();
							Iterator<String> search = categories.iterator();
							if(!outType.equals("number of occurrences per word"))
							{
								for(int k = 0; k < catNames.length; k++)
								{
									if(catNames[k].equals(entry.category) && lineUsed[entry.lineNum] == false)
									{
										if(entry.count > 0 && outType.equals("maximum of one instance per line"))
										{
											catCount[k]++;
											lineUsed[entry.lineNum] = true;
										}
										else if(entry.count > 0 && outType.equals("records if category occurred or not"))
											catCount[k] = 1;
										else
											catCount[k] = entry.count + catCount[k];
									}
								}
							}
							else
							{
								String temp = entry.count + "" +',';
								writer.append(temp);
							}
						}
					}
				} 

				if(!outType.equals("number of occurrences per word"))
				{				
					for(int k = 0; k < catCount.length; k++)
					{
						String temp = catCount[k] + "" +',';
						writer.append(temp);
					}
				}
				writer.append(numWords + "");
				writer.append('\n');
			}
		}
	    writer.flush();
		writer.close();
	}
}
