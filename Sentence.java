import java.util.ArrayList;

/**
 * Sentence class creates a Sentence object that stores a group of 
 * Phrase objects and contains the following methods: <addPhrase,
 * getSentence, toString>
 * Justification: the Phrase objects are stored in an ArrayList 
 * because it can be efficiently traversed, quickly accessed 
 * with O(1) access and insertion to the end of the list.
 * @author Helen Li
 * @version March 9 2018
 */
public class Sentence
{
    /** stores Phrase objects that make up a Sentence */
    private ArrayList<Phrase> sentence;
    
    /**
     * Constructs a new Sentence and initializes the instance 
     * ArrayList of Phrase objects. 
     */
    public Sentence()
    {
        sentence = new ArrayList<Phrase>();
    }
    
    /**
     * Adds p to the sentence ArrayList. 
     * Big O: requires O(1) time of insert to the end of ArrayList
     * @param p specify the Phrase object to add
     */
    public void addPhrase(Phrase p)
    {
        sentence.add(p);
    }
    
    /**
     * Retrieves a copy of the sentence ArrayList. 
     * @return an ArrayList that contains all values of sentence
     */
    public ArrayList<Phrase> getSentence()
    {
        ArrayList <Phrase> copy = new ArrayList <Phrase>();
        for(Phrase p: sentence)
            copy.add(p);
        return copy;
    }
    
    /**
     * Returns a String object containing the values in sentence ArrayList.
     * @return a String object with sentence ArrayList values 
     */
    public String toString()
    {
        String value = "";
        for(Phrase p: sentence)
        {
            value += "[" + p + "]";
        }
        return value;
    }
}