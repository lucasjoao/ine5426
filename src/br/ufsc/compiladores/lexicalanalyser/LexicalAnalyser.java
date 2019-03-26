package br.ufsc.compiladores.lexicalanalyser;

import br.ufsc.compiladores.automata.FiniteAutomata;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class LexicalAnalyser {

    private FiniteAutomata baseAutomata;
    private Set<String> reservedWords = new HashSet<>(Arrays.asList("float","int","bool","byte","while", "do", "break", "if", "then", "else", "true", "false"));
    private List<Token> tokens = new ArrayList<>();
    private int iterator;
    private List<String> errors = new ArrayList<>();

    public LexicalAnalyser(FiniteAutomata baseAutomata) {
        this.baseAutomata = baseAutomata;
    }

    private LexicalAnalyser() {

    }

    public boolean compile(String path) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path),
                    Charset.defaultCharset());
           iterator = 0;
            int lineNumber = 1;
            for (String line : lines) {
                List<String> stringTokens = tokenizeString(line);
                int wordNumber = 1;
                for (String lexeme : stringTokens) {
                    if (baseAutomata.acceptWord(lexeme)){
                        tokens.add(this.st(baseAutomata.getCurrentState().getLabel(), lexeme, lineNumber, wordNumber));
                    }else{
                       getErrors().add(String.format("Erro léxico na linha (%s), palavra (%s): lexema com problema: %s", lineNumber, wordNumber, lexeme));
                    }
                    wordNumber++;
                }
                lineNumber++;
            }

        } catch (Exception e) {
            return false;
        }
        return errors.size() == 0;
    }

    public List<String> tokenizeString(String text) {
        return Arrays.asList(text.split(" "));
    }

    public Token st(String stateName, String lexeme, int line , int word) {
        int stateId = Integer.parseInt(stateName.replace("q", ""));
        switch (stateId) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 12:
                return new Token(TokenType.RELOP, lexeme, line, word);
            case 13:
            case 15:
            case 22:
                return new Token(TokenType.BLOCK_OPEN, lexeme, line, word);
            case 14:
            case 16:
            case 23:
                return new Token(TokenType.BLOCK_CLOSE, lexeme, line, word);
            case 17:
                return new Token(TokenType.DELIMITER, lexeme, line, word);
            case 18:
            case 19:
            case 20:
            case 21:
                return new Token(TokenType.AROP, lexeme, line, word);
            case 24:
                return new Token(TokenType.NUMBER, lexeme, line, word);
            case 25:
                return new Token(reservedWords.contains(lexeme) ? TokenType.PR : TokenType.ID, lexeme, line, word);
        }
        return null;
    }

    public Token getNextToken() {
        return tokens.get(iterator++);
    }

    public boolean hasToken() {
        return iterator < tokens.size();
    }

    public List<String> getErrors() {
        return errors;
    }
}





