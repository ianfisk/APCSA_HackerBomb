package hacker.harder_bomb;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public final class HarderHackerBomb {
	public HarderHackerBomb() throws Exception {
		md = MessageDigest.getInstance("SHA-512");
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
	}

	public void defuse() throws Exception {
		String passwordAttempt1;
		String passwordAttempt2;

		try (Timer t = new Timer()) {
			computeSecretOne();
			computeSecretTwo();
		}

		try (Scanner passwordScanner = new Scanner(System.in).useDelimiter("\n|\r\n")) {
			System.out.print("Password 1: ");
			passwordAttempt1 = passwordScanner.next();
			System.out.print("Password 2: ");
			passwordAttempt2 = passwordScanner.next();
		}

		try (Timer t = new Timer()) {
			checkFirstPassword(passwordAttempt1);
			checkSecondPassword(passwordAttempt2);
		}

		System.out.println("Nice job getting past that, but try getting past this...");
		triggerHiddenTrap();

		printLnColor("Success! You defused the bomb!");
	}

	private void computeSecretOne() {
		Random randomNumGenerator = new Random();
		int secretNumber = 0;
		for (int i = 0; i < 1000; i++) {
			secretNumber += (randomNumGenerator.nextInt(10) * 87) % 10000;
		}

		secretOneHash = hash(Integer.toString(secretNumber));
	}

	private void computeSecretTwo() {
		String uuid = UUID.randomUUID().toString();
		secretTwoHash = hash(uuid);
	}

	private void triggerHiddenTrap() throws Exception {
		final Box<Integer> pausePoint = new Box<Integer>();
		try (MinimumTimer t = new MinimumTimer(1000 * 10, () -> {
			if (pausePoint.value != 500) {
				throw new WrongPasswordError("You didn't pause at the right time!");
			}
		})) {

			long lastPauseMs = java.lang.System.currentTimeMillis();
			long maxPausedTime = -1;

			for (int i = 0; i < 1000; i++) {
				long currentMs = java.lang.System.currentTimeMillis();
				long pauseMs = currentMs - lastPauseMs;
				if (pauseMs > maxPausedTime) {
					maxPausedTime = pauseMs;
					pausePoint.value = i;
				}

				lastPauseMs = currentMs;
			}
		}
	}

	private void checkFirstPassword(String attempt) {
		if (!Arrays.equals(hash(attempt), secretOneHash)) {
			throw new WrongPasswordError("First password is WRONG");
		}
	}

	private void checkSecondPassword(String attempt) {
		if (!Arrays.equals(hash(attempt), secretTwoHash)) {
			throw new WrongPasswordError("Second password is WRONG");
		}
	}

	private static void printLnColor(String x) {
		System.out.println(ConsoleColors.GREEN_BACKGROUND + ConsoleColors.WHITE_BOLD + x + ConsoleColors.RESET);
	}

	private byte[] hash(String password) {
		md.reset();
		md.update(salt);
		return md.digest(password.getBytes(StandardCharsets.UTF_8));
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

	private static class Timer implements AutoCloseable {
		protected final long startMs = java.lang.System.currentTimeMillis();
		protected final int maxMsDelta;

		public Timer() {
			this(100);
		}

		public Timer(int maxMsDelta) {
			this.maxMsDelta = maxMsDelta;
		}

		@Override
		public void close() throws Exception {
			if (java.lang.System.currentTimeMillis() - startMs >= maxMsDelta) {
				throw new WrongPasswordError("You think you can pause this program at this line in the debugger?! WRONG");
			}
		}
	}

	private static final class MinimumTimer extends Timer {
		private OnClose onClose;

		// Loosely inspired by
		// https://faithlife.codes/blog/2008/08/leverage_using_blocks_with_scope/
		public MinimumTimer(int maxMsDelta, OnClose onClose) {
			super(maxMsDelta);
			this.onClose = onClose;
		}

		@Override
		public void close() throws Exception {
			if (java.lang.System.currentTimeMillis() - startMs < maxMsDelta) {
				throw new WrongPasswordError(
						"You actually do have to pause this program in the debugger at some point for 10s...");
			}

			onClose.onClose();
		}
	}

	private interface OnClose {
		void onClose() throws Exception;
	}

	private static class Box<T> {
		public T value;
	}

	private byte[] salt = new byte[16];
	private MessageDigest md;
	private byte[] secretOneHash;
	private byte[] secretTwoHash;
}
