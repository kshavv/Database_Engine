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

public class SearchDuplicateTestLocalDate {
    @Test
    public void multipleRows() {
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<LocalDate> tree = new BPlusTreeIndex<>(LocalDate.class, "dateOfBirth");
        System.out.println("[LOG] multipleRows: Created B Plus tree");

        // Insert multiple rows with LocalDate keys and corresponding values
        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2023, 1, 2);
        LocalDate date3 = LocalDate.of(2023, 1, 3);
        LocalDate date4 = LocalDate.of(2023, 1, 4);
        LocalDate date5 = LocalDate.of(2023, 1, 5);
        LocalDate date6 = LocalDate.of(2023, 1, 6);

        tree.insert(date1, 1);
        tree.insert(date1, 2);
        tree.insert(date3, 3);
        tree.insert(date4, 4);
        tree.insert(date1, 5);
        tree.insert(date2, 6);
        tree.insert(date1, 7);
        tree.insert(date1, 8);
        tree.insert(date3, 9);
        tree.insert(date4, 10);
        tree.insert(date4, 11);
        tree.insert(date4, 12);
        tree.insert(date5, 13);
        tree.insert(date6, 14);
        tree.insert(date3, 15);
        tree.insert(date3, 16);
        tree.insert(date5, 17);
        tree.insert(date6, 18);
        tree.insert(date2, 19);

        List<Integer> ans = tree.search(date3);  // Searching for date3 (2023-01-03)
        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(9);
        expected.add(15);
        expected.add(16);
        Collections.sort(ans);
        Collections.sort(expected);
        
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchDuplicateTestLocalDate.java: multipleRows");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }
    
}
