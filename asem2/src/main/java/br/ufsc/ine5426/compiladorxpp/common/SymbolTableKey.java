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
 * Classe que representa a chave da tabela de símbolos utilizada no projeto.
 *
 */
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
