package pro.delfik.lmao.command.handle;

public class NotEnoughArgumentsException extends Exception {
	
	public final String usage;
	
	public NotEnoughArgumentsException(String usage) {
		super(usage);
		this.usage = usage;
	}
}
