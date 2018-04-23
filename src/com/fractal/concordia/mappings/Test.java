import java.util.regex.*;
import java.util.regex.Pattern;

public class Test {
	public static void main(String[] args) {

		String tosearchfor = "xx";
		String digit = "([1-9]|[1-8][0-9]|9[0-9]|[1-8][0-9]{2}|9[0-8][0-9]|99[0-9]|[1-8][0-9]{3}|9[0-8][0-9]{2}|99[0-8][0-9]|999[0-9]|10000)";
		String space = "\\s+";

		// String string = "xyz 123 zz 23 abd";

		String string = " 123 xx 23";
		String regex = "([1-9]|[1-8][0-9]|9[0-9]|[1-8][0-9]{2}|9[0-8][0-9]|99[0-9]|[1-8][0-9]{3}|9[0-8][0-9]{2}|99[0-8][0-9]|999[0-9]|10000)"
				+ tosearchfor
				+ "([1-9]|[1-8][0-9]|9[0-9]|[1-8][0-9]{2}|9[0-8][0-9]|99[0-9]|[1-8][0-9]{3}|9[0-8][0-9]{2}|99[0-8][0-9]|999[0-9]|10000)";
		// string = string.replaceAll(" ","" ).toLowerCase();
		string = string.replaceAll(tosearchfor + " ", tosearchfor).replaceAll(" " + tosearchfor, tosearchfor)
				.toLowerCase();
		// string=string.replaceAll("[a-yA-Y]", "");

		Pattern[] pattern = { Pattern.compile(space + digit + tosearchfor.toLowerCase() + digit),
				// Pattern.compile(".*"+digit + tosearchfor.toLowerCase() +
				// digit+".*"),
				Pattern.compile(tosearchfor.toLowerCase() + digit + space + digit),
				Pattern.compile(space + digit + space + digit + tosearchfor.toLowerCase()) };
		for (int reg = 0; reg < 3; reg++) {
			Matcher regexMatcher = pattern[reg].matcher(string);
			if (regexMatcher.matches()) {
				System.out.println(regexMatcher.group(0));
				System.out.println(regexMatcher.group(1));
				System.out.println(regexMatcher.group(2));
			}
		}

		// Pattern pattern1 =
		// Pattern.compile(digit+tosearchfor.toLowerCase()+digit);
		// Matcher regexMatcher1 = pattern1.matcher(string);
		// if (regexMatcher1.matches()) {
		// System.out.println(regexMatcher1.group(0));
		// System.out.println(regexMatcher1.group(1));
		// System.out.println(regexMatcher1.group(2));
		// // m.group(0) is the entire matched item, not the first group.
		// // etc...
		// }

		// while (string.contains(" ")) {
		// string=string.replaceAll(" ", " ");
		// }

		// System.out.println(string.replaceAll(" ", ""));

	}
}
