package br.ufsc.ine5426.compiladorxpp.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SymbolTableKey {

	private Scope scope;
	private String lexeme;

	@Override
	public String toString() {
		return this.scope.getId() + " " + this.lexeme;
	}

}
