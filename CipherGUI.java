import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;


/** 
 * Programming AE2
 * Class to display cipher GUI and listen for events
 */
public class CipherGUI extends JFrame implements ActionListener
{
	//instance variables which are the components
	private JPanel top, bottom, middle;
	private JButton monoButton, vigenereButton;
	private JTextField keyField, messageField;
	private JLabel keyLabel, messageLabel;

	//application instance variables
	//including the 'core' part of the textFile filename
	//some way of indicating whether encoding or decoding is to be done
	private MonoCipher mcipher;
	private VCipher vcipher;
	private LetterFrequencies lfreq; 
	
	private FileReader reader;
	private PrintWriter writer, writer2;
	
	private String messageString, keyString, createNewFile, freqFile;
	private String p = "P";
	private String c = "C";
	private String d = "D";
	private String f = "F";
	
	
	/**
	 * The constructor adds all the components to the frame
	 */
	public CipherGUI()
	{		
		this.setSize(400,150);
		this.setLocation(750,420);
		this.setTitle("Cipher GUI");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.layoutComponents();
	}
	
	/**
	 * Helper method to add components to the frame
	 */
	public void layoutComponents()
	{
		//top panel is yellow and contains a text field of 10 characters
		top = new JPanel();
		top.setBackground(Color.yellow);
		keyLabel = new JLabel("Keyword : ");
		top.add(keyLabel);
		keyField = new JTextField(10);
		top.add(keyField);
		this.add(top,BorderLayout.NORTH);
		
		//middle panel is yellow and contains a text field of 10 characters
		middle = new JPanel();
		middle.setBackground(Color.yellow);
		messageLabel = new JLabel("Message file : ");
		middle.add(messageLabel);
		messageField = new JTextField(10);
		middle.add(messageField);
		this.add(middle,BorderLayout.CENTER);
		
		//bottom panel is green and contains 2 buttons
		
		bottom = new JPanel();
		bottom.setBackground(Color.green);
		//create mono button and add it to the top panel
		monoButton = new JButton("Process Mono Cipher");
		monoButton.addActionListener(this);
		bottom.add(monoButton);
		//create vigenere button and add it to the top panel
		vigenereButton = new JButton("Process Vigenere Cipher");
		vigenereButton.addActionListener(this);
		bottom.add(vigenereButton);
		//add the top panel
		this.add(bottom,BorderLayout.SOUTH);
	}
	
	/**
	 * Listen for and react to button press events
	 * (use helper methods below)
	 * @param e the event
	 */
	public void actionPerformed(ActionEvent e)
	{
	    /* This block of code will check whether mono or vigenere button is pressed. If a 
	     * button is pressed, then the program will check if the keyword and the filename is valid.
	     * If they are both valid then the keyword will be passed to the relevant class and a cipher
	     * will be created. Also a letter frequency report will be generated. The program will terminate
	     * after a successfull encryption/decryption. 
	     */
		if (e.getSource() == monoButton)
		{
			if (getKeyword() == true && processFileName() == true)
			{
				
				mcipher = new MonoCipher(keyString);	//passes keyword into monocipher
				lfreq = new LetterFrequencies();		//creates letterFrequency object
				processFile(false);						
				JOptionPane.showMessageDialog(null, "Successfully Processed", 
						"Process Status", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);							
			}
		}
		else if (e.getSource() == vigenereButton)
		{
			if (getKeyword() == true && processFileName() == true)
			{
				vcipher = new VCipher(keyString);		//passes the keyword into vcipher
				lfreq = new LetterFrequencies();		//creates letterFrequency object
				processFile(true);						
				JOptionPane.showMessageDialog(null, "Successfully Processed", 
						"Process Status", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);							
			}
		}
	}
	
	/** 
	 * Obtains cipher keyword
	 * If the keyword is invalid, a message is produced
	 * @return whether a valid keyword was entered
	 */
	private boolean getKeyword()
	{
		keyString = keyField.getText();				//converts the textField to string
				
		//Checks if the keyField is empty. It will throw an error if it is empty.
		if (keyString.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Keyword is Empty!!!", 
					"KEYWORD ERROR", JOptionPane.ERROR_MESSAGE);
			keyField.setText("");					//clears keyword field
			keyField.grabFocus();					//puts the cursor back to the keyword field
			return false;				
		}
		
		//checks if it contains invalid characters and throws errors if an invalid character is found
		else if(!keyString.matches("[a-zA-Z]+"))	
		{
			//Print error message  
			JOptionPane.showMessageDialog(null, "Please Enter A Valid Keyword", 
					"KEYWORD ERROR", JOptionPane.ERROR_MESSAGE);
			keyField.setText("");				
			keyField.grabFocus();					
			return false;							
		}
		else 
		
			//Checks if the entered keyword is all in upperCase or not
			if (!keyString.toUpperCase().equals(keyString))
			{
				JOptionPane.showMessageDialog(null, "Please Enter All Letters in Uppercase", 
						"KEYWORD ERROR", JOptionPane.ERROR_MESSAGE);
				keyField.setText("");								
				keyField.grabFocus();								
				return false;										
			}
			else
			{
				//Checks all the index of the keyword
				for(int i = 0; i < keyString.length(); i++)
				{
					char indexChar = keyString.charAt(i);			//returns char value at specified index
					int first = keyString.indexOf(indexChar);		//index of the indexChar of the first occurrence of the specified char
					int last = keyString.lastIndexOf(indexChar);	//index of the indexChar of the last occurrence of the specified char
					
					//checks if the first index is not equal to the last index
					if (first != last)								
					{
						JOptionPane.showMessageDialog(null, "You Have Repeated a Letter", 
								"KEYWORD ERROR", JOptionPane.ERROR_MESSAGE);
						keyField.setText("");						
						keyField.grabFocus();						
						return false;								
					}
				}
			}
		
	    return true;											
	}
	
	
	//This method checks if the last letter of the file name contains a letter "P"
	private boolean validateLetterP()
	{
		//gets last letter of the file name
		char lastIndex = messageString.charAt(messageString.length() - 1);
		
		char letterP = p.charAt(0);		//gets letter P
		
		if (lastIndex == letterP)		//if the last letter is same as letter P
			return true;				//it contains P
		
		return false;
	}
	
	
	//This method checks if the last letter of the file name contains a letter "C"
	private boolean validateLetterC()
	{
		//gets last letter of the file name
		char lastIndex = messageString.charAt(messageString.length() - 1);	
		
		char letterC = c.charAt(0);		//gets letter C
		
		if (lastIndex == letterC)		//if the last letter is same as letter C
			return true;				//it contains C
		
		return false;
	}
	
	
	/** 
	 * Obtains filename from GUI
	 * The details of the filename and the type of coding are extracted
	 * If the filename is invalid, a message is produced 
	 * The details obtained from the filename must be remembered
	 * @return whether a valid filename was entered
	 */
	private boolean processFileName()
	{
		messageString = messageField.getText();	//converts the messageField to string
		
		//Checks if the keyField is empty. If it is then throw an error.
		if (messageString.equals(""))	 
		{
			JOptionPane.showMessageDialog(null, "FileName is Empty!!!", 
					"FILENAME ERROR", JOptionPane.ERROR_MESSAGE);
			messageField.setText("");		
			messageField.grabFocus();		
			return false;				
		}
		
		//checks if the message file field does not contains P or C 
		else if (validateLetterP() == false && validateLetterC() == false)
		{
			JOptionPane.showMessageDialog(null, "Please Enter FileName Ending With P or C", 
					"FILENAME ERROR", JOptionPane.ERROR_MESSAGE);
			messageField.setText("");		
			messageField.grabFocus();		
			return false;
		}
		
		
	    try
	    {
	    	reader = new FileReader(messageString+".txt");	//opens the text file for reading
	    }
	    catch(IOException x)
	    {
			JOptionPane.showMessageDialog(null, "File Not Found!!!", 
					"FILENAME ERROR", JOptionPane.ERROR_MESSAGE);
	    	messageField.setText("");	
			messageField.grabFocus();	
			return false;
	    }
	    return true;
	}
	
	//This method creates an encryption/decryption file by checking the last letter
	//of the input file.
	private void createFile()
	{
		int i = messageString.lastIndexOf(p);	//gets the index position of letter P
		int j = messageString.lastIndexOf(c);	//gets the index position of letter C

		//Checks if letter P is present in the filename
		if (validateLetterP() == true)
		{
			if (i == -1)									//if index is not found
				System.err.println("Not found");
			else
			{
				String b = messageString.substring(0, i);	//remove the letter P
				createNewFile = b + c;						//adds C to the EOF name
				freqFile = b + f;
				
			}
		}
		
		//Checks if letter C is present in the filename
		else if (validateLetterC() == true)
		{
			if (j == -1)
				System.err.println("Not found");
			else
			{
				String b = messageString.substring(0, j);	//remove the letter C
				createNewFile = b + d;						//adds D to the EOF name
				freqFile = b + f;
			}
		}
	}
	
	/** 
	 * Reads the input text file character by character
	 * Each character is encoded or decoded as appropriate
	 * and written to the output text file
	 * @param vigenere whether the encoding is Vigenere (true) or Mono (false)
	 * @return whether the I/O operations were successful
	 */
	private boolean processFile(boolean vigenere)
	{
		try
		{
			//read from plain text file
			reader = new FileReader(messageString+".txt");	//open the textFile with .txt
			
			createFile();		//creates the encoded/decoded file depending on the last letter
			
			//write to plain text file
			writer = new PrintWriter(createNewFile+".txt");	//create a new output file
			writer2 = new PrintWriter(freqFile+".txt");		//creates frequency file
			
			//reads character by character
			int letter;
			char e = ' ';
			
			/*
			 * This block of code will read each character by character in the specified file and 
			 * carry on reading till the end of the file. If the vigenere is false then the program
			 * will process a mono cipher otherwise will process a vigenere cipher if vignere is true.
			 * The frequency letter report is generated as each encoded/decoded is returned back.
			 */
			while ((letter = reader.read()) != -1)			
			{
				 char d = (char) letter;					//converts each letter to a character
				 String s = Character.toString(d);			
				 
				 if (vigenere == false)						
				 {
					 if(!s.matches("[A-Z]+"))				//checks if a non character is found
					 {
						 writer.write(d);					//writes the non character to the textFile
					 }
					 else if (validateLetterP() == true)	//checks if last letter is "P"
					 {
					 	 e = mcipher.encode(d);				//get the encoded character back
					 	 writer.write(e);					//write the encoded letter to a text file
					 	 lfreq.addChar(e);					//pass encoded character to letterFrequency class
					 	 
					 }
					 else if (validateLetterC() == true)	//checks if last letter is "C"
					 {
						 e = mcipher.decode(d);				//get the encoded character back
					 	 writer.write(e);					//write the encoded letter to a text file
					 	 lfreq.addChar(e);					//pass decoded character to letterFrequency class
					 }
				 }
				 else if(vigenere == true)					
				 {
					 if(!s.matches("[A-Z]+"))				//checks if a non character is found
					 {
						 writer.write(d);					//writes the non character to the textFile
					 }
					 else if (validateLetterP() == true)	//checks if last letter is "P"
					 {			
					 	 e = vcipher.encode(d);				//get the encoded character back
					 	 writer.write(e);					//write the encoded letter to a text file
					 	 lfreq.addChar(e);					//pass encoded character to letterFrequency class
					 	 
					 }
					 else if (validateLetterC() == true)	//checks if last letter is "C"
					 {			
						 e = vcipher.decode(d);				//gets the decoded character back
					 	 writer.write(e);					//write the encoded letter to a text file
					 	 lfreq.addChar(e);					//pass decoded character to letterFrequency class
					 }
				 }
			}
			writer2.write(lfreq.getReport());		//write the report to letterFreq file
			writer.close();							//close the writer 
			writer2.close();						//close the letterFreq file
			reader.close();							//close the reader after done
		}
		catch (IOException e) 
		{
			System.err.println("File Problem");
		}
		
	    return vigenere; 
	}
}
