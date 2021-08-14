import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * A Scanner is responsible for reading an input stream, one character at a
 * time, and separating the input into tokens.  A token is defined as:
 *  1. A 'word' which is defined as a non-empty sequence of characters that 
 *     begins with an alpha character and then consists of alpha characters, 
 *     numbers, the single quote character "'", or the hyphen character "-". 
 *  2. An 'end-of-sentence' delimiter defined as any one of the characters 
 *     ".", "?", "!".
 *  3. An end-of-file token which is returned when the scanner is asked for a
 *     token and the input is at the end-of-file.
 *  4. A phrase separator which consists of one of the characters ",",":" or
 *     ";".
 *  5. A digit.
 *  6. Any other character not defined above.
 * @author Mr. Page, Helen Li
 * @version May 17 2018
 */
public class Scanner
{
	/** the File Reader */ 
	private Reader in;

	/** current character of File */
	private String currentChar;

	/** stores whether the Scanner reached the end of file yet */ 
	private boolean endOfFile;

	/** define symbolic constants for each type of token */
	public static enum TOKEN_TYPE
	{WORD, END_OF_SENTENCE, END_OF_FILE, END_OF_PHRASE, DIGIT, UNKNOWN};

	/**
	 *  Constructor for Scanner objects.  The Reader object should be one of
	 *  1. A StringReader
	 *  2. A BufferedReader wrapped around an InputStream
	 *  3. A BufferedReader wrapped around a FileReader
	 *  The instance field for the Reader is initialized to the input parameter,
	 *  and the endOfFile indicator is set to false.  The currentChar field is
	 *  initialized by the getNextChar method.
	 * @param in is the reader object supplied by the program constructing
	 *        this Scanner object.
	 */
	public Scanner(Reader in)
	{
		this.in = in;
		endOfFile = false;
		getNextChar();
	}

	/**
	 * The getNextChar method attempts to get the next character from the input
	 * stream.  It sets the endOfFile flag true if the end of file is reached on
	 * the input stream.  Otherwise, it reads the next character from the stream
	 * and converts it to a Java String object.
	 * postcondition: The input stream is advanced one character if it is not at
	 * end of file and the currentChar instance field is set to the String 
	 * representation of the character read from the input stream.  The flag
	 * endOfFile is set true if the input stream is exhausted.
	 */
	private void getNextChar()
	{
		try
		{
			int inp = in.read();
			if(inp == -1) 
				endOfFile = true;
			else 
				currentChar = "" + (char) inp;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Compares str to currentChar.If they match, calls getNextChar(); Otherwise, 
	 * throw an IllegalArgumentException to tell them to start debugging. 
	 * @param str specify the String object to be compared to currentChar
	 */
	private void eat(String str)
	{
		if(str.equals(currentChar))
			getNextChar();
		else
			throw new IllegalStateException("Characters did not match, debug");
	}

	/**
	 * Checks if str is a letter. 
	 * @param str specify the String object to be tested for
	 * @return true if str is a letter; otherwise, false
	 */
	private boolean isLetter(String str)
	{
		return (str.compareTo("A") >= 0 && str.compareTo("Z") <= 0) ||
				(str.compareTo("a") >= 0 && str.compareTo("z") <= 0);
	}

	/**
	 * Checks if str is a digit. 
	 * @param str specify the String object to be tested for
	 * @return true if str is a digit; otherwise, false
	 */
	private boolean isDigit(String str)
	{
		return str.compareTo("0") >= 0 && str.compareTo("9") <= 0;
	}

	/**
	 * Checks if str is a special character consisting of a 
	 * single quote or a hyphen. 
	 * @param str specify the String object to be tested for
	 * @return true if str is a letter; otherwise, false
	 */
	private boolean isSpecial(String str)
	{
		return str.equals("'") || str.equals("-");
	}

	/**
	 * Checks if str is a phrase terminator consisting of colons,
	 * semi-colons, or commas. 
	 * @param str specify the String object to be tested for
	 * @return true if str is a phrase terminator; otherwise, false
	 */
	private boolean isPhraseT(String str)
	{
		return str.equals(";") || str.equals(":") || str.equals(",");
	}

	/**
	 * Checks if str is a sentence terminator consisting of periods,
	 * exclamation marks, or question marks.  
	 * @param str specify the String object to be tested for
	 * @return true if str is a sentence terminator; otherwise, false
	 */
	private boolean isSentenceT(String str)
	{
		return str.equals(".") || str.equals("?") || str.equals("!");
	}

	/**
	 * Checks if str is a white space. 
	 * @param str specify the String object to be tested for
	 * @return true if str is a white space; otherwise, false
	 */
	private boolean isSpace(String str)
	{
		return str.equals(" ") || str.equals("\n") || 
				str.equals("\t") || str.equals("\r");
	}

	/**
	 * Checks if there are more tokens in the input stream.
	 * There are more if the input stream is not at end-of-file
	 * @return true if there are more tokens in the input stream; 
	 *         otherwise, false
	 */
	public boolean hasNextToken()
	{
		return !endOfFile;
	}

	/**
	 * Identify the Token type of the String object and return it.
	 * Each Token of type Scanner.TOKEN_TYPE.WORD is returned in lower case.
	 * @return a Token object containing a word, digit, end of phrase,
	 *         end of file, end of sentence, or unknown characters
	 */
	public Token nextToken()
	{
		String current = currentChar;
		if(hasNextToken())
		{
			while(!endOfFile && isSpace(currentChar))
				eat(currentChar);
			if(isLetter(currentChar))
			{
				String word = "";
				while(hasNextToken() && (isLetter(currentChar) || isDigit(currentChar) 
						|| isSpecial(currentChar)))
				{
					word += currentChar;
					eat(currentChar);
				}
				return new Token(Scanner.TOKEN_TYPE.WORD, word);
			}
			else if(isDigit(currentChar))
			{
				eat(currentChar);
				return new Token(Scanner.TOKEN_TYPE.DIGIT, current);
			}
			else if(isPhraseT(currentChar))
			{
				eat(currentChar);
				return new Token(Scanner.TOKEN_TYPE.END_OF_PHRASE, current);
			}
			else if(isSentenceT(currentChar))
			{
				eat(currentChar);
				return new Token(Scanner.TOKEN_TYPE.END_OF_SENTENCE, current);
			}
			else if(hasNextToken())
			{
				eat(currentChar);
				return new Token(Scanner.TOKEN_TYPE.UNKNOWN, current);
			}
			else
				return new Token(Scanner.TOKEN_TYPE.END_OF_FILE, current);
		}
		return new Token(Scanner.TOKEN_TYPE.END_OF_FILE, current);
	}

	/**
	 * Starts the testing program. 
	 * @param str array of String objects 
	 */
	public static void main(String[] str) throws FileNotFoundException
	{
		FileReader reader = new FileReader(new File
				("/Users/helenli/Desktop/FindAuthor/src/MysteryText/mystery1.txt"));
		Scanner scanner = new Scanner(reader);
		while(scanner.hasNextToken())
		{
			System.out.println(scanner.nextToken());
		}
	}
}