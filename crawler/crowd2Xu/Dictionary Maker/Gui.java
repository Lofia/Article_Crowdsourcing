import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/*Class that creates a gui that the dictionary is built on.
 * 
 * Katie Pezzot
 */

public class Gui 
{
	private int greatestX;						//stores largest xValue
	private int greatestY;						//stores the largest yValue
	private JFrame gui;							//stores the gui
	private JPanel workSpace;					//stores the workspace of the gui
	private GridBagConstraints c;				//the location of the objects 
	private int numXResizes;					// keeps track of the number of times the window was resized horizontally
	private int numYResizes;					// keeps track of the number of times the window was resized vertically
	private String path;						//stores the folder path

	//constructor that sets the initial gui settings
	public Gui()
	{
		path = null;
		numXResizes = 1;
		numYResizes = 1;
		gui = new JFrame();
		workSpace = new JPanel();							//sets up workspace
		workSpace.setPreferredSize(new Dimension(700, 700));		//will be changed as button number increases
		workSpace.setLayout(new GridBagLayout());  
		c = new GridBagConstraints();
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.WEST;
		//creates starting configurations for the GUI
		makeLabel("Dictionary Title: ", 0, 0);
		makeText("Dictionary", 1, 0, 8);
		save();
		load();

		generationType();
		select();

		makeLabel("Category Title: ", 0, 3);
		makeText("Title", 1, 3, 8);

		greatestY= 3;
		greatestX = 3;
		addNew(3);		//adds new input options

		JScrollPane scroll = new JScrollPane(workSpace);		//adds scroll bar and sets up the gui
		gui.setSize(700, 700);	
		gui.add(scroll);
		gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gui.setVisible(true);
	}

	//used in load. Creates empty set up GUI
	Gui(boolean stuff)
	{
		path = null;
		numXResizes = 0;
		numYResizes = 0;
		gui = new JFrame();
		workSpace = new JPanel();							//sets up workspace
		workSpace.setPreferredSize(new Dimension(700, 700));
		workSpace.setLayout(new GridBagLayout());  
		c = new GridBagConstraints();
		c.weighty = 1.0;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.WEST;

		JScrollPane scroll = new JScrollPane(workSpace);		//adds scroll bar and sets up the gui
		gui.setSize(700, 700);	
		gui.add(scroll);
		gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gui.setVisible(true);
	}

	//Adds another category option
	public void addCategory(int yLocation)
	{
		makeLabel("Category Title: ", 0, yLocation);
		makeText("Title", 1, yLocation, 8);
		resizeWorkspace(1, yLocation);

		delete(2, yLocation);			//adds delete and new line options
		if(yLocation == greatestY)
			addNew(yLocation);				//adds new line
	}

	//adds new line with options for line
	public void addNew(int yLocation)
	{
		if(yLocation < greatestY)
			addSpace(yLocation + 1);
		else
			greatestY = yLocation + 1;
		String[] titles = new String[] {"New Category", "New Word", "New Phrase"};		//options for selection box
		JComboBox<String> list = new JComboBox<>(titles);			//makes selection box
		c.gridx = 0;
		c.gridy = yLocation + 1;
		workSpace.add(list, c);			//adds combo box to the screen	
		list.addActionListener(new ActionListener() 		//is activated when item is selected
		{	 
			//if selection is picked, the type of line is made
			@Override
			public void actionPerformed(ActionEvent event)
			{
				JComboBox<String> combo = (JComboBox<String>) event.getSource();
				combo.setVisible(false);
				GridBagLayout layout =  (GridBagLayout) workSpace.getLayout();
				GridBagConstraints gbc = layout.getConstraints(combo);
				String selectedChoice = (String) combo.getSelectedItem();	//creates a new category, phrase, or word depending on the selection
				if (selectedChoice.equals("New Category"))
					addCategory(gbc.gridy);
				else if (selectedChoice.equals("New Phrase"))
					addPhrase(gbc.gridy);
				else if (selectedChoice.equals("New Word"))
					addWord(gbc.gridy); 
			}
		});
	}

	//creates the phrase line
	public void addPhrase(int yLocation)
	{
		makeText("Phrase", 0, yLocation, 20);	

		resizeWorkspace(0, yLocation);	//window can be resized if needed
		delete(1, yLocation);
		if(yLocation == greatestY)	//adds a new line if the phrase line is the bottom most line
			addNew(yLocation);
	}

	//creates the word line
	public void addWord(int yLocation)
	{
		makeText("word", 0, yLocation, 8);
		if(yLocation == greatestY)
			addNew(yLocation);

		String[] titles = new String[] {"or", "within _ words of", "done with line"};
		JComboBox<String> list = new JComboBox<>(titles);			//creates selection box for word line
		c.gridx = 1;
		c.gridy = yLocation;
		workSpace.add(list, c);				
		resizeWorkspace(1, yLocation);
		//actionListener is created to listen for selection and respond with appropriate changes to line
		list.addActionListener(new ActionListener() 
		{	 
			@Override
			public void actionPerformed(ActionEvent event)
			{
				JComboBox<String> combo = (JComboBox<String>) event.getSource();
				GridBagLayout layout =  (GridBagLayout) workSpace.getLayout();
				GridBagConstraints gbc = layout.getConstraints(combo);
				combo.setVisible(false);
				String selectedChoice = (String) combo.getSelectedItem();	//creates the rest of the line depending on the selection
				if (selectedChoice.equals("or"))							
				{
					makeLabel("or", gbc.gridx, gbc.gridy);
					makeText("word", gbc.gridx + 1, gbc.gridy, 8);
					addOr(gbc.gridx + 2, gbc.gridy);
				}
				else if (selectedChoice.equals("within _ words of"))
					addWithin(gbc.gridx, gbc.gridy);
				else if (selectedChoice.equals("done with line"))
					delete(gbc.gridx, gbc.gridy);
			}
		});
	}

	//adds the or and within words option for word lines
	public void addOr(int xLocation, int yLocation)
	{
		String[] titles = new String[] {"or", "within _ words of", "done with line"};
		JComboBox<String> list = new JComboBox<>(titles);		
		c.gridx = xLocation;
		c.gridy = yLocation;
		workSpace.add(list, c);				
		resizeWorkspace(greatestX, greatestY);
		if(greatestX < xLocation)
			greatestX = xLocation;
		list.addActionListener(new ActionListener() 	//responds to selected choice
		{	 
			@Override
			public void actionPerformed(ActionEvent event)
			{
				JComboBox<String> combo = (JComboBox<String>) event.getSource();
				GridBagLayout layout =  (GridBagLayout) workSpace.getLayout();
				GridBagConstraints gbc = layout.getConstraints(combo);
				combo.setVisible(false);
				String selectedChoice = (String) combo.getSelectedItem();	
				//creates another addOr, creates an or, or finishes the line
				if (selectedChoice.equals("or"))
				{
					makeLabel("or", gbc.gridx, gbc.gridy);
					makeText("word", gbc.gridx + 1, gbc.gridy, 8);
					addOr(gbc.gridx + 2, gbc.gridy);
				}
				else if (selectedChoice.equals("within _ words of"))
					addWithin(gbc.gridx, gbc.gridy);
				else if (selectedChoice.equals("done with line"))
					delete(gbc.gridx, gbc.gridy);
			}
		});
	}

	//creates the text boxes and labels for the or option of the word line
	public void addWithin(int xLocation, int yLocation)
	{
		makeLabel("is within", xLocation, yLocation);
		makeText("number", xLocation + 1, yLocation, 8);
		makeLabel("words of", xLocation + 2, yLocation);
		makeText("word", xLocation + 3, yLocation, 8);

		resizeWorkspace(xLocation + 3, yLocation);
		or(xLocation + 4, yLocation);
	}

	//creates the combo box for the or option of the word line
	public void or(int xLocation, int yLocation)
	{
		String[] titles = new String[] {"or", "done with line"};
		JComboBox<String> list = new JComboBox<>(titles);				
		c.gridx = xLocation;
		c.gridy = yLocation;
		workSpace.add(list, c);
		if(greatestX < xLocation)
			greatestX = xLocation;
		resizeWorkspace(xLocation, yLocation);
		list.addActionListener(new ActionListener() //responds to selection
		{	 
			@Override
			public void actionPerformed(ActionEvent event)
			{
				JComboBox<String> combo = (JComboBox<String>) event.getSource();
				GridBagLayout layout =  (GridBagLayout) workSpace.getLayout();
				GridBagConstraints gbc = layout.getConstraints(combo);
				combo.setVisible(false);
				String selectedChoice = (String) combo.getSelectedItem();
				if (selectedChoice.equals("or"))							//creates another or part of the line
				{
					makeLabel("or", gbc.gridx, gbc.gridy);
					makeText("word", gbc.gridx + 1, gbc.gridy, 8);
					resizeWorkspace(gbc.gridx + 1, gbc.gridy);
					or(gbc.gridx + 2, gbc.gridy);
				}
				else if(selectedChoice.equals("done with line"))		//finishes the line
					delete(gbc.gridx, gbc.gridy);
			}
		});
	}

	//makes the workspace larger if necessary
	public void resizeWorkspace(int xLocation, int yLocation)
	{
		if(xLocation >= 3 * numXResizes)	//resizes every 3 x values
		{
			numXResizes = numXResizes + 1;
			workSpace.setPreferredSize(new Dimension(700 * numXResizes, 600 * numYResizes));
			workSpace.revalidate();		//makes change visible immediately
		}
		if(yLocation >= 10 * numYResizes)		//resizes every 10 y values
		{
			numYResizes = numYResizes + 1;
			workSpace.setPreferredSize(new Dimension(700 * numXResizes, 600 * numYResizes));
			workSpace.revalidate();		//makes change visible immediately
		}
	}

	//adds a button to delete the specified line. Also calls addLine to create a button to add a new line
	public void delete(int xLocation, int yLocation)
	{
		addLine(xLocation + 1, yLocation);
		JButton delete = new JButton("Delete Line");
		c.gridx = xLocation;
		c.gridy = yLocation;
		workSpace.add(delete, c);
		resizeWorkspace(xLocation, yLocation);
		delete.addActionListener(new ActionListener() 
		{	 
			@Override
			public void actionPerformed(ActionEvent event)	
			//if delete button is selected every item in the line is set invisible and appropriate components are shifted up
			{
				greatestY = greatestY -1;
				JButton combo = (JButton) event.getSource();
				GridBagLayout layout =  (GridBagLayout) workSpace.getLayout();
				GridBagConstraints button = layout.getConstraints(combo);
				combo.setVisible(false);
				Component[] comps = workSpace.getComponents();
				for (int i = 0; i < comps.length; ++i)
				{
					Component comp = comps[i];
					GridBagConstraints gbc = layout.getConstraints(comp);
					if(button.gridy < gbc.gridy)		//shifts components up
					{
						gbc.gridy = gbc.gridy - 1;
						workSpace.add(comp, gbc);
						workSpace.setPreferredSize(new Dimension(600 * numXResizes, 600 * numYResizes));
						workSpace.revalidate();
					}
					else if(button.gridy == gbc.gridy)		//makes components invisible
						comp.setVisible(false);
				}
			}
		});
	}

	//creates an empty row on the GUI
	public void addSpace(int yLocation)
	{
		greatestY = greatestY + 1;
		Component[] comps = workSpace.getComponents();
		GridBagLayout layout =  (GridBagLayout) workSpace.getLayout();
		for (int i = 0; i < comps.length; ++i)
		{
			Component comp = comps[i];
			GridBagConstraints gbc = layout.getConstraints(comp);
			if(yLocation < gbc.gridy)			//moves all components under the specified row down one row
			{
				gbc.gridy = gbc.gridy + 1;
				workSpace.add(comp, gbc);
				workSpace.setPreferredSize(new Dimension(600 * numXResizes, 600 * numYResizes));
				workSpace.revalidate();
			}
		}
	}

	//creates a new line in the middle of the GUI if the add new Line button is selected.
	public void addLine(int xLocation, int yLocation)
	{
		if(greatestX < xLocation)
			greatestX = xLocation;
		JButton addLine = new JButton("add new Line");
		c.gridx = xLocation;
		c.gridy = yLocation;
		workSpace.add(addLine, c);
		resizeWorkspace(xLocation, greatestY);
		addLine.addActionListener(new ActionListener()
		{	 
			@Override
			public void actionPerformed(ActionEvent event)
			{
				JButton combo = (JButton) event.getSource();
				GridBagLayout layout =  (GridBagLayout) workSpace.getLayout();
				GridBagConstraints button = layout.getConstraints(combo);
				addSpace(button.gridy);		//creates space for new line
				addNew(button.gridy);		//adds the new line
			}
		});
	}

	//generates the document
	public void generate() throws IOException
	{
		Component[] comps = workSpace.getComponents();
		GridBagLayout layout =  (GridBagLayout) workSpace.getLayout();
		Component [][] itemLayout = new Component [greatestX + 1][greatestY + 1];
		//puts visible components into itemLayout based on their location. [2][1] corresponds to x =2, y = 1
		for(int i = 0; i < comps.length - 1; i++)
		{
			if(comps[i].isVisible())
			{
				GridBagConstraints location = layout.getConstraints(comps[i]);
				itemLayout[location.gridx][location.gridy] = comps[i];
			}
		}
		BuildDictionary build = new BuildDictionary(itemLayout);
		HashTable table = build.getTable();
		JLabel type = (JLabel) itemLayout[1][1];
		String genType = type.getText();
		JTextField outputName = (JTextField) itemLayout[1][0];
		String output = outputName.getText();
		MakeOutput run = new MakeOutput(path, build.getCategories(), table, genType, greatestY, output);	//calls MakeOutput to crease the output document
	}

	//creates the options for the generation type
	public void generationType()
	{
		makeLabel("Type of output generation: ", 0, 1);

		Component[] comps = workSpace.getComponents();
		GridBagLayout layout =  (GridBagLayout) workSpace.getLayout();
		//resets the line if a type was previously selected by setting no longer relevant components invisible
		for (int i = 0; i < comps.length; ++i)
		{
			Component comp = comps[i];
			GridBagConstraints gbc = layout.getConstraints(comp);
			if(gbc.gridy == 1 && gbc.gridx > 0)
				comp.setVisible(false);
		}

		String[] titles = new String[] {"number of occurrences in each category", "maximum of one instance per line",
				"records if category occurred or not", "number of occurrences per word"};
		JComboBox<String> list = new JComboBox<>(titles);			//creates selection box for the generation type
		c.gridx = 1;
		c.gridy = 1;
		workSpace.add(list, c);	
		list.addActionListener(new ActionListener() 
		{	 
			@Override
			public void actionPerformed(ActionEvent event)
			{	
				JComboBox<String> combo = (JComboBox<String>) event.getSource();
				combo.setVisible(false);
				String selectedChoice = (String) combo.getSelectedItem();

				makeLabel(selectedChoice, 1, 1);	//displays selected choice
				generateButt();						//creates the generation button
			}

		});
	}

	//creates the generation button
	public void generateButt()
	{
		JButton generate = new JButton("Generate");
		c.gridx = 2;
		c.gridy = 1;
		workSpace.add(generate, c);
		generate.addActionListener(new ActionListener() 
		{	 
			@Override
			public void actionPerformed(ActionEvent event)
			{
				try 
				{
					generate(); 		//generates the output file
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		});

		JButton reset = new JButton("reset output options");	//creates a reset button if the user wants to select a new type of output			
		c.gridx = 3;
		c.gridy = 1;
		workSpace.add(reset, c);
		resizeWorkspace(3, 1);
		reset.addActionListener(new ActionListener() 
		{	 
			@Override
			public void actionPerformed(ActionEvent event)
			{
				generationType();
			}
		});
	}

	//allows the user to select a folder with text files to be read
	public void select()
	{
		Component[] comps = workSpace.getComponents();
		GridBagLayout layout =  (GridBagLayout) workSpace.getLayout();
		//resets line if a folder was previously selected by setting old compnents invisible
		for (int i = 0; i < comps.length; ++i)
		{
			Component comp = comps[i];
			GridBagConstraints gbc = layout.getConstraints(comp);
			if(gbc.gridy == 2 && gbc.gridx >= 0)
				comp.setVisible(false);
		}

		JButton upload = new JButton("Select folder");	//creates the select folder button
		c.gridx = 0;
		c.gridy = 2;
		workSpace.add(upload, c);
		upload.addActionListener(new ActionListener() //if button is selected a file selection window opens
		{	 
			//if selection is picked, the type of line is made
			@Override
			public void actionPerformed(ActionEvent event)
			{
				JPanel filePanel = new JPanel();
				JButton combo = (JButton) event.getSource();
				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new java.io.File("."));
				file.setDialogTitle("choosertitle");
				file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				file.setAcceptAllFileFilterUsed(false);
				filePanel.add(file);
				if (file.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
					//ifan approved path is selected, path is set and an unselect option is presented	
				{															
					combo.setVisible(false);
					path = file.getSelectedFile().toString();
					makeLabel("File selected", 0, 2);

					JButton reset = new JButton("unselect folder");
					c.gridx = 1;
					c.gridy = 2;
					workSpace.add(reset, c);
					reset.addActionListener(new ActionListener() //resets the select folder line
					{	 
						@Override
						public void actionPerformed(ActionEvent event)
						{
							path = null;
							select();
						}
					});
				} 
			}
		});
	}

	//makes a JLabel
	public void makeLabel(String text, int x, int y)
	{
		JLabel lab = new JLabel(text);
		c.gridx = x;
		c.gridy = y;
		workSpace.add(lab, c);
	}

	//makes a text field
	public void makeText(String text, int x, int y, int size)
	{
		JTextField txt = new JTextField(text, size);
		c.gridx = x;
		c.gridy = y;
		workSpace.add(txt, c);
	}

	//saves the current GUI in the save folder under the title of the dictionary
	public void save()
	{
		JButton save = new JButton("Save");
		c.gridx = 2;
		c.gridy = 0;
		workSpace.add(save, c);
		save.addActionListener(new ActionListener() 
		{	 
			@Override
			public void actionPerformed(ActionEvent event)
			{
				JButton fix = new JButton("not visible");	//this button is created to let the component list update correctly
				fix.setVisible(false);
				workSpace.add(fix, c);
				Component[] comps = workSpace.getComponents();
				GridBagLayout layout =  (GridBagLayout) workSpace.getLayout();
				Component [][] itemLayout = new Component [greatestX + 1][greatestY + 1];
				//gets all visible components
				for(int i = 0; i < comps.length - 1; i++)
				{
					if(comps[i].isVisible())
					{
						GridBagConstraints location = layout.getConstraints(comps[i]);
						itemLayout[location.gridx][location.gridy] = comps[i];
					}
				}
				JTextField outputName = (JTextField) itemLayout[1][0];
				String output = outputName.getText();
				try 
				{
					Save generate = new Save(itemLayout, output);		//generates the save file
				} 
				catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	//Loads a saved GUI
	public void load()
	{
		JButton load = new JButton("load");
		c.gridx = 3;
		c.gridy = 0;
		workSpace.add(load, c);
		load.addActionListener(new ActionListener() 
		{	 
			@Override
			public void actionPerformed(ActionEvent event)
			{
				//makes a selection box for all text files in the save folder
				JPanel panel = new JPanel();
				panel.add(new JLabel("Please make a selection:"));
				DefaultComboBoxModel model = new DefaultComboBoxModel();
				File dir = new File(Paths.get("").toAbsolutePath().toString() + "/save/");
				//runs through all files in the specified folder
				for (File file : dir.listFiles())
				{
					if (file.getName().endsWith((".txt"))) 				//only uses text files
					{
						model.addElement(file.getName());
					}
				}
				JComboBox comboBox = new JComboBox(model);
				panel.add(comboBox);

				//gets the result from the option window
				int result = JOptionPane.showConfirmDialog(null, panel, "Saved File Select", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				switch (result)
				{
				case JOptionPane.OK_OPTION:
					try 						//if file is selected the saved GUI is generated
					{
						Load newWind = new Load(comboBox.getSelectedItem().toString());
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	//creates the formatted GUI
	public static void main(String[] args)
	{
		Gui test = new Gui();
	}
}
