/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Jacyara Bosse, Lucas João Martins e Nathália Liz de Brito.
 *
 */

package br.ufsc.ine5426.compiladorxpp.automata;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import br.ufsc.ine5426.compiladorxpp.common.Symbol;
import br.ufsc.ine5426.compiladorxpp.lexicalanalyzer.LexicalAnalyser;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Classe que representa o AF que é utilizado pelo {@link LexicalAnalyser}.
 *
 */
@NoArgsConstructor
public class FiniteAutomata {

	/**
	 * Estado inicial.
	 */
	private State initialState;
	/**
	 * Conjunto de estados de aceitação.
	 */
	private Set<State> finalStates;
	/**
	 * Tabela de transição do AF.
	 */
	private Map<State, Map<Symbol, Set<State>>> transitionTable;
	/**
	 * Estado que se encontra o AF.
	 */
	@Getter
	private State currentState;
	/**
	 * Penúltimo estado em que se encontrava o AF.
	 */
	@Getter
	private State oldState;

	/**
	 * Responsável por criar o AF com base nas informações contidas no arquivo que
	 * representa o AF.
	 *
	 * @param path caminho para o AF em formato de texto
	 * @return o AF configurado corretamente
	 * @throws IOException se houver algum problema na leitura do arquivo de texto
	 */
	public static FiniteAutomata Load(String path) throws IOException {
		var symbols = new LinkedHashSet<Symbol>();
		var statesMap = new TreeMap<String, State>();
		var finalStates = new HashSet<State>();
		var transitions = new TreeMap<State, Map<Symbol, Set<State>>>();
		var ret = new FiniteAutomata();
		var initialState = new HashSet<State>(); // workaround

		List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());

		parseSymbols(lines.remove(0), symbols);
		parseStates(lines, finalStates, initialState, statesMap);
		parseTransitions(lines, statesMap, symbols, transitions);

		ret.finalStates = finalStates;
		ret.transitionTable = transitions;
		ret.initialState = initialState.iterator().next();

		return ret;
	}

	/**
	 * Função auxiliar para criação do AF.
	 */
	private static void parseSymbols(String line, Set<Symbol> symbols) {
		var parts = line.replaceAll(" ", "").trim().split("\t");
		for (var part : parts) {
			var trimPart = part.trim();
			if (!trimPart.isEmpty()) {
				symbols.add(new Symbol(trimPart));
			}
		}
	}

	/**
	 * Função auxiliar para criação do AF.
	 */
	private static void parseStates(List<String> lines, Set<State> finalStates, Set<State> initialState,
			Map<String, State> states) {
		for (var line : lines) {
			var cleanLine = line.replaceAll(" ", "").trim().split("\t")[0];

			var isInitialIsFinal = new Boolean[2];
			var state = parseState(cleanLine, isInitialIsFinal, states);

			if (isInitialIsFinal[1]) {
				finalStates.add(state);
			}
			if (isInitialIsFinal[0] && initialState.isEmpty()) {
				initialState.add(state);
			}
		}
	}

	/**
	 * Função auxiliar para criação do AF.
	 */
	private static State parseState(String part, Boolean[] isInitialIsFinal, Map<String, State> states) {
		String cleanLine = part.trim();
		isInitialIsFinal[0] = cleanLine.contains(">");
		isInitialIsFinal[1] = cleanLine.contains("*");

		if (isInitialIsFinal[0] || isInitialIsFinal[1]) {
			cleanLine = cleanLine.replaceAll("\\*", "").replaceAll("\\>", "");

		}

		if (!states.containsKey(cleanLine)) {
			states.put(cleanLine, new State(states.size(), cleanLine));
		}

		return states.get(cleanLine);
	}

	/**
	 * Função auxiliar para criação do AF.
	 */
	private static void parseTransitions(List<String> lines, Map<String, State> states, Set<Symbol> symbols,
			Map<State, Map<Symbol, Set<State>>> transitions) {
		for (var row : lines) {
			var columns = row.replaceAll(" ", "").trim().split("\t");
			var transition = new LinkedHashMap<Symbol, Set<State>>();
			var isInitialIsFinal = new Boolean[2];
			var state = parseState(columns[0], isInitialIsFinal, states);
			int offset = 1;
			for (var symbol : symbols) {
				var nextStates = parseNextState(columns[offset++], states);
				if (nextStates.size() > 0) {
					transition.put(symbol, nextStates);
				}

			}
			transitions.put(state, transition);
		}
	}

	/**
	 * Função auxiliar para criação do AF.
	 */
	private static Set<State> parseNextState(String str, Map<String, State> states) {
		String cleanLine = str.trim();
		var nextStates = new TreeSet<State>();
		if (cleanLine.isEmpty() || "-".equals(cleanLine)) {
			return nextStates;
		}
		boolean isSet = cleanLine.startsWith("{") && cleanLine.endsWith("}");
		if (isSet) {
			cleanLine = cleanLine.replace("{", "").replace("}", "");
			if (!cleanLine.contains(",")) {

				nextStates.add(states.get(cleanLine));
			} else {
				var parts = cleanLine.split(",");
				for (var part : parts) {
					if (!part.equals("{") && !part.equals("}") && !part.isEmpty()) {
						nextStates.add(states.get(part));
					}
				}
			}
		} else {
			nextStates.add(states.get(cleanLine));
		}

		return nextStates;
	}

	/**
	 * Reinicia o AF e coloca como estado atual o estado inicial
	 */
	public void resetAutomata() {
		this.currentState = this.initialState;
	}

	/**
	 * Verifica se é um estado final
	 *
	 * @param state estado que será verificado
	 * @return se o estado passado como parâmetro é final ou não
	 */
	public boolean isFinalState(State state) {
		return this.finalStates.contains(state);
	}

	/**
	 * Caminha pelo diagrama de transição conforme o caractere recebido. Caso o
	 * caractere recebido seja um espaço em branco, cria-se um símbolo especial,
	 * compatível com a definição realizada no arquivo .txt que define o AF.
	 *
	 * @param ch que representa o símbolo de entrada
	 * @return estado que o AF se encontra após transitar com o símbolo de entrada
	 *         ch
	 */
	public State transitByChar(Character ch) {
		try {
			this.oldState = this.currentState;
			var transition = this.transitionTable.get(this.currentState);
			var symbol = Character.isWhitespace(ch) ? new Symbol("<space>") : new Symbol(ch);
			this.currentState = transition.get(symbol).iterator().next();
		} catch (Exception e) {
			System.out.println("Erro ao compilar o caractere: " + ch);
			System.exit(0);
		}
		return this.currentState;
	}
}
