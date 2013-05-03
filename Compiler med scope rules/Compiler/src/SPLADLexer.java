// Generated from SPLAD.g4 by ANTLR 4.0
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SPLADLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__58=1, T__57=2, T__56=3, T__55=4, T__54=5, T__53=6, T__52=7, T__51=8, 
		T__50=9, T__49=10, T__48=11, T__47=12, T__46=13, T__45=14, T__44=15, T__43=16, 
		T__42=17, T__41=18, T__40=19, T__39=20, T__38=21, T__37=22, T__36=23, 
		T__35=24, T__34=25, T__33=26, T__32=27, T__31=28, T__30=29, T__29=30, 
		T__28=31, T__27=32, T__26=33, T__25=34, T__24=35, T__23=36, T__22=37, 
		T__21=38, T__20=39, T__19=40, T__18=41, T__17=42, T__16=43, T__15=44, 
		T__14=45, T__13=46, T__12=47, T__11=48, T__10=49, T__9=50, T__8=51, T__7=52, 
		T__6=53, T__5=54, T__4=55, T__3=56, T__2=57, T__1=58, T__0=59, STRINGTOKEN=60, 
		LETTER=61, DIGIT=62, NOTZERODIGIT=63, COMMENT=64, WS=65, OTHER=66;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'from'", "'*'", "'['", "'A'", "'<'", "'false'", "'!='", "'<='", "'to'", 
		"'double'", "'drink'", "'nothing'", "'char'", "'case'", "'AND'", "'bool'", 
		"')'", "'='", "'const'", "'call'", "'begin'", "'!'", "'<--'", "'LOW'", 
		"'button'", "'using'", "']'", "'default'", "','", "'of'", "'-'", "'while'", 
		"'('", "':'", "'if'", "'int'", "'break'", "'OR'", "'else'", "'add'", "'HIGH'", 
		"'true'", "'is'", "'function'", "'.'", "'INPUT'", "'step'", "'+'", "'return'", 
		"';'", "'container'", "'OUTPUT'", "'>'", "'string'", "'switch'", "'/'", 
		"'>='", "'remove'", "'end'", "STRINGTOKEN", "LETTER", "DIGIT", "NOTZERODIGIT", 
		"COMMENT", "WS", "' '"
	};
	public static final String[] ruleNames = {
		"T__58", "T__57", "T__56", "T__55", "T__54", "T__53", "T__52", "T__51", 
		"T__50", "T__49", "T__48", "T__47", "T__46", "T__45", "T__44", "T__43", 
		"T__42", "T__41", "T__40", "T__39", "T__38", "T__37", "T__36", "T__35", 
		"T__34", "T__33", "T__32", "T__31", "T__30", "T__29", "T__28", "T__27", 
		"T__26", "T__25", "T__24", "T__23", "T__22", "T__21", "T__20", "T__19", 
		"T__18", "T__17", "T__16", "T__15", "T__14", "T__13", "T__12", "T__11", 
		"T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", 
		"T__1", "T__0", "STRINGTOKEN", "LETTER", "DIGIT", "NOTZERODIGIT", "COMMENT", 
		"WS", "OTHER"
	};


	public SPLADLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SPLAD.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 63: COMMENT_action((RuleContext)_localctx, actionIndex); break;

		case 64: WS_action((RuleContext)_localctx, actionIndex); break;

		case 65: OTHER_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1: skip();  break;
		}
	}
	private void OTHER_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2: skip();  break;
		}
	}
	private void COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4D\u01bc\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t"+
		"\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20"+
		"\t\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27"+
		"\t\27\4\30\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36"+
		"\t\36\4\37\t\37\4 \t \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4"+
		"(\t(\4)\t)\4*\t*\4+\t+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62"+
		"\t\62\4\63\t\63\4\64\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4"+
		":\t:\4;\t;\4<\t<\4=\t=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\3\2\3\2\3\2"+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3"+
		"\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3"+
		"\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3"+
		"\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3"+
		"\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3"+
		"\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3"+
		"\33\3\33\3\33\3\33\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3\35\3"+
		"\36\3\36\3\37\3\37\3\37\3 \3 \3!\3!\3!\3!\3!\3!\3\"\3\"\3#\3#\3$\3$\3"+
		"$\3%\3%\3%\3%\3&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3(\3(\3(\3(\3(\3)\3)\3)\3"+
		")\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3,\3,\3,\3-\3-\3-\3-\3-\3-\3-\3-\3-\3"+
		".\3.\3/\3/\3/\3/\3/\3/\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3\62\3\62\3"+
		"\62\3\62\3\62\3\62\3\62\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3"+
		"\64\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\66\3\66\3\67\3\67\3"+
		"\67\3\67\3\67\3\67\3\67\38\38\38\38\38\38\38\39\39\3:\3:\3:\3;\3;\3;\3"+
		";\3;\3;\3;\3<\3<\3<\3<\3=\3=\7=\u018d\n=\f=\16=\u0190\13=\3=\3=\3>\6>"+
		"\u0195\n>\r>\16>\u0196\3?\6?\u019a\n?\r?\16?\u019b\3@\3@\7@\u01a0\n@\f"+
		"@\16@\u01a3\13@\3A\3A\3A\7A\u01a8\nA\fA\16A\u01ab\13A\3A\3A\3A\3A\3A\3"+
		"B\6B\u01b3\nB\rB\16B\u01b4\3B\3B\3C\3C\3C\3C\4\u018e\u01a9D\3\3\1\5\4"+
		"\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1\25\f\1\27\r\1\31\16"+
		"\1\33\17\1\35\20\1\37\21\1!\22\1#\23\1%\24\1\'\25\1)\26\1+\27\1-\30\1"+
		"/\31\1\61\32\1\63\33\1\65\34\1\67\35\19\36\1;\37\1= \1?!\1A\"\1C#\1E$"+
		"\1G%\1I&\1K\'\1M(\1O)\1Q*\1S+\1U,\1W-\1Y.\1[/\1]\60\1_\61\1a\62\1c\63"+
		"\1e\64\1g\65\1i\66\1k\67\1m8\1o9\1q:\1s;\1u<\1w=\1y>\1{?\1}@\1\177A\1"+
		"\u0081B\2\u0083C\3\u0085D\4\3\2\7\4C\\c|\3\62;\3\63;\3\62;\5\13\f\17\17"+
		"\"\"\u01c1\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2"+
		"\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2"+
		"\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2"+
		"\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2"+
		"\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S"+
		"\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2"+
		"\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2"+
		"\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y"+
		"\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3"+
		"\2\2\2\2\u0085\3\2\2\2\3\u0087\3\2\2\2\5\u008c\3\2\2\2\7\u008e\3\2\2\2"+
		"\t\u0090\3\2\2\2\13\u0092\3\2\2\2\r\u0094\3\2\2\2\17\u009a\3\2\2\2\21"+
		"\u009d\3\2\2\2\23\u00a0\3\2\2\2\25\u00a3\3\2\2\2\27\u00aa\3\2\2\2\31\u00b0"+
		"\3\2\2\2\33\u00b8\3\2\2\2\35\u00bd\3\2\2\2\37\u00c2\3\2\2\2!\u00c6\3\2"+
		"\2\2#\u00cb\3\2\2\2%\u00cd\3\2\2\2\'\u00cf\3\2\2\2)\u00d5\3\2\2\2+\u00da"+
		"\3\2\2\2-\u00e0\3\2\2\2/\u00e2\3\2\2\2\61\u00e6\3\2\2\2\63\u00ea\3\2\2"+
		"\2\65\u00f1\3\2\2\2\67\u00f7\3\2\2\29\u00f9\3\2\2\2;\u0101\3\2\2\2=\u0103"+
		"\3\2\2\2?\u0106\3\2\2\2A\u0108\3\2\2\2C\u010e\3\2\2\2E\u0110\3\2\2\2G"+
		"\u0112\3\2\2\2I\u0115\3\2\2\2K\u0119\3\2\2\2M\u011f\3\2\2\2O\u0122\3\2"+
		"\2\2Q\u0127\3\2\2\2S\u012b\3\2\2\2U\u0130\3\2\2\2W\u0135\3\2\2\2Y\u0138"+
		"\3\2\2\2[\u0141\3\2\2\2]\u0143\3\2\2\2_\u0149\3\2\2\2a\u014e\3\2\2\2c"+
		"\u0150\3\2\2\2e\u0157\3\2\2\2g\u0159\3\2\2\2i\u0163\3\2\2\2k\u016a\3\2"+
		"\2\2m\u016c\3\2\2\2o\u0173\3\2\2\2q\u017a\3\2\2\2s\u017c\3\2\2\2u\u017f"+
		"\3\2\2\2w\u0186\3\2\2\2y\u018a\3\2\2\2{\u0194\3\2\2\2}\u0199\3\2\2\2\177"+
		"\u019d\3\2\2\2\u0081\u01a4\3\2\2\2\u0083\u01b2\3\2\2\2\u0085\u01b8\3\2"+
		"\2\2\u0087\u0088\7h\2\2\u0088\u0089\7t\2\2\u0089\u008a\7q\2\2\u008a\u008b"+
		"\7o\2\2\u008b\4\3\2\2\2\u008c\u008d\7,\2\2\u008d\6\3\2\2\2\u008e\u008f"+
		"\7]\2\2\u008f\b\3\2\2\2\u0090\u0091\7C\2\2\u0091\n\3\2\2\2\u0092\u0093"+
		"\7>\2\2\u0093\f\3\2\2\2\u0094\u0095\7h\2\2\u0095\u0096\7c\2\2\u0096\u0097"+
		"\7n\2\2\u0097\u0098\7u\2\2\u0098\u0099\7g\2\2\u0099\16\3\2\2\2\u009a\u009b"+
		"\7#\2\2\u009b\u009c\7?\2\2\u009c\20\3\2\2\2\u009d\u009e\7>\2\2\u009e\u009f"+
		"\7?\2\2\u009f\22\3\2\2\2\u00a0\u00a1\7v\2\2\u00a1\u00a2\7q\2\2\u00a2\24"+
		"\3\2\2\2\u00a3\u00a4\7f\2\2\u00a4\u00a5\7q\2\2\u00a5\u00a6\7w\2\2\u00a6"+
		"\u00a7\7d\2\2\u00a7\u00a8\7n\2\2\u00a8\u00a9\7g\2\2\u00a9\26\3\2\2\2\u00aa"+
		"\u00ab\7f\2\2\u00ab\u00ac\7t\2\2\u00ac\u00ad\7k\2\2\u00ad\u00ae\7p\2\2"+
		"\u00ae\u00af\7m\2\2\u00af\30\3\2\2\2\u00b0\u00b1\7p\2\2\u00b1\u00b2\7"+
		"q\2\2\u00b2\u00b3\7v\2\2\u00b3\u00b4\7j\2\2\u00b4\u00b5\7k\2\2\u00b5\u00b6"+
		"\7p\2\2\u00b6\u00b7\7i\2\2\u00b7\32\3\2\2\2\u00b8\u00b9\7e\2\2\u00b9\u00ba"+
		"\7j\2\2\u00ba\u00bb\7c\2\2\u00bb\u00bc\7t\2\2\u00bc\34\3\2\2\2\u00bd\u00be"+
		"\7e\2\2\u00be\u00bf\7c\2\2\u00bf\u00c0\7u\2\2\u00c0\u00c1\7g\2\2\u00c1"+
		"\36\3\2\2\2\u00c2\u00c3\7C\2\2\u00c3\u00c4\7P\2\2\u00c4\u00c5\7F\2\2\u00c5"+
		" \3\2\2\2\u00c6\u00c7\7d\2\2\u00c7\u00c8\7q\2\2\u00c8\u00c9\7q\2\2\u00c9"+
		"\u00ca\7n\2\2\u00ca\"\3\2\2\2\u00cb\u00cc\7+\2\2\u00cc$\3\2\2\2\u00cd"+
		"\u00ce\7?\2\2\u00ce&\3\2\2\2\u00cf\u00d0\7e\2\2\u00d0\u00d1\7q\2\2\u00d1"+
		"\u00d2\7p\2\2\u00d2\u00d3\7u\2\2\u00d3\u00d4\7v\2\2\u00d4(\3\2\2\2\u00d5"+
		"\u00d6\7e\2\2\u00d6\u00d7\7c\2\2\u00d7\u00d8\7n\2\2\u00d8\u00d9\7n\2\2"+
		"\u00d9*\3\2\2\2\u00da\u00db\7d\2\2\u00db\u00dc\7g\2\2\u00dc\u00dd\7i\2"+
		"\2\u00dd\u00de\7k\2\2\u00de\u00df\7p\2\2\u00df,\3\2\2\2\u00e0\u00e1\7"+
		"#\2\2\u00e1.\3\2\2\2\u00e2\u00e3\7>\2\2\u00e3\u00e4\7/\2\2\u00e4\u00e5"+
		"\7/\2\2\u00e5\60\3\2\2\2\u00e6\u00e7\7N\2\2\u00e7\u00e8\7Q\2\2\u00e8\u00e9"+
		"\7Y\2\2\u00e9\62\3\2\2\2\u00ea\u00eb\7d\2\2\u00eb\u00ec\7w\2\2\u00ec\u00ed"+
		"\7v\2\2\u00ed\u00ee\7v\2\2\u00ee\u00ef\7q\2\2\u00ef\u00f0\7p\2\2\u00f0"+
		"\64\3\2\2\2\u00f1\u00f2\7w\2\2\u00f2\u00f3\7u\2\2\u00f3\u00f4\7k\2\2\u00f4"+
		"\u00f5\7p\2\2\u00f5\u00f6\7i\2\2\u00f6\66\3\2\2\2\u00f7\u00f8\7_\2\2\u00f8"+
		"8\3\2\2\2\u00f9\u00fa\7f\2\2\u00fa\u00fb\7g\2\2\u00fb\u00fc\7h\2\2\u00fc"+
		"\u00fd\7c\2\2\u00fd\u00fe\7w\2\2\u00fe\u00ff\7n\2\2\u00ff\u0100\7v\2\2"+
		"\u0100:\3\2\2\2\u0101\u0102\7.\2\2\u0102<\3\2\2\2\u0103\u0104\7q\2\2\u0104"+
		"\u0105\7h\2\2\u0105>\3\2\2\2\u0106\u0107\7/\2\2\u0107@\3\2\2\2\u0108\u0109"+
		"\7y\2\2\u0109\u010a\7j\2\2\u010a\u010b\7k\2\2\u010b\u010c\7n\2\2\u010c"+
		"\u010d\7g\2\2\u010dB\3\2\2\2\u010e\u010f\7*\2\2\u010fD\3\2\2\2\u0110\u0111"+
		"\7<\2\2\u0111F\3\2\2\2\u0112\u0113\7k\2\2\u0113\u0114\7h\2\2\u0114H\3"+
		"\2\2\2\u0115\u0116\7k\2\2\u0116\u0117\7p\2\2\u0117\u0118\7v\2\2\u0118"+
		"J\3\2\2\2\u0119\u011a\7d\2\2\u011a\u011b\7t\2\2\u011b\u011c\7g\2\2\u011c"+
		"\u011d\7c\2\2\u011d\u011e\7m\2\2\u011eL\3\2\2\2\u011f\u0120\7Q\2\2\u0120"+
		"\u0121\7T\2\2\u0121N\3\2\2\2\u0122\u0123\7g\2\2\u0123\u0124\7n\2\2\u0124"+
		"\u0125\7u\2\2\u0125\u0126\7g\2\2\u0126P\3\2\2\2\u0127\u0128\7c\2\2\u0128"+
		"\u0129\7f\2\2\u0129\u012a\7f\2\2\u012aR\3\2\2\2\u012b\u012c\7J\2\2\u012c"+
		"\u012d\7K\2\2\u012d\u012e\7I\2\2\u012e\u012f\7J\2\2\u012fT\3\2\2\2\u0130"+
		"\u0131\7v\2\2\u0131\u0132\7t\2\2\u0132\u0133\7w\2\2\u0133\u0134\7g\2\2"+
		"\u0134V\3\2\2\2\u0135\u0136\7k\2\2\u0136\u0137\7u\2\2\u0137X\3\2\2\2\u0138"+
		"\u0139\7h\2\2\u0139\u013a\7w\2\2\u013a\u013b\7p\2\2\u013b\u013c\7e\2\2"+
		"\u013c\u013d\7v\2\2\u013d\u013e\7k\2\2\u013e\u013f\7q\2\2\u013f\u0140"+
		"\7p\2\2\u0140Z\3\2\2\2\u0141\u0142\7\60\2\2\u0142\\\3\2\2\2\u0143\u0144"+
		"\7K\2\2\u0144\u0145\7P\2\2\u0145\u0146\7R\2\2\u0146\u0147\7W\2\2\u0147"+
		"\u0148\7V\2\2\u0148^\3\2\2\2\u0149\u014a\7u\2\2\u014a\u014b\7v\2\2\u014b"+
		"\u014c\7g\2\2\u014c\u014d\7r\2\2\u014d`\3\2\2\2\u014e\u014f\7-\2\2\u014f"+
		"b\3\2\2\2\u0150\u0151\7t\2\2\u0151\u0152\7g\2\2\u0152\u0153\7v\2\2\u0153"+
		"\u0154\7w\2\2\u0154\u0155\7t\2\2\u0155\u0156\7p\2\2\u0156d\3\2\2\2\u0157"+
		"\u0158\7=\2\2\u0158f\3\2\2\2\u0159\u015a\7e\2\2\u015a\u015b\7q\2\2\u015b"+
		"\u015c\7p\2\2\u015c\u015d\7v\2\2\u015d\u015e\7c\2\2\u015e\u015f\7k\2\2"+
		"\u015f\u0160\7p\2\2\u0160\u0161\7g\2\2\u0161\u0162\7t\2\2\u0162h\3\2\2"+
		"\2\u0163\u0164\7Q\2\2\u0164\u0165\7W\2\2\u0165\u0166\7V\2\2\u0166\u0167"+
		"\7R\2\2\u0167\u0168\7W\2\2\u0168\u0169\7V\2\2\u0169j\3\2\2\2\u016a\u016b"+
		"\7@\2\2\u016bl\3\2\2\2\u016c\u016d\7u\2\2\u016d\u016e\7v\2\2\u016e\u016f"+
		"\7t\2\2\u016f\u0170\7k\2\2\u0170\u0171\7p\2\2\u0171\u0172\7i\2\2\u0172"+
		"n\3\2\2\2\u0173\u0174\7u\2\2\u0174\u0175\7y\2\2\u0175\u0176\7k\2\2\u0176"+
		"\u0177\7v\2\2\u0177\u0178\7e\2\2\u0178\u0179\7j\2\2\u0179p\3\2\2\2\u017a"+
		"\u017b\7\61\2\2\u017br\3\2\2\2\u017c\u017d\7@\2\2\u017d\u017e\7?\2\2\u017e"+
		"t\3\2\2\2\u017f\u0180\7t\2\2\u0180\u0181\7g\2\2\u0181\u0182\7o\2\2\u0182"+
		"\u0183\7q\2\2\u0183\u0184\7x\2\2\u0184\u0185\7g\2\2\u0185v\3\2\2\2\u0186"+
		"\u0187\7g\2\2\u0187\u0188\7p\2\2\u0188\u0189\7f\2\2\u0189x\3\2\2\2\u018a"+
		"\u018e\7$\2\2\u018b\u018d\13\2\2\2\u018c\u018b\3\2\2\2\u018d\u0190\3\2"+
		"\2\2\u018e\u018f\3\2\2\2\u018e\u018c\3\2\2\2\u018f\u0191\3\2\2\2\u0190"+
		"\u018e\3\2\2\2\u0191\u0192\7$\2\2\u0192z\3\2\2\2\u0193\u0195\t\2\2\2\u0194"+
		"\u0193\3\2\2\2\u0195\u0196\3\2\2\2\u0196\u0194\3\2\2\2\u0196\u0197\3\2"+
		"\2\2\u0197|\3\2\2\2\u0198\u019a\t\3\2\2\u0199\u0198\3\2\2\2\u019a\u019b"+
		"\3\2\2\2\u019b\u0199\3\2\2\2\u019b\u019c\3\2\2\2\u019c~\3\2\2\2\u019d"+
		"\u01a1\t\4\2\2\u019e\u01a0\t\5\2\2\u019f\u019e\3\2\2\2\u01a0\u01a3\3\2"+
		"\2\2\u01a1\u019f\3\2\2\2\u01a1\u01a2\3\2\2\2\u01a2\u0080\3\2\2\2\u01a3"+
		"\u01a1\3\2\2\2\u01a4\u01a5\7\61\2\2\u01a5\u01a9\7,\2\2\u01a6\u01a8\13"+
		"\2\2\2\u01a7\u01a6\3\2\2\2\u01a8\u01ab\3\2\2\2\u01a9\u01aa\3\2\2\2\u01a9"+
		"\u01a7\3\2\2\2\u01aa\u01ac\3\2\2\2\u01ab\u01a9\3\2\2\2\u01ac\u01ad\7,"+
		"\2\2\u01ad\u01ae\7\61\2\2\u01ae\u01af\3\2\2\2\u01af\u01b0\bA\2\2\u01b0"+
		"\u0082\3\2\2\2\u01b1\u01b3\t\6\2\2\u01b2\u01b1\3\2\2\2\u01b3\u01b4\3\2"+
		"\2\2\u01b4\u01b2\3\2\2\2\u01b4\u01b5\3\2\2\2\u01b5\u01b6\3\2\2\2\u01b6"+
		"\u01b7\bB\3\2\u01b7\u0084\3\2\2\2\u01b8\u01b9\7\"\2\2\u01b9\u01ba\3\2"+
		"\2\2\u01ba\u01bb\bC\4\2\u01bb\u0086\3\2\2\2\t\2\u018e\u0196\u019b\u01a1"+
		"\u01a9\u01b4";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}