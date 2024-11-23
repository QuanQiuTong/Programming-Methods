jar cvfm sort.jar manifest.mf -C bin edu/fudan/ISort.class -C bin edu/fudan/Sort.class

jar cvf quicksort.jar -C bin edu/quicksort/QuickSort.class

jar cvf insertionsort.jar -C bin edu/insertionsort/InsertionSort.class

java -jar sort.jar