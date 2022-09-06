import java.util.Iterator;
import java.util.LinkedList;

//Creates the HashTable for the dictionary
public class HashTable
{
	private LinkedList<HashEntry> [] table;			//stores the HashTable
	private int numInTable;							//holds the number of items in the table

	//Constructor that sets the default table size to 50
	public HashTable()
	{
		table = new LinkedList[50];
		numInTable = 0;
	}

	//method that inserts a new HashEntry into the table
	public void add(HashEntry entry)
	{
		//creates new LinkedList if array is empty at the desired location
		if(table[Math.abs(entry.word1.hashCode()) % table.length] == null)
			table[Math.abs(entry.word1.hashCode()) % table.length] = new LinkedList<HashEntry>();
		table[Math.abs(entry.word1.hashCode())%table.length].addFirst(entry);		//inserts HashEntry into table
		numInTable++;
	}

	//returns HashEntries for the key
	public LinkedList<HashEntry> getEntries(String key)
	{
		if(table[Math.abs(Math.abs(key.hashCode())) % table.length] != null)	
		{
			Iterator<HashEntry> search = table[Math.abs(key.hashCode())%table.length].iterator();
			LinkedList<HashEntry> entries = new LinkedList<HashEntry>();
			//searches LinkedList for the key at the correct location
			while(search.hasNext() == true)
			{
				HashEntry entry = search.next();
				if(entry.word1.equals(key))		
					entries.add(entry);
			}
			return entries;
		}
		return null;
	}

	//sets all the HashTable counts to zero
	public void clearCounts()
	{
		for(int i = 0; i < table.length; i++)
		{
			if(table[i] != null)
			{
				Iterator<HashEntry> run = table[i].iterator();
				while(run.hasNext())
				{
					run.next().count = 0;
				}
			}
		}
	}
	
	//returns the number of different items in the table
	public int numInTable()
	{
		return numInTable;
	}

	//returns the HashTable
	public LinkedList<HashEntry> [] getTable()
	{
		return table;
	}
}