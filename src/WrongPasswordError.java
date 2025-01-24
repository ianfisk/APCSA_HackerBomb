public class WrongPasswordError extends Error {
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
