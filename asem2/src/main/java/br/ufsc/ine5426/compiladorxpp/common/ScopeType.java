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

/**
 * Enum utilizado para identificar o tipo de escopo. Escopo pode ser criado por um if, por um for ou ser o default (NONE).
 *
 * @see Token
 */
public enum ScopeType {

	FOR, IF, NONE;

}
