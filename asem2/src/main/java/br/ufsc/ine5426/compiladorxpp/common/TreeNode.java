/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Jacyara Bosse, Lucas João Martins e Nathália Liz de Brito.
 *
 */

package br.ufsc.ine5426.compiladorxpp.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Nodo de uma árvore (de nenhum tipo específico) que irá representar a árvore de escopo do código fonte.
 *
 */
@Getter
@Setter
public class TreeNode {

	private Scope scope;
	private TreeNode parent;
	private List<TreeNode> children;

	public TreeNode(Scope scope) {
		this.scope = scope;
		this.children = new ArrayList<>();
	}

	public TreeNode addChild(TreeNode child) {
		child.setParent(this);
		this.children.add(child);
		return child;
	}

	public TreeNode getRoot() {
		return this.parent == null ? this : this.parent.getRoot();
	}

	public void printFromRoot() {
		this.printTree(this.getRoot(), " ");
	}

	private void printTree(TreeNode node, String appender) {
		System.out.println(appender + node.getScope());
		node.getChildren().forEach(each -> this.printTree(each, appender + appender));
	}

	public TreeNode findByScope(Scope scope) {
		if (this.scope.equals(scope)) {
			return this;
		}

		for (TreeNode treeNode : this.children) {
			return treeNode.findByScope(scope);
		}

		return null;
	}

}
