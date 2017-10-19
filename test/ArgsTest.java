import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ArgsTest {

	@Test
	public void booleanArgsMapShouldContainDefaultValue(){
		Map<Character, Boolean> map = new HashMap<>();
		Args subject = new Args(new String[]{"l"},"l,p#");
		subject.parse();
		assertEquals(false, subject.getBoolean('l'));
		
	}
	@Test
	public void returnFalseIfArgumentsAreEmpty() {
		String[] args = {};
		Args subject = new Args(args, null);
		assertFalse(subject.isValidArguments());
	}

	@Test
	public void returnFalseIfArgumentsAreNull() {
		String[] args = null;
		Args subject = new Args(args, null);
		assertFalse(subject.isValidArguments());
	}

	@Test
	public void returnFalseIfSchemaIsNull() {
		String[] args = {"l"};
		Args subject = new Args(args, null);
		assertFalse(subject.isValidSchema());
	}

	@Test
	public void returnTrueIfSchemaIsValid() {
		String[] args = {"l"};
		Args subject = new Args(args, "l");
		assertTrue(subject.isValidSchema());
	}

	@Test
	public void returnFalseIfSchemaIsBlank() {
		String[] args = {"l"};
		Args subject = new Args(args, "");
		assertFalse(subject.isValidSchema());
	}

}
