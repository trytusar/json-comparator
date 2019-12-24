package com.json.comparator;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonDiffResponse {

	private JsonNode patchNode;
	private JsonNode patchedJson;
	
	public JsonNode getPatchNode() {
		return patchNode;
	}
	public void setPatchNode(JsonNode patchNode) {
		this.patchNode = patchNode;
	}
	public JsonNode getPatchedJson() {
		return patchedJson;
	}
	public void setPatchedJson(JsonNode patchedJson) {
		this.patchedJson = patchedJson;
	}
	
	@Override
	public String toString() {
		return "JsonDiffResponse [patchNode=" + patchNode + ", patchedJson=" + patchedJson + "]";
	}
		
	
}
