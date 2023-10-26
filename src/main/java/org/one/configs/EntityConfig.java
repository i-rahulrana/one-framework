package org.one.configs;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.ecco.utils.FileUtility;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class EntityConfig {

    /**
     * This method reads sorting config for the filters.
     *
     * @return
     * @throws IOException
     */
    public static Map<String, String> readSortingConfig() throws IOException {
        byte[] jsonData = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonData = Files.readAllBytes(
                    FileUtils.getFile(FileUtility.getFile(FilepathConfig.SORTING_FILTERS_CONFIG)).toPath()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, String> sortingModel = objectMapper.readValue(jsonData, Map.class);
        return sortingModel;
    }
}
