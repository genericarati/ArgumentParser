import java.util.HashMap;
import java.util.Map;

public class Args {
	private String[] arguments;
	private String schema;
	private Map<Character, ArgumentMarshaler> booleanArgs;
	private Map<Character, ArgumentMarshaler> stringArgs;
	private Map<Character, Integer> intArgs;

	public Args(String schema, String[] arguments) throws ArgsException {
		this.arguments = arguments;
		this.schema = schema;
		booleanArgs = new HashMap<>();
		intArgs = new HashMap<>();
		stringArgs = new HashMap<>();
		parse();
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

	protected boolean isValidSchema() {
		if (this.schema == null || this.schema.isEmpty())
			return false;
		else
			return true;
	}

	protected void parseSchema() throws ArgsException {
		String[] schemaStrings = schema.split(",");
		for (int i = 0; i < schemaStrings.length; i++) {
			String s = schemaStrings[i];
			if (s.length() == 1)
				setBooleanArgs(s.charAt(0), false);
			else if (s.charAt(1) == ('#')) {
				intArgs.put(s.charAt(0), 0);
			} else {
				setStringArgs(s.charAt(0), "");
			}
		}
	}
	
	protected boolean isValidArguments() {
		if (this.arguments == null || this.arguments.length == 0)
			return false;
		else
			return true;
	}


	private void parseArguments() throws ArgsException {
		for (int i = 0; i < arguments.length; i++) {
			char schemaChar = arguments[i].charAt(1);
			if (booleanArgs.containsKey(schemaChar)) {
				setBooleanArgs(schemaChar, true);
			} else if (intArgs.containsKey(schemaChar)) {
				setIntArgs(schemaChar);
			} else if (stringArgs.containsKey(schemaChar)) {
				setStringArgs(schemaChar, arguments[i]);
			} else {
				throw new ArgsException("Arguments don't match as per schema");
			}
		}
	}

	private void setStringArgs(char schemaChar, String value) {
		ArgumentMarshaler stringArgumentMarshaler = new StringArgumentMarshaler();
		stringArgumentMarshaler.setStringValue(value);
		stringArgs.put(schemaChar, stringArgumentMarshaler);
	}

	private void setBooleanArgs(char schemaChar, boolean value) {
		ArgumentMarshaler booleanArgumentMarshaler = new BooleanArgumentMarshaler();
		booleanArgumentMarshaler.setBooleanValue(value);
		booleanArgs.put(schemaChar, booleanArgumentMarshaler);
	}

	private void setIntArgs(char schemaChar) throws ArgsException {
		try {
			intArgs.put(schemaChar, Integer.parseInt(String.valueOf(schemaChar)));
		} catch (NumberFormatException e) {
			throw new ArgsException("Argument could not be parsed to integer.");
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
			return this.booleanArgs.get(schemaChar).getBooleanValue();
		else
			return false;
	}

	public String getString(char schemaChar) {
		return this.stringArgs.get(schemaChar).getStringValue();
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

	protected class ArgumentMarshaler {
		private boolean booleanValue = false;
		private String stringValue = "";

		public boolean getBooleanValue() {
			return booleanValue;
		}

		
		public String getStringValue() {
			return stringValue;
		}


		public void setStringValue(String stringValue) {
			this.stringValue = stringValue;
		}

		public void setBooleanValue(boolean booleanValue) {
			this.booleanValue = booleanValue;
		}
	}

	protected class BooleanArgumentMarshaler extends ArgumentMarshaler {
	}

	private class IntegerArgumentMarshaler extends ArgumentMarshaler {
	}

	private class StringArgumentMarshaler extends ArgumentMarshaler {
	}

}
