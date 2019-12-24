package com.json.comparator;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.diff.JsonDiff;

public class JsonCompareUtil {

	private static final Logger log = Logger.getLogger(JsonCompareUtil.class);
	
	public static Optional<JsonDiffResponse> compareJsonNode(JsonNode jsonNode1, JsonNode jsonNode2) {

		JsonDiffResponse jsonDiffResponse = null;

		try {
			JsonNode patchNode = JsonDiff.asJson(jsonNode1, jsonNode2);
			// System.out.println("Diff:"+ patchNode);

			final JsonPatch patch = JsonDiff.asJsonPatch(jsonNode1, jsonNode2);
			final JsonNode patchedJson = patch.apply(jsonNode1);

			jsonDiffResponse = new JsonDiffResponse();
			jsonDiffResponse.setPatchNode(patchNode);
			jsonDiffResponse.setPatchedJson(patchedJson);

		} catch (Exception e) {
			log.error("Error in compareJsonNode", e);
		}
		return Optional.ofNullable(jsonDiffResponse);
	}

	public static Optional<JsonDiffResponse> compareJsonFiles(File file1, File file2) {

		FileInputStream fis1 = null;
		FileInputStream fis2 = null;

		try {

			if (!file1.exists() || !file2.exists()) {
				log.error("File(s) doesn't exist");
				return Optional.ofNullable(null);
			}

			fis1 = new FileInputStream(file1);
			fis2 = new FileInputStream(file2);

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

			final JsonNode patch1 = mapper.readValue(fis1, JsonNode.class);
			final JsonNode patch2 = mapper.readValue(fis2, JsonNode.class);

			return compareJsonNode(patch1, patch2);

		} catch (Exception e) {
			log.error("Error in compareJsonFiles", e);
		} finally {
			try {
				if (fis1 != null)
					fis1.close();
				if (fis2 != null)
					fis2.close();
			} catch (Exception e) {
			}
		}
		return Optional.ofNullable(null);
	}

	public static Optional<JsonDiffResponse> compareJsonString(String json1, String json2) {

		try {

			if ((json1 == null || json2 == null) || (json1.isEmpty() || json2.isEmpty()))
				return Optional.ofNullable(null);

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

			final JsonNode patch1 = mapper.readValue(json1, JsonNode.class);
			final JsonNode patch2 = mapper.readValue(json2, JsonNode.class);

			return compareJsonNode(patch1, patch2);

		} catch (Exception e) {
			log.error("Error in compareJsonString()", e);
		}
		return Optional.ofNullable(null);
	}

	public static Optional<JsonDiffResponse> compareJsonObject(Class<?> obj1, Class<?> obj2) {

		try {

			if (obj1 == null || obj2 == null)
				return Optional.ofNullable(null);

			// Verify required or not
			if (obj1.getClass() != obj2.getClass()) {
				log.error("Object class type doesn't match");
				return Optional.ofNullable(null);
			}

			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

			JsonNode patch1 = mapper.valueToTree(obj1);
			JsonNode patch2 = mapper.valueToTree(obj2);

			return compareJsonNode(patch1, patch2);

		} catch (Exception e) {
			log.error("Error in compareJsonObject()", e);
		}
		return Optional.ofNullable(null);
	}

}
