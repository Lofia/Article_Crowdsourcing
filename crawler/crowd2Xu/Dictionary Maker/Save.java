import java.awt.Component;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

//saves the GUI into a file using the title of the dictionary in the save folder
public class Save 
{
	public Save(Component [][] itemLayout, String fileName) throws FileNotFoundException
	{
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		PrintWriter writer = new PrintWriter(s + "/save/" + fileName + ".txt");
		int i = 0;
		for(int j = 0; j < itemLayout[i].length; j++)
		{
			StringBuilder build = new StringBuilder(".");
			while(i < itemLayout.length && itemLayout[i][j] != null)
			{
				if(itemLayout[i][j] instanceof JLabel)
				{
					JLabel lab = (JLabel) itemLayout[i][j];
					build.append("L !" + lab.getText() + "! " + i + "x " + j + ".");
				}
				else if(itemLayout[i][j] instanceof JTextField)
				{
					JTextField text = (JTextField) itemLayout[i][j];
					build.append("T !" + text.getText() + "! " + text.getColumns() + "w " + i + "x " + j + ".");
				}
				else if(itemLayout[i][j] instanceof JButton)
				{
					JButton text = (JButton) itemLayout[i][j];
					build.append("B !" + text.getText() + "! "  + i + "x " + j + ".");
				}
				else if(itemLayout[i][j] instanceof JComboBox)
				{
					JComboBox<String> text = (JComboBox<String>) itemLayout[i][j];
					build.append("C "  + i + "x " + text.getItemCount() + "c " + j + ".");
				}
				i++;
			}
			i = 0;
			writer.println(build.toString());
		}
		writer.close();
	}

}
