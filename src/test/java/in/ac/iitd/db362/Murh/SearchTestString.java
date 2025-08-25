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

public class SearchTestString {
    @Test
    public void basicSearch(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new  BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] basicSearch: Created B Plus tree");
        tree.insert("a", 10);
        tree.insert("b", 20);
        tree.insert("c", 30);
        List<Integer> ans = tree.search("c");
        List<Integer> expected = new ArrayList<>();
        expected.add(30);
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchTestString.java: basicSearch");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }

    @Test
    public void notExistSearch(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new  BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] notExistSearch: Created B Plus tree");
        tree.insert("aa", 10);
        tree.insert("bb", 20);
        tree.insert("cc", 30);
        List<Integer> ans = tree.search("z");
        List<Integer> expected = new ArrayList<>();
        
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchTestString.java: notExistSearch");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        
        assertTrue(result);  
    }

    @Test
    public void basicSearch2(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new  BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] basicSearch2: Created B Plus tree");
        tree.insert("apple", 10);
        tree.insert("banana", 40);
        tree.insert("cherry", 70);
        tree.insert("date", 100);
        tree.insert("datefruit", 170);
        tree.insert("fig", 210);
        tree.insert("grape", 310);
        tree.insert("honey", 250);
        tree.insert("honeybee", 190);
        tree.insert("jacfruit", 200);
        tree.insert("kiwi", 280);
        tree.insert("lemon", 420);
        List<Integer> ans = tree.search("fig");
        List<Integer> expected = new ArrayList<>();
        expected.add(210);

        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchTestString.java: basicSearch2");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        
        assertTrue(result);  
    }


}
