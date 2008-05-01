/*
 * Created on 2005/11/29
 */
package com.googlecode.ochagl.graphics.xloader;

import java.io.Reader;

/**
 * 字句解析器クラス。与えられた文字列をトークンに分解する。<br> 
 * トークンとは、数値、文字列、キーワード、シンボルを指す。<br>
 * 下請け的なメソッドは全てパッケージプライベート。<br>
 * これは、テストしやすくするため。<br>
 * 同様の理由でLexReaderはすべて引数でとるようにし、 メソッドの独立性を高めている。<br>
 */
class Lexer {

	/** リーダ。 */
	private LexReader reader_;

	/** 現在のトークンの種類。 */
	private int tokenType_;

	/** 現在のトークンの値 */
	private String tokenValue_;
	private int tokenIntValue_;
	private float tokenFloatValue_;

	/**
	 * デフォルトコンストラクタ。
	 */
	Lexer() {
		reader_ = new LexReader();
	}

	/**
	 * コンストラクタ。
	 */
	public Lexer(Reader reader) {
		this();
		setReader(reader);
	}

	/**
	 * リーダを設定する。
	 */
	public void setReader(Reader reader) {
		reader_.setReader(reader);
	}

	/**
	 * 読み込み位置（列）を返す。1オリジン。
	 * @return 列
	 */
	public int getCol() {
		return reader_.getCol();
	}

	/**
	 * 読み込み位置（行）を返す。1オリジン。
	 * @return 行
	 */
	public int getRow() {
		return reader_.getRow();
	}

	/**
	 * 繰り返し可能な初期化
	 */
	public void reset() {
		reader_.reset();
	}

	/**
	 * 現在のトークンを種類を返す。
	 * 
	 * @return トークンの種類
	 */
	public int getTokenType() {
		return tokenType_;
	}

	/**
	 * 現在のトークンの値を取得する。
	 * 
	 * @return 現在のトークンの値
	 */
	public String getValue() {
		return tokenValue_;
	}

	/**
	 * 現在のトークンの値を取得する。
	 * 
	 * @return 現在のトークンの値
	 */
	public int getInt() {
		return tokenIntValue_;
	}

	/**
	 * 現在のトークンの値を取得する。
	 * 
	 * @return 現在のトークンの値
	 */
	public float getFloat() {
		return tokenFloatValue_;
	}

	/**
	 * トークンを取り出し、次のトークンに進む。
	 * 
	 * @return true:トークン取り出し成功 false:トークン取り出し失敗
	 */
	public boolean advance() {
		return advance(reader_);
	}

	/**
	 * トークンを取り出し、次のトークンに進む（下請け）。
	 * 
	 * @param reader リーダ
	 * @return true:トークン取り出し成功 false:トークン取り出し失敗
	 */
	boolean advance(LexReader reader) {
		skipWhitespace(reader);
		int c = reader.read();
		if (c < 0) {
			return false;
		}

		if (isDelimiter(c)) {
			tokenType_ = c;
			return true;
		}

		//System.out.println("c:" + (char)c);
		if (c == '/') {
			tokenType_ = c;
			c = reader.read();
			if (c == '/') {
				skipLineComment(reader);
				return advance();
			} else if (c == '*') {
				skipComment(reader);
				return advance();
			}
			reader.unread();
			return true;
		}

		if (c == '-' || Character.isDigit((char) c)) {
			if (c == '-') {
				skipWhitespace(reader);
			} else {
				reader.unread();
			}
			lexDigit(reader);
			return true;
		}

		reader.unread();
		lexSymbol(reader);
		if (ScriptTokenType.isReserve(tokenValue_)) {
			// 予約語
			tokenType_ = ScriptTokenType.KEYWORD;
		}
		return true;
	}

	/**
	 * 空白を読み飛ばす。
	 * @param reader リーダ
	 */
	void skipWhitespace(LexReader reader) {
		int c = reader.read();
		while ((c != -1) && Character.isWhitespace((char) c)) {
			c = reader.read();
		}
		reader.unread();
	}

	/**
	 * コメントを読み飛ばす。
	 * @param reader リーダ
	 */
	void skipLineComment(LexReader reader) {
		int c = 0;
		while ((c = reader.read()) != '\n') {
			if (c < 0) {
				throw new ScriptException("コメント中にファイルの終端に達しました。");
			}
		}
	}

	/**
	 * 複数行コメントを読み飛ばす。
	 * @param reader リーダ
	 */
	void skipComment(LexReader reader) {
		int c = 0;
		while (true) {
			c = reader.read();
			if (c < 0) {
				throw new ScriptException("コメント中にファイルの終端に達しました。");
			}
			if (c == '*') {
				c = reader.read();
				if (c == '/') {
					break;
				}
			}
		}
	}

	/**
	 * 数字を読み込む。
	 * @param reader リーダ
	 */
	void lexDigit(LexReader reader) {
		StringBuffer buf = new StringBuffer();
		int c = 0;
		while (true) {
			c = reader.read();
			if (c < 0) {
				break;
			}
			if (c != '.' && !Character.isDigit((char) c)) {
				reader.unread();
				break;
			}
			buf.append((char) c);
		}

		String value = buf.toString();
		//System.out.printf("digit-value:%s\n", value);
		try {
			tokenIntValue_ = Integer.parseInt(value);
			tokenType_ = ScriptTokenType.INT;
		} catch (Exception e) {
			try {
				tokenFloatValue_ = Float.parseFloat(value);
				tokenType_ = ScriptTokenType.FLOAT;
			} catch (Exception ex) {
				throw new ScriptException("数値データが不正です。");
			}
		}
	}

	/**
	 * 文字列を読み込む。
	 * @param reader リーダ。
	 */
	void lexString(LexReader reader) {
		StringBuffer buf = new StringBuffer();
		while (true) {
			int c = reader.read();
			if (c < 0) {
				throw new ScriptException("文字列中にファイルの終端に達しました。");
			}
			if (c == '"') {
				break;
			} else if (c == '\\') {
				c = reader.read();
				if (c < 0) {
					throw new ScriptException("文字列中にファイルの終端に達しました。");
				}
			}
			buf.append((char) c);
		}
		tokenType_ = ScriptTokenType.STRING;
		tokenValue_ = buf.toString();
	}

	/**
	 * シンボルを読み込む。
	 * @param reader リーダ
	 */
	void lexSymbol(LexReader reader) {
		StringBuffer buf = new StringBuffer();
		int c = 0;
		while (true) {
			c = reader.read();
			if (c < 0) {
				break;
			}
			if (isDelimiter(c)) {
				reader.unread();
				break;
			}
			buf.append((char) c);
		}
		tokenType_ = ScriptTokenType.SYMBOL;
		tokenValue_ = buf.toString();
	}

	boolean isDelimiter(int c) {
		switch ((char) c) {
		case ';':
		case '{':
		case '}':
		case ',':
		case '.':
		case '\n':
		case ' ':
			return true;
		}
		return false;
	}
}