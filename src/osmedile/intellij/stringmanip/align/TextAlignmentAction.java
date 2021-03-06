package osmedile.intellij.stringmanip.align;

import com.intellij.openapi.editor.Editor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import osmedile.intellij.stringmanip.MultiCaretHandlerHandler;
import osmedile.intellij.stringmanip.MyEditorAction;

import java.util.ArrayList;
import java.util.List;

public abstract class TextAlignmentAction extends MyEditorAction {

	protected TextAlignmentAction(Alignment alignment) {
		this(true, alignment);
	}

	protected TextAlignmentAction(boolean setupHandler, final Alignment alignment) {
		super(null);
		if (setupHandler) {
			this.setupHandler(new MultiCaretHandlerHandler<Void>(getActionClass()) {

				@Override
				protected String processSingleSelection(Editor editor, String text, Void additionalParameter) {
					return alignment.alignLines(text);
				}

				@Override
				protected List<String> processMultiSelections(Editor editor, List<String> lines, Void additionalParameter) {
					return alignLines(lines, alignment);
				}

			});
		}
	}

	private List<String> alignLines(List<String> lines, Alignment alignment) {
		ArrayList<String> strings = new ArrayList<String>();
		for (String line : lines) {
			strings.add(alignment.alignLines(line));
		}
		return strings;
	}

	public enum Alignment {
		LEFT() {
			@Override
			@NotNull
			protected String align(String s) {
				return ltrim(s);
			}
		},
		RIGHT {
			@Override
			@NotNull
			protected String align(String s) {
				return rtrim(s);
			}
		},
		CENTER {
			@Override
			@NotNull
			protected String align(String s) {
				return center(s);
			}

		};

		public String alignLines(String text) {
			String[] split = text.split("\n");
			for (int i = 0; i < split.length; i++) {
				String s = split[i];
				split[i] = align(s);
			}
			String join = StringUtils.join(split, '\n');
			if (text.endsWith("\n")) {
				join = join + "\n";
			}
			return join;
		}

		protected abstract String align(String s);

	}

	private static String ltrim(String s) {
		int i = 0;
		while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
			i++;
		}
		return s.substring(i) + s.substring(0, i);
	}

	private static String rtrim(String s) {
		int i = s.length() - 1;
		while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
			i--;
		}
		return s.substring(i + 1, s.length()) + s.substring(0, i + 1);
	}

	private static String center(String s) {
		return org.apache.commons.lang3.StringUtils.center(s.trim(), s.length());
	}
}
