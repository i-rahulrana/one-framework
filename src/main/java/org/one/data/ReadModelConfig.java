package org.one.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ecco.helpers.config.FilepathConfig;
import org.ecco.utils.FileUtility;

import java.io.IOException;
import java.nio.file.Files;

public class ReadModelConfig {
    public static Logger LOGGER = LogManager.getLogger(ReadModelConfig.class);

    /**
     * This methods provides all the shipping address present in the shipping address json file.
     *
     * @return Shipping Address object
     */
    public static ShippingAddressModel readShippingAddress() {
        byte[] jsonData = null;
        ObjectMapper objectMapper = new ObjectMapper();
        String file = FilepathConfig.TEST_DATA_JSON + "/shippingAddress.json";
        try {
            jsonData = Files.readAllBytes(FileUtils.getFile(FileUtility.getFile(file)).toPath());
        } catch (IOException e) {
            LOGGER.info(e);
        }
        ShippingAddressModel[] shippingAddresses = null;
        try {
            shippingAddresses = objectMapper.readValue(jsonData, ShippingAddressModel[].class);
        } catch (IOException e) {
            LOGGER.info(e);
        }
        return new ShippingAddressModel(shippingAddresses);
    }

    /**
     * This methods provides all the billing address present in the billing address json file.
     *
     * @return Billing Address object
     */
    public static BillingAddressModel readBillingAddress() {
        byte[] jsonData = null;
        ObjectMapper objectMapper = new ObjectMapper();
        String file = FilepathConfig.TEST_DATA_JSON + "/billingAddress.json";
        try {
            jsonData = Files.readAllBytes(FileUtils.getFile(FileUtility.getFile(file)).toPath());
        } catch (IOException e) {
            LOGGER.info(e);
        }
        BillingAddressModel[] billingAddresses = null;
        try {
            billingAddresses = objectMapper.readValue(jsonData, BillingAddressModel[].class);
        } catch (IOException e) {
            LOGGER.info(e);
        }
        return new BillingAddressModel(billingAddresses);
    }

    /**
     * Provides all the 3D secure cards present in the shipping address json file.
     *
     * @return ThreeDSecureCard object
     */
    public static ThreeDSCardModel read3DSecureCard() {
        byte[] jsonData = null;
        ObjectMapper objectMapper = new ObjectMapper();
        String file = FilepathConfig.TEST_DATA_JSON + "/threeDSCardData.json";
        try {
            jsonData = Files.readAllBytes(FileUtils.getFile(FileUtility.getFile(file)).toPath());
        } catch (IOException e) {
            LOGGER.info(e);
        }
        ThreeDSCardModel[] threeDSCards = null;
        try {
            threeDSCards = objectMapper.readValue(jsonData, ThreeDSCardModel[].class);
        } catch (IOException e) {
            LOGGER.info(e);
        }
        return new ThreeDSCardModel(threeDSCards);
    }

    /**
     * Provides all the Non-3D secure cards present in the shipping address json file.
     *
     * @return NonThreeDSecureCard object
     */
    public static NonThreeDSCardModel readNon3DSecureCard() {
        byte[] jsonData = null;
        ObjectMapper objectMapper = new ObjectMapper();
        String file = FilepathConfig.TEST_DATA_JSON + "/nonThreeDSCardData.json";
        try {
            jsonData = Files.readAllBytes(FileUtils.getFile(FileUtility.getFile(file)).toPath());
        } catch (IOException e) {
            LOGGER.info(e);
        }
        NonThreeDSCardModel[] nonThreeDSCards = null;
        try {
            nonThreeDSCards = objectMapper.readValue(jsonData, NonThreeDSCardModel[].class);
        } catch (IOException e) {
            LOGGER.info(e);
        }
        return new NonThreeDSCardModel(nonThreeDSCards);
    }
}
