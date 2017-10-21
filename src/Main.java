
public class Main {

	public static void main(String[] args) {

		Args args1;
		try {
			args1 = new Args("l,p#", new String[] { "-l","-p" });
			System.out.println(args1.getBoolean('l'));
			System.out.println(args1.getString('p'));
		} catch (Args.ArgsException e) {
			System.out.println(e.getMessage());
		}
	}

}
