package org.tsymq.io;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * java IOTest.java "io" "source"  "target"
 */
public class IOTest {

    public static void main(String[] args) {

        String type = args[0];
        String inputFilePath = args[1];
        String outputFilePath = args[2];

        if ("io".equalsIgnoreCase(type)) {
            inputStreamCopyFile(inputFilePath, outputFilePath);

        } else if ("buffer".equalsIgnoreCase(type)) {
            bufferInputStreamCopyFile(inputFilePath, outputFilePath);

        } else if ("mmap".equalsIgnoreCase(type)) {
            mmapCopyFile(inputFilePath, outputFilePath);

        } else if ("sendfile".equalsIgnoreCase(type)) {
            sendfileCopyFile(inputFilePath, outputFilePath);

        }

    }

    private static void sendfileCopyFile(String inputFilePath, String outputFilePath) {

        long start = System.currentTimeMillis();

        try (FileChannel channelIn = new FileInputStream(inputFilePath).getChannel();
             FileChannel channelOut = new FileOutputStream(outputFilePath).getChannel();){
            //方式一：针对小于2GB文件，返回实际拷贝的文件大小；如果超过2GB，则超过的会被丢弃
            //long transferSize = channelIn.transferTo(0, channelIn.size(), channelOut);

            //方式二：针对大于2GB文件，分多次读写
            //获取文件总大小
            long size = channelIn.size();
            for(long left=size ; left > 0;){
                // transferSize 真实拷贝的长度，size - left 计算下次要拷贝的位置
                long transferSize = channelIn.transferTo((size-left),left, channelOut);
                System.out.println("总大小："+size+ ",拷贝大小："+transferSize);
                //剩余的字节数
                left = left-transferSize;
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end - start));



    }

    private static void mmapCopyFile(String inputFilePath, String outputFilePath) {
        long start = System.currentTimeMillis();

        try (
                FileChannel channelIn = new FileInputStream(inputFilePath).getChannel();
                FileChannel channelOut = new RandomAccessFile(outputFilePath,"rw").getChannel()
        ){
            long size = channelIn.size();
            System.out.println("mmap size:"+size);

            MappedByteBuffer mappedInByteBuffer = channelIn.map(FileChannel.MapMode.READ_ONLY, 0, size);
            MappedByteBuffer mappedOutByteBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE, 0, size);

            for(int i=0; i<size;i++){
                byte b = mappedInByteBuffer.get(i);
                mappedOutByteBuffer.put(b);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end - start));


    }

    private static void bufferInputStreamCopyFile(String inputFilePath, String outputFilePath) {
        long start = System.currentTimeMillis();
        try (
                BufferedInputStream bis = new BufferedInputStream( new FileInputStream(inputFilePath));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFilePath));
        ) {

            byte[] buf = new byte[1];
            int len;
            while ((len = bis.read(buf)) != -1) {
                bos.write(buf);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end - start));
    }

    private static void inputStreamCopyFile(String inputFilePath, String outputFilePath) {

        long start = System.currentTimeMillis();
        try (
                FileInputStream fis = new FileInputStream(inputFilePath);
                FileOutputStream fos = new FileOutputStream(outputFilePath)
        ) {

            byte[] buf = new byte[1];
            int len;
            while ((len = fis.read(buf)) != -1) {
                fos.write(buf);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end - start));

    }
}