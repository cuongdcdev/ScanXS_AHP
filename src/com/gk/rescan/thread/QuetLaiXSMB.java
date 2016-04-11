/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gk.rescan.thread;

import com.gk.scan.thread.ProcessStaticAll;
import com.gk.app.AppStart;
import com.gk.scan.xs.GetDataMinhNgoc;
import com.gk.scan.xs.LotteryResult;
import com.gk.scan.xs.LotteryUtils;
import com.gk.utils.DateProc;
import java.sql.Timestamp;

/**
 *
 * @author centurion
 */
public class QuetLaiXSMB extends Thread {

    @Override
    public void run() {
        Timestamp currentTime = DateProc.createTimestamp();
        int i = 0;
        while (i < AppStart.XS_BACK_DATE) {
            try {
                String date = DateProc.Timestamp2DDMMYYYY(currentTime);
                String code = LotteryUtils.getMainCompany(date);
                LotteryResult result = new GetDataMinhNgoc().getDataMB();
                currentTime = DateProc.getPreviousDate(currentTime);
                if (result != null) {
                    result.insertResultMB(result);
                    // Xu ly Static
                    ProcessStaticAll oneThred = new ProcessStaticAll(result);
                    oneThred.start();
                }
                i++;
                sleep(1 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
