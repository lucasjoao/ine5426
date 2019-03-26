/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Lucas João Martins e Nathália Liz de Brito.
 *
 */

package br.ufsc.compiladores.grammar;

import br.ufsc.compiladores.common.Symbol;
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
        return this.getVariable() == null && getTerminal() != null;
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ProductionRule) {
            ProductionRule productionRule = (ProductionRule) obj;
            return productionRule.variable == variable && productionRule.terminal == terminal;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = terminal != null ? terminal.hashCode() : 0;
        result = 31 * result + (variable != null ? variable.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String ret = terminal.toString();
        if (variable != null) {
            ret += variable.toString();
        }
        return ret;
    }
}
