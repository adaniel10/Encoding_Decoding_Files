/**
 * Programming AE2
 * Processes report on letter frequencies
 */
public class LetterFrequencies
{
	/** Size of the alphabet */
	private final int SIZE = 26;
	
	/** Count for each letter */
	private int [] alphaCounts;
	
	/** The alphabet */
	private char [] alphabet; 
												 	
	/** Average frequency counts */
	private double [] avgCounts = {8.2, 1.5, 2.8, 4.3, 12.7, 2.2, 2.0, 6.1, 7.0,
							       0.2, 0.8, 4.0, 2.4, 6.7, 7.5, 1.9, 0.1, 6.0,  
								   6.3, 9.1, 2.8, 1.0, 2.4, 0.2, 2.0, 0.1};
	private double [] freqPerc;
	private double [] diff;

	/** Character that occurs most frequently */
	private char maxCh;

	/** Total number of characters encrypted/decrypted */
	private int totChars;
	
	/**
	 * Instantiates a new letterFrequencies object.
	 */
	public LetterFrequencies()
	{
		alphaCounts = new int [SIZE];		//creates an alphaCount array with a set SIZE
		alphabet = new char [SIZE];			//creates an alphabet array with a set SIZE
		freqPerc = new double [SIZE];		//creates an frequency % array with a set SIZE
		diff = new double [SIZE];			//creates an difference array with a set SIZE
		
		//This block of code sets all values of the arrays to initially 0 and fills the
		//alphabet array with all the letters of the alphabet
		for (int i = 0; i < SIZE; i++)
		{
			alphaCounts[i] = 0;
			alphabet[i] = (char)('A' + i);
			freqPerc[i] = 0.0;
			diff[i] = 0.0;
		}
		
	}
	
		
	/**
	 * Increases frequency details for given character
	 * @param ch the character just read
	 */
	public void addChar(char ch)
	{
		for (int i = 0; i < SIZE; i++)	//check through all the alphabets
		{
			if(ch == alphabet[i])		//if the character equals alphabet
			{
				alphaCounts[i] += 1;	//increment the number in the index position
				totChars++;				//total number of characters in the file
			}
		}
		
		for (int i = 0; i < SIZE; i++)	//check through all the alphabets
		{
			double nCount = alphaCounts[i];				//sets the elements of alphaCounts to a double type
			freqPerc[i] = (nCount / totChars) * 100;	//convert to percentage
			diff[i] = freqPerc[i] - avgCounts[i];		//calculate the difference	
		}
	}
	
	
	/**
	 * Gets the maximum frequency
	 * @return the maximum frequency
	 */
	private double getMaxPC()
    {
		int num = 0;				
		double max = 0.0;			
		
		//This block of code runs through all the numbers in the alphaCount array and
		//find the max number and then returns it.
		for (int d = 0; d < SIZE; d++)
		{
			if(alphaCounts[d] > num)
			{
				num = alphaCounts[d];
				max = freqPerc[d];
				maxCh = alphabet[d];
			}
			
			if(alphaCounts[d] == num)
				maxCh = alphabet[d];
		}
		
	    return max;  
	}
	
	
	/**
	 * Returns a String consisting of the full frequency report
	 * @return the report
	 */
	public String getReport()
	{
		double maximum = getMaxPC();
		String info = "";
		
		//Prints all the headings
		String text = ("LETTER ANALYSIS\n\n"+"Letter"+"      "+"Freq"+"        "+
		"Freq%"+"       "+"AvgFreq%"+"      "+"Diff\n");
		
		//Prints out all the results
		for(int i = 0; i < SIZE; i++)
		{
			info +=  String.format("%3s %11s %12s %12s %12s %n", 
					alphabet[i], alphaCounts[i], String.format("%.1f",freqPerc[i]),
					avgCounts[i], String.format("%.1f",diff[i]));
		}
		
		//Prints the max letter and the frequency
		String mostFreq = "The most frequent letter is "+maxCh+" at "+
								String.format("%.1f",maximum)+"%";
		
		text = text+info+"\n\n"+mostFreq;	//combines the headings + results + most frequent letter
		
	    return text; 
	}
}
