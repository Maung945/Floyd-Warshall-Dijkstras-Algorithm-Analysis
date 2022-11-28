/**
 * Author: Myo Aung & Jihun Choi:
 * Class: CS3310-02 - Project2 Fall-2022
 * Main.java
 */

public class Main {
    public static void main(String[] args) {
        Test tests = new Test();
        tests.runSanityChecks();
        tests.runTests(3, 4);           // Change up number of runs and the size of matrix as needed
    }
}
