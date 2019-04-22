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
import br.ufsc.ine5426.compiladorxpp.lexicalanalyser.LexicalAnalyser;
import br.ufsc.ine5426.compiladorxpp.lexicalanalyser.Token;

public class Main {

	public static void main(String[] args) throws IOException {
		Options options = new Options();
		options.addOption("i", "input", true, "caminho do arquivo de entrada");
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			if (cmd.hasOption("input")) {
				doCompile(cmd);
			} else {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("java -jar trabalho1.jar", options);
			}
		} catch (ParseException e) {
			// no time bro
			e.printStackTrace();
		}
	}

	// TODO: fix it
	private static boolean doCompile(CommandLine cmd) {

		var input = cmd.getOptionValue("i");
		try {
			var la = new LexicalAnalyser(FiniteAutomata.Load("./baseAutomata.txt"));
			la.compile(input);
			for (Token token : la.getTokens()) {
				System.out.println(token.getType() + " " + token.getName());
			}


			//			if (ll1.compile(input)){
			//				System.out.println("Compilação bem-sucedida!");
			//			}else{
			//				System.out.println("Compilação mal-sucedida. O(s) seguinte(s) problema(s) aconteceram:");
			//				ll1.getErrors().forEach(msg->System.out.println(msg));
			//			}
		} catch (IOException e) {
			// no time bro
			e.printStackTrace();
		}

		return false;
	}
}