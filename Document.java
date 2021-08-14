import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Document class creates a Document object that stores a group of 
 * Sentence objects and contains the following methods: <eat, getDoc,
 * getNextToken, parsePhrase, parseSentence, parseDocument, toString>
 * Justification: the Sentence objects are stored in an ArrayList 
 * because it can be efficiently traversed, quickly accessed with
 * O(1) access and insertion to the end of the list.
 * @author Helen Li
 * @version May 21 2018
 */
public class Document 
{
    /** stores Sentence objects that make up a Document */ 
    private ArrayList<Sentence> document;
    
    /** current Token of the Document */
    private Token currToken;
    
    /** Scanner that scans the Document when parsing */ 
    private Scanner in;

    /**
     * Constructs a new Document and initializes the instance 
     * ArrayList of Sentence objects. 
     * @param s specify the Scanner to use for the Document
     */
    public Document(Scanner s)
    {
        in = s;
        document = new ArrayList<Sentence>();
        getNextToken();
    }

    /**
     * The getNextToken method attempts to get the next Token from the Scanner in.
     * It sets currToken value to null if there are no more Tokens left. 
     * Otherwise, it reads the next Token from the Scanner and set it to currToken. 
     * postcondition: The Scanner is advanced one Token if it is not at its last Token
     * and the currToken instance field is set to Token read from the Scanner in.
     */
    private void getNextToken()
    {
        if(in.hasNextToken())
            currToken = in.nextToken();
        else
            currToken = null;
    }

    /**
     * Compares t to currToken. If they match, calls getNextToken(); Otherwise, 
     * throw an RunTimeException to tell them to start debugging. 
     * @param t specify the Token object to be compared to currToken
     */
    public void eat(Token t)
    {
        if(t.equals(currToken))
            getNextToken();
        else
            throw new RuntimeException("Tokens did not match, debug");
    }

    /**
     * Returns a shallow copy of the document ArrayList.
     * @return a shallow copy of the document ArrayList
     */
    public ArrayList<Sentence> getDoc()
    {
        return document;
    }

    /**
     * Returns a String object containing the values in document ArrayList.
     * @return a String object with document ArrayList values 
     */
    public String toString()
    {
        String str = "";
        for(Sentence s: document)
            str += " " + s + " ";
        return str;
    }

    /**
     * Only takes in Token objects with type WORD and adds them to a 
     * local Phrase object. Other Token types are discarded.
     * This stops parsing the Phrase when the Token is either type of 
     * END_OF_PHRASE, END_OF_FILE, or END_OF_SENTENCE. 
     * @return a Phrase object with all parsed Tokens
     */
    public Phrase parsePhrase()
    {
        Phrase p = new Phrase();
        while(!(currToken.getType().equals(Scanner.TOKEN_TYPE.END_OF_FILE)) 
                && !(currToken.getType().equals(Scanner.TOKEN_TYPE.END_OF_PHRASE))
                && !(currToken.getType().equals(Scanner.TOKEN_TYPE.END_OF_SENTENCE)))
        {
            if(currToken.getType().equals(Scanner.TOKEN_TYPE.WORD))
                p.addToken(currToken);
            eat(currToken);
        }
        if(currToken.getType().equals(Scanner.TOKEN_TYPE.END_OF_PHRASE))
            eat(currToken);
        return p;
    }

    /**
     * Parses individual Phrase objects from the Scanner in until the 
     * end of a sentence or the end of file is reached. 
     * This uses parsePhrase to accomplish its work.
     * @return a Sentence object with all parsed Phrases
     */
    public Sentence parseSentence()
    {
        Sentence s = new Sentence();
        while(!(currToken.getType().equals(Scanner.TOKEN_TYPE.END_OF_FILE))
                && !(currToken.getType().equals(Scanner.TOKEN_TYPE.END_OF_SENTENCE)))
            s.addPhrase(parsePhrase());
        if(currToken.getType().equals(Scanner.TOKEN_TYPE.END_OF_SENTENCE))
            eat(currToken);
        return s;
    }

    /**
     * Parses sentences from Scanner in until the end of file is reached.
     * This will skip any leading Tokens that are not with type WORD.
     * This uses the method of parseSentence. 
     */
    public void parseDocument()
    {
        while(!(currToken.getType().equals(Scanner.TOKEN_TYPE.END_OF_FILE)))
            document.add(parseSentence());
    }
}
