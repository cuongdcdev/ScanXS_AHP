package com.gk.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import org.apache.log4j.Logger;

// Referenced classes of package com.fss.util:
//            BASE64Encoder
public class StringUtil {

    static Logger logger = Logger.getLogger(StringUtil.class);

    public static String format(Date dtImput, String strPattern) {
        if (dtImput == null) {
            return null;
        } else {
            SimpleDateFormat fmt = new SimpleDateFormat(strPattern);
            return fmt.format(dtImput);
        }
    }

    public StringUtil() {
    }

    public static String format(long lngNumber, String strPattern) {
        DecimalFormat fmt = new DecimalFormat(strPattern);
        return fmt.format(lngNumber);
    }

    public static String format(double dblNumber, String strPattern) {
        DecimalFormat fmt = new DecimalFormat(strPattern);
        return fmt.format(dblNumber);
    }

    public static String replaceAll(String strSrc, String strFind, String strReplace) {
        if (strFind == null || strFind.length() == 0) {
            return strSrc;
        }
        int iLocation = 0;
        int iPrevLocation = 0;
        StringBuilder strResult = new StringBuilder();
        while ((iLocation = strSrc.indexOf(strFind, iLocation)) >= 0) {
            strResult.append(strSrc.substring(iPrevLocation, iLocation));
            strResult.append(strReplace);
            iLocation += strFind.length();
            iPrevLocation = iLocation;
        }
        strResult.append(strSrc.substring(iPrevLocation, strSrc.length()));
        return strResult.toString();
    }

    public static String replaceAll(String strSrc, String strFind, String strReplace, int iMaxReplacement) {
        int iLocation = 0;
        if (strFind == null || strFind.length() == 0) {
            return strSrc;
        }
        int iPrevLocation = 0;
        int iCount = 0;
        StringBuilder strResult = new StringBuilder();
        while ((iLocation = strSrc.indexOf(strFind, iLocation)) >= 0 && iCount < iMaxReplacement) {
            strResult.append(strSrc.substring(iPrevLocation, iLocation));
            strResult.append(strReplace);
            iCount++;
            iLocation += strFind.length();
            iPrevLocation = iLocation;
        }
        strResult.append(strSrc.substring(iPrevLocation, strSrc.length()));
        return strResult.toString();
    }

    public static String replaceAllIgnoreCase(String strSrc, String strFind, String strReplace) {
        if (strFind == null || strFind.length() == 0) {
            return strSrc;
        }
        String strSrcUpper = strSrc.toUpperCase();
        strFind = strFind.toUpperCase();
        int iLocation = 0;
        int iPrevLocation = 0;
        StringBuilder strResult = new StringBuilder();
        while ((iLocation = strSrcUpper.indexOf(strFind, iLocation)) >= 0) {
            strResult.append(strSrc.substring(iPrevLocation, iLocation));
            strResult.append(strReplace);
            iLocation += strFind.length();
            iPrevLocation = iLocation;
        }
        strResult.append(strSrc.substring(iPrevLocation, strSrc.length()));
        return strResult.toString();
    }

    public static String nvl(Object objInput, String strNullValue) {
        if (objInput == null) {
            return strNullValue;
        } else {
            return objInput.toString();
        }
    }

    public static int indexOfLetter(String strSource, int iOffset) {
        for (; iOffset < strSource.length(); iOffset++) {
            char c = strSource.charAt(iOffset);
            if (c > ' ') {
                return iOffset;
            }
        }

        return -1;
    }

    public static int indexOfSpace(String strSource, int iOffset) {
        while (iOffset < strSource.length()) {
            char c = strSource.charAt(iOffset);
            if (c > ' ') {
                iOffset++;
            } else {
                return iOffset;
            }
        }
        return -1;
    }

    public static String[] toStringArray(String strSource, String strSeparator) {
        ArrayList vtReturn = toStringArrayList(strSource, strSeparator);
        String strReturn[] = new String[vtReturn.size()];
        for (int iIndex = 0; iIndex < strReturn.length; iIndex++) {
            strReturn[iIndex] = (String) vtReturn.get(iIndex);
        }

        return strReturn;
    }

    public static ArrayList toStringArrayList(String strSource, String strSeparator) {
        ArrayList vtReturn = new ArrayList();
        int iIndex = 0;
        int iLastIndex;
        for (iLastIndex = 0; (iIndex = strSource.indexOf(strSeparator, iLastIndex)) >= 0; iLastIndex = iIndex + strSeparator.length()) {
            vtReturn.add(strSource.substring(iLastIndex, iIndex).trim());
        }

        if (iLastIndex <= strSource.length()) {
            vtReturn.add(strSource.substring(iLastIndex, strSource.length()).trim());
        }
        return vtReturn;
    }

    public static String[] toStringArray(String strSource) {
        return toStringArray(strSource, ",");
    }

    public static ArrayList toStringArrayList(String strSource) {
        return toStringArrayList(strSource, ",");
    }

    public static String convertCharForm(String strSource, String strCharformSource, String strCharformDestination) {
        if (strSource == null) {
            return null;
        }
        int iLength = strSource.length();
        int iResult = 0;
        StringBuilder strReturn = new StringBuilder();
        for (int iIndex = 0; iIndex < iLength; iIndex++) {
            char c = strSource.charAt(iIndex);
            if ((iResult = strCharformSource.indexOf(c)) >= 0) {
                strReturn.append(strCharformDestination.charAt(iResult));
            } else {
                strReturn.append(c);
            }
        }

        return strReturn.toString();
    }

    public static String unicodeToTCVN(String strSource) {
        return convertCharForm(strSource, "\340\341\u1EA3\343\u1EA1\342\u1EA7\u1EA5\u1EA9\u1EAB\u1EAD\u0103\u1EB1\u1EAF\u1EB3\u1EB5\u1EB7\350\351\u1EBB\u1EBD\u1EB9\352\u1EC1\u1EBF\u1EC3\u1EC5\u1EC7\354\355\u1EC9\u0129\u1ECB\362\363\u1ECF\365\u1ECD\364\u1ED3\u1ED1\u1ED5\u1ED7\u1ED9\u01A1\u1EDD\u1EDB\u1EDF\u1EE1\u1EE3\371\372\u1EE7\u0169\u1EE5\u01B0\u1EEB\u1EE9\u1EED\u1EEF\u1EF1\u1EF3\375\u1EF7\u1EF9\u1EF5\u0111\300\301\u1EA2\303\u1EA0\302\u1EA6\u1EA4\u1EA8\u1EAA\u1EAC\u0102\u1EB0\u1EAE\u1EB2\u1EB4\u1EB6\310\311\u1EBA\u1EBC\u1EB8\312\u1EC0\u1EBE\u1EC2\u1EC4\u1EC6\314\315\u1EC8\u0128\u1ECA\322\323\u1ECE\325\u1ECC\324\u1ED2\u1ED0\u1ED4\u1ED6\u1ED8\u01A0\u1EDC\u1EDA\u1EDE\u1EE0\u1EE2\331\332\u1EE6\u0168\u1EE4\u01AF\u1EEA\u1EE8\u1EEC\u1EEE\u1EF0\u1EF2\335\u1EF6\u1EF8\u1EF4\u0110", "\265\270\266\267\271\251\307\312\310\311\313\250\273\276\274\275\306\314\320\316\317\321\252\322\325\323\324\326\327\335\330\334\336\337\343\341\342\344\253\345\350\346\347\351\254\352\355\353\354\356\357\363\361\362\364\255\365\370\366\367\371\372\375\373\374\376\256\265\270\266\267\271\251\307\312\310\311\313\250\273\276\274\275\306\314\320\316\317\321\252\322\325\323\324\326\327\335\330\334\336\337\343\341\342\344\253\345\350\346\347\351\254\352\355\353\354\356\357\363\361\362\364\255\365\370\366\367\371\372\375\373\374\376\256");
    }

    public static String tcvnToUnicode(String strSource) {
        return convertCharForm(strSource, "\265\270\266\267\271\251\307\312\310\311\313\250\273\276\274\275\306\314\320\316\317\321\252\322\325\323\324\326\327\335\330\334\336\337\343\341\342\344\253\345\350\346\347\351\254\352\355\353\354\356\357\363\361\362\364\255\365\370\366\367\371\372\375\373\374\376\256\265\270\266\267\271\251\307\312\310\311\313\250\273\276\274\275\306\314\320\316\317\321\252\322\325\323\324\326\327\335\330\334\336\337\343\341\342\344\253\345\350\346\347\351\254\352\355\353\354\356\357\363\361\362\364\255\365\370\366\367\371\372\375\373\374\376\256", "\340\341\u1EA3\343\u1EA1\342\u1EA7\u1EA5\u1EA9\u1EAB\u1EAD\u0103\u1EB1\u1EAF\u1EB3\u1EB5\u1EB7\350\351\u1EBB\u1EBD\u1EB9\352\u1EC1\u1EBF\u1EC3\u1EC5\u1EC7\354\355\u1EC9\u0129\u1ECB\362\363\u1ECF\365\u1ECD\364\u1ED3\u1ED1\u1ED5\u1ED7\u1ED9\u01A1\u1EDD\u1EDB\u1EDF\u1EE1\u1EE3\371\372\u1EE7\u0169\u1EE5\u01B0\u1EEB\u1EE9\u1EED\u1EEF\u1EF1\u1EF3\375\u1EF7\u1EF9\u1EF5\u0111\300\301\u1EA2\303\u1EA0\302\u1EA6\u1EA4\u1EA8\u1EAA\u1EAC\u0102\u1EB0\u1EAE\u1EB2\u1EB4\u1EB6\310\311\u1EBA\u1EBC\u1EB8\312\u1EC0\u1EBE\u1EC2\u1EC4\u1EC6\314\315\u1EC8\u0128\u1ECA\322\323\u1ECE\325\u1ECC\324\u1ED2\u1ED0\u1ED4\u1ED6\u1ED8\u01A0\u1EDC\u1EDA\u1EDE\u1EE0\u1EE2\331\332\u1EE6\u0168\u1EE4\u01AF\u1EEA\u1EE8\u1EEC\u1EEE\u1EF0\u1EF2\335\u1EF6\u1EF8\u1EF4\u0110");
    }

    public static String clearHornUnicode(String strSource) {
        return convertCharForm(strSource, "\340\341\u1EA3\343\u1EA1\342\u1EA7\u1EA5\u1EA9\u1EAB\u1EAD\u0103\u1EB1\u1EAF\u1EB3\u1EB5\u1EB7\350\351\u1EBB\u1EBD\u1EB9\352\u1EC1\u1EBF\u1EC3\u1EC5\u1EC7\354\355\u1EC9\u0129\u1ECB\362\363\u1ECF\365\u1ECD\364\u1ED3\u1ED1\u1ED5\u1ED7\u1ED9\u01A1\u1EDD\u1EDB\u1EDF\u1EE1\u1EE3\371\372\u1EE7\u0169\u1EE5\u01B0\u1EEB\u1EE9\u1EED\u1EEF\u1EF1\u1EF3\375\u1EF7\u1EF9\u1EF5\u0111\300\301\u1EA2\303\u1EA0\302\u1EA6\u1EA4\u1EA8\u1EAA\u1EAC\u0102\u1EB0\u1EAE\u1EB2\u1EB4\u1EB6\310\311\u1EBA\u1EBC\u1EB8\312\u1EC0\u1EBE\u1EC2\u1EC4\u1EC6\314\315\u1EC8\u0128\u1ECA\322\323\u1ECE\325\u1ECC\324\u1ED2\u1ED0\u1ED4\u1ED6\u1ED8\u01A0\u1EDC\u1EDA\u1EDE\u1EE0\u1EE2\331\332\u1EE6\u0168\u1EE4\u01AF\u1EEA\u1EE8\u1EEC\u1EEE\u1EF0\u1EF2\335\u1EF6\u1EF8\u1EF4\u0110", "aaaaaaaaaaaaaaaaaeeeeeeeeeeeiiiiiooooooooooooooooouuuuuuuuuuuyyyyydAAAAAAAAAAAAAAAAAEEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYYD");
    }

    public static String clearHornTCVN(String strSource) {
        return convertCharForm(strSource, "\265\270\266\267\271\251\307\312\310\311\313\250\273\276\274\275\306\314\320\316\317\321\252\322\325\323\324\326\327\335\330\334\336\337\343\341\342\344\253\345\350\346\347\351\254\352\355\353\354\356\357\363\361\362\364\255\365\370\366\367\371\372\375\373\374\376\256\265\270\266\267\271\251\307\312\310\311\313\250\273\276\274\275\306\314\320\316\317\321\252\322\325\323\324\326\327\335\330\334\336\337\343\341\342\344\253\345\350\346\347\351\254\352\355\353\354\356\357\363\361\362\364\255\365\370\366\367\371\372\375\373\374\376\256", "aaaaaaaaaaaaaaaaaeeeeeeeeeeeiiiiiooooooooooooooooouuuuuuuuuuuyyyyydAAAAAAAAAAAAAAAAAEEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYYD");
    }

    public static String pronounceVietnameseNumber(long lNumber) {
        String strUnit[] = {
            "", "ngh\354n", "tri\u1EC7u", "t\u1EF7", "ngh\354n t\u1EF7", "tri\u1EC7u t\u1EF7", "ngh\354n tri\u1EC7u t\u1EF7", "t\u1EF7 t\u1EF7"
        };
        byte btDecimalNumber[] = new byte[30];
        byte btDecimalCount = 0;
        boolean bNegative = lNumber < 0L;
        if (bNegative) {
            lNumber = -lNumber;
        }
        while (lNumber > 0L) {
            byte btValue = (byte) (int) (lNumber - 10L * (lNumber / 10L));
            lNumber /= 10L;
            btDecimalNumber[btDecimalCount++] = btValue;
        }
        String strReturn = "";
        for (int iUnitIndex = 0; iUnitIndex < strUnit.length && iUnitIndex * 3 < btDecimalCount; iUnitIndex++) {
            String str = pronounceVietnameseNumber(btDecimalNumber[iUnitIndex * 3], btDecimalNumber[iUnitIndex * 3 + 1], btDecimalNumber[iUnitIndex * 3 + 2], iUnitIndex * 3 + 2 < btDecimalCount);
            if (str.length() <= 0) {
                continue;
            }
            if (strReturn.length() > 0) {
                strReturn = str + " " + strUnit[iUnitIndex] + " " + strReturn;
            } else {
                strReturn = str + " " + strUnit[iUnitIndex];
            }
        }

        if (bNegative) {
            strReturn = "\342m " + strReturn;
        }
        return strReturn;
    }

    private static String pronounceVietnameseNumber(byte bUnit, byte bTen, byte bHundred, boolean bMax) {
        if (bUnit == 0 && bTen == 0 && bHundred == 0) {
            return "";
        }
        String strUnitSuffix[] = {
            "", "m\u1ED9t", "hai", "ba", "t\u01B0", "l\u0103m", "s\341u", "b\u1EA3y", "t\341m", "ch\355n"
        };
        String strUnitTen[] = {
            "", "m\u01B0\u1EDDi m\u1ED9t", "m\u01B0\u1EDDi hai", "m\u01B0\u1EDDi ba", "m\u01B0\u1EDDi b\u1ED1n", "m\u01B0\u1EDDi l\u0103m", "m\u01B0\u1EDDi s\341u", "m\u01B0\u1EDDi b\u1EA3y", "m\u01B0\u1EDDi t\341m", "m\u01B0\u1EDDi ch\355n"
        };
        String strUnit[] = {
            "", "m\u1ED9t", "hai", "ba", "b\u1ED1n", "n\u0103m", "s\341u", "b\u1EA3y", "t\341m", "ch\355n"
        };
        String strTenFirst[] = {
            "", "m\u01B0\u1EDDi m\u1ED9t", "hai m\u01B0\u01A1i m\u1ED1t", "ba m\u01B0\u01A1i m\u1ED1t", "b\u1ED1n m\u01B0\u01A1i m\u1ED1t", "n\u0103m m\u01B0\u01A1i m\u1ED1t", "s\341u m\u01B0\u01A1i m\u1ED1t", "b\u1EA3y m\u01B0\u01A1i m\u1ED1t", "t\341m m\u01B0\u01A1i m\u1ED1t", "ch\355n m\u01B0\u01A1i m\u1ED1t"
        };
        String strTen[] = {
            "", "m\u01B0\u1EDDi", "hai m\u01B0\u01A1i", "ba m\u01B0\u01A1i", "b\u1ED1n m\u01B0\u01A1i", "n\u0103m m\u01B0\u01A1i", "s\341u m\u01B0\u01A1i", "b\u1EA3y m\u01B0\u01A1i", "t\341m m\u01B0\u01A1i", "ch\355n m\u01B0\u01A1i"
        };
        String strHundred[] = {
            "kh\364ng tr\u0103m", "m\u1ED9t tr\u0103m", "hai tr\u0103m", "ba tr\u0103m", "b\u1ED1n tr\u0103m", "n\u0103m tr\u0103m", "s\341u tr\u0103m", "b\u1EA3y tr\u0103m", "t\341m tr\u0103m", "ch\355n tr\u0103m"
        };
        String strReturn = "";
        if (bMax || bHundred > 0) {
            strReturn = strHundred[bHundred];
        }
        if (bTen > 0) {
            if (strReturn.length() > 0) {
                strReturn = strReturn + " ";
            }
            if (bUnit > 0) {
                if (bTen == 1) {
                    strReturn = strReturn + strUnitTen[bUnit];
                } else if (bUnit == 1) {
                    strReturn = strReturn + strTenFirst[bTen];
                } else {
                    strReturn = strReturn + strTen[bTen] + " " + strUnitSuffix[bUnit];
                }
            } else {
                strReturn = strReturn + strTen[bTen];
            }
        } else if (bUnit > 0) {
            if (strReturn.length() > 0) {
                strReturn = strReturn + " linh " + strUnit[bUnit];
            } else {
                strReturn = strUnit[bUnit];
            }
        }
        return strReturn;
    }

    public static String align(String str, int iAlignment, int iLength, char c) {
        if (str == null) {
            return null;
        }
        if (str.length() > iLength) {
            return str.substring(0, iLength);
        }
        StringBuilder buf = new StringBuilder();
        if (iAlignment == 0) {
            str = lpad(str, str.length() + (iLength - str.length()) / 2, c);
            return rpad(str, iLength, c);
        }
        if (iAlignment == 2) {
            return lpad(str, iLength, c);
        }
        if (iAlignment == 1) {
            return rpad(str, iLength, c);
        } else {
            return buf.toString();
        }
    }

    public static String align(String str, int iAlignment, int iLength) {
        return align(str, iAlignment, iLength, ' ');
    }

    public static String lpad(String str, int iLength) {
        return lpad(str, iLength, ' ');
    }

    public static String rpad(String str, int iLength) {
        return rpad(str, iLength, ' ');
    }

    public static String lpad(String str, int iLength, char c) {
        if (str == null) {
            return null;
        }
        int iCount = iLength - str.length();
        if (iCount > 0) {
            return createMonoString(c, iCount) + str;
        } else {
            return str;
        }
    }

    public static String rpad(String str, int iLength, char c) {
        if (str == null) {
            return null;
        }
        int iCount = iLength - str.length();
        if (iCount > 0) {
            return str + createMonoString(c, iCount);
        } else {
            return str;
        }
    }

    public static String createMonoString(char c, int iLength) {
        StringBuilder buf = new StringBuilder();
        for (int iIndex = 0; iIndex < iLength; iIndex++) {
            buf.append(c);
        }

        return buf.toString();
    }

    public static String join(String items[], String delim) {
        if (items == null || items.length == 0) {
            return "";
        }
        StringBuilder sbuf = new StringBuilder();
        for (int i = 0; i < items.length; i++) {
            sbuf.append(items[i]);
            if (i < items.length - 1) {
                sbuf.append(delim);
            }
        }

        return sbuf.toString();
    }

    public static String join(ArrayList vt, String delim) {
        if (vt == null || vt.isEmpty()) {
            return "";
        }
        StringBuilder sbuf = new StringBuilder();
        for (int i = 0; i < vt.size(); i++) {
            sbuf.append(nvl(vt.add(i), ""));
            if (i < vt.size()) {
                sbuf.append(delim);
            }
        }

        return sbuf.toString();
    }

    public static String capitalize(String str) {
        int iOffset = 0;
        StringBuilder buf = new StringBuilder();
        do {
            if ((iOffset = indexOfLetter(str, buf.length())) < 0) {
                break;
            }
            buf.append(str.substring(buf.length(), iOffset));
            buf.append(Character.toUpperCase(str.charAt(buf.length())));
            iOffset = indexOfSpace(str, buf.length());
            if (iOffset < 0) {
                buf.append(str.substring(buf.length(), str.length()).toLowerCase());
                break;
            }
            buf.append(str.substring(buf.length(), iOffset).toLowerCase());
        } while (true);
        return buf.toString();
    }

    /**
     * Hàm che thông tin số CMND bằng dấu *<br>
     * Ẩn 3 chữ số cuối trong số CMND bằng dấu
     *
     *
     * @author thuclh
     */
    public static String genIdentityNumber(String identityNumber) {
        return identityNumber.substring(0, 6) + "***";
    }

    /**
     * Hàm che thông tin số điện thoại bằng dấu * <br>
     * Ẩn 4 chữ số cuối trong số CMND bằng dấu
     *
     *
     * @author thuclh
     */
    public static String genMobile(String mobile) {
        return mobile.substring(0, 6) + "****";
    }
    public static final String CHARFORM_NOHORN = "aaaaaaaaaaaaaaaaaeeeeeeeeeeeiiiiiooooooooooooooooouuuuuuuuuuuyyyyydAAAAAAAAAAAAAAAAAEEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYYD";
    public static final String CHARFORM_UNICODE = "\340\341\u1EA3\343\u1EA1\342\u1EA7\u1EA5\u1EA9\u1EAB\u1EAD\u0103\u1EB1\u1EAF\u1EB3\u1EB5\u1EB7\350\351\u1EBB\u1EBD\u1EB9\352\u1EC1\u1EBF\u1EC3\u1EC5\u1EC7\354\355\u1EC9\u0129\u1ECB\362\363\u1ECF\365\u1ECD\364\u1ED3\u1ED1\u1ED5\u1ED7\u1ED9\u01A1\u1EDD\u1EDB\u1EDF\u1EE1\u1EE3\371\372\u1EE7\u0169\u1EE5\u01B0\u1EEB\u1EE9\u1EED\u1EEF\u1EF1\u1EF3\375\u1EF7\u1EF9\u1EF5\u0111\300\301\u1EA2\303\u1EA0\302\u1EA6\u1EA4\u1EA8\u1EAA\u1EAC\u0102\u1EB0\u1EAE\u1EB2\u1EB4\u1EB6\310\311\u1EBA\u1EBC\u1EB8\312\u1EC0\u1EBE\u1EC2\u1EC4\u1EC6\314\315\u1EC8\u0128\u1ECA\322\323\u1ECE\325\u1ECC\324\u1ED2\u1ED0\u1ED4\u1ED6\u1ED8\u01A0\u1EDC\u1EDA\u1EDE\u1EE0\u1EE2\331\332\u1EE6\u0168\u1EE4\u01AF\u1EEA\u1EE8\u1EEC\u1EEE\u1EF0\u1EF2\335\u1EF6\u1EF8\u1EF4\u0110";
    public static final String CHARFORM_TCVN = "\265\270\266\267\271\251\307\312\310\311\313\250\273\276\274\275\306\314\320\316\317\321\252\322\325\323\324\326\327\335\330\334\336\337\343\341\342\344\253\345\350\346\347\351\254\352\355\353\354\356\357\363\361\362\364\255\365\370\366\367\371\372\375\373\374\376\256\265\270\266\267\271\251\307\312\310\311\313\250\273\276\274\275\306\314\320\316\317\321\252\322\325\323\324\326\327\335\330\334\336\337\343\341\342\344\253\345\350\346\347\351\254\352\355\353\354\356\357\363\361\362\364\255\365\370\366\367\371\372\375\373\374\376\256";
    public static final int ALIGN_CENTER = 0;
    public static final int ALIGN_LEFT = 1;
    public static final int ALIGN_RIGHT = 2;
}
