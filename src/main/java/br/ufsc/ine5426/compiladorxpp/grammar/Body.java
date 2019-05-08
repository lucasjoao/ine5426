package br.ufsc.ine5426.compiladorxpp.grammar;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine5426.compiladorxpp.automata.Symbol;
import lombok.Getter;

@Getter
public class Body {

	private int id;
	private List<Symbol> symbols;

	public Body(int id) {
		this.id = id;
		symbols = new ArrayList<>();
	}

	public void addSymbol(Symbol symbol) {
		symbols.add(symbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Body) {
			Body body = (Body) obj;
			return body.id == id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id ^ (id >>> 32);
	}
}
