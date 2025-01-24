import java.time.LocalDate;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

public class HackerVirus {

	public void defuse() {
		Scanner passwordScanner = new Scanner(System.in).useDelimiter("\n");

		checkRequiredPasswords(passwordScanner);
		checkOptionalPassword(passwordScanner);

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
	}

	private void checkSecondPassword(Scanner passwordScanner) {
		String password2Prefix = "VGhlIGRhdGUgaXMg";

		String expectedSecondPassword = new String(Base64.getDecoder().decode(password2Prefix)) + LocalDate.now();
		String secondPasswordAttempt = passwordScanner.next();
		if (!secondPasswordAttempt.equals(expectedSecondPassword)) {
			throw new WrongPasswordError("Second password is WRONG");
		}
	}

	private void checkThirdPassword(Scanner passwordScanner) {
		int magicNumber = random.nextInt(100);

		if (!passwordScanner.hasNextInt() || passwordScanner.nextInt() != magicNumber) {
			throw new WrongPasswordError("Third password is WRONG");
		}
	}

	private void checkOptionalPassword(Scanner passwordScanner) {
		System.out.print("Do you want an extra challenge? (Y/N) ");
		String response = passwordScanner.next();

		if (response.toLowerCase().equals("y")) {
			System.out.println("Bypass this...");

			int a = random.nextInt();
			int b = random.nextInt();
			if (a != b) {
				throw new WrongPasswordError("Fourth (extra-hard) password is WRONG");
			}
		} else {
			System.out.println("Ok...");
		}
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

	private final Random random = new Random();
}
