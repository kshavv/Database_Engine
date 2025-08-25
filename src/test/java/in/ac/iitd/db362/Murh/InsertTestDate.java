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


public class InsertTestDate {
        public String printTreeLocalDate(BPlusTreeIndex<LocalDate> bptree) {
        StringBuilder sb = new StringBuilder();
        System.out.println("[LOG] Printing B+ Tree");
        Node<LocalDate, Integer> rootNode = bptree.getRoot();
        // printNode(rootNode);
        System.out.println("[LOG] Printing Node");
        Queue<Node<LocalDate, Integer>> queue = new LinkedList<>();
        queue.add(rootNode);

        while (!queue.isEmpty()) {
            // Number of nodes at the current level
            int levelSize = queue.size();
    
            // Print all nodes at the current level
            for (int i = 0; i < levelSize; i++) {
                Node<LocalDate, Integer> node = queue.poll();
                sb.append("[" + node.getKeys() + "]");  // Print node's keys
    
                // If the node is not a leaf, add its children to the queue
                if (!node.isLeaf()) {
                    for (Node<LocalDate, Integer> child : node.getChildren()) {
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
    public void basicInsertEven() { // No split, order 4
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<LocalDate> tree = new BPlusTreeIndex<>(LocalDate.class, "date");
        System.out.println("[LOG] basicInsertEven: Created B Plus tree (order " + order + ")");
        tree.insert(LocalDate.of(2024, 1, 1), 1);
        tree.insert(LocalDate.of(2024, 1, 2), 2);
        tree.insert(LocalDate.of(2024, 1, 3), 3);
        String ans = printTreeLocalDate(tree);
        String expected = "[[2024-01-01, 2024-01-02, 2024-01-03]]";
        
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTestDate.java: basicInsertEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        // Expected: [2024-01-01, 2024-01-02, 2024-01-03]
    }

   

    @Test
    public void basicSplitEven() { // Split, order 4
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<LocalDate> tree = new BPlusTreeIndex<>(LocalDate.class, "date");
        System.out.println("[LOG] basicSplitEven: Created B Plus tree (order " + order + ")");
        tree.insert(LocalDate.of(2024, 1, 1), 1);
        tree.insert(LocalDate.of(2024, 1, 2), 2);
        tree.insert(LocalDate.of(2024, 1, 3), 3);
        tree.insert(LocalDate.of(2024, 1, 4), 4);
        String ans = printTreeLocalDate(tree);
        String expected = "[[2024-01-03]]\n" + "[[2024-01-01, 2024-01-02]][[2024-01-03, 2024-01-04]]";
        
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTestDate.java: basicSplitEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }

        assertTrue(result);
        // Expected: [2024-01-03]
        //           [[2024-01-01, 2024-01-02]][[2024-01-03, 2024-01-04]]
    }

    

    @Test
    public void multipleLevelSplitOdd() { // Order 3
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<LocalDate> tree = new BPlusTreeIndex<>(LocalDate.class, "date");
        System.out.println("[LOG] multipleLevelSplitOdd: Created B Plus tree (order " + order + ")");
        tree.insert(LocalDate.of(2024, 2, 2), 1);
        tree.insert(LocalDate.of(2024, 1, 16), 2);
        tree.insert(LocalDate.of(2024, 2, 3), 3);
        tree.insert(LocalDate.of(2024, 2, 4), 4);
        tree.insert(LocalDate.of(2024, 2, 5), 5);
        tree.insert(LocalDate.of(2024, 2, 6), 6);
        tree.insert(LocalDate.of(2024, 2, 7), 7);
        String ans = printTreeLocalDate(tree);
        String expected = "[[2024-02-03, 2024-02-05]]\n" + "[[2024-02-02]][[2024-02-04]][[2024-02-06]]\n" + "[[2024-01-16]][[2024-02-02]][[2024-02-03]][[2024-02-04]][[2024-02-05]][[2024-02-06, 2024-02-07]]";
        
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTestDate.java: multipleLevelSplitOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        // Expected: [[2024-02-03, 2024-02-05]]
        //           [[2024-02-02]][[2024-02-04]][[2024-02-06]]
        //           [[2024-01-16]][[2024-02-02]][[2024-02-03]][[2024-02-04]][[2024-02-05]][[2024-02-06, 2024-02-07]]
    }
}
