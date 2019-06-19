// Generated from ./expr/expr.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link exprParser}.
 */
public interface exprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link exprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(exprParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(exprParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#numexpression}.
	 * @param ctx the parse tree
	 */
	void enterNumexpression(exprParser.NumexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#numexpression}.
	 * @param ctx the parse tree
	 */
	void exitNumexpression(exprParser.NumexpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(exprParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(exprParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#terms}.
	 * @param ctx the parse tree
	 */
	void enterTerms(exprParser.TermsContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#terms}.
	 * @param ctx the parse tree
	 */
	void exitTerms(exprParser.TermsContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#unaryexpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryexpr(exprParser.UnaryexprContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#unaryexpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryexpr(exprParser.UnaryexprContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#unaryexprs}.
	 * @param ctx the parse tree
	 */
	void enterUnaryexprs(exprParser.UnaryexprsContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#unaryexprs}.
	 * @param ctx the parse tree
	 */
	void exitUnaryexprs(exprParser.UnaryexprsContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(exprParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(exprParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link exprParser#col}.
	 * @param ctx the parse tree
	 */
	void enterCol(exprParser.ColContext ctx);
	/**
	 * Exit a parse tree produced by {@link exprParser#col}.
	 * @param ctx the parse tree
	 */
	void exitCol(exprParser.ColContext ctx);
}