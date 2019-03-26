/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Lucas João Martins e Nathália Liz de Brito.
 *
 */

package br.ufsc.compiladores;

import br.ufsc.compiladores.automata.FiniteAutomata;
import br.ufsc.compiladores.lexicalanalyser.LexicalAnalyser;
import br.ufsc.compiladores.syntaxanalyser.LL1;
import org.apache.commons.cli.*;

import java.io.IOException;

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
                formatter.printHelp("java -jar trabalho3.jar", options);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static boolean doCompile(CommandLine cmd) {

            var input = cmd.getOptionValue("i");
            try {
                var la = new LexicalAnalyser(FiniteAutomata.Load("./baseAutomata.txt"));
                var ll1 = new LL1("./baseGrammar.txt", la);
                if (ll1.compile(input)){
                    System.out.println("Compilação bem-sucedida!");
                }else{
                    System.out.println("Compilação mal-sucedida. O(s) seguinte(s) problema(s) aconteceram:");
                    ll1.getErrors().forEach(msg->System.out.println(msg));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        return false;
    }
}
