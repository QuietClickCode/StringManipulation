package com.github.krasa.stringmanipulation.intellij.swap;

import com.github.krasa.stringmanipulation.intellij.AbstractStringManipAction;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;

public class SwapQuoteAction extends AbstractStringManipAction {

	@Override
	protected String transformSelection(Editor editor, DataContext dataContext, String s, Object additionalParam) {
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char aChar = chars[i];
			if (aChar == '\'') {
				chars[i] = '\"';
			} else if (aChar == '\"') {
				chars[i] = '\'';
			}
		}
		return String.valueOf(chars);
	}

	@Override
	public String transformByLine(String s) {
		throw new UnsupportedOperationException();
	}

}