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

public class OtherTests {
    @Test
    public void getAllKeysInteger(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;
        
        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] getAllKeysInteger: Created B Plus tree");
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
        List<Integer> ans = tree.getAllKeys();
        List<Integer> expected = Arrays.asList(0,0,0,0,1,1,1,1,8,8,8,8,8,9,22,31,32,33,34);
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] OtherTests.java: getAllKeysInteger");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }

    
    @Test
    public void allKeysDouble(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] allKeysDouble: Created B Plus tree");
        tree.insert(0.08, 1);
        tree.insert(0.08, 2);
        tree.insert(0.01, 3);
        tree.insert(0.00, 4);
        tree.insert(0.08, 5);
        tree.insert(0.09,6);
        tree.insert(0.08, 7);
        tree.insert(0.08, 8);
        tree.insert(0.01, 9);
        tree.insert(0.00, 10);
        tree.insert(0.0, 11);
        tree.insert(0.00, 12);
        tree.insert(0.22, 13);
        tree.insert(0.31, 14);
        tree.insert(0.01, 15);
        tree.insert(0.01,16);
        tree.insert(0.32,17);
        tree.insert(0.33,18);
        tree.insert(0.34,19);
        List<Double> ans = tree.getAllKeys();
        List<Double> expected = Arrays.asList(0.0,0.0,0.0,0.0,0.01,0.01,0.01,0.01,0.08,0.08,0.08,0.08,0.08,0.09,0.22,0.31,0.32,0.33,0.34);
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] OtherTests.java: allKeysDouble");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  

    }
    @Test
    public void allKeysString(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] allKeysString: Created B Plus tree");
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
        List<String> ans = tree.getAllKeys();
        List<String> expected = Arrays.asList("aa","aa","aa","aa","bb","bb","bb","bb","ii","ii","ii","ii","ii","jj","kk","ll","mm","nn","oo");
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] OtherTests.java: allKeysString");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
        // String ans=printTreeInteger(tree);

    } 
    @Test
    public void getHeightInteger(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] getHeightInteger: Created B Plus tree");
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
        Integer ans = tree.getHeight();
        assertEquals(3, ans);
        //[22]
        //[8] [32]
        //[1] [9] [31][33]
        //[0] [1] [8] [9] [22] [31] [32] [33,34]
    }
    @Test
    public void getHeightDouble(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] getHeightDouble: Created B Plus tree");
        tree.insert(8.1, 1);
        tree.insert(8.1, 2);
        tree.insert(1.1, 3);
        tree.insert(0.1, 4);
        tree.insert(8.1, 5);
        tree.insert(9.1,6);
        tree.insert(8.1, 7);
        tree.insert(8.1, 8);
        tree.insert(1.1, 9);
        tree.insert(0.1, 10);
        tree.insert(0.1, 11);
        tree.insert(0.1, 12);
        tree.insert(22.1, 13);
        tree.insert(31.1, 14);
        tree.insert(1.1, 15);
        tree.insert(1.1,16);
        tree.insert(32.1,17);
        tree.insert(33.1,18);
        tree.insert(34.1,19);
        Integer ans= tree.getHeight();
        assertEquals(3, ans);
        //[22.1]
        //[8.1] [32.1]
        //[1.1] [9.1] [31.1][33.1]
        //[0.1] [1.1] [8.1] [9.1] [22.1] [31.1] [32.1] [33.1,34.1]
    }
    @Test
    public void getHeightString() {
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] getHeightString: Created B Plus tree");
        tree.insert("x", 1);
        tree.insert("x", 2);
        tree.insert("a", 3);
        tree.insert("b", 4);
        tree.insert("x", 5);
        tree.insert("y", 6);
        tree.insert("x", 7);
        tree.insert("x", 8);
        tree.insert("a", 9);
        tree.insert("b", 10);
        tree.insert("b", 11);
        tree.insert("b", 12);
        tree.insert("z", 13);
        tree.insert("w", 14);
        tree.insert("a", 15);
        tree.insert("a", 16);
        tree.insert("m", 17);
        tree.insert("n", 18);
        tree.insert("o", 19);
        Integer ans = tree.getHeight();
        assertEquals(2, ans);
        
        //["m","x"]
        //["b"] ["n","o"] ["y"]
        //["a"] ["b"] ["m"] ["n"] ["o","w"] ["x"]  ["y","z"] 
    }
    
}
