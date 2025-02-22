package hacker.harder_bomb;

public final class Program {
	public static void main(String[] args) throws Exception {
		HarderHackerBomb bomb = new HarderHackerBomb();

		// Use the Eclipse Debugger to defuse the virus! Enter the right passwords, find
		// the hidden traps, and you'll be safe...
		// NOTE: You CANNOT just use simple breakpoints to defuse this one. You also
		// cannot pause the program in the debugger or else the bomb will explode!
		//
		// You should not:
		// ❌ Manipulate variable values in the variables pane (tricky anyways because
		// you can't pause the program at a breakpoint).
		// ❌ Modify any of the code.
		bomb.defuse();
	}
}
