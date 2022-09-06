import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

//creates the GUI based on the inputed save file
public class Load
{
	public Load(String saveFile) throws IOException
	{
		Gui load = new Gui(false);
		FileReader inputFile = new FileReader(Paths.get("").toAbsolutePath().toString() + "/save/" + saveFile);
		BufferedReader reader = new BufferedReader(inputFile);
		String line = reader.readLine();
		int x = -1;
		int y = -1;
		int count = -1;
		String type = null;
		int width = -1;
		String text = null;
		while(line != null)
		{
			int i = 0;
			while( i < line.length())
			{
				if(line.charAt(i) == 'L' && line.charAt(i-1) == '.')
					type = "JLabel";
				else if(line.charAt(i) == 'T' && line.charAt(i-1) == '.')
					type = "JTextField";
				else if(line.charAt(i) == 'B' && line.charAt(i-1) == '.')
					type = "JButton";
				else if(line.charAt(i) == 'C' && line.charAt(i-1) == '.')
					type = "JCombo";
				else if((line.charAt(i) == '!'))
				{
					i++;
					text = "";
					while(line.charAt(i) != '!')
					{
						text = text + line.charAt(i);
						i++;
					}
				}
				else if(line.charAt(i) >= '0' && line.charAt(i) <= '9')
				{
					String temp = "" + line.charAt(i);
					i++;
					while(line.charAt(i) >= '0' && line.charAt(i) <= '9')
					{
						temp = temp + line.charAt(i);
						i++;
					}
					if(line.charAt(i) == 'c')
						count = Integer.parseInt(temp);
					else if(line.charAt(i) == '!')
						text = temp;
					else if(line.charAt(i) == 'x')
						x = Integer.parseInt(temp);
					else if(line.charAt(i) == 'w')
						width = Integer.parseInt(temp);
					else if(line.charAt(i) == '.')
						y = Integer.parseInt(temp);
				}
				if(line.charAt(i) == '.' && i != 0)
				{
					//make stuff
					if(type.equals("JLabel") && text.equals("File selected"))
						load.select();
					else if(type.equals("JLabel"))
						load.makeLabel(text, x, y);		
					else if(type.equals("JTextField"))
						load.makeText(text, x, y, width);
					else if(type.equals("JCombo"))	//4 = output, 3 = cat or first or, 2 = second or
					{
						if(count == 4)
							load.generationType();
						else if(count == 2)
							load.or(x, y);
						else if(count == 3 && x == 0)
							load.addNew(y);
						else
							load.addOr(x,  y);
					}
					else if(type.equals("JButton"))
					{
						
						if(text.equals("load"))
							load.load();
						else if(text.equals("Save"))
							load.save();
						else if(text.equals("Delete Line"))
							load.delete(x, y);
						else if(text.equals("Select folder"))
							load.select();
						else if(text.equals("Generate"))
							load.generateButt();
					}
				}
				i++;
			}
			line = reader.readLine();
		}
		reader.close();
	}
}
