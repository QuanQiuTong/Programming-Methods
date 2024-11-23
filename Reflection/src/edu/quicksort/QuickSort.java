package edu.quicksort;

import edu.fudan.ISort;

public class QuickSort implements ISort {
    public int[] sort(int[] input) {
        System.out.println("Algorithm:\tQuickSort");

        quickSort(input, 0, input.length - 1);

        return input;
    }

    private void quickSort(int[] input, int low, int high) {
        if (low < high) {
            int pi = partition(input, low, high);
            quickSort(input, low, pi - 1);
            quickSort(input, pi + 1, high);
        }
    }

    private int partition(int[] input, int low, int high) {
        int pivot = input[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (input[j] < pivot) {
                i++;
                int temp = input[i];
                input[i] = input[j];
                input[j] = temp;
            }
        }
        int temp = input[i + 1];
        input[i + 1] = input[high];
        input[high] = temp;
        return i + 1;
    }
}
