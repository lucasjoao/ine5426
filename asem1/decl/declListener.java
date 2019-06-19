// Generated from ./decl/decl.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link declParser}.
 */
public interface declListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link declParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDecl(declParser.DeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link declParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDecl(declParser.DeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link declParser#vardecl}.
	 * @param ctx the parse tree
	 */
	void enterVardecl(declParser.VardeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link declParser#vardecl}.
	 * @param ctx the parse tree
	 */
	void exitVardecl(declParser.VardeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link declParser#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(declParser.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link declParser#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(declParser.TipoContext ctx);
	/**
	 * Enter a parse tree produced by {@link declParser#col}.
	 * @param ctx the parse tree
	 */
	void enterCol(declParser.ColContext ctx);
	/**
	 * Exit a parse tree produced by {@link declParser#col}.
	 * @param ctx the parse tree
	 */
	void exitCol(declParser.ColContext ctx);
	/**
	 * Enter a parse tree produced by {@link declParser#mvardecl}.
	 * @param ctx the parse tree
	 */
	void enterMvardecl(declParser.MvardeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link declParser#mvardecl}.
	 * @param ctx the parse tree
	 */
	void exitMvardecl(declParser.MvardeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link declParser#virgula}.
	 * @param ctx the parse tree
	 */
	void enterVirgula(declParser.VirgulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link declParser#virgula}.
	 * @param ctx the parse tree
	 */
	void exitVirgula(declParser.VirgulaContext ctx);
}