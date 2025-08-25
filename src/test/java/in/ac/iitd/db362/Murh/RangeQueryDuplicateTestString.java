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

public class RangeQueryDuplicateTestString {
    public String printTreeString(BPlusTreeIndex<String> bptree) {
        StringBuilder sb = new StringBuilder();
        System.out.println("[LOG] Printing B+ Tree");
        Node<String, Integer> rootNode = bptree.getRoot();
        // printNode(rootNode);
        System.out.println("[LOG] Printing Node");
        Queue<Node<String, Integer>> queue = new LinkedList<>();
        queue.add(rootNode);

        while (!queue.isEmpty()) {
            // Number of nodes at the current level
            int levelSize = queue.size();
    
            // Print all nodes at the current level
            for (int i = 0; i < levelSize; i++) {
                Node<String, Integer> node = queue.poll();
                sb.append("[" + node.getKeys() + "]");  // Print node's keys
    
                // If the node is not a leaf, add its children to the queue
                if (!node.isLeaf()) {
                    for (Node<String, Integer> child : node.getChildren()) {
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

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] allKeys: Created B Plus tree");
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
        List<Integer> ans = tree.rangeQuery("a", true, "z", true);
        List<Integer> expected = Arrays.asList(4,10,11,12,3,9,15,16,1,2,5,7,8,6,13,14,17,18,19);
        Collections.sort(ans);
        Collections.sort(expected);
       
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] RangeQueryDuplicateTestString.java: allKeys");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
        // String ans=printTreeInteger(tree);

    } 
    @Test
    public void subsetOfDuplicates(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] subsetOfDuplicates: Created B Plus tree");
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
        List<Integer> ans1 = tree.rangeQuery("bb", true, "kk", true);
        List<Integer> expected1 = Arrays.asList(3,9,15,16,1,2,5,7,8,6,13);
        Collections.sort(ans1);
        Collections.sort(expected1);
        
        List<Integer> ans2 = tree.rangeQuery("bb", false, "kk", true);
        List<Integer> expected2 = Arrays.asList(1,2,5,7,8,6,13);
        Collections.sort(ans2);
        Collections.sort(expected2);
        
        List<Integer> ans3 = tree.rangeQuery("bb", true, "kk", false);
        List<Integer> expected3 = Arrays.asList(3,9,15,16,1,2,5,7,8,6);
        Collections.sort(ans3);
        Collections.sort(expected3);
        
        List<Integer> ans4 = tree.rangeQuery("bb", false, "kk", false);
        List<Integer> expected4 = Arrays.asList(1,2,5,7,8,6);
        Collections.sort(ans4);
        Collections.sort(expected4);

        boolean result1 = ans1.equals(expected1); 
        boolean result2 = ans2.equals(expected2);
        boolean result3 = ans3.equals(expected3);
        boolean result4 = ans4.equals(expected4);

        if (!(result1 && result2 && result3 && result4)) {
            System.out.println("[LOG] RangeQueryDuplicateTestString.java: subsetOfDuplicates");
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
        // String ans=printTreeInteger(tree);

    } 
    @Test  
    public void noDuplicates(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] noDuplicates: Created B Plus tree");
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
        List<Integer> ans = tree.rangeQuery("kk", false, "ll", false);
        List<Integer> expected = new ArrayList<>();
        Collections.sort(ans);
        Collections.sort(expected);

          
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] RangeQueryDuplicateTestString.java: noDuplicates");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        
        assertTrue(result);  
    }  
    @Test  
    public void keysNotPresent(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] keysNotPresent: Created B Plus tree");
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
        List<Integer> ans = tree.rangeQuery("kz", true, "la", true);
        List<Integer> expected= new ArrayList<>();
        Collections.sort(ans);
        Collections.sort(expected);
        
          
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] RangeQueryDuplicateTestString.java: keysNotPresent");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }    
}
