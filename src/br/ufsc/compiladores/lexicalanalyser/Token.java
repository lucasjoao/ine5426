package br.ufsc.compiladores.lexicalanalyser;

public class Token {
    private TokenType type;
    private String name;
    private int rowPos; //linha
    private int wordPos; //em relação ao número de palavras

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRowPos() {
        return rowPos;
    }

    public void setRowPos(int rowPos) {
        this.rowPos = rowPos;
    }

    public int getWordPos() {
        return wordPos;
    }

    public void setWordPos(int wordPos) {
        this.wordPos = wordPos;
    }

    @Override
    public String toString() {
        return type.toString();
    }

    public Token(TokenType type, String name, int rowPos, int wordPos) {
        this.type = type;
        this.name = name;
        this.rowPos = rowPos;
        this.wordPos = wordPos;
    }


}
