package br.ufsc.ine5426.compiladorxpp.common;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SymbolTableKey {

	private Scope scope;
	private String lexeme;

	@Override
	public int hashCode() {
		return Objects.hash(this.lexeme, this.scope);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SymbolTableKey)) {
			return false;
		}
		SymbolTableKey other = (SymbolTableKey) obj;
		return Objects.equals(this.lexeme, other.lexeme) && Objects.equals(this.scope, other.scope);
	}

	@Override
	public String toString() {
		return this.scope.getId() + " " + this.lexeme;
	}

}
