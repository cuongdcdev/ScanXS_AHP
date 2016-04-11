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
public class QuetLaiXSMN extends Thread {

    @Override
    public void run() {
        Timestamp currentTime = DateProc.createTimestamp();
        int i = 0;
        while (i < AppStart.XS_BACK_DATE) {
            try {
                String date = DateProc.Timestamp2DDMMYYYY(currentTime);
                String[] codeMN = LotteryUtils.getMNCompany(date);
                for (int j = 0; j < codeMN.length; j++) {
                    if (codeMN[j].equals("")) {
                        continue;
                    }
                    try {
                        LotteryResult result = null;
//                        LotteryResult result = new GetXSmxoso24h().getDataFromMxoso24h(codeMN[j], date);
                        if (result != null) {
                            result.insertResult(result);
                            // Xu ly Static
                            ProcessStaticAll oneThred = new ProcessStaticAll(result);
                            oneThred.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                sleep(2 * 1000);
                currentTime = DateProc.getPreviousDate(currentTime);
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
