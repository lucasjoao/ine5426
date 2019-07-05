package br.ufsc.ine5426.compiladorxpp.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Scope {

	@EqualsAndHashCode.Include
	private int id;
	private ScopeType type;

	@Override
	public String toString() {
		return this.id + " " + this.type.toString();
	}

}
