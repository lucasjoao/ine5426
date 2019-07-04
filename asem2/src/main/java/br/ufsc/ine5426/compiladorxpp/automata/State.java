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

import static br.ufsc.ine5426.compiladorxpp.common.Constants.ERROR_STATE;
import static br.ufsc.ine5426.compiladorxpp.common.Constants.ERROR_STATE_CH_SPECIAL;

import br.ufsc.ine5426.compiladorxpp.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Classe que representa os estados do {@link FiniteAutomata}.
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class State implements Comparable<State> {

	/**
	 * Inteiro que é o identificador do estado.
	 */
	private int id;
	/**
	 * String que representa o nome do estado.
	 */
	private String label;

	/**
	 * Necessário por causa das estruturas de dados que o State compõe.
	 */
	@Override
	public int compareTo(State state) {
		return this.getId() - state.getId();
	}

	/**
	 * Dois State são iguais se os seus identificadores são idênticos.
	 */
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

	/**
	 * hashCode é reimplementado por causa que o State é utilizado em estruturas de
	 * dados que utilizam desse valor.
	 *
	 * @see FiniteAutomata#Load(String)
	 */
	@Override
	public int hashCode() {
		return this.getId() ^ (this.getId() >>> 32);
	}

	/**
	 * Verifica se o estado é de erro, ou seja, se ele é igual
	 * {@link Constants#ERROR_STATE} ou {@link Constants#ERROR_STATE_CH_SPECIAL}.
	 *
	 * @param label que representa o estado que será verificado
	 * @return true se o estado representado pela label é de erro
	 */
	public static boolean isErrorState(String label) {
		return ERROR_STATE.equals(label) || ERROR_STATE_CH_SPECIAL.equals(label);
	}
}
