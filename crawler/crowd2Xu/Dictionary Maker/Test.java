import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
 
public class Test
{
   public static void main(String [] args)
   {
	   Path currentRelativePath = Paths.get("");
	   String s = currentRelativePath.toAbsolutePath().toString();
	   generateCsvFile(); 
   }
 
   private static void generateCsvFile()
   {
	try
	{
	    FileWriter writer = new FileWriter("test.csv");
 
	    writer.append("DisplayName");
	    writer.append(',');
	    writer.append("Age");
	    writer.append('\n');
 
	    writer.append("MKYONG");
	    writer.append(',');
	    writer.append("26");
            writer.append('\n');
 
	    writer.append("YOUR NAME");
	    writer.append(',');
	    writer.append("29");
	    writer.append('\n');
 
	    //generate whatever data you want
 
	    writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
	     e.printStackTrace();
	} 
    }
}
