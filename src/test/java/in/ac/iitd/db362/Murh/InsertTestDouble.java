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

public class InsertTestDouble {
    public String printTreeDouble(BPlusTreeIndex<Double> bptree) {
        StringBuilder sb = new StringBuilder();
        System.out.println("[LOG] Printing B+ Tree");
        Node<Double, Integer> rootNode = bptree.getRoot();
        // printNode(rootNode);
        System.out.println("[LOG] Printing Node");
        Queue<Node<Double, Integer>> queue = new LinkedList<>();
        queue.add(rootNode);

        while (!queue.isEmpty()) {
            // Number of nodes at the current level
            int levelSize = queue.size();
    
            // Print all nodes at the current level
            for (int i = 0; i < levelSize; i++) {
                Node<Double, Integer> node = queue.poll();
                sb.append("[" + node.getKeys() + "]");  // Print node's keys
    
                // If the node is not a leaf, add its children to the queue
                if (!node.isLeaf()) {
                    for (Node<Double, Integer> child : node.getChildren()) {
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

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] basicInsertEven: Created B Plus tree");
        tree.insert(0.1, 1);
        tree.insert(0.2, 2);
        tree.insert(0.3, 3);
        String ans = printTreeDouble(tree);
        String expected = "[[0.1, 0.2, 0.3]]";
        
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTestDouble.java: basicInsertEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);

       // [0.1,0.2,0.3]
    }

    @Test
    public void basicInsertOdd() { // No split
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] basicInsertOdd: Created B Plus tree");
        tree.insert(0.001, 1);
        tree.insert(0.002, 2);
        tree.insert(0.01, 3);
        tree.insert(0.1, 4);
        String ans = printTreeDouble(tree);
        String expected = "[[0.001, 0.002, 0.01, 0.1]]";
        
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTestDouble.java: basicInsertOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        //[0.001,0.002,0.01,0.1]

    }
    @Test
    public void basicSplitEven() { // Split
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] basicSplitEven: Created B Plus tree");
        tree.insert(0.1, 1);
        tree.insert(0.2, 2);
        tree.insert(0.3, 3);
        tree.insert(0.4, 4);
        String ans = printTreeDouble(tree);
        String expected = "[[0.3]]\n[[0.1, 0.2]][[0.3, 0.4]]";
        
          
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTestDouble.java: basicSplitEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // [0.3]
        // [0.1,0.2] [0.3,0.4]

    }

    @Test
    public void basicSplitOdd() { // Split
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] basicSplitOdd: Created B Plus tree");
        tree.insert(1.1, 1);
        tree.insert(2.1, 2);
        tree.insert(3.1, 3);
        tree.insert(4.1, 4);
        tree.insert(5.1, 5);
        String ans = printTreeDouble(tree);
        String expected = "[[3.1]]\n[[1.1, 2.1]][[3.1, 4.1, 5.1]]";
        
          
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTestDouble.java: basicSplitOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // [3.1]
        // [1.1,2.1] [3.1,4.1,5.1]
    }
    @Test
    public void basicSplitEven2() { // Split
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] basicSplitEven2: Created B Plus tree");
        tree.insert(0.1, 1);
        tree.insert(0.2, 2);
        tree.insert(0.3, 3);
        tree.insert(0.4, 4);
        tree.insert(0.5, 5);
        tree.insert(0.6, 6);
        String ans = printTreeDouble(tree);
        String expected = "[[0.3,0.5]]\n" + "[[0.1, 0.2]][[0.3, 0.4]][[0.5, 0.6]]";
        
          
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTestDouble.java: basicSplitEven2");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // [0.3] [0.5]
        // [0.1,0.2] [0.3,0.4] [0.5,0.6]
        
    }

    @Test
    public void checkInsertOrderEven(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] checkInsertOrderEven: Created B Plus tree");
        tree.insert(9.6, 6);
        tree.insert(9.1, 1);
        tree.insert(9.4, 4);
        tree.insert(9.3, 3);
        tree.insert(9.2, 2);
        tree.insert(9.5, 5);
        String ans = printTreeDouble(tree);
        String expected = "[[9.4]]\n[[9.1, 9.2, 9.3]][[9.4, 9.5, 9.6]]";
        
          
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTestDouble.java: checkInsertOrderEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        // [9.4]
        // [9.1,9.2,9.3] [9.4,9.5,9.6]
    }
  
   
    @Test
    public void multipleLevelSplitOdd(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] multipleLevelSplitOdd: Created B Plus tree");
        tree.insert(0.1, 1);
        tree.insert(0.2, 2);
        tree.insert(0.3, 3);
        tree.insert(0.4, 4);
        tree.insert(0.5, 5);
        tree.insert(0.6, 6);
        tree.insert(0.7, 7);
        String ans = printTreeDouble(tree);
        String expected = "[[0.3, 0.5]]\n" + "[[0.2]][[0.4]][[0.6]]\n" + "[[0.1]][[0.2]][[0.3]][[0.4]][[0.5]][[0.6, 0.7]]";
        
          
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTestDouble.java: multipleLevelSplitOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // [0.3,0.5]
        // [0.2] [0.4] [0.6]
        // [0.1] [0.2] [0.3] [0.4] [0.5] [0.6,0.7]
    }

    
    

}
