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

public class SearchDuplicateTestString {
    @Test
    public void multipleRows(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new  BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] multipleRows: Created B Plus tree");
        tree.insert("ii", 1);
        tree.insert("ii", 2);
        tree.insert("bb", 3);
        tree.insert("aa", 4);
        tree.insert("ii", 5);
        tree.insert("jj",6);
        tree.insert("ii", 7);
        tree.insert("ii", 8);
        tree.insert("bb", 9);
        tree.insert("aa", 10);
        tree.insert("aa", 11);
        tree.insert("aa", 12);
        tree.insert("kk", 13);
        tree.insert("ll", 14);
        tree.insert("bb", 15);
        tree.insert("bb",16);
        tree.insert("mm",17);
        tree.insert("nn",18);
        tree.insert("oo",19);
        List<Integer> ans = tree.search("bb");
        List<Integer> expected = new ArrayList<>();
        expected.add(3);
        expected.add(9);
        expected.add(15);
        expected.add(16);
        Collections.sort(ans);
        Collections.sort(expected);
    
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchDuplicateTestString.java: multipleRows");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }
    
    @Test
    public void singleRow(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new  BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] singleRow: Created B Plus tree");
        tree.insert("ii", 1);
        tree.insert("ii", 2);
        tree.insert("bb", 3);
        tree.insert("aa", 4);
        tree.insert("ii", 5);
        tree.insert("jj",6);
        tree.insert("ii", 7);
        tree.insert("ii", 8);
        tree.insert("bb", 9);
        tree.insert("aa", 10);
        tree.insert("aa", 11);
        tree.insert("aa", 12);
        tree.insert("kk", 13);
        tree.insert("ll", 14);
        tree.insert("mm", 15);
        tree.insert("bb",16);
        tree.insert("mm",17);
        tree.insert("nn",18);
        tree.insert("oo",19);
        List<Integer> ans = tree.search("oo");
        List<Integer> expected = new ArrayList<>();
        expected.add(19);
        Collections.sort(ans);
        Collections.sort(expected);
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchDuplicateTestString.java: singleRow");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }

    @Test
    public void notPresent(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new  BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] notPresent: Created B Plus tree");
        tree.insert("ii", 1);
        tree.insert("ii", 2);
        tree.insert("bb", 3);
        tree.insert("aa", 4);
        tree.insert("ii", 5);
        tree.insert("jj",6);
        tree.insert("ii", 7);
        tree.insert("ii", 8);
        tree.insert("bb", 9);
        tree.insert("aa", 10);
        tree.insert("aa", 11);
        tree.insert("aa", 12);
        tree.insert("kk", 13);
        tree.insert("ll", 14);
        tree.insert("mm", 15);
        tree.insert("bb",16);
        tree.insert("mm",17);
        tree.insert("nn",18);
        tree.insert("oo",19);
        List<Integer> ans = tree.search("tom");
        List<Integer> expected = new ArrayList<>();
        Collections.sort(ans);
        Collections.sort(expected);
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] SearchDuplicateTestString.java: notPresent");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }

    
    
}
