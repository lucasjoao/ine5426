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
import br.ufsc.ine5426.compiladorxpp.automata.State;

public class LexicalAnalyser {

	private FiniteAutomata baseAutomata;
	private Set<String> reservedWords = new HashSet<>(Arrays.asList("class", "extends", "int", "string", "constructor", "break", "print", "read", "return", "super", "if", "else", "for", "new", "null"));
	@Getter
	private List<Token> tokens = new ArrayList<>();
	@Getter
	private List<String> errors = new ArrayList<>();

	public LexicalAnalyser(FiniteAutomata baseAutomata) {
		this.baseAutomata = baseAutomata;
	}

	public boolean compile(String path) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
			int lineNumber = 1;
			for (String line : lines) {
				this.baseAutomata.resetAutomata();
				int startOfLexeme = 0;
				for (int endOfLexeme = 0; endOfLexeme < line.length(); endOfLexeme++) {
					char ch = line.charAt(endOfLexeme);
					State state = this.baseAutomata.transitByChar(ch);

					// TODO: melhorar mensagens de erros
					if (State.ERROR_STATE.equals(state.getLabel())) {
						this.errors.add("melhorar isso daqui");
						startOfLexeme = endOfLexeme;
						endOfLexeme -= 1;
						this.baseAutomata.resetAutomata();
					} else if (State.RETRACT_STATE.equals(state.getLabel())) {
						String lexeme = line.substring(startOfLexeme, endOfLexeme);
						startOfLexeme = endOfLexeme;
						endOfLexeme -= 1;
						this.baseAutomata.resetAutomata();
						this.tokens.add(this.st(this.baseAutomata.getOldState().getLabel(), lexeme, lineNumber, 0));
					} else if (endOfLexeme == line.length() - 1) {
						// ultimo char da linha
						if (this.baseAutomata.isFinalState(state)) {
							String lexeme = line.substring(startOfLexeme, endOfLexeme+1);
							this.tokens.add(this.st(state.getLabel(), lexeme, lineNumber, 0));
						} else {
							this.errors.add("melhorar isso daqui");
						}
					}

				}

				lineNumber++;
			}

		} catch (Exception e) {
			// TODO: melhorar isso
			e.printStackTrace();

			return false;
		}
		return this.errors.size() == 0;
	}

	// TODO: melhorar nome do metodo
	private Token st(String stateName, String lexeme, int line , int word) {
		lexeme = lexeme.trim();
		int stateId = Integer.parseInt(stateName.replace("q", ""));
		switch (stateId) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 8:
			return new Token(TokenType.RELOP, lexeme, line, word);
		case 9:
		case 11:
		case 21:
			return new Token(TokenType.BLOCK_OPEN, lexeme, line, word);
		case 10:
		case 12:
		case 22:
			return new Token(TokenType.BLOCK_CLOSE, lexeme, line, word);
		case 13:
		case 14:
			return new Token(TokenType.DELIMITER, lexeme, line, word);
		case 19:
			return new Token(TokenType.POINT, lexeme, line, word);
		case 15:
		case 16:
		case 17:
		case 18:
		case 20:
			return new Token(TokenType.AROP, lexeme, line, word);
		case 24:
			return new Token(TokenType.INT_CONSTANT, lexeme, line, word);
		case 25:
			return new Token(this.reservedWords.contains(lexeme) ? TokenType.PR : TokenType.IDENT, lexeme, line, word);
		case 26:
			return new Token(TokenType.STRING_CONSTANT, lexeme, line, word);
		}
		return null;
	}
}





