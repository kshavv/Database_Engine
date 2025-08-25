package in.ac.iitd.db362.bplustree;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import in.ac.iitd.db362.index.bplustree.BPlusTreeIndex;
import in.ac.iitd.db362.index.bplustree.Node;

public class InsertDuplicateTestDouble {
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
    public void smallOdd(){
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] smallOdd: Created B Plus tree");
        tree.insert(0.1, 10);
        tree.insert(0.2, 20);
        tree.insert(0.3, 30);
        tree.insert(0.4, 40);
        tree.insert(0.2, 21);
        tree.insert(0.2, 22);
        tree.insert(0.2, 23);
        String ans = printTreeDouble(tree); 
        String expected = "[[0.2, 0.3]]\n[[0.1]][[0.2]][[0.3, 0.4]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertDuplicateTestDouble.java: smallOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);

        //[0.2,0.3]
        //[0.1] [0.2] [0.3,0.4]
    }
    @Test
    public void smallEven(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] smallEven: Created B Plus tree");
        tree.insert(0.2, 20);
        tree.insert(0.2, 21);
        tree.insert(0.2, 22);
        tree.insert(0.2, 23);
        String ans = printTreeDouble(tree);  
        String expected = "[[0.2]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertDuplicateTestDouble.java: smallEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        //[0.2]
    }

    
}
