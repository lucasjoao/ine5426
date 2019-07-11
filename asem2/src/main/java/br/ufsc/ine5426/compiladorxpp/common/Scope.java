/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Jacyara Bosse, Lucas João Martins e Nathália Liz de Brito.
 *
 */

package br.ufsc.ine5426.compiladorxpp.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Representa o escopo de um trecho do código, ou seja, utilizado para dizer qual o escopo de um Token.
 *
 * @see Token
 * @see ScopeType
 */
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
