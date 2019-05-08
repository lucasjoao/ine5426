package br.ufsc.ine5426.compiladorxpp.syntacticanalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import br.ufsc.ine5426.compiladorxpp.automata.Symbol;
import br.ufsc.ine5426.compiladorxpp.grammar.Body;
import br.ufsc.ine5426.compiladorxpp.grammar.ContextFreeGrammar;
import br.ufsc.ine5426.compiladorxpp.lexicalanalyser.LexicalAnalyser;
import br.ufsc.ine5426.compiladorxpp.lexicalanalyser.Token;
import br.ufsc.ine5426.compiladorxpp.lexicalanalyser.TokenType;

public class LL1 {

	private List<String> errors = new ArrayList<>();
	private Map<Symbol, Map<Symbol, Body>> table;
	private ContextFreeGrammar grammar;
	private Map<Symbol, Set<Symbol>> firsts;
	private Map<Symbol, Set<Symbol>> follows;
	private LexicalAnalyser lexicalAnalyser;
	private Stack<Symbol> stack;

	public LL1(String grammarPath, LexicalAnalyser lexicalAnalyser) throws IOException {
		this.lexicalAnalyser = lexicalAnalyser;
		grammar = ContextFreeGrammar.Load(grammarPath);
		firsts = new HashMap<>();
		follows = new HashMap<>();
		stack = new Stack<>();
		errors = new ArrayList<>();
		prepareLLC();
	}

	private void prepareLLC() {
		firsts = new HashMap<>();
		follows = new HashMap<>();
		for (var key : this.grammar.getProductions().keySet()) {
			firsts.put(key, new HashSet<>());
			follows.put(key, new HashSet<>());
		}
		generateFirst();
		generateFollow();
		generateTable();

	}

	public void generateFirst() {
		boolean hasChanges;
		var productions = this.grammar.getProductions();
		do {
			hasChanges = false;
			for (var key : productions.keySet()) {
				for (var body : productions.get(key)) {
					var symbols = genBodyFirst(body, 0);
					if (this.firsts.get(key).addAll(symbols)) {
						hasChanges = true;
					}
				}
			}
		} while (hasChanges);
	}

	public void generateFollow() {
		this.follows.get(this.grammar.getInitialSymbol()).add(Symbol.CFG_EMPTY_STACK);

		boolean hasChanges;
		var productions = this.grammar.getProductions();
		do {
			hasChanges = false;
			for (var key : productions.keySet()) {
				for (var body : productions.get(key)) {
					int offset = 0;
					for (var bodySymbol : body.getSymbols()) {
						offset++;
						if (!bodySymbol.isVariable()) {
							continue;
						}
						var firstSymbols = genBodyFirst(body, offset);
						for (var symbol : firstSymbols) {
							if (symbol.equals(Symbol.CFG_EPSILON)) {
								continue;
							}

							if (this.follows.get(bodySymbol).add(symbol)) {
								hasChanges = true;
							}
						}

						if (firstSymbols.contains(Symbol.CFG_EPSILON)) {
							if (this.follows.get(bodySymbol).addAll(this.follows.get(key))) {
								hasChanges = true;
							}
						}
					}
				}
			}
		} while (hasChanges);

	}

	public boolean compile(String path) {
		errors = new ArrayList<>();
		if (lexicalAnalyser.compile(path)) {
			this.stack.clear();
			stack.push(grammar.getInitialSymbol());
			// TODO: criar metodos faltantes no LA
//			while (lexicalAnalyser.hasToken()) {
//				Token token = lexicalAnalyser.getNextToken();
//				this.operate(token);
//				if (errors.size() > 0) {
//					return false;
//				}
//			}
		} else {
			errors.addAll(lexicalAnalyser.getErrors());
			return false;
		}
		if (!stack.isEmpty()) {
			errors.add("A pilha não terminou vazia! Esqueceu de alguma coisa? ->" + stack.toString());
		}
		return errors.size() == 0;
	}

	private Symbol convertToken(Token token) {
		String equivalent = "";
		if (token.getType() == TokenType.IDENT) { // TODO: verificar se eh esse tipo mesmo
			equivalent = "id";
		} else if (token.getType() == TokenType.INT_CONSTANT) { // TODO: verificar se eh esse tipo mesmo
			equivalent = "num";
		} else {
			equivalent = token.getName();
		}
		return new Symbol(equivalent);
	}

	private void operate(Token token) {
		Symbol symbol = convertToken(token);
		while (!stack.isEmpty()) {
			var top = stack.peek();
			if (!top.isVariable() && symbol.equals(top)) {
				stack.pop();
				return; // avanca leitura
			} else {
				try {
					var symbols = table.get(top).get(symbol).getSymbols();
					stack.pop();
					for (int i = symbols.size() - 1; i >= 0; i--) {
						Symbol is = symbols.get(i);
						if (!is.equals(Symbol.CFG_EPSILON)) {
							stack.push(is);
						}
					}

				} catch (Exception e) {
					// TODO: verificar valores e frase
					errors.add(String.format("Erro sintático na linha (%s), palavra (%s): lexema com problema: %s",
							token.getLine(), token.getColumn(), token.getName()));
					return;
				}
			}
		}
	}

	private void generateTable() {

		Body eBody = new Body(-1);
		eBody.addSymbol(Symbol.CFG_EPSILON);
		table = new HashMap<>();
		var productions = this.grammar.getProductions();
		for (var key : productions.keySet()) {
			if (!this.table.containsKey(key)) {
				this.table.put(key, new HashMap<>());
			}
			for (var body : productions.get(key)) {
				var symbols = genBodyFirst(body, 0);
				for (var symbol : symbols) {
					if (symbol.equals(Symbol.CFG_EPSILON)) {
						this.follows.get(key).forEach(s -> this.table.get(key).put(s, eBody));
					} else {
						this.table.get(key).put(symbol, body);
					}

				}
			}
		}
	}

	private Set<Symbol> genBodyFirst(Body body, int offset) {
		HashSet<Symbol> first = new HashSet<>();

		var symbols = body.getSymbols();

		for (int i = offset; i < symbols.size(); i++) {
			var symbol = symbols.get(i);
			if (!symbol.isVariable()) {
				first.add(symbol);
				return first;
			}

			if (!this.firsts.get(symbol).contains(Symbol.CFG_EPSILON)) {
				first.addAll(this.firsts.get(symbol));
				return first;
			}

			first.addAll(this.firsts.get(symbol));
		}
		first.add(Symbol.CFG_EPSILON);
		return first;
	}

	public List<String> getErrors() {
		return errors;
	}
}
