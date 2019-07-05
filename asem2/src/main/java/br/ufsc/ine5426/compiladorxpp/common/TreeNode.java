package br.ufsc.ine5426.compiladorxpp.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreeNode<T> {

	private T data;
	private TreeNode<T> parent;
	private List<TreeNode<T>> children;

	public TreeNode(T data) {
		this.data = data;
		this.children = new ArrayList<>();
	}

	public TreeNode<T> addChild(TreeNode<T> child) {
		child.setParent(this);
		this.children.add(child);
		return child;
	}

}
