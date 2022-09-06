import java.util.LinkedList;

//stores the HashEntries
public class HashEntry 
{
	public String word1;			
	public String category;
	public LinkedList<String> follows;
	public int numWordsBetween;
	public int count;
	public int lineNum;
	
	//Stores the entry for a phrase
	public HashEntry(String word1, String category, LinkedList<String> follows, int lineNum)
	{
		this(word1, category, 0, follows, lineNum);
	}
	 
	//stores the entry for a word entry 
	public HashEntry(String word1, String category, int numWordsBetween, LinkedList<String> follows, int lineNum)
	{
		this.word1 = word1;
		this.category = category;
		this.numWordsBetween = numWordsBetween;
		this.follows = follows;
		this.lineNum = lineNum;
		count = 0;
	}

}
