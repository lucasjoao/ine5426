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

import lombok.Getter;
import lombok.NoArgsConstructor;

import br.ufsc.ine5426.compiladorxpp.common.Symbol;

@NoArgsConstructor
public class FiniteAutomata {

	private State initialState;
	private State errorState;
	private Set<State> finalStates;
	private Map<State, Map<Symbol, Set<State>>> transitionTable;
	@Getter
	private State currentState;
	@Getter
	private State oldState;

	public static FiniteAutomata Load(String path) throws IOException {
		var symbols = new LinkedHashSet<Symbol>();
		var statesMap = new TreeMap<String, State>();
		var finalStates = new HashSet<State>();
		var transitions = new TreeMap<State, Map<Symbol, Set<State>>>();
		var ret = new FiniteAutomata();
		var initialState = new HashSet<State>(); // workaround

		List<String> lines = Files.readAllLines(Paths.get(path),
				Charset.defaultCharset());

		parseSymbols(lines.remove(0), symbols);
		parseStates(lines, finalStates, initialState, statesMap);
		parseTransitions(lines, statesMap, symbols, transitions);

		ret.finalStates = finalStates;
		ret.transitionTable = transitions;
		ret.initialState = initialState.iterator().next();


		return ret;
	}

	private static void parseSymbols(String line, Set<Symbol> symbols) {
		var parts = line.replaceAll(" ", "").trim().split("\t");
		for (var part : parts) {
			var trimPart = part.trim();
			if (!trimPart.isEmpty()) {
				symbols.add(new Symbol(trimPart.charAt(0)));
			}
		}
	}

	private static void parseStates
	(List<String> lines, Set<State> finalStates, Set<State> initialState, Map<String, State> states) {
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

	private static void parseTransitions
	(List<String> lines, Map<String, State> states, Set<Symbol> symbols, Map<State, Map<Symbol, Set<State>>> transitions) {
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		var symbols = this.getAlphabet();
		this.fillHeader(sb, symbols);
		this.fillBody(sb, symbols);
		return sb.toString();
	}

	private Set<Symbol> getAlphabet() {
		var symbols = new LinkedHashSet<Symbol>();
		for (var entry : this.transitionTable.entrySet()) {
			var transition = entry.getValue();
			if (transition == null || transition.isEmpty()) {
				continue;
			}
			symbols.addAll(transition.keySet());
		}

		return symbols;
	}

	private void fillHeader(StringBuilder sb, Set<Symbol> symbols) {
		for (var symbol : symbols) {
			sb.append("\t" + symbol);
		}
		sb.append("\n");
	}

	private void fillBody(StringBuilder sb, Set<Symbol> symbols) {
		var states = this.transitionTable.keySet();
		for (var state : states) {
			if (this.isInitialState(state)) {
				sb.append(">");
			}
			if (this.isFinalState(state)) {
				sb.append("*");
			}
			sb.append(state.getLabel());

			var transitions = this.transitionTable.get(state);
			for (var symbol : symbols) {
				sb.append("\t");
				if (transitions.containsKey(symbol)) {
					sb.append(this.getNextStateSetString(transitions.get(symbol)));
				} else {
					sb.append("-");
				}
			}
			sb.append("\n");

		}
	}

	private boolean isInitialState(State state) {
		return this.initialState.equals(state);
	}


	private String getNextStateSetString(Set<State> states) {
		if (states == null || states.isEmpty()) {
			return "-";
		} else if (states.size() == 1) {
			return states.iterator().next().getLabel();
		}

		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (var state : states) {
			sb.append(state.getLabel() + ",");
		}
		var lastPos = sb.lastIndexOf(",");
		sb.replace(lastPos, lastPos + 1, "}");
		return sb.toString();

	}

	public void resetAutomata() {
		this.currentState = this.initialState;
	}

	public boolean isFinalState(State state) {
		return this.finalStates.contains(state);
	}

	// TODO: add verificacao de que eh deterministico?
	public State transitByChar(Character ch) {
		this.oldState = this.currentState;
		var transition = this.transitionTable.get(this.currentState);
		var symbol = new Symbol(ch);
		this.currentState = transition.get(symbol).iterator().next();
		return this.currentState;
	}

	// TODO: deixar esse metodo?
	public boolean acceptWord(String str) {
		if (!this.isDeterministic()) {
			System.out.println("Autômato não determinístico.");
		}
		this.currentState = this.initialState;
		try {
			for (int i = 0; i < str.length(); i++) {
				var transition = this.transitionTable.get(this.currentState);
				var symbol = new Symbol(str.charAt(i));
				this.currentState = transition.get(symbol).iterator().next();
			}
		} catch (Exception ex) {
			return false;
		}
		return this.isFinalState(this.currentState);
	}

	private boolean isDeterministic() {
		for (var entry : this.transitionTable.entrySet()) {
			var transition = entry.getValue();
			if (transition == null || transition.isEmpty()) {
				continue;
			}

			if (transition.containsKey(Symbol.EPSILON)) {
				return false;
			}

			for (var nextStates : transition.values()) {
				if (nextStates.size() > 1) {
					return false;
				}
			}
		}

		return true;
	}
}
