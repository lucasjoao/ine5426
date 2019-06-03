package br.ufsc.ine5426.compiladorxpp.syntacticanalyzer;

import static br.ufsc.ine5426.compiladorxpp.common.Constants.CFG_EMPTY_STACK;
import static br.ufsc.ine5426.compiladorxpp.common.Constants.CFG_EPSILON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import br.ufsc.ine5426.compiladorxpp.common.Symbol;
import br.ufsc.ine5426.compiladorxpp.grammar.Body;
import br.ufsc.ine5426.compiladorxpp.grammar.ContextFreeGrammar;
import br.ufsc.ine5426.compiladorxpp.lexicalanalyzer.LexicalAnalyser;
import br.ufsc.ine5426.compiladorxpp.lexicalanalyzer.Token;
import br.ufsc.ine5426.compiladorxpp.lexicalanalyzer.TokenType;

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
		this.grammar = ContextFreeGrammar.Load(grammarPath);
		this.firsts = new HashMap<>();
		this.follows = new HashMap<>();
		this.stack = new Stack<>();
		this.errors = new ArrayList<>();
		this.prepareLLC();
	}

	private void prepareLLC() {
		this.firsts = new HashMap<>();
		this.follows = new HashMap<>();
		for (var key : this.grammar.getProductions().keySet()) {
			this.firsts.put(key, new HashSet<>());
			this.follows.put(key, new HashSet<>());
		}
		this.generateFirst();
		this.generateFollow();
		this.generateTable();

	}

	public void generateFirst() {
		boolean hasChanges;
		var productions = this.grammar.getProductions();
		do {
			hasChanges = false;
			for (var key : productions.keySet()) {
				for (var body : productions.get(key)) {
					var symbols = this.genBodyFirst(body, 0);
					if (this.firsts.get(key).addAll(symbols)) {
						hasChanges = true;
					}
				}
			}
		} while (hasChanges);
	}

	public void generateFollow() {
		this.follows.get(this.grammar.getInitialSymbol()).add(CFG_EMPTY_STACK);

		boolean hasChanges;
		var productions = this.grammar.getProductions();
		do {
			hasChanges = false;
			for (var key : productions.keySet()) {
				for (var body : productions.get(key)) {
					int offset = 0;
					for (var bodySymbol : body.getSymbols()) {
						offset++;
						if (!bodySymbol.isNonTerminal()) {
							continue;
						}
						var firstSymbols = this.genBodyFirst(body, offset);
						for (var symbol : firstSymbols) {
							if (symbol.equals(CFG_EPSILON)) {
								continue;
							}

							if (this.follows.get(bodySymbol).add(symbol)) {
								hasChanges = true;
							}
						}

						if (firstSymbols.contains(CFG_EPSILON)) {
							if (this.follows.get(bodySymbol).addAll(this.follows.get(key))) {
								hasChanges = true;
							}
						}
					}
				}
			}
		} while (hasChanges);

	}

	private void generateTable() {

		Body eBody = new Body(-1);
		eBody.addSymbol(CFG_EPSILON);
		this.table = new HashMap<>();
		var productions = this.grammar.getProductions();
		for (var key : productions.keySet()) {
			if (!this.table.containsKey(key)) {
				this.table.put(key, new HashMap<>());
			}
			for (var body : productions.get(key)) {
				var symbols = this.genBodyFirst(body, 0);
				for (var symbol : symbols) {
					if (symbol.equals(CFG_EPSILON)) {
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
			if (!symbol.isNonTerminal()) {
				first.add(symbol);
				return first;
			}

			if (!this.firsts.get(symbol).contains(CFG_EPSILON)) {
				first.addAll(this.firsts.get(symbol));
				return first;
			}

			first.addAll(this.firsts.get(symbol));
		}
		first.add(CFG_EPSILON);
		return first;
	}

	public boolean compile(String path) {
		this.errors = new ArrayList<>();
		if (this.lexicalAnalyser.compile(path)) {
			this.stack.clear();
			this.stack.push(this.grammar.getInitialSymbol());
			while (this.lexicalAnalyser.hasToken()) {
				Token token = this.lexicalAnalyser.getNextToken();
				this.operate(token);
				if (this.errors.size() > 0) {
					return false;
				}
			}
		} else {
			this.errors.addAll(this.lexicalAnalyser.getErrors());
			return false;
		}
		if (!this.stack.isEmpty()) {
			this.errors.add("A pilha não terminou vazia! Esqueceu de alguma coisa? ->" + this.stack.toString());
		}
		return this.errors.size() == 0;
	}

	private void operate(Token token) {
		Symbol symbol = this.convertToken(token);
		while (!this.stack.isEmpty()) {
			var top = this.stack.peek();
			if (!top.isNonTerminal() && symbol.equals(top)) {
				this.stack.pop();
				return; // avanca leitura
			} else {
				try {
					var symbols = this.table.get(top).get(symbol).getSymbols();
					this.stack.pop();
					for (int i = symbols.size() - 1; i >= 0; i--) {
						Symbol is = symbols.get(i);
						if (!is.equals(CFG_EPSILON)) {
							this.stack.push(is);
						}
					}

				} catch (Exception e) {
					this.errors.add(String.format("Erro sintático na linha (%s), palavra (%s): lexema com problema: %s",
							token.getLine(), token.getColumn(), token.getName()));
					return;
				}
			}
		}
	}

	private Symbol convertToken(Token token) {
		String equivalent = "";
		if (token.getType() == TokenType.IDENT) {
			equivalent = "id";
		} else if (token.getType() == TokenType.INT_CONSTANT) {
			equivalent = "num";
		} else {
			equivalent = token.getName();
		}
		return new Symbol(equivalent);
	}
}
