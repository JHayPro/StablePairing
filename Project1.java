//Jude Hayes
//Project 1
//7/21/2021

import java.util.*;
import java.io.*;

public class Project1 {

    public static void main(String args[]) {
        Project1 p1 = new Project1();//Creates instance of pair

        if (p1.checkStability())//Checks if system is stable matching, returns true if stable
            System.out.println("Stable");
        else
            System.out.println("Unstable");
    }

    int[][] preferenceA;//2d array of preferences
    int[][] pairA;//2d array of pairs

    Project1() {//constructor

        Scanner scanner = null;//creates instance of Scanner

        try {//attempts to open input file, if it fails, exit the program
            scanner = new Scanner(new File("input.txt"));
        } catch (FileNotFoundException e) {
            System.exit(1);
        }

        int pairCount = scanner.nextInt();//reads in the amount of pairs
        preferenceA = new int[pairCount * 2][pairCount * 2 - 1];//resizes pref array based off of pairCount
        pairA = new int[pairCount][2];//resizes pairA based on pairCount

        for (int i = 0; i < preferenceA.length; i++)//uses scanner to add values preferenceA
            for (int j = 0; j < preferenceA[i].length; j++)
                if (scanner.hasNextInt())//exits if input file has run out of values
                    preferenceA[i][j] = scanner.nextInt();
                else
                    System.exit(1);

        for (int i = 0; i < pairA.length; i++)//uses scanner to add values pairA
            for (int j = 0; j < pairA[i].length; j++)
                if (scanner.hasNextInt())//exits if input file has run out of values
                    pairA[i][j] = scanner.nextInt();
                else
                    System.exit(1);
    }

    boolean checkStability() {//Checks if system is stable matching, returns true if stable

        for (int roommateIndex = 0; roommateIndex < preferenceA.length - 1; roommateIndex++) {//Iterates through all roommates to check their pair is stable

            int roommate = roommateIndex + 1; //Sets roommate equal to the roommateIndex + 1
            int roommatePair = getPairedRoommate(roommate);//roommatePair is set equal to the roommate's pair

            for (int roommatePrefIndex = 0; roommatePrefIndex < preferenceA[roommateIndex].length; roommatePrefIndex++) {//Iterates through roommate's preferences until the loop is broken

                int roommatePref = preferenceA[roommateIndex][roommatePrefIndex];//Sets the roommatePref equal to the roommate's current preference in the preference array
                if (roommatePref == roommatePair) //If the roommate's preference is equal to it's pair before it is equal to a roommate that prefers them, then the current roommate is stable
                    break;

                int roommatePrefPair = getPairedRoommate(roommatePref);//sets roommatePrefPair to the roommate's current preference's paired roommate
                for (int roommatePrefPrefIndex = 0; roommatePrefPrefIndex < preferenceA[roommateIndex].length; roommatePrefPrefIndex++) {//Iterates through roommate's current preference's preferences until the loop is broken

                    int roommatePrefPref = preferenceA[roommatePref - 1][roommatePrefPrefIndex];//sets roommatePrefPref equal to the roommate's current preference's current preference
                    if (roommatePrefPref == roommatePrefPair) // if roommatePrefPref is equal to roommatePrefPair then the roommate's current preference does not want to pair with roommate
                        break;
                    if (roommatePrefPref == roommate) //if the roommatePrefPref is equal to roommate then the roommate's current preference wants to pair with the roommate, which means the system is unstable
                        return false;
                }
            }
        }
        return true;
    }

    int getPairedRoommate(int roommate){//returns roommate paired to the given roommate

        for(int j = 0; j < pairA.length; j++) {
            if (pairA[j][0] == roommate)//if roommate's pair is found, return the number of the roommate paired to them
                return pairA[j][1];
            else if (pairA[j][1] == roommate)//if roommate's pair is found, return the number of the roommate paired to them
                return pairA[j][0];
        }

        System.out.println("Unstable");//if no pair is found for the given roommate, the system is unstable
        System.exit(1);
        return -1;
    }
}