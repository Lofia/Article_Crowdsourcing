import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class count
{
	public count()
	{
		JFrame gui = new JFrame();
		JPanel workSpace = new JPanel();
		String filename = "";
		JPanel filePanel = new JPanel();
		JFileChooser file = new JFileChooser();
		file.setCurrentDirectory(new java.io.File("."));
		file.setDialogTitle("choosertitle");
		file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		file.setAcceptAllFileFilterUsed(false);
		filePanel.add(file);
		if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			//ifan approved path is selected, path is set and an unselect option is presented	
		{															
			filename = file.getSelectedFile().toString();

		}
		File dir = new File(filename);
		int count = 0;
		for (File files: dir.listFiles())
		{
			if (files.getName().endsWith((".txt"))) 				//only uses text files
			{
				count++;
			}
		}
		System.out.println(count);
	}

	public static void main (String [] args)
	{
		count test = new count();
	}

}
