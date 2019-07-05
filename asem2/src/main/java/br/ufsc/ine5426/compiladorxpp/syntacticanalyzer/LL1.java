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
import lombok.Getter;

public class LL1 {

	private List<String> errors = new ArrayList<>();
	private Map<Symbol, Map<Symbol, List<Body>>> table;
	private ContextFreeGrammar grammar;
	private Map<Symbol, Set<Symbol>> firsts;
	private Map<Symbol, Set<Symbol>> follows;
	@Getter
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
		System.out.println(this.toString());
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
					this.helperTableCreation(key, symbol);
					if (symbol.equals(CFG_EPSILON)) {
						this.follows.get(key).forEach(s -> {
							this.helperTableCreation(key, s);
							this.table.get(key).get(s).add(eBody);
						});
					} else {
						this.table.get(key).get(symbol).add(body);
					}

				}
			}
		}
	}

	private void helperTableCreation(Symbol nonTerminal, Symbol symbol) {
		if (!this.table.get(nonTerminal).containsKey(symbol)) {
			this.table.get(nonTerminal).put(symbol, new ArrayList<>());
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
					this.errors.forEach(i -> System.out.println(i));
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
					System.out.println("Meu NT: " + top.toString());
					System.out.println("Meu símbolo: " + symbol.toString());
					this.table.get(top).keySet().forEach(i -> System.out.println("Espero: " + i.toString()));

					List<Body> bodies = this.table.get(top).get(symbol);

					List<Symbol> symbols;
					if (bodies.size() > 1) {
						symbols = this.defineWhichSymbols(bodies);
					} else {
						symbols = bodies.get(0).getSymbols();
					}

					this.stack.pop();
					for (int i = symbols.size() - 1; i >= 0; i--) {
						Symbol is = symbols.get(i);
						System.out.println("Símbolo add na pilha: " + is.toString());
						if (!is.equals(CFG_EPSILON)) {
							this.stack.push(is);
						}
					}

				} catch (Exception e) {
					this.errors.add(String.format("Erro sintático na linha (%s), coluna (%s): lexema com problema: %s",
							token.getLine(), token.getColumn(), token.getName()));
					return;
				}
			}
		}
	}

	private List<Symbol> defineWhichSymbols(List<Body> bodies) {
		var canReturnEpsilon = false;
		List<Symbol> eSymbols = null;

		for (Body body : bodies) {
			for (Symbol symbol : body.getSymbols()) {
				if (symbol.equals(CFG_EPSILON)) {
					canReturnEpsilon = true;
					eSymbols = body.getSymbols();
					break;
				}
			}
			if (canReturnEpsilon) {
				break;
			}
		}

		Token nextToken = this.lexicalAnalyser.seeNextToken();
		Symbol nextSymbol = this.convertToken(nextToken);
		List<Symbol> symbols = null;

		for (Body body : bodies) {
			for (Symbol symbol : body.getSymbols()) {
				var foundInProductions = false;
				if (symbol.isNonTerminal()) {
					foundInProductions = this.helperDefineWhichSymbols(symbol);
				}

				if (symbol.equals(nextSymbol) || foundInProductions) {
					symbols = body.getSymbols();
					break;
				}
			}
			if (symbols != null) {
				break;
			}
		}

		if (canReturnEpsilon && symbols == null) {
			symbols = eSymbols;
		}

		// if this be null, then we need debug looking for problems
		return symbols;
	}

	private boolean helperDefineWhichSymbols(Symbol symbolToSearch) {
		boolean result = false;
		List<Body> bodiesFromProduction = this.grammar.getProductions().get(symbolToSearch);
		for (Body body : bodiesFromProduction) {
			for (Symbol symbol : body.getSymbols()) {
				result = symbolToSearch.equals(symbol);

				if (!result && symbol.isNonTerminal()) {
					result = this.helperDefineWhichSymbols(symbol);
				}

				if (result) {
					break;
				}
			}
			if (result) {
				break;
			}
		}
		return result;
	}

	private Symbol convertToken(Token token) {
		String equivalent = "";
		if (token.getType() == TokenType.IDENT) {
			equivalent = "ident";
		} else if (token.getType() == TokenType.INT_CONSTANT) {
			equivalent = "int-constant";
		} else if (token.getType() == TokenType.STRING_CONSTANT) {
			equivalent = "string-constant";
		} else {
			equivalent = token.getName();
		}
		return new Symbol(equivalent);
	}

	/**
	 * Retorna uma String que possui o conjunto de First, Follow e a tabela do LL1.
	 * Utilizado para debug.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("FIRST:").append("\n");
		this.firsts.entrySet().forEach(entry -> {
			builder.append(entry.getKey().toString()).append(" ");
			entry.getValue().forEach(i -> {
				builder.append(i.toString()).append(" ");
			});
			builder.append("\n");
		});

		builder.append("\n");
		builder.append("FOLLOW:").append("\n");
		this.follows.entrySet().forEach(entry -> {
			builder.append(entry.getKey().toString()).append(" ");
			entry.getValue().forEach(i -> {
				builder.append(i.toString()).append(" ");
			});
			builder.append("\n");
		});

		builder.append("\n");
		builder.append("TABLE:").append("\n");
		this.table.entrySet().forEach(entry -> {
			builder.append(entry.getKey().toString()).append(" ");
			entry.getValue().entrySet().forEach(innerEntry -> {
				builder.append("TERMINAL: ").append(innerEntry.getKey().toString()).append(" ");
				builder.append("RESULTADO: ").append(innerEntry.getValue().toString()).append(" ");
			});
			builder.append("\n");
		});

		return builder.toString();
	}
}
