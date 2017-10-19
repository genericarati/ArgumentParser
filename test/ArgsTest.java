import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ArgsTest {

	@Test
	public void booleanArgsMapShouldContainTrueForBooleanScema() {
		Args subject = new Args("l", new String[] { "-l" });
		assertEquals(true, subject.falseIfNull('l'));
		assertEquals(true, subject.getBoolean('l'));
		assertEquals(false, subject.getBoolean('p'));
		assertEquals(false, subject.falseIfNull('p'));
	}

	@Test
	public void booleanArgsMapShouldNotContainStringScemaChars() {
		Args subject = new Args("l,p#", new String[] { "-l" });
		subject.parseSchema();
		assertEquals(true, subject.falseIfNull('l'));
		assertEquals(false, subject.getBoolean('l'));
		assertEquals(false, subject.getBoolean('p'));
		assertEquals(false, subject.falseIfNull('p'));
	}

	@Test
	public void booleanArgsMapShouldContainDefaultValue() {
		Args subject = new Args("l", new String[] { "-l" });
		subject.parseSchema();
		assertEquals(true, subject.falseIfNull('l'));
		assertEquals(false, subject.getBoolean('l'));
	}

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@Test
	public void returnFalseIfArgumentsAreEmpty() {
		String[] args = {};
		Args subject = new Args(null, args);
		assertFalse(subject.isValidArguments());
	}

	@Test
	public void returnFalseIfArgumentsAreNull() {
		String[] args = null;
		Args subject = new Args(null, args);
		assertFalse(subject.isValidArguments());
	}

	@Test
	public void returnFalseIfSchemaIsNull() {
		String[] args = { "-l" };
		Args subject = new Args(null, args);
		assertFalse(subject.isValidSchema());
	}

	@Test
	public void returnTrueIfSchemaIsValid() {
		String[] args = { "-l" };
		Args subject = new Args("l", args);
		assertTrue(subject.isValidSchema());
	}

	@Test
	public void returnFalseIfSchemaIsBlank() {
		String[] args = { "-l" };
		Args subject = new Args("", args);
		assertFalse(subject.isValidSchema());
	}

}
