package org.one.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUtility {

    /**
     * This method establish connection with the given filename and returns file
     * object.
     *
     * @param fileName
     * @return file instance.
     */
    public static File getFile(String fileName) {
        if (FileUtility.class.getClassLoader().getResourceAsStream(fileName) != null) {
            InputStream resourceAsStream = FileUtility.class.getClassLoader().getResourceAsStream(fileName);
            File file = new File(fileName, "");
            try {
                FileUtils.copyInputStreamToFile(resourceAsStream, file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return file;
        } else {
            return new File(fileName);
        }
    }

    /**
     * This methods creates directory if not exists.
     *
     * @param directory
     */
    public static void createDirectoryIfNotExist(File directory) {
        if (!directory.exists()) {
            File dir = new File("./" + directory);
            dir.mkdirs();
        }
    }

    /**
     * Copying file to the given directory.
     *
     * @param file
     * @param directory
     * @throws IOException
     */
    public static void copyFileToDirectory(File file, File directory) throws IOException {
        createDirectoryIfNotExist(directory);
        FileUtils.copyFileToDirectory(file, directory, true);
    }

    /**
     * This methods force delete the given file.
     *
     * @param file
     * @throws IOException
     */
    public static void forceDelete(File file) {
        file.delete();
    }
}
