package softuni.exam.util;

import java.io.IOException;

public interface FileUtil {

    String readFile(String filePath) throws IOException;

    void write(String content, String filePath) throws IOException;
}
