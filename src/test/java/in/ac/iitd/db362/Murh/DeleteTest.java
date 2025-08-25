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

public class DeleteTest {
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
    public void basicDeleteOdd() { //no merge or borrow  // only 1 node
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] basicDeleteOdd: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
        tree.insert(4, 4);
        tree.delete(4);
        String ans = printTreeInteger(tree);
        String expected = "[[1,2,3]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTest.java: basicDeleteOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
    }

    @Test
    public void deleteRootOdd() { 
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class,"age");
        System.out.println("[LOG] deleteRootOdd: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
       
        tree.delete(2);

        String ans =printTreeInteger(tree);
        String expected = "[[3]]\n[[1]][[3]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTest.java: deleteRootOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
    }
    
    @Test
    public void deleteRootEven() { 
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class,"age");
        System.out.println("[LOG] deleteRootEven: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
        tree.insert(4, 4);
        tree.delete(3);
        String ans = printTreeInteger(tree);
        String expected = "[[4]]\n[[1, 2]][[4]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTest.java: deleteRootEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
    }
    @Test
    public void deleteAllEven() { //
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class,"age");
        System.out.println("[LOG] deleteRootEven: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
        tree.insert(4, 4);
        tree.delete(3);
        tree.delete(4);
        tree.delete(2);
        tree.delete(1);
        String ans = printTreeInteger(tree);
        // ans must be one of the following
        // String expected1 = null;
        // String expected2 = "[]";
        String expected = "";

        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTest.java: deleteAllEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        // return null
        // return []
        // return ""

    }

    @Test
    public void basicDeleteEven() { 
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class,"age");
        System.out.println("[LOG] basicDeleteEven: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(4, 4);
        tree.insert(7, 7);
        tree.insert(10, 10);
        tree.insert(17, 17);
        tree.insert(21, 21);
        tree.insert(31, 31);
        tree.insert(25, 25);
        tree.insert(19, 19);
        tree.insert(20, 20);
        tree.insert(28, 28);
        tree.insert(42, 42);
        tree.delete(21);
        String ans = printTreeInteger(tree);
        String expected = "[[20]]\n[[7, 17]][[25, 31]]\n[[1, 4]][[7, 10]][[17, 19]][[20]][[25, 28]][[31, 42]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTest.java: basicDeleteEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // [20]
       // [7 ,17]  [25,31]
       // [1 ,4] [7,10] [17 ,19] [20] [25,28] [31,42]
    }
    @Test
    public void basicDeleteEven2() { //no merge or borrow  //multi level tree - delete from 2 levels
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class,"age");
        System.out.println("[LOG] basicDeleteEven2: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(4, 4);
        tree.insert(7, 7);
        tree.insert(10, 10);
        tree.insert(17, 17);
        tree.insert(21, 21);
        tree.insert(31, 31);
        tree.insert(25, 25);
        tree.insert(19, 19);
        tree.insert(20, 20);
        tree.insert(28, 28);
        tree.insert(42, 42);
        tree.delete(21);
        tree.delete(31);

        String ans = printTreeInteger(tree);
        String expected = "[[20]]\n[[7, 17]][[25, 42]]\n[[1, 4]][[7, 10]][[17, 19]][[20]][[25, 28]][[42]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTest.java: basicDeleteEven2");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // [20]
       // [7 ,17]  [25,42]
       // [1 ,4] [7,10] [17 ,19] [20] [25,28] [42]
    }
    

    @Test
    public void basicDelete2Odd(){// no merge or borrow - multiple levels in tree
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class,"age");
        System.out.println("[LOG] basicDelete2Odd: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
        tree.insert(4, 4);
        tree.insert(5, 5);
        tree.delete(4);
        String ans = printTreeInteger(tree);
        String expected = "[[3]]\n[[1, 2]][[3, 5]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTest.java: basicDelete2Odd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        //[3]
        //[1,2] [3,5]
    }

    @Test
    public void mergeAfterDeleteOdd(){// no merge or borrow - multiple levels in tree
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class,"age");
        System.out.println("[LOG] mergeAfterDeleteOdd: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
        tree.insert(4, 4);
        tree.insert(5, 5);
        tree.delete(4);
        tree.delete(5);
        String ans = printTreeInteger(tree);
        String expected = "[[1, 2, 3]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTest.java: mergeAfterDeleteOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // [1,2,3]
    }

    @Test
    public void borrowFromRightOdd(){
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class,"age");
        System.out.println("[LOG] borrowFromRightOdd: Created B Plus tree");
        tree.insert(0, 0);
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
        tree.insert(4, 4);
        tree.insert(5, 5);
        tree.delete(0);
        String ans = printTreeInteger(tree);
        String expected = "[[3]]\n[[1, 2]][[3, 4, 5]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTest.java: borrowFromRightOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        //[3]
        //[1,2] [3,4,5]
    }

    @Test
    public void borrowFromLeftOdd(){
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class,"age");
        System.out.println("[LOG] borrowFromLeftOdd: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
        tree.insert(4, 4);
        tree.insert(5, 5);
        tree.insert(0, 0);
        tree.delete(3);
        tree.delete(4);
        String ans = printTreeInteger(tree);
        String expected = "[[2]]\n[[0, 1]][[2, 5]]";
        
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTest.java: borrowFromLeftOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        //[2]
        //[0,1] [2,5]
    }

    @Test
    public void borrowFromLeftAndMergeOdd(){
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class,"age");
        System.out.println("[LOG] borrowFromLeftAndMergeOdd: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(2, 2);
        tree.insert(3, 3);
        tree.insert(4, 4);
        tree.insert(5, 5);
        tree.insert(0, 0);
        tree.delete(3);
        tree.delete(4);
        tree.delete(0);
        String ans = printTreeInteger(tree);
        String expected = "[[1, 2, 5]]" ;
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTest.java: borrowFromLeftAndMergeOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
       // [1,2,5]
    }
    @Test
    public void borrowFromRightEven(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class,"age");
        System.out.println("[LOG] basicDeleteEven: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(4, 4);
        tree.insert(7, 7);
        tree.insert(10, 10);
        tree.insert(17, 17);
        tree.insert(21, 21);
        tree.insert(31, 31);
        tree.insert(25, 25);
        tree.insert(19, 19);
        tree.insert(20, 20);
        tree.insert(28, 28);
        tree.insert(42, 42);
        tree.delete(21);
        tree.delete(31);
        tree.delete(20);
        String ans = printTreeInteger(tree);
        // String expected = "[[25]]\n[[4, 17]][[28, 42]]\n[[1, 4]][[7, 10]][[17, 19]][[25]][[28]][[42]]";
        String expected = "[[25]]\n[[7, 17]][[28, 42]]\n[[1, 4]][[7, 10]][[17, 19]][[25]][[28]][[42]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTest.java: borrowFromRightEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // [25]
       // [4,17]  [28,42]
       // [1 ,4] [7,10] [17 ,19] [25] [28] [42]

    }

    @Test
    public void borrowFromLeftEven(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new BPlusTreeIndex<>(Integer.class,"age");
        System.out.println("[LOG] borrowFromLeftEven: Created B Plus tree");
        tree.insert(1, 1);
        tree.insert(4, 4);
        tree.insert(7, 7);
        tree.insert(10, 10);
        tree.insert(17, 17);
        tree.insert(21, 21);
        tree.insert(31, 31);
        tree.insert(25, 25);
        tree.insert(19, 19);
        tree.insert(20, 20);
        tree.insert(28, 28);
        tree.insert(42, 42);
        tree.delete(21);
        tree.delete(31);
        tree.delete(20);
        tree.delete(7);
        tree.delete(10);
        String ans = printTreeInteger(tree);
        String expected = "[[25]]\n[[7, 17]][[28, 42]]\n[[1]][[4]][[17, 19]][[25]][[28]][[42]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTest.java: borrowFromLeftEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // [25]
       // [7,17]  [28,42]
       // [1] [4] [17,19] [25] [28] [42]
    }
    @Test
    public void searchAfterDeleteEven() { // No split
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Integer> tree = new  BPlusTreeIndex<>(Integer.class, "age");
        System.out.println("[LOG] searchAfterDeleteEven: Created B Plus tree");
        tree.insert(1, 1);
        tree.delete(1);
        List<Integer> ans =  tree.search(1);
        List<Integer> expected = new ArrayList<>();
        Collections.sort(ans);
        Collections.sort(expected);
        

        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] DeleteTest.java: searchAfterDeleteEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }
    
}
