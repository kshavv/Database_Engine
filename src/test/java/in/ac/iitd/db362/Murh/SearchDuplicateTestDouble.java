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

public class SearchDuplicateTestDouble {
    @Test
    public void multipleRows(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new  BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] multipleRows: Created B Plus tree");
        tree.insert(0.08, 1);
        tree.insert(0.08, 2);
        tree.insert(0.01, 3);
        tree.insert(-1.0, 4);
        tree.insert(0.08, 5);
        tree.insert(0.09,6);
        tree.insert(0.08, 7);
        tree.insert(0.08, 8);
        tree.insert(0.01, 9);
        tree.insert(-1.0, 10);
        tree.insert(-1.0, 11);
        tree.insert(-1.0, 12);
        tree.insert(0.22, 13);
        tree.insert(0.31, 14);
        tree.insert(0.01, 15);
        tree.insert(0.01,16);
        tree.insert(0.32,17);
        tree.insert(0.33,18);
        tree.insert(0.34,19);
        List<Integer> ans = tree.search(0.01);
        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(9);
        expected.add(15);
        expected.add(16);
        Collections.sort(ans);
        Collections.sort(expected);
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchDuplicateTestDouble.java: multipleRows");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }
    
    @Test
    public void singleRow(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new  BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] singleRow: Created B Plus tree");
        tree.insert(0.08, 1);
        tree.insert(0.08, 2);
        tree.insert(0.01, 3);
        tree.insert(-1.0, 4);
        tree.insert(0.08, 5);
        tree.insert(0.09,6);
        tree.insert(0.08, 7);
        tree.insert(0.08, 8);
        tree.insert(0.01, 9);
        tree.insert(-1.0, 10);
        tree.insert(-1.0, 11);
        tree.insert(-1.0, 12);
        tree.insert(0.22, 13);
        tree.insert(0.31, 14);
        tree.insert(0.01, 15);
        tree.insert(0.01,16);
        tree.insert(0.32,17);
        tree.insert(0.33,18);
        tree.insert(0.34,19);
        List<Integer> ans = tree.search(0.34);
        List<Integer> expected = new ArrayList<>();
        expected.add(19);
        Collections.sort(ans);
        Collections.sort(expected);
        
        
        boolean result = ans.equals(expected); 
        assertTrue(result);  
        if (!result) {
            System.out.println("[LOG] SearchDuplicateTestDouble.java: singleRow");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
    }

    @Test
    public void notPresent(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new  BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] notPresent: Created B Plus tree");
        tree.insert(0.08, 1);
        tree.insert(0.08, 2);
        tree.insert(0.01, 3);
        tree.insert(-1.0, 4);
        tree.insert(0.08, 5);
        tree.insert(0.09,6);
        tree.insert(0.08, 7);
        tree.insert(0.08, 8);
        tree.insert(0.01, 9);
        tree.insert(-1.0, 10);
        tree.insert(-1.0, 11);
        tree.insert(-1.0, 12);
        tree.insert(0.22, 13);
        tree.insert(0.31, 14);
        tree.insert(0.01, 15);
        tree.insert(0.01,16);
        tree.insert(0.32,17);
        tree.insert(0.33,18);
        tree.insert(0.34,19);
        List<Integer> ans = tree.search(-2.0);
        List<Integer> expected = new ArrayList<>();
        Collections.sort(ans);
        Collections.sort(expected);
        
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchDuplicateTestDouble.java: notPresent");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }

    
    
}
