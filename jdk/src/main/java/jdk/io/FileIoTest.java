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

    /**
     * 批量更改文件名
     * @param files
     */
    public void reNameFile(List<File> files) {
        for(File file : files){
            String name = file.getName();
            String unwantedString = "| 值得所有学生收藏，推荐学习！（含课件讲义）";
            if(name.contains(unwantedString)){
                // 删除文件名中的一部分
                String newName = name.replace(unwantedString, "");

                // 创建修改后的文件路径
                String renamedFilePath = file.getParent() + File.separator + newName;
                File renamedFile = new File(renamedFilePath);

                // 重命名文件
                boolean success = file.renameTo(renamedFile);
                if (success) {
                    System.out.println("文件已成功重命名！名字为："+ newName);
                } else {
                    System.out.println("文件重命名失败，请检查文件权限和路径。");
                }

            }
        }
    }

    public static void main(String[] args) {
        FileIoTest fileIoTest = new FileIoTest();
//        fileIoTest.copyFileTest();
        List<File> files = fileIoTest.listFileTest(new File("/Users/tsymq/Desktop/baiduyun/博弈论"));
        fileIoTest.reNameFile(files);
    }


}
