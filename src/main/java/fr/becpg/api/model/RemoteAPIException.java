package fr.becpg.api.model;

/**
 * <p>RemoteAPIException class.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
public class RemoteAPIException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7262998255684115631L;

	private RemoteAPIError error;
	

	/**
	 * <p>Constructor for RemoteAPIException.</p>
	 *
	 * @param error a {@link fr.becpg.api.model.RemoteAPIError} object
	 */
	public RemoteAPIException(RemoteAPIError error) {
		super(error.toErrorString());
		this.error = error;
	}


	/**
	 * <p>Getter for the field <code>error</code>.</p>
	 *
	 * @return a {@link fr.becpg.api.model.RemoteAPIError} object
	 */
	public RemoteAPIError getError() {
		return error;
	}

	

}
