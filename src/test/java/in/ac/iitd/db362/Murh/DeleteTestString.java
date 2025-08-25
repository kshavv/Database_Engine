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

public class DeleteTestString {
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
    public void basicDeleteOdd() {
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "name");
        System.out.println("[LOG] basicDeleteOdd: Created B Plus tree");
        tree.insert("a", 1);
        tree.insert("bc", 2);
        tree.insert("def", 3);
        tree.insert("gh", 4);
        tree.delete("gh");
        String ans = printTreeString(tree);
        String expected = "[[a, bc, def]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestString.java: basicDeleteOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        //["a", "bc" , "def"] 

    }

  
    @Test
    public void deleteRootEven() {
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "name");
        System.out.println("[LOG] deleteRootEven: Created B Plus tree");
        tree.insert("a", 1);
        tree.insert("de", 2);
        tree.insert("ghi", 3);
        tree.insert("jk", 4);
        tree.delete("ghi");
        String ans = printTreeString(tree);
        String expected = "[[jk]]\n[[a, de]][[jk]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestString.java: deleteRootEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        //["jk"]
        //["a", "de"] ["jk"]
    }

    @Test
    public void deleteAllEven() {
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "name");
        System.out.println("[LOG] deleteAllEven: Created B Plus tree");
        tree.insert("a", 1);
        tree.insert("de", 2);
        tree.insert("ghi", 3);
        tree.insert("jk", 4);
        tree.delete("ghi");
        tree.delete("jk");
        tree.delete("de");
        tree.delete("a");
        String ans = printTreeString(tree);
        String expected = "";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestString.java: deleteAllEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // return null
        // return []
        // return ""
    }



    @Test
    public void mergeAfterDeleteOdd() {
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "name");
        System.out.println("[LOG] mergeAfterDeleteOdd: Created B Plus tree");
        tree.insert("ab", 1);
        tree.insert("cd", 2);
        tree.insert("efg", 3);
        tree.insert("h", 4);
        tree.insert("ij", 5);
        tree.delete("h");
        tree.delete("ij");
        String ans = printTreeString(tree);
        String expected = "[[ab, cd, efg]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestString.java: mergeAfterDeleteOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        //["ab", "cd" , "efg"]
    }

    @Test
    public void borrowFromRightOdd() {
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "name");
        System.out.println("[LOG] borrowFromRightOdd: Created B Plus tree");
        tree.insert("a", 1);
        tree.insert("bc", 2);
        tree.insert("de", 3);
        tree.insert("fg", 4);
        tree.insert("hi", 5);
        tree.insert("jk", 6);
        tree.delete("a");
        String ans = printTreeString(tree);
        String expected = "[[fg]]\n[[bc, de]][[fg, hi, jk]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestString.java: borrowFromRightOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // ["fg"]
        //["bc", "de"] ["fg", "hi", "jk"]
    }

    @Test
    public void borrowFromLeftOdd() {
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new BPlusTreeIndex<>(String.class, "name");
        System.out.println("[LOG] borrowFromLeftOdd: Created B Plus tree");
        tree.insert("ab", 1);
        
        tree.insert("cd", 2);
        tree.insert("efg", 3);
        
        tree.insert("klm", 4);
        tree.insert("a",5);
        tree.insert("ac", 6);
        tree.delete("efg");
        tree.delete("efg");
        tree.delete("cd");
        String ans = printTreeString(tree);
        String expected = "[[ac]]\n[[a, ab]][[ac, klm]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestString.java: borrowFromLeftOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        //["ac"]
        //["a", "ab"] ["ac", "klm"]
    }

    @Test
    public void searchAfterDeleteEven() { // No split
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<String> tree = new  BPlusTreeIndex<>(String.class, "age");
        System.out.println("[LOG] searchAfterDeleteEven: Created B Plus tree");
        tree.insert("a", 1);
        tree.delete("a");
        List<Integer> ans =  tree.search("a");
        List<Integer> expected = new ArrayList<>();
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] DeleteTestString.java: searchAfterDeleteEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }

    
}
