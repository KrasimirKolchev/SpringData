package com.productsshopxml.utils.fileUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileUtil {
    String readFileContent(String filePath) throws IOException;

    void write(String content, String filePath) throws IOException;

    FileInputStream streamFile(String filePath) throws FileNotFoundException;
}
