/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Jacyara Bosse, Lucas João Martins e Nathália Liz de Brito.
 *
 */

package br.ufsc.ine5426.compiladorxpp.regex;
/*
 * Árvore binária
 */
public class Node {
	private int id;
	private Node parent;
	private Node left;
	private Node right;
	private Character value;
	public Node(int id, Character value){
		this.setId(id);
		this.setValue(value);
	}

	public boolean isLeaf(){
		return this.left == null && this.right == null;
	}

	public Node getParent() {
		return this.parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getLeft() {
		return this.left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return this.right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Character getValue() {
		return this.value;
	}

	public void setValue(Character value) {
		this.value = value;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Node) {
			Node node = (Node) obj;
			return this.getId() == node.getId();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getId() ^ (this.getId() >>> 32);
	}
}
