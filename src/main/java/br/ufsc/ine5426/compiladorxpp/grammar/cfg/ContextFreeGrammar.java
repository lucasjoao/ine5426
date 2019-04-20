package br.ufsc.ine5426.compiladorxpp.grammar.cfg;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import br.ufsc.ine5426.compiladorxpp.common.Symbol;

public class ContextFreeGrammar {

	private HashMap<Symbol, List<Body>> productions;
	private int bodyId;

	public Symbol getInitialSymbol() {
		return this.initialSymbol;
	}

	private Symbol initialSymbol;

	public ContextFreeGrammar(){
	}

	public Set<Symbol> getAllVariables(){
		return this.productions.keySet().stream().collect(Collectors.toSet());
	}

	public Map<Symbol, List<Body>> getProductions(){
		return this.productions;
	}

	public Set<Symbol> getAllTerminals(){
		Set<Symbol> terminals = new HashSet<>();
		this.productions.values().forEach(
				i->i.forEach(
						b->terminals.addAll(b.getSymbols().stream().filter(s->!s.isVariable()).collect(Collectors.toSet()))
						));
		return terminals;
	}

	private static List<Body> extractBodies(List<String> columns, int bodyIdOffset){
		int bodyId = bodyIdOffset;
		List<Body> bodies = new ArrayList<>();
		for (int i = 1; i < columns.size(); i++){
			Body body = new Body(bodyId++);
			var symbolsText = columns.get(i).split(" ");
			for(var symbolText : symbolsText){
				body.addSymbol(new Symbol(symbolText.trim()));
			}
			bodies.add(body);
		}

		return bodies;
	}

	public static ContextFreeGrammar Load(String path) throws IOException {
		var cfg = new ContextFreeGrammar();
		List<String> lines = Files.readAllLines(Paths.get(path),
				Charset.defaultCharset());
		var productions = new  HashMap<Symbol, List<Body>>();
		int bodyIdOffset = 0;
		for (var row: lines) {
			var columns = row.split("\t");
			var symbol = new Symbol(columns[0].trim());
			var bodies = extractBodies(Arrays.asList(columns), bodyIdOffset);
			productions.put(symbol,bodies);
			bodyIdOffset += bodies.size();
			if (productions.size() == 1){
				cfg.initialSymbol = symbol;
			}
		}
		cfg.productions = productions;
		return cfg;
	}
}
