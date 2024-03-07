package fr.mds.helloworld.data.api.character;

import java.util.List;

public class CharactersResponse {
    private Info info;
    private List<Character> results;

    public List<Character> getResults() {
        return results;
    }

    // Classe interne Info
    public class Info {
        private int count;
        private int pages;
        private String next;
        private String prev;
        // Getters et Setters
    }

    // Getters et Setters pour info et results
}
