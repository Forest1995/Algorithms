/*
 * Name: <your name>
 * EID: <your EID>
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Your solution goes in this class.
 * <p>
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * <p>
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program3 extends AbstractProgram3 {
    /**
     * Determines a solution to the optimal antenna range for the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the optimal solution
     */
    @Override
    public TownPlan OptimalRange(TownPlan town) {
        int n = town.getHouseCount();
        int k = town.getStationCount();
        float[][] range = new float[n][k];
        ArrayList<Float> HousePosition = town.getHousePosition();
        for (int i = 0; i < n; i++) {   //if only 1 base station, the range is equal to half of the farthest houses
            range[i][0] = (HousePosition.get(i) - HousePosition.get(0)) / 2;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < k; j++) {
                float MinRange = Float.MAX_VALUE;
                float RangeForThisCase;
                if (i <= j)
                    range[i][j] = 0; //if number of base station is larger than houses, the range is equal to zero
                else {
                    for (int x = 1; x <= i; x++) {
                        RangeForThisCase = max(range[i - x][j - 1], (HousePosition.get(i) - HousePosition.get(i - x + 1)) / 2);
                        MinRange = min(MinRange, RangeForThisCase);
                    }
                    range[i][j] = MinRange;
                }
            }
        }
        town.setRange(range[n - 1][k - 1]);
        return town;

    }

    /**
     * Determines a solution to the set of base station positions that optimise antenna range for the given input set. Study the
     * project description to understand the variables which } represent the input to your solution.
     *
     * @return Updaed TownPlan town with the optimal solution
     */
    @Override
    public TownPlan OptimalPosBaseStations(TownPlan town) {
        int n = town.getHouseCount();
        int k = town.getStationCount();
        float[][] range = new float[n][k];
        ArrayList<ArrayList<ArrayList<Float>>> c = new ArrayList<ArrayList<ArrayList<Float>>>();
        ArrayList<Float> HousePosition = town.getHousePosition();
        for(int i=0;i<n;i++) {
            c.add(new ArrayList<ArrayList<Float>>());
            for(int j=0;j<k;j++)
                c.get(i).add(new ArrayList<Float>());
        }
        for (int i = 0; i < n; i++) {   //if only 1 base station, the range is equal to half of the farthest houses
            range[i][0] = (HousePosition.get(i) - HousePosition.get(0)) / 2;
            c.get(i).get(0).add( (HousePosition.get(i) + HousePosition.get(0)) / 2);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < k; j++) {
                float MinRange = Float.MAX_VALUE;
                float RangeForThisCase;
                int NumberOfHousesCovered=0;
                if (i <= j) {
                    range[i][j] = 0; //if number of base station is larger than houses, the range is equal to zero
                    for (int z = 0; z <= i; z++)
                        c.get(i).get(j).add(HousePosition.get(z));
                    for(int z=0;z<(j-i);z++)
                        c.get(i).get(j).add(HousePosition.get(0));
                } else {
                    for (int x = 1; x <= i; x++) {
                        RangeForThisCase = max(range[i - x][j - 1], (HousePosition.get(i) - HousePosition.get(i - x + 1)) / 2);
                        if(RangeForThisCase<MinRange) {
                            MinRange = RangeForThisCase;
                            NumberOfHousesCovered=x;
                        }
                    }
                    range[i][j] = MinRange;
                    c.get(i).get(j).addAll(c.get(i-NumberOfHousesCovered).get(j-1));
                    c.get(i).get(j).add( (HousePosition.get(i) + HousePosition.get(i-NumberOfHousesCovered+1)) / 2);
                }
            }
        }
        town.setPositionBaseStations(c.get(n-1).get(k-1));
        return town;
    }


}

