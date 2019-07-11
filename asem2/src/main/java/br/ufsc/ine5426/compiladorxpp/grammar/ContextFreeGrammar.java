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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufsc.ine5426.compiladorxpp.common.Symbol;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Classe que representa a gramática utilizada para realizar a análise sintática no projeto.
 */
@Getter
@NoArgsConstructor
public class ContextFreeGrammar {

	private HashMap<Symbol, List<Body>> productions;
	private Symbol initialSymbol;

	public static ContextFreeGrammar Load(String path) throws IOException {
		var cfg = new ContextFreeGrammar();
		List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
		var productions = new HashMap<Symbol, List<Body>>();
		int bodyIdOffset = 0;
		for (var row : lines) {
			var columns = row.split("\t");
			var symbol = new Symbol(columns[0].trim());
			var bodies = extractBodies(Arrays.asList(columns), bodyIdOffset);
			productions.put(symbol, bodies);
			bodyIdOffset += bodies.size();
			if (productions.size() == 1) {
				cfg.initialSymbol = symbol;
			}
		}
		cfg.productions = productions;
		return cfg;
	}

	private static List<Body> extractBodies(List<String> columns, int bodyIdOffset) {
		int bodyId = bodyIdOffset;
		List<Body> bodies = new ArrayList<>();
		for (int i = 1; i < columns.size(); i++) {
			Body body = new Body(bodyId++);
			var symbolsText = columns.get(i).split(" ");
			for (var symbolText : symbolsText) {
				body.addSymbol(new Symbol(symbolText.trim()));
			}
			bodies.add(body);
		}

		return bodies;
	}

	public Set<Symbol> getAllVariables() {
		return this.productions.keySet().stream().collect(Collectors.toSet());
	}

	public Set<Symbol> getAllTerminals() {
		Set<Symbol> terminals = new HashSet<>();
		this.productions.values().forEach(i -> i.forEach(b -> terminals
				.addAll(b.getSymbols().stream().filter(s -> !s.isNonTerminal()).collect(Collectors.toSet()))));
		return terminals;
	}

	/**
	 * Retorna uma String que possui um formato similar ao .txt que alimenta o CFG.
	 * Utilizado para debug.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		this.productions.entrySet().forEach(entry -> {
			builder.append(entry.getKey().toString());
			builder.append(" ");
			entry.getValue().forEach(value -> {
				builder.append(value.toString());
				builder.append(" ");
			});
			builder.append("\n");
		});
		return builder.toString();
	}
}
