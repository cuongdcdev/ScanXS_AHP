/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gk.rescan.thread;

import com.gk.scan.thread.ProcessStaticAll;
import com.gk.app.AppStart;
import com.gk.scan.xs.LotteryResult;
import com.gk.scan.xs.LotteryUtils;
import com.gk.utils.DateProc;
import java.sql.Timestamp;

/**
 *
 * @author centurion
 */
public class QuetLaiXSMT extends Thread {

    static String[] ALL = {"HCM", "KG", "TRV", "LD", "QB", "PY", "TTH", "QNM", "DLK", "DNG", "KH", "BDI", "QT", "GL", "NT", "QNG", "DNO", "KT", "DT",
        "CM", "BTR", "VT", "BL", "DNI", "CT", "ST", "BTH", "TN", "AG", "BD", "VL", "BP", "HG", "LA", "TG"};

    public QuetLaiXSMT() {
    }

    @Override
    public void run() {
        Timestamp currentTime = DateProc.createTimestamp();
        int i = 0;
        while (i < AppStart.XS_BACK_DATE) {
            try {
                String date = DateProc.Timestamp2DDMMYYYY(currentTime);
                String[] codeMT = LotteryUtils.getCodeMTCompany(date);
                for (String oneCode : codeMT) {
                    if (oneCode.equals("")) {
                        continue;
                    }
                    try {
                        LotteryResult result = null;
//                        LotteryResult result = new GetXSmxoso24h().getDataFromMxoso24h(oneCode, date);
                        if (result != null) {
                            result.insertResult(result);
                            // Xu ly Static
                            ProcessStaticAll oneThred = new ProcessStaticAll(result);
                            oneThred.start();
                        }
                    }catch (Exception e) {
                        e.printStackTrace();                        
                    }
                }
                sleep(3 * 1000);
                currentTime = DateProc.getPreviousDate(currentTime);
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
