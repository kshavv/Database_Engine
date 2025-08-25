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

public class RangeQueryDuplicateTest {
    public String printTreeInteger(BPlusTreeIndex<Integer> bptree) {
        StringBuilder sb = new StringBuilder();
        System.out.println("[LOG] Printing B+ Tree");
        Node<Integer, Integer> rootNode = bptree.getRoot();
        // printNode(rootNode);
        System.out.println("[LOG] Printing Node");
        Queue<Node<Integer, Integer>> queue = new LinkedList<>();
        queue.add(rootNode);

        while (!queue.isEmpty()) {
            // Number of nodes at the current level
            int levelSize = queue.size();
    
            // Print all nodes at the current level
            for (int i = 0; i < levelSize; i++) {
                Node<Integer, Integer> node = queue.poll();
                sb.append("[" + node.getKeys() + "]");  // Print node's keys
    
                // If the node is not a leaf, add its children to the queue
                if (!node.isLeaf()) {
                    for (Node<Integer, Integer> child : node.getChildren()) {
                        queue.add(child);
                    }
                }
            }
            // Move to the next line after printing all nodes at the current level
            sb.append('\n');
            
        } 
        return sb.toString();
    }
    
    @Test
    public void allKeys(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] allKeys: Created B Plus tree");
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
        List<Integer> ans = tree.rangeQuery(-1, true, 35, true);
        List<Integer> expected = Arrays.asList(4,10,11,12,3,9,15,16,1,2,5,7,8,6,13,14,17,18,19);
        Collections.sort(ans);
        Collections.sort(expected);


        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] RangeQueryDuplicateTest.java: allKeys");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        
        assertTrue(result);  

    } 
    @Test
    public void subsetOfDuplicates(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] subsetOfDuplicates: Created B Plus tree");
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

        List<Integer> ans1 = tree.rangeQuery(1, true, 22, true);
        List<Integer> expected1 = Arrays.asList(3,9,15,16,1,2,5,7,8,6,13);
        Collections.sort(ans1);
        Collections.sort(expected1);
        
        List<Integer> ans2 = tree.rangeQuery(1, false, 22, true);
        List<Integer> expected2 = Arrays.asList(1,2,5,7,8,6,13);
        Collections.sort(ans2);
        Collections.sort(expected2);
        
        List<Integer> ans3 = tree.rangeQuery(1, true, 22, false);
        List<Integer> expected3 = Arrays.asList(3,9,15,16,1,2,5,7,8,6);
        Collections.sort(ans3);
        Collections.sort(expected3);

        List<Integer> ans4 = tree.rangeQuery(1, false, 22, false);
        List<Integer> expected4 = Arrays.asList(1,2,5,7,8,6);
        Collections.sort(ans4);
        Collections.sort(expected4);

        boolean result1 = ans1.equals(expected1); 
        boolean result2 = ans2.equals(expected2);
        boolean result3 = ans3.equals(expected3);
        boolean result4 = ans4.equals(expected4);

        if (!(result1 && result2 && result3 && result4)) {
            System.out.println("[LOG] RangeQueryDuplicateTest.java: subsetOfDuplicates");
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
    public void noDuplicates(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] noDuplicates: Created B Plus tree");
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
        List<Integer> ans1 = tree.rangeQuery(22, false, 31, false);
        List<Integer> expected1 = new ArrayList<>();
        Collections.sort(ans1);
        Collections.sort(expected1);

        
        boolean result1 = ans1.equals(expected1); 
        
        if(!result1) {
            System.out.println("[LOG] RangeQueryDuplicateTest.java: noDuplicates");
            System.out.println("[LOG] Expected1: " + expected1);
            System.out.println("[LOG] Ans1  : " + ans1);
        }
        
        assertTrue(result1 ); 


    }  
    @Test  
    public void keysNotPresent(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] keysNotPresent: Created B Plus tree");
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
        List<Integer> ans1 = tree.rangeQuery(23, true, 30, true);
        List<Integer> expected1 = new ArrayList<>();

        boolean result1 = ans1.equals(expected1); 
        if(!result1) {
            System.out.println("[LOG] RangeQueryDuplicateTest.java: keysNotPresent");
            System.out.println("[LOG] Expected1: " + expected1);
            System.out.println("[LOG] Ans1  : " + ans1);
        }
        
        assertTrue(result1 ); 
    }    
}
