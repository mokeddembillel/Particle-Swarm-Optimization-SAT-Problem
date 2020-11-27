package com.company;

import java.io.File;
import java.util.ArrayList;

public class PSO {
    public SAT sat;
    public Integer particlesNumber;
    public Integer iterationsNumber;
    public double c1, c2, w, r1, r2;
    public Integer GBest, GBestError;
    public ArrayList<ArrayList<Integer>> particlesTable = new ArrayList<>();

    public PSO() {
        try {
            String filePath = new File("").getAbsolutePath();
            filePath = filePath + "\\res\\uf20-01.cnf";
            sat = new SAT(filePath);
        } catch (Exception e) {
            System.out.println("Can not read the file " + e);
        }

        particlesNumber = 100;
        iterationsNumber = 1000;
        w = 1.0;
        r1 = Math.random();
        r2 = Math.random();
        c1 = 0.5;
        c2 = 2;

        for (int i = 0; i < particlesNumber; i++) {
            particlesTable.add(new ArrayList<>());
        }
    }

    public void PSOFunction() {
        // Initialize N particles with positions, velocities and PBest
        for (ArrayList<Integer> particle : particlesTable) {
            particle.add((int)(Math.random() * ((sat.searchSpaceSize) + 1)));
            particle.add((int)(Math.random() * ((sat.variablesNumber) + 1)));
            //particle.add(0);
            //particle.add(1);
            particle.add(particle.get(0));
            particle.add(fitnessFunction(particle.get(2)));
        }
        // Calculate GBest
        GBestError = particlesTable.get(0).get(3);
        System.out.println(GBestError);

        GBest = particlesTable.get(0).get(2);
        for (int i = 1; i < particlesNumber; i++) {
            if (particlesTable.get(i).get(3) < GBestError) {
                GBestError = particlesTable.get(i).get(3);
                GBest = particlesTable.get(i).get(2);
            }
        }

        for(int i = 0; i < iterationsNumber; i++) {
            for(ArrayList<Integer> particle : particlesTable) {
                // Update the velocity
                particle.set(1, (int) (w * particle.get(1) + c1*r1*(particle.get(2) - particle.get(0)) + c2*r2*(GBest - particle.get(0))));
                // Move the particle
                particle.set(0, particle.get(0) + particle.get(1));
                // Evaluate the fitness and Update  PBest
                int newError = fitnessFunction(particle.get(0));
                if (newError < particle.get(3)) {
                    particle.set(2, particle.get(0));
                    particle.set(3, newError);
                }

            }
            // Update GBest
            for (int j = 0; j < particlesNumber; j++) {
                if (particlesTable.get(j).get(3) < GBestError) {
                    GBestError = particlesTable.get(j).get(3);
                    GBest = particlesTable.get(j).get(2);
                }
            }
            System.out.println("Iteration " + i + " => GBest = " + GBest + " With accuracy = " + GBestError);
        }

    }

    public Integer fitnessFunction(Integer model) {
        ArrayList<Integer> formattedModel = sat.modelAdapter(model);
        Integer UCNumber = sat.clausesNumber;
        for (ArrayList<Integer> clause: sat.clausesList) {
            for (Integer literal : clause) {
                if (literal * formattedModel.get(Math.abs(literal) - 1) > 0) {
                    UCNumber--;
                    break;
                }
            }
        }
        return UCNumber;
    }

    public Integer calculateDistance(ArrayList<Integer> x, ArrayList<Integer> y) {
        Integer distance = 0;
        for (int i = 0; i < x.size(); i++) {
            if (x.get(i) * y.get(i) < 0) {
                distance++;
            }
        }
        return distance;
    }




}
