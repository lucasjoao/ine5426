/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Jacyara Bosse, Lucas João Martins e Nathália Liz de Brito.
 *
 */

package br.ufsc.ine5426.compiladorxpp;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import br.ufsc.ine5426.compiladorxpp.automata.FiniteAutomata;
import br.ufsc.ine5426.compiladorxpp.lexicalanalyzer.LexicalAnalyser;
import br.ufsc.ine5426.compiladorxpp.semanticanalyzer.SemanticAnalyzer;
import br.ufsc.ine5426.compiladorxpp.syntacticanalyzer.LL1;

/**
 * Classe que possui o método principal que da o start no compilador.
 */
public class Main {

	/**
	 * Pega o caminho para o arquivo que é o programa fonte através de uma opção -i
	 * ao executar o .jar. Com isso, cria um AF a partir de um .txt criado pela
	 * equipe e feito com base na gramática fornecida pelo professor. Além disso,
	 * cria um AL que irá compilar o programa fonte.
	 */
	public static void main(String[] args) {
		Options options = new Options();
		options.addOption("i", "input", true, "caminho do arquivo de entrada");
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			if (cmd.hasOption("input")) {
				var input = cmd.getOptionValue("i");
				var lexicalAnalyser = new LexicalAnalyser(FiniteAutomata.Load("./baseAutomata.txt"));
				var ll1 = new LL1("./baseGrammar.txt", lexicalAnalyser);
				var semanticAnalyser = new SemanticAnalyzer(ll1);

				// TODO: pensar melhor na questão das mensagens
				if (semanticAnalyser.compile(input)) {
					System.out.println("Compilação bem-sucedida!");
					lexicalAnalyser.printSymbolTable();
					lexicalAnalyser.printTokens();
				} else {
					System.out.println("Compilação mal-sucedida.");
				}
			} else {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("java -jar {PATH_TO_JAR}compiladorxpp-1.0.jar", options);
			}
		} catch (ParseException e) {
			System.out.println("Erro ao ler os argumentos passados através da linha de comando");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Erro ao ler o arquivo do autômato finito");
			e.printStackTrace();
		}
	}

}