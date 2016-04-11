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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author centurion
 */
public class LotteryLive implements Serializable {

    private static final long serialVersionUID = 1L;

    public boolean insertResultLive(LotteryResult result) {
        boolean flag = false;
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        String sqlCheck = "SELECT count(*) FROM LOTTERY_RESULT_LIVE WHERE "
                + "DATE_FORMAT(OPEN_DATE,'%d/%m/%Y') = DATE_FORMAT(now(), '%d/%m/%Y') "
                + " AND UPPER(CODE) = UPPER(?) AND PRIZE = ?";
        int prizeNum = 7;
        if (LotteryUtils.checkCodeisMB(result.getCode())) {
            prizeNum = 7;
        } else {
            prizeNum = 8;
        }
        try {
            // Giai MB bat dau tu 1
            conn = XSDBPool.getConnection();
            for (int i = 0; i <= prizeNum; i++) {
                // Check Exist Result
                pstm = conn.prepareStatement(sqlCheck);
                pstm.setString(1, result.getCode());
                // Giai bat dau tu 1
                pstm.setInt(2, i);
                rs = pstm.executeQuery();
                //----------------
                if (rs.next()) {
                    int tem = rs.getInt(1);
                    XSDBPool.releadRsPstm(rs, pstm);
                    if (tem == 0) {
                        // Chua co thi insert
                        String sqlin = "INSERT INTO LOTTERY_RESULT_LIVE(CODE,OPEN_DATE,PRIZE,S_NUMBER)"
                                + "VALUES(?,now(),?,?) ";
                        pstm = conn.prepareStatement(sqlin);
                        pstm.setString(1, result.getCode());
                        pstm.setInt(2, i);
                        pstm.setString(3, getSnumber(result, i));
                        if (pstm.executeUpdate() == 1) {
                            flag = true;
                        }
                    } else {
                        // Co roi thi Update
                        String sqlup = "UPDATE LOTTERY_RESULT_LIVE SET S_NUMBER = ? "
                                + " WHERE DATE_FORMAT(OPEN_DATE,'%d/%m/%Y') = DATE_FORMAT(now(), '%d/%m/%Y') "
                                + " AND UPPER(CODE) = UPPER(?) AND PRIZE = ?";
                        pstm = conn.prepareStatement(sqlup);
                        pstm.setString(1, getSnumber(result, i));
                        pstm.setString(2, result.getCode());
                        pstm.setInt(3, i);
                        pstm.execute();
                    }
                    XSDBPool.releadRsPstm(rs, pstm);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            XSDBPool.freeConn(rs, pstm, conn);
        }return flag;
    }
    static final HashMap<Integer, Integer> GET_GIAI_MT_MN = new HashMap<>();
    static final HashMap<Integer, Integer> GET_GIAI_MB = new HashMap<>();

    static {
        GET_GIAI_MT_MN.put(0, 8);
        GET_GIAI_MT_MN.put(1, 7);
        GET_GIAI_MT_MN.put(2, 6);
        GET_GIAI_MT_MN.put(3, 5);
        GET_GIAI_MT_MN.put(4, 4);
        GET_GIAI_MT_MN.put(5, 3);
        GET_GIAI_MT_MN.put(6, 2);
        GET_GIAI_MT_MN.put(7, 1);
        GET_GIAI_MT_MN.put(8, 0);
        //MB
        GET_GIAI_MB.put(0, 1);
        GET_GIAI_MB.put(1, 2);
        GET_GIAI_MB.put(2, 3);
        GET_GIAI_MB.put(3, 4);
        GET_GIAI_MB.put(4, 5);
        GET_GIAI_MB.put(5, 6);
        GET_GIAI_MB.put(6, 7);
        GET_GIAI_MB.put(7, 0);
    }

    private static String getSnumber(LotteryResult result, int i) {
        String str = "";
        switch (i) {
            case 0:
                str = result.getSpecial();
                break;
            case 1:
                str = result.getFirst();
                break;
            case 2:
                str = result.getSecond();
                break;
            case 3:
                str = result.getThird();
                break;
            case 4:
                str = result.getFourth();
                break;
            case 5:
                str = result.getFifth();
                break;
            case 6:
                str = result.getSixth();
                break;
            case 7:
                str = result.getSeventh();
                break;
            case 8:
                str = result.getEighth();
                break;
            default:
                break;
        }
        return str;

    }

    public static String[] buildLotteryLive(LotteryResult result) {
        String[] lot = null;
        if (LotteryUtils.checkCodeisMB(result.getCode())) {
            int i = 0;
            if (result.getFirst() != null && result.getFirst().length() > 1) {
                i++;
            }
            if (result.getSecond() != null && result.getSecond().length() > 1) {
                i++;
            }
            if (result.getThird() != null && result.getThird().length() > 1) {
                i++;
            }
            if (result.getFourth() != null && result.getFourth().length() > 1) {
                i++;
            }
            if (result.getFifth() != null && result.getFifth().length() > 1) {
                i++;
            }
            if (result.getSixth() != null && result.getSixth().length() > 1) {
                i++;
            }
            if (result.getSeventh() != null && result.getSeventh().length() > 1) {
                i++;
            }
            if (result.getSpecial() != null && result.getSpecial().length() > 1) {
                i++;
            }
            if (i > 0) {
                lot = new String[i];
            }
            for (int j = 0; j < lot.length; j++) {
                if (j == 0) {
                    lot[0] = result.getFirst();
                }
                if (j == 1) {
                    lot[j] = result.getSecond();
                }
                if (j == 2) {
                    lot[j] = result.getThird();
                }
                if (j == 3) {
                    lot[j] = result.getFourth();
                }
                if (j == 4) {
                    lot[j] = result.getFifth();
                }
                if (j == 5) {
                    lot[j] = result.getSixth();
                }
                if (j == 6) {
                    lot[j] = result.getSeventh();
                }
                if (j == 7) {
                    lot[j] = result.getSpecial();
                }
            }
        } else {
            int i = 0;

            if (result.getEighth() != null && result.getEighth().length() > 1) {
                i++;
            }
            if (result.getSeventh() != null && result.getSeventh().length() > 1) {
                i++;
            }
            if (result.getSixth() != null && result.getSixth().length() > 1) {
                i++;
            }
            if (result.getFifth() != null && result.getFifth().length() > 1) {
                i++;
            }
            if (result.getFourth() != null && result.getFourth().length() > 1) {
                i++;
            }
            if (result.getThird() != null && result.getThird().length() > 1) {
                i++;
            }
            if (result.getSecond() != null && result.getSecond().length() > 1) {
                i++;
            }
            if (result.getFirst() != null && result.getFirst().length() > 1) {
                i++;
            }
            if (result.getSpecial() != null && result.getSpecial().length() > 1) {
                i++;
            }
            if (i > 0) {
                lot = new String[i];
            }
            for (int j = 0; j < lot.length; j++) {
                if (j == 0) {
                    lot[0] = result.getEighth();
                }
                if (j == 1) {
                    lot[j] = result.getSeventh();
                }
                if (j == 2) {
                    lot[j] = result.getSixth();
                }
                if (j == 3) {
                    lot[j] = result.getFifth();
                }
                if (j == 4) {
                    lot[j] = result.getFourth();
                }
                if (j == 5) {
                    lot[j] = result.getThird();
                }
                if (j == 6) {
                    lot[j] = result.getSecond();
                }
                if (j == 7) {
                    lot[j] = result.getFirst();
                }
                if (j == 8) {
                    lot[j] = result.getSpecial();
                }
            }
        }
        return lot;
    }

//    public static void cacheLiveResult(String code, String[] lot) {
//        ArrayList<LotteryLive> all = buildLotteryLive(code, lot);
//        String sCurrDay = DateProc.Timestamp2DDMMYYYY(DateProc.createTimestamp());
//        sCurrDay = sCurrDay.replaceAll("/", "_");
//        File f = new File(Constants.PATH_CACHE_XS_LIVE_KETQUA888 + "/" + code + "_" + sCurrDay + ".live");
//        FileOutputStream fout = null;
//        ObjectOutput objOut = null;
//        try {
//            fout = new FileOutputStream(f);
//            objOut = new ObjectOutputStream(fout);
//            objOut.writeObject(all);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (objOut != null) {
//                    objOut.close();
//                }
//                if (fout != null) {
//                    fout.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
    private static ArrayList<LotteryLive> buildLotteryLive(String code, String[] lot) {
        ArrayList<LotteryLive> all = new ArrayList<>();
        if (LotteryUtils.checkCodeisMB(code)) {
            for (int i = 0; i < lot.length; i++) {
                LotteryLive one = new LotteryLive();
                one.setCode(code);
                one.setPrize(GET_GIAI_MB.get(i));
                one.setS_number(lot[i]);
                all.add(one);
            }
        } else {
            for (int i = 0; i < lot.length; i++) {
                LotteryLive one = new LotteryLive();
                one.setCode(code);
                one.setPrize(GET_GIAI_MT_MN.get(i));
                one.setS_number(lot[i]);
                all.add(one);
            }
        }
        return all;
    }
    //-----------
    private long id;
    private String code;
    private Timestamp open_date;
    private int prize;
    private String s_number;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getOpen_date() {
        return open_date;
    }

    public void setOpen_date(Timestamp open_date) {
        this.open_date = open_date;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public String getS_number() {
        return s_number;
    }

    public void setS_number(String s_number) {
        this.s_number = s_number;
    }
}
