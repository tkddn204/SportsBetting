package com.ssanggland.views;

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
            home+=5;
            double homeAttack = (((double) home / 10.0 - (double) away / 20.0) / 2.0);
            double awayAttack = (((double) away / 10.0 - (double) home / 20.0) / 2.0);
            int homeScore=0, awayScore=0;
            for(int i=0;i<100;i++) {
                if ((int) (1000 * homeAttack) >= (int) (Math.random() * 100000) + 1)
                    homeScore++;
                if ((int) (1000 * awayAttack) >= (int) (Math.random() * 100000) + 1)
                    awayScore++;
            }
            System.out.println("\nHome " + homeScore + " vs " + awayScore + " Away\n");
        }
    }
}
