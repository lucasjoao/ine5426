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
import lombok.Setter;
import lombok.ToString;

/**
 * Classe que representa um Token.
 *
 */
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString(onlyExplicitlyIncluded = true)
public class Token {

	@ToString.Include
	private TokenType type;
	/**
	 * O lexema que faz o token.
	 */
	private String name;
	/**
	 * Linha em que o token foi encontrado.
	 */
	private int line;
	/**
	 * Coluna em que o começo do token foi encontrado.
	 */
	private int column;

	@ToString.Include
	private IdentType identType;

	private Scope scope;

	public boolean isDuplicate(Token other) {
		return this.type.equals(other.getType()) && this.name.equals(other.getName())
				&& this.identType.equals(other.getIdentType()) && this.scope.equals(other.getScope());
	}
}
