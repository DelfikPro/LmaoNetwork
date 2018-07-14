package pro.delfik.lmao.command.handle;

import pro.delfik.lmao.permissions.Rank;

public class NotEnoughPermissionsException extends Exception {
	
	public final Rank required;
	
	public NotEnoughPermissionsException(Rank required) {
		super(required.toString());
		this.required = required;
	}
	
}
