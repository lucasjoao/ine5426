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

import java.text.Normalizer;

import br.ufsc.ine5426.compiladorxpp.automata.FiniteAutomata;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * Classe que representa os símbolos de entrada do {@link FiniteAutomata}.
 *
 */
@ToString
@AllArgsConstructor
public class Symbol {

	/**
	 * String que representa o símbolo de entrada.
	 */
	private String content;

	/**
	 * É feito um tratamento sobre a entrada para deixá-la em caixa baixa e sem
	 * acentos. Dessa forma, considera-se que se for feito a leitura no programa
	 * fonte do símbolo 'Á', o mesmo será tratado como símbolo 'a'.
	 *
	 * @param symbol char que será o símbolo de entrada em string
	 */
	public Symbol(Character symbol) {
		this.content = Normalizer.normalize(Character.toString(Character.toLowerCase(symbol)), Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "");
	}

	/**
	 * Dois Symbol são iguais se os seus conteúdos são idênticos.
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj instanceof Symbol) {
			Symbol symbol = (Symbol) obj;
			return this.content.equals(symbol.content);
		}
		return false;
	}

	/**
	 * hashCode é reimplementado por causa que o Symbol é utilizado em estruturas de
	 * dados que utilizam desse valor.
	 *
	 * @see FiniteAutomata#Load(String)
	 */
	@Override
	public int hashCode() {
		return 31 + this.content.hashCode();
	}

	public boolean isNonTerminal() {
		return this.content != null && this.content.startsWith("<") && this.content.endsWith(">");
	}

}
