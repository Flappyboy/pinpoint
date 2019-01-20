package cn.edu.nju.software.pinpoint.statistics.utils.file;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;

public class FileCompress {
//    public static void main(String[] args) {
//        unCompress("/Users/yaya/Desktop/analysis_code-0.0.1-SNAPSHOT.war", "/Users/yaya/Desktop/analysis_code");
//
//        unCompress("/Users/yaya/Desktop/jedis-2.4.2.jar", "/Users/yaya/Desktop/jedis-2.4.2");
//    }

    public static void unCompress(String warOrJarFile, String unzipPath) {
        if (warOrJarFile.trim().endsWith(".war") || warOrJarFile.trim().endsWith(".jar")) {
            File warFile = new File(warOrJarFile);

            try {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(warFile));
                ArchiveInputStream in = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.JAR, bufferedInputStream);
                JarArchiveEntry entry = null;
                while ((entry = (JarArchiveEntry) in.getNextEntry()) != null) {
                    if (entry.isDirectory()) {
                        new File(unzipPath, entry.getName()).mkdir();
                    } else {
                        OutputStream out = FileUtils.openOutputStream(new File(unzipPath, entry.getName()));
                        IOUtils.copy(in, out);
                        out.close();
                    }
                }
                in.close();
            } catch (FileNotFoundException e) {
                System.err.println("未找到war文件");
            } catch (ArchiveException e) {
                System.err.println("不支持的压缩格式");
            } catch (IOException e) {
                System.err.println("文件写入发生错误");
            }
        }

    }
}
