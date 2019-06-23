// Generated from xppll1.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link xppll1Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface xppll1Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#xppll1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXppll1(xppll1Parser.Xppll1Context ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(xppll1Parser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#classlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClasslist(xppll1Parser.ClasslistContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#classlistb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClasslistb(xppll1Parser.ClasslistbContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#classdecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassdecl(xppll1Parser.ClassdeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#classbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassbody(xppll1Parser.ClassbodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#classbodyb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassbodyb(xppll1Parser.ClassbodybContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#vardecls}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardecls(xppll1Parser.VardeclsContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#constructdecls}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructdecls(xppll1Parser.ConstructdeclsContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#methoddecls}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethoddecls(xppll1Parser.MethoddeclsContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(xppll1Parser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#vardecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardecl(xppll1Parser.VardeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#brackets}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBrackets(xppll1Parser.BracketsContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#multivardecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultivardecl(xppll1Parser.MultivardeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#vardeclcomma}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardeclcomma(xppll1Parser.VardeclcommaContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#constructdecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructdecl(xppll1Parser.ConstructdeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#methoddecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethoddecl(xppll1Parser.MethoddeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#methodbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodbody(xppll1Parser.MethodbodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#paramlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamlist(xppll1Parser.ParamlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#multiparam}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiparam(xppll1Parser.MultiparamContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#statment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatment(xppll1Parser.StatmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#varoratrib}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVaroratrib(xppll1Parser.VaroratribContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#atribstat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtribstat(xppll1Parser.AtribstatContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#atribstatb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtribstatb(xppll1Parser.AtribstatbContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#printstat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintstat(xppll1Parser.PrintstatContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#readstat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadstat(xppll1Parser.ReadstatContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#returnstat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnstat(xppll1Parser.ReturnstatContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#returnstatb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnstatb(xppll1Parser.ReturnstatbContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#superstat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuperstat(xppll1Parser.SuperstatContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#ifstat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfstat(xppll1Parser.IfstatContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#forstat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForstat(xppll1Parser.ForstatContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#insidefor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsidefor(xppll1Parser.InsideforContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#insideforb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsideforb(xppll1Parser.InsideforbContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#insideforc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsideforc(xppll1Parser.InsideforcContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#statlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatlist(xppll1Parser.StatlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#statlistb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatlistb(xppll1Parser.StatlistbContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#lvalue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLvalue(xppll1Parser.LvalueContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#lvalueb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLvalueb(xppll1Parser.LvaluebContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#alocexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlocexpression(xppll1Parser.AlocexpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#alocexpressionb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlocexpressionb(xppll1Parser.AlocexpressionbContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#alocexpressionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlocexpressionc(xppll1Parser.AlocexpressioncContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#expressions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressions(xppll1Parser.ExpressionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#expressionsb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionsb(xppll1Parser.ExpressionsbContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(xppll1Parser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#expressionb}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionb(xppll1Parser.ExpressionbContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#expressionc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionc(xppll1Parser.ExpressioncContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#numexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumexpression(xppll1Parser.NumexpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#terms}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerms(xppll1Parser.TermsContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(xppll1Parser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#unaryexprs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryexprs(xppll1Parser.UnaryexprsContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#unaryexpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryexpr(xppll1Parser.UnaryexprContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(xppll1Parser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#arglist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArglist(xppll1Parser.ArglistContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#arglists}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArglists(xppll1Parser.ArglistsContext ctx);
}