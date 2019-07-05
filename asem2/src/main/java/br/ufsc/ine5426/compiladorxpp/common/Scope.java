package br.ufsc.ine5426.compiladorxpp.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Scope {

	private int id;
	private ScopeType type;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Scope) {
			Scope scope = (Scope) obj;
			return this.id == scope.getId();
		}
		return false;
	}

}
