/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gk.app;

import java.io.File;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jconfig.Configuration;

/**
 *
 * @author TUANPLA
 */
public class MyConfig {

    static Logger logger = Logger.getLogger(MyConfig.class);
    public static Configuration config;

    public MyConfig() {
        config = null;
    }

    public static String SMTP_MAIL;
    public static String SMTP_PASS;
    public static String MAIL_HOST;
    public static String FROM_NAME;
    public static String MAIL_DEBUG;
    public static int SEND_MAIL_FALSE = 1;
    //--Menu Type
    public static int MENU_TYPE_CAT = 0;
    public static int MENU_TYPE_LINK = 1;
    // khuyenmai 36
    public static String KHUYENMAI36_ROOT_DIR;

    static {

        // khuyenmai 36
    }
    //------------STATUS CONTENT------------------------------
//    public static long MAX_FILE_SIZE = 10 * 1024 * 1024;

    public static void loadConfig() {
        try {
            // Email
            SMTP_MAIL = getString("SMTP_MAIL", "smpp.gmail.com", "EMAIL");
            SMTP_PASS = getString("SMTP_PASS", "smpp.gmail.com", "EMAIL");
            MAIL_HOST = getString("SMTP_PASS", "smpp.gmail.com", "EMAIL");
            FROM_NAME = getString("SMTP_PASS", "smpp.gmail.com", "EMAIL");
            MAIL_DEBUG = getString("SMTP_PASS", "smpp.gmail.com", "EMAIL");
            // khuyenmai 36
            KHUYENMAI36_ROOT_DIR = getString("KHUYENMAI36_ROOT_DIR", "", "khuyenmai");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initLog4j() {
        String log4jPath = "../config/Log4j.properties";
        //--
        File yoMamaYesThisSaysYoMama = new File(log4jPath);
        if (yoMamaYesThisSaysYoMama.exists()) {
            System.out.println("====>Initializing Log4j ServiceNews:" + log4jPath);
            PropertyConfigurator.configure(log4jPath);
        } else {
            System.err.println("=====> ServiceNews *** " + log4jPath + " file not found, so initializing log4j with BasicConfigurator");
            BasicConfigurator.configure();
        }
    }

    public static int getInt(String properties, int defaultVal, String categoryName) {
        try {
            return Integer.parseInt(config.getProperty(properties, defaultVal + "", categoryName));
        } catch (NumberFormatException e) {
            logger.error(e);
            return defaultVal;
        }
    }

    public static boolean getBoolean(String properties, boolean defaultVal, String categoryName) {
        boolean val = false;
        try {
            int tem = Integer.parseInt(config.getProperty(properties, 1 + "", categoryName));
            if (tem == 1) {
                val = true;
            }
            return val;
        } catch (NumberFormatException e) {
            logger.error(e);
            return defaultVal;
        }
    }

    public static long getLong(String properties, long defaultVal, String categoryName) {
        try {
            return Long.parseLong(config.getProperty(properties, defaultVal + "", categoryName));
        } catch (NumberFormatException e) {
            logger.error(e);
            return defaultVal;
        }
    }

    public static Double getDouble(String properties, Double defaultVal, String categoryName) {
        try {
            return Double.parseDouble(config.getProperty(properties, defaultVal + "", categoryName));
        } catch (NumberFormatException e) {
            logger.error(e);
            return defaultVal;
        }
    }

    public static String getString(String properties, String defaultVal, String categoryName) {
        try {
            return config.getProperty(properties, defaultVal, categoryName);
        } catch (Exception e) {
            logger.error(e);
            return defaultVal;
        }
    }
}
