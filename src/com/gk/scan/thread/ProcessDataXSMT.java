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
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author centurion
 */
public class ProcessDataXSMT extends Thread {

    private String[] listXsCode = null;
    private int[] hasResult = null;
    ArrayList<LotteryResult> resultMinhNgoc = null;
    private static final HashMap<String, Integer> HAS_FULL = new HashMap();  // danh dau co ket qua hay chua

    private boolean fullMT = false;

    public ProcessDataXSMT() {
        this.setName("THREAD ProcessDataXSMT");
    }

    private void clearAll() {
        listXsCode = null;
        hasResult = null;
        resultMinhNgoc = null;
        HAS_FULL.clear();
        fullMT = false;
    }

    @Override
    public void run() {
        while (!fullMT) {
            String date = DateProc.Timestamp2DDMMYYYY(DateProc.createTimestamp());
            double time = DateProc.getTimer();
            buildXSCodeToDay(date);
            System.out.println("TIME-MIEN TRUNG:" + time);
            try {
                if (listXsCode != null) {
                    // Minh Ngoc
                    try {
                        fullMT = checkFull(resultMinhNgoc);
                        if (!fullMT) {
                            resultMinhNgoc = new GetDataMinhNgoc().getDataMTrung();
                            LotteryLive liveDao = new LotteryLive();
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
                            System.out.println("----------------> DA FULL KQ MIEN TRUNG Minh Ngoc----------------------");
                        }
                    } catch (Exception e) {
                        System.out.println("Error:" + e.getMessage());
                    }

                    //-------- END MINH NGOC
//                    if (time > 17.40 && time < 17.75 && !fullMT) {
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
//                                    System.out.println("Lay ket qua MIEN TRUNG  xoso24h xong:" + date + "-Code:" + listXsCode[i]);
//                                    hasResult[i] = 1;
//                                    //
//                                } else {
//                                    System.out.println("Chua co ket qua MIEN TRUNG  xoso24h!");
//                                }
//                            }
//                        }
//                    }
                } else {
                    buildXSCodeToDay(date);
                }
                sleep(7 * 1000);
                System.out.print("^_^");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    sleep(30 * 1000);
                } catch (Exception ex) {
                }
            }
        }
        System.out.println("End Thread ProcessDataXSMT: " + DateProc.createTimestamp());
        clearAll();
    }

    private boolean checkFullFlag(String code) {
        if (HAS_FULL.get(code) == null || HAS_FULL.get(code) == 0) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    private boolean checkFull(ArrayList<LotteryResult> all) {
        boolean flag = true;
        if (all == null) {
            return false;
        }
        for (LotteryResult one : all) {
            if (one.getSpecial() == null || one.getSpecial().equals("") || one.getSpecial().length() < 5) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private void buildXSCodeToDay(String date) {
        String[] lotteDay = LotteryUtils.getCodeMTCompany(date);
        try {
            if (lotteDay != null && lotteDay.length > 0) {
                // Khoi tao cac do tuong
                listXsCode = new String[lotteDay.length];
                hasResult = new int[lotteDay.length];

                //----------
                for (int i = 0; i < lotteDay.length; i++) {
                    if (lotteDay[i] != null) {
                        listXsCode[i] = lotteDay[i];
                        System.out.println("XS-MIEM TRUNG=" + listXsCode[i]);
                        hasResult[i] = 0;
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
