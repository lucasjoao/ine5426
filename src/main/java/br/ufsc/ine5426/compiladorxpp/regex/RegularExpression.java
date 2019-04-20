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


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import br.ufsc.ine5426.compiladorxpp.automata.FiniteAutomata;
import br.ufsc.ine5426.compiladorxpp.automata.State;
import br.ufsc.ine5426.compiladorxpp.common.Symbol;


public class RegularExpression {
	private final Map<Node, Boolean> nullable = new HashMap<>();
	private final Map<Node, Set<Node>> firstPos = new HashMap<>();
	private final Map<Node, Set<Node>> lastPos = new HashMap<>();
	private final Map<Integer, Set<Node>> followPos = new HashMap<>();
	private final Set<Character> operators = new HashSet<Character>(Arrays.asList('.','+', '*', '(', ')'));
	private String regex;
	private int nodeId = -1;

	public static RegularExpression Load(String path) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(path),
				Charset.defaultCharset());
		return new RegularExpression(lines.get(0).trim());
	}

	private boolean isOperator(char ch) {
		return this.operators.contains(ch);
	}

	public RegularExpression(String regex){
		this.regex = regex;
	}

	private void doStar(Stack<Node> stack){
		Node node1 = stack.pop();
		Node root = new Node(++this.nodeId,'*');
		node1.setParent(root);
		root.setLeft(node1);
		root.setRight(null);

		stack.push(root);
	}

	private void doUnion(Stack<Node> stack){
		Node node2 = stack.pop();
		Node node1 = stack.pop();
		Node root = new Node(++this.nodeId,'+');
		node1.setParent(root);
		node2.setParent(root);
		root.setLeft(node1);
		root.setRight(node2);
		stack.push(root);
	}

	private void doConcat(Stack<Node> stack){
		Node node2 = stack.pop();
		Node node1 = stack.pop();
		Node root = new Node(++this.nodeId,'.');
		node1.setParent(root);
		node2.setParent(root);
		root.setLeft(node1);
		root.setRight(node2);
		stack.push(root);
	}

	private FiniteAutomata buildFiniteAutomata(Node root){
		State initialState = null;
		var finalStates = new HashSet<State>();
		Map<State, Map<Symbol, Set<State>>> transitionTable = new LinkedHashMap<>();
		Set<Set<Node>> dStates = new HashSet<>();
		int stateId = 0;
		var states = new HashMap<Set<Node>, State>();
		var composedState = this.computeFirstPos(root);
		dStates.add(composedState);
		while (!dStates.isEmpty()) {
			State currentState = null;
			composedState = dStates.iterator().next();
			if (!states.containsKey(composedState)) {
				var newState = new State(stateId, "q" + stateId++);
				states.put(composedState, newState);
				if (initialState == null) {
					initialState = newState;
				}
			}
			currentState = states.get(composedState);
			transitionTable.put(currentState, new HashMap<>());
			if (composedState.stream().anyMatch(node->node.getValue() == '#')){
				finalStates.add(currentState);
			}
			for (var node : composedState) {
				if (node.getValue() == '#'){
					continue;
				}
				var similarNodes = composedState.stream().filter(similarNode -> similarNode.getValue() != '#' && similarNode.getValue().equals(node.getValue())).collect(Collectors.toList());
				var composedNextState = new HashSet<Node>();
				similarNodes.forEach(similarNode -> composedNextState.addAll(new HashSet<>(this.followPos.get(similarNode.getId()))));
				if (!states.containsKey(composedNextState)) {
					var newState = new State(stateId, "q" + stateId++);
					states.put(composedNextState, newState);
				}
				if (!transitionTable.containsKey(states.get(composedNextState))){
					dStates.add(composedNextState);
				}
				transitionTable.get(currentState).put(new Symbol(node.getValue()), new HashSet<>(Arrays.asList(states.get(composedNextState))));
			}
			dStates.remove(composedState);
		}
		return new FiniteAutomata(initialState, finalStates, transitionTable);
	}

	/*
	 * Algoritmo do livro do Aho com árvore sintática pra conversão de expressões regulares para autômato finito.
	 */
	public FiniteAutomata toFiniteAutomata(){
		var root = this.generateTree();
		this.computeNullable(root);
		this.computeFirstPos(root);
		this.computeLastPos(root);
		this.computeFollowPos(root);
		return this.buildFiniteAutomata(root);
	}
	private void computeFollowPos(Node node){
		if (node == null){
			return;
		}
		this.computeFollowPos(node.getLeft());
		this.computeFollowPos(node.getRight());
		var ch = node.getValue();
		if (ch == '#'){
			this.followPos.put(node.getId(), new HashSet<>());
		}
		else if (ch == '.') {
			for (var nodeLp : this.computeLastPos(node.getLeft())) { //c1
				if (!this.followPos.containsKey(nodeLp.getId())) {
					this.followPos.put(nodeLp.getId(), new HashSet<>());
				}
				this.followPos.get(nodeLp.getId()).addAll(this.computeFirstPos(node.getRight()));
			}
		}else if (ch == '*'){
			for (var nodeLp : this.computeLastPos(node)) {
				if (!this.followPos.containsKey(nodeLp.getId())) {
					this.followPos.put(nodeLp.getId(), new HashSet<>());
				}
				this.followPos.get(nodeLp.getId()).addAll(this.computeFirstPos(node));
			}
		}
	}
	private Set<Node> computeLastPos(Node node){
		if (this.lastPos.containsKey(node)){
			return this.lastPos.get(node);
		}
		var ret = new HashSet<Node>();
		if (node == null){
			return ret;
		}
		Character ch = node.getValue();
		if (node.isLeaf()){
			if (ch != '&'){
				ret.add(node);
			}
		}else{
			var lastPosLeft = this.computeLastPos(node.getLeft());
			var lastPosRight = this.computeLastPos(node.getRight());
			switch (ch) {
			case '+':
				ret.addAll(lastPosLeft); //c1
				ret.addAll(lastPosRight); //c2
				break;
			case '.':
				ret.addAll(lastPosRight); //c2
				if (this.computeNullable(node.getRight())) {
					ret.addAll(lastPosLeft); //c1
				}
				break;
			case '*':
				ret.addAll(lastPosLeft); //c1
				break;
			}
		}
		this.lastPos.put(node, ret);
		return new HashSet<>(this.lastPos.get(node));
	}

	private Set<Node> computeFirstPos(Node node){
		if (this.firstPos.containsKey(node)){
			return new HashSet<>(this.firstPos.get(node));
		}
		var ret = new HashSet<Node>();
		if (node == null){
			return ret;
		}
		Character ch = node.getValue();
		if (node.isLeaf()){
			if (ch != '&'){
				ret.add(node);
			}
		}else{
			var firstPosLeft = this.computeFirstPos(node.getLeft());
			var firstPosRight= this.computeFirstPos(node.getRight());
			switch (ch) {
			case '+':
				ret.addAll(firstPosLeft); //c1
				ret.addAll(firstPosRight); //c2
				break;
			case '.':
				ret.addAll(firstPosLeft); //c1
				if (this.computeNullable(node.getLeft())) {
					ret.addAll(firstPosRight); //c2
				}
				break;
			case '*':
				ret.addAll(firstPosLeft); //c1
				break;
			}
		}
		this.firstPos.put(node, ret);
		return new HashSet<>(this.firstPos.get(node));
	}

	private boolean computeNullable(Node node){

		if (node == null){
			return false;
		}
		if (this.nullable.containsKey(node)){
			return this.nullable.get(node);
		}
		Character ch = node.getValue();
		boolean ret = false;
		if (node.isLeaf()){
			ret = ch == '&';
		}else{
			var nullableLeft = this.computeNullable(node.getLeft());
			var nullableRight = this.computeNullable(node.getRight());
			switch (ch) {
			case '+':
				ret= nullableLeft || nullableRight;
				break;
			case '.':
				ret= nullableLeft && nullableRight;
				break;
			case '*':
				ret = true;
				break;
			}
		}
		this.nullable.put(node, ret);
		return this.nullable.get(node);
	}
	/*
	 * Método cria uma árvore sintática para a expressão regular
	 */
	private Node generateTree(){
		var extended = this.extendRegexString(this.regex);
		var postfixed = this.convertToPostFix(extended);
		Stack<Node> stack = new Stack<>();
		var oldCh = '#';
		for (int i = 0; i < postfixed.length();i++){
			var ch = postfixed.charAt(i);
			if (this.isOperator(ch)){
				switch (ch){
				case '*':
					this.doStar(stack);
					break;
				case '.':
					this.doConcat(stack);
					break;
				case '+':
					this.doUnion(stack);
					break;
				}
			}else if (oldCh!='\\'){
				stack.push(new Node(++this.nodeId,ch));
			}
			oldCh = ch;
		}
		return stack.pop();
	}

	/*
     retorna verdeiro para os sequintes casos:
     ab
     a(
     )a
	 *a
	 *(
     )(
	 */
	private boolean needConcatenationSymbol(char a, char b) {
		if (!this.isOperator(b) && (!this.isOperator(a) || a == ')' || a == '*')) {
			return true;
		} else if (b == '(' && (!this.isOperator(a) || a == '*' || a == ')')) {
			return true;
		}
		return false;
	}
	/*
	 * Método adiciona . (concatenação) em posições de acordo com o needConcatenationSymbol() e um # no final da expressão regular.
	 */

	public String extendRegexString(String regex){
		String ret="";
		for(int z =0; z<regex.length()-1;z++){
			ret += regex.charAt(z);
			if (this.needConcatenationSymbol(regex.charAt(z),regex.charAt(z+1))){
				ret+= '.';
			}
		}
		ret += regex.substring(regex.length()-1)+".#";
		return ret;
	}

	/*
	 * Transforma a string da expressão regular para a notação posfixa.
	 */
	public String convertToPostFix(String exp)
	{
		String result = "";
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i<exp.length(); ++i)
		{
			char c = exp.charAt(i);

			// If the scanned character is an operand, add it to output.
			if (!this.isOperator(c))
				result += c;

			// If the scanned character is an '(', push it to the stack.
			else if (c == '(')
				stack.push(c);

			//  If the scanned character is an ')', pop and output from the stack
			// until an '(' is encountered.
			else if (c == ')')
			{
				while (!stack.isEmpty() && stack.peek() != '(')
					result += stack.pop();

				if (!stack.isEmpty() && stack.peek() != '(')
					return "Invalid Expression"; // invalid expression
				else
					stack.pop();
			}
			else // an operator is encountered
			{
				while (!stack.isEmpty() && this.getPrecedence(c) <= this.getPrecedence(stack.peek()))
					result += stack.pop();
				stack.push(c);
			}

		}
		// pop all the operators from the stack
		while (!stack.isEmpty())
			result += stack.pop();
		return result;
	}

	private int getPrecedence(Character operator){
		switch (operator)
		{
		case '.':
			return 1;
		case '+':
			return 2;
		case '*':
			return 3;
		}
		return -1;
	}
}
