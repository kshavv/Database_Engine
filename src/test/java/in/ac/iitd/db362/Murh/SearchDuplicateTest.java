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

public class SearchDuplicateTest {
    @Test
    public void multipleRows(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new  BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] multipleRows: Created B Plus tree");
        tree.insert(8, 1);
        tree.insert(8, 2);
        tree.insert(1, 3);
        tree.insert(0, 4);
        tree.insert(8, 5);
        tree.insert(9,6);
        tree.insert(8, 7);
        tree.insert(8, 8);
        tree.insert(1, 9);
        tree.insert(0, 10);
        tree.insert(0, 11);
        tree.insert(0, 12);
        tree.insert(22, 13);
        tree.insert(31, 14);
        tree.insert(1, 15);
        tree.insert(1,16);
        tree.insert(32,17);
        tree.insert(33,18);
        tree.insert(34,19);
        List<Integer> ans = tree.search(1);
        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(9);
        expected.add(15);
        expected.add(16);
        Collections.sort(ans);
        Collections.sort(expected);
        
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchDuplicateTest.java: multipleRows");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        
        assertTrue(result);  
    }
    
    @Test
    public void singleRow(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new  BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] singleRow: Created B Plus tree");
        tree.insert(8, 1);
        tree.insert(8, 2);
        tree.insert(1, 3);
        tree.insert(0, 4);
        tree.insert(8, 5);
        tree.insert(9,6);
        tree.insert(8, 7);
        tree.insert(8, 8);
        tree.insert(1, 9);
        tree.insert(0, 10);
        tree.insert(0, 11);
        tree.insert(0, 12);
        tree.insert(22, 13);
        tree.insert(31, 14);
        tree.insert(1, 15);
        tree.insert(1,16);
        tree.insert(32,17);
        tree.insert(33,18);
        tree.insert(34,19);
        List<Integer> ans = tree.search(34);
        List<Integer> expected = new ArrayList<>();
        expected.add(19);
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchDuplicateTest.java: singleRow");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }

    @Test
    public void notPresent(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new  BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] notPresent: Created B Plus tree");
        tree.insert(8, 1);
        tree.insert(8, 2);
        tree.insert(1, 3);
        tree.insert(0, 4);
        tree.insert(8, 5);
        tree.insert(9,6);
        tree.insert(8, 7);
        tree.insert(8, 8);
        tree.insert(1, 9);
        tree.insert(0, 10);
        tree.insert(0, 11);
        tree.insert(0, 12);
        tree.insert(22, 13);
        tree.insert(31, 14);
        tree.insert(1, 15);
        tree.insert(1,16);
        tree.insert(32,17);
        tree.insert(33,18);
        tree.insert(34,19);
        List<Integer> ans = tree.search(35);
        List<Integer> expected = new ArrayList<>();
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchDuplicateTest.java: notPresent");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }

    
    
}
