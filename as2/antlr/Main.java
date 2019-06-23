/*
 *  Trabalho realizado para a disciplina INE5426 (Construção de Compiladores) do
 *  curso de Ciência da Computação da Universidade Federal de Santa Catarina.
 *  Disciplina ministrada pelo prof. Alvaro Franco.
 *
 *  Desenvolvido por:
 *  Christian de Pieri, Jacyara Bosse, Lucas João Martins e Nathália Liz de Brito.
 *
 */

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Main {

	public static void main(String[] args) {
		xppll1Lexer lexer;
		try {
			lexer = new xppll1Lexer(CharStreams.fromFileName(args[0]));
		} catch (Exception e) {
			System.out.println("Erro ao ler o arquivo do programa xpp.");
			return;
		}

		xppll1Parser parser = new xppll1Parser(new CommonTokenStream(lexer));
		xppll1Visitor visitor = new xppll1BaseVisitor<Void>();
		visitor.visitProgram(parser.program());

		String resultMsg = parser.getNumberOfSyntaxErrors() > 0 ?
			"Compilação mal-sucedida! Erros acima." :
			"Compilação bem-sucedida!";

		System.out.println(resultMsg);
	}

}
