package in.ac.iitd.db362.bplustree;

import java.time.LocalDate;

import in.ac.iitd.db362.index.bplustree.BPlusTreeIndex;

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

public class RangeQueryTestLocalDate {
    @Test
    public void bothKeysPresent() {
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<LocalDate> tree = new BPlusTreeIndex<>(LocalDate.class, "date");
        System.out.println("[LOG] bothKeysPresent: Created B Plus tree");

        // Insert LocalDate keys with corresponding values
        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2023, 1, 2);
        LocalDate date3 = LocalDate.of(2023, 1, 5);
        LocalDate date4 = LocalDate.of(2023, 1, 6);
        LocalDate date5 = LocalDate.of(2023, 2, 17);
        LocalDate date6 = LocalDate.of(2023, 3, 1);
        LocalDate date7 = LocalDate.of(2023, 4, 5);
        LocalDate date8 = LocalDate.of(2023, 1, 10);
        LocalDate date9 = LocalDate.of(2023, 1, 20);

        tree.insert(date1, 10);
        tree.insert(date2, 20);
        tree.insert(date3, 50);
        tree.insert(date4, 60);
        tree.insert(date5, 770);
        tree.insert(date6, 1000);
        tree.insert(date7, 2500);
        tree.insert(date8, 100);
        tree.insert(date9, 500);

        // Perform range queries
        List<Integer> ans1 = tree.rangeQuery(date3, false, date5, false);
        List<Integer> ans2 = tree.rangeQuery(date3, true, date5, false);
        List<Integer> ans3 = tree.rangeQuery(date3, true, date5, true);
        List<Integer> ans4 = tree.rangeQuery(date3, false, date5, true);

        // Define expected results for each query
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

        // Assert the results match the expected values
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
            System.out.println("[LOG] RangeQueryTestLocalDate.java: bothKeysPresent");
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
    public void bothKeysAbsent() {
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<LocalDate> tree = new BPlusTreeIndex<>(LocalDate.class, "date");
        System.out.println("[LOG] bothKeysAbsent: Created B Plus tree");

        // Insert LocalDate keys with corresponding values
        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2023, 1, 2);
        LocalDate date3 = LocalDate.of(2023, 1, 5);
        LocalDate date4 = LocalDate.of(2023, 1, 6);
        LocalDate date5 = LocalDate.of(2023, 2, 17);
        LocalDate date6 = LocalDate.of(2023, 3, 1);
        LocalDate date7 = LocalDate.of(2023, 4, 5);
        LocalDate date8 = LocalDate.of(2023, 1, 10);
        LocalDate date9 = LocalDate.of(2023, 1, 20);

        tree.insert(date1, 10);
        tree.insert(date2, 20);
        tree.insert(date3, 50);
        tree.insert(date4, 60);
        tree.insert(date5, 770);
        tree.insert(date6, 1000);
        tree.insert(date7, 2500);
        tree.insert(date8, 100);
        tree.insert(date9, 500);

        // Perform range queries
        List<Integer> ans1 = tree.rangeQuery(LocalDate.of(2023, 1, 4), false, LocalDate.of(2023, 2, 18), false);
        List<Integer> ans2 = tree.rangeQuery(LocalDate.of(2023, 1, 4), true, LocalDate.of(2023, 2, 18), false);
        List<Integer> ans3 = tree.rangeQuery(LocalDate.of(2023, 1, 4), true, LocalDate.of(2023, 2, 18), true);
        List<Integer> ans4 = tree.rangeQuery(LocalDate.of(2023, 1, 4), false, LocalDate.of(2023, 2, 18), true);

    

        List<Integer> expected = new ArrayList<>();
        expected.add(50);
        expected.add(60);
        expected.add(100);
        expected.add(500);
        expected.add(770);

        Collections.sort(ans1);
        Collections.sort(ans2);
        Collections.sort(ans3);
        Collections.sort(ans4);
        Collections.sort(expected);

        
        boolean result1 = ans1.equals(expected); 
        boolean result2 = ans2.equals(expected);
        boolean result3 = ans3.equals(expected);
        boolean result4 = ans4.equals(expected);

        if (!(result1 && result2 && result3 && result4)) {
            System.out.println("[LOG] RangeQueryTestLocalDate.java: bothKeysAbsent");
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
