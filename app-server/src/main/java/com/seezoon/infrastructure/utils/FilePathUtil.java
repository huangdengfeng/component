package com.seezoon.infrastructure.utils;

import org.apache.commons.lang3.StringUtils;

public class FilePathUtil {

    /**
     * HTTP协议前缀
     */
    public static final String HTTP_PREFIX = "http://";

    /**
     * HTTPS协议前缀
     */
    public static final String HTTPS_PREFIX = "https://";

    public static String getFilePath(String fileUrlPrefix, String file) {
        if (StringUtils.isEmpty(file) || StringUtils.isEmpty(fileUrlPrefix)) {
            return file;
        }
        if (file.startsWith(HTTP_PREFIX) || file.startsWith(HTTPS_PREFIX)) {
            return file;
        }
        return fileUrlPrefix + file;
    }
}
