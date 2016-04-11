/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gk.app;

import com.gk.rescan.thread.QuetLaiXSMB;
import com.gk.rescan.thread.QuetLaiXSMN;
import com.gk.rescan.thread.QuetLaiXSMT;
import com.gk.utils.Tool;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author TUANPLA
 */
public class Moniter extends Thread {

    @Override
    public void run() {
        while (AppStart.runing) {
            try {
                InputStreamReader inR = new InputStreamReader(System.in);
                BufferedReader bR = new BufferedReader(inR);
                String input = bR.readLine();
                if (input!=null && input.startsWith("rerun")) {
                    String[] arr = input.split(" ");
                    AppStart.XS_BACK_DATE = Tool.string2Integer(arr[1]);
                    System.out.println("Quet Lai Ket Qua: [" + AppStart.XS_BACK_DATE + "] ngày");
                    QuetLaiXSMB mb = new QuetLaiXSMB();
                    mb.start();
                    QuetLaiXSMN mn = new QuetLaiXSMN();
                    mn.start();
                    QuetLaiXSMT mt = new QuetLaiXSMT();
                    mt.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
