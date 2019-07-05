package br.ufsc.ine5426.compiladorxpp.semanticanalyzer;

import br.ufsc.ine5426.compiladorxpp.syntacticanalyzer.LL1;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SemanticAnalyzer {
	
	private LL1 ll1;
	
	private String result;
	
	public SemanticAnalyzer(LL1 ll1) {
		this.ll1 = ll1;
	}
	
	public boolean compile(String path) {
		if (ll1.compile(path)) {
			// TODO: add mensagens
			return checkType() && checkVariableScope() && checkBreak();
		} else {
			// TODO: add mensagens
			return false;
		}
	}
	
	private boolean checkType() {
		return true;
	}
	
	private boolean checkVariableScope() {
		return true;
	}
	
	private boolean checkBreak() {
		return true;
	}

}
