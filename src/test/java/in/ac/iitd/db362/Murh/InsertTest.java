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

public class InsertTest {
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
    public void basicInsertEven() { // No split
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] basicInsertEven: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
        String ans = printTreeInteger(tree);
        String expected = "[[1, 2, 3]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTest.java: basicInsertEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
       // [1,2,3]
    }

    @Test
    public void basicInsertOdd() { // No split
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] basicInsertOdd: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
        tree.insert(4, 4);
        String ans = printTreeInteger(tree);
        String expected = "[[1, 2, 3 , 4]]";
        
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTest.java: basicInsertOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        //[1,2,3,4]

    }
    @Test
    public void basicSplitEven() { // Split
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] basicSplitEven: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
        tree.insert(4, 4);
        String ans = printTreeInteger(tree);
        String expected = "[[3]]\n[[1, 2]][[3, 4]]";
        
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTest.java: basicSplitEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        // [3]
        // [1,2] [3,4]

    }

    @Test
    public void basicSplitOdd() { // Split
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] basicSplitOdd: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
        tree.insert(4, 4);
        tree.insert(5, 5);
        String ans = printTreeInteger(tree);
        String expected = "[[3]]\n[[1, 2]][[3, 4, 5]]";
        
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTest.java: basicSplitOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);

        // [3]
        // [1,2] [3,4,5]
    }
    @Test
    public void basicSplitEven2() { // Split
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] basicSplitEven2: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
        tree.insert(4, 4);
        tree.insert(5, 5);
        tree.insert(6, 6);
        String ans = printTreeInteger(tree);
        String expected = "[[3, 5]]\n" + "[[1, 2]][[3, 4]][[5, 6]]";
        
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTest.java: basicSplitEven2");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);

        // [3] [5]
        // [1,2] [3,4] [5,6]
        
    }

    @Test
    public void checkInsertOrderEven(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] checkInsertOrderEven: Created B Plus tree");
        tree.insert(6, 6);
        tree.insert(1, 1);
        tree.insert(4, 4);
        tree.insert(3, 3);
        tree.insert(2, 2);
        tree.insert(5, 5);
        String ans = printTreeInteger(tree);
        String expected = "[[4]]\n[[1, 2, 3]][[4, 5, 6]]";
        
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTest.java: checkInsertOrderEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        // [4]
        // [1,2,3] [4,5,6]
    }
    @Test
    public void basicSplitOdd2() { // Split
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] basicSplitOdd2: Created B Plus tree");
        tree.insert(0, 0);
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
        tree.insert(4, 4);
        tree.insert(5, 5);
        tree.insert(6, 6);
        String ans = printTreeInteger(tree);
        String expected = "[[2, 4]]\n" + "[[0, 1]][[2, 3]][[4, 5, 6]]";
        
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTest.java: basicSplitOdd2");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        //[2 ,4]
        // [0,1] [2,3] [4,5,6]
    }
    @Test
    public void checkInsertOrderOdd(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] checkInsertOrderOdd: Created B Plus tree");
        tree.insert(6, 6);
        tree.insert(1, 1);
        tree.insert(4, 4);
        tree.insert(3, 3);
        tree.insert(2, 2);
        tree.insert(5, 5);
        tree.insert(0, 0);
        String ans = printTreeInteger(tree);
        String expected = "[[2, 4]]\n" + "[[0, 1]][[2, 3]][[4, 5, 6]]";
        
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTest.java: checkInsertOrderOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        //[2 ,4]
        // [0,1] [2,3] [4,5,6]
    }
    @Test
    public void multipleLevelSplitOdd(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] multipleLevelSplitOdd: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
        tree.insert(4, 4);
        tree.insert(5, 5);
        tree.insert(6, 6);
        tree.insert(7, 7);

        String ans = printTreeInteger(tree);
        String expected = "[[3, 5]]\n" + "[[2]][[4]][[6]]\n" + "[[1]][[2]][[3]][[4]][[5]][[6, 7]]";
        
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTest.java: multipleLevelSplitOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);

        // [3,5]
        // [2] [4] [6]
        // [1] [2] [3] [4] [5] [6,7]
    }

  

}
