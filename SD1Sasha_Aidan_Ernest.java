// Student_holist.java: sample implementation for Student
// COS 445 HW1, Spring 2018
// Created by Andrew Wonnacott

import java.util.Arrays;
import java.util.List;

public class SD1Sasha_Aidan_Ernest implements Student {
  private class School implements Comparable<School> {
    public School(int i, double q) {
      index = i;
      quality = q;
    }

    private int index;
    private double quality;

    public int compareTo(School n) { // smaller pairs are higher quality
      int ret = Double.compare(n.quality, quality);
      return (ret == 0) ? (Integer.compare(index, n.index)) : ret;
    }
  }

  public int[] getApplications(int N, double S, double T, double W, 
  double aptitude, List<Double> schools, List<Double> synergies) {
    

    double expectedNumBetterStudents = 0;
    // only can calculate if S != 0
    if(S != 0){
      expectedNumBetterStudents = Math.round((N * (1 - aptitude/S)));
    }
    
    // Part A
    // if all schools are equal, and student aptitudes just run synergist
    if(T == 0 && W != 0){
        School[] preferences = new School[schools.size()];
        for (int i = 0; i != synergies.size(); ++i) {
            preferences[i] = new School(i, synergies.get(i));
        }
        Arrays.sort(preferences);
        int[] ret = new int[10];

        for (int i = 0; i != 10; ++i) {
            ret[i] = preferences[i].index;
        }

        return ret;
    }

    //Part B
    /* Turns into university proposed deferred acceptance where every university
    has the same ranking */

    if(W == 0){
        int start = (int) Math.max(0, expectedNumBetterStudents - 5);
        int end = (int) Math.min(N-1, expectedNumBetterStudents + 5);
        int[] ret = new int[10];

        int counter = 0;
        for(int i = start; i != (start + 10) % N; i++){
            ret[counter] = i;
            counter++;
        }
        
        return ret;
    }

    // Part C
    // get bounds
    double k = 10.0; // Constant for size of our bounds
    double range = Math.max(5.0, k * (W/T));
    int topBound = Math.min(N - 1, (int) (expectedNumBetterStudents + range));
    int bottomBound = Math.max(0, (int) (expectedNumBetterStudents - range));

    // get list of schools
    School[] schoolRange = new School[10]; // TODO: 10 won't always work
    for (int i = bottomBound; i < topBound; i++) {
      School s = new School(i, synergies.get(i)); // TODO: Try synergies + qualities as well
      schoolRange[i - bottomBound] = s;
    }

    // sort list of schools
    Arrays.sort(schoolRange);
    int[] ret = new int[10];
    
    for ( int i = schoolRange.length; i >= 0; i-- )
    {
      ret[schoolRange.length - i] = schoolRange[i].index;
    }

    return ret;

  }
}
