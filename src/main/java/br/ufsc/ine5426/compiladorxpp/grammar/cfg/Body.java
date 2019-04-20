package br.ufsc.ine5426.compiladorxpp.grammar.cfg;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine5426.compiladorxpp.common.Symbol;

public class Body {
	private List<Symbol> symbols;

	public int getId() {
		return this.id;
	}

	private int id;

	public Body(int id){
		this.id = id;
		this.symbols = new ArrayList<>();
	}

	public void addSymbol(Symbol symbol){
		this.symbols.add(symbol);
	}

	public List<Symbol> getSymbols(){
		return this.symbols;
	}


	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Body) {
			Body body = (Body) obj;
			return body.id == this.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.id ^ (this.id >>> 32);
	}


}
