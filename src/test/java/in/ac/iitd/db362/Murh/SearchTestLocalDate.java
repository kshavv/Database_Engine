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

public class SearchTestLocalDate {

    @Test
    public void notExistSearch() {
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<LocalDate> tree = new BPlusTreeIndex<>(LocalDate.class, "dateOfBirth");
        System.out.println("[LOG] notExistSearch: Created B Plus tree");

        // Insert some LocalDate values
        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2023, 2, 1);
        LocalDate date3 = LocalDate.of(2023, 3, 1);
        
        tree.insert(date1, 10);
        tree.insert(date2, 20);
        tree.insert(date3, 30);

        LocalDate searchDate = LocalDate.of(2023, 4, 1);  // Date that does not exist in the tree
        List<Integer> ans = tree.search(searchDate);
        List<Integer> expected = new ArrayList<>();
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchTestLocalDate.java: notExistSearch");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }
    @Test
    public void basicSearch() {
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<LocalDate> tree = new BPlusTreeIndex<>(LocalDate.class, "dateOfBirth");
        System.out.println("[LOG] basicSearch: Created B Plus tree");

        // Insert LocalDate values
        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2023, 2, 1);
        LocalDate date3 = LocalDate.of(2023, 3, 1);
        
        tree.insert(date1, 10);
        tree.insert(date2, 20);
        tree.insert(date3, 30);

        List<Integer> ans = tree.search(date3);  // Search for date3
        List<Integer> expected = new ArrayList<>();
        expected.add(30);  // The ID associated with date3 is 30
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchTestLocalDate.java: basicSearch");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }

    
}
