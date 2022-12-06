package jdk.io;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class FileIoTest {

    /**
     * 递归查找路径下所有路径和文件
     */
    public List<File> listFileTest(File file) {
        List<File> fileList = new ArrayList<>();
        listFile(file, fileList);
        return fileList;
    }

    private void listFile(File file, List<File> fileList) {
        if (file == null || !file.exists() || !file.isDirectory()) {
            return;
        }
        for (File fileTemp : file.listFiles()) {
            fileList.add(fileTemp);
            listFile(fileTemp, fileList);
        }
    }

    /**
     * 复制文件测试
     */
    public void copyFileTest() {
        try (
                InputStream inputStream = Files.newInputStream(new File("/Users/tsymq/Downloads/1669099892411.jpg").toPath());
                BufferedInputStream bis = new BufferedInputStream(inputStream);
                OutputStream outputStream = Files.newOutputStream(new File("/Users/tsymq/test.jpg").toPath());
                BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        ) {
            byte[] buffer = new byte[1024];
            int size;
            while ((size = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, size);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        FileIoTest fileIoTest = new FileIoTest();
//        fileIoTest.copyFileTest();
        List<File> files = fileIoTest.listFileTest(new File("/Users/tsymq/logs"));
        for(File file : files){
            System.out.println(file);
        }
    }
}
