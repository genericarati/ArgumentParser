import java.util.HashMap;
import java.util.Map;

public class Args {
	private Map<Character, Boolean> booleanArgs;
	private String[] arguments;
	private String schema;

	public Args(String[] arguments, String schema) {
		this.arguments = arguments;
		this.schema = schema;
		booleanArgs = new HashMap<>();
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

	public void parse() {
		if (isValidArguments() && isValidSchema()) {
			parseSchema();
			parseArguments();
		}
	}

	private void parseArguments() {
		for (int i=0; i< arguments.length; i++){
			if (booleanArgs.containsKey(arguments[i].charAt(0))){
				booleanArgs.put(arguments[i].charAt(0), true);
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
}
