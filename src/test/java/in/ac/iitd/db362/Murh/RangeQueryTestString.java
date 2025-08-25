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

public class RangeQueryTestString {
    @Test
    public void bothKeysPresentInt(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] bothKeysPresentInt: Created B Plus tree");
        tree.insert("apple", 10);
        tree.insert("banana", 20);
        tree.insert("cat", 50);
        tree.insert("dog", 60);
        tree.insert("goat", 770);
        tree.insert("horse", 1000);
        tree.insert("ivy", 2500);
        tree.insert("eagle", 100);
        tree.insert("fig", 500);
        List<Integer> ans1 = tree.rangeQuery("cat", false, "goat", false);
        List<Integer> ans2 = tree.rangeQuery("cat", true, "goat", false);
        List<Integer> ans3 = tree.rangeQuery("cat", true, "goat", true);
        List<Integer> ans4 = tree.rangeQuery("cat", false, "goat", true);
        List<Integer> expected1 = new ArrayList<>();
        expected1.add(60);
        expected1.add(100);
        expected1.add(500);

        List<Integer> expected2 = new ArrayList<>();
        expected2.add(50);
        expected2.add(60);
        expected2.add(100);
        expected2.add(500);
        List<Integer> expected3 = new ArrayList<>();
        expected3.add(50);
        expected3.add(60);
        expected3.add(100);
        expected3.add(500);
        expected3.add(770);
        List<Integer> expected4 = new ArrayList<>();
        expected4.add(60);
        expected4.add(100);
        expected4.add(500);
        expected4.add(770);
        Collections.sort(ans1);
        Collections.sort(expected1);
        Collections.sort(ans2);
        Collections.sort(expected2);
        Collections.sort(ans3);
        Collections.sort(expected3);
        Collections.sort(ans4);
        Collections.sort(expected4);
        
        
        boolean result1 = ans1.equals(expected1); 
        boolean result2 = ans2.equals(expected2);
        boolean result3 = ans3.equals(expected3);
        boolean result4 = ans4.equals(expected4);

        if (!(result1 && result2 && result3 && result4)) {
            System.out.println("[LOG] RangeQueryTestString.java: bothKeysPresentInt");
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

    @Test
    public void bothKeysAbsentInt(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] bothKeysAbsentInt: Created B Plus tree");
        tree.insert("apple", 10);
        tree.insert("banana", 20);
        tree.insert("cat", 50);
        tree.insert("dog", 60);
        tree.insert("goat", 770);
        tree.insert("horse", 1000);
        tree.insert("ivy", 2500);
        tree.insert("eagle", 100);
        tree.insert("fig", 500);
        List<Integer> ans1 = tree.rangeQuery("a", false, "e", false);
        List<Integer> ans2 = tree.rangeQuery("a", false, "e", true);
        List<Integer> ans3 = tree.rangeQuery("a", true, "e", false);
        List<Integer> ans4 = tree.rangeQuery("a", true, "e", true);
        List<Integer> expected = new ArrayList<>();
        expected.add(10);
        expected.add(20);
        expected.add(50);
        expected.add(60);
        Collections.sort(ans1);
        Collections.sort(ans2);
        Collections.sort(ans3);
        Collections.sort(ans4);
        Collections.sort(expected);
        
        
        boolean result1 = ans1.equals(expected); 
        boolean result2 = ans2.equals(expected);
        boolean result3 = ans3.equals(expected);
        boolean result4 = ans4.equals(expected);

        if (!(result1 && result2 && result3 && result4)) {
            System.out.println("[LOG] RangeQueryTestString.java: bothKeysAbsentInt");
            if(!result1) {
                System.out.println("[LOG] Expected1: " + expected);
                System.out.println("[LOG] Ans1  : " + ans1);
            }
            if(!result2) {
                System.out.println("[LOG] Expected2: " + expected);
                System.out.println("[LOG] Ans2  : " + ans2);
            }
            if(!result3) {
                System.out.println("[LOG] Expected3: " + expected);
                System.out.println("[LOG] Ans3  : " + ans3);
            }
            if(!result4) {
                System.out.println("[LOG] Expected4: " + expected);
                System.out.println("[LOG] Ans4  : " + ans4);
            }
        }
        assertTrue(result1 && result2 && result3 && result4); 

    }
       
}
