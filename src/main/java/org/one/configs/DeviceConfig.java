package org.one.configs;

import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.one.data.AndroidDeviceModel;
import org.one.data.DeviceViewportModel;
import org.one.data.IOSDeviceModel;
import org.one.utils.FileUtility;

import java.io.IOException;
import java.nio.file.Files;

public class DeviceConfig {
    public static String executionPlatform;

    public static synchronized String getExecutionPlatform() {
        return executionPlatform;
    }

    public synchronized void setExecutionPlatform(String executionPlatform) {
        this.executionPlatform = executionPlatform;
    }

    /**
     * This method reads iOS device config to create the iOS appium session.
     * @return
     * @throws IOException
     */
    public static IOSDeviceModel readIOSDeviceConfig() throws IOException {
        byte[] jsonData = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonData = Files.readAllBytes(
                    FileUtils.getFile(FileUtility.getFile(FilepathConfig.IOS_DEVICE_CONFIG)).toPath()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IOSDeviceModel[] iosDeviceModels = objectMapper.readValue(jsonData, IOSDeviceModel[].class);
        return new IOSDeviceModel(iosDeviceModels);
    }

    /**
     * This method reads android device config to create the android appium session.
     *
     * @return
     * @throws IOException
     */
    public static AndroidDeviceModel readAndroidDeviceConfig() throws IOException {
        byte[] jsonData = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonData = Files.readAllBytes(
                    FileUtils.getFile(FileUtility.getFile(FilepathConfig.ANDROID_DEVICE_CONFIG)).toPath()

            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AndroidDeviceModel[] androidDeviceModels = objectMapper.readValue(jsonData, AndroidDeviceModel[].class);
        return new AndroidDeviceModel(androidDeviceModels);
    }

    /**
     * This method reads device viewport config.
     *
     * @return
     * @throws IOException
     */
    public static DeviceViewportModel readDeviceViewportConfig() throws IOException {
        byte[] jsonData = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonData = Files.readAllBytes(
                    FileUtils.getFile(FileUtility.getFile(FilepathConfig.DEVICE_VIEWPORT_CONFIG)).toPath()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DeviceViewportModel[] deviceViewportModels = objectMapper.readValue(jsonData, DeviceViewportModel[].class);
        return new DeviceViewportModel(deviceViewportModels);
    }
}
