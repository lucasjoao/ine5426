/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Jacyara Bosse, Lucas João Martins e Nathália Liz de Brito.
 *
 */

package br.ufsc.ine5426.compiladorxpp.automata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//TODO: tudo pronto, refatorar aqui
@Getter
@Setter
@AllArgsConstructor
public class State implements Comparable<State> {

	private int id;
	private String label;

	// TODO: documentar que eh esses valores por causa do automato
	public static final String RETRACT_STATE = "q27";
	public static final String ERROR_STATE = "q28";

	@Override
	public int compareTo(State state) {
		return this.getId() - state.getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof State) {
			State state = (State) obj;
			return this.getId() == state.getId();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getId() ^ (this.getId() >>> 32);
	}

	public State copy() {
		return new State(this.id, this.label);
	}
}
