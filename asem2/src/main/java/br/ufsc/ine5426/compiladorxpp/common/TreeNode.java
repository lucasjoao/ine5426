package br.ufsc.ine5426.compiladorxpp.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

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

}
