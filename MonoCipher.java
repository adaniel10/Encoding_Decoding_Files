/**
 * Programming AE2
 * Contains monoAlphabetic cipher and methods to encode and decode a character.
 */


public class MonoCipher
{
	/** The size of the alphabet. */
	private final int SIZE = 26;

	/** The alphabet. */
	private char [] alphabet;
	
	/** The cipher array. */
	private char [] cipher;
	

	/**
	 * Instantiates a new mono cipher.
	 * @param keyword the cipher keyword
	 */
	public MonoCipher(String keyword)
	{
		//creates alphabets
		System.err.println("\n Alphabet Array");
		alphabet = new char [SIZE];				//sets the max size of the array
		int i;
		
		//This block of code fills the alphabet arrays with all the letters of the alphabet
		for (i = 0; i < SIZE; i++)
		{
			alphabet[i] = (char)('A' + i);		//moves onto the next alphabet each time
		
			System.err.print(" "+alphabet[i]);	//prints the alphabet array
		}
		
		// create first part of cipher from keyword
		System.err.println("\n Cipher Array");
		cipher = new char [SIZE];				//sets the max size of cipher array
		int p;
		
		//This block of code will fill the first part of the cipher with the keyword
		for (p = 0; p < keyword.length(); p++)
		{
			cipher[p] = keyword.charAt(p);		//add each character of the keyword to the array
		}
		
		// creates remainder of cipher from the remaining characters of the alphabet
		int t = 0;								//position of the index after the keyword
		for (int m = 25; -1 < m; m--)			//decrementing the alphabet each time
		{
			boolean contains = false;			
			int n = 0;							//index position of the keyword
			
			while(n < keyword.length())			//checks if all the index is checked
			{
				if (cipher[n] == alphabet[m])	//if the character from keyword is equal to an alphabet
					contains = true;			
				else
				{
					contains = false;			
					n++;						//check the next character of the keyword 
				}
				
				//if the contain is true then it should exit the loop
				if(contains)					
					n = keyword.length();		//exit the loop
			}
			
			//checks if a letter is not present in the keyword
			if(!contains && n==keyword.length())
			{
				cipher[p+t] = alphabet[m];	//add the letter to the cipher array
				t++;						//move onto the next available position
			}
		}
		
		// print cipher array for testing and tutors
		for (int k = 0; k < SIZE; k++)
			System.err.print(" "+cipher[k]);
		
	}
	
	
	/**
	 * Encode a character
	 * @param ch the character to be encoded
	 * @return the encoded character
	 */
	public char encode(char ch)
	{
		int indexPosition = ch - 'A';			//finds index position from alphabet array
		
		char encChar = cipher[indexPosition];	//converts the letter to an encoded letter 
		
	    return encChar;  						//returns the encoded character back
	}
	

	/**
	 * Decode a character
	 * @param ch the character to be encoded
	 * @return the decoded character
	 */
	public char decode(char ch)
	{
		char decChar = ' ';				//initialize a character
		
		for (int i = 0; i < SIZE; i++)	//checks through all the elements of the array
		{
			if (ch == cipher[i])		//checks if the character is found in the array
			{
				decChar = alphabet[i];	//gets the letter back from the alphabet array
			}
		}
	    return decChar; 				//returns the decoded character back
	}
}
