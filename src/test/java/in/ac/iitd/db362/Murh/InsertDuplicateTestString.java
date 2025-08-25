package in.ac.iitd.db362.bplustree;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import in.ac.iitd.db362.index.bplustree.BPlusTreeIndex;
import in.ac.iitd.db362.index.bplustree.Node;

public class InsertDuplicateTestString {
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
    public void smallOdd() {
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] smallOdd: Created B Plus tree");
        tree.insert("a", 1);
        tree.insert("bc", 2);
        tree.insert("def", 3);
        tree.insert("gh", 4);
        tree.insert("bc", 5);
        tree.insert("bc", 6);
        tree.insert("bc", 7);
        String ans = printTreeString(tree);   
        String expected = "[[bc, def]]\n[[a]][[bc]][[def, gh]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertDuplicateTestString.java: smallOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);

        // ["bc", "def"]
        //["a"] ["bc"] ["def","gh"]
    }

    @Test
    public void smallEven() {
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] smallEven: Created B Plus tree");
        tree.insert("bc", 2);
        tree.insert("bc", 3);
        tree.insert("bc", 4);
        tree.insert("bc", 5);
        String ans = printTreeString(tree);   
        String expected = "[[bc]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertDuplicateTestString.java: smallEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // ["bc"]
    }

    
}
