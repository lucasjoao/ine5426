package br.ufsc.ine5426.compiladorxpp.lexicalanalyser;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;

import br.ufsc.ine5426.compiladorxpp.automata.FiniteAutomata;

public class LexicalAnalyser {

	private FiniteAutomata baseAutomata;
	private Set<String> reservedWords = new HashSet<>(Arrays.asList("float","int","bool","byte","while", "do", "break", "if", "then", "else", "true", "false"));
	@Getter
	private List<Token> tokens = new ArrayList<>();
	private List<String> errors = new ArrayList<>();

	public LexicalAnalyser(FiniteAutomata baseAutomata) {
		this.baseAutomata = baseAutomata;
	}

	public boolean compile(String path) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
			int lineNumber = 1;
			for (String line : lines) {
				List<String> stringTokens = this.tokenizeString(line);
				int wordNumber = 1;
				for (String lexeme : stringTokens) {
					if (this.baseAutomata.acceptWord(lexeme)){
						this.tokens.add(this.st(this.baseAutomata.getCurrentState().getLabel(), lexeme, lineNumber, wordNumber));
					}else{
						this.errors.add(String.format("Erro léxico na linha (%s), palavra (%s): lexema com problema: %s", lineNumber, wordNumber, lexeme));
					}
					wordNumber++;
				}
				lineNumber++;
			}

		} catch (Exception e) {
			return false;
		}
		return this.errors.size() == 0;
	}

	private List<String> tokenizeString(String text) {
		return Arrays.asList(text.split(" "));
	}

	private Token st(String stateName, String lexeme, int line , int word) {
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
			return new Token(this.reservedWords.contains(lexeme) ? TokenType.PR : TokenType.ID, lexeme, line, word);
		}
		return null;
	}
}





