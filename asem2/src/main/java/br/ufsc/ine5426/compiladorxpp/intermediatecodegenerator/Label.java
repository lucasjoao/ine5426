/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Jacyara Bosse, Lucas João Martins e Nathália Liz de Brito.
 *
 */

package br.ufsc.ine5426.compiladorxpp.intermediatecodegenerator;

import lombok.Getter;

/**
 * Classe que representa as labels automáticas criadas para a geração de código intermediário.
 *
 * @see IntermediateCodeGenerator
 *
 */
@Getter
public class Label {

	private String name;

	public Label(int id) {
		this.name = "LABEL" + id;
	}

	public String getNameWithColon() {
		return this.name + ":";
	}

}
