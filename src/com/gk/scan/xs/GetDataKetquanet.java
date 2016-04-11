/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gk.scan.xs;

import com.gk.utils.HtmlTool;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author TUANPLA
 */
public class GetDataKetquanet {
    //  http://ketqua.net/xo-so/kqauto/kqmb.html?t=1368530248

    static final HashMap<String, String> MAP_MATINH_KETQUA_NET = new HashMap<>();

    static {
        MAP_MATINH_KETQUA_NET.put("AG", "ag");
        MAP_MATINH_KETQUA_NET.put("BL", "bl");
        MAP_MATINH_KETQUA_NET.put("BTR", "btr");
    }

    public static void getdataByCode(String code) {
        String time = System.currentTimeMillis() + "";
        time = time.substring(0, 10);
        String url = "http://ketqua.net/xo-so/kqauto/kq" + code.toLowerCase() + ".html?t=" + time;
        try {
            Document document = Jsoup.connect(url).userAgent(HtmlTool.getRanUserAgent()).get().outputSettings(new Document.OutputSettings().charset("utf8"));
            if (document.hasText()) {
                Elements allNote = document.select("div");
                if (allNote != null && allNote.size() > 0) {
                    Element kqAll = allNote.first();
                    System.out.println(HtmlTool.htmlCode2Vn(kqAll.html()));
                }
            } else {
                System.out.println("Chua co ket qua");
            }
        } catch (Exception e) {
            System.out.println("Lay Ket qua ketqua.net loi:" + e.getMessage());
        }
    }
}
