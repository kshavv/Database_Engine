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

public class RangeQueryDuplicateTestLocalDate {
    @Test
    public void subsetOfDuplicates() {
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<LocalDate> tree = new BPlusTreeIndex<>(LocalDate.class, "date");
        System.out.println("[LOG] subsetOfDuplicates: Created B Plus tree");


        // Insert records with duplicate dates
        tree.insert(LocalDate.of(2023, 1, 1), 1);
        tree.insert(LocalDate.of(2023, 1, 1), 2);
        tree.insert(LocalDate.of(2023, 1, 1), 3);
        tree.insert(LocalDate.of(2023, 1, 3), 4);
        tree.insert(LocalDate.of(2023, 1, 1), 5);
        tree.insert(LocalDate.of(2023, 1, 6), 6);
        tree.insert(LocalDate.of(2023, 1, 1), 7);
        tree.insert(LocalDate.of(2023, 1, 1), 8);
        tree.insert(LocalDate.of(2023, 1, 2), 9);
        tree.insert(LocalDate.of(2023, 1, 3), 10);
        tree.insert(LocalDate.of(2023, 1, 3), 11);
        tree.insert(LocalDate.of(2023, 1, 3), 12);
        tree.insert(LocalDate.of(2023, 1, 4), 13);
        tree.insert(LocalDate.of(2023, 1, 5), 14);
        tree.insert(LocalDate.of(2023, 1, 2), 15);
        tree.insert(LocalDate.of(2023, 1, 2), 16);
        tree.insert(LocalDate.of(2023, 1, 6), 17);
        tree.insert(LocalDate.of(2023, 1, 5), 18);
        tree.insert(LocalDate.of(2023, 1, 6), 19);

        // Perform range queries
        List<Integer> ans1 = tree.rangeQuery(LocalDate.of(2023, 1, 2), true, LocalDate.of(2023, 1, 4), true);
        List<Integer> expected1 = Arrays.asList(9, 15, 16, 4, 10, 11, 12, 13);
        Collections.sort(ans1);
        Collections.sort(expected1);
        
        List<Integer> ans2 = tree.rangeQuery(LocalDate.of(2023, 1, 2), false, LocalDate.of(2023, 1, 4), true);
        List<Integer> expected2 = Arrays.asList(4, 10, 11, 12, 13);
        Collections.sort(ans2);
        Collections.sort(expected2);
        
        List<Integer> ans3 = tree.rangeQuery(LocalDate.of(2023, 1, 2), true, LocalDate.of(2023, 1, 4), false);
        List<Integer> expected3 = Arrays.asList(9, 15, 16, 4, 10, 11, 12);
        Collections.sort(ans3);
        Collections.sort(expected3);
        
        List<Integer> ans4 = tree.rangeQuery(LocalDate.of(2023, 1, 2), false, LocalDate.of(2023, 1, 4), false);
        List<Integer> expected4 = Arrays.asList(4, 10, 11, 12);
        Collections.sort(ans4);
        Collections.sort(expected4);
        boolean result1 = ans1.equals(expected1); 
        boolean result2 = ans2.equals(expected2);
        boolean result3 = ans3.equals(expected3);
        boolean result4 = ans4.equals(expected4);

        if (!(result1 && result2 && result3 && result4)) {
            System.out.println("[LOG] RangeQueryDuplicateTestLocalDate.java: subsetOfDuplicates");
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
}
