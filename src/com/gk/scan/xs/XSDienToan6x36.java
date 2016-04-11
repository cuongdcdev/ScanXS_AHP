/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gk.scan.xs;

import com.gk.db.XSDBPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 *
 * @author TUANPLA
 */
public class XSDienToan6x36 {

    public boolean insertNew6x36(XSDienToan6x36 one) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sqlCheck = "SELECT count(*) FROM result_6x36 WHERE DATE_FORMAT(OPEN_DATE,'%d/%m/%Y') =DATE_FORMAT(NOW(),'%d/%m/%Y') ";
        try {
            conn = XSDBPool.getConnection();
            pstm = conn.prepareStatement(sqlCheck);
            rs = pstm.executeQuery();
            if (rs.next()) {
                int tem = rs.getInt(1);
                XSDBPool.releadRsPstm(rs, pstm);
                if (tem == 0) {
                    // Chua co
                    String sql = "INSERT INTO result_6x36(FIRST,SECOND,THIRD,FOUR,FIVE,SIX,OPEN_DATE)"
                            + "VALUES(?,?,?,?,?,?,NOW())";
                    pstm = conn.prepareStatement(sql);
                    int i = 1;
                    pstm.setString(i++, one.getGiai1());
                    pstm.setString(i++, one.getGiai2());
                    pstm.setString(i++, one.getGiai3());
                    pstm.setString(i++, one.getGiai4());
                    pstm.setString(i++, one.getGiai5());
                    pstm.setString(i++, one.getGiai6());
                    if (pstm.executeUpdate() == 1) {
                        flag = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            XSDBPool.freeConn(rs, pstm, conn);
        }return flag;
    }

    public String getGiai1() {
        return giai1;
    }

    public void setGiai1(String giai1) {
        this.giai1 = giai1;
    }

    public String getGiai2() {
        return giai2;
    }

    public void setGiai2(String giai2) {
        this.giai2 = giai2;
    }

    public String getGiai3() {
        return giai3;
    }

    public void setGiai3(String giai3) {
        this.giai3 = giai3;
    }

    public String getGiai4() {
        return giai4;
    }

    public void setGiai4(String giai4) {
        this.giai4 = giai4;
    }

    public String getGiai5() {
        return giai5;
    }

    public void setGiai5(String giai5) {
        this.giai5 = giai5;
    }

    public String getGiai6() {
        return giai6;
    }

    public void setGiai6(String giai6) {
        this.giai6 = giai6;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Timestamp openDate) {
        this.openDate = openDate;
    }
    int id;
    Timestamp openDate;
    String giai1;
    String giai2;
    String giai3;
    String giai4;
    String giai5;
    String giai6;
}
