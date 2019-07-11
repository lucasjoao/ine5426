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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufsc.ine5426.compiladorxpp.common.Constants;
import br.ufsc.ine5426.compiladorxpp.common.Scope;
import br.ufsc.ine5426.compiladorxpp.common.Token;
import br.ufsc.ine5426.compiladorxpp.common.TokenType;
import br.ufsc.ine5426.compiladorxpp.common.TreeNode;
import br.ufsc.ine5426.compiladorxpp.semanticanalyzer.SemanticAnalyzer;

/**
 *  Classe que representa o gerador de código intermediário.
 *
 */
public class IntermediateCodeGenerator {

	private static final String FINAL = "FINAL:";
	private static final String BREAK_LINE = "\n";

	private List<Token> tokens;
	private StringBuilder intermediateCode;
	private Set<Integer> seenIds;
	private int labelCounter;
	private int memCounter;
	private int lastLineOperate;
	private TreeNode treeScope;
	private String currentExitOfFor;

	public IntermediateCodeGenerator(SemanticAnalyzer semanticAnalyzer) {
		this.tokens = semanticAnalyzer.getTokens();
		this.treeScope = semanticAnalyzer.getLl1().getLexicalAnalyser().getTreeScope();
		this.intermediateCode = new StringBuilder();
		this.seenIds = new HashSet<Integer>();
		this.labelCounter = 0;
		this.memCounter = 0;
		this.lastLineOperate = 1;
		this.currentExitOfFor = FINAL;
	}

	public void run() {
		for (int i = 0; i < this.tokens.size(); i++) {
			if (!this.seenIds.contains(i)) {
				// TODO: retestar coisas para ver necessidade disso, removido para fazer ifelse
				// quando só faltava o for e o break
				//				this.seenIds.clear();
				this.operate(i);
			}
			this.lastLineOperate = this.tokens.get(i).getLine();
		}

		this.intermediateCode.append(BREAK_LINE);
		this.intermediateCode.append(FINAL);
		this.intermediateCode.append(BREAK_LINE);
		this.intermediateCode.append("exit");
	}

	private void operate(int index) {
		this.seenIds.add(index);
		Token token = this.tokens.get(index);
		if (TokenType.PR.equals(token.getType())) {
			if (Constants.BREAK.equals(token.getName())) {
				this.createToBreak();
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
			if (!TokenType.getNotPrintableTypes().contains(token.getType())) {
				if (this.lastLineOperate != token.getLine()) {
					this.intermediateCode.append(BREAK_LINE);
				}
				this.intermediateCode.append(token.getName() + " ");
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
		int insideIfElseIndex = index;
		for (int i = index; i < this.tokens.size(); i++) {
			this.seenIds.add(i);
			Token token = this.tokens.get(i);
			if (TokenType.BLOCK_OPEN.equals(token.getType()) && "{".equals(token.getName())) {
				insideIfElseIndex = i + 1;
				break;
			} else {
				this.intermediateCode.append(token.getName() + " ");
			}
		}

		Label entryLabel = new Label(this.labelCounter++);
		Label elseLabel = new Label(this.labelCounter++);
		Label exitLabel = new Label(this.labelCounter++);

		this.intermediateCode.append("== 0 GOTO " + elseLabel.getName());
		this.intermediateCode.append(BREAK_LINE);
		this.intermediateCode.append(entryLabel.getNameWithColon());
		this.intermediateCode.append(BREAK_LINE);

		Scope currentScope = this.tokens.get(insideIfElseIndex).getScope();
		for (int j = insideIfElseIndex; j < this.tokens.size(); j++) {
			Token token = this.tokens.get(j);
			if (token.getScope().equals(currentScope) && !this.seenIds.contains(j)) {
				this.operate(j);
			}
		}

		this.intermediateCode.append("GOTO " + exitLabel.getName());
		this.intermediateCode.append(BREAK_LINE);

		this.intermediateCode.append(elseLabel.getNameWithColon());
		this.intermediateCode.append(BREAK_LINE);

		Scope parentOfCurrentScope = this.treeScope.findByScope(currentScope).getParent().getScope();
		for (int k = insideIfElseIndex; k < this.tokens.size(); k++) {
			Token token = this.tokens.get(k);
			if (!token.getScope().equals(parentOfCurrentScope) && !this.seenIds.contains(k)) {
				this.operate(k);
			}
		}

		this.intermediateCode.append("GOTO " + exitLabel.getName());
		this.intermediateCode.append(BREAK_LINE);

		this.intermediateCode.append(exitLabel.getNameWithColon());
		this.intermediateCode.append(BREAK_LINE);

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
		int insideForIndex = index;
		for (int i = index; i < this.tokens.size(); i++) {
			this.seenIds.add(i);
			Token token = this.tokens.get(i);
			if (TokenType.BLOCK_OPEN.equals(token.getType()) && "{".equals(token.getName())) {
				insideForIndex = i + 1;
				break;
			}
		}

		StringBuilder initialization = new StringBuilder();
		StringBuilder condition = new StringBuilder();
		StringBuilder inOrDecrement = new StringBuilder();
		int semiColonNumber = 0;
		List<Token> firstLine = this.tokens.subList(index + 2, insideForIndex - 2);
		for (Token token : firstLine) {
			if (token.getName().equals(";")) {
				semiColonNumber++;
				continue;
			}

			String toAppend = token.getName() + " ";
			switch (semiColonNumber) {
			case 0:
				initialization.append(toAppend);
				break;
			case 1:
				condition.append(toAppend);
				break;
			case 2:
				inOrDecrement.append(toAppend);
				break;
			default:
				break;
			}
		}

		Label ifLabel = new Label(this.labelCounter++);
		Label entryLabel = new Label(this.labelCounter++);
		Label exitLabel = new Label(this.labelCounter++);
		this.currentExitOfFor = exitLabel.getName();

		this.intermediateCode.append(initialization.toString());
		this.intermediateCode.append(BREAK_LINE);
		this.intermediateCode.append(ifLabel.getNameWithColon());
		this.intermediateCode.append(BREAK_LINE);
		this.intermediateCode.append("if ( " + condition.toString() + ") == 0 GOTO " + exitLabel.getName());
		this.intermediateCode.append(BREAK_LINE);
		this.intermediateCode.append(entryLabel.getNameWithColon());
		this.intermediateCode.append(BREAK_LINE);

		Scope currentScope = this.tokens.get(insideForIndex).getScope();
		for (int j = insideForIndex; j < this.tokens.size(); j++) {
			Token token = this.tokens.get(j);
			if (token.getScope().equals(currentScope) && !this.seenIds.contains(j)) {
				this.operate(j);
			}
		}

		this.intermediateCode.append(inOrDecrement.toString());
		this.intermediateCode.append(BREAK_LINE);
		this.intermediateCode.append("GOTO " + ifLabel.getName());
		this.intermediateCode.append(BREAK_LINE);
		this.intermediateCode.append(exitLabel.getNameWithColon());
		this.intermediateCode.append(BREAK_LINE);
	}

	private void createToBreak() {
		this.intermediateCode.append("GOTO " + this.currentExitOfFor);
		this.intermediateCode.append(BREAK_LINE);
	}

	private boolean isSemiColon(Token token) {
		return TokenType.DELIMITER.equals(token.getType()) && ";".equals(token.getName());
	}

	public void print() {
		System.out.println(this.intermediateCode.toString());
	}

}
