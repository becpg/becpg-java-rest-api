package fr.becpg.api.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoteEntity extends RemoteNodeInfo {

	@JsonProperty("bcpg:code")
	String code;
	@JsonProperty("bcpg:erpCode")
	String erpCode;
	@JsonProperty("datalists")
	Map<String,List<RemoteNodeInfo>> datalists;
	@JsonProperty("params")
	Map<String,Object> params;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getErpCode() {
		return erpCode;
	}
	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	public Map<String, List<RemoteNodeInfo>> getDatalists() {
		return datalists;
	}
	public void setDatalists(Map<String, List<RemoteNodeInfo>> datalists) {
		this.datalists = datalists;
	}
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(code, datalists, erpCode, params);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RemoteEntity other = (RemoteEntity) obj;
		return Objects.equals(code, other.code) && Objects.equals(datalists, other.datalists) && Objects.equals(erpCode, other.erpCode)
				&& Objects.equals(params, other.params);
	}
	@Override
	public String toString() {
		return "RemoteEntity [code=" + code + ", erpCode=" + erpCode + ", datalists=" + datalists + ", params=" + params + "]";
	}
	
	
	
}
