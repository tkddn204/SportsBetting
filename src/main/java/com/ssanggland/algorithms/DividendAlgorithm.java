package com.ssanggland.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DividendAlgorithm {
//    public static void main(String args[]) {
//        Scanner scanner = new Scanner(System.in);
//        while(true) {
//            System.out.print("홈 OVR : ");
//            int home = scanner.nextInt();
//            System.out.print("\n어웨이 OVR : ");
//            int away = scanner.nextInt();
//
//            System.out.println(" Home : " + homeDividendformat + " Draw : " + drawDividendformat + " Away : " + awayDividendformat);
//        }
//    }
    public static List<Double> calculate(int home, int away) {
        if (home == 0) return null;

        ArrayList<Double> result = new ArrayList<>();

        home += 5;
        double homeAttack = (((double) home / 10.0 - (double) away / 20.0) / 2.0);
        double awayAttack = (((double) away / 10.0 - (double) home / 20.0) / 2.0);
        double homeDividend = ((((homeAttack*1000)+(awayAttack*1000))*1.2)/(homeAttack*1000))+0.06;
        double awayDividend = ((((awayAttack*1000)+(homeAttack*1000))*1.2)/(awayAttack*1000));
        double drawDividend = DrawDividendCalculator(homeAttack,awayAttack);
        //(homeAttack+awayAttack)/2/homeAttack+(homeAttack+awayAttack)/2/awayAttack;

        result.add(homeDividend);
        result.add(awayDividend);
        result.add(drawDividend);

        return result;
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
