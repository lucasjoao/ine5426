package br.ufsc.ine5426.compiladorxpp.lexicalanalyser;

public class Token {
	private TokenType type;
	private String name;
	private int rowPos; //linha
	private int wordPos; //em relação ao número de palavras

	public TokenType getType() {
		return this.type;
	}

	public void setType(TokenType type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRowPos() {
		return this.rowPos;
	}

	public void setRowPos(int rowPos) {
		this.rowPos = rowPos;
	}

	public int getWordPos() {
		return this.wordPos;
	}

	public void setWordPos(int wordPos) {
		this.wordPos = wordPos;
	}

	@Override
	public String toString() {
		return this.type.toString();
	}

	public Token(TokenType type, String name, int rowPos, int wordPos) {
		this.type = type;
		this.name = name;
		this.rowPos = rowPos;
		this.wordPos = wordPos;
	}


}
