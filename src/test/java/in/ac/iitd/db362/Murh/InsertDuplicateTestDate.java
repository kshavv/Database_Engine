package in.ac.iitd.db362.bplustree;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import in.ac.iitd.db362.index.bplustree.BPlusTreeIndex;
import in.ac.iitd.db362.index.bplustree.Node;


public class InsertDuplicateTestDate {
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
    public void smallOdd() {
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<LocalDate> tree = new BPlusTreeIndex<>(LocalDate.class, "date");
        System.out.println("[LOG] smallOdd: Created B Plus tree (order " + order + ")");
        tree.insert(LocalDate.of(2024, 1, 1), 10);
        tree.insert(LocalDate.of(2024, 1, 2), 20);
        tree.insert(LocalDate.of(2024, 1, 3), 30);
        tree.insert(LocalDate.of(2024, 1, 4), 40);
        tree.insert(LocalDate.of(2024, 1, 2), 21);
        tree.insert(LocalDate.of(2024, 1, 2), 22);
        tree.insert(LocalDate.of(2024, 1, 2), 23);
        String ans = printTreeLocalDate(tree);
        String expected = "[[2024-01-02, 2024-01-03]]\n" + "[[2024-01-01]][[2024-01-02]][[2024-01-03, 2024-01-04]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertDuplicateTestDate.java: smallOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        // Expected: [[2024-01-02, 2024-01-03]]
        //           [[2024-01-01]][[2024-01-02]][[2024-01-03, 2024-01-04]]
    }




}
