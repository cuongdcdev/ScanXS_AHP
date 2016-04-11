/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gk.scan.thread;

import com.gk.scan.xs.GetDataMinhNgoc;
import com.gk.scan.xs.LotteryLive;
import com.gk.scan.xs.LotteryResult;
import com.gk.scan.xs.LotteryUtils;
import com.gk.utils.DateProc;

/**
 *
 * @author centurion
 */
public class ProcessDataXSMB extends Thread {

    private boolean XS_MinhNgoc = false;

    public ProcessDataXSMB() {
        this.setName("Thread ProcessDataXSMB");
    }

    @Override
    public void run() {
        LotteryLive liveDao = new LotteryLive();
        double time = DateProc.getTimer();
        //--- RUN
        while (!XS_MinhNgoc && time > 18 && time < 19) {
            String date = DateProc.Timestamp2DDMMYYYY(DateProc.createTimestamp());
            String code = LotteryUtils.getMainCompany(date);

            System.out.println("TIME-MIENBAC:" + time);
            try {
                // Minh Ngoc
                try {
                    if (!XS_MinhNgoc) {
                        LotteryResult result = new GetDataMinhNgoc().getDataMB();
                        if (result != null) {
                            liveDao.insertResultLive(result);
                            //----------------
                            if (LotteryUtils.checkFullPrizeMB(result.getSpecial(), 0)) {
                                XS_MinhNgoc = true;
                                result.insertResultMB(result);
                                // INSERT Statistic
                                ProcessStaticAll oneThred = new ProcessStaticAll(result);
                                oneThred.start();
                            }
                        }
                    } else {
                        System.out.println("----------Minh Ngoc da co ket qua Full MB -------------");
                    }
                } catch (Exception e) {
                    System.out.println("Khong lay duoc ket qua MN....");
                }

//                //--------
//                if (time > 18.40 && time < 18.75 && !XS_MinhNgoc) {
//                    // Co nen gửi 1 MO thong bao ko ?
//                    LotteryResult result = new GetXSmxoso24h().getDataFromMxoso24h(code, date);
//                    if (result != null && LotteryUtils.checkFullPrizeMB(result.getSpecial(), 0)) {
//                        result.insertResultMB(result);
//                        //--
//                        // INSERT Statistic
//                        ProcessStaticAll oneThred = new ProcessStaticAll(result);
//                        oneThred.start();
//                        //
//                        System.out.println("Lay ket qua Mien bac xoso24h xong:" + date);
//                    } else {
//                        System.out.println("Chua co ket qua Mien bac xoso24h!");
//                    }
//                }
                System.out.print("^_^");
                sleep(5 * 1000);
                System.out.print("^_^");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    sleep(30 * 1000);
                } catch (Exception ex) {
                }
            }
        }
        System.out.println("End Thread Scan XSMB");
        XS_MinhNgoc = false;
    }
}
