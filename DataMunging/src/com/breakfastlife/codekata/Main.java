package com.breakfastlife.codekata;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//http://codekata.com/kata/kata04-data-munging/
public class Main {

    public static void main(String[] args) {
        List<List<String>> document = readFile("weather.dat");
        System.out.println("Day: " + smallestPointDifference(document, "weather.dat"));
        document = readFile("football.dat");
        System.out.println("Team: " + smallestPointDifference(document, "football.dat"));
    }

    public static String smallestPointDifference(List<List<String>> document, String file){
        int smallestTemp = 0, tempSpread = 0, index1 = 0, index2 = 0, indexReturn = 0, maxInt = 0;
        String returnValue = "";

        //figure out how to go through the document list using this simple nonsense
        if(file.equals("weather.dat")){
            index1 = 1;
            index2 = 2;
            indexReturn = 0;
            maxInt = 1;
        }
        else if(file.equals("football.dat")){
            index1 = 6;
            index2 = 7;
            indexReturn = 1;
        }
        else{
            return "Error, wrong file";
        }

        //go through the document
        for(int i = 1; i < document.size() - maxInt; i++){

            //this is for football, the dash line after line 17 gets turned into an empty list
            if(document.get(i).size() == 0){
                continue;
            }

            try {
                //parse the string in document at row i at specified index 1 and 2.
                tempSpread = Math.abs(Integer.parseInt(document.get(i).get(index1)) - Integer.parseInt(document.get(i).get(index2)));
            } catch (NumberFormatException e){
                //this catch was added for the * character in the weather data
                    String[] temp1 = document.get(i).get(index1).split("\\*");
                    String[] temp2 = document.get(i).get(index2).split("\\*");
                    tempSpread = (Integer.parseInt(temp1[0]) - Integer.parseInt(temp2[0]));
                }
            //set smallest temp spread and return value
            if(smallestTemp > tempSpread || smallestTemp == 0){
                smallestTemp = tempSpread;
                returnValue = document.get(i).get(indexReturn);
            }
        }
        return returnValue;
    }

    //This function is for reading the file and returning a usable list to go through in the following function
    public static List<List<String>> readFile(String file){
        BufferedReader reader;
        List<List<String>> document = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            List<String> stringList = new ArrayList<>();

            //keep out empty and null lines
            while(line != null){
                if(!line.equals("")) {

                    //why is there like 500 empty characters....bruh
                    line.trim();

                    //split the line into an array to iterate through
                    String[] lineArr = line.split(" ");
                    for(int i = 0; i < lineArr.length; i++){

                        //make sure not to add any empty characters or those ridiculous dashes
                        if(!lineArr[i].equals("") && !lineArr[i].contains("-")){
                            stringList.add(lineArr[i]);
                        }
                    }

                    //add the new line array to the document list
                    document.add(stringList);
                    stringList = new ArrayList<>();
                }
                line = reader.readLine();
            }
            //System.out.println(document);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }
}
