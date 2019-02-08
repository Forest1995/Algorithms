/*
 * Name: <Yuesen Lu>
 * EID: <YL33489>
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

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
public class Program1 extends AbstractProgram1 {
    /**
     * Determines whether a candidate Matching represents a solution to the Stable Marriage problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */


    @Override
    public boolean isStableMatching(Matching marriage) {

        int hospital_assigned_for_student1;
        int hospital_assigned_for_student2;
        int n = marriage.getResidentCount();
        int count = 0;
        int slots = marriage.totalHospitalSlots();
        ArrayList<ArrayList<Integer>> hospitalPref = marriage.getHospitalPreference();
        ArrayList<ArrayList<Integer>> ResidentPref = marriage.getResidentPreference();
        ArrayList<Integer> matching = marriage.getResidentMatching();

        if (matching == null)
            return false;
        for (int i = 0; i < n; i++) {       //check whether all slots have been taken
            if (matching.get(i) != -1)
                count = count + 1;
        }
        if (count != slots) {
            return false;
        }

        for (int i = 0; i < n; i++) {      //i is student i
            hospital_assigned_for_student1 = matching.get(i);// representing the index of hospital student i is matched to, -1 if not matched
            for (int j = 0; j < n; j++)   //j is student j
            {
                hospital_assigned_for_student2 = matching.get(j);//representing the index of hospital student j is matched to, -1 if not matched
                if (hospital_assigned_for_student1 == -1 && hospital_assigned_for_student2 != -1) { //if student i is not matched and student 2 is matched
                    if (hospitalPref.get(hospital_assigned_for_student2).indexOf(j) > hospitalPref.get(hospital_assigned_for_student2).indexOf(i)) {
                        //System.out.println(i + " " + j);
                        return false;                                                   //check whether h2 prefers student i than student j.
                    }
                }
                if (hospital_assigned_for_student1 != -1 && hospital_assigned_for_student2 != -1) {   //if both students are matched to a hospital
                    if (hospitalPref.get(hospital_assigned_for_student2).indexOf(j) > hospitalPref.get(hospital_assigned_for_student2).indexOf(i)) {    //check whether h2 prefers student i than student j
                        if (ResidentPref.get(i).indexOf(hospital_assigned_for_student1) > ResidentPref.get(i).indexOf(hospital_assigned_for_student2)) {//check whether student i prefers h2 to h1
                           // System.out.println(i + " " + j);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    /**
     * Determines a resident optimal solution to the Stable Marriage problem from the given input set.
     * Study the project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMarriageBruteForce_residentoptimal(Matching marriage) {
        int n = marriage.getResidentCount();
        int slots = marriage.totalHospitalSlots();
        ArrayList<Matching> StableMatchingList = new ArrayList<Matching>();
        ArrayList<ArrayList<Integer>> S_Pref = marriage.getResidentPreference();

        Permutation p = new Permutation(n, slots);
        Matching matching;
        while ((matching = p.getNextMatching(marriage)) != null) { //get every stable matching solution
            if (isStableMatching(matching)) {
                StableMatchingList.add(matching);
            }
        }
        int length = StableMatchingList.size();
        for (int i = 0; i < (length - 1); i++) {       //compare each solution and find the resident optimal one
            Matching A = StableMatchingList.get(0);
            Matching B = StableMatchingList.get(1);
            for (int j = 0; j < n; j++) {
                if (A.getResidentMatching().get(j) != -1 && B.getResidentMatching().get(j) != -1) {
                    if (S_Pref.get(j).indexOf((A.getResidentMatching().get(j))) > S_Pref.get(j).indexOf((B.getResidentMatching().get(j)))) {//check if B gets better valid partner
                        StableMatchingList.remove(A);
                        break;
                    }
                    if (S_Pref.get(j).indexOf((A.getResidentMatching().get(j))) < S_Pref.get(j).indexOf((B.getResidentMatching().get(j)))) {//check if A gets better valid partner
                        StableMatchingList.remove(B);
                        break;
                    }
                }
                else {
                    if (A.getResidentMatching().get(j) == -1 && B.getResidentMatching().get(j) != -1) {
                        StableMatchingList.remove(A);
                        break;
                    }
                    if (A.getResidentMatching().get(j) != -1 && B.getResidentMatching().get(j) == -1) {
                        StableMatchingList.remove(B);
                        break;
                    }
                }
                if(j==(n-1))
                    StableMatchingList.remove(A);
            }
        }
        return StableMatchingList.get(0);
    }

    /**
     * Determines a resident optimal solution to the Stable Marriage problem from the given input set.
     * Study the description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMarriageGaleShapley_residentoptimal(Matching marriage) {
        int n = marriage.getResidentCount();  //get parameters
        int m = marriage.getHospitalCount();
        int[] slots_table = new int[m];
        int[][] proposed_table = new int[n][m];
        ArrayList<Integer> Slots = marriage.getHospitalSlots();
        ArrayList<ArrayList<Integer>> H_Pref = marriage.getHospitalPreference();
        ArrayList<ArrayList<Integer>> S_Pref = marriage.getResidentPreference();
        ArrayList<ArrayList<Integer>> Hospital_matching = new ArrayList<>();
        ArrayList<Integer> matching = new ArrayList<>(n);

        for (int i = 0; i < m; i++) {               //initialize slots table and hospital matching
            slots_table[i] = Slots.get(i);
            ArrayList<Integer> each_hospital = new ArrayList<>();
            Hospital_matching.add(each_hospital);
        }

        for (int i = 0; i < n; i++) {            //initialize proposed table and matching array
            matching.add(-1);
            for (int j = 0; j < m; j++) {
                proposed_table[i][j] = Slots.get(j);
            }
        }
        boolean is_all_matched = false;

        while (!is_all_matched) {
            for (int i = 0; i < n; i++) {    //start from student i
                if (matching.get(i) == -1) { //check if student i has been assigned
                    for (int j = 0; j < m; j++) {    //j is the j th rank of preference list of student i
                        int hospital = S_Pref.get(i).get(j);
                        if (proposed_table[i][hospital] != 0) {  //if not proposed
                            if (slots_table[hospital] > 0) {    //check if hospital still has slots
                                matching.set(i, hospital);      //student i get assigned to hospital
                                proposed_table[i][hospital] = proposed_table[i][hospital] - 1;
                                slots_table[hospital] = slots_table[hospital] - 1;
                                Hospital_matching.get(hospital).add(i);
                                break;
                            }
                            if (slots_table[hospital] == 0) {  //if no available slots
                                for (int x = 0; x < Hospital_matching.get(hospital).size(); x++) {   //go through the hospital's current assigned student
                                    if (H_Pref.get(hospital).indexOf(Hospital_matching.get(hospital).get(x)) > H_Pref.get(hospital).indexOf(i)) { //if hospital prefer student i more
                                        matching.set(i, hospital);    //student i get assigned to hospital
                                        matching.set(Hospital_matching.get(hospital).get(x), -1);  //the previous-assigned student is free now
                                        Hospital_matching.get(hospital).remove(x);    //remove the previous-assigned student from hospital current list
                                        Hospital_matching.get(hospital).add(i);       //add
                                        break;
                                    }
                                }
                                proposed_table[i][hospital] = proposed_table[i][hospital] - 1;
                                if (matching.get(i) != -1)       //check if student i has been assigned
                                    break;
                            }
                        }
                    }


                }
            }
            is_all_matched = true;
            for (int i = 0; i < n; i++) {//check if all unassigned students have proposed to every hospital
                if (matching.get(i) == -1) {
                    for (int j = 0; j < m; j++) {
                        if (proposed_table[i][j] != 0) {
                            is_all_matched = false;
                            break;
                        }
                    }
                }
            }
        }
        return new Matching(marriage, matching);

    }

    /**
     * Determines a hospital optimal solution to the Stable Marriage problem from the given input set.
     * Study the description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMarriageGaleShapley_hospitaloptimal(Matching marriage) {
        int n = marriage.getResidentCount();  //get parameters
        int m = marriage.getHospitalCount();
        int[] slots_table = new int[m];
        ArrayList<Integer> Slots = marriage.getHospitalSlots();
        ArrayList<ArrayList<Integer>> H_Pref = marriage.getHospitalPreference();
        ArrayList<ArrayList<Integer>> S_Pref = marriage.getResidentPreference();
        ArrayList<Integer> matching = new ArrayList<>(n);
        boolean[][] proposed_table = new boolean[m][n];

        for (int i = 0; i < n; i++) {     //initialize matching array
            matching.add(-1);
        }

        for (int i = 0; i < m; i++) {    //initialize proposed table and slots table
            slots_table[i] = Slots.get(i);
            for (int j = 0; j < n; j++) {
                proposed_table[i][j] = false;
            }
        }

        boolean is_all_matched = false;

        while (!is_all_matched) {
            for (int i = 0; i < m; i++) {         //start from hospital 0
                if (slots_table[i] > 0) {    //check if hospital still has available slots. if not, go for next hospital
                    for (int j = 0; j < n; j++) {   // go through the pref list from top to below
                        if (slots_table[i] == 0)
                            break;
                        int student = H_Pref.get(i).get(j);  //student stores the current favourite student
                        if (!proposed_table[i][student]) {   //check if proposed. if not, go for next student
                            if (matching.get(student) == -1) { //check if the student has a matching before
                                matching.set(student, i);       //if yes, matching student with hospital
                                proposed_table[i][student] = true;  //change proposed status
                                slots_table[i] = slots_table[i] - 1;  //change number of slots
                            } else {                               //if student has a matching, check whether student prefers hospital i to current assigned hospital
                                if (S_Pref.get(student).indexOf(i) < S_Pref.get(student).indexOf(matching.get(student))) {
                                    slots_table[matching.get(student)] = slots_table[matching.get(student)] + 1; //add one slot for current assigned hospital
                                    matching.set(student, i);    //if yes, matching student with hospital i
                                    proposed_table[i][student] = true; //change proposed status
                                    slots_table[i] = slots_table[i] - 1; //delete one slot for hospital i
                                } else
                                    proposed_table[i][student] = true;
                            }
                        }
                    }
                }
            }
            is_all_matched = true;
            for (int i = 0; i < m; i++) {   //check if all slots have been taken
                if (slots_table[i] != 0) {
                    is_all_matched = false;
                    break;
                }
            }
        }
        return new Matching(marriage, matching);
    }

}