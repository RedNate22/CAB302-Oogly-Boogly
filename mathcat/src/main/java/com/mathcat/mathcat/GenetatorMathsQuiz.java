package com.mathcat.mathcat;

import java.util.Random;

public class GenetatorMathsQuiz {

    static void randomize (int arr[], int n){
        Random r = new Random();

        int a = r.nextInt(10)+1;
        int b = r.nextInt(10)+1;

        System.out.println(a + " + " + b + " = " + (a + b));

    }

}