package com.gk.utils;

import java.io.*;

public class FileManipulation {

    public FileManipulation() {
        super();
    }

    // Copies src file to dst file.
    // If the dst file does not exist, it is created
    public static void copy(String src, String dst) throws IOException {
        File f_src = new File(src);
        File f_dst = new File(dst);
        InputStream in = new FileInputStream(f_src);
        OutputStream out = new FileOutputStream(f_dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
    //

    public static void createTempFile(String pattern, String suffix) {//yyyymmdd,.gif
        try {
            // Create temp file.
            File temp = File.createTempFile(pattern, suffix);

            // Delete temp file when program exits.
            temp.deleteOnExit();

            // Write to temp file
            BufferedWriter out = new BufferedWriter(new FileWriter(temp));
            out.write("aString");
            out.close();
        } catch (IOException e) {
        }
    }
    //

    public static void moveFile(String src, String des) {
        // File (or directory) to be moved
        File file = new File(src);

        // Destination directory
        File dir = new File(des);

        // Move file to new directory
        boolean success = file.renameTo(new File(dir, file.getName()));
        if (!success) {
            // File was not successfully moved
        }

    }
    //

    public static void rename(String oldName, String newName) {
        String str = "Success";
        // Renaming a File or Directory

        // File (or directory) with old name
        File file = new File(oldName);

        // File (or directory) with new name
        File file2 = new File(newName);

        // Rename file (or directory)
        boolean success = file.renameTo(file2);
        if (!success) {
            // File was not successfully renamed
            str = "Doi ten file khong thanh cong";
            System.out.println(str);
        }
    }

    //
    public static boolean createNewFile(String filename) {

        try {
            File file = new File(filename);

            // Create file if it does not exist
            boolean success = file.createNewFile();
            if (success) {
                // File did not exist and was created
                return true;
            } else {
                // File already exists
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    //
    public static long getFileSize(String filename) {
        File file = new File(filename);
        // Get the number of bytes in the file
        long length = file.length();
        return length;

    }

    //
    public static void listFiles(String pathDir) {
        File dir = new File(pathDir);

        String[] children = dir.list();
        if (children == null) {
            // Either dir does not exist or is not a directory
        } else {
            for (int i = 0; i < children.length; i++) {
                // Get filename of file or directory
                String filename = children[i];
            }
        }

        // It is also possible to filter the list of returned files.
        // This example does not return any files that start with `.'.
        FilenameFilter filter = new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return !name.startsWith(".");
            }
        };
        children = dir.list(filter);

        // The list of files can also be retrieved as File objects
        File[] files = dir.listFiles();

        // This filter only returns directories
        FileFilter fileFilter = new FileFilter() {

            public boolean accept(File file) {
                return file.isDirectory();
            }
        };
        files = dir.listFiles(fileFilter);

    }

    //
    public static boolean createDir(String dir) {
        // Create a directory; all ancestor directories must exist
        boolean success = true;
//  boolean success = (new File(dir)).mkdir();
//  if (!success) {
//      // Directory creation failed
//  }

        // Create a directory; all non-existent ancestor directories are
        // automatically created
        success = (new File(dir)).mkdirs();
        if (!success) {
            // Directory creation failed
            return false;

        }
        return success;
    }

    //
    public static void delete(String fileName) {
        try {
            // Construct a File object for the file to be deleted.
            File target = new File(fileName);

            if (!target.exists()) {
                System.err.println("File " + fileName
                        + " not present to begin with!");
                return;
            }

            // Quick, now, delete it immediately:
            if (target.delete()) {
                System.err.println("** Deleted " + fileName + " **");
            } else {
                System.err.println("Failed to delete " + fileName);
            }
        } catch (SecurityException e) {
            System.err.println("Unable to delete " + fileName + "("
                    + e.getMessage() + ")");
        }
    }

    //
    public static String getFileType(String fileName) {
        String type = "";
        try {
            int index = fileName.indexOf(".");
            type = fileName.substring(index + 1, fileName.length());
        } catch (Exception e) {
        }
        return type;
    }

    //
    public static String converTxtFile2String(BufferedReader br) {
        if (br == null) {
            return null;
        }
        String result = "";
        try {
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line + "\n";
            }
        } catch (Exception ex) {
            System.out.println("convertTxtFie2String:" + ex.toString());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception ex) {
            }
        }
        return result;
    }
}
