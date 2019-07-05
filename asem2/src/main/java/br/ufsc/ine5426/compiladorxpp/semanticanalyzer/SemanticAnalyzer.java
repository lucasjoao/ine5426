package br.ufsc.ine5426.compiladorxpp.semanticanalyzer;

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
import lombok.Setter;

// XXX: necessario?
@Setter
@Getter
public class SemanticAnalyzer {

	private LL1 ll1;
	private String result;
	private List<Token> tokens;
	private TreeNode treeScope;

	public SemanticAnalyzer(LL1 ll1) {
		this.ll1 = ll1;
	}

	public boolean compile(String path) {
		if (this.ll1.compile(path)) {
			// TODO: add mensagens
			this.tokens = this.ll1.getLexicalAnalyser().getTokens();
			this.treeScope = this.ll1.getLexicalAnalyser().getTreeScope();
			return this.checkType() && this.checkVariableScope() && this.checkBreak();
		} else {
			// TODO: add mensagens
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
		return alright;
	}

	private boolean isGoodTokenType(int index, boolean moveForward) {
		if (index < 0 || this.tokens.size() <= index) {
			System.out.println("Problema com index"); // XXX: add result
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

		System.out.println("Erro semântico com o token de nome: " + token.getName()); // XXX: add result
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
		return alright;
	}

	private boolean isGoodVariableScope(Token search) {
		for (Token token : this.tokens) {
			if (token.equals(search)) {
				continue;
			}
			if (token.isDuplicate(search)) {
				System.out.println("Erro no isGoodVariableScope"); // XXX: add result
				return false;
			}
		}
		return true;
	}

	private boolean checkBreak() {
		boolean alright = true;
		for (int i = 0; i < this.tokens.size(); i++) {
			if (this.tokens.get(i).getName().equals(Constants.BREAK)) {
				Scope breakScope = this.tokens.get(i).getScope();
				TreeNode tree = this.treeScope.findByScope(breakScope);
				alright = alright && this.isGoodTreeNodeForBreak(tree);
			}

			if (!alright) {
				System.out.println("Erro no checkBreak"); // XXX: add result
				break;
			}
		}
		return alright;
	}

	private boolean isGoodTreeNodeForBreak(TreeNode tree) {
		if (tree == null) {
			return false;
		}
		return tree.getScope().getType().equals(ScopeType.FOR) ? true : this.isGoodTreeNodeForBreak(tree.getParent());
	}

}
