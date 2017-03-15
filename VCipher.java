/**
 * Programming AE2
 * Class contains Vigenere cipher and methods to encode and decode a character
 */
public class VCipher
{
	private char [] alphabet;   	//initiliazing alphabet array
	private final int SIZE = 26;	//initialising the size
	
	/** The cipher array. */
	private char [] [] cipher;		//initiliazing cipher array
	
    // more instance variables
	private int rowCounter = 0;		//index position of the row
	private int keyLength = 0;		//setting keyLength to 0
	
	/** 
	 * The constructor generates the cipher
	 * @param keyword the cipher keyword
	 */
	public VCipher(String keyword)
	{
		
		keyLength = keyword.length();	//gets the length of the keyword
		
		//creates alphabets
		System.err.println("\n Alphabet Array");
		alphabet = new char [SIZE];		//setting alphabet array size
		int i;
		
		//This block of code fills the alphabet arrays with all the letters of the alphabet
		for (i = 0; i < SIZE; i++)
		{
			alphabet[i] = (char)('A' + i);			//moves onto the next alphabet each time
				
			System.err.print(" "+alphabet[i]);		//prints the alphabet array
		}
		
		//create first of cipher from keyword
		System.err.println("\n Cipher Array");
		
		cipher = new char [keyLength] [SIZE];		//defines the rows and columns of the cipher array
		
		//This block of code will fill the first part of the cipher with the keyword
		for (int p = 0; p < keyword.length(); p++)
		{
			cipher[p][0] = keyword.charAt(p);		//creates the first part of the keyword array
		}
		
		// create remainder part of cipher from the alphabet array
		int n = 0;								//first index position of the keyword
		while (n < keyword.length())			//checks if the index position is less than the keyword
		{
				char a = cipher[n][0];			//assigns a to the first letter of the keyword
				
				for (int k = 0; k < SIZE; k++)	//runs through all the alphabets
				{
					cipher[n][k] = a++;			//adds remaining alphabets after each keyword 
					
					if(a == 'Z'+ 1)				//checks if alphabet has reached 'Z'
					{
						a = 'A';				//assign character a to 'A'
					}
					
				}
				n++;							//next keyword index
		}
		
		// print cipher array for testing and tutors
		for (int j = 0; j < keyLength; j++)
		{
			for (int k = 0; k < SIZE; k++)
				System.err.print(" "+cipher[j][k]);
			System.err.print("\n");
		}
		
	}
	
	
	
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */	
	public char encode(char ch)
	{
		
		int indexPosition = ch - 'A';			//finds index position from alphabet array
		char encChar = ' ';						//initialising encChar to an empty character
		
		if (rowCounter < keyLength)				//checks if the rowCounter is less than the length of key
		{
			encChar = cipher[rowCounter][indexPosition];	//put the character in the column of the cipher array
			rowCounter++;									//go to the next row
		}
		
		if (rowCounter == keyLength)			//checks if the rowCounter is equal to the keyLength
			rowCounter = 0;						//set the rowCounter to 0
		
	    return encChar;  						// returns the encoded character
	}
	
	
	/**
	 * Decode a character
	 * @param ch the character to be decoded
	 * @return the decoded character
	 */  
	public char decode(char ch)
	{
		char encChar = ' ';						//initialize a character
		
		for (int i = 0; i < SIZE; i++)			//checks through all the elements of the array
		{
			if (rowCounter > keyLength-1)		//checks if the rowCounter is greater than the keyLength
				rowCounter = 0;				
			
			if (ch == cipher[rowCounter][i])	//checks if the character is found in the array
			{
				encChar = alphabet[i];			//gets the letter back from the alphabet array
				rowCounter++;					//goes to the next row of the cipher array
				break;					
			}
		}
		
	    return encChar;  
	}
}
