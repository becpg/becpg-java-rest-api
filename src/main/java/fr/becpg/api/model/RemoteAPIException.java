package fr.becpg.api.model;

public class RemoteAPIException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7262998255684115631L;

	private RemoteAPIError error;
	

	public RemoteAPIException(RemoteAPIError error) {
		super(error.toErrorString());
		this.error = error;
	}


	public RemoteAPIError getError() {
		return error;
	}

	

}
