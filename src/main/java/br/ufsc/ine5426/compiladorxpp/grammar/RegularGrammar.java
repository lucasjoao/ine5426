/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Jacyara Bosse, Lucas João Martins e Nathália Liz de Brito.
 *
 */

package br.ufsc.ine5426.compiladorxpp.grammar;

import java.io.FileWriter;
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
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufsc.ine5426.compiladorxpp.automata.FiniteAutomata;
import br.ufsc.ine5426.compiladorxpp.automata.State;
import br.ufsc.ine5426.compiladorxpp.common.Symbol;

/*
 * Cada variável (não terminal), tem associado um conjunto de ProductionRule's
 */
public class RegularGrammar {
	private Map<Symbol, Set<ProductionRule>> productions;

	public RegularGrammar(Map<Symbol, Set<ProductionRule>> productions) {
		this.productions = productions;
	}

	public RegularGrammar() {

	}

	/*
	 * Basicamente, é o algoritmo que vimos em sala de aula:
	 * 1) Cada variavel da gramática tem um estado correspondente no autômato + criação de um estado final X
	 * 2) O estado inicial, é o estado correspondente ao não terminal inicial da gramática.
	 * 3) As transições são criadas da seguinte forma:
	 * Considere uma regra a S-> aA | bB. Estando no estado S e lendo um símbolo 'a', vamos para o estado A e
	 * lendo um símbolo 'b', vamos para B.
	 * 4) Quando um ProductionRule, é apenas um símbolo terminal, adicionamos uma
	 * transição para o estado X quando lido esse símbolo.
	 *
	 */
	public FiniteAutomata toFiniteAutomata() {
		State initialState = null;
		Map<State, Map<Symbol, Set<State>>> transitionTable = new LinkedHashMap<>();
		var finalStates = new HashSet<State>();
		int id = 0;
		var statex = new State(id, "q" + id++);
		transitionTable.put(statex, new LinkedHashMap<>());
		finalStates.add(statex);
		var stateMap = new LinkedHashMap<Symbol, State>();
		for (var symbol : this.productions.keySet()) {
			var state = new State(id, "q" + id++);
			stateMap.put(symbol, state);
		}
		initialState = stateMap.entrySet().iterator().next().getValue();
		for (var entry : this.productions.entrySet()) {
			var symbolMap = new LinkedHashMap<Symbol, Set<State>>();
			for (var rule : entry.getValue()) {
				if (!symbolMap.containsKey(rule.getTerminal())) {
					symbolMap.put(rule.getTerminal(), new TreeSet<State>());
				}
				symbolMap.get(rule.getTerminal()).add(rule.isTerminal() ? statex : stateMap.get(rule.getVariable()));
			}
			transitionTable.put(stateMap.get(entry.getKey()), symbolMap);
		}
		//minimize()
		return new FiniteAutomata(initialState, finalStates, transitionTable);
	}

	public static RegularGrammar Load(String path) throws IOException {
		RegularGrammar rg = new RegularGrammar();
		rg.productions = new LinkedHashMap<>();

		List<String> lines = Files.readAllLines(Paths.get(path),
				Charset.defaultCharset());
		String cleanLine = "";
		Pattern pattern = Pattern.compile("([A-Z])->([a-z&][A-Z]? ?\\|?)*");
		for (var line : lines) {
			cleanLine = line.replaceAll(" ", "").trim();
			Matcher m = pattern.matcher(cleanLine);
			if (!m.find() || !cleanLine.equals(m.group(0)) || m.group(2) == null) {
				//throw exception
				return null;
			}
			cleanLine = cleanLine.replaceAll("->", "|");
			var parts = cleanLine.split("\\|");
			var variable = new Symbol(parts[0].charAt(0));
			if (!rg.productions.containsKey(variable)) {
				rg.productions.put(variable, new LinkedHashSet<>());
			}
			for (int i = 1; i < parts.length; i++) {
				var token = parts[i];
				Symbol terminal = new Symbol(token.charAt(0));
				Symbol nonTerminal = token.length() > 1 ? new Symbol(token.charAt(1)) : null;
				rg.productions.get(variable).add(new ProductionRule(terminal, nonTerminal));
			}
		}

		return rg;
	}

	public void Save(String path) throws IOException {
		var fw = new FileWriter(path);
		fw.write(this.toString());
		fw.flush();
	}

	@Override
	public String toString() {
		var sb = new StringBuilder();
		for (var entry : this.productions.entrySet()) {
			sb.append(entry.getKey().toString());
			sb.append(" -> ");
			boolean first = true;
			for (var pv : entry.getValue()) {
				if (!first) {
					sb.append(" | ");
				} else {
					first = false;
				}
				sb.append(pv.toString());
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
