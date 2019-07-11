package br.ufsc.ine5426.compiladorxpp.intermediatecodegenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufsc.ine5426.compiladorxpp.common.Constants;
import br.ufsc.ine5426.compiladorxpp.common.Scope;
import br.ufsc.ine5426.compiladorxpp.common.Token;
import br.ufsc.ine5426.compiladorxpp.common.TokenType;
import br.ufsc.ine5426.compiladorxpp.semanticanalyzer.SemanticAnalyzer;

public class IntermediateCodeGenerator {

	private List<Token> tokens;
	private StringBuilder intermediateCode;
	private static final String BREAK_LINE = "\n";
	private Set<Integer> seenIds;

	private int labelCounter;
	private int memCounter;
	// XXX: salvar ultima label de saída para break???

	public IntermediateCodeGenerator(SemanticAnalyzer semanticAnalyzer) {
		this.tokens = semanticAnalyzer.getTokens();
		this.intermediateCode = new StringBuilder();
		this.seenIds = new HashSet<Integer>();
		this.labelCounter = 0;
		this.memCounter = 0;
	}

	public void run() {
		for (int i = 0; i < this.tokens.size(); i++) {
			if (!this.seenIds.contains(i)) {
				this.seenIds.clear();
				this.operate(i);
			}
		}

		this.intermediateCode.append("FINAL:");
		this.intermediateCode.append(BREAK_LINE);
		this.intermediateCode.append("exit");
	}

	private void operate(int index) {
		this.seenIds.add(index);
		Token token = this.tokens.get(index);
		if (TokenType.PR.equals(token.getType())) {
			if (Constants.BREAK.equals(token.getName())) {
				this.createToBreak(index);
			} else if (Constants.FOR.equals(token.getName())) {
				this.createToFor(index);
			} else if (Constants.IF.equals(token.getName())) {
				this.createToIf(index);
			} else if (Constants.IFELSE.equals(token.getName())) {
				this.createToIfElse(index);
			} else if (Constants.RETURN.equals(token.getName())) {
				this.createToReturn(index);
			} else if (Constants.PRINT.equals(token.getName())) {
				this.createToReadOrPrint(index, "out");
			} else if (Constants.READ.equals(token.getName())) {
				this.createToReadOrPrint(index, "in");
			}
		} else if (TokenType.IDENT.equals(token.getType())) {
			this.createToIdent(index);
		} else {
			// XXX: verificar se há problema com a linha
			if (!TokenType.getNotPrintableTypes().contains(token.getType())) {
				this.intermediateCode.append(token.getName());
			}
		}
	}

	private void createToReadOrPrint(int index, String toAppend) {
		this.intermediateCode.append(toAppend);
		for (int i = index + 1; i < this.tokens.size(); i++) {
			this.seenIds.add(i);
			Token token = this.tokens.get(i);
			if (this.isSemiColon(token)) {
				break;
			} else {
				this.intermediateCode.append(" " + token.getName());
			}
		}
		this.intermediateCode.append(BREAK_LINE);
	}

	private void createToIdent(int index) {
		this.appendIdent(index);

		for (int i = index + 1; i < this.tokens.size(); i++) {
			this.seenIds.add(i);
			Token token = this.tokens.get(i);
			if (this.isSemiColon(token)) {
				break;
			} else if (TokenType.IDENT.equals(token.getType())) {
				this.appendIdent(i);
			}
		}
	}

	private void appendIdent(int index) {
		String toAppend = "$" + this.memCounter + " = " + this.tokens.get(index).getName();
		this.intermediateCode.append(toAppend);
		this.intermediateCode.append(BREAK_LINE);
		this.memCounter++;
	}

	private void createToReturn(int index) {
		this.intermediateCode.append("GOTO FINAL");
		for (int i = index + 1; i < this.tokens.size(); i++) {
			this.seenIds.add(i);
			Token token = this.tokens.get(i);
			if (this.isSemiColon(token)) {
				break;
			}
		}
		this.intermediateCode.append(BREAK_LINE);

	}

	private void createToIfElse(int index) {
		// TODO Auto-generated method stub

	}

	private void createToIf(int index) {
		int insideIfIndex = index;
		for (int i = index; i < this.tokens.size(); i++) {
			this.seenIds.add(i);
			Token token = this.tokens.get(i);
			if (TokenType.BLOCK_OPEN.equals(token.getType()) && "{".equals(token.getName())) {
				insideIfIndex = i + 1;
				break;
			} else {
				this.intermediateCode.append(token.getName() + " ");
			}
		}

		Label entryLabel = new Label(this.labelCounter++);
		Label exitLabel = new Label(this.labelCounter++);

		this.intermediateCode.append("== 0 GOTO " + exitLabel.getName());
		this.intermediateCode.append(BREAK_LINE);
		this.intermediateCode.append(entryLabel.getNameWithColon());
		this.intermediateCode.append(BREAK_LINE);

		Scope currentScope = this.tokens.get(insideIfIndex).getScope();
		for (int j = insideIfIndex; j < this.tokens.size(); j++) {
			Token token = this.tokens.get(j);
			if (token.getScope().equals(currentScope) && !this.seenIds.contains(j)) {
				this.operate(j);
			}
		}

		this.intermediateCode.append("GOTO " + exitLabel.getName());
		this.intermediateCode.append(BREAK_LINE);
		this.intermediateCode.append(exitLabel.getNameWithColon());
		this.intermediateCode.append(BREAK_LINE);
	}

	private void createToFor(int index) {
		// TODO Auto-generated method stub

	}

	private void createToBreak(int index) {
		// TODO Auto-generated method stub

	}

	private boolean isSemiColon(Token token) {
		return TokenType.DELIMITER.equals(token.getType()) && ";".equals(token.getName());
	}

	public void print() {
		System.out.println(this.intermediateCode.toString());
	}

}
