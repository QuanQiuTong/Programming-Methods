package com.project1;

import org.openjdk.jmh.annotations.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)

public class MyStringBenchmark {

    // Generate a more realistic set of test data
    private static final char[] VALUE = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.".toCharArray();
    private static final MyString MY_STRING = new MyString(VALUE);
    private static final String STANDARD_STRING = new String(VALUE);

    private static final char[] PATTERN = "dolore".toCharArray();
    private static final char[] REPLACEMENT = "happiness".toCharArray();

    // Randomly generated long input with variability in characters
    private static final char[] LONG_VALUE = generateRandomCharArray(10000, 1); // Length: 10,000, 5% pattern
    private static final MyString LONG_MY_STRING = new MyString(LONG_VALUE);
    private static final String LONG_STANDARD_STRING = new String(LONG_VALUE);

    // Method to generate a char array with a specified percentage of pattern occurrences
    private static char[] generateRandomCharArray(int length, int patternFrequencyPercent) {
        Random random = new Random();
        char[] array = new char[length];
        for (int i = 0; i < length; i++) {
            // Randomly decide to insert the pattern at certain positions based on frequency
            if (random.nextInt(100) < patternFrequencyPercent && i <= length - PATTERN.length) {
                // Insert pattern
                for (int j = 0; j < PATTERN.length; j++) {
                    array[i + j] = PATTERN[j];
                }
                i += PATTERN.length - 1; // Skip over the pattern
            } else {
                // Insert a random character
                array[i] = (char) ('a' + random.nextInt(26)); // Random lowercase letter
            }
        }
        return array;
    }

    // Benchmark for short input
    @Benchmark
    public int myStringIndexOfShort() {
        return MY_STRING.indexOf(PATTERN);
    }

    @Benchmark
    public MyString myStringReplaceShort() {
        return MY_STRING.replace(PATTERN, REPLACEMENT);
    }

    @Benchmark
    public int standardStringIndexOfShort() {
        return STANDARD_STRING.indexOf("dolore");
    }

    @Benchmark
    public String standardStringReplaceShort() {
        return STANDARD_STRING.replace("dolore", "happiness");
    }

    // Benchmark for long input
    @Benchmark
    public int myStringIndexOfLong() {
        return LONG_MY_STRING.indexOf(PATTERN);
    }

    @Benchmark
    public MyString myStringReplaceLong() {
        return LONG_MY_STRING.replace(PATTERN, REPLACEMENT);
    }

    @Benchmark
    public int standardStringIndexOfLong() {
        return LONG_STANDARD_STRING.indexOf("dolore");
    }

    @Benchmark
    public String standardStringReplaceLong() {
        return LONG_STANDARD_STRING.replace("dolore", "happiness");
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(MyStringBenchmark.class.getSimpleName())
                .build();
        new Runner(options).run();
    }
}
/**
 * Results:

Benchmark                                     Mode  Cnt      Score      Error  Units
MyStringBenchmark.myStringIndexOfLong         avgt    5    160.580 ±    1.375  ns/op
MyStringBenchmark.myStringIndexOfShort        avgt    5    113.858 ±   31.243  ns/op
MyStringBenchmark.myStringReplaceLong         avgt    5  28347.729 ± 4823.478  ns/op
MyStringBenchmark.myStringReplaceShort        avgt    5    280.999 ±   50.287  ns/op
MyStringBenchmark.standardStringIndexOfLong   avgt    5      4.750 ±    1.287  ns/op
MyStringBenchmark.standardStringIndexOfShort  avgt    5     19.718 ±    4.710  ns/op
MyStringBenchmark.standardStringReplaceLong   avgt    5   5625.513 ±  374.832  ns/op
MyStringBenchmark.standardStringReplaceShort  avgt    5     72.153 ±    2.637  ns/op

 * Sometimes 'IndexOfLong' takes shorter time just because the pattern is found at about the beginning 100 positions of the string, 
 * while 'IndexOfShort' takes longer time because the pattern is found at the 103rd character. Their names does not matter.
 */