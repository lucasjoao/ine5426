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
	 * Visit a parse tree produced by {@link xppll1Parser#classlistepsilon}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClasslistepsilon(xppll1Parser.ClasslistepsilonContext ctx);
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
	 * Visit a parse tree produced by {@link xppll1Parser#classbodydentro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassbodydentro(xppll1Parser.ClassbodydentroContext ctx);
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
	 * Visit a parse tree produced by {@link xppll1Parser#col}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCol(xppll1Parser.ColContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#vardeclm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardeclm(xppll1Parser.VardeclmContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#virgula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVirgula(xppll1Parser.VirgulaContext ctx);
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
	 * Visit a parse tree produced by {@link xppll1Parser#parameteres}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameteres(xppll1Parser.ParameteresContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#statment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatment(xppll1Parser.StatmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#varoratribut}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVaroratribut(xppll1Parser.VaroratributContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#atribstat}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtribstat(xppll1Parser.AtribstatContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#atributstatss}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtributstatss(xppll1Parser.AtributstatssContext ctx);
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
	 * Visit a parse tree produced by {@link xppll1Parser#returnstatexpress}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnstatexpress(xppll1Parser.ReturnstatexpressContext ctx);
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
	 * Visit a parse tree produced by {@link xppll1Parser#dentrofor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDentrofor(xppll1Parser.DentroforContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#dentroforexpress}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDentroforexpress(xppll1Parser.DentroforexpressContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#dentroforatribs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDentroforatribs(xppll1Parser.DentroforatribsContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#statlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatlist(xppll1Parser.StatlistContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#statlistas}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatlistas(xppll1Parser.StatlistasContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#lvalue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLvalue(xppll1Parser.LvalueContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#lvaluex}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLvaluex(xppll1Parser.LvaluexContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#alocexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlocexpression(xppll1Parser.AlocexpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#alocexpressionx}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlocexpressionx(xppll1Parser.AlocexpressionxContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#alocexpressionz}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlocexpressionz(xppll1Parser.AlocexpressionzContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#expressions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressions(xppll1Parser.ExpressionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#expressionsx}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionsx(xppll1Parser.ExpressionsxContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(xppll1Parser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#expressionz}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionz(xppll1Parser.ExpressionzContext ctx);
	/**
	 * Visit a parse tree produced by {@link xppll1Parser#expressiony}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressiony(xppll1Parser.ExpressionyContext ctx);
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