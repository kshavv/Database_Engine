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

public class SearchTestDouble {
    
    @Test
    public void basicSearch(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new  BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] basicSearch: Created B Plus tree");
        tree.insert(0.1, 10);
        tree.insert(0.2, 20);
        tree.insert(0.3, 30);
        List<Integer> ans = tree.search(0.3);
        List<Integer> expected = new ArrayList<>();
        expected.add(30);
        Collections.sort(ans);
        Collections.sort(expected);
       
        boolean result = ans.equals(expected); 
        
        if (!result) {
            System.out.println("[LOG] SearchTestDouble.java: basicSearch");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        
        assertTrue(result);  
    }

    @Test
    public void notExistSearch(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new  BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] notExistSearch: Created B Plus tree");
        tree.insert(0.1, 10);
        tree.insert(0.2, 20);
        tree.insert(0.3, 30);
        List<Integer> ans = tree.search(0.0);
        List<Integer> expected = new ArrayList<>();
        Collections.sort(ans);
        Collections.sort(expected);
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchTestDouble.java: notExistSearch");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  

    }

    @Test
    public void basicSearch2(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new  BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] basicSearch2: Created B Plus tree");
        tree.insert(0.01, 10);
        tree.insert(0.04, 40);
        tree.insert(0.07, 70);
        tree.insert(0.10, 100);
        tree.insert(0.17, 170);
        tree.insert(0.21, 210);
        tree.insert(0.31, 310);
        tree.insert(0.25, 250);
        tree.insert(0.19, 190);
        tree.insert(0.20, 200);
        tree.insert(0.28, 280);
        tree.insert(0.42, 420);
        List<Integer> ans = tree.search(0.21);
        List<Integer> expected = new ArrayList<>();
        expected.add(210);
        Collections.sort(ans);
        Collections.sort(expected);

        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchTestDouble.java: basicSearch2");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        
        assertTrue(result);  
    }


}
