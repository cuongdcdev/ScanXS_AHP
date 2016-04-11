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
import com.gk.utils.Tool;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author centurion
 */
public class ProcessDataXSMN extends Thread {

    private String[] listXsCode = null;
    private int[] hasResult = null;
    ArrayList<LotteryResult> resultMinhNgoc = null;
    private static final HashMap<String, Integer> HAS_FULL = new HashMap();  // danh dau co ket qua hay chua

    private boolean fullMN = false;

    public ProcessDataXSMN() {
        this.setName("THREAD ProcessDataXSMN");
    }

    private void clearAll() {
        listXsCode = null;
        hasResult = null;
        resultMinhNgoc = null;
        HAS_FULL.clear();
        fullMN = false;
    }

    @Override
    public void run() {
        // Lay Ngay Hien Tai
        String date = DateProc.Timestamp2DDMMYYYY(DateProc.createTimestamp());
        // Build Code NGay Hien Tai
        buildXSCodeToDay(date);
        LotteryLive liveDao = new LotteryLive();
        while (!fullMN) {   // Chua Full Ket Qua MN thi con chay            
            double time = DateProc.getTimer();
            System.out.println("TIME- MIEN NAM:" + time);
            try {
                if (listXsCode != null) {
                    // ********** Minh Ngoc ***************
                    try {
                        fullMN = checkFull(resultMinhNgoc, listXsCode.length);
                        if (!fullMN) {
                            // Chua full Thi Quet
                            resultMinhNgoc = new GetDataMinhNgoc().getDataMNam();
                            if (resultMinhNgoc != null && resultMinhNgoc.size() > 0) {
                                for (LotteryResult one : resultMinhNgoc) {
                                    if (!checkFullFlag(one.getCode())) {
                                        liveDao.insertResultLive(one);
                                    }
                                    if (LotteryUtils.checkFullPrizeMN_MT(one.getSpecial(), 0)
                                            && !checkFullFlag(one.getCode())) {
                                        // Inser ket qua
                                        one.insertResult(one);
                                        ProcessStaticAll oneThred = new ProcessStaticAll(one);
                                        oneThred.start();
                                        // Danh dau da insert Ket qua
                                        HAS_FULL.put(one.getCode(), 1);
                                    }
                                }
                            }
                        } else {
                            System.out.println("----------------> DA FULL KQ MEIN NAM Minh Ngoc----------------------");
                        }
                    } catch (Exception e) {
                    }

                    //********** END Minh Ngoc ***************
//                    if (time > 16.40 && time < 16.75 && !fullMN) {
//                        GetXSmxoso24h oneNet = new GetXSmxoso24h();
//                        for (int i = 0; i < listXsCode.length; i++) {
//                            if (Tool.checkNull(listXsCode[i])) {
//                                continue;
//                            }
//                            if (hasResult[i] == 0) {
//                                LotteryResult result = oneNet.getDataFromMxoso24h(listXsCode[i], date);
//                                if (result != null && LotteryUtils.checkFullPrizeMN_MT(result.getSpecial(), 0)) {
//                                    result.insertResult(result);
//                                    ProcessStaticAll oneThred = new ProcessStaticAll(result);
//                                    oneThred.start();
//                                    System.out.println("Lay ket qua MIEN NAM xoso24h xong:" + date + "-Code:" + listXsCode[i]);
//                                    hasResult[i] = 1;
//                                } else {
//                                    System.out.println("Chua co ket qua MIEN NAM xoso24h !");
//                                }
//                            }
//                        }
//                    }
                } else {
                    buildXSCodeToDay(date);
                }
                sleep(10 * 1000);
                System.out.print("^_^");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    sleep(30 * 1000);
                } catch (InterruptedException ex) {
                }
            }
        }
        System.out.println("End Thread ProcessDataXSMN: " + DateProc.createTimestamp());
        clearAll();
    }

    private boolean checkFullFlag(String code) {
        if (HAS_FULL.get(code) == null || HAS_FULL.get(code) == 0) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    private boolean checkFull(ArrayList<LotteryResult> all, int size) {
        boolean flag = true;
        if (all == null || all.isEmpty() || (all.size() != size)) {
            return false;
        }
        for (LotteryResult one : all) {
            if (Tool.checkNull(one.getSpecial()) || one.getSpecial().length() < 5) {
//                Chi can 1 thang chua full la thong bao false luon
                flag = false;
                break;
            }
        }
        return flag;
    }

    private void buildXSCodeToDay(String date) {
        String[] lotteDay = LotteryUtils.getMNCompany(date);
        try {
            if (lotteDay != null && lotteDay.length > 0) {
                // Khoi tao cac do tuong
                listXsCode = new String[lotteDay.length];
                hasResult = new int[lotteDay.length];
                //----------
                for (int i = 0; i < lotteDay.length; i++) {
                    if (lotteDay[i] != null) {
                        listXsCode[i] = lotteDay[i];
                        System.out.println("XS-MN=" + listXsCode[i]);
                        hasResult[i] = 0;
                        HAS_FULL.put(listXsCode[0], 0);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
