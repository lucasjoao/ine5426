/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Jacyara Bosse, Lucas João Martins e Nathália Liz de Brito.
 *
 */

package br.ufsc.ine5426.compiladorxpp.lexicalanalyzer;

/**
 * Define os tipos de tokens que a linguagem X++ possui.
 *
 */
public enum TokenType {
	/**
	 * Palavra reservada.
	 */
	PR,
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
	INT_CONSTANT,
	STRING_CONSTANT,
	/**
	 * Ponto (.).
	 */
	POINT;
}