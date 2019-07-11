package br.ufsc.ine5426.compiladorxpp.intermediatecodegenerator;

import lombok.Getter;

@Getter
public class Label {
	
	private String name;
	
	public Label(int id) {
		this.name = "LABEL" + id + ":";
	}

}
