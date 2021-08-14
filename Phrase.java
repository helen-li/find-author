import java.util.ArrayList;

/**
 * Phrase class creates a Phrase object that stores a group of 
 * Token objects and contains the following methods: <addToken,
 * getPhrase, toString>
 * Justification: the Token objects are stored in an ArrayList 
 * because it can be efficiently traversed, quickly accessed with
 * O(1) access and insertion to the end of the list.
 * @author Helen Li
 * @version March 9 2018
 */
public class Phrase
{
    /** stores Token objects that make up a Phrase */ 
    private ArrayList <Token> phrase;
    
    /**
     * Constructs a new Phrase and initializes the instance 
     * ArrayList of Token objects. 
     */
    public Phrase()
    {
        phrase = new ArrayList<Token>();
    }
    
    /**
     * Adds t to the phrase ArrayList. 
     * Big O: requires O(1) time of insert to the end of ArrayList
     * @param t specify the Token object to add
     */
    public void addToken(Token t)
    {
        phrase.add(t);
    }
    
    /**
     * Retrieves a copy of the phrase ArrayList. 
     * @return an ArrayList that contains all values of phrase
     */
    public ArrayList<Token> getPhrase()
    {
        ArrayList <Token> copy = new ArrayList <Token>();
        for(Token t: phrase)
            copy.add(t);
        return copy;
    }
    
    /**
     * Returns a String object containing the values in phrase ArrayList.
     * @return a String object with phrase ArrayList values
     */
    public String toString()
    {
        String value = "";
        for(Token t: phrase)
        {
            value += "{" + t + "}";
        }
        return value;
    }
}
