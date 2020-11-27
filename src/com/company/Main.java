package com.company;
import java.io.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        /*SAT sp;
        try {
            String filePath = new File("").getAbsolutePath();
            filePath = filePath + "\\res\\uf20-01.cnf";
            sp = new SAT(filePath);
            sp.modelAdapter(999999999);

        } catch(Exception e) {
            System.out.println("Can not read the file " + e);
        }*/

        PSO pso = new PSO();

        pso.PSOFunction();

    }
}
