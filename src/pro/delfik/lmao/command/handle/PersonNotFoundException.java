package pro.delfik.lmao.command.handle;

public class PersonNotFoundException extends Exception {
	public final String person;
	
	public PersonNotFoundException(String person) {
		super(person);
		this.person = person;
	}
}
