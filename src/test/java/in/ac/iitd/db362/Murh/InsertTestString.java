package in.ac.iitd.db362.bplustree;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import in.ac.iitd.db362.index.bplustree.BPlusTreeIndex;
import in.ac.iitd.db362.index.bplustree.Node;

public class InsertTestString {
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
    public void basicInsertEven() { // No split
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "name");
        System.out.println("[LOG] basicInsertEven: Created B Plus tree");
        tree.insert("a", 1);
        tree.insert("b", 2);
        tree.insert("c", 3);
        String ans=printTreeString(tree);
        String expected = "[[a, b, c]]";
        
          
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTestString.java: basicInsertEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        //["a","b","c"]
    }

    @Test
    public void basicInsertOdd() { // No split
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "name");
        System.out.println("[LOG] basicInsertOdd: Created B Plus tree");
        tree.insert("a", 1);
        tree.insert("b", 2);
        tree.insert("c", 3);
        tree.insert("d", 4);
        String ans=printTreeString(tree);
        String expected = "[[a, b, c, d]]";
        
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTestString.java: basicInsertOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        //["a","b","c","d"]
    }

    @Test
    public void basicSplitEven() { // Split
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "name");
        System.out.println("[LOG] basicSplitEven: Created B Plus tree");
        tree.insert("a", 1);
        tree.insert("ab", 2);
        tree.insert("ac", 3);
        tree.insert("ad", 4);
        String ans=printTreeString(tree);
        String expected = "[[ac]]\n" + "[[a, ab]][[ac, ad]]";
        
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTestString.java: basicSplitEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // ["ac"]
        // ["a","ab"] ["ac","ad"]
    }

    @Test
    public void basicSplitOdd() { // Split
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "name");
        System.out.println("[LOG] basicSplitOdd: Created B Plus tree");
        tree.insert("a", 1);
        tree.insert("b", 2);
        tree.insert("c", 3);
        tree.insert("d", 4);
        tree.insert("e", 5);
        String ans=printTreeString(tree);
        String expected = "[[c]]\n" + "[[a, b]][[c, d, e]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTestString.java: basicSplitOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // ["c"]
        // ["a","b"] ["c","d","e"]
    }

    @Test
    public void multipleLevelSplitOdd() {
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "name");
        System.out.println("[LOG] multipleLevelSplitOdd: Created B Plus tree");
        tree.insert("a", 1);
        tree.insert("az", 2);
        tree.insert("ab", 3);
        tree.insert("b", 4);
        tree.insert("bx", 5);
        tree.insert("bc", 6);
        tree.insert("c", 7);
        String ans=printTreeString(tree);
        String expected = "[[az, bc]]\n" + "[[ab]][[b]][[bx]]\n" + "[[a]][[ab]][[az]][[b]][[bc]][[bx, c]]";
         
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] InsertTestString.java: multipleLevelSplitOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        //["az" ,"bc"]
        //["ab"] ["b"] ["bx"]
        // ["a"] ["ab"] ["az"] ["b"] ["bc"]["bx","c"]

    }


   

    

}
