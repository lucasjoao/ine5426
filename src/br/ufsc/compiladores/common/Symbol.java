/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Lucas João Martins e Nathália Liz de Brito.
 *
 */

package br.ufsc.compiladores.common;

public class Symbol {
    private String content;

    public static final Symbol EPSILON = new Symbol('$');
    public static final Symbol CFG_EPSILON = new Symbol('&');
    public static final Symbol CFG_EMPTY_STACK = new Symbol('$');
    public static final Symbol ESCAPE = new Symbol('\'');

    public Symbol(Character symbol) {
        this.content = ""+symbol;
    }

    public Symbol(String symbol) {
        this.content = symbol;
    }

    @Override
    public String toString() {
        return content;
    }

    public boolean isVariable(){
        return content != null && content.startsWith("<") && content.endsWith(">");
    }

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

    @Override
    public int hashCode() {
        return 31 + this.content.hashCode();
    }
}



