/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gk.scan.xs;

import com.gk.utils.DateProc;
import com.gk.utils.Tool;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Ngoc
 */
public class GetDataMinhNgoc {

    private static final HashMap<String, String> MAP_MINHNGOC_CODE = new HashMap<>();

    static {
        MAP_MINHNGOC_CODE.put("T1", "HCM");
        MAP_MINHNGOC_CODE.put("T2", "DT");
        MAP_MINHNGOC_CODE.put("T3", "CM");

        MAP_MINHNGOC_CODE.put("T7", "BTR");
        MAP_MINHNGOC_CODE.put("T8", "VT");
        MAP_MINHNGOC_CODE.put("T9", "BL");
        MAP_MINHNGOC_CODE.put("T10", "DNI");
        MAP_MINHNGOC_CODE.put("T11", "CT");
        MAP_MINHNGOC_CODE.put("T12", "ST");
        MAP_MINHNGOC_CODE.put("T13", "TN");
        MAP_MINHNGOC_CODE.put("T14", "AG");
        MAP_MINHNGOC_CODE.put("T15", "BTH");
        MAP_MINHNGOC_CODE.put("T16", "VL");
        MAP_MINHNGOC_CODE.put("T17", "BD");
        MAP_MINHNGOC_CODE.put("T18", "TRV");
        MAP_MINHNGOC_CODE.put("T19", "LA");
        MAP_MINHNGOC_CODE.put("T20", "HG");
        MAP_MINHNGOC_CODE.put("T21", "BP");
        MAP_MINHNGOC_CODE.put("T22", "TG");
        MAP_MINHNGOC_CODE.put("T23", "KG");
        MAP_MINHNGOC_CODE.put("T24", "LD");
        MAP_MINHNGOC_CODE.put("T26", "TTH");
        MAP_MINHNGOC_CODE.put("T27", "PY");
        MAP_MINHNGOC_CODE.put("T28", "QNM");
        MAP_MINHNGOC_CODE.put("T29", "DLK");
        MAP_MINHNGOC_CODE.put("T30", "DNG");
        MAP_MINHNGOC_CODE.put("T31", "KH");
        MAP_MINHNGOC_CODE.put("T32", "BDI");
        MAP_MINHNGOC_CODE.put("T33", "QT");
        MAP_MINHNGOC_CODE.put("T34", "QB");
        MAP_MINHNGOC_CODE.put("T35", "GL");
        MAP_MINHNGOC_CODE.put("T36", "NT");
        MAP_MINHNGOC_CODE.put("T37", "QNG");
        MAP_MINHNGOC_CODE.put("T38", "DNO");
        MAP_MINHNGOC_CODE.put("T39", "KT");

        MAP_MINHNGOC_CODE.put("T46", "MB");
        MAP_MINHNGOC_CODE.put("T47", "MB");
        MAP_MINHNGOC_CODE.put("T48", "MB");
        MAP_MINHNGOC_CODE.put("T50", "MB");
        MAP_MINHNGOC_CODE.put("T51", "MB");
    }

    private static final String URL_MN_LIVE = "http://server2.xosominhngoc.com/xstt/MN/MN.php?visit=1&_=";
    private static final String URL_MN_OFF = "http://server2.xosominhngoc.com/xstt/MN/MN.php?visit=0&_=";

    private static final String URL_MT_LIVE = "http://server2.xosominhngoc.com/xstt/MT/MT.php?visit=1&_=";
    private static final String URL_MT_OFF = "http://server2.xosominhngoc.com/xstt/MT/MT.php?visit=0&_=";
    //--
    private static final String URL_MB_LIVE = "http://server2.xosominhngoc.com/xstt/MB/MB.php?visit=1&_=";
    private static final String URL_MB_OFF = "http://server2.xosominhngoc.com/xstt/MB/MB.php?visit=0&_=";
    // Ko truc tiep 
    //http://ww2.minhngoc.net.vn/xstt/MB/MB.php?visit=0&_=1438272125555

    public HashMap<String, String> MAP_RESULT_MB = new HashMap<>();

    public LotteryResult getDataMB() {
        LotteryResult result = null;
        try {
            URL url = new URL(URL_MB_LIVE + System.currentTimeMillis());
            double hm = DateProc.getTimer();
            if (hm > 18.6) {
                url = new URL(URL_MB_OFF + System.currentTimeMillis());
            }
            //-----------------
//            InputStream stream = url.openStream();
            URLConnection ucconn = url.openConnection();
            ucconn.setRequestProperty("User-Agent", USER_AGENT[buildRandomUserAgent()]);

            try (InputStream ipst = ucconn.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(ipst, "UTF-8"))) {
                String line = "";
                String info = "";
                while (line != null) {
                    if (line.length() > 0) {
                        info += line.trim();
                    }
                    line = reader.readLine();
                }
                info = info.replaceAll("\"", "");
                info = info.replaceAll("kqxs", "");
                info = info.replaceAll("\\*", "");
                info = info.replaceAll("\\+", "");
                info = info.replaceAll("\\[", "");
                info = info.replaceAll("\\]", "");

//                System.out.println(info);
                result = new LotteryResult();
                String[] arr = info.split(";");
                MAP_RESULT_MB.clear();
                for (String one : arr) {
                    String[] key_val = processArr(one);
                    MAP_RESULT_MB.put(key_val[0].toUpperCase(), key_val[1]);
                }
                String matinhMN = MAP_RESULT_MB.get("LISTTINHNEW");
                String code = LotteryUtils.getMainCompany();
                result.setCode(code);
                result.setOpenDate(DateProc.createTimestamp());
//                //G7
                String seventh = "";
                seventh += MAP_RESULT_MB.get("T" + matinhMN + "_G7_1") + "-";
                seventh += MAP_RESULT_MB.get("T" + matinhMN + "_G7_2") + "-";
                seventh += MAP_RESULT_MB.get("T" + matinhMN + "_G7_3") + "-";
                seventh += MAP_RESULT_MB.get("T" + matinhMN + "_G7_4");
                result.setSeventh(seventh);
                //G6
                String sixth = "";
                sixth += MAP_RESULT_MB.get("T" + matinhMN + "_G6_1") + "-";
                sixth += MAP_RESULT_MB.get("T" + matinhMN + "_G6_2") + "-";
                sixth += MAP_RESULT_MB.get("T" + matinhMN + "_G6_3");
                result.setSixth(sixth);
                //G5
                String fifth = "";
                fifth += MAP_RESULT_MB.get("T" + matinhMN + "_G5_1") + "-";
                fifth += MAP_RESULT_MB.get("T" + matinhMN + "_G5_2") + "-";
                fifth += MAP_RESULT_MB.get("T" + matinhMN + "_G5_3") + "-";
                fifth += MAP_RESULT_MB.get("T" + matinhMN + "_G5_4") + "-";
                fifth += MAP_RESULT_MB.get("T" + matinhMN + "_G5_5") + "-";
                fifth += MAP_RESULT_MB.get("T" + matinhMN + "_G5_6");
                result.setFifth(fifth);
                //G4
                String fouth = "";
                fouth += MAP_RESULT_MB.get("T" + matinhMN + "_G4_1") + "-";
                fouth += MAP_RESULT_MB.get("T" + matinhMN + "_G4_2") + "-";
                fouth += MAP_RESULT_MB.get("T" + matinhMN + "_G4_3") + "-";
                fouth += MAP_RESULT_MB.get("T" + matinhMN + "_G4_4");
                result.setFourth(fouth);
                //G3
                String thirth = "";
                thirth += MAP_RESULT_MB.get("T" + matinhMN + "_G3_1") + "-";
                thirth += MAP_RESULT_MB.get("T" + matinhMN + "_G3_2") + "-";
                thirth += MAP_RESULT_MB.get("T" + matinhMN + "_G3_3") + "-";
                thirth += MAP_RESULT_MB.get("T" + matinhMN + "_G3_4") + "-";
                thirth += MAP_RESULT_MB.get("T" + matinhMN + "_G3_5") + "-";
                thirth += MAP_RESULT_MB.get("T" + matinhMN + "_G3_6");
                result.setThird(thirth);
                //G2
                String second = "";
                second += MAP_RESULT_MB.get("T" + matinhMN + "_G2_1") + "-";
                second += MAP_RESULT_MB.get("T" + matinhMN + "_G2_2");
                result.setSecond(second);
                //G1
                String first = "";
                first += MAP_RESULT_MB.get("T" + matinhMN + "_G1");
                if (Tool.checkNull(first)) {
                    first = "-";
                }
                result.setFirst(first);
                // DB
                String special = "";
                special += MAP_RESULT_MB.get("T" + matinhMN + "_GDB");
                if (Tool.checkNull(special)) {
                    special = "-";
                }
                result.setSpecial(special);
                result.setLastUpdate(DateProc.createTimestamp());
                //--->
//                System.out.println("==>43644 - 63123 - 18142 - 80228 - 29500 - 69838" + seventh);
                System.out.println("1:" + result.getFirst());
                System.out.println("2:" + result.getSecond());
                System.out.println("3:" + result.getThird());
                System.out.println("4:" + result.getFourth());
                System.out.println("5:" + result.getFifth());
                System.out.println("6:" + result.getSixth());
                System.out.println("7:" + result.getSeventh());
                System.out.println("0:" + result.getSpecial());

                getDienToan123();
                getDienToan6x36();
                getDienToanTT();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public boolean getDienToanTT() {
        boolean flag = false;
        if (MAP_RESULT_MB.get("DTTHANTAI4").equalsIgnoreCase("true")) {
            System.out.println("Co ket qua Dien Toan DTTHANTAI4");
            XSThanTai4 one = new XSThanTai4();
            one.setGiai1(MAP_RESULT_MB.get("TDTTT4_G1"));
            flag = one.insertDTthantai(one);
        }
        return flag;
    }

    public boolean getDienToan6x36() {
        boolean flag = false;
        if (MAP_RESULT_MB.get("DT6X36").equalsIgnoreCase("true")) {
            XSDienToan6x36 one = new XSDienToan6x36();
            one.setGiai1(MAP_RESULT_MB.get("TDT6X36_G1"));
            one.setGiai2(MAP_RESULT_MB.get("TDT6X36_G2"));
            one.setGiai3(MAP_RESULT_MB.get("TDT6X36_G3"));
            one.setGiai4(MAP_RESULT_MB.get("TDT6X36_G4"));
            one.setGiai5(MAP_RESULT_MB.get("TDT6X36_G5"));
            one.setGiai6(MAP_RESULT_MB.get("TDT6X36_G6"));
            flag = one.insertNew6x36(one);
        }
        return flag;
    }

    public boolean getDienToan123() {
        boolean flag = false;
        if (MAP_RESULT_MB.get("DT123").equalsIgnoreCase("true")) {
            XSDienToan123 one = new XSDienToan123();
            one.setGiai1(MAP_RESULT_MB.get("TDT123_G1"));
            one.setGiai2(MAP_RESULT_MB.get("TDT123_G2"));
            one.setGiai3(MAP_RESULT_MB.get("TDT123_G3"));
            one.setOpenDate(DateProc.createTimestamp());
            flag = one.insertNew123(one);
        }
        return flag;
    }
    public HashMap<String, String> MAP_RESULT_MN = new HashMap<>();

    public ArrayList<LotteryResult> getDataMNam() {
        ArrayList<LotteryResult> all = new ArrayList<>();
        try {
            URL url = new URL(URL_MN_LIVE + System.currentTimeMillis());
            double hm = DateProc.getTimer();
            if (hm > 16.6) {
                url = new URL(URL_MN_OFF + System.currentTimeMillis());
            }
            //-----------------
            URLConnection ucconn = url.openConnection();
            ucconn.setRequestProperty("User-Agent", USER_AGENT[buildRandomUserAgent()]);
            try (InputStream ipst = ucconn.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(ipst, "UTF-8"))) {
                String line = "";
                String info = "";
                while (line != null) {
                    if (line.length() > 0) {
                        info += line.trim();
                    }
                    line = reader.readLine();
                }
                info = info.replaceAll("\"", "");
                info = info.replaceAll("kqxs", "");
                info = info.replaceAll("\\*", "");
                info = info.replaceAll("\\+", "");
                info = info.replaceAll("\\[", "");
                info = info.replaceAll("\\]", "");
                String[] arr = info.split(";");
//                System.out.println(info);
                MAP_RESULT_MN.clear();
                for (String one : arr) {
                    String[] key_val = processArr(one);
                    MAP_RESULT_MN.put(key_val[0].toUpperCase(), key_val[1]);
                }
//                Set<String> key = MAP_RESULT_MN.keySet();
//                for (String one : key) {
//                    System.out.println(one + "=" + MAP_RESULT_MN.get(one));
//                }
                String tinh = MAP_RESULT_MN.get("LISTTINHNEW");
                if (tinh == null) {
                    tinh = "";
                }
//                System.out.println(tinh);
                String[] arrTinh = tinh.split(",");
                if (arrTinh != null && arrTinh.length > 0) {
                    for (String oneTinh : arrTinh) {
                        LotteryResult result = new LotteryResult();
                        // Ma Tinh
                        result.setCode(MAP_MINHNGOC_CODE.get("T" + oneTinh));
                        result.setOpenDate(DateProc.createTimestamp());
                        // G8
                        String eighth = "";
                        eighth += MAP_RESULT_MN.get("T" + oneTinh + "_G8");
                        if (Tool.checkNull(eighth)) {
                            eighth = "-";
                        }
                        result.setEighth(eighth);
                        //G7
                        String seventh = "";
                        seventh += MAP_RESULT_MN.get("T" + oneTinh + "_G7");
                        if (Tool.checkNull(seventh)) {
                            seventh = "-";
                        }
                        result.setSeventh(seventh);
                        //G6
                        String sixth = "";
                        sixth += MAP_RESULT_MN.get("T" + oneTinh + "_G6_1") + "-";
                        sixth += MAP_RESULT_MN.get("T" + oneTinh + "_G6_2") + "-";
                        sixth += MAP_RESULT_MN.get("T" + oneTinh + "_G6_3");
                        result.setSixth(sixth);
                        //G5
                        String fifth = "";
                        fifth += MAP_RESULT_MN.get("T" + oneTinh + "_G5");
                        result.setFifth(fifth);
                        //G4
                        String fouth = "";
                        fouth += MAP_RESULT_MN.get("T" + oneTinh + "_G4_1") + "-";
                        fouth += MAP_RESULT_MN.get("T" + oneTinh + "_G4_2") + "-";
                        fouth += MAP_RESULT_MN.get("T" + oneTinh + "_G4_3") + "-";
                        fouth += MAP_RESULT_MN.get("T" + oneTinh + "_G4_4") + "-";
                        fouth += MAP_RESULT_MN.get("T" + oneTinh + "_G4_5") + "-";
                        fouth += MAP_RESULT_MN.get("T" + oneTinh + "_G4_6") + "-";
                        fouth += MAP_RESULT_MN.get("T" + oneTinh + "_G4_7");
                        result.setFourth(fouth);
                        //G3
                        String thirth = "";
                        thirth += MAP_RESULT_MN.get("T" + oneTinh + "_G3_1") + "-";
                        thirth += MAP_RESULT_MN.get("T" + oneTinh + "_G3_2");
                        result.setThird(thirth);
                        //G2
                        String second = "";
                        second += MAP_RESULT_MN.get("T" + oneTinh + "_G2");
                        if (Tool.checkNull(second)) {
                            second = "-";
                        }
                        result.setSecond(second);
                        //G1
                        String first = "";
                        first += MAP_RESULT_MN.get("T" + oneTinh + "_G1");
                        if (Tool.checkNull(first)) {
                            first = "-";
                        }
                        result.setFirst(first);
                        // DB
                        String special = "";
                        special += MAP_RESULT_MN.get("T" + oneTinh + "_GDB");
                        if (Tool.checkNull(special)) {
                            special = "-";
                        }
                        result.setSpecial(special);
                        result.setLastUpdate(DateProc.createTimestamp());
                        all.add(result);
                    }
                    for (LotteryResult result : all) {
                        System.out.println("Code:" + result.getCode());
                        System.out.println("1:" + result.getFirst());
                        System.out.println("2:" + result.getSecond());
                        System.out.println("3:" + result.getThird());
                        System.out.println("4:" + result.getFourth());
                        System.out.println("5:" + result.getFifth());
                        System.out.println("6:" + result.getSixth());
                        System.out.println("7:" + result.getSeventh());
                        System.out.println("8:" + result.getEighth());
                        System.out.println("0:" + result.getSpecial());
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return all;
    }

    public HashMap<String, String> MAP_RESULT_MT = new HashMap<>();

    public ArrayList<LotteryResult> getDataMTrung() {
        ArrayList<LotteryResult> all = new ArrayList<>();
        try {
            URL url = new URL(URL_MT_LIVE + System.currentTimeMillis());
            double hm = DateProc.getTimer();
            if (hm > 17.6) {
                url = new URL(URL_MT_OFF + System.currentTimeMillis());
            }
            //-----------------
            URLConnection ucconn = url.openConnection();
            ucconn.setRequestProperty("User-Agent", USER_AGENT[buildRandomUserAgent()]);
//            InputStream ipst = ucconn.getInputStream();
            try (InputStream ipst = ucconn.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(ipst, "UTF-8"))) {
                String line = "";
                String info = "";
                while (line != null) {
                    if (line.length() > 0) {
                        info += line.trim();
                    }
                    line = reader.readLine();
                }

                info = info.replaceAll("\"", "");
                info = info.replaceAll("kqxs", "");
                info = info.replaceAll("\\*", "");
                info = info.replaceAll("\\+", "");
                info = info.replaceAll("\\[", "");
                info = info.replaceAll("\\]", "");
                String[] arr = info.split(";");
//                System.out.println(info);
                MAP_RESULT_MT.clear();
                for (String one : arr) {
                    String[] key_val = processArr(one);
                    MAP_RESULT_MT.put(key_val[0].toUpperCase(), key_val[1]);
                }
//                Set<String> key = MAP_RESULT_MT.keySet();
//                for (String one : key) {
//                    System.out.println(one + "=" + MAP_RESULT_MT.get(one));
//                }
                String tinh = MAP_RESULT_MT.get("LISTTINHNEW");
                if (tinh == null) {
                    tinh = "";
                }
//                System.out.println(tinh);
                String[] arrTinh = tinh.split(",");
                if (arrTinh != null && arrTinh.length > 0) {
                    for (String oneTinh : arrTinh) {
                        LotteryResult result = new LotteryResult();
                        // Ma Tinh
                        result.setCode(MAP_MINHNGOC_CODE.get("T" + oneTinh));
                        result.setOpenDate(DateProc.createTimestamp());
                        // G8
                        String eighth = "";
                        eighth += MAP_RESULT_MT.get("T" + oneTinh + "_G8");
                        if (Tool.checkNull(eighth)) {
                            eighth = "-";
                        }
                        result.setEighth(eighth);
                        //G7
                        String seventh = "";
                        seventh += MAP_RESULT_MT.get("T" + oneTinh + "_G7");
                        if (Tool.checkNull(seventh)) {
                            seventh = "-";
                        }
                        result.setSeventh(seventh);
                        //G6
                        String sixth = "";
                        sixth += MAP_RESULT_MT.get("T" + oneTinh + "_G6_1") + "-";
                        sixth += MAP_RESULT_MT.get("T" + oneTinh + "_G6_2") + "-";
                        sixth += MAP_RESULT_MT.get("T" + oneTinh + "_G6_3");
                        result.setSixth(sixth);
                        //G5
                        String fifth = "";
                        fifth += MAP_RESULT_MT.get("T" + oneTinh + "_G5");
                        result.setFifth(fifth);
                        //G4
                        String fouth = "";
                        fouth += MAP_RESULT_MT.get("T" + oneTinh + "_G4_1") + "-";
                        fouth += MAP_RESULT_MT.get("T" + oneTinh + "_G4_2") + "-";
                        fouth += MAP_RESULT_MT.get("T" + oneTinh + "_G4_3") + "-";
                        fouth += MAP_RESULT_MT.get("T" + oneTinh + "_G4_4") + "-";
                        fouth += MAP_RESULT_MT.get("T" + oneTinh + "_G4_5") + "-";
                        fouth += MAP_RESULT_MT.get("T" + oneTinh + "_G4_6") + "-";
                        fouth += MAP_RESULT_MT.get("T" + oneTinh + "_G4_7");
                        result.setFourth(fouth);
                        //G3
                        String thirth = "";
                        thirth += MAP_RESULT_MT.get("T" + oneTinh + "_G3_1") + "-";
                        thirth += MAP_RESULT_MT.get("T" + oneTinh + "_G3_2");
                        result.setThird(thirth);
                        //G2
                        String second = "";
                        second += MAP_RESULT_MT.get("T" + oneTinh + "_G2");
                        if (Tool.checkNull(second)) {
                            second = "-";
                        }
                        result.setSecond(second);
                        //G1
                        String first = "";
                        first += MAP_RESULT_MT.get("T" + oneTinh + "_G1");
                        if (Tool.checkNull(first)) {
                            first = "-";
                        }
                        result.setFirst(first);
                        // DB
                        String special = "";
                        special += MAP_RESULT_MT.get("T" + oneTinh + "_GDB");
                        if (Tool.checkNull(special)) {
                            special = "-";
                        }
                        result.setSpecial(special);
                        result.setLastUpdate(DateProc.createTimestamp());
                        all.add(result);
                    }
                    for (LotteryResult result : all) {
                        System.out.println("Code:" + result.getCode());
                        System.out.println("1:" + result.getFirst());
                        System.out.println("2:" + result.getSecond());
                        System.out.println("3:" + result.getThird());
                        System.out.println("4:" + result.getFourth());
                        System.out.println("5:" + result.getFifth());
                        System.out.println("6:" + result.getSixth());
                        System.out.println("7:" + result.getSeventh());
                        System.out.println("8:" + result.getEighth());
                        System.out.println("0:" + result.getSpecial());
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return all;
    }

    private String[] processArr(String str) {
        String[] arr = new String[2];
        arr[0] = "";
        arr[1] = "";
        try {
            if (str == null || str.equals("")) {
                return arr;
            } else {
                String[] tem = str.split("=");
                if (tem == null || tem.length == 0) {
                    return arr;
                } else {
                    if (tem.length == 1) {
                        arr[0] = tem[0];
                        arr[1] = "";
                    } else {
                        arr[0] = tem[0];
                        arr[1] = tem[1];
                    }
                    return arr;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return arr;
        }
    }
    public static String[] USER_AGENT = {
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 1.1.4322; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C; .NET4.0E; InfoPath.3)",
        "Opera/9.80 (Windows NT 5.1; U; Edition Campaign 04; en) Presto/2.7.62 Version/11.01",
        "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/534.24 (KHTML, like Gecko) Chrome/11.0.696.77 Safari/534.24",
        "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13"
    };

    public static int buildRandomUserAgent() {
        int ran = 0;
        while (true) {
            ran = (int) (Math.random() * 10);
            if (ran < 4) {
                break;
            }
        }
        return ran;
    }
}
