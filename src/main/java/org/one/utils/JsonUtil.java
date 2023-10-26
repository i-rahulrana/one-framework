package org.one.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Map;

public class JsonUtil {
    private static final Logger log = LogManager.getLogger(JsonUtil.class);
    JSONObject jsonObject;
    JSONArray jsonArray;
    Object obj;

    public JsonUtil(String jsonFilePath, String jsonFileName){
        initJsonTestData(jsonFilePath, jsonFileName);
    }

    /**
     * Initialize json test data file.
     */
    public void initJsonTestData(String jsonFilePath, String jsonFileName) {
        try {
            obj = new JSONParser().parse(new FileReader(jsonFilePath + jsonFileName));
            jsonObject = (JSONObject) obj;
            jsonArray = new JSONArray();
        } catch (Exception e) {
            log.info("Json test data file :" + jsonFileName + " not found");
        }
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    /**
     * This method is used to get json data value as String.
     * @param key
     * @return
     */
    public String getStringJsonValue(String key) {
        String value = null;
        value = String.valueOf(jsonObject.get(key)).trim();
        return value;
    }

    /**
     * This method is used to get json data object as Map.
     * @param key
     */
    public Map getMapJsonObject(String key) {
        Map jsonObj = null;
        jsonObj = (Map) jsonObject.get(key);
        return jsonObj;
    }

    /**
     * This method is used to get json data value as JSON Array.
     *
     * @param key
     * @return
     *
     */
    public JSONArray getJSONArrayObject(String key) {
        JSONArray jsonArrayObj = null;
        jsonArrayObj = (JSONArray) jsonObject.get(key);
        return jsonArrayObj;
    }

    /**
     * This method is used to get the json data value as long.
     *
     * @param key
     * @return
     *
     */
    public long getLongJsonValue(String key) {
        long value = 0;
        value = (long) jsonObject.get(key);
        return value;
    }

    /**
     * This method is used to set the json test data key value.
     *
     * @param key
     * @param value
     *
     */
    public void setStringJsonValue(String key, String value) {
        jsonObject.put(key, value);
    }

    /**
     * This method is used to set the json test data key value.
     *
     * @param key
     * @param value
     */
    public void setLongJsonValue(String key, long value) {
        jsonObject.put(key, value);
    }

    /**
     * This method is used to set the json test data key map.
     *
     * @param key
     * @param value
     */
    public void setMapJsonObject(String key, Map value) {
        jsonObject.put(key, value);
    }
}
