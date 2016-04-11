/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gk.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author PLATUAN
 */
public class FileUtils {

    /**
     *
     * @param arrbyte
     * @param full_path
     * @return
     */
    public static boolean writeFileToDisk(byte[] arrbyte, String full_path) {
        boolean flag = false;
        FileOutputStream fsave = null;
        try {
            File f = new File(full_path);
            if (!f.exists()) {
                f.createNewFile();
            }
            fsave = new FileOutputStream(f);
            fsave.write(arrbyte);
            flag = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fsave.flush();
                fsave.close();
            } catch (IOException ex) {
                System.out.println("Loi dong Ouput Stream");
            }
        }
        return flag;
    }

    /**
     *
     * @param bis
     * @return
     */
    public static byte[] writeBuffer2Byte(BufferedInputStream bis) {
        byte[] byteReturn = null;
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            while (true) {
                int iBytes = bis.read(buffer);
                ////System.out.println(iBytes);
                // If there was nothing read, get out of loop
                if (iBytes == -1) {
                    break;
                }
                baos.write(buffer, 0, iBytes);
            }
            byteReturn = baos.toByteArray();
        } catch (IOException ie) {
            ie.printStackTrace();
        } finally {
            try {
                baos.flush();
                baos.close();
                bis.close();
            } catch (Exception e) {
            }
        }
        return byteReturn;
    }

    public static boolean writeContent(BufferedInputStream bis, ByteArrayOutputStream baos) {
        boolean flag = true;
        byte[] buffer = new byte[1024];
        try {
            while (true) {
                int iBytes = bis.read(buffer);
                // If there was nothing read, get out of loop
                if (iBytes == -1) {
                    break;
                }
                baos.write(buffer, 0, iBytes);
            }
        } catch (IOException ie) {
            ie.printStackTrace();
            flag = false;
        } finally {
            try {
                baos.flush();
                baos.close();
                bis.close();
            } catch (Exception e) {
            }
        }
        return flag;
    }

    public static String getImgExtendTion(String ctType, String imageURL) {
        String ext = "jpg";
        if (!Tool.checkNull(ctType)) {
            if (ctType.equalsIgnoreCase("image/png")) {
                ext = "png";
            }
            if (ctType.equalsIgnoreCase("image/gif")) {
                ext = "gif";
            }
        } else {
            int index = imageURL.lastIndexOf(".");
            if (index > 0) {
                ext = imageURL.substring(index + 1);
            }
        }
        return ext;
    }

    public static byte[] getBytesFromFile(File file) throws IOException, FileNotFoundException {
        byte[] bytes;
        try (InputStream fin = new FileInputStream(file)) {
            long length = file.length();
            if (length > Integer.MAX_VALUE) {
                throw new IOException("File is too large" + file.getName());
                // File is too large
            }
            bytes = new byte[(int) length];
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = fin.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }
        }
        return bytes;
    }
}
