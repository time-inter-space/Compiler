// Generated from d:\ACM\Compiler\src\parser\Mx.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MxLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Void=1, Bool=2, Int=3, String=4, New=5, Class=6, Null=7, True=8, False=9, 
		This=10, If=11, Else=12, For=13, While=14, Break=15, Continue=16, Return=17, 
		LeftParen=18, RightParen=19, LeftBracket=20, RightBracket=21, LeftBrace=22, 
		RightBrace=23, Less=24, LessEqual=25, Greater=26, GreaterEqual=27, LeftShift=28, 
		RightShift=29, Plus=30, Minus=31, PlusPlus=32, MinusMinus=33, Multiple=34, 
		Division=35, Modulo=36, And=37, Or=38, AndAnd=39, OrOr=40, Caret=41, Not=42, 
		Tilde=43, Assign=44, Equal=45, NotEqual=46, Dot=47, Question=48, Colon=49, 
		Semi=50, Comma=51, Identifier=52, DecimalInteger=53, StringLiteral=54, 
		Whitespace=55, Newline=56, BlockComment=57, LineComment=58;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"Void", "Bool", "Int", "String", "New", "Class", "Null", "True", "False", 
			"This", "If", "Else", "For", "While", "Break", "Continue", "Return", 
			"LeftParen", "RightParen", "LeftBracket", "RightBracket", "LeftBrace", 
			"RightBrace", "Less", "LessEqual", "Greater", "GreaterEqual", "LeftShift", 
			"RightShift", "Plus", "Minus", "PlusPlus", "MinusMinus", "Multiple", 
			"Division", "Modulo", "And", "Or", "AndAnd", "OrOr", "Caret", "Not", 
			"Tilde", "Assign", "Equal", "NotEqual", "Dot", "Question", "Colon", "Semi", 
			"Comma", "Identifier", "DecimalInteger", "StringLiteral", "Whitespace", 
			"Newline", "BlockComment", "LineComment"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'void'", "'bool'", "'int'", "'string'", "'new'", "'class'", "'null'", 
			"'true'", "'false'", "'this'", "'if'", "'else'", "'for'", "'while'", 
			"'break'", "'continue'", "'return'", "'('", "')'", "'['", "']'", "'{'", 
			"'}'", "'<'", "'<='", "'>'", "'>='", "'<<'", "'>>'", "'+'", "'-'", "'++'", 
			"'--'", "'*'", "'/'", "'%'", "'&'", "'|'", "'&&'", "'||'", "'^'", "'!'", 
			"'~'", "'='", "'=='", "'!='", "'.'", "'?'", "':'", "';'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Void", "Bool", "Int", "String", "New", "Class", "Null", "True", 
			"False", "This", "If", "Else", "For", "While", "Break", "Continue", "Return", 
			"LeftParen", "RightParen", "LeftBracket", "RightBracket", "LeftBrace", 
			"RightBrace", "Less", "LessEqual", "Greater", "GreaterEqual", "LeftShift", 
			"RightShift", "Plus", "Minus", "PlusPlus", "MinusMinus", "Multiple", 
			"Division", "Modulo", "And", "Or", "AndAnd", "OrOr", "Caret", "Not", 
			"Tilde", "Assign", "Equal", "NotEqual", "Dot", "Question", "Colon", "Semi", 
			"Comma", "Identifier", "DecimalInteger", "StringLiteral", "Whitespace", 
			"Newline", "BlockComment", "LineComment"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public MxLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Mx.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2<\u016a\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\3\2\3\2\3\2"+
		"\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\t\3\t"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f"+
		"\3\f\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21"+
		"\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\24\3\24\3\25"+
		"\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\33\3\33"+
		"\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3"+
		"!\3\"\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*"+
		"\3+\3+\3,\3,\3-\3-\3.\3.\3.\3/\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63"+
		"\3\63\3\64\3\64\3\65\3\65\7\65\u0124\n\65\f\65\16\65\u0127\13\65\3\66"+
		"\3\66\7\66\u012b\n\66\f\66\16\66\u012e\13\66\3\66\5\66\u0131\n\66\3\67"+
		"\3\67\3\67\3\67\3\67\3\67\3\67\3\67\7\67\u013b\n\67\f\67\16\67\u013e\13"+
		"\67\3\67\3\67\38\68\u0143\n8\r8\168\u0144\38\38\39\39\59\u014b\n9\39\5"+
		"9\u014e\n9\39\39\3:\3:\3:\3:\7:\u0156\n:\f:\16:\u0159\13:\3:\3:\3:\3:"+
		"\3:\3;\3;\3;\3;\7;\u0164\n;\f;\16;\u0167\13;\3;\3;\3\u0157\2<\3\3\5\4"+
		"\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C"+
		"#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o9q:s;u<\3"+
		"\2\t\4\2C\\c|\6\2\62;C\\aac|\3\2\63;\3\2\62;\4\2\"#%\u0080\4\2\13\13\""+
		"\"\4\2\f\f\17\17\2\u0175\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3"+
		"\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2"+
		"\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2"+
		"Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3"+
		"\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2"+
		"\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\3"+
		"w\3\2\2\2\5|\3\2\2\2\7\u0081\3\2\2\2\t\u0085\3\2\2\2\13\u008c\3\2\2\2"+
		"\r\u0090\3\2\2\2\17\u0096\3\2\2\2\21\u009b\3\2\2\2\23\u00a0\3\2\2\2\25"+
		"\u00a6\3\2\2\2\27\u00ab\3\2\2\2\31\u00ae\3\2\2\2\33\u00b3\3\2\2\2\35\u00b7"+
		"\3\2\2\2\37\u00bd\3\2\2\2!\u00c3\3\2\2\2#\u00cc\3\2\2\2%\u00d3\3\2\2\2"+
		"\'\u00d5\3\2\2\2)\u00d7\3\2\2\2+\u00d9\3\2\2\2-\u00db\3\2\2\2/\u00dd\3"+
		"\2\2\2\61\u00df\3\2\2\2\63\u00e1\3\2\2\2\65\u00e4\3\2\2\2\67\u00e6\3\2"+
		"\2\29\u00e9\3\2\2\2;\u00ec\3\2\2\2=\u00ef\3\2\2\2?\u00f1\3\2\2\2A\u00f3"+
		"\3\2\2\2C\u00f6\3\2\2\2E\u00f9\3\2\2\2G\u00fb\3\2\2\2I\u00fd\3\2\2\2K"+
		"\u00ff\3\2\2\2M\u0101\3\2\2\2O\u0103\3\2\2\2Q\u0106\3\2\2\2S\u0109\3\2"+
		"\2\2U\u010b\3\2\2\2W\u010d\3\2\2\2Y\u010f\3\2\2\2[\u0111\3\2\2\2]\u0114"+
		"\3\2\2\2_\u0117\3\2\2\2a\u0119\3\2\2\2c\u011b\3\2\2\2e\u011d\3\2\2\2g"+
		"\u011f\3\2\2\2i\u0121\3\2\2\2k\u0130\3\2\2\2m\u0132\3\2\2\2o\u0142\3\2"+
		"\2\2q\u014d\3\2\2\2s\u0151\3\2\2\2u\u015f\3\2\2\2wx\7x\2\2xy\7q\2\2yz"+
		"\7k\2\2z{\7f\2\2{\4\3\2\2\2|}\7d\2\2}~\7q\2\2~\177\7q\2\2\177\u0080\7"+
		"n\2\2\u0080\6\3\2\2\2\u0081\u0082\7k\2\2\u0082\u0083\7p\2\2\u0083\u0084"+
		"\7v\2\2\u0084\b\3\2\2\2\u0085\u0086\7u\2\2\u0086\u0087\7v\2\2\u0087\u0088"+
		"\7t\2\2\u0088\u0089\7k\2\2\u0089\u008a\7p\2\2\u008a\u008b\7i\2\2\u008b"+
		"\n\3\2\2\2\u008c\u008d\7p\2\2\u008d\u008e\7g\2\2\u008e\u008f\7y\2\2\u008f"+
		"\f\3\2\2\2\u0090\u0091\7e\2\2\u0091\u0092\7n\2\2\u0092\u0093\7c\2\2\u0093"+
		"\u0094\7u\2\2\u0094\u0095\7u\2\2\u0095\16\3\2\2\2\u0096\u0097\7p\2\2\u0097"+
		"\u0098\7w\2\2\u0098\u0099\7n\2\2\u0099\u009a\7n\2\2\u009a\20\3\2\2\2\u009b"+
		"\u009c\7v\2\2\u009c\u009d\7t\2\2\u009d\u009e\7w\2\2\u009e\u009f\7g\2\2"+
		"\u009f\22\3\2\2\2\u00a0\u00a1\7h\2\2\u00a1\u00a2\7c\2\2\u00a2\u00a3\7"+
		"n\2\2\u00a3\u00a4\7u\2\2\u00a4\u00a5\7g\2\2\u00a5\24\3\2\2\2\u00a6\u00a7"+
		"\7v\2\2\u00a7\u00a8\7j\2\2\u00a8\u00a9\7k\2\2\u00a9\u00aa\7u\2\2\u00aa"+
		"\26\3\2\2\2\u00ab\u00ac\7k\2\2\u00ac\u00ad\7h\2\2\u00ad\30\3\2\2\2\u00ae"+
		"\u00af\7g\2\2\u00af\u00b0\7n\2\2\u00b0\u00b1\7u\2\2\u00b1\u00b2\7g\2\2"+
		"\u00b2\32\3\2\2\2\u00b3\u00b4\7h\2\2\u00b4\u00b5\7q\2\2\u00b5\u00b6\7"+
		"t\2\2\u00b6\34\3\2\2\2\u00b7\u00b8\7y\2\2\u00b8\u00b9\7j\2\2\u00b9\u00ba"+
		"\7k\2\2\u00ba\u00bb\7n\2\2\u00bb\u00bc\7g\2\2\u00bc\36\3\2\2\2\u00bd\u00be"+
		"\7d\2\2\u00be\u00bf\7t\2\2\u00bf\u00c0\7g\2\2\u00c0\u00c1\7c\2\2\u00c1"+
		"\u00c2\7m\2\2\u00c2 \3\2\2\2\u00c3\u00c4\7e\2\2\u00c4\u00c5\7q\2\2\u00c5"+
		"\u00c6\7p\2\2\u00c6\u00c7\7v\2\2\u00c7\u00c8\7k\2\2\u00c8\u00c9\7p\2\2"+
		"\u00c9\u00ca\7w\2\2\u00ca\u00cb\7g\2\2\u00cb\"\3\2\2\2\u00cc\u00cd\7t"+
		"\2\2\u00cd\u00ce\7g\2\2\u00ce\u00cf\7v\2\2\u00cf\u00d0\7w\2\2\u00d0\u00d1"+
		"\7t\2\2\u00d1\u00d2\7p\2\2\u00d2$\3\2\2\2\u00d3\u00d4\7*\2\2\u00d4&\3"+
		"\2\2\2\u00d5\u00d6\7+\2\2\u00d6(\3\2\2\2\u00d7\u00d8\7]\2\2\u00d8*\3\2"+
		"\2\2\u00d9\u00da\7_\2\2\u00da,\3\2\2\2\u00db\u00dc\7}\2\2\u00dc.\3\2\2"+
		"\2\u00dd\u00de\7\177\2\2\u00de\60\3\2\2\2\u00df\u00e0\7>\2\2\u00e0\62"+
		"\3\2\2\2\u00e1\u00e2\7>\2\2\u00e2\u00e3\7?\2\2\u00e3\64\3\2\2\2\u00e4"+
		"\u00e5\7@\2\2\u00e5\66\3\2\2\2\u00e6\u00e7\7@\2\2\u00e7\u00e8\7?\2\2\u00e8"+
		"8\3\2\2\2\u00e9\u00ea\7>\2\2\u00ea\u00eb\7>\2\2\u00eb:\3\2\2\2\u00ec\u00ed"+
		"\7@\2\2\u00ed\u00ee\7@\2\2\u00ee<\3\2\2\2\u00ef\u00f0\7-\2\2\u00f0>\3"+
		"\2\2\2\u00f1\u00f2\7/\2\2\u00f2@\3\2\2\2\u00f3\u00f4\7-\2\2\u00f4\u00f5"+
		"\7-\2\2\u00f5B\3\2\2\2\u00f6\u00f7\7/\2\2\u00f7\u00f8\7/\2\2\u00f8D\3"+
		"\2\2\2\u00f9\u00fa\7,\2\2\u00faF\3\2\2\2\u00fb\u00fc\7\61\2\2\u00fcH\3"+
		"\2\2\2\u00fd\u00fe\7\'\2\2\u00feJ\3\2\2\2\u00ff\u0100\7(\2\2\u0100L\3"+
		"\2\2\2\u0101\u0102\7~\2\2\u0102N\3\2\2\2\u0103\u0104\7(\2\2\u0104\u0105"+
		"\7(\2\2\u0105P\3\2\2\2\u0106\u0107\7~\2\2\u0107\u0108\7~\2\2\u0108R\3"+
		"\2\2\2\u0109\u010a\7`\2\2\u010aT\3\2\2\2\u010b\u010c\7#\2\2\u010cV\3\2"+
		"\2\2\u010d\u010e\7\u0080\2\2\u010eX\3\2\2\2\u010f\u0110\7?\2\2\u0110Z"+
		"\3\2\2\2\u0111\u0112\7?\2\2\u0112\u0113\7?\2\2\u0113\\\3\2\2\2\u0114\u0115"+
		"\7#\2\2\u0115\u0116\7?\2\2\u0116^\3\2\2\2\u0117\u0118\7\60\2\2\u0118`"+
		"\3\2\2\2\u0119\u011a\7A\2\2\u011ab\3\2\2\2\u011b\u011c\7<\2\2\u011cd\3"+
		"\2\2\2\u011d\u011e\7=\2\2\u011ef\3\2\2\2\u011f\u0120\7.\2\2\u0120h\3\2"+
		"\2\2\u0121\u0125\t\2\2\2\u0122\u0124\t\3\2\2\u0123\u0122\3\2\2\2\u0124"+
		"\u0127\3\2\2\2\u0125\u0123\3\2\2\2\u0125\u0126\3\2\2\2\u0126j\3\2\2\2"+
		"\u0127\u0125\3\2\2\2\u0128\u012c\t\4\2\2\u0129\u012b\t\5\2\2\u012a\u0129"+
		"\3\2\2\2\u012b\u012e\3\2\2\2\u012c\u012a\3\2\2\2\u012c\u012d\3\2\2\2\u012d"+
		"\u0131\3\2\2\2\u012e\u012c\3\2\2\2\u012f\u0131\7\62\2\2\u0130\u0128\3"+
		"\2\2\2\u0130\u012f\3\2\2\2\u0131l\3\2\2\2\u0132\u013c\7$\2\2\u0133\u0134"+
		"\7^\2\2\u0134\u013b\7p\2\2\u0135\u0136\7^\2\2\u0136\u013b\7^\2\2\u0137"+
		"\u0138\7^\2\2\u0138\u013b\7$\2\2\u0139\u013b\t\6\2\2\u013a\u0133\3\2\2"+
		"\2\u013a\u0135\3\2\2\2\u013a\u0137\3\2\2\2\u013a\u0139\3\2\2\2\u013b\u013e"+
		"\3\2\2\2\u013c\u013a\3\2\2\2\u013c\u013d\3\2\2\2\u013d\u013f\3\2\2\2\u013e"+
		"\u013c\3\2\2\2\u013f\u0140\7$\2\2\u0140n\3\2\2\2\u0141\u0143\t\7\2\2\u0142"+
		"\u0141\3\2\2\2\u0143\u0144\3\2\2\2\u0144\u0142\3\2\2\2\u0144\u0145\3\2"+
		"\2\2\u0145\u0146\3\2\2\2\u0146\u0147\b8\2\2\u0147p\3\2\2\2\u0148\u014a"+
		"\7\17\2\2\u0149\u014b\7\f\2\2\u014a\u0149\3\2\2\2\u014a\u014b\3\2\2\2"+
		"\u014b\u014e\3\2\2\2\u014c\u014e\7\f\2\2\u014d\u0148\3\2\2\2\u014d\u014c"+
		"\3\2\2\2\u014e\u014f\3\2\2\2\u014f\u0150\b9\2\2\u0150r\3\2\2\2\u0151\u0152"+
		"\7\61\2\2\u0152\u0153\7,\2\2\u0153\u0157\3\2\2\2\u0154\u0156\13\2\2\2"+
		"\u0155\u0154\3\2\2\2\u0156\u0159\3\2\2\2\u0157\u0158\3\2\2\2\u0157\u0155"+
		"\3\2\2\2\u0158\u015a\3\2\2\2\u0159\u0157\3\2\2\2\u015a\u015b\7,\2\2\u015b"+
		"\u015c\7\61\2\2\u015c\u015d\3\2\2\2\u015d\u015e\b:\2\2\u015et\3\2\2\2"+
		"\u015f\u0160\7\61\2\2\u0160\u0161\7\61\2\2\u0161\u0165\3\2\2\2\u0162\u0164"+
		"\n\b\2\2\u0163\u0162\3\2\2\2\u0164\u0167\3\2\2\2\u0165\u0163\3\2\2\2\u0165"+
		"\u0166\3\2\2\2\u0166\u0168\3\2\2\2\u0167\u0165\3\2\2\2\u0168\u0169\b;"+
		"\2\2\u0169v\3\2\2\2\r\2\u0125\u012c\u0130\u013a\u013c\u0144\u014a\u014d"+
		"\u0157\u0165\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}