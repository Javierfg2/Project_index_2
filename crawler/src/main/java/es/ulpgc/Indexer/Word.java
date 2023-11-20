package es.ulpgc.word_searcher.Indexer;

import java.util.HashMap;

public class Word {
    private final String word;
    private HashMap<String, Integer> references;

    public Word(String word){
        this.word = word;
        this.references = new HashMap<String, Integer>();
    }

    public void setReferences(String newBook, int newCount){
        references.put(newBook, newCount);
    }

    public HashMap<String, Integer> getReferences() {
        return references;
    }

    public String getWord() {
        return word;
    }
}
