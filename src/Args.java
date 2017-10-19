import java.util.HashMap;
import java.util.Map;

public class Args {
	private Map<Character, Boolean> booleanArgs;
	private String[] arguments;
	private String schema;

	public Args(String schema, String[] arguments) throws ArgsException {
		this.arguments = arguments;
		this.schema = schema;
		booleanArgs = new HashMap<>();
		parse();
	}

	protected boolean isValidSchema() {
		if (this.schema == null || this.schema.isEmpty())
			return false;
		else
			return true;
	}

	protected boolean isValidArguments() {
		if (this.arguments == null || this.arguments.length == 0)
			return false;
		else
			return true;
	}

	public void parse() throws ArgsException {
		if (isValidSchema()) {
			parseSchema();
		} else {
			throw new ArgsException("Schema is not valid");
		}

		if (isValidArguments()) {
			parseArguments();
		} else {
			throw new ArgsException("Arguments are not valid");
		}
	}

	private void parseArguments() {
		for (int i = 0; i < arguments.length; i++) {
			if (booleanArgs.containsKey(arguments[i].charAt(1))) {
				booleanArgs.put(arguments[i].charAt(1), true);
			}
		}
	}

	protected void parseSchema() {
		String[] schemaStrings = schema.split(",");
		for (int i = 0; i < schemaStrings.length; i++) {
			String s = schemaStrings[i];
			if (s.length() == 1)
				booleanArgs.put(s.charAt(0), false);
		}
	}

	protected boolean falseIfNull(char schemaChar) {
		if (booleanArgs.containsKey(schemaChar))
			return true;
		else
			return false;
	}

	protected boolean getBoolean(char schemaChar) {
		if (falseIfNull(schemaChar))
			return this.booleanArgs.get(schemaChar);
		else
			return false;
	}

	protected class ArgsException extends Exception {

		public ArgsException(String errorMessage) {
			super(errorMessage);
		}

	}
}
