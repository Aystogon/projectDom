package editor;

public class CharProcessor {
	
	private static boolean isInstantiated;
	private static boolean isShifted;
	private static String regexNumbersMatch;
	private static String regexLettersMatch;
	
	private CharProcessor() {  /* Deny object creation. */ }
	
	/**
	 * Ensure member variables are enstiated.
	 */
	private static void EnsureInstance() {
		if (!isInstantiated) {
			isShifted = false;
			regexNumbersMatch = ".*[0-9].*";
			regexLettersMatch = ".*[a-zA-Z].*";
			isInstantiated = true;
		}
	}
	
	public static void ProcessCharStringRep(String charRep, StringBuilder token) {
		EnsureInstance();
		
		if (isShifted && isLetterOrDigit(charRep)) {
			token.append(Character.toUpperCase(charRep.charAt(0)));
			isShifted = false;
		} else if (charRep.equals("backspace") && token.length() > 0) {
			token.deleteCharAt(token.length() - 1);
		} else if (charRep.equals("minus")) {
			token.append('-');
		} else if (charRep.equals("period")) {
			token.append('.');
		} else if (charRep.equals("shift")) {
			isShifted = true;
		} else if (isLetterOrDigit(charRep)) {
			token.append(charRep);
		} else {
			token.append(charRep);
		}
	}
	/**
	 * Determines if the given string represents either a letter or a digit.
	 * @param charName name of the supposed letter or digit.
	 * @return true if the given string represents a letter or a digit.
	 */
	private static boolean isLetterOrDigit(String charName) {
		if (charName.matches(regexLettersMatch) || charName.matches(regexNumbersMatch)) {
			return true;
		} else {
			return false;
		}
	}
}
