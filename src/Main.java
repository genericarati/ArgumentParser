
public class Main {

	public static void main(String[] args) {
		
		Args args1 = new Args("l,p#", new String[] {"-l"});
		System.out.println(args1.getBoolean('l'));
	}

}
