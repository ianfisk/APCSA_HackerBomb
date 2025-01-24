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
		String P1 = "QVAgQ1NBIGlzIHNvIGNvb2w=";

		String expectedFirstPassword = new String(Base64.getDecoder().decode(P1));
		String firstPasswordAttempt = passwordScanner.next();
		if (!firstPasswordAttempt.equals(expectedFirstPassword)) {
			throw new WrongPasswordError("First password is WRONG");
		}
	}

	private void checkSecondPassword(Scanner passwordScanner) {
		String P2 = "VGhlIGRhdGUgaXMg";

		String expectedSecondPassword = new String(Base64.getDecoder().decode(P2)) + LocalDate.now();
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

	private final Random random = new Random();
}
