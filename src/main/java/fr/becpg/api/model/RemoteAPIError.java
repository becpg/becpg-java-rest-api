package fr.becpg.api.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>RemoteAPIError class.</p>
 *
 * @author matthieu
 * @version $Id: $Id
 */
@JsonInclude(Include.NON_NULL)
public class RemoteAPIError {

	@JsonProperty("exception")
	private String exception;

	@JsonProperty("message")
	private String message;

	@JsonProperty("status")
	private RemoteAPIErrorStatus status;

	public class RemoteAPIErrorStatus {

		@JsonProperty("code")
		String code;

		@JsonProperty("name")
		String name;

		@JsonProperty("description")
		String description;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = (prime * result) + getEnclosingInstance().hashCode();
			result = (prime * result) + Objects.hash(code, description, name);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if ((obj == null) || (getClass() != obj.getClass())) {
				return false;
			}
			RemoteAPIErrorStatus other = (RemoteAPIErrorStatus) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance())) {
				return false;
			}
			return Objects.equals(code, other.code) && Objects.equals(description, other.description) && Objects.equals(name, other.name);
		}

		private RemoteAPIError getEnclosingInstance() {
			return RemoteAPIError.this;
		}

	}

	/**
	 * <p>Getter for the field <code>exception</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getException() {
		return exception;
	}

	/**
	 * <p>Setter for the field <code>exception</code>.</p>
	 *
	 * @param exception a {@link java.lang.String} object
	 */
	public void setException(String exception) {
		this.exception = exception;
	}

	/**
	 * <p>Getter for the field <code>message</code>.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * <p>Setter for the field <code>message</code>.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * <p>Getter for the field <code>status</code>.</p>
	 *
	 * @return a {@link fr.becpg.api.model.RemoteAPIError.RemoteAPIErrorStatus} object
	 */
	public RemoteAPIErrorStatus getStatus() {
		return status;
	}

	/**
	 * <p>Setter for the field <code>status</code>.</p>
	 *
	 * @param status a {@link fr.becpg.api.model.RemoteAPIError.RemoteAPIErrorStatus} object
	 */
	public void setStatus(RemoteAPIErrorStatus status) {
		this.status = status;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return Objects.hash(exception, message, status);
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		RemoteAPIError other = (RemoteAPIError) obj;
		return Objects.equals(exception, other.exception) && Objects.equals(message, other.message) && Objects.equals(status, other.status);
	}

	/**
	 * <p>toErrorString.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	public String toErrorString() {
		StringBuilder ret = new StringBuilder();
		if(status!=null) {
			ret.append(status.code);
			ret.append(" - ");
			if(message==null || message.isBlank()) {
				ret.append(status.description);
			}
		}
		
		if(message!=null) {
			ret.append(message);
		}
		
		return ret.toString();
	}
	
	

}
