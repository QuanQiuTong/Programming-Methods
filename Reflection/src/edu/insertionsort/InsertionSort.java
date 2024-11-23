package edu.insertionsort;
import edu.fudan.ISort;

public class InsertionSort implements ISort {
    public int [] sort(int [] input) {
        System.out.println("InsertionSort");
        for (int i = 1; i < input.length; i++) {
            int key = input[i];
            int j = i - 1;
            while (j >= 0 && input[j] > key) {
                input[j + 1] = input[j];
                j = j - 1;
            }
            input[j + 1] = key;
        }
        return input;
    }
    
}
