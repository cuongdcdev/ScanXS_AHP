 /* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gk.scan.xs;

import com.gk.utils.DateProc;
import com.gk.utils.Tool;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 *
 * @author PLATUAN
 */
public class LotteryUtils {

    private static final String SPECIAL_CHARACTOR = "[ .-/]";

    public LotteryUtils() {
    }

    public static boolean checkCodeisMB(String sCode) {
        boolean flag = false;
        if (sCode.equals(LotteryUtils.LOTT_XSMB) || sCode.equals(LotteryUtils.LOTT_XSTD) || sCode.equals(LotteryUtils.LOTT_XSHN)
                || sCode.equals(LotteryUtils.LOTT_XSBN) || sCode.equals(LotteryUtils.LOTT_XSTB) || sCode.equals(LotteryUtils.LOTT_XSND)
                || sCode.equals(LotteryUtils.LOTT_XSQN) || sCode.equals(LotteryUtils.LOTT_XSHP)) {
            flag = true;
        }
        return flag;
    }

    public static boolean checkCodeisMN(String subccode) {
        boolean flag = false;
        for (int i = 0; i < LOTTERY_CODE_REGION_NAME.length; i++) {
            if (subccode.equals(LOTTERY_CODE_REGION_NAME[i][0])) {
                if (LOTTERY_CODE_REGION_NAME[i][1].equalsIgnoreCase("MN")) {
                    flag = true;
                }
                break;
            }
        }
        return flag;
    }

    public static boolean checkCodeisMT(String subccode) {
        boolean flag = false;
        for (String[] code_region : LOTTERY_CODE_REGION_NAME) {
            if (subccode.equals(code_region[0])) {
                if (code_region[1].equalsIgnoreCase("MT")) {
                    flag = true;
                }
                break;
            }
        }
        return flag;
    }

    public static String[] getXSCodeOpenToday() {
        ArrayList<String> all = new ArrayList();
        all.add(getMainCompany());
        String[] mt = getCodeMTCompany();
        String[] mn = getCodeMNCompany();
        for (String one : mt) {
            if (!Tool.checkNull(one)) {
                all.add(one);
            } 
        }
        for (String one : mn) {
            if (!Tool.checkNull(one)) {
                all.add(one);
            } 
        }
        String[] str = new String[all.size()];
        int i = 0;
        for (String one : all) {
            System.out.println(one);
            str[i] = one;
            i++;
        }
        return str;
    }

    public static String[] getCodeMNCompany() {
        Calendar cal = Calendar.getInstance();
        int d = cal.get(Calendar.DAY_OF_WEEK);
        String[] company_code = {"HCM", "BP", "HG", "LA"};
        switch (d) {
            case Calendar.MONDAY:
                company_code[0] = "HCM";
                company_code[1] = "CM";
                company_code[2] = "DT";
                company_code[3] = "";
                break;
            case Calendar.TUESDAY:
                company_code[0] = "BL";
                company_code[1] = "BTR";
                company_code[2] = "VT";
                company_code[3] = "";
                break;
            case Calendar.WEDNESDAY:
                company_code[0] = "CT";
                company_code[1] = "DNI";
                company_code[2] = "ST";
                company_code[3] = "";
                break;
            case Calendar.THURSDAY:
                company_code[0] = "AG";
                company_code[1] = "BTH";
                company_code[2] = "TN";
                company_code[3] = "";
                break;
            case Calendar.FRIDAY:
                company_code[0] = "BD";
                company_code[1] = "TRV";
                company_code[2] = "VL";
                company_code[3] = "";
                break;
            case Calendar.SATURDAY:
                company_code[0] = "HCM";
                company_code[1] = "BP";
                company_code[2] = "HG";
                company_code[3] = "LA";
                break;
            case Calendar.SUNDAY:
                company_code[0] = "KG";
                company_code[1] = "LD";
                company_code[2] = "TG";
                company_code[3] = "";
                break;
        }
        return company_code;
    }

    public static String validStringLotteryRequest(String input) {

        if (input == null) {
            return "";
        }
        input = input.trim();
        input = input.replaceAll("/", " ");
        input = input.replaceAll("\"", " ");
        input = input.replaceAll(",", " ");
        input = input.replaceAll("\\.", " ");
        input = input.replaceAll("\\:", " ");
        input = input.replaceAll("\\*", " ");
        input = input.replaceAll("\\(", " ");
        input = input.replaceAll("\\)", " ");
        input = input.replaceAll("`", " ");
        input = input.replaceAll("\\?", " ");
        input = input.replaceAll("<", " ");
        input = input.replaceAll(">", " ");
        input = input.replaceAll("\\{", " ");
        input = input.replaceAll("}", " ");
        input = input.replaceAll(";", " ");
        input = input.replaceAll("!", " ");
        input = input.replaceAll("@", " ");
        input = input.replaceAll("%", " ");
        input = input.replaceAll("&", " ");
        input = input.replaceAll("'", " ");
        input = input.replaceAll("#", " ");
        input = input.replaceAll("\\$", " ");
        input = input.replaceAll("\\^", " ");
        input = input.replaceAll("-", " ");
        StringBuilder sb = new StringBuilder(input.length());
        boolean lastWasBlankChar = false;
        int len = input.length();
        char c;

        for (int i = 0; i < len; i++) {
            c = input.charAt(i);
            if (c == ' ') {
                if (lastWasBlankChar) {
                    sb.append("");
                } else {
                    lastWasBlankChar = true;
                    sb.append('-');
                }
            } else {
                lastWasBlankChar = false;
                // HTML Special Chars
                int ci = 0xffff & c;
                if (ci < 160) // nothing special only 7 Bit
                {
                    sb.append(c);
                } else {
                    // Not 7 Bit use the unicode system
                    sb.append("&#");
                    sb.append(String.valueOf(ci));
                    sb.append(';');
                }
            }
        }

        return sb.toString();
    }
    //*********
    public static String[][] LOTTERY_CODE_REGION_NAME = {
        {"MB", "MB", "Xổ số Miền Bắc"},
        {"HN", "MB", "Xổ số Miền Bắc"},
        {"TD", "MB", "Xổ số Miền Bắc"},
        {"HP", "MB", "Xổ số Miền Bắc"},
        {"QN", "MB", "Xổ số Miền Bắc"},
        {"TB", "MB", "Xổ số Miền Bắc"},
        {"ND", "MB", "Xổ số Miền Bắc"},
        {"BN", "MB", "Xổ số Miền Bắc"},
        //--
        {"HCM", "MN", "Xổ Số Thành Phố Hồ Chí Minh"},
        {"KG", "MN", "Xổ Số Kiên Giang"},
        {"TRV", "MN", "Xổ Số Trà Vinh"},
        {"LD", "MN", "Xổ Số Lâm Đồng"},
        {"DT", "MN", "Xổ Số Đồng Tháp"},
        {"CM", "MN", "Xổ Số Cà Mau"},
        {"BTR", "MN", "Xổ số Bến Tre"},
        {"VT", "MN", "Xổ Số Vũng Tàu"},
        {"BL", "MN", "Xổ số Bạc Liêu"},
        {"DNI", "MN", "Xổ số Đồng Nai"},
        {"CT", "MN", "Xổ số Cần Thơ"},
        {"ST", "MN", "Xổ Số Sóc Trăng"},
        {"BTH", "MN", "Xổ Số Bình Thuận"},
        {"TN", "MN", "Xổ Số Tây Ninh"},
        {"AG", "MN", "Xổ số An Giang"},
        {"BD", "MN", "Xổ Số Bình Dương"},
        {"VL", "MN", "Xổ Số Vĩnh Long"},
        {"BP", "MN", "Xổ số Bình Phước"},
        {"HG", "MN", "Xổ Số Hậu Giang"},
        {"LA", "MN", "Xổ Số Long An"},
        {"TG", "MN", "Xổ Số Tiền Giang"},
        //--
        {"QB", "MT", "Xổ số Quảng Bình"},
        {"PY", "MT", "Xổ Số Phú Yên"},
        {"TTH", "MT", "Xổ Số Thừa Thiên Huế "},
        {"QNM", "MT", "Xổ Số Quảng Nam"},
        {"DLK", "MT", "Xổ số Đắc Lak"},
        {"DNG", "MT", "Xổ số Đà Nẵng"},
        {"KH", "MT", "Xổ số Khánh Hoà"},
        {"BDI", "MT", "Xổ số Bình Định"},
        {"QT", "MT", "Xổ số Quảng Trị"},
        {"GL", "MT", "Xổ số Gia Lai"},
        {"NT", "MT", "Xổ số Ninh Thuận"},
        {"QNG", "MT", "Xổ số Quảng Ngãi"},
        {"DNO", "MT", "Xổ Số Đắc Nông"},
        {"KT", "MT", "Xổ số Kon Tum"}
    };
    public final static String LOTT_XSTD = "TD";
    public final static String LOTT_XSHN = "HN";
    public final static String LOTT_XSQN = "QN";
    public final static String LOTT_XSBN = "BN";
    public final static String LOTT_XSHP = "HP";
    public final static String LOTT_XSND = "ND";
    public final static String LOTT_XSTB = "TB";
    //
    public final static String LOTT_XSMB = "MB";
    public final static String LOTT_XSMN = "MN";
    public final static String LOTT_XSMT = "MT";
    //db
    public final static int LOTT_G_0 = 0;
    //1
    public final static int LOTT_G_1 = 1;
    //g-2
    public final static int LOTT_G_2 = 2;
    //G3
    public final static int LOTT_G_3 = 3;
    //G4
    public final static int LOTT_G_4 = 4;
    //G5
    public final static int LOTT_G_5 = 5;
    //G6
    public final static int LOTT_G_6 = 6;
    //G7
    public final static int LOTT_G_7 = 7;
    //G8
    public final static int LOTT_G_8 = 8;

    /**
     * Lấy mã tỉnh mở thưởng của MB
     *
     * @return
     */
    public static String getMainCompany() {
        Calendar cal = Calendar.getInstance();
        int d = cal.get(Calendar.DAY_OF_WEEK);
        String company_code = "TD";
        switch (d) {
            case Calendar.MONDAY:
                company_code = LOTT_XSTD;
                break;
            case Calendar.TUESDAY:
                company_code = LOTT_XSQN;
                break;
            case Calendar.WEDNESDAY:
                company_code = LOTT_XSBN;
                break;
            case Calendar.THURSDAY:
                company_code = LOTT_XSTD;
                break;
            case Calendar.FRIDAY:
                company_code = LOTT_XSHP;
                break;
            case Calendar.SATURDAY:
                company_code = LOTT_XSND;
                break;
            case Calendar.SUNDAY:
                company_code = LOTT_XSTB;
                break;
            default:
                company_code = "";
        }
        return company_code;
    }

    /**
     * Lấy mã tỉnh mở thưởng của miền bắc theo ngày
     *
     * @param date
     * @return
     */
    public static String getMainCompany(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateProc.String2Timestamp(date));
        int d = cal.get(Calendar.DAY_OF_WEEK);
        String company_code = "TD";
        switch (d) {
            case Calendar.MONDAY:
                company_code = LOTT_XSTD;
                break;
            case Calendar.TUESDAY:
                company_code = LOTT_XSQN;
                break;
            case Calendar.WEDNESDAY:
                company_code = LOTT_XSBN;
                break;
            case Calendar.THURSDAY:
                company_code = LOTT_XSTD;
                break;
            case Calendar.FRIDAY:
                company_code = LOTT_XSHP;
                break;
            case Calendar.SATURDAY:
                company_code = LOTT_XSND;
                break;
            case Calendar.SUNDAY:
                company_code = LOTT_XSTB;
                break;
            default:
                company_code = "";
        }
        return company_code;
    }

    /**
     * Ma tinh mo thuong theo Thu
     *
     * @param day_of_week : 2 - 7 <br/> CN : = 1
     * @return Mã tỉnh mở thưởng vào thứ đã chọn
     */
    public static String getMainCompany(int day_of_week) {
        String company_code = "TD";
        switch (day_of_week) {
            case Calendar.MONDAY:
                company_code = LOTT_XSTD;
                break;
            case Calendar.TUESDAY:
                company_code = LOTT_XSQN;
                break;
            case Calendar.WEDNESDAY:
                company_code = LOTT_XSBN;
                break;
            case Calendar.THURSDAY:
                company_code = LOTT_XSTD;
                break;
            case Calendar.FRIDAY:
                company_code = LOTT_XSHP;
                break;
            case Calendar.SATURDAY:
                company_code = LOTT_XSND;
                break;
            case Calendar.SUNDAY:
                company_code = LOTT_XSTB;
                break;
            default:
                company_code = "";
        }
        return company_code;
    }

    /**
     * Lấy Miền trung Mã và Tỉnh
     *
     * @return String[][]
     * <br/>
     */
    public static String[][] getMTCompany() {
        Calendar cal = Calendar.getInstance();
        int d = cal.get(Calendar.DAY_OF_WEEK);
        String[] company_code = {"PY", "TTH", ""};
        switch (d) {
            case Calendar.MONDAY:
                company_code[0] = "PY";
                company_code[1] = "TTH";
                company_code[2] = "";
                break;
            case Calendar.TUESDAY:
                company_code[0] = "QNM";
                company_code[1] = "DLK";
                company_code[2] = "";
                break;
            case Calendar.WEDNESDAY:
                company_code[0] = "DNG"; //x
                company_code[1] = "KH";//x
                company_code[2] = "";
                break;
            case Calendar.THURSDAY:
                company_code[0] = "BDI";
                company_code[1] = "QT";
                company_code[2] = "QB";
                break;
            case Calendar.FRIDAY:
                company_code[0] = "GL";
                company_code[1] = "NT";
                company_code[2] = "";
                break;
            case Calendar.SATURDAY:
                company_code[0] = "QNG";
                company_code[1] = "DNO";
                company_code[2] = "DNG"; //x
                break;
            case Calendar.SUNDAY:
                company_code[0] = "KT";
                company_code[1] = "KH";//x
                company_code[2] = "";
                break;
        }

        String[][] companyName = new String[4][2];
        int i = 0;

        for (String s : company_code) {
            if (s != null && !s.trim().equals("")) {
                for (String[] arrName : LOTTERY_CODE_REGION_NAME) {
                    if (s.equalsIgnoreCase(arrName[0])) {
                        companyName[i][0] = arrName[0];
                        companyName[i][1] = arrName[2];
                        i++;
                        break;
                    }
                }
            }
        }

        return companyName;
    }

    /**
     * *
     * Lấy code Miền trung mở thưởng theo ngày
     *
     * @param date
     * @return
     */
    public static String[] getCodeMTCompany(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateProc.String2Timestamp(date));
        int d = cal.get(Calendar.DAY_OF_WEEK);
        String[] company_code = {"PY", "TTH", ""};
        switch (d) {
            case Calendar.MONDAY:
                company_code[0] = "PY";
                company_code[1] = "TTH";
                company_code[2] = "";
                break;
            case Calendar.TUESDAY:
                company_code[0] = "QNM";
                company_code[1] = "DLK";
                company_code[2] = "";
                break;
            case Calendar.WEDNESDAY:
                company_code[0] = "DNG";
                company_code[1] = "KH";
                company_code[2] = "";
                break;
            case Calendar.THURSDAY:
                company_code[0] = "BDI";
                company_code[1] = "QT";
                company_code[2] = "QB";
                break;
            case Calendar.FRIDAY:
                company_code[0] = "GL";
                company_code[1] = "NT";
                company_code[2] = "";
                break;
            case Calendar.SATURDAY:
                company_code[0] = "QNG";
                company_code[1] = "DNO";
                company_code[2] = "DNG";
                break;
            case Calendar.SUNDAY:
                company_code[0] = "KT";
                company_code[1] = "KH";
                company_code[2] = "";
                break;
        }
        // Xu ly bo Tinh null or ""
        ArrayList<String> arr = new ArrayList<>();
        for (String onString : company_code) {
            if (onString == null || onString.equals("")) {
                continue;
            }
            arr.add(onString);
        }
        company_code = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            company_code[i] = arr.get(i);
        }
        return company_code;
    }

    /**
     * *
     * Lấy code Miền trung mở thưởng ngày hôm nay
     *
     * @return
     */
    public static String[] getCodeMTCompany() {
        Calendar cal = Calendar.getInstance();
        int d = cal.get(Calendar.DAY_OF_WEEK);
        String[] company_code = {"PY", "TTH", ""};
        switch (d) {
            case Calendar.MONDAY:
                company_code[0] = "PY";
                company_code[1] = "TTH";
                company_code[2] = "";
                break;
            case Calendar.TUESDAY:
                company_code[0] = "QNM";
                company_code[1] = "DLK";
                company_code[2] = "";
                break;
            case Calendar.WEDNESDAY:
                company_code[0] = "DNG";
                company_code[1] = "KH";
                company_code[2] = "";
                break;
            case Calendar.THURSDAY:
                company_code[0] = "BDI";
                company_code[1] = "QT";
                company_code[2] = "QB";
                break;
            case Calendar.FRIDAY:
                company_code[0] = "GL";
                company_code[1] = "NT";
                company_code[2] = "";
                break;
            case Calendar.SATURDAY:
                company_code[0] = "QNG";
                company_code[1] = "DNO";
                company_code[2] = "DNG";
                break;
            case Calendar.SUNDAY:
                company_code[0] = "KT";
                company_code[1] = "KH";
                company_code[2] = "";
                break;
        }
        // Xu ly bo Tinh null or ""
        ArrayList<String> arr = new ArrayList<>();
        for (String onString : company_code) {
            if (onString != null && !onString.equals("")) {
                arr.add(onString);
            }
        }
        company_code = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            company_code[i] = arr.get(i);
        }
        return company_code;
    }

    /**
     * *
     * Lay ma Mien trung mo thuong Next ngay
     *
     * @param n : so ngay can lay
     * @return
     */
    public static String[] getMTCompany_Next(int n) {
        Calendar c = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) + n);
        int d = cal.get(Calendar.DAY_OF_WEEK);
        String[] company_code = {"PY", "TTH", ""};
        switch (d) {
            case Calendar.MONDAY:
                company_code[0] = "PY";
                company_code[1] = "TTH";
                company_code[2] = "";
                break;
            case Calendar.TUESDAY:
                company_code[0] = "QNM";
                company_code[1] = "DLK";
                company_code[2] = "";
                break;
            case Calendar.WEDNESDAY:
                company_code[0] = "DNG";
                company_code[1] = "KH";
                company_code[2] = "";
                break;
            case Calendar.THURSDAY:
                company_code[0] = "BDI";
                company_code[1] = "QT";
                company_code[2] = "QB";
                break;
            case Calendar.FRIDAY:
                company_code[0] = "GL";
                company_code[1] = "NT";
                company_code[2] = "";
                break;
            case Calendar.SATURDAY:
                company_code[0] = "QNG";
                company_code[1] = "DNO";
                company_code[2] = "DNG";
                break;
            case Calendar.SUNDAY:
                company_code[0] = "KT";
                company_code[1] = "KH";
                company_code[2] = "";
                break;
        }
        // Xu ly bo Tinh null or ""
        ArrayList<String> arr = new ArrayList<>();
        for (String onString : company_code) {
            if (onString != null && !onString.equals("")) {
                arr.add(onString);
            }
        }
        company_code = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            company_code[i] = arr.get(i);
        }
        return company_code;
    }

    public static String[] getMNCompany(String date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateProc.String2Timestamp(date));
        int d = cal.get(Calendar.DAY_OF_WEEK);
        String[] company_code = {"HCM", "BP", "HG", "LA"};
        switch (d) {
            case Calendar.MONDAY:
                company_code[0] = "HCM";
                company_code[1] = "CM";
                company_code[2] = "DT";
                company_code[3] = "";
                break;
            case Calendar.TUESDAY:
                company_code[0] = "BL";
                company_code[1] = "BTR";
                company_code[2] = "VT";
                company_code[3] = "";
                break;
            case Calendar.WEDNESDAY:
                company_code[0] = "CT";
                company_code[1] = "DNI";
                company_code[2] = "ST";
                company_code[3] = "";
                break;
            case Calendar.THURSDAY:
                company_code[0] = "AG";
                company_code[1] = "BTH";
                company_code[2] = "TN";
                company_code[3] = "";
                break;
            case Calendar.FRIDAY:
                company_code[0] = "BD";
                company_code[1] = "TRV";
                company_code[2] = "VL";
                company_code[3] = "";
                break;
            case Calendar.SATURDAY:
                company_code[0] = "HCM";
                company_code[1] = "BP";
                company_code[2] = "HG";
                company_code[3] = "LA";
                break;
            case Calendar.SUNDAY:
                company_code[0] = "KG";
                company_code[1] = "LD";
                company_code[2] = "TG";
                company_code[3] = "";
                break;
        }
        // Xu ly bo Tinh null or ""
        ArrayList<String> arr = new ArrayList<>();
        for (String onString : company_code) {
            if (onString == null || onString.equals("")) {
                continue;
            }
            arr.add(onString);
        }
        company_code = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            company_code[i] = arr.get(i);
        }
        return company_code;
    }

    /**
     * *
     * getMNCompany_next(int n)
     *
     * @param n
     * @return
     */
    public static String[] getMNCompany_next(int n) {
        Calendar c = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH) + n);
        int d = cal.get(Calendar.DAY_OF_WEEK);
        String[] company_code = {"HCM", "BP", "HG", "LA"};
        switch (d) {
            case Calendar.MONDAY:
                company_code[0] = "HCM";
                company_code[1] = "CM";
                company_code[2] = "DT";
                company_code[3] = "";
                break;
            case Calendar.TUESDAY:
                company_code[0] = "BL";
                company_code[1] = "BTR";
                company_code[2] = "VT";
                company_code[3] = "";
                break;
            case Calendar.WEDNESDAY:
                company_code[0] = "CT";
                company_code[1] = "DNI";
                company_code[2] = "ST";
                company_code[3] = "";
                break;
            case Calendar.THURSDAY:
                company_code[0] = "AG";
                company_code[1] = "BTH";
                company_code[2] = "TN";
                company_code[3] = "";
                break;
            case Calendar.FRIDAY:
                company_code[0] = "BD";
                company_code[1] = "TRV";
                company_code[2] = "VL";
                company_code[3] = "";
                break;
            case Calendar.SATURDAY:
                company_code[0] = "HCM";
                company_code[1] = "BP";
                company_code[2] = "HG";
                company_code[3] = "LA";
                break;
            case Calendar.SUNDAY:
                company_code[0] = "KG";
                company_code[1] = "LD";
                company_code[2] = "TG";
                company_code[3] = "";
                break;
        }
        // Xu ly bo Tinh null or ""
        ArrayList<String> arr = new ArrayList<>();
        for (String onString : company_code) {
            if (onString != null && !onString.equals("")) {
                arr.add(onString);
            }
        }
        company_code = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            company_code[i] = arr.get(i);
        }
        return company_code;
    }

    public String[][] getMNCompany() {
        Calendar cal = Calendar.getInstance();
        int d = cal.get(Calendar.DAY_OF_WEEK);
        String[] company_code = {"HCM", "BP", "HG", "LA"};
        switch (d) {
            case Calendar.MONDAY:
                company_code[0] = "HCM";
                company_code[1] = "CM";
                company_code[2] = "DT";
                company_code[3] = "";
                break;
            case Calendar.TUESDAY:
                company_code[0] = "BL";
                company_code[1] = "BTR";
                company_code[2] = "VT";
                company_code[3] = "";
                break;
            case Calendar.WEDNESDAY:
                company_code[0] = "CT";
                company_code[1] = "DNI";
                company_code[2] = "ST";
                company_code[3] = "";
                break;
            case Calendar.THURSDAY:
                company_code[0] = "AG";
                company_code[1] = "BTH";
                company_code[2] = "TN";
                company_code[3] = "";
                break;
            case Calendar.FRIDAY:
                company_code[0] = "BD";
                company_code[1] = "TRV";
                company_code[2] = "VL";
                company_code[3] = "";
                break;
            case Calendar.SATURDAY:
                company_code[0] = "HCM";
                company_code[1] = "BP";
                company_code[2] = "HG";
                company_code[3] = "LA";
                break;
            case Calendar.SUNDAY:
                company_code[0] = "KG";
                company_code[1] = "LD";
                company_code[2] = "TG";
                company_code[3] = "";
                break;
        }

        String[][] companyName = new String[4][2];
        int i = 0;

        for (String s : company_code) {
            if (s != null && !s.trim().equals("")) {
                for (String[] name : LOTTERY_CODE_REGION_NAME) {
                    if (s.equalsIgnoreCase(name[0])) {
                        companyName[i][0] = name[0];    // CODE
                        companyName[i][1] = name[2];    // FULL NAME
                        i++;
                        break;
                    }
                }
            }
        }

        return companyName;
    }

    public static String[] getCompany_NT_MN() {
        Calendar cal = Calendar.getInstance();
        int d = cal.get(Calendar.DAY_OF_WEEK);
        String[] company_code = {"HCM", "BP", "HG", "LA", "", "", "", ""};
        switch (d) {
            case Calendar.MONDAY:
                company_code[0] = "HCM";
                company_code[1] = "CM";
                company_code[2] = "DT";
                company_code[3] = "TD";
                company_code[4] = "PY";
                company_code[5] = "TTH";
                break;
            case Calendar.TUESDAY:
                company_code[0] = "BL";
                company_code[1] = "BTR";
                company_code[2] = "VT";
                company_code[3] = "QN";
                company_code[4] = "QNM";
                company_code[5] = "DLK";
                break;
            case Calendar.WEDNESDAY:
                company_code[0] = "CT";
                company_code[1] = "DNI";
                company_code[2] = "ST";
                company_code[3] = "BN";
                break;
            case Calendar.THURSDAY:
                company_code[0] = "AG";
                company_code[1] = "BTH";
                company_code[2] = "TN";
                company_code[3] = "TD";
                break;
            case Calendar.FRIDAY:
                company_code[0] = "BD";
                company_code[1] = "TRV";
                company_code[2] = "VL";
                company_code[3] = "HP";
                break;
            case Calendar.SATURDAY:
                company_code[0] = "HCM";
                company_code[1] = "BP";
                company_code[2] = "HG";
                company_code[3] = "LA";
                company_code[4] = "DNO";
                company_code[5] = "QNG";
                company_code[6] = "DNG";
                company_code[7] = "ND";
                break;
            case Calendar.SUNDAY:
                company_code[0] = "KG";
                company_code[1] = "LD";
                company_code[2] = "TG";
                company_code[3] = "TB";
                break;
        }
        return company_code;
    }

    public static String buildName(String code) {
        if (code == null) {
            return "";
        }

        String name = code;
        if (code.equals("HCM") || code.equals("TP")) {
            name = "TP HCM";
        } else if (code.equals("BD")) {
            name = "BINH DUONG";
        } else if (code.equals("MB")) {
            name = "MIEN BAC";
        } else if (code.equals("TD")) {
            name = "THU DO";
        } else if (code.equals("HP")) {
            name = "HAI PHONG";
        } else if (code.equals("QN")) {
            name = "QUANG NINH";
        } else if (code.equals("TB")) {
            name = "THAI BINH";
        } else if (code.equals("BN")) {
            name = "BAC NINH";
        } else if (code.equals("ND")) {
            name = "NAM DINH";
            //---- END MB
        } else if (code.equals("ST")) {
            name = "SOC TRANG";
        } else if (code.equals("KG")) {
            name = "KIEN GIANG";
        } else if (code.equals("TG")) {
            name = "TIEN GIANG";
        } else if (code.equals("DNI")) {
            name = "DONG NAI";
        } else if (code.equals("TN")) {
            name = "TAY NINH";
        } else if (code.equals("VL")) {
            name = "VINH LONG";
        } else if (code.equals("LA")) {
            name = "LONG AN";
        } else if (code.equals("BP")) {
            name = "BINH PHUOC";
        } else if (code.equals("CT")) {
            name = "CAN THO";
        } else if (code.equals("DNG")) {
            name = "DA NANG";
        } else if (code.equals("AG")) {
            name = "AN GIANG";
        } else if (code.equals("BL")) {
            name = "BAC LIEU";
        } else if (code.equals("TRV")) {
            name = "TRA VINH";
        } else if (code.equals("BTR")) {
            name = "BEN TRE";
        } else if (code.equals("VT")) {
            name = "VUNG TAU";
        } else if (code.equals("CM")) {
            name = "CA MAU";
        } else if (code.equals("DL") || code.equals("LD")) {
            name = "DA LAT";
        } else if (code.equals("DL")) {
            name = "DA LAT";
        } else if (code.equals("DT")) {
            name = "DONG THAP";
        } else if (code.equals("PY")) {
            name = "PHU YEN";
        } else if (code.equals("TTH")) {
            name = "TT HUE";
        } else if (code.equals("QNM")) {
            name = "QUANG NAM";
        } else if (code.equals("DLK")) {
            name = "DAC LAK";
        } else if (code.equals("DNG")) {
            name = "DA NANG";
        } else if (code.equals("KH")) {
            name = "KHANH HOA";
        } else if (code.equals("BDI")) {
            name = "BINH DINH";
        } else if (code.equals("QT")) {
            name = "QUANG TRI";
        } else if (code.equals("GL")) {
            name = "GIA LAI";
        } else if (code.equals("NT")) {
            name = "NINH THUAN";
        } else if (code.equals("QNG")) {
            name = "QUANG NGAI";
        } else if (code.equals("DNO")) {
            name = "DAK NONG";
        } else if (code.equals("KT")) {
            name = "KON TUM";
        } else if (code.equals("BTH")) {
            name = "BINH THUAN";
        }
        return name;
    }
    // -------********************--------- SEND IDC -------********************---------

    /**
     * *
     * BUILD SAU KHI DA CHECK FULL GIẢI RỒI
     *
     * @param onResult
     * @return
     */
    public static String buildResultSendIDC(LotteryResult onResult) {
        String str = "";
        str += processPrize(onResult.getFirst()) + "#";
        str += processPrize(onResult.getSecond()) + "#";
        str += processPrize(onResult.getThird()) + "#";
        str += processPrize(onResult.getFourth()) + "#";
        str += processPrize(onResult.getFifth()) + "#";
        str += processPrize(onResult.getSixth()) + "#";
        str += processPrize(onResult.getSeventh()) + "#";
        str += processPrize(onResult.getSpecial());
        return str;
    }

    public static String buildResultSendIDC_MN_MT(LotteryResult onResult) {
        String str = "";
        str += processPrize(onResult.getEighth()) + "#";
        str += processPrize(onResult.getSeventh()) + "#";
        str += processPrize(onResult.getSixth()) + "#";
        str += processPrize(onResult.getFifth()) + "#";
        str += processPrize(onResult.getFourth()) + "#";
        str += processPrize(onResult.getThird()) + "#";
        str += processPrize(onResult.getSecond()) + "#";
        str += processPrize(onResult.getFirst()) + "#";
        str += processPrize(onResult.getSpecial());
        return str;
    }

    public static String processPrize(String result) {
        String str = "";
        if (result == null || result.equals("")) {
            return str;
        }
        StringTokenizer tokenSpace = new StringTokenizer(result, SPECIAL_CHARACTOR);
        while (tokenSpace.hasMoreElements()) {
            String tempStr = tokenSpace.nextToken();
            str += tempStr + "-";
        }
        if (!str.equals("") && str.endsWith("-")) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static int countPrize(String result) {
        int count = 0;
        if (result != null && !result.equals("")) {
            String[] arr = result.split("-");
            if (arr != null && arr.length >= 0) {
                count = arr.length;
            }
        }
        return count;
    }

    public static boolean checkFullPrizeMB(String oneResult, int prize) {
        boolean flag = false;
        oneResult = processPrize(oneResult);
        switch (prize) {
            case 0:
                if (countPrize(oneResult) == 1 && oneResult.length() == 5) {
                    flag = true;
                }
                break;
            case 1:
                if (countPrize(oneResult) == 1 && oneResult.length() == 5) {
                    flag = true;
                }
                break;
            case 2:
                if (countPrize(oneResult) == 2 && oneResult.length() == 11) {
                    flag = true;
                }
                break;
            case 3:
                if (countPrize(oneResult) == 6 && oneResult.length() == 35) {
                    flag = true;
                }
                break;
            case 4:
                if (countPrize(oneResult) == 4 && oneResult.length() == 19) {
                    flag = true;
                }
                break;
            case 5:
                if (countPrize(oneResult) == 6 && oneResult.length() == 29) {
                    flag = true;
                }
                break;
            case 6:
                if (countPrize(oneResult) == 3 && oneResult.length() == 11) {
                    flag = true;
                }
                break;
            case 7:
                if (countPrize(oneResult) == 4 && oneResult.length() == 11) {
                    flag = true;
                }
                break;
            default:
                break;
        }
        return flag;
    }

    public static boolean checkFullPrizeMN_MT(String result, int prize) {
        boolean flag = false;
        result = processPrize(result);
        switch (prize) {
            case 0:
                if (countPrize(result) == 1 && (result.length() == 5 || result.length() == 6)) {
                    flag = true;
                }
                break;
            case 1:
                if (countPrize(result) == 1 && result.length() == 5) {
                    flag = true;
                }
                break;
            case 2:
                if (countPrize(result) == 1 && result.length() == 5) {
                    flag = true;
                }
                break;
            case 3:
                if (countPrize(result) == 2 && result.length() == 11) {
                    flag = true;
                }
                break;
            case 4:
                if (countPrize(result) == 7 && result.length() == 41) {
                    flag = true;
                }
                break;
            case 5:
                if (countPrize(result) == 1 && result.length() == 4) {
                    flag = true;
                }
                break;
            case 6:
                if (countPrize(result) == 3 && result.length() == 14) {
                    flag = true;
                }
                break;
            case 7:
                if (countPrize(result) == 1 && result.length() == 3) {
                    flag = true;
                }
                break;
            case 8:
                if (countPrize(result) == 1 && result.length() == 2) {
                    flag = true;
                }
                break;
            default:
                break;
        }
        return flag;
    }
    ///----
}
