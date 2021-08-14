import java.util.*;

/**
 * DocumentStatistics creates a document signature and generates stats data
 * for the Document given five linguistic features and contains the following 
 * methods: <getAverageWordLength, getTypeTokenRation, getHapaxLegomanaRatio, 
 * getAverageWordsPerSentence, getSentenceComplexity>
 * @author Helen Li
 * @version May 21 2018
 */
public class DocumentStatistics 
{
    /**  Document object that stores relevant info */
    private Document doc;

    /**
     * Constructs a DocumentStatistics object and 
     * instantiates the instance variables
     * @param obj   the document to be examined
     */
    public DocumentStatistics(Document obj)
    {
        doc = obj;
    }

    /**
     * Returns the average word length feature, which isthe average number 
     * of characters per word, calculated after stripping punctuation. 
     * Comma and final period are stripped but any hyphen characters or 
     * underscore characters are not.  
     * @return the average word length feature
     */
    public double getAverageWordLength() 
    {
        double count = 0.0, sum = 0.0;
        for(Sentence s : doc.getDoc())
        {
            for(Phrase p : s.getSentence())
            {
                for(Token t : p.getPhrase())
                {
                    if(t.getType() == Scanner.TOKEN_TYPE.WORD)
                    {
                        count++;
                        sum += t.getValue().length();
                    }
                }
            }
        }
        return sum / count;
    }

    /**
     * Returns the type-token ration feature, which is the number of 
     * different words used in a text divided by the total number of words. 
     * TTR measures how repetitive the vocabulary is. 
     * @return the type-token ration feature
     */
    public double getTypeTokenRation()
    {
        double count = 0.0;
        Set<String> wordSet = new HashSet<String>();
        for(Sentence s : doc.getDoc())
        {
            for(Phrase p : s.getSentence())
            {
                for(Token t : p.getPhrase())
                {
                    if(t.getType() == Scanner.TOKEN_TYPE.WORD)
                    {
                        count++;
                        wordSet.add(t.getValue());
                    }
                }
            }
        }
        return wordSet.size() / count;
    }

    /**
     * Returns a ratio using the total number of words as denominator 
     * and number of words occurring exactly once in the text as numerator.
     * @return the Hapax Legomana Ratio
     */
    public double getHapaxLegomanaRatio()
    {
        double count = 0.0;
        Set<String> wordSet = new HashSet<String>();
        Set<String> duplicates = new HashSet<String>();
        for(Sentence s : doc.getDoc())
        {
            for(Phrase p : s.getSentence())
            {
                for(Token t : p.getPhrase())
                {
                    if(t.getType() == Scanner.TOKEN_TYPE.WORD)
                    {
                        count++;
                        String word = t.getValue();
                        if(!wordSet.add(word))
                            duplicates.add(word);
                    }
                }
            }
        }
        return Math.abs(wordSet.size() - duplicates.size()) / count;
    }

    /**
     * Returns the average number of words per sentence.
     * @return the average number of words per sentence
     */
    public double getAverageWordsPerSentence()
    {
        double count = 0.0;
        for(Sentence s: doc.getDoc())
        {
            for(Phrase p: s.getSentence())
            {
                for(Token t: p.getPhrase())
                {
                    if(t.getType() == Scanner.TOKEN_TYPE.WORD)
                        count++;
                }
            }
        }
        return count / doc.getDoc().size();
    }

    /**
     * Returns the average number of phrases per sentence. 
     * This shows the sentence complexity, and it finds the phrases by taking 
     * each sentence and splitting it on any of colon, semi-colon, or comma. 
     * @return the average number of phrases per sentence
     */
    public double getSentenceComplexity()
    {
        double phraseCount = 0.0;
        for(Sentence s: doc.getDoc())
        {
            for(Phrase p: s.getSentence())
                phraseCount += 1;
        }
        return phraseCount / doc.getDoc().size();
    }
}