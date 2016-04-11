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
public class XSDienToan123 {

    public boolean insertNew123(XSDienToan123 one) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sqlCheck = "SELECT count(*) FROM result_123 WHERE DATE_FORMAT(OPEN_DATE,'%d/%m/%Y') =DATE_FORMAT(NOW(),'%d/%m/%Y') ";
        try {
            conn = XSDBPool.getConnection();
            pstm = conn.prepareStatement(sqlCheck);
            rs = pstm.executeQuery();
            if (rs.next()) {
                int tem = rs.getInt(1);
                XSDBPool.releadRsPstm(rs, pstm);
                if (tem == 0) {
                    // Chua co
                    String sql = "INSERT INTO result_123(FIRST,SECOND,THIRD,OPEN_DATE)"
                            + "VALUES(?,?,?,NOW())";
                    pstm = conn.prepareStatement(sql);
                    int i = 1;
                    pstm.setString(i++, one.getGiai1());
                    pstm.setString(i++, one.getGiai2());
                    pstm.setString(i++, one.getGiai3());
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
}
