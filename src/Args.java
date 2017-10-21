import java.util.HashMap;
import java.util.Map;

public class Args {
	private Map<Character, Boolean> booleanArgs;
	private String[] arguments;
	private String schema;
	private Map<Character, String> stringArgs;

	public Args(String schema, String[] arguments) throws ArgsException {
		this.arguments = arguments;
		this.schema = schema;
		booleanArgs = new HashMap<>();
		stringArgs = new HashMap<>();
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

	private void parseArguments() throws ArgsException {
		for (int i = 0; i < arguments.length; i++) {
			if (booleanArgs.containsKey(arguments[i].charAt(1))) {
				booleanArgs.put(arguments[i].charAt(1), true);
			} else if (stringArgs.containsKey(arguments[i].charAt(1))) {
				stringArgs.put(arguments[i].charAt(1), arguments[i]);
			} else {
				throw new ArgsException("Arguments don't match as per schema");
			}
		}
	}

	protected void parseSchema() throws ArgsException {
		String[] schemaStrings = schema.split(",");
		for (int i = 0; i < schemaStrings.length; i++) {
			String s = schemaStrings[i];
			if (s.length() == 1)
				booleanArgs.put(s.charAt(0), false);
			else
				stringArgs.put(s.charAt(0), "");
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

	public String getString(char schemaChar) {
		return this.stringArgs.get(schemaChar);
	}

	protected class ArgsException extends Exception {
		public ArgsException(String errorMessage) {
			super(errorMessage);
		}

	}

}
