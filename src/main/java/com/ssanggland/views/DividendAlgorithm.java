package com.ssanggland.views;

import java.util.Scanner;

public class DividendAlgorithm {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("홈 OVR : ");
            int home = scanner.nextInt();
            System.out.print("\n어웨이 OVR : ");
            int away = scanner.nextInt();
            if (home == 0)
                break;
            home += 5;
            double homeAttack = (((double) home / 10.0 - (double) away / 20.0) / 2.0);
            double awayAttack = (((double) away / 10.0 - (double) home / 20.0) / 2.0);
            double homeDividend = ((((homeAttack*1000)+(awayAttack*1000))*1.2)/(homeAttack*1000))+0.06;
            double awayDividend = ((((awayAttack*1000)+(homeAttack*1000))*1.2)/(awayAttack*1000));
            double drawDividend = DrawDividendCalculator(homeAttack,awayAttack);//(homeAttack+awayAttack)/2/homeAttack+(homeAttack+awayAttack)/2/awayAttack;

            String homeDividendformat = String.format("%.2f",homeDividend);
            String awayDividendformat = String.format("%.2f",awayDividend);
            String drawDividendformat = String.format("%.2f",drawDividend);
            System.out.println(" Home : " + homeDividendformat + " Draw : " + drawDividendformat + " Away : " + awayDividendformat);
        }
    }
    private static double DrawDividendCalculator(double homeD, double awayD) {
        double a, b;
        if(homeD > awayD) {
            a = homeD;
            b = awayD;
        }
        else {
            a = awayD;
            b = homeD;
        }
        return (a+b)/1.77/a+(a+b)/1.25/b;
    }
}
