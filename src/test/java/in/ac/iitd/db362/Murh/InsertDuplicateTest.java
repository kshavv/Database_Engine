package in.ac.iitd.db362.bplustree;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import in.ac.iitd.db362.index.bplustree.BPlusTreeIndex;
import in.ac.iitd.db362.index.bplustree.Node;

public class InsertDuplicateTest {
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
    public void smallOdd(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] smallOdd: Created B Plus tree");
        tree.insert(1, 10);
        tree.insert(2, 20);
        tree.insert(3, 30);
        tree.insert(4, 40);
        tree.insert(2, 21);
        tree.insert(2, 22);
        tree.insert(2, 23);
        String ans = printTreeInteger(tree);   
        String expected = "[[2, 3]]\n[[1]][[2]][[3, 4]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertDuplicateTest.java: smallOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        //[2,3]
        //[1] [2] [3,4]
    }
    @Test
    public void smallEven(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] smallEven: Created B Plus tree");
        tree.insert(2, 20);
        tree.insert(2, 21);
        tree.insert(2, 22);
        tree.insert(2, 23);
        String ans = printTreeInteger(tree);   
        String expected = "[[2]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertDuplicateTest.java: smallEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        //[2]
    }

    
}
