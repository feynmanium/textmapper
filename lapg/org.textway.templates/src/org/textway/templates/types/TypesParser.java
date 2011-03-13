package org.textway.templates.types;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.textway.templates.types.TypesLexer.ErrorReporter;
import org.textway.templates.types.TypesLexer.LapgSymbol;
import org.textway.templates.types.TypesLexer.Lexems;
import org.textway.templates.types.ast.AstConstraint;
import org.textway.templates.types.ast.AstFeatureDeclaration;
import org.textway.templates.types.ast.AstInput;
import org.textway.templates.types.ast.AstLiteralExpression;
import org.textway.templates.types.ast.AstMapEntriesItem;
import org.textway.templates.types.ast.AstMapSeparator;
import org.textway.templates.types.ast.AstMethodDeclaration;
import org.textway.templates.types.ast.AstMultiplicity;
import org.textway.templates.types.ast.AstStringConstraint;
import org.textway.templates.types.ast.AstStructuralExpression;
import org.textway.templates.types.ast.AstType;
import org.textway.templates.types.ast.AstTypeDeclaration;
import org.textway.templates.types.ast.AstTypeEx;
import org.textway.templates.types.ast.Ast_String;
import org.textway.templates.types.ast.IAstExpression;
import org.textway.templates.types.ast.IAstMemberDeclaration;

public class TypesParser {

	public static class ParseException extends Exception {
		private static final long serialVersionUID = 1L;

		public ParseException() {
		}
	}

	private final ErrorReporter reporter;

	public TypesParser(ErrorReporter reporter) {
		this.reporter = reporter;
	}

	private static final boolean DEBUG_SYNTAX = false;
	private static final int lapg_action[] = {
		-1, -1, -3, 2, -9, 1, -1, 4, -1, 67, -15, -23, -29, -1, -1, -43,
		43, 45, 44, -57, 10, 11, 12, -1, -71, -83, -1, 68, -99, -107, 21, -1,
		9, -115, -1, 47, 7, -1, -1, -125, -1, 14, -139, -145, -1, 36, 22, 48,
		-1, 31, -1, -1, -1, 26, 27, -155, -1, 16, -1, -1, -1, 42, -1, -1,
		-1, -1, 24, 51, 52, 53, -163, 23, 50, 49, -1, 17, 40, 39, 37, 20,
		34, 35, -177, 33, -185, 25, 60, -193, -1, -199, -1, -1, 59, -1, -205, -1,
		32, 61, 64, 65, 66, -1, -1, 56, 62, -1, -1, 63, -1, -2
	};

	private static final short lapg_lalr[] = {
		20, -1, 0, 0, -1, -2, 21, -1, 14, 3, -1, -2, 7, -1, 10, 69,
		14, 69, -1, -2, 10, -1, 14, 8, -1, -2, 1, -1, 14, -1, 22, -1,
		23, -1, 24, -1, 15, 5, -1, -2, 1, -1, 14, -1, 22, -1, 23, -1,
		24, -1, 13, 18, -1, -2, 1, -1, 14, -1, 22, -1, 23, -1, 24, -1,
		15, 6, -1, -2, 18, -1, 1, 41, 10, 41, 13, 41, 17, 41, -1, -2,
		7, -1, 8, -1, 1, 46, 10, 46, 13, 46, 17, 46, 18, 46, -1, -2,
		7, -1, 10, 70, 14, 70, -1, -2, 10, -1, 13, 19, 17, 19, -1, -2,
		16, -1, 18, -1, 9, 13, 12, 13, -1, -2, 1, -1, 14, -1, 22, -1,
		23, -1, 24, -1, 17, 18, -1, -2, 12, -1, 9, 15, -1, -2, 6, -1,
		9, 38, 10, 38, 19, 38, -1, -2, 10, -1, 9, 28, 19, 28, -1, -2,
		1, -1, 2, -1, 3, -1, 4, -1, 18, -1, 19, 57, -1, -2, 10, -1,
		9, 29, 19, 29, -1, -2, 10, -1, 9, 30, 19, 30, -1, -2, 10, -1,
		19, 58, -1, -2, 1, -1, 17, 54, -1, -2, 10, -1, 17, 55, -1, -2
	};

	private static final short lapg_sym_goto[] = {
		0, 1, 23, 31, 41, 46, 46, 47, 51, 53, 56, 64, 68, 71, 74, 80,
		82, 84, 86, 93, 96, 98, 99, 104, 109, 114, 116, 118, 119, 120, 122, 123,
		124, 126, 128, 130, 132, 133, 134, 135, 137, 139, 141, 144, 147, 151, 156, 161,
		166, 171, 176, 177, 178, 180, 192, 193, 194, 195, 196, 197, 199, 200, 201
	};

	private static final short lapg_sym_from[] = {
		108, 1, 6, 12, 13, 14, 15, 19, 23, 37, 39, 40, 56, 63, 64, 65,
		70, 89, 90, 91, 101, 102, 106, 56, 63, 64, 70, 90, 91, 101, 106, 34,
		40, 56, 59, 60, 65, 70, 91, 101, 106, 56, 70, 91, 101, 106, 43, 10,
		25, 28, 74, 25, 59, 52, 58, 62, 11, 29, 44, 55, 82, 84, 87, 94,
		50, 51, 93, 105, 42, 93, 105, 31, 93, 105, 8, 12, 15, 19, 37, 39,
		26, 38, 33, 74, 48, 95, 24, 33, 56, 70, 91, 101, 106, 44, 52, 88,
		0, 2, 4, 12, 15, 19, 37, 39, 12, 15, 19, 37, 39, 12, 15, 19,
		37, 39, 40, 65, 40, 65, 0, 0, 0, 2, 4, 12, 12, 19, 12, 19,
		12, 19, 15, 39, 42, 33, 40, 40, 65, 40, 65, 63, 64, 63, 64, 90,
		34, 40, 65, 34, 40, 60, 65, 12, 15, 19, 37, 39, 12, 15, 19, 37,
		39, 56, 70, 91, 101, 106, 56, 70, 91, 101, 106, 56, 70, 91, 101, 106,
		70, 89, 93, 105, 6, 12, 14, 15, 19, 37, 39, 56, 70, 91, 101, 106,
		6, 4, 12, 33, 42, 15, 39, 89, 70
	};

	private static final short lapg_sym_to[] = {
		109, 4, 9, 9, 27, 9, 9, 9, 33, 9, 9, 49, 9, 80, 80, 49,
		9, 93, 80, 9, 9, 105, 9, 67, 81, 81, 67, 81, 67, 67, 67, 43,
		43, 68, 76, 43, 43, 68, 68, 68, 68, 69, 69, 69, 69, 69, 59, 13,
		13, 13, 13, 35, 77, 65, 75, 79, 14, 37, 60, 60, 90, 90, 91, 102,
		63, 64, 98, 98, 56, 99, 99, 38, 100, 100, 12, 15, 15, 15, 15, 15,
		36, 47, 39, 89, 62, 103, 34, 40, 70, 70, 70, 70, 70, 61, 66, 92,
		1, 1, 6, 16, 16, 16, 16, 16, 17, 17, 17, 17, 17, 18, 18, 18,
		18, 18, 50, 50, 51, 51, 108, 2, 3, 5, 7, 19, 20, 32, 21, 21,
		22, 22, 29, 29, 57, 41, 52, 53, 85, 54, 54, 82, 84, 83, 83, 96,
		44, 55, 55, 45, 45, 78, 45, 23, 30, 23, 46, 30, 24, 24, 24, 24,
		24, 71, 86, 97, 104, 107, 72, 72, 72, 72, 72, 73, 73, 73, 73, 73,
		87, 94, 101, 106, 10, 25, 28, 25, 25, 25, 25, 74, 74, 74, 74, 74,
		11, 8, 26, 42, 58, 31, 48, 95, 88
	};

	private static final short lapg_rlen[] = {
		1, 2, 1, 0, 1, 0, 1, 6, 2, 2, 1, 1, 1, 0, 1, 0,
		1, 5, 0, 1, 6, 1, 3, 2, 3, 3, 1, 1, 1, 3, 3, 1,
		3, 1, 1, 1, 1, 3, 1, 3, 3, 1, 4, 1, 1, 1, 1, 2,
		4, 1, 1, 1, 1, 1, 0, 1, 4, 0, 1, 3, 1, 3, 3, 5,
		1, 1, 1, 1, 3, 1, 3
	};

	private static final short lapg_rlex[] = {
		27, 28, 28, 55, 55, 56, 56, 29, 30, 31, 31, 32, 32, 57, 57, 58,
		58, 33, 59, 59, 34, 35, 35, 36, 37, 38, 38, 39, 39, 40, 40, 40,
		41, 41, 42, 42, 43, 43, 44, 44, 44, 45, 45, 46, 46, 46, 46, 46,
		46, 47, 47, 48, 48, 48, 60, 60, 49, 61, 61, 49, 50, 50, 51, 51,
		52, 52, 52, 53, 53, 54, 54
	};

	protected static final String[] lapg_syms = new String[] {
		"eoi",
		"identifier",
		"scon",
		"icon",
		"bcon",
		"_skip",
		"'..'",
		"'.'",
		"'*'",
		"';'",
		"','",
		"':'",
		"'='",
		"'=>'",
		"'{'",
		"'}'",
		"'('",
		"')'",
		"'['",
		"']'",
		"Lclass",
		"Lextends",
		"Lint",
		"Lbool",
		"Lstring",
		"Lset",
		"Lchoice",
		"input",
		"declarations",
		"type_declaration",
		"extends_clause",
		"member_declarations",
		"member_declaration",
		"feature_declaration",
		"method_declaration",
		"parameters",
		"defaultval",
		"modifiers",
		"constraints",
		"constraint",
		"string_constraint",
		"strings",
		"string",
		"multiplicity_list",
		"multiplicity",
		"type_ex",
		"type",
		"expression",
		"literal_expression",
		"structural_expression",
		"expression_list",
		"map_entries",
		"map_separator",
		"name",
		"name_list",
		"extends_clauseopt",
		"member_declarationsopt",
		"modifiersopt",
		"defaultvalopt",
		"parametersopt",
		"map_entriesopt",
		"expression_listopt",
	};

	public interface Tokens extends Lexems {
		// non-terminals
		public static final int input = 27;
		public static final int declarations = 28;
		public static final int type_declaration = 29;
		public static final int extends_clause = 30;
		public static final int member_declarations = 31;
		public static final int member_declaration = 32;
		public static final int feature_declaration = 33;
		public static final int method_declaration = 34;
		public static final int parameters = 35;
		public static final int defaultval = 36;
		public static final int modifiers = 37;
		public static final int constraints = 38;
		public static final int constraint = 39;
		public static final int string_constraint = 40;
		public static final int strings = 41;
		public static final int string = 42;
		public static final int multiplicity_list = 43;
		public static final int multiplicity = 44;
		public static final int type_ex = 45;
		public static final int type = 46;
		public static final int expression = 47;
		public static final int literal_expression = 48;
		public static final int structural_expression = 49;
		public static final int expression_list = 50;
		public static final int map_entries = 51;
		public static final int map_separator = 52;
		public static final int name = 53;
		public static final int name_list = 54;
		public static final int extends_clauseopt = 55;
		public static final int member_declarationsopt = 56;
		public static final int modifiersopt = 57;
		public static final int defaultvalopt = 58;
		public static final int parametersopt = 59;
		public static final int map_entriesopt = 60;
		public static final int expression_listopt = 61;
	}

	protected final static int lapg_next(int state, int symbol) {
		int p;
		if (lapg_action[state] < -2) {
			for (p = -lapg_action[state] - 3; lapg_lalr[p] >= 0; p += 2) {
				if (lapg_lalr[p] == symbol) {
					break;
				}
			}
			return lapg_lalr[p + 1];
		}
		return lapg_action[state];
	}

	protected final static int lapg_state_sym(int state, int symbol) {
		int min = lapg_sym_goto[symbol], max = lapg_sym_goto[symbol + 1] - 1;
		int i, e;

		while (min <= max) {
			e = (min + max) >> 1;
			i = lapg_sym_from[e];
			if (i == state) {
				return lapg_sym_to[e];
			} else if (i < state) {
				min = e + 1;
			} else {
				max = e - 1;
			}
		}
		return -1;
	}

	protected int lapg_head;
	protected LapgSymbol[] lapg_m;
	protected LapgSymbol lapg_n;

	public AstInput parse(TypesLexer lexer) throws IOException, ParseException {

		lapg_m = new LapgSymbol[1024];
		lapg_head = 0;

		lapg_m[0] = new LapgSymbol();
		lapg_m[0].state = 0;
		lapg_n = lexer.next();

		while (lapg_m[lapg_head].state != 109) {
			int lapg_i = lapg_next(lapg_m[lapg_head].state, lapg_n.lexem);

			if (lapg_i >= 0) {
				reduce(lapg_i);
			} else if (lapg_i == -1) {
				shift(lexer);
			}

			if (lapg_i == -2 || lapg_m[lapg_head].state == -1) {
				break;
			}
		}

		if (lapg_m[lapg_head].state != 109) {
			reporter.error(lapg_n.offset, lapg_n.endoffset, lexer.getTokenLine(), MessageFormat.format("syntax error before line {0}", lexer.getTokenLine()));
			throw new ParseException();
		}
		return (AstInput)lapg_m[lapg_head - 1].sym;
	}

	protected void shift(TypesLexer lexer) throws IOException {
		lapg_m[++lapg_head] = lapg_n;
		lapg_m[lapg_head].state = lapg_state_sym(lapg_m[lapg_head - 1].state, lapg_n.lexem);
		if (DEBUG_SYNTAX) {
			System.out.println(MessageFormat.format("shift: {0} ({1})", lapg_syms[lapg_n.lexem], lexer.current()));
		}
		if (lapg_m[lapg_head].state != -1 && lapg_n.lexem != 0) {
			lapg_n = lexer.next();
		}
	}

	protected void reduce(int rule) {
		LapgSymbol lapg_gg = new LapgSymbol();
		lapg_gg.sym = (lapg_rlen[rule] != 0) ? lapg_m[lapg_head + 1 - lapg_rlen[rule]].sym : null;
		lapg_gg.lexem = lapg_rlex[rule];
		lapg_gg.state = 0;
		if (DEBUG_SYNTAX) {
			System.out.println("reduce to " + lapg_syms[lapg_rlex[rule]]);
		}
		LapgSymbol startsym = (lapg_rlen[rule] != 0) ? lapg_m[lapg_head + 1 - lapg_rlen[rule]] : lapg_n;
		lapg_gg.line = startsym.line;
		lapg_gg.offset = startsym.offset;
		lapg_gg.endoffset = (lapg_rlen[rule] != 0) ? lapg_m[lapg_head].endoffset : lapg_n.offset;
		applyRule(lapg_gg, rule, lapg_rlen[rule]);
		for (int e = lapg_rlen[rule]; e > 0; e--) {
			lapg_m[lapg_head--] = null;
		}
		lapg_m[++lapg_head] = lapg_gg;
		lapg_m[lapg_head].state = lapg_state_sym(lapg_m[lapg_head-1].state, lapg_gg.lexem);
	}

	@SuppressWarnings("unchecked")
	protected void applyRule(LapgSymbol lapg_gg, int rule, int ruleLength) {
		switch (rule) {
			case 0:  // input ::= declarations
				lapg_gg.sym = new AstInput(
						((List<AstTypeDeclaration>)lapg_m[lapg_head-0].sym) /* declarations */,
						null /* input */, lapg_m[lapg_head-0].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 1:  // declarations ::= declarations type_declaration
				((List<AstTypeDeclaration>)lapg_m[lapg_head-1].sym).add(((AstTypeDeclaration)lapg_m[lapg_head-0].sym));
				break;
			case 2:  // declarations ::= type_declaration
				lapg_gg.sym = new ArrayList();
				((List<AstTypeDeclaration>)lapg_gg.sym).add(((AstTypeDeclaration)lapg_m[lapg_head-0].sym));
				break;
			case 7:  // type_declaration ::= Lclass identifier extends_clauseopt '{' member_declarationsopt '}'
				lapg_gg.sym = new AstTypeDeclaration(
						((String)lapg_m[lapg_head-4].sym) /* name */,
						((List<List<String>>)lapg_m[lapg_head-3].sym) /* _extends */,
						((List<IAstMemberDeclaration>)lapg_m[lapg_head-1].sym) /* memberDeclarationsopt */,
						null /* input */, lapg_m[lapg_head-5].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 8:  // extends_clause ::= Lextends name_list
				lapg_gg.sym = ((List<List<String>>)lapg_m[lapg_head-0].sym);
				break;
			case 9:  // member_declarations ::= member_declarations member_declaration
				((List<IAstMemberDeclaration>)lapg_m[lapg_head-1].sym).add(((IAstMemberDeclaration)lapg_m[lapg_head-0].sym));
				break;
			case 10:  // member_declarations ::= member_declaration
				lapg_gg.sym = new ArrayList();
				((List<IAstMemberDeclaration>)lapg_gg.sym).add(((IAstMemberDeclaration)lapg_m[lapg_head-0].sym));
				break;
			case 17:  // feature_declaration ::= type_ex identifier modifiersopt defaultvalopt ';'
				lapg_gg.sym = new AstFeatureDeclaration(
						((String)lapg_m[lapg_head-3].sym) /* name */,
						((AstTypeEx)lapg_m[lapg_head-4].sym) /* typeEx */,
						((List<AstConstraint>)lapg_m[lapg_head-2].sym) /* modifiersopt */,
						((IAstExpression)lapg_m[lapg_head-1].sym) /* defaultvalopt */,
						null /* input */, lapg_m[lapg_head-4].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 20:  // method_declaration ::= type_ex identifier '(' parametersopt ')' ';'
				lapg_gg.sym = new AstMethodDeclaration(
						((AstTypeEx)lapg_m[lapg_head-5].sym) /* returnType */,
						((String)lapg_m[lapg_head-4].sym) /* name */,
						((List<AstTypeEx>)lapg_m[lapg_head-2].sym) /* parametersopt */,
						null /* input */, lapg_m[lapg_head-5].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 21:  // parameters ::= type_ex
				lapg_gg.sym = new ArrayList();
				((List<AstTypeEx>)lapg_gg.sym).add(((AstTypeEx)lapg_m[lapg_head-0].sym));
				break;
			case 22:  // parameters ::= parameters ',' type_ex
				((List<AstTypeEx>)lapg_m[lapg_head-2].sym).add(((AstTypeEx)lapg_m[lapg_head-0].sym));
				break;
			case 23:  // defaultval ::= '=' expression
				lapg_gg.sym = ((IAstExpression)lapg_m[lapg_head-0].sym);
				break;
			case 24:  // modifiers ::= '[' constraints ']'
				lapg_gg.sym = ((List<AstConstraint>)lapg_m[lapg_head-1].sym);
				break;
			case 25:  // constraints ::= constraints ';' constraint
				((List<AstConstraint>)lapg_m[lapg_head-2].sym).add(((AstConstraint)lapg_m[lapg_head-0].sym));
				break;
			case 26:  // constraints ::= constraint
				lapg_gg.sym = new ArrayList();
				((List<AstConstraint>)lapg_gg.sym).add(((AstConstraint)lapg_m[lapg_head-0].sym));
				break;
			case 27:  // constraint ::= string_constraint
				lapg_gg.sym = new AstConstraint(
						((AstStringConstraint)lapg_m[lapg_head-0].sym) /* stringConstraint */,
						null /* multiplicityList */,
						null /* input */, lapg_m[lapg_head-0].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 28:  // constraint ::= multiplicity_list
				lapg_gg.sym = new AstConstraint(
						null /* stringConstraint */,
						((List<AstMultiplicity>)lapg_m[lapg_head-0].sym) /* multiplicityList */,
						null /* input */, lapg_m[lapg_head-0].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 29:  // string_constraint ::= Lset ':' strings
				lapg_gg.sym = new AstStringConstraint(
						AstStringConstraint.LSET,
						((List<Ast_String>)lapg_m[lapg_head-0].sym) /* strings */,
						null /* identifier */,
						null /* input */, lapg_m[lapg_head-2].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 30:  // string_constraint ::= Lchoice ':' strings
				lapg_gg.sym = new AstStringConstraint(
						AstStringConstraint.LCHOICE,
						((List<Ast_String>)lapg_m[lapg_head-0].sym) /* strings */,
						null /* identifier */,
						null /* input */, lapg_m[lapg_head-2].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 31:  // string_constraint ::= identifier
				lapg_gg.sym = new AstStringConstraint(
						0,
						null /* strings */,
						((String)lapg_m[lapg_head-0].sym) /* identifier */,
						null /* input */, lapg_m[lapg_head-0].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 32:  // strings ::= strings ',' string
				((List<Ast_String>)lapg_m[lapg_head-2].sym).add(((Ast_String)lapg_m[lapg_head-0].sym));
				break;
			case 33:  // strings ::= string
				lapg_gg.sym = new ArrayList();
				((List<Ast_String>)lapg_gg.sym).add(((Ast_String)lapg_m[lapg_head-0].sym));
				break;
			case 34:  // string ::= identifier
				lapg_gg.sym = new Ast_String(
						((String)lapg_m[lapg_head-0].sym) /* identifier */,
						null /* scon */,
						null /* input */, lapg_m[lapg_head-0].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 35:  // string ::= scon
				lapg_gg.sym = new Ast_String(
						null /* identifier */,
						((String)lapg_m[lapg_head-0].sym) /* scon */,
						null /* input */, lapg_m[lapg_head-0].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 36:  // multiplicity_list ::= multiplicity
				lapg_gg.sym = new ArrayList();
				((List<AstMultiplicity>)lapg_gg.sym).add(((AstMultiplicity)lapg_m[lapg_head-0].sym));
				break;
			case 37:  // multiplicity_list ::= multiplicity_list ',' multiplicity
				((List<AstMultiplicity>)lapg_m[lapg_head-2].sym).add(((AstMultiplicity)lapg_m[lapg_head-0].sym));
				break;
			case 38:  // multiplicity ::= icon
				lapg_gg.sym = new AstMultiplicity(
						((Integer)lapg_m[lapg_head-0].sym) /* lo */,
						false,
						null /* hi */,
						null /* input */, lapg_m[lapg_head-0].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 39:  // multiplicity ::= icon '..' '*'
				lapg_gg.sym = new AstMultiplicity(
						((Integer)lapg_m[lapg_head-2].sym) /* lo */,
						true,
						null /* hi */,
						null /* input */, lapg_m[lapg_head-2].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 40:  // multiplicity ::= icon '..' icon
				lapg_gg.sym = new AstMultiplicity(
						((Integer)lapg_m[lapg_head-2].sym) /* lo */,
						false,
						((Integer)lapg_m[lapg_head-0].sym) /* hi */,
						null /* input */, lapg_m[lapg_head-2].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 41:  // type_ex ::= type
				lapg_gg.sym = new AstTypeEx(
						((AstType)lapg_m[lapg_head-0].sym) /* type */,
						null /* multiplicityList */,
						null /* input */, lapg_m[lapg_head-0].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 42:  // type_ex ::= type '[' multiplicity_list ']'
				lapg_gg.sym = new AstTypeEx(
						((AstType)lapg_m[lapg_head-3].sym) /* type */,
						((List<AstMultiplicity>)lapg_m[lapg_head-1].sym) /* multiplicityList */,
						null /* input */, lapg_m[lapg_head-3].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 43:  // type ::= Lint
				lapg_gg.sym = new AstType(
						AstType.LINT,
						false,
						false,
						null /* name */,
						null /* parametersopt */,
						null /* input */, lapg_m[lapg_head-0].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 44:  // type ::= Lstring
				lapg_gg.sym = new AstType(
						AstType.LSTRING,
						false,
						false,
						null /* name */,
						null /* parametersopt */,
						null /* input */, lapg_m[lapg_head-0].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 45:  // type ::= Lbool
				lapg_gg.sym = new AstType(
						AstType.LBOOL,
						false,
						false,
						null /* name */,
						null /* parametersopt */,
						null /* input */, lapg_m[lapg_head-0].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 46:  // type ::= name
				lapg_gg.sym = new AstType(
						0,
						false,
						false,
						((List<String>)lapg_m[lapg_head-0].sym) /* name */,
						null /* parametersopt */,
						null /* input */, lapg_m[lapg_head-0].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 47:  // type ::= name '*'
				lapg_gg.sym = new AstType(
						0,
						true,
						false,
						((List<String>)lapg_m[lapg_head-1].sym) /* name */,
						null /* parametersopt */,
						null /* input */, lapg_m[lapg_head-1].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 48:  // type ::= '{' parametersopt '=>' '}'
				lapg_gg.sym = new AstType(
						0,
						false,
						true,
						null /* name */,
						((List<AstTypeEx>)lapg_m[lapg_head-2].sym) /* parametersopt */,
						null /* input */, lapg_m[lapg_head-3].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 51:  // literal_expression ::= scon
				lapg_gg.sym = new AstLiteralExpression(
						((String)lapg_m[lapg_head-0].sym) /* scon */,
						null /* icon */,
						null /* bcon */,
						null /* input */, lapg_m[lapg_head-0].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 52:  // literal_expression ::= icon
				lapg_gg.sym = new AstLiteralExpression(
						null /* scon */,
						((Integer)lapg_m[lapg_head-0].sym) /* icon */,
						null /* bcon */,
						null /* input */, lapg_m[lapg_head-0].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 53:  // literal_expression ::= bcon
				lapg_gg.sym = new AstLiteralExpression(
						null /* scon */,
						null /* icon */,
						((Boolean)lapg_m[lapg_head-0].sym) /* bcon */,
						null /* input */, lapg_m[lapg_head-0].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 56:  // structural_expression ::= name '(' map_entriesopt ')'
				lapg_gg.sym = new AstStructuralExpression(
						((List<String>)lapg_m[lapg_head-3].sym) /* name */,
						((List<AstMapEntriesItem>)lapg_m[lapg_head-1].sym) /* mapEntriesopt */,
						null /* expressionListopt */,
						null /* input */, lapg_m[lapg_head-3].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 59:  // structural_expression ::= '[' expression_listopt ']'
				lapg_gg.sym = new AstStructuralExpression(
						null /* name */,
						null /* mapEntriesopt */,
						((List<IAstExpression>)lapg_m[lapg_head-1].sym) /* expressionListopt */,
						null /* input */, lapg_m[lapg_head-2].offset, lapg_m[lapg_head-0].endoffset);
				break;
			case 60:  // expression_list ::= expression
				lapg_gg.sym = new ArrayList();
				((List<IAstExpression>)lapg_gg.sym).add(((IAstExpression)lapg_m[lapg_head-0].sym));
				break;
			case 61:  // expression_list ::= expression_list ',' expression
				((List<IAstExpression>)lapg_m[lapg_head-2].sym).add(((IAstExpression)lapg_m[lapg_head-0].sym));
				break;
			case 62:  // map_entries ::= identifier map_separator expression
				lapg_gg.sym = new ArrayList();
				((List<AstMapEntriesItem>)lapg_gg.sym).add(new AstMapEntriesItem(
						((String)lapg_m[lapg_head-2].sym) /* identifier */,
						((AstMapSeparator)lapg_m[lapg_head-1].sym) /* mapSeparator */,
						((IAstExpression)lapg_m[lapg_head-0].sym) /* expression */,
						null /* input */, lapg_m[lapg_head-2].offset, lapg_m[lapg_head-0].endoffset));
				break;
			case 63:  // map_entries ::= map_entries ',' identifier map_separator expression
				((List<AstMapEntriesItem>)lapg_m[lapg_head-4].sym).add(new AstMapEntriesItem(
						((String)lapg_m[lapg_head-2].sym) /* identifier */,
						((AstMapSeparator)lapg_m[lapg_head-1].sym) /* mapSeparator */,
						((IAstExpression)lapg_m[lapg_head-0].sym) /* expression */,
						null /* input */, lapg_m[lapg_head-4].offset, lapg_m[lapg_head-0].endoffset));
				break;
			case 64:  // map_separator ::= ':'
				lapg_gg.sym = AstMapSeparator.COLON;
				break;
			case 65:  // map_separator ::= '='
				lapg_gg.sym = AstMapSeparator.EQUAL;
				break;
			case 66:  // map_separator ::= '=>'
				lapg_gg.sym = AstMapSeparator.EQUALGREATER;
				break;
			case 67:  // name ::= identifier
				lapg_gg.sym = new ArrayList();
				((List<String>)lapg_gg.sym).add(((String)lapg_m[lapg_head-0].sym));
				break;
			case 68:  // name ::= name '.' identifier
				((List<String>)lapg_m[lapg_head-2].sym).add(((String)lapg_m[lapg_head-0].sym));
				break;
			case 69:  // name_list ::= name
				lapg_gg.sym = new ArrayList();
				((List<List<String>>)lapg_gg.sym).add(((List<String>)lapg_m[lapg_head-0].sym));
				break;
			case 70:  // name_list ::= name_list ',' name
				((List<List<String>>)lapg_m[lapg_head-2].sym).add(((List<String>)lapg_m[lapg_head-0].sym));
				break;
		}
	}
}
