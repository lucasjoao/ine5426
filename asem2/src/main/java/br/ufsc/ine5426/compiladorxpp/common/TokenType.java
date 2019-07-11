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

import java.util.Set;

/**
 * Define os tipos de tokens que a linguagem X++ possui.
 *
 */
public enum TokenType {
	/**
	 * Palavra reservada.
	 */
	PR,
	/**
	 * Identificador.
	 */
	IDENT,
	/**
	 * Delimitador.
	 */
	DELIMITER,
	/**
	 * Operador relacional.
	 */
	RELOP,
	/**
	 * Operador aritmético.
	 */
	AROP,
	/**
	 * Abertura de bloc.
	 */
	BLOCK_OPEN,
	/**
	 * Fechamento de bloco.
	 */
	BLOCK_CLOSE,
	/**
	 * Números inteiros.
	 */
	INT_CONSTANT,
	/**
	 * String entre aspas duplas.
	 */
	STRING_CONSTANT,
	/**
	 * Ponto (.).
	 */
	POINT;

	/**
	 * Tipos de Tokens que não são impressos de maneira implícitas no GCI.
	 *
	 *  @return conjunto com os tipos especificados
	 */
	public static Set<TokenType> getNotPrintableTypes() {
		return Set.of(BLOCK_OPEN, BLOCK_CLOSE, DELIMITER);
	}
}