package com.googlecode.ochagl.graphics.xloader;

import com.googlecode.ochagl.graphics.xloader.XFileLoader;

import junit.framework.TestCase;


public class XFileLoaderTest extends TestCase {
	public void test() {
		XFileLoader loader = new XFileLoader();
	 	loader.load("test.x");
         System.out.println("hogege");
	    //assertEquals("01234", sb.toString());
    }
}
