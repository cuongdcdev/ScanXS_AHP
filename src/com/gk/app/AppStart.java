package com.gk.app;

import com.gk.db.XSDBPool;
import static com.gk.db.XSDBPool.getConnection;
import com.gk.rescan.thread.QuetLaiXSMB;
import com.gk.rescan.thread.QuetLaiXSMN;
import com.gk.rescan.thread.QuetLaiXSMT;
import com.gk.scan.thread.ProcessDataXSMB;
import com.gk.scan.thread.ProcessDataXSMN;
import com.gk.scan.thread.ProcessDataXSMT;
import com.gk.utils.DateProc;
import com.gk.utils.Tool;
import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jconfig.Configuration;
import org.jconfig.ConfigurationManager;
import org.jconfig.ConfigurationManagerException;
import org.jconfig.handler.XMLFileHandler;

/**
 * MONITER THREAD SCAN XS
 *
 * @author TUANPLA
 */
public class AppStart extends Thread {

    static final Logger logger = Logger.getLogger(AppStart.class);
    public static int nThread;
    public static boolean runing = true;
    public static boolean debug;
    //--------------------------------
    public static final String configDir = "/home/cuong/NetBeansProjects/ScanXsAHP_Clone/config";
//    public static final String configDir = "../config";
    //-------- XS 
    private static boolean XS_RE_RUN = false;
    public static int XS_BACK_DATE = 0;
    // SMS
    private static boolean CALL_THREAD_MN = false;
    private static boolean CALL_THREAD_MT = false;
    private static boolean CALL_THREAD_MB = false;

    static {
        // initial Log4j
        initLog4j(configDir + "/Log4j.properties");
        // Load MyConfig XML
        MyConfig.config = getConfig("config.xml");
        //--SMS
        if (MyConfig.config != null) {
            debug = MyConfig.getBoolean("RE_RUN", true, "appconfig");
            XS_RE_RUN = MyConfig.getBoolean("RE_RUN", false, "xsConfig");
            XS_BACK_DATE = MyConfig.getInt("BACK_DATE", 1, "xsConfig");
            nThread = MyConfig.config.getIntProperty("nThread", 3, "appconfig");
            MyConfig.loadConfig();
        }
        XSDBPool.CreatePool();
        try {
            Connection conn = getConnection();
            if (conn == null || conn.isClosed()) {
                logger.error("INIT CONNECTUON FALSE");
                System.exit(1);
            } else {
                System.out.println("INIT CONNECTION OK");
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        double hm;
        while (AppStart.runing) {
            hm = DateProc.getTimer();
            if (hm > 16 && hm < 16.20) {
                CALL_THREAD_MN = false;
                CALL_THREAD_MT = false;
                CALL_THREAD_MB = false;
            }

            if (hm >= 16.20 && hm < 16.75 && !CALL_THREAD_MN) // Mien Nam chạy đến lúc nào có thì thôi
            {
                ProcessDataXSMN xsmn = new ProcessDataXSMN();
                xsmn.setPriority(Thread.MIN_PRIORITY);
                xsmn.start();
                CALL_THREAD_MN = true;
            }
            if (hm > 17.20 && hm < 17.75 && !CALL_THREAD_MT) // Mien Nam chạy đến lúc nào có thì thôi
            {
                ProcessDataXSMT xsmt = new ProcessDataXSMT();
                xsmt.setPriority(Thread.MIN_PRIORITY);
                xsmt.start();
                CALL_THREAD_MT = true;
            }
            if (hm > 18.20 && hm < 18.75 && !CALL_THREAD_MB) // Mien Bắc chạy đến lúc nào có thì thôi
            {
                ProcessDataXSMB xsmb = new ProcessDataXSMB();
                xsmb.setPriority(Thread.MIN_PRIORITY);
                xsmb.start();
                CALL_THREAD_MB = true;
            }
            try {
                sleep(30 * 1000);
                System.out.print("^_^");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            if (XS_RE_RUN) {
                System.out.println("Quet Lai Ket Qua: [" + AppStart.XS_BACK_DATE + "] ngày");
                QuetLaiXSMB mb = new QuetLaiXSMB();
                mb.start();
                QuetLaiXSMN mn = new QuetLaiXSMN();
                mn.start();
                QuetLaiXSMT mt = new QuetLaiXSMT();
                mt.start();
            }

            new AppStart().start();
            new Moniter().start();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    private static Configuration getConfig(String fileName) {
        Configuration _config = null;
        String configPath = configDir + "/" + fileName;
        configPath = configPath.replaceAll("\\\\", "/");
        File file = new File(configPath);
        logger.info(file.getName());
        XMLFileHandler handler = new XMLFileHandler();
        handler.setFile(file);
        try {
            logger.info("trying to load file config");
            ConfigurationManager cm = ConfigurationManager.getInstance();
            cm.load(handler, "engineConfig");
            logger.info("file config successfully processed");
            logger.info("get Config From ConfigurationManager");
            _config = ConfigurationManager.getConfiguration("engineConfig");
        } catch (ConfigurationManagerException e) {
            e.printStackTrace();
        }
        return _config;
    }

    private static void initLog4j(String log4jPath) {
        if (log4jPath == null) {
            System.err.println("==> No ServiceNews- log4j-properties-location init param, so initializing log4j with BasicConfigurator");
            BasicConfigurator.configure();
        } else {
            File yoMamaYesThisSaysYoMama = new File(log4jPath);
            if (yoMamaYesThisSaysYoMama.exists()) {
                System.out.println("====>Initializing Log4j ServiceNews:" + log4jPath);
                PropertyConfigurator.configure(log4jPath);
            } else {
                System.err.println("=====> ServiceNews *** " + log4jPath + " file not found, so initializing log4j with BasicConfigurator");
                BasicConfigurator.configure();
            }
        }
    }

    /**
     * ShutDown
     */
    public void shutDown() {
        runing = false;
        //---
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                Tool.Debug("taichinhinfo.com -- Deregis Driver:" + driver.toString());
                logger.log(Level.INFO, String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                logger.log(Level.ERROR, String.format("Error deregistering driver %s", driver), e);
            }
        }
        System.out.println("taichinhinfo.com contextDestroyed ............");
    }
}
