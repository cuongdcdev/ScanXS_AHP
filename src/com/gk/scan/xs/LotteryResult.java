/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gk.scan.xs;

import com.gk.db.XSDBPool;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author PLATUAN
 */
public class LotteryResult implements Serializable {

    private static final long serialVersionUID = 2L;

    public boolean insertResultMB(LotteryResult oneResult) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) FROM LOTTERY_RESULT WHERE DATE_FORMAT(OPEN_DATE,'%d/%m/%Y') = DATE_FORMAT(?,'%d/%m/%Y') "
                + " AND (UPPER(CODE) = UPPER('BN') OR UPPER(CODE) = UPPER('HP') OR UPPER(CODE) = UPPER('ND') OR UPPER(CODE) = UPPER('QN') "
                + " OR UPPER(CODE) = UPPER('TB') OR UPPER(CODE) = UPPER('TD') OR UPPER(CODE) = UPPER('MB'))";
        try {
            conn = XSDBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setTimestamp(1, oneResult.getOpenDate());
            rs = pstm.executeQuery();
            if (rs.next()) {
                int tem = rs.getInt(1);
                if (tem == 0) {
                    // INSERT --
                    XSDBPool.releadRsPstm(rs, pstm);
                    sql = "INSERT INTO LOTTERY_RESULT(CODE,OPEN_DATE,PRIZE,SPECIAL,FIRST,SECOND,THIRD,FOURTH,FIFTH,SIXTH,SEVENTH,EIGHTH,LAST_UPDATE,ZONE)"
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,now(),?)";
                    pstm = conn.prepareStatement(sql);
                    int i = 1;
                    pstm.setString(i++, oneResult.getCode());
                    pstm.setTimestamp(i++, oneResult.getOpenDate());
                    pstm.setInt(i++, oneResult.getPrize());
                    pstm.setString(i++, oneResult.getSpecial());
                    pstm.setString(i++, oneResult.getFirst());
                    pstm.setString(i++, LotteryUtils.validStringLotteryRequest(oneResult.getSecond()));
                    pstm.setString(i++, LotteryUtils.validStringLotteryRequest(oneResult.getThird()));
                    pstm.setString(i++, LotteryUtils.validStringLotteryRequest(oneResult.getFourth()));
                    pstm.setString(i++, LotteryUtils.validStringLotteryRequest(oneResult.getFifth()));
                    pstm.setString(i++, LotteryUtils.validStringLotteryRequest(oneResult.getSixth()));
                    pstm.setString(i++, LotteryUtils.validStringLotteryRequest(oneResult.getSeventh()));
                    pstm.setString(i++, oneResult.getEighth());
                    pstm.setString(i++, LotteryUtils.LOTT_XSMB);
                    pstm.execute();
                    flag = true;
                } else {
                    System.out.println("DA CO KET QUA NGAY :" + oneResult.getOpenDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            XSDBPool.freeConn(rs, pstm, conn);
        }
        return flag;
    }

    /**
     *
     * @param oneResult
     * @return
     */
    public boolean insertResult(LotteryResult oneResult) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) FROM LOTTERY_RESULT "
                + " WHERE DATE_FORMAT(OPEN_DATE,'%d/%m/%Y') = DATE_FORMAT(?,'%d/%m/%Y') "
                + " AND UPPER(CODE) = UPPER(?) ";
        try {
            String zone = "";
            if (LotteryUtils.checkCodeisMN(oneResult.getCode())) {
                zone = LotteryUtils.LOTT_XSMN;
            } else if (LotteryUtils.checkCodeisMT(oneResult.getCode())) {
                zone = LotteryUtils.LOTT_XSMT;
            }
            conn = XSDBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setTimestamp(1, oneResult.getOpenDate());
            pstm.setString(2, oneResult.getCode());
            rs = pstm.executeQuery();
            if (rs.next()) {
                int tem = rs.getInt(1);
                if (tem == 0) {
                    // INSERT --
                    XSDBPool.releadRsPstm(rs, pstm);
                    sql = "INSERT INTO LOTTERY_RESULT(CODE,OPEN_DATE,PRIZE,SPECIAL,FIRST,SECOND,THIRD,FOURTH,FIFTH,"
                            + "SIXTH,SEVENTH,EIGHTH,LAST_UPDATE,ZONE)"
                            + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,now(),?)";
                    pstm = conn.prepareStatement(sql);
                    int i = 1;
                    pstm.setString(i++, oneResult.getCode());
                    pstm.setTimestamp(i++, oneResult.getOpenDate());
                    pstm.setInt(i++, oneResult.getPrize());
                    pstm.setString(i++, oneResult.getSpecial());
                    pstm.setString(i++, oneResult.getFirst());
                    pstm.setString(i++, oneResult.getSecond());
                    pstm.setString(i++, oneResult.getThird());
                    pstm.setString(i++, oneResult.getFourth());
                    pstm.setString(i++, oneResult.getFifth());
                    pstm.setString(i++, oneResult.getSixth());
                    pstm.setString(i++, oneResult.getSeventh());
                    pstm.setString(i++, oneResult.getEighth());
                    pstm.setString(i++, zone);
                    pstm.execute();
                    flag = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            XSDBPool.freeConn(rs, pstm, conn);
        }
        return flag;
    }

    public static void updateZone() {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sql = "SELECT CODE,ID FROM LOTTERY_RESULT";
        try {
            conn = XSDBPool.getConnection();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                String code = rs.getString("CODE");
                int id = rs.getInt("ID");
                String zone = "";
                if (LotteryUtils.checkCodeisMB(code)) {
                    zone = LotteryUtils.LOTT_XSMB;
                }
                if (LotteryUtils.checkCodeisMN(code)) {
                    zone = LotteryUtils.LOTT_XSMN;
                }
                if (LotteryUtils.checkCodeisMT(code)) {
                    zone = LotteryUtils.LOTT_XSMT;
                }
                try (PreparedStatement pstm1 = conn.prepareStatement("UPDATE LOTTERY_RESULT SET ZONE = ? WHERE ID = ? ")) {
                    pstm1.setString(1, zone);
                    pstm1.setInt(2, id);
                    pstm1.execute();
                    System.out.println("UPDATE CODE :" + code + " IS " + zone);
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
            }
            System.out.println("====> UPDATE ZONE COMPLETED......");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            XSDBPool.freeConn(rs, pstm, conn);
        }
    }

    public LotteryResult() {
    }
    private int id;
    private String code;
    private Timestamp openDate;
    private int prize;
    private String special;
    private String first;
    private String second;
    private String third;
    private String fourth;
    private String fifth;
    private String sixth;
    private String seventh;
    private String eighth;
    private Timestamp lastUpdate;
    private String zone;

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEighth() {
        return eighth;
    }

    public void setEighth(String eighth) {
        this.eighth = eighth;
    }

    public String getFifth() {
        return fifth;
    }

    public void setFifth(String fifth) {
        this.fifth = fifth;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getFourth() {
        return fourth;
    }

    public void setFourth(String fourth) {
        this.fourth = fourth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Timestamp getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Timestamp openDate) {
        this.openDate = openDate;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getSeventh() {
        return seventh;
    }

    public void setSeventh(String seventh) {
        this.seventh = seventh;
    }

    public String getSixth() {
        return sixth;
    }

    public void setSixth(String sixth) {
        this.sixth = sixth;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
