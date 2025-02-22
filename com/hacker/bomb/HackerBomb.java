package hacker.bomb;

import java.time.LocalDate;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

public class HackerBomb {

	public void defuse() {
		Scanner passwordScanner = new Scanner(System.in).useDelimiter("\n|\r\n");

		checkRequiredPasswords(passwordScanner);
		checkOptionalPassword(passwordScanner);
		passwordScanner.close();

		System.out.println("Success! You defused the bomb!");
	}

	private void checkRequiredPasswords(Scanner passwordScanner) {
		checkFirstPassword(passwordScanner);
		checkSecondPassword(passwordScanner);
		checkThirdPassword(passwordScanner);
	}

	private void checkFirstPassword(Scanner passwordScanner) {
		String password1 = "QVAgQ1NBIGlzIHNvIGNvb2w=";

		String expectedFirstPassword = new String(Base64.getDecoder().decode(password1));
		String firstPasswordAttempt = passwordScanner.next();
		if (!firstPasswordAttempt.equals(expectedFirstPassword)) {
			throw new WrongPasswordError("First password is WRONG");
		}

		printLnColor("You entered password one correctly!");
	}

	private void checkSecondPassword(Scanner passwordScanner) {
		String password2Prefix = "VGhlIGRhdGUgaXMg";

		String expectedSecondPassword = new String(Base64.getDecoder().decode(password2Prefix)) + LocalDate.now();
		String secondPasswordAttempt = passwordScanner.next();
		if (!secondPasswordAttempt.equals(expectedSecondPassword)) {
			throw new WrongPasswordError("Second password is WRONG");
		}

		printLnColor("You entered password two correctly!");
	}

	private void checkThirdPassword(Scanner passwordScanner) {
		Random randomNumGenerator = new Random();
		int magicNumber = randomNumGenerator.nextInt(100);

		if (!passwordScanner.hasNextInt() || passwordScanner.nextInt() != magicNumber) {
			throw new WrongPasswordError("Third password is WRONG");
		}

		printLnColor("You entered password three correctly!");
	}

	private void checkOptionalPassword(Scanner passwordScanner) {
		System.out.print("Do you want an extra challenge? (Y/N) ");
		String response = passwordScanner.next();

		if (response.toLowerCase().equals("y")) {
			System.out.println("Bypass this...");

			Random randomNumGenerator = new Random();
			int a = randomNumGenerator.nextInt();
			int b = randomNumGenerator.nextInt();
			if (a != b) {
				throw new WrongPasswordError(
						"Fourth (extra-hard) password is WRONG. You didn't use the debugger to move past this if-statement...");
			}
		} else {
			System.out.println("Ok...");
		}
	}

	private static void printLnColor(String x) {
		System.out.println(ConsoleColors.GREEN_BACKGROUND + ConsoleColors.WHITE_BOLD + x + ConsoleColors.RESET);
	}

	static class WrongPasswordError extends Error {
		private static String EXPLOSION_ASCII = "    _.-^^---....,,--\n"
				+ " _--                  --_\n"
				+ "<                        >)\n"
				+ "|                         |\n"
				+ " \\._                   _./\n"
				+ "    ```--. . , ; .--'''\n"
				+ "          | |   |\n"
				+ "       .-=||  | |=-.\n"
				+ "       `-=#$%&%$#=-'\n"
				+ "          | ;  :|\n"
				+ " _____.,-#%&$@%#&#~,._____\n\n";

		// Force a custom message.
		@SuppressWarnings("unused")
		private WrongPasswordError() {
		}

		public WrongPasswordError(String message) {
			super("HACKERBOMB EXPLOOOOOoOOoDES\n\n" + EXPLOSION_ASCII + message);
		}
	}

	private static class ConsoleColors {
		private static final String RESET = "\033[0m";
		private static final String WHITE_BOLD = "\033[1;37m";
		private static final String GREEN_BACKGROUND = "\u001B[42m";
	}
}
