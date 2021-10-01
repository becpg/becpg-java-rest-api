package fr.becpg.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class RemoteEntity extends RemoteNodeInfo {

	@JsonProperty("datalists")
	Map<String,List<RemoteNodeInfo>> datalists;
	@JsonProperty("params")
	Map<String,Object> params;

	@Nullable
	public Map<String, List<RemoteNodeInfo>> getDatalists() {
		return datalists;
	}
	
	public void setDatalists(Map<String, List<RemoteNodeInfo>> datalists) {
		this.datalists = datalists;
	}
	
	@Nullable
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	
	@NonNull
	public  List<RemoteNodeInfo> getDatalistItems(String datalistName){
		if(this.datalists!=null) {
			return this.datalists.computeIfAbsent(datalistName, d -> new ArrayList<>());
		}
		return new ArrayList<>();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(datalists, params);
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
		return Objects.equals(datalists, other.datalists) && Objects.equals(params, other.params);
	}
	
	
	@Override
	public String toString() {
		return "RemoteEntity [datalists=" + datalists + ", params=" + params + "]";
	}

	
	
	
	
}
