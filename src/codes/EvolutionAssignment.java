package codes;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class EvolutionAssignment {
	
	
	public static File infile = new File("infile.txt");
	public static File outfile = new File("outfile.txt");
	
	public static char[] originalChars() throws Exception { // create an array of chars from the original text
		Scanner input = new Scanner(infile);
		String temp = "";
		char[] originalChars = new char[(int) infile.length()];
		int incriment = 0;
		
		while(input.hasNextLine())
		{
			temp = input.nextLine();
			char[] tempChars = temp.toCharArray();
			
			for(int x = 0; x < tempChars.length; x++)
				originalChars[incriment++] = tempChars[x]; // populate originalChars with all chars from infile
			
			if(input.hasNextLine())
				originalChars[incriment++] = 10; // account for next lines
		}
		
		return originalChars;
	} // end originalChars
	
	public static char[] uniqueChars(char[] originalChars) throws Exception { // create an array of unique chars from the original chars
		char[] uniqueChars = new char[originalChars.length];
		int incriment = 0;
		
		for(int x = 0; x < originalChars.length; x++)
		{
			boolean check = true;
			
			for(int y = 0; y < uniqueChars.length; y++)
				if(originalChars[x] == uniqueChars[y]) // if the char already exists in uniqueChars, skip
				{
					check = false;
					continue;
				}
			
			if(check) // if char doesn't already exist in uniqueChars, add it
				uniqueChars[incriment++] = originalChars[x];
		}
		
		return uniqueChars;
	} // end uniqueChars
	
	public static char[] generateText(char[] uniqueChars, char[] tryChars, boolean[] matchArray) { // generate an array of random chars from the unique chars
		char[] generatedChars = tryChars;
		
		for(int x = 0; x < infile.length(); x++)
		{
			if(matchArray[x]) // if the char matches the char in the infile, leave it
				continue;
			else // if the char does not match, regenerate
			{
				int randnum = (int)(Math.random()*(uniqueChars.length));
			
				generatedChars[x] = uniqueChars[randnum];
			}
		}
		
		return generatedChars;
	} // end generateText

	public static boolean[] compare (boolean[] matchArray, char[] originalChars, char[] tryChars) throws Exception { // compare generated char array to original char array
		for(int x = 0; x < matchArray.length; x++)
		{
			if(originalChars[x] != tryChars[x]) // if the pair doesn't match, mark as false
				matchArray[x] = false;
			else // if the pair matches, mark as true
				matchArray[x] = true;
		}
		
		return matchArray;
	} // end compare
	
//***************************************************************************************//
	
	public static void main(String[] args) throws Exception {
		
		PrintWriter output = new PrintWriter (outfile);
		
		char[] originalChars = originalChars(); // create an array of the original chars
		char[] uniqueChars = uniqueChars(originalChars); // create an array of unique chars from the original
		
		boolean[] matchArray = new boolean[originalChars.length];
		boolean match = true;
		char[]tryChars = new char[originalChars.length];
		
		for(int x = 0; x < matchArray.length; x++)
			matchArray[x] = false;
		
		for(int x = 0; x < tryChars.length; x++)
			tryChars[x] = 0;
		
		do
		{
			match = true;
			
			char[] generatedChars = generateText(uniqueChars, tryChars, matchArray); // generate an array of chars from the unique chars
			
			tryChars = generatedChars;
		
			compare(matchArray, originalChars, tryChars); // compare the generated array to the original array to see if they are identical
		
			for(int x = 0; x < matchArray.length; x++) // check to see if the generated chars match the originals
				if(!matchArray[x]) // if not, regenerate
				{
					match = false;
					break;
				}
				
		} while(!match);
		
		for(int x = 0; x < tryChars.length; x++) // print the final text into the outfile
			output.print(tryChars[x]);
		
		output.close();
		System.out.println("Terminated");

	} // end main

}