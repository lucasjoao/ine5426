package br.ufsc.ine5426.compiladorxpp.intermediatecodegenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufsc.ine5426.compiladorxpp.common.Constants;
import br.ufsc.ine5426.compiladorxpp.common.Token;
import br.ufsc.ine5426.compiladorxpp.common.TokenType;
import br.ufsc.ine5426.compiladorxpp.semanticanalyzer.SemanticAnalyzer;

public class IntermediateCodeGenerator {
	
		private List<Token> tokens;
		private StringBuilder intermediateCode;
		private static final String BREAK_LINE = "\n";
		private Set<Integer> seenIds;
		
		private int labelCounter;
		// XXX: salvar ultima label de saída para break???
	
		public IntermediateCodeGenerator(SemanticAnalyzer semanticAnalyzer) {
			this.tokens = semanticAnalyzer.getTokens();
			this.intermediateCode = new StringBuilder();
			this.seenIds = new HashSet<Integer>();
			this.labelCounter = 0;
		}

		public void run() {
			for (int i = 0; i < tokens.size(); i++) {
				if (!seenIds.contains(i)) {
					seenIds.clear();
					operate(i);
				}
			}
			
			intermediateCode.append("FINAL:");
			intermediateCode.append(BREAK_LINE);
			intermediateCode.append("exit");
		}

		private void operate(int index) {
			this.seenIds.add(index);
			Token token = tokens.get(index);
			if (TokenType.PR.equals(token.getType())) {
				if (Constants.BREAK.equals(token.getName())) {
					createToBreak(index);
				} else if (Constants.FOR.equals(token.getName())) {
					createToFor(index);
				} else if (Constants.IF.equals(token.getName())) {
					createToIf(index);
				} else if (Constants.IFELSE.equals(token.getName())) {
					createToIfElse(index);
				} else if (Constants.RETURN.equals(token.getName())) {
					createToReturn(index);
				} else if (Constants.PRINT.equals(token.getName())) {
					createToReadOrPrint(index, "out");
				} else if (Constants.READ.equals(token.getName())) {
					createToReadOrPrint(index, "in");
				}
			} else if (TokenType.IDENT.equals(token.getType())) { 
				createToIdent(index);
			} else {
				// XXX: verificar se há problema com a linha
				intermediateCode.append(token.getName());
			}
		}
		
		private void createToReadOrPrint(int index, String toAppend) {
			intermediateCode.append(toAppend);
			for (int i = index + 1; i < tokens.size(); i++) {
				this.seenIds.add(i);
				Token token = tokens.get(i);
				if (TokenType.DELIMITER.equals(token.getType()) && ";".equals(token.getName())) {
					break;
				} else {
					intermediateCode.append(" " + token.getName());
				}
			}
			intermediateCode.append(BREAK_LINE);
		}

		private void createToIdent(int index) {
			
		}

		private void createToReturn(int index) {
			intermediateCode.append("GOTO FINAL");
			for (int i = index + 1; i < tokens.size(); i++) {
				this.seenIds.add(i);
				Token token = tokens.get(i);
				if (TokenType.DELIMITER.equals(token.getType()) && ";".equals(token.getName())) {
					break;
				}
			}
			intermediateCode.append(BREAK_LINE);
			
		}

		private void createToIfElse(int index) {
			// TODO Auto-generated method stub
			
		}

		private void createToIf(int index) {
			// TODO Auto-generated method stub
			
		}

		private void createToFor(int index) {
			// TODO Auto-generated method stub
			
		}

		private void createToBreak(int index) {
			// TODO Auto-generated method stub
			
		}

		public void print() {
			System.out.println(intermediateCode.toString());
		}

}
