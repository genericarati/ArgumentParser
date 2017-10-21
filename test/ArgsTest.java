import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ArgsTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void booleanArgsMapShouldContainTrueForBooleanScema() throws Args.ArgsException {
		Args subject = new Args("l", new String[] { "-l"});
		assertEquals(true, subject.falseIfNull('l'));
		assertEquals(true, subject.getBoolean('l'));
	}

	@Test
	public void booleanArgsMapShouldNotContainStringScemaChars() throws Args.ArgsException {
		Args subject = new Args("l,p#", new String[] { "-l" });
		assertEquals(false, subject.getBoolean('p'));
	}

	@Test
	public void booleanArgsMapShouldContainDefaultValue() throws Args.ArgsException {
		Args subject = new Args("l", new String[] { "-l" });
		subject.parseSchema();
		assertEquals(true, subject.falseIfNull('l'));
		assertEquals(false, subject.getBoolean('l'));
	}

	@Test
	public void returnFalseIfArgumentsAreEmpty() throws Args.ArgsException {
		String[] args = {};
		expectedEx.expect(Args.ArgsException.class);
		expectedEx.expectMessage("Arguments are not valid");
		new Args("l", args);
	}

	@Test
	public void returnFalseIfArgumentsAreNull() throws Args.ArgsException {
		String[] args = null;
		expectedEx.expect(Args.ArgsException.class);
		expectedEx.expectMessage("Arguments are not valid");
		new Args("l", args);
	}

	@Test
	public void returnFalseIfSchemaIsNull() throws Args.ArgsException {
		String[] args = { "-l" };
		expectedEx.expect(Args.ArgsException.class);
		expectedEx.expectMessage("Schema is not valid");
		new Args(null, args);
	}

	@Test
	public void returnTrueIfSchemaIsValid() throws Args.ArgsException {
		String[] args = { "-l" };
		Args subject = new Args("l", args);
		assertTrue(subject.isValidSchema());
	}

	@Test
	public void returnFalseIfSchemaIsBlank() throws Args.ArgsException {
		String[] args = { "-l" };
		expectedEx.expect(Args.ArgsException.class);
		expectedEx.expectMessage("Schema is not valid");
		new Args("", args);
	}

	@Test
	public void shouldReturnExceptionIfArgumentsDontMatchSchema() throws Args.ArgsException {
		String[] args = { "-l" };
		String schema = "p";
		expectedEx.expect(Args.ArgsException.class);
		expectedEx.expectMessage("Arguments don't match as per schema");
		new Args(schema, args);
	}

	@Test
	public void booleanArgsMapShouldContainArgument() throws Args.ArgsException {
		String[] arguments = { "-l" };
		String schema = "l";
		Args args = new Args(schema, arguments);
		assertEquals(true, args.getBoolean('l'));
	}

	@Test
	public void shouldThrowExceptionWhenIntegerCouldNotBeFormatted() throws Args.ArgsException {
		String[] arguments = { "-l","-p" };
		String schema = "l,p#";
		expectedEx.expect(Args.ArgsException.class);
		expectedEx.expectMessage("Argument could not be parsed to integer");
		new Args(schema, arguments);
	}

//	@Test
//	public void integerArgsMapShouldContainArgument() throws Args.ArgsException {
//		String[] arguments = { "-l","-p" };
//		String schema = "l,p#";
//		Args args = new Args(schema, arguments);
//		assertEquals("-p", args.getString('p'));
//	}

}
