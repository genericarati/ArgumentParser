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
		}
	}

	protected void parseSchema() {

	}

	public boolean getBoolean(char schemaChar) {
		if (this.booleanArgs.containsKey(schemaChar)) {
			return this.booleanArgs.get(schemaChar);
		}
		return false;
	}
}
