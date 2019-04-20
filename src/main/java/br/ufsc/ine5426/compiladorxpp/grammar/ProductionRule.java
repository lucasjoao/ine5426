/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Jacyara Bosse, Lucas João Martins e Nathália Liz de Brito.
 *
 */

package br.ufsc.ine5426.compiladorxpp.grammar;

import br.ufsc.ine5426.compiladorxpp.common.Symbol;
/*
 * Classe para denotar o "produtório". Por exemplo, na regra: S -> aA | a,
 * teríamos duas ProductionRule's com os valores aA e a, respectivamente.
 */
public class ProductionRule {
	private Symbol terminal;
	private Symbol variable;

	public ProductionRule(Symbol terminal, Symbol variable) {
		this.setTerminal(terminal);
		this.setVariable(variable);
	}

	public boolean isTerminal() {
		return this.getVariable() == null && this.getTerminal() != null;
	}

	public Symbol getVariable() {
		return this.variable;
	}

	public void setVariable(Symbol variable) {
		this.variable = variable;
	}

	public Symbol getTerminal() {
		return this.terminal;
	}

	public void setTerminal(Symbol terminal) {
		this.terminal = terminal;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof ProductionRule) {
			ProductionRule productionRule = (ProductionRule) obj;
			return productionRule.variable == this.variable && productionRule.terminal == this.terminal;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = this.terminal != null ? this.terminal.hashCode() : 0;
		result = 31 * result + (this.variable != null ? this.variable.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		String ret = this.terminal.toString();
		if (this.variable != null) {
			ret += this.variable.toString();
		}
		return ret;
	}
}
