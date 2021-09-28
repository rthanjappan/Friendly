package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Friendly {

    String[] persons;
    String[][] personFriends;


    public static void main(String[] args) {

        Friendly friendly = new Friendly();
        friendly.readFriends("friends.txt");

        friendly.getInputOutput();
    }

    //do not change this method
    void getInputOutput() {
        int choice = 0;
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("*** Welcome to Friendly! ***");
            System.out.println("1. Find the number of friends a person has");
            System.out.println("2. Find the number of common friends between two persons");
            System.out.println("3. Find the names of common friends between two persons");
            System.out.println("4. Exit");
            choice = input.nextInt();
            input.nextLine(); //clear the buffer
            switch (choice) {
                case 1: {
                    System.out.println("Enter the person's name");
                    String name = input.nextLine();
                    String[] friends = findFriends(name);
                    if (friends != null) {
                        System.out.printf("%s has %d friends%n", name, friends.length);
                        int count = 0;
                        for (String s : friends ) {
                            System.out.println(++count + ". " + s );
                        }
                    } else System.out.println("Sorry! No friends found!");
                    System.out.println("-----------------------------");
                    break;
                }
                case 2: {
                    System.out.println("Enter first person's name");
                    String name1 = input.nextLine();
                    System.out.println("Enter second person's name");
                    String name2 = input.nextLine();
                    System.out.printf("%s and %s have %d common friends%n", name1, name2, countCommonFriends(name1, name2));
                    System.out.println("-----------------------------");
                    break;
                }
                case 3: {
                    System.out.println("Enter first person's name");
                    String name1 = input.nextLine();
                    System.out.println("Enter second person's name");
                    String name2 = input.nextLine();
                    String[] commonFriends = findCommonFriends(name1, name2);
                    if (commonFriends != null) {
                        System.out.printf("%s and %s have %d common friends%n", name1, name2, commonFriends.length);
                        int count = 0;
                        for (String s : commonFriends) {
                            System.out.println(++count + ". " + s);
                        }
                    } else System.out.println("Sorry! No match found!");
                    System.out.println("-----------------------------");
                    break;
                }
                default: System.out.println("Goodbye!");break;
            }
        } while (choice != 4);
        input.close();
    }


    /** readFriends() reads the file with filename to
     * populate persons and personFriends arrays
     */
    void readFriends(String filename) {


        String[] personData=null;
        try {

            int count=0;
            File personFile = new File(filename);
            Scanner myReader = new Scanner(personFile);
            while (myReader.hasNextLine()) {
                myReader.nextLine();
                count++;
            }
            myReader.close();
            //System.out.println(count);
            personFile = new File(filename);
            Scanner myReader1 = new Scanner(personFile);
            personData=new String[count];
            int i=0;
            while (myReader1.hasNextLine()) {
                String data = myReader1.nextLine();
                personData[i++]=data;
            }
            myReader1.close();
        } catch (FileNotFoundException e) {
            System.out.println(filename+" not found.");
            e.printStackTrace();
        }

        /**
         * Once the data is read from file,
         * the data is processed and placed to an Array
         */


        persons=new String[personData.length];
        personFriends=new String[personData.length][];
        for(int i=0;i<personData.length;i++) {

            //System.out.println(personData.get(i));

            String[] words = personData[i].split(": ");
//            for (String s : words) {
//                System.out.println("s =" + s);
//            }

            if (words.length == 1) {
                persons[i] = words[0];
            } else if (words.length == 2) {
                persons[i] = words[0];
                words = words[1].split(", ");
//                for (String s : words) {
//                    System.out.println("words[1] =" + s);
//                }
                if (words.length > 0) {
                    personFriends[i] = new String[words.length];
                    int j = 0;
                    while (j < words.length) {
                        personFriends[i][j] = words[j];
                        j++;
                    }
                }
            }
        }
        System.out.println("From process person data : ");
        System.out.print("Persons : ");
        for (String s : persons) {
            System.out.print(s + "   : ");
        }
        System.out.println("\nPerson Friends : ");
        for (String[] s : personFriends) {
            for (String d : s) {
                System.out.print(d + ", ");
            }

            System.out.println();

        }



    }



    /** given a name, returns an array of friends a person has
     * If the name is not found, it returns null
     */
    String[] findFriends(String name) {
        int index=-1;
        for(int i=0;i<persons.length;i++){
            if(name.compareTo(persons[i])==0) {
                index = i;
                break;
            }
        }
        if(index==-1){
            return null;
        }
        if(personFriends[index].length>0) {
            return personFriends[index];
        }else {

            return null;
        }
    }

    /** given two names, returns how many common friends they have */
    int countCommonFriends(String name1, String name2) {
        String[] commonFriends= findCommonFriends(name1,name2);
        if(commonFriends==null){
            return 0;
        }
        else{
            return commonFriends.length;
        }
    }

    /**given two names, returns an array of names of common friends.
     * If there are no common friends, then it returns an empty array, i.e. array of size 0*/
    String[] findCommonFriends(String name1, String name2) {
        int index1=-1,index2=-1;
        for(int i=0;i<persons.length;i++){
            if(name1.compareTo(persons[i])==0) {
                index1 = i;

            }
            if(name2.compareTo(persons[i])==0) {
                index2 = i;

            }
        }
        if(index1==-1|| index2==-1){
            return null;
        }
        if(personFriends[index1].length>0 || personFriends.length>0) {
            //ArrayList<String> commonFriends=new ArrayList<>();
            String[] commonFriends=new String[persons.length];
            int k=0,size=0;
            for(int i=0;i<personFriends[index1].length;i++){
                for(int j=0;j<personFriends[index2].length;j++){
                    if(personFriends[index1][i].compareTo(personFriends[index2][j])==0){
                        commonFriends[size]=personFriends[index1][i];
                        size++;
                        break;
                    }
                }
            }
            String[] originalFriends= new String[size];
            for(int i=0;i<size;i++){

                originalFriends[i]=commonFriends[i];

            }
            return originalFriends;
        }else {

            return null;
        }



    }

}


