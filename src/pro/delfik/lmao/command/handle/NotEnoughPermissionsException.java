package pro.delfik.lmao.command.handle;

import pro.delfik.util.Rank;

public class NotEnoughPermissionsException extends RuntimeException {
	
	public final Rank required;
	
	public NotEnoughPermissionsException(Rank required) {
		super(required.toString());
		this.required = required;
	}
	
}
