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

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine5426.compiladorxpp.common.Symbol;
import lombok.Getter;

/**
 * Classe que representa o corpo de uma produção da Gramática.
 *
 */
@Getter
public class Body {

	private int id;
	private List<Symbol> symbols;

	public Body(int id) {
		this.id = id;
		this.symbols = new ArrayList<>();
	}

	public void addSymbol(Symbol symbol) {
		this.symbols.add(symbol);
	}

	@Override
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

	/**
	 * Reimplementado para facilitar a realização de outro toString.
	 *
	 * @see ContextFreeGrammar#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		this.symbols.forEach(i -> {
			builder.append(i.toString());
			builder.append(" ");
		});
		return builder.toString();
	}
}
