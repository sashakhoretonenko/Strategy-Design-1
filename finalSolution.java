import java.util.Arrays;
import java.util.List;

public class finalSolution implements Student {
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
    double offset = 10;
    // only can calculate if S != 0
    if(S != 0){
      expectedNumBetterStudents = Math.max(0, (int)((N * (1 - aptitude/S)) - offset));
    }
    
    // Part A
    // if all schools are equal, and student aptitudesjust run synergist
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
        int end = (int) Math.min(N, expectedNumBetterStudents + 5);
        int[] ret = new int[10];

        if (start > N - 11) {
          start = N - 10;
          end = N;
        } else if (end < 10) {
          start = 0;
          end = 10;
        }

        int counter = 0;
        for(int i = start; i < end; i++){
            ret[counter] = i;
            counter++;
        }
        
        return ret;
    }


    // Part C
    // get bounds
    double k = 5.0; // Constant for size of our bounds
    double range = Math.max(5.0, k * (W/T));
    int bottomBound = Math.max(0, (int) (expectedNumBetterStudents - range));

    // get list of schools
    School[] schoolRange = new School[N - bottomBound];
    for (int i = bottomBound; i < N; i++) {
      School s = new School(i, synergies.get(i) + schools.get(i));
      schoolRange[i - bottomBound] = s;
    }
    Arrays.sort(schoolRange);
    
    // paste best choices into ret array
    int[] ret = new int[10];
    for (int i = 0; i < 10; i++)
    {
      ret[i] = schoolRange[i].index;
    }

    // get best school
    School[] allSchools = new School[N];
    for (int i = 0; i < N; i++) {
      School s = new School(i, synergies.get(i) + schools.get(i));
      allSchools[i] = s;
    }
    Arrays.sort(allSchools);
    School bestSchool = allSchools[0];

    // add best school to the list at the top
    if (bestSchool.index != ret[0])
    {
      for (int i = ret.length - 1; i >= 1; i--)
      {
        ret[i] = ret[i-1];
      }
      ret[0] = bestSchool.index;
    }
    
    return ret;

  }
}
