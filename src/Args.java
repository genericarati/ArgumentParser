import java.util.HashMap;
import java.util.Map;

public class Args {
	private String[] arguments;
	private String schema;
	private Map<Character, Boolean> booleanArgs;
	private Map<Character, String> stringArgs;
	private Map<Character, Integer> intArgs;

	public Args(String schema, String[] arguments) throws ArgsException {
		this.arguments = arguments;
		this.schema = schema;
		booleanArgs = new HashMap<>();
		intArgs = new HashMap<>();
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
			char schemaChar = arguments[i].charAt(1);
			if (booleanArgs.containsKey(schemaChar)) {
				setBooleanArgs(schemaChar);
			} else if (intArgs.containsKey(schemaChar)) {
				setIntArgs(schemaChar);
			} else if (stringArgs.containsKey(schemaChar)) {
				setStringArgs(i);
			} else {
				throw new ArgsException("Arguments don't match as per schema");
			}
		}
	}

	private void setStringArgs(int i) {
		stringArgs.put(arguments[i].charAt(1), arguments[i]);
	}

	private void setBooleanArgs(char schemaChar) {
		booleanArgs.put(schemaChar, true);
	}

	private void setIntArgs(char schemaChar) throws ArgsException {
			try{
				intArgs.put(schemaChar, Integer.parseInt(String.valueOf(schemaChar)));
			}catch(NumberFormatException e){
				throw new ArgsException("Argument could not be parsed to integer.");
			}
	}

	protected void parseSchema() throws ArgsException {
		String[] schemaStrings = schema.split(",");
		for (int i = 0; i < schemaStrings.length; i++) {
			String s = schemaStrings[i];
			if (s.length() == 1)
				booleanArgs.put(s.charAt(0), false);
			else if (s.charAt(1) == ('#')) {
				intArgs.put(s.charAt(0), 0);
			} else {
				stringArgs.put(s.charAt(0), "");
			}
		}
	}

	protected boolean zeroIfNull(char schemaChar) {
		if (intArgs != null && intArgs.containsKey(schemaChar))
			return true;
		else
			return false;
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

	public Integer getInteger(char schemaChar) {
		if (!zeroIfNull(schemaChar))
			return this.intArgs.get(schemaChar);
		else
			return 0;
	}

	protected class ArgsException extends Exception {
		public ArgsException(String errorMessage) {
			super(errorMessage);
		}

	}

}
