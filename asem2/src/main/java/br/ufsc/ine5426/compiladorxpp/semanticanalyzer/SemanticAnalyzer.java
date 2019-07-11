/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Jacyara Bosse, Lucas João Martins e Nathália Liz de Brito.
 *
 */

package br.ufsc.ine5426.compiladorxpp.semanticanalyzer;

import static br.ufsc.ine5426.compiladorxpp.common.Constants.BREAK_LINE;

import java.util.List;

import br.ufsc.ine5426.compiladorxpp.common.Constants;
import br.ufsc.ine5426.compiladorxpp.common.IdentType;
import br.ufsc.ine5426.compiladorxpp.common.Scope;
import br.ufsc.ine5426.compiladorxpp.common.ScopeType;
import br.ufsc.ine5426.compiladorxpp.common.Token;
import br.ufsc.ine5426.compiladorxpp.common.TokenType;
import br.ufsc.ine5426.compiladorxpp.common.TreeNode;
import br.ufsc.ine5426.compiladorxpp.syntacticanalyzer.LL1;
import lombok.Getter;

/**
 * Classe que representa o analisador semântico do projeto.
 *
 */
@Getter
public class SemanticAnalyzer {

	private LL1 ll1;
	private StringBuilder result;
	private List<Token> tokens;
	private TreeNode treeScope;

	public SemanticAnalyzer(LL1 ll1) {
		this.ll1 = ll1;
		this.result = new StringBuilder();
	}

	public boolean compile(String path) {
		if (this.ll1.compile(path)) {
			this.tokens = this.ll1.getLexicalAnalyser().getTokens();
			this.treeScope = this.ll1.getLexicalAnalyser().getTreeScope();
			return this.checkType() && this.checkVariableScope() && this.checkBreak();
		} else {
			return false;
		}
	}

	private boolean checkType() {
		boolean alright = true;
		for (int i = 0; i < this.tokens.size(); i++) {
			if (this.tokens.get(i).getType().equals(TokenType.AROP)) {
				alright = alright && this.isGoodTokenType(i - 1, false) && this.isGoodTokenType(i + 1, true);
			}

			if (!alright) {
				break;
			}
		}

		this.result.append(BREAK_LINE);
		this.result.append("Há alguma violação na verificação de tipos em expressões aritméticas? ");
		this.result.append(alright ? "Não!" : "Sim, ver erros nas linhas anteriores!");
		this.result.append(BREAK_LINE);
		return alright;
	}

	private boolean isGoodTokenType(int index, boolean moveForward) {
		if (index < 0 || this.tokens.size() <= index) {
			this.result.append(BREAK_LINE);
			this.result.append("Erro: acessar um token com um 'index' incorreto!");
			return false;
		}

		Token token = this.tokens.get(index);
		if (token.getType().equals(TokenType.INT_CONSTANT)
				|| token.getType().equals(TokenType.IDENT) && token.getIdentType().equals(IdentType.INT)) {
			return true;
		}

		if ((token.getType().equals(TokenType.BLOCK_OPEN) && token.getName().equals("("))
				|| (token.getType().equals(TokenType.BLOCK_CLOSE) && token.getName().equals(")"))) {
			int newIndex = moveForward ? index + 1 : index - 1;
			return this.isGoodTokenType(newIndex, moveForward);
		}

		this.result.append(BREAK_LINE);
		this.result.append("O token com o seguinte lexema viola a verificação de tipos: " + token.getName());
		return false;
	}

	private boolean checkVariableScope() {
		boolean alright = true;
		for (int i = 0; i < this.tokens.size(); i++) {
			if (this.tokens.get(i).getType().equals(TokenType.IDENT)) {
				alright = alright && this.isGoodVariableScope(this.tokens.get(i));
			}

			if (!alright) {
				break;
			}
		}

		this.result.append(BREAK_LINE);
		this.result.append("Existem duas ou mais declarações de um identificador em um mesmo escopo? ");
		this.result.append(alright ? "Não!" : "Sim, ver erros nas linhas anteriores!");
		this.result.append(BREAK_LINE);
		return alright;
	}

	private boolean isGoodVariableScope(Token search) {
		Token lastSeen = search;
		// posso pular porque sei que vou receber o primeiro ident não visto aqui
		boolean skipToken = true;
		for (Token token : this.tokens) {
			if (skipToken) {
				skipToken = !token.equals(search);
				continue;
			}

			if (token.isDuplicate(search) && this.isLastSeenThatWillGenerateBadResult(lastSeen)) {
				this.result.append(BREAK_LINE);
				this.result.append("O identificador " + search.getName() + " está repetido em um mesmo escopo!");
				return false;
			}
			lastSeen = token;
		}
		return true;
	}

	private boolean isLastSeenThatWillGenerateBadResult(Token lastSeen) {
		return lastSeen.getType().equals(TokenType.PR)
				&& (lastSeen.getName().equals(Constants.INT) || lastSeen.getName().equals(Constants.STRING));
	}

	private boolean checkBreak() {
		boolean alright = true;
		for (int i = 0; i < this.tokens.size(); i++) {
			Token token = this.tokens.get(i);
			if (token.getName().equals(Constants.BREAK)) {
				Scope breakScope = this.tokens.get(i).getScope();
				TreeNode tree = this.treeScope.findByScope(breakScope);
				alright = alright && this.isGoodTreeNodeForBreak(tree);
			}

			if (!alright) {
				this.result.append(BREAK_LINE);
				this.result.append("break da linha " + token.getLine() + " fora de um escopo adequado!");
				break;
			}
		}

		this.result.append(BREAK_LINE);
		this.result.append("Existe um comando break fora do escopo de um comando de repetição? ");
		this.result.append(alright ? "Não!" : "Sim, ver erros nas linhas anteriores!");
		this.result.append(BREAK_LINE);
		return alright;
	}

	private boolean isGoodTreeNodeForBreak(TreeNode tree) {
		if (tree == null) {
			return false;
		}
		return tree.getScope().getType().equals(ScopeType.FOR) ? true : this.isGoodTreeNodeForBreak(tree.getParent());
	}

	public void printResult() {
		System.out.println(this.result.toString());
	}

}
