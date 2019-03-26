package br.ufsc.compiladores.grammar.cfg;

import br.ufsc.compiladores.common.Symbol;

import java.util.ArrayList;
import java.util.List;

public class Body {
    private List<Symbol> symbols;

    public int getId() {
        return id;
    }

    private int id;

    public Body(int id){
        this.id = id;
        symbols = new ArrayList<>();
    }

    public void addSymbol(Symbol symbol){
        symbols.add(symbol);
    }

    public List<Symbol> getSymbols(){
        return symbols;
    }


        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Body) {
                Body body = (Body) obj;
                return body.id == id;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return (int) (id ^ (id >>> 32));
    }


}
