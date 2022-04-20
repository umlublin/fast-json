package pl.sollers.json;

import junit.framework.TestCase;
import pl.sollers.json.token.Lexer;
import pl.sollers.json.token.Token;

public class LexerTest extends TestCase {

	public void testYylex() throws Exception {
		String s = "\"\\/\"";
		System.out.println(s);
		Lexer lexer = new Lexer(s.getBytes());
		Token token = lexer.getNextToken();
		assertEquals(Token.TYPE_UNQUOTED, token.type);
		assertEquals("/", token.getValue());
	}

	public void testYylex2() throws Exception {
		String s = "\"abc\\/\\r\\b\\n\\t\\f\\\\\"";
		System.out.println(s);
		Lexer lexer = new Lexer(s.getBytes());
		Token token = lexer.getNextToken();
		assertEquals(Token.TYPE_UNQUOTED, token.type);
		System.out.println("'"+token.getValue()+"'");
		assertEquals("abc/\r\b\n\t\f\\", token.getValue());
	}

	public void testYylex3() throws Exception {
		String s = "[\t \n\r\n{ \t \t\n\r}";
		System.out.println(s);
		Lexer lexer = new Lexer(s.getBytes());
		Token token = lexer.getNextToken();
		assertEquals(Token.LEFT_SQUARE, token.type);
		token = lexer.getNextToken();
		assertEquals(Token.LEFT_BRACE, token.type);
		token = lexer.getNextToken();
		assertEquals(Token.RIGHT_BRACE, token.type);
	}

	public void testComplex() throws Exception {
		String s = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
		System.out.println(s);
		Lexer lexer = new Lexer(s.getBytes());
		Token token = lexer.getNextToken();
		System.out.println(token);
		assertEquals(Token.LEFT_SQUARE, token.type);
		token = lexer.getNextToken();
		System.out.println(token);
		assertEquals(Token.TYPE_UNQUOTED, token.type);
		token = lexer.getNextToken();
		System.out.println(token);
		assertEquals(Token.TYPE_COMMA, token.type);
		token = lexer.getNextToken();
		System.out.println(token);
		assertEquals(Token.LEFT_BRACE, token.type);
	}

}
