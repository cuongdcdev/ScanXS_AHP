/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gk.scan.thread;

import com.gk.db.XSDBPool;
import com.gk.scan.xs.LotteryResult;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author centurion
 */
public class ProcessStaticAll extends Thread {

    private LotteryResult oneLotte = null;

    public ProcessStaticAll() {
    }

    public ProcessStaticAll(LotteryResult oneLotte) {
        this.oneLotte = oneLotte;
    }

    @Override
    public void run() {
        try {
            if (oneLotte != null && oneLotte.getSpecial() != null && !oneLotte.getSpecial().equals("")) {
                insertStatistic(oneLotte);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                sleep(5 * 60000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private void insertStatistic(LotteryResult oneLotte) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = XSDBPool.getConnection();
            String sql = "SELECT COUNT(*) FROM STATISTIC_ALL WHERE PRIZE = ? and DATE_FORMAT(OPEN_DATE,'%d/%m/%Y') = DATE_FORMAT(?,'%d/%m/%Y')"
                    + " and upper(CODE) = upper(?)";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, 0);
            pstm.setTimestamp(2, oneLotte.getOpenDate());
            pstm.setString(3, oneLotte.getCode());
            rs = pstm.executeQuery();
            int tem = 1;
            if (rs.next()) {
                tem = rs.getInt(1);
            }
            if (tem == 0) {
                XSDBPool.releadRsPstm(rs, pstm);
                ProcessStaticAll statisticDao = new ProcessStaticAll();
                // 1
                String[] arrFirst = buildStringStatistic(oneLotte.getFirst());
                statisticDao.insertPrize(1, oneLotte.getOpenDate(), arrFirst, oneLotte.getCode());
                //2
                String[] arrSecond = buildStringStatistic(oneLotte.getSecond());
                statisticDao.insertPrize(2, oneLotte.getOpenDate(), arrSecond, oneLotte.getCode());
                //3
                String[] arrThird = buildStringStatistic(oneLotte.getThird());
                statisticDao.insertPrize(3, oneLotte.getOpenDate(), arrThird, oneLotte.getCode());
                //4
                String[] arrFourth = buildStringStatistic(oneLotte.getFourth());
                statisticDao.insertPrize(4, oneLotte.getOpenDate(), arrFourth, oneLotte.getCode());
                //5
                String[] arrFifth = buildStringStatistic(oneLotte.getFifth());
                statisticDao.insertPrize(5, oneLotte.getOpenDate(), arrFifth, oneLotte.getCode());
                //6
                String[] arrSixth = buildStringStatistic(oneLotte.getSixth());
                statisticDao.insertPrize(6, oneLotte.getOpenDate(), arrSixth, oneLotte.getCode());
                //7
                String[] arrSeven = buildStringStatistic(oneLotte.getSeventh());
                statisticDao.insertPrize(7, oneLotte.getOpenDate(), arrSeven, oneLotte.getCode());
                //8
                if (oneLotte.getEighth() != null && !oneLotte.getEighth().equals("")) {
                    String[] arrEighth = buildStringStatistic(oneLotte.getEighth());
                    statisticDao.insertPrize(8, oneLotte.getOpenDate(), arrEighth, oneLotte.getCode());
                }
                // DB
                String[] arrSpecial = buildStringStatistic(oneLotte.getSpecial());
                statisticDao.insertPrize(0, oneLotte.getOpenDate(), arrSpecial, oneLotte.getCode());
                System.out.println("INSERT THANH CONG STATISTIC_ALL:" + oneLotte.getOpenDate() + "-CODE:" + oneLotte.getCode());
            } else {
                System.out.println("DA CO STATISTIC_ALL:" + oneLotte.getOpenDate() + "-COE:" + oneLotte.getCode());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            XSDBPool.freeConn(rs, pstm, conn);
        }
    }

    private void insertPrize(int prize, Timestamp openDate, String[] all, String code) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            if (all != null && all.length > 0) {
                conn = XSDBPool.getConnection();
                String sql = "INSERT INTO STATISTIC_ALL(PRIZE,OPEN_DATE,S_NUMBER,CODE)"
                        + "VALUES(?,?,?,?)";
                for (String one : all) {
                    pstm = conn.prepareStatement(sql);
                    pstm.setInt(1, prize);
                    pstm.setTimestamp(2, openDate);
                    pstm.setString(3, one);
                    pstm.setString(4, code);
                    pstm.execute();
                    //--
                    pstm.clearParameters();
                    pstm.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            XSDBPool.freeConn(rs, pstm, conn);
        }
    }

    private static String[] buildStringStatistic(String str) {
        if (str == null || str.equals("")) {
            return null;
        } else {
            String[] arrStr = str.split("-");
            if (arrStr == null || arrStr.length == 0) {
                return null;
            } else {
                for (int i = 0; i < arrStr.length; i++) {
                    if (arrStr[i] != null) {
                        arrStr[i] = arrStr[i].trim();
                        if (arrStr[i].length() > 2) {
                            arrStr[i] = arrStr[i].substring(arrStr[i].length() - 2, arrStr[i].length());
                        }
                    }
                }
                return arrStr;
            }
        }
    }

    public void ProcessStatisticALL() {
        ArrayList<LotteryResult> all = new ProcessStaticAll().getAllLotteryResult();
        try {
            int i = 0;
            for (LotteryResult one : all) {
                i++;
                ProcessStaticAll oneThread = new ProcessStaticAll(one);
                sleep(50);
                oneThread.start();
            }
            System.out.println("Xu ly het:" + i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<LotteryResult> getAllLotteryResult() {
        ArrayList<LotteryResult> all = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM LOTTERY_RESULT ORDER BY OPEN_DATE DESC";
        try {
            conn = XSDBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                LotteryResult one = new LotteryResult();
                one.setId(rs.getInt("ID"));
                one.setCode(rs.getString("CODE"));
                one.setOpenDate(rs.getTimestamp("OPEN_DATE"));
                one.setSpecial(rs.getString("SPECIAL"));
                one.setFirst(rs.getString("FIRST"));
                one.setSecond(rs.getString("SECOND"));
                one.setThird(rs.getString("THIRD"));
                one.setFourth(rs.getString("FOURTH"));
                one.setFifth(rs.getString("FIFTH"));
                one.setSixth(rs.getString("SIXTH"));
                one.setSeventh(rs.getString("SEVENTH"));
                one.setEighth(rs.getString("EIGHTH"));
                all.add(one);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            XSDBPool.freeConn(rs, pstm, conn);
        }return all;
    }
}
