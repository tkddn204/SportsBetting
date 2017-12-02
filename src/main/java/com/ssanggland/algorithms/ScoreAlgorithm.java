package com.ssanggland.algorithms;

import com.ssanggland.models.enumtypes.KindOfDividend;

import java.util.Scanner;

public class ScoreAlgorithm {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("홈 OVR : ");
            int home = scanner.nextInt();
            System.out.print("\n어웨이 OVR : ");
            int away = scanner.nextInt();
            if(home == 0)
                break;
            calculate(home, away);
        }
    }
    public static int[] calculate(int homeOVR, int awayOVR) {
        homeOVR += 5;
        double homeAttack = (((double) homeOVR / 10.0 - (double) awayOVR / 20.0) / 2.0);
        double awayAttack = (((double) awayOVR / 10.0 - (double) homeOVR / 20.0) / 2.0);
        int homeScore=0, awayScore=0;
        for(int i=0;i<100;i++) {
            if ((int) (1000 * homeAttack) >= (int) (Math.random() * 100000) + 1)
                homeScore++;
            if ((int) (1000 * awayAttack) >= (int) (Math.random() * 100000) + 1)
                awayScore++;
        }
        int[] result = {homeScore, awayScore};
        return result;
//        String result = "\nHome " + homeScore + " vs " + awayScore + " Away\n";
//        System.out.println(result);
    }
}
