package com.googlecode.ochagl.graphics.xloader;

import java.io.StringReader;

import com.googlecode.ochagl.graphics.xloader.Lexer;
import com.googlecode.ochagl.graphics.xloader.ScriptTokenType;

import junit.framework.TestCase;


public class LexerTest extends TestCase {
	private Lexer lexer_ = new Lexer();

    // シンボル(キーワード以外の文字列)と数値が解析できるかのテスト
    public void test1() {
    	setData("aaa 123 1.12 0303bbb ");
    	
		lexer_.advance();
		assertEquals(ScriptTokenType.SYMBOL, lexer_.getTokenType());
		assertEquals("aaa", lexer_.getValue());

		lexer_.advance();
		assertEquals(ScriptTokenType.INT, lexer_.getTokenType());
		assertEquals(123, lexer_.getInt());

		lexer_.advance();
		assertEquals(ScriptTokenType.FLOAT, lexer_.getTokenType());
		assertEquals(1.12f, lexer_.getFloat());

		lexer_.advance();
		assertEquals(ScriptTokenType.INT, lexer_.getTokenType());
		assertEquals(303, lexer_.getInt());

		lexer_.advance();
		assertEquals(ScriptTokenType.SYMBOL, lexer_.getTokenType());
		assertEquals("bbb", lexer_.getValue());
    }

    // コメントのテスト
    public void test2() {
    	setData("aaa /*123*/ bbb ");
		lexer_.advance();
		assertEquals(ScriptTokenType.SYMBOL, lexer_.getTokenType());
		assertEquals("aaa", lexer_.getValue());

		lexer_.advance();
		assertEquals(ScriptTokenType.SYMBOL, lexer_.getTokenType());
		assertEquals("bbb", lexer_.getValue());

		setData("aaa // asdfg \n bbb");
		lexer_.advance();
		assertEquals(ScriptTokenType.SYMBOL, lexer_.getTokenType());
		assertEquals("aaa", lexer_.getValue());

		lexer_.advance();
		assertEquals(ScriptTokenType.SYMBOL, lexer_.getTokenType());
		assertEquals("bbb", lexer_.getValue());

    }

    private void setData(String s) {
		StringReader reader = new StringReader(s);
		lexer_.setReader(reader);
    }   
}
