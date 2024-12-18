package com.project1;

public class MyString {
    private final char[] value;

    // Constructor that clones the input array to prevent external modification
    public MyString(char[] v) {
        this.value = v.clone();
    }

    // Private constructor used to avoid array copying when necessary
    private MyString(char[] value, boolean noCopy) {
        this.value = value;
    }

    /**
     * Finds the first occurrence of the given pattern (v) in this string.
     * 
     * This method implements a simplified version of the Boyer-Moore algorithm.
     * @param v The pattern to search for
     * @return The starting index of the pattern in the string, or -1 if not found
     */
    public int indexOf(char[] v) {
        int n = value.length; // Length of the text string
        int m = v.length; // Length of the pattern string

        if (m == 0) {
            return 0; // An empty pattern always matches
        }
        if (n < m) {
            return -1; // The text is shorter than the pattern, no match possible
        }

        // Build the shift table for Boyer-Moore-like algorithm
        int[] shift = new int[256]; // Assuming ASCII character set
        for (int i = 0; i < shift.length; i++) {
            shift[i] = m + 1; // Default shift is pattern length + 1
        }
        for (int i = 0; i < m; i++) {
            shift[v[i]] = m - i; // Set shifts based on pattern characters
        }

        // Perform the search
        int i = 0; // Text pointer
        while (i <= n - m) {
            // Compare the pattern with the text
            int j = 0;
            while (j < m && value[i + j] == v[j]) {
                j++;
            }
            if (j == m) {
                return i; // Match found at index i
            }
            // Adjust pointer based on the shift table
            if (i + m < n) {
                i += shift[value[i + m]]; // Use shift table for fast skipping
            } else {
                break;
            }
        }
        return -1; // No match found
    }

    /**
     * Concatenates the given char array to the end of this MyString.
     * 
     * @param v The char array to append
     * @return A new MyString that represents the concatenation of this string and v
     */
    public MyString concat(char[] v) {
        char[] newValue = new char[value.length + v.length];
        System.arraycopy(value, 0, newValue, 0, value.length);
        System.arraycopy(v, 0, newValue, value.length, v.length);
        // Avoid copying the array again
        return new MyString(newValue, true);
    }

    /**
     * Replaces all occurrences of v1 with v2 in this MyString.
     * 
     * @param v1 The pattern to be replaced
     * @param v2 The replacement pattern
     * @return A new MyString with all occurrences of v1 replaced by v2
     */
    public MyString replace(char[] v1, char[] v2) {
        char[] v = new char[value.length];
        outerLoop: for (int i = 0; i < value.length; ++i) {
            if (value[i] == v1[0]) {
                // Check if the pattern v1 matches at the current position
                for (int j = 1; j < v1.length; ++j) {
                    if (i + j >= value.length || value[i + j] != v1[j]) {
                        v[i] = value[i];
                        continue outerLoop;
                    }
                }
                // Replace the found pattern with v2
                for (int j = 0; j < v2.length; ++j) {
                    v[i + j] = v2[j];
                }
                i += v1.length - 1; // Move the index past the replaced part
            } else {
                v[i] = value[i];
            }
        }
        return new MyString(v, true);
    }

    /**
     * Returns the length of this MyString.
     * 
     * @return The length of the underlying char array
     */
    public int length() {
        return value.length;
    }

    /**
     * Returns a copy of the underlying char array.
     * 
     * @return A clone of the char array
     */
    public char[] getValue() {
        return value.clone();
    }
}
