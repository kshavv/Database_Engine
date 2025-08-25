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

public class RangeQueryTest {
    @Test
    public void bothKeysPresentInt(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] bothKeysPresentInt: Created B Plus tree");
        tree.insert(1, 10);
        tree.insert(2, 20);
        tree.insert(5, 50);
        tree.insert(6, 60);
        tree.insert(77, 770);
        tree.insert(100, 1000);
        tree.insert(250, 2500);
        tree.insert(10, 100);
        tree.insert(50, 500);
        List<Integer> ans1 = tree.rangeQuery(5, false, 77, false);
        List<Integer> ans2 = tree.rangeQuery(5, true, 77, false);
        List<Integer> ans3 = tree.rangeQuery(5, true, 77, true);
        List<Integer> ans4 = tree.rangeQuery(5, false, 77, true);

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
            System.out.println("[LOG] RangeQueryTest.java: bothKeysPresentInt");
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

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] bothKeysAbsentInt: Created B Plus tree");
        tree.insert(1, 10);
        tree.insert(2, 20);
        tree.insert(5, 50);
        tree.insert(6, 60);
        tree.insert(77, 770);
        tree.insert(100, 1000);
        tree.insert(250, 2500);
        tree.insert(10, 100);
        tree.insert(50, 500);
        List<Integer> ans1 = tree.rangeQuery(0, false, 8, false);
        List<Integer> ans2 = tree.rangeQuery(0, false, 8, true);
        List<Integer> ans3 = tree.rangeQuery(0, true, 8, false);
        List<Integer> ans4 = tree.rangeQuery(0, true, 8, true);
        List<Integer> expected = new ArrayList<>();
        expected.add(10);
        expected.add(20);
        expected.add(50);
        expected.add(60);
        Collections.sort(ans1);
        Collections.sort(expected);
        Collections.sort(ans2);
        Collections.sort(expected);
        Collections.sort(ans3);
        Collections.sort(expected);
        Collections.sort(ans4);
        Collections.sort(expected);
        
        
        boolean result1 = ans1.equals(expected); 
        boolean result2 = ans2.equals(expected);
        boolean result3 = ans3.equals(expected);
        boolean result4 = ans4.equals(expected);

        if (!(result1 && result2 && result3 && result4)) {
            System.out.println("[LOG] RangeQueryTest.java: bothKeysAbsentInt");
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
    // @Test
    // public void bothKeysPresentDouble1(){
    //     Integer order = 3;
    //     BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
    //     System.out.println("[LOG] bothKeysPresentDouble1: Created B Plus tree");
    //     tree.insert(1, 10);
    //     tree.insert(2, 20);
    //     tree.insert(5, 50);
    //     tree.insert(6, 60);
    //     tree.insert(77, 770);
    //     tree.insert(100, 1000);
    //     tree.insert(250, 2500);
    //     tree.insert(10, 100);
    //     tree.insert(50, 500);
    //     List<Integer> ans1 = tree.rangeQuery(5.0, false, 77.0, false);
    //     List<Integer> ans2 = tree.rangeQuery(5.0, true, 77.0, false);
    //     List<Integer> ans3 = tree.rangeQuery(5.0, true, 77.0, true);
    //     List<Integer> ans4 = tree.rangeQuery(5.0, false, 77.0, true);
    //     List<Integer> expected1 = new ArrayList<>();
    //     expected1.add(60);
    //     expected1.add(100);
    //     expected1.add(500);

    //     List<Integer> expected2 = new ArrayList<>();
    //     expected2.add(50);
    //     expected2.add(60);
    //     expected2.add(100);
    //     expected2.add(500);
    //     List<Integer> expected3 = new ArrayList<>();
    //     expected3.add(50);
    //     expected3.add(60);
    //     expected3.add(100);
    //     expected3.add(500);
    //     expected3.add(770);
    //     List<Integer> expected4 = new ArrayList<>();
    //     expected4.add(60);
    //     expected4.add(100);
    //     expected4.add(500);
    //     expected4.add(770);
    //     assertEquals(ans1, expected1);
    //     assertEquals(ans2, expected2);
    //     assertEquals(ans3, expected3);
    //     assertEquals(ans4, expected4);
    // }
    // @Test
    // public void bothKeysPresentDouble2(){
    //     Integer order = 3;
    //     BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
    //     System.out.println("[LOG] bothKeysPresentDouble2: Created B Plus tree");
    //     tree.insert(1, 10);
    //     tree.insert(2, 20);
    //     tree.insert(5, 50);
    //     tree.insert(6, 60);
    //     tree.insert(77, 770);
    //     tree.insert(100, 1000);
    //     tree.insert(250, 2500);
    //     tree.insert(10, 100);
    //     tree.insert(50, 500);
    //     List<Integer> ans1 = tree.rangeQuery(5.5, false, 77.5, false);
    //     List<Integer> ans2 = tree.rangeQuery(5.5, true, 77.5, false);
    //     List<Integer> ans3 = tree.rangeQuery(5.5, true, 77.5, true);
    //     List<Integer> ans4 = tree.rangeQuery(5.5, false, 77.5, true);
    //     List<Integer> expected1 = new ArrayList<>();
    //     expected1.add(60);
    //     expected1.add(100);
    //     expected1.add(500);
    //     expected1.add(770);
    //     List<Integer> expected2 = new ArrayList<>();
    //     expected2.add(60);
    //     expected2.add(100);
    //     expected2.add(500);
    //     expected2.add(770);

    //     List<Integer> expected3 = new ArrayList<>();
    //     expected3.add(60);
    //     expected3.add(100);
    //     expected3.add(500);
    //     expected3.add(770);
    //     List<Integer> expected4 = new ArrayList<>();
    //     expected4.add(60);
    //     expected4.add(100);
    //     expected4.add(500);
    //     expected4.add(770);
    //     assertEquals(ans1, expected1);
    //     assertEquals(ans2, expected2);
    //     assertEquals(ans3, expected3);
    //     assertEquals(ans4, expected4);
    // }
    // @Test
    // public void bothKeysPresentDouble3(){
    //     Integer order = 3;
    //     BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
    //     System.out.println("[LOG] bothKeysPresentDouble3: Created B Plus tree");
    //     tree.insert(1, 10);
    //     tree.insert(2, 20);
    //     tree.insert(5, 50);
    //     tree.insert(6, 60);
    //     tree.insert(77, 770);
    //     tree.insert(100, 1000);
    //     tree.insert(250, 2500);
    //     tree.insert(10, 100);
    //     tree.insert(50, 500);
    //     List<Integer> ans1 = tree.rangeQuery(4.5, false, 77.5, false);
    //     List<Integer> ans2 = tree.rangeQuery(4.5, true, 77.5, false);
    //     List<Integer> ans3 = tree.rangeQuery(4.5, true, 77.5, true);
    //     List<Integer> ans4 = tree.rangeQuery(4.5, false, 77.5, true);
    //     List<Integer> expected1 = new ArrayList<>();
    //     expected1.add(50);
    //     expected1.add(60);
    //     expected1.add(100);
    //     expected1.add(500);
    //     expected1.add(770);
    //     assertEquals(ans1, expected1);
    //     assertEquals(ans2, expected1);
    //     assertEquals(ans3, expected1);
    //     assertEquals(ans4, expected1);
    // }
       
}
