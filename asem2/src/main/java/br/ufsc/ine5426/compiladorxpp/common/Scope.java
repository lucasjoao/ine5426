package br.ufsc.ine5426.compiladorxpp.common;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Scope {

	private int id;
	private ScopeType type;

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Scope)) {
			return false;
		}
		Scope other = (Scope) obj;
		return this.id == other.id;
	}

}
