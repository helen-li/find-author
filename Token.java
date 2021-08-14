/**
 * Token class creates Token objects that store its type
 * (WORD, END_OF_SENTENCE, END_OF_FILE, END_OF_PHRASE, 
 * DIGIT, or UNKNOWN) and its value. 
 * @author Helen Li
 * @version March 5 2018
 */
public class Token 
{
	private Scanner.TOKEN_TYPE type; 
	private String value;

	/**
	 * Creates a Token object and initiates the instance variables
	 * by assigning the token type and the value to them.
	 * @param type specify the type of the Token object
	 * @param value specify the value of the Token object
	 */
	public Token(Scanner.TOKEN_TYPE type, String value)
	{
		this.type = type;
		this.value = value.toLowerCase();
	}
	/**
	 * Retrieves the type of the Token object.
	 * @return type of the Token object
	 */
	public Scanner.TOKEN_TYPE getType()
	{
		return type;
	}

	/**
	 * Retrieves the value of the Token object.
	 * @return value of the Token object
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Returns the Token object with the representation
	 * of its type and then its value. 
	 * @return a String representing the Token object
	 */
	public String toString()
	{
		return type + ": " + value;
	}

	/**
	 * Tests if the Token object and obj are equal.
	 * Two Token objects are considered equal if they 
	 * have the same value.
	 * @param obj specify the obj to be compared to the Token
	 * @return true if the Token and obj are equal; otherwise, false
	 */
	public boolean equals(Object obj)
	{
		return ((Token)obj).getValue().equals(value);
	}
}