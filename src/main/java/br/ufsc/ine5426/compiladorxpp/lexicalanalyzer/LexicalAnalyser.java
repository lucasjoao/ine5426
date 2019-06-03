/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Jacyara Bosse, Lucas João Martins e Nathália Liz de Brito.
 *
 */

package br.ufsc.ine5426.compiladorxpp.lexicalanalyzer;

import static br.ufsc.ine5426.compiladorxpp.common.Constants.ERROR_STATE;
import static br.ufsc.ine5426.compiladorxpp.common.Constants.RETRACT_STATE;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.ufsc.ine5426.compiladorxpp.automata.FiniteAutomata;
import br.ufsc.ine5426.compiladorxpp.automata.State;
import lombok.Getter;

/**
 * Classe que representa o analisador léxico do compilador da linguagem X++.
 *
 */
public class LexicalAnalyser {

	/**
	 * Autômato Finito utilizado pelo analisador léxico para realizar a análise.
	 */
	private FiniteAutomata baseAutomata;
	/**
	 * Conjunto de palavras reservadas que foram retiradas da gramática fornecida
	 * pelo professor.
	 */
	private Set<String> reservedWords = new HashSet<>(Arrays.asList("class", "extends", "int", "string", "constructor",
			"break", "print", "read", "return", "super", "if", "else", "for", "new", "null"));
	/**
	 * Lista com os tokens encontrados durante o processo de compilação.
	 */
	private List<Token> tokens = new ArrayList<>();
	/**
	 * Lista com os erros encontrados durante o processo de compilação.
	 */
	@Getter
	private List<String> errors = new ArrayList<>();
	/**
	 * Mapa que representa a tabela de símbolos alimentada durante a análise léxica.
	 */
	private Map<String, Token> table = new HashMap<>();

	private int iterator;

	/**
	 * Construtor que recebe o autômato finito.
	 *
	 * @param baseAutomata autômato finito que é utilizado pelo AL.
	 */
	public LexicalAnalyser(FiniteAutomata baseAutomata) {
		this.baseAutomata = baseAutomata;
	}

	/**
	 * O processo de análise léxica funciona com a seguinte ideia: (1) para cada
	 * linha do arquivo fonte o AL fica mandando caractere por caractere para o AF;
	 * (2) o AF caminha pelo seu diagrama de transição e para cada caractere
	 * recebido retorna o estado que aquele símbolo de entrada fez ele parar; (3) o
	 * AL verifica se o estado recebido é um dos estados especiais ou se trata do
	 * último caractere da linha; (4) caso seja o último caractere da linha e o
	 * estado que o AF está é de aceitação, cria-se um token; (5) os estados
	 * especiais são o de retração e o de erro; (6) se estiver em um estado de erro,
	 * adiciona o erro na lista de erros e continua a leitura do programa fonte; (7)
	 * se estiver em um estado de retração, atualiza os ponteiros e cria um token
	 * com o lexema correto.
	 *
	 * @param path caminho para o programa fonte que será compilado.
	 * @return valor booleano que indica se a compilação foi um sucesso ou não. Para
	 *         a compilação ter sido um sucesso não deve ter ocorrido nenhum erro
	 *         léxico.
	 */
	public boolean compile(String path) {
		this.iterator = 0;
		try {
			List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
			int lineNumber = 1;
			for (String line : lines) {
				this.baseAutomata.resetAutomata();
				int startOfLexeme = 0;
				for (int endOfLexeme = 0; endOfLexeme < line.length(); endOfLexeme++) {
					char ch = line.charAt(endOfLexeme);
					State state = this.baseAutomata.transitByChar(ch);

					if (State.isErrorState(state.getLabel())) {
						this.errors.add(String.format(
								"Erro encontrado na linha de número %s, coluna %s que possui o seguinte texto: %s",
								lineNumber, startOfLexeme, line));
						startOfLexeme = endOfLexeme;
						if (ERROR_STATE.equals(state.getLabel())) {
							endOfLexeme -= 1;
						}
						this.baseAutomata.resetAutomata();
					} else if (RETRACT_STATE.equals(state.getLabel())) {
						String lexeme = line.substring(startOfLexeme, endOfLexeme);
						Token token = this.createToken(this.baseAutomata.getOldState().getLabel(), lexeme, lineNumber,
								startOfLexeme);
						this.tokens.add(token);
						startOfLexeme = endOfLexeme;
						endOfLexeme -= 1;
						this.baseAutomata.resetAutomata();
						this.addInSymbolTable(lexeme, token);
					} else if (endOfLexeme == line.length() - 1 && this.baseAutomata.isFinalState(state)) {
						String lexeme = line.substring(startOfLexeme, endOfLexeme + 1);
						Token token = this.createToken(state.getLabel(), lexeme, lineNumber, startOfLexeme);
						this.tokens.add(token);
						this.addInSymbolTable(lexeme, token);
					}
				}
				lineNumber++;
			}
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo do programa fonte");
			e.printStackTrace();
			return false;
		}

		return this.errors.size() == 0;
	}

	/**
	 * Cria um Token com base nos parâmetros recebidos. O switch case sobre o
	 * stateId é feito com base no autômato montado pela equipe.
	 *
	 * @param stateName estado de aceitação do lexema
	 * @param lexeme    definição do lexema que representa o Token
	 * @param line      linha que o Token foi encontrado
	 * @param column    coluna inicial que o Token foi encontrado
	 * @return um Token criado a partir dos parâmetros
	 */
	private Token createToken(String stateName, String lexeme, int line, int column) {
		var numberOfWhitespace = 0;
		for (char ch : lexeme.toCharArray()) {
			if (Character.isWhitespace(ch)) {
				numberOfWhitespace++;
			}
		}

		// lexema pode vir com espaço no começo
		lexeme = lexeme.trim();
		column += numberOfWhitespace + 1; // necessario corrigir a coluna
		int stateId = Integer.parseInt(stateName.replace("q", ""));

		switch (stateId) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 8:
			return new Token(TokenType.RELOP, lexeme, line, column);
		case 9:
		case 11:
		case 21:
			return new Token(TokenType.BLOCK_OPEN, lexeme, line, column);
		case 10:
		case 12:
		case 22:
			return new Token(TokenType.BLOCK_CLOSE, lexeme, line, column);
		case 13:
		case 14:
			return new Token(TokenType.DELIMITER, lexeme, line, column);
		case 19:
			return new Token(TokenType.POINT, lexeme, line, column);
		case 15:
		case 16:
		case 17:
		case 18:
		case 20:
			return new Token(TokenType.AROP, lexeme, line, column);
		case 24:
			return new Token(TokenType.INT_CONSTANT, lexeme, line, column);
		case 25:
			return new Token(this.reservedWords.contains(lexeme) ? TokenType.PR : TokenType.IDENT, lexeme, line,
					column);
		case 26:
			return new Token(TokenType.STRING_CONSTANT, lexeme, line, column);
		}
		return null;
	}

	/**
	 * Adiciona um par lexema (key) e token (value) na TS.
	 *
	 * @param lexeme lexema que será adicionado, a chave na tabela
	 * @param token  token que será adicionado como valor na tabela
	 */
	private void addInSymbolTable(String lexeme, Token token) {
		// lexema pode vir com espaço no começo
		lexeme = lexeme.trim();
		// só adiciona na TS se o lexema não estiver lá
		if (!this.table.containsKey(lexeme)) {
			this.table.put(lexeme, token);
		}
	}

	/**
	 * Printa no console todos os tokens.
	 */
	public void printTokens() {
		this.tokens.forEach(token -> {
			String string = "%s %s - linha %s, coluna %s";
			String format = String.format(string, token.getType(), token.getName(), token.getLine(), token.getColumn());
			System.out.println(format);
		});

	}

	/**
	 * Printa no console a tabela de símbolos.
	 */
	public void printSymbolTable() {
		this.table.forEach((key, value) -> System.out.println(key + " - " + value.getType()));
	}

	/**
	 * Printa no console a lista de erros.
	 */
	public void printErrors() {
		this.errors.forEach(msg -> System.out.println(msg));
	}

	public boolean hasToken() {
		return this.iterator < this.tokens.size();
	}

	public Token getNextToken() {
		return this.tokens.get(this.iterator++);
	}
}
