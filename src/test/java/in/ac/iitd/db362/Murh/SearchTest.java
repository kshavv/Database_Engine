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

public class SearchTest {


    @Test
    public void basicSearch(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new  BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] basicSearch: Created B Plus tree");
        tree.insert(1, 10);
        tree.insert(2, 20);
        tree.insert(3, 30);
        List<Integer> ans = tree.search(3);
        List<Integer> expected = new ArrayList<>();
        expected.add(30);
        Collections.sort(ans);
        Collections.sort(expected);
        
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchTest.java: basicSearch");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        
        assertTrue(result);  

    }

    @Test
    public void notExistSearch(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new  BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] notExistSearch: Created B Plus tree");
        tree.insert(1, 10);
        tree.insert(2, 20);
        tree.insert(3, 30);
        List<Integer> ans = tree.search(0);
        List<Integer> expected = new ArrayList<>();
        Collections.sort(ans);
        Collections.sort(expected);
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchTest.java: notExistSearch");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        
        assertTrue(result);  
    }

    @Test
    public void basicSearch2(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new  BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] basicSearch2: Created B Plus tree");
        tree.insert(1, 10);
        tree.insert(4, 40);
        tree.insert(7, 70);
        tree.insert(10, 100);
        tree.insert(17, 170);
        tree.insert(21, 210);
        tree.insert(31, 310);
        tree.insert(25, 250);
        tree.insert(19, 190);
        tree.insert(20, 200);
        tree.insert(28, 280);
        tree.insert(42, 420);
        List<Integer> ans = tree.search(21);
        List<Integer> expected = new ArrayList<>();
        expected.add(210);
        Collections.sort(ans);
        Collections.sort(expected);
        
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchTest.java: basicSearch2");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        
        assertTrue(result);  
    }


}
