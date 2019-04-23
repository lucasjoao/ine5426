package br.ufsc.ine5426.compiladorxpp.lexicalanalyser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Token {

	@ToString.Include
	private TokenType type;
	private String name;
	private int rowPos; //linha
	private int wordPos; //em relação ao número de palavras
}
