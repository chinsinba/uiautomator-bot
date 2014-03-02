package in.BBAT.presenter.wizards.pages;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * <h3>{@link JavaElementValidator}</h3> ...
 * 
 * @author Andreas Hoegger
 * @since 3.8.0 11.03.2012
 */
public final class JavaElementValidator {

	private static Set<String> javaKeyWords = null;
	private final static Object LOCK = new Object();

	private final static Pattern REGEX_PACKAGE_NAME = Pattern.compile("^[0-9a-zA-Z\\.\\_]*$");
	private final static Pattern REGEX_PACKAGE_NAME_START = Pattern.compile("[a-zA-Z]{1}.*$");
	private final static Pattern REGEX_PACKAGE_NAME_END = Pattern.compile("^.*[a-zA-Z]{1}$");
	private final static Pattern REGEX_CONTAINS_UPPER_CASE = Pattern.compile(".*[A-Z].*");

	private JavaElementValidator() {
	}

	public static Set<String> getJavaKeyWords() {
		if (javaKeyWords == null) {
			synchronized (LOCK) {
				if (javaKeyWords == null) {
					String[] keyWords = new String[]{"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum",
							"extends", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private", "protected",
							"public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while", "false", "null", "true"};
					HashSet<String> tmp = new HashSet<String>(keyWords.length);
					for (String s : keyWords) {
						tmp.add(s);
					}
					javaKeyWords = Collections.unmodifiableSet(tmp);
				}
			}
		}
		return javaKeyWords;
	}

	public static boolean validatePackageName(String pckName) {
		// no double points
		if (pckName.contains("..")) {
			return false;
		}
		// invalid characters
		if (!REGEX_PACKAGE_NAME.matcher(pckName).matches()) {
			return false;
		}
		// no start and end with number or special characters
		if (!REGEX_PACKAGE_NAME_START.matcher(pckName).matches() || !REGEX_PACKAGE_NAME_END.matcher(pckName).matches()) {
			return false;
		}
		// reserved java keywords
		String jkw = getContainingJavaKeyWord(pckName);
		if (jkw != null) {
			return false;
		}

		return true;
	}

	private static String getContainingJavaKeyWord(String s) {
		for (String keyWord : getJavaKeyWords()) {
			if (s.startsWith(keyWord + ".") || s.endsWith("." + keyWord) || s.contains("." + keyWord + ".")) {
				return keyWord;
			}
		}
		return null;
	}

	public static boolean isReserverWord(String s){

		for (String keyWord : getJavaKeyWords()) {
			if ( s.equals( keyWord )) {
				return true;
			}
		}
		return false;

	}

}
