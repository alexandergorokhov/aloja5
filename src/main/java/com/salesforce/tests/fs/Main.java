package com.salesforce.tests.fs;

import com.salesforce.tests.fs.fs.FsSimulation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * The entry point for the Test program
 */
public class Main {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        FsSimulation fsSimulation = new FsSimulation();
        fsSimulation.simulation();
    }
}
