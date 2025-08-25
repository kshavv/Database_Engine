package in.ac.iitd.db362.bplustree;

import in.ac.iitd.db362.index.Index;
import in.ac.iitd.db362.catalog.Catalog;
import in.ac.iitd.db362.index.bplustree.*;
import in.ac.iitd.db362.parser.QueryNode;

// import in.ac.iitd.db362.index.hashindex.ExtendibleHashing;
// import in.ac.iitd.db362.index.BitmapIndex;
import org.junit.jupiter.api.*;
import java.nio.file.*;
import java.beans.Transient;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RangeQueryTestDouble {
    @Test
    public void bothKeysPresent(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] bothKeysPresent: Created B Plus tree");
        tree.insert(0.001, 10);
        tree.insert(0.002, 20);
        tree.insert(0.005, 50);
        tree.insert(0.006, 60);
        tree.insert(0.077, 770);
        tree.insert(0.100, 1000);
        tree.insert(0.250, 2500);
        tree.insert(0.010, 100);
        tree.insert(0.050, 500);
        List<Integer> ans1 = tree.rangeQuery(0.005, false, 0.077, false);
        List<Integer> ans2 = tree.rangeQuery(0.005, true, 0.077, false);
        List<Integer> ans3 = tree.rangeQuery(0.005, true, 0.077, true);
        List<Integer> ans4 = tree.rangeQuery(0.005, false, 0.077, true);
        List<Integer> expected1 = new ArrayList<>();
        expected1.add(60);
        expected1.add(100);
        expected1.add(500);

        List<Integer> expected2 = new ArrayList<>();
        expected2.add(50);
        expected2.add(60);
        expected2.add(100);
        expected2.add(500);
        List<Integer> expected3 = new ArrayList<>();
        expected3.add(50);
        expected3.add(60);
        expected3.add(100);
        expected3.add(500);
        expected3.add(770);
        List<Integer> expected4 = new ArrayList<>();
        expected4.add(60);
        expected4.add(100);
        expected4.add(500);
        expected4.add(770);
        Collections.sort(ans1);
        Collections.sort(expected1);
        Collections.sort(ans2);
        Collections.sort(expected2);
        Collections.sort(ans3);
        Collections.sort(expected3);
        Collections.sort(ans4);
        Collections.sort(expected4);
        
        
        boolean result1 = ans1.equals(expected1); 
        boolean result2 = ans2.equals(expected2);
        boolean result3 = ans3.equals(expected3);
        boolean result4 = ans4.equals(expected4);

        if (!(result1 && result2 && result3 && result4)) {
            System.out.println("[LOG] RangeQueryTestDouble.java: bothKeysPresent");
            if(!result1) {
                System.out.println("[LOG] Expected1: " + expected1);
                System.out.println("[LOG] Ans1  : " + ans1);
            }
            if(!result2) {
                System.out.println("[LOG] Expected2: " + expected2);
                System.out.println("[LOG] Ans2  : " + ans2);
            }
            if(!result3) {
                System.out.println("[LOG] Expected3: " + expected3);
                System.out.println("[LOG] Ans3  : " + ans3);
            }
            if(!result4) {
                System.out.println("[LOG] Expected4: " + expected4);
                System.out.println("[LOG] Ans4  : " + ans4);
            }
        }
        assertTrue(result1 && result2 && result3 && result4); 
    }

    @Test
    public void bothKeysAbsent(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] bothKeysAbsent: Created B Plus tree");
        tree.insert(0.001, 10);
        tree.insert(0.002, 20);
        tree.insert(0.005, 50);
        tree.insert(0.006, 60);
        tree.insert(0.077, 770);
        tree.insert(0.100, 1000);
        tree.insert(0.250, 2500);
        tree.insert(0.010, 100);
        tree.insert(0.050, 500);
        List<Integer> ans1 = tree.rangeQuery(-1.0, false, 0.008, false);
        List<Integer> ans2 = tree.rangeQuery(-1.0, false, 0.008, true);
        List<Integer> ans3 = tree.rangeQuery(-1.0, true, 0.008, false);
        List<Integer> ans4 = tree.rangeQuery(-1.0, true, 0.008, true);
        List<Integer> expected = new ArrayList<>();
        expected.add(10);
        expected.add(20);
        expected.add(50);
        expected.add(60);
        Collections.sort(ans1);
        Collections.sort(expected);
        Collections.sort(ans2);
        Collections.sort(ans3);
        Collections.sort(ans4);

        
        boolean result1 = ans1.equals(expected); 
        boolean result2 = ans2.equals(expected);
        boolean result3 = ans3.equals(expected);
        boolean result4 = ans4.equals(expected);

        if (!(result1 && result2 && result3 && result4)) {
            System.out.println("[LOG] RangeQueryTestDouble.java: bothKeysAbsent");
            if(!result1) {
                System.out.println("[LOG] Expected1: " + expected);
                System.out.println("[LOG] Ans1  : " + ans1);
            }
            if(!result2) {
                System.out.println("[LOG] Expected2: " + expected);
                System.out.println("[LOG] Ans2  : " + ans2);
            }
            if(!result3) {
                System.out.println("[LOG] Expected3: " + expected);
                System.out.println("[LOG] Ans3  : " + ans3);
            }
            if(!result4) {
                System.out.println("[LOG] Expected4: " + expected);
                System.out.println("[LOG] Ans4  : " + ans4);
            }
        }
        assertTrue(result1 && result2 && result3 && result4); 

    }
       
}
