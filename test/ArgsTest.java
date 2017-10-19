import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ArgsTest {

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void booleanArgsMapShouldContainTrueForBooleanScema() throws Args.ArgsException {
		Args subject = new Args("l", new String[] { "-l" });
		assertEquals(true, subject.falseIfNull('l'));
		assertEquals(true, subject.getBoolean('l'));
		assertEquals(false, subject.getBoolean('p'));
		assertEquals(false, subject.falseIfNull('p'));
	}

	@Test
	public void booleanArgsMapShouldNotContainStringScemaChars() throws Args.ArgsException {
		Args subject = new Args("l,p#", new String[] { "-l" });
		subject.parseSchema();
		assertEquals(true, subject.falseIfNull('l'));
		assertEquals(false, subject.getBoolean('l'));
		assertEquals(false, subject.getBoolean('p'));
		assertEquals(false, subject.falseIfNull('p'));
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

}
