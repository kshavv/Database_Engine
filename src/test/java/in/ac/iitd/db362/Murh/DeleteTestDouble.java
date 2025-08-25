package in.ac.iitd.db362.bplustree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import in.ac.iitd.db362.index.bplustree.BPlusTreeIndex;
import in.ac.iitd.db362.index.bplustree.Node;

public class DeleteTestDouble {
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
    public void basicDeleteOdd() { //no merge or borrow  // only 1 node
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] basicDeleteOdd: Created B Plus tree");
        tree.insert(1.5, 1);
        tree.insert(2.2, 2);
        tree.insert(3.0, 3);
        tree.insert(4.6, 4);
        tree.delete(4.6);
        String ans = printTreeDouble(tree);
        String expected = "[[1.5, 2.2, 3.0]]" ;
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestDouble.java: basicDeleteOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        //[1.5] [2.2] [3.0]
    }

    @Test
    public void deleteRootOdd() { 
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] deleteRootOdd: Created B Plus tree");
        tree.insert(1.5, 1);
        tree.insert(2.2, 2);
        tree.insert(3.0, 3);
       
        tree.delete(2.2);

        String ans = printTreeDouble(tree);
        String expected = "[[3.0]]\n[[1.5]][[3.0]]" ;
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestDouble.java: deleteRootOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // [3.0]
        // [1.5] [3.0]
    }
    @Test
    public void deleteRootEven() { 
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] deleteRootEven: Created B Plus tree");
        tree.insert(1.5, 1);
        tree.insert(2.2, 2);
        tree.insert(3.0, 3);
        tree.insert(4.6, 4);
        tree.delete(3.0);
        String ans = printTreeDouble(tree);
        String expected = "[[4.6]]\n[[1.5, 2.2]][[4.6]]" ;
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestDouble.java: deleteRootEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        //[4.6]
        // [1.5,2.2] [4.6]
    }
    @Test
    public void deleteAllEven() { //
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] deleteRootEven: Created B Plus tree");
        tree.insert(1.5, 1);
        tree.insert(2.2, 2);
        tree.insert(3.0, 3);
        tree.insert(4.6, 4);
        tree.delete(3.0);
        tree.delete(4.6);
        tree.delete(2.2);
        tree.delete(1.5);
        String ans = printTreeDouble(tree);
        String expected = "";
        // ans can be null or empty string or ""
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestDouble.java: deleteAllEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // return null
        // return []
        // return " "

    }

    @Test
    public void basicDeleteEven() { 
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] basicDeleteEven: Created B Plus tree");
        tree.insert(1.5, 1);
        tree.insert(4.6, 4);
        tree.insert(7.0, 7);
        tree.insert(10.0, 10);
        tree.insert(17.0, 17);
        tree.insert(21.0, 21);
        tree.insert(31.0, 31);
        tree.insert(25.0, 25);
        tree.insert(19.9, 19);
        tree.insert(20.01, 20);
        tree.insert(28.0, 28);
        tree.insert(42.0, 42);
        tree.delete(21.0);
        String ans = printTreeDouble(tree);
        String expected = "[[20.01]]\n[[7.0, 17.0]][[25.0, 31.0]]\n[[1.5, 4.6]][[7.0, 10.0]][[17.0, 19.9]][[20.01]][[25.0, 28.0]][[31.0, 42.0]]" ;
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestDouble.java: basicDeleteEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // [20.01]
       // [7.0 ,17.0]  [25.0,31.0]
       // [1.5 ,4.6] [7.0,10.0] [17.0 ,19.9] [20.01] [25.0,28.0] [31.0,42.0]
    }
    @Test
    public void basicDeleteEven2() { //no merge or borrow  //multi level tree - delete from 2 levels
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] basicDeleteEven2: Created B Plus tree");
        tree.insert(1.5, 1);
        tree.insert(4.6, 4);
        tree.insert(7.0, 7);
        tree.insert(10.0, 10);
        tree.insert(17.0, 17);
        tree.insert(21.0, 21);
        tree.insert(31.0, 31);
        tree.insert(25.0, 25);
        tree.insert(19.9, 19);
        tree.insert(20.01, 20);
        tree.insert(28.0, 28);
        tree.insert(42.0, 42);
        tree.delete(21.0);
        tree.delete(31.0);

        String ans = printTreeDouble(tree);
        String expected = "[[20.01]]\n[[7.0, 17.0]][[25.0, 42.0]]\n[[1.5, 4.6]][[7.0, 10.0]][[17.0, 19.9]][[20.01]][[25.0, 28.0]][[42.0]]" ;
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestDouble.java: basicDeleteEven2");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // [20.01]
       // [7.0 ,17.0]  [25.0,42.0]
       // [1.5 ,4.6] [7.0,10.0] [17.0 ,19.9] [20.01] [25.0,28.0] [42.0]
    }
    

    @Test
    public void basicDelete2Odd(){// no merge or borrow - multiple levels in tree
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] basicDelete2Odd: Created B Plus tree");
        tree.insert(1.5, 1);
        tree.insert(2.2, 2);
        tree.insert(3.0, 3);
        tree.insert(4.6, 4);
        tree.insert(5.0, 5);
        tree.delete(4.6);
        String ans = printTreeDouble(tree);
        String expected = "[[3.0]]\n[[1.5, 2.2]][[3.0, 5.0]]" ;
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestDouble.java: basicDelete2Odd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        //[3.0]
        //[1.5,2.2] [3.0,5.0]
    }

    @Test
    public void mergeAfterDeleteOdd(){// no merge or borrow - multiple levels in tree
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] mergeAfterDeleteOdd: Created B Plus tree");
        tree.insert(1.5, 1);
        tree.insert(2.2, 2);
        tree.insert(3.0, 3);
        tree.insert(4.6, 4);
        tree.insert(5.0, 5);
        tree.delete(4.6);
        tree.delete(5.0);
        String ans = printTreeDouble(tree);
        String expected = "[[1.5, 2.2, 3.0]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestDouble.java: mergeAfterDeleteOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        // [1.5,2.2,3.0]
    }

    @Test
    public void borrowFromRightOdd(){
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] borrowFromRightOdd: Created B Plus tree");
        tree.insert(0.0, 0);
        tree.insert(1.0, 1);
        tree.insert(2.1, 2);
        tree.insert(3.3, 3);
        tree.insert(4.4, 4);
        tree.insert(5.5, 5);
        tree.delete(0.0);
        String ans = printTreeDouble(tree);
        String expected = "[[3.3]]\n[[1.0, 2.1]][[3.3, 4.4, 5.5]]" ;
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestDouble.java: borrowFromRightOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        //[3.3]
        //[1.0,2.1] [3.3,4.4,5.5]
    }

    @Test
    public void borrowFromLeftOdd(){
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] borrowFromLeftOdd: Created B Plus tree");
        tree.insert(1.0, 1);
        tree.insert(2.1, 2);
        tree.insert(3.3, 3);
        tree.insert(4.4, 4);
        tree.insert(5.5, 5);
        tree.insert(0.0, 0);
        tree.delete(3.3);
        tree.delete(4.4);
        String ans = printTreeDouble(tree);
        String expected = "[[2.1]]\n[[0.0, 1.0]][[2.1, 5.5]]" ;
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestDouble.java: borrowFromLeftOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        //[2.1]
        //[0.0,1.0] [2.1,5.5]
    }

    @Test
    public void borrowFromLeftAndMergeOdd(){
        Integer order = 5;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] borrowFromLeftAndMergeOdd: Created B Plus tree");
        tree.insert(1.01, 1);
        tree.insert(1.02, 2);
        tree.insert(1.03, 3);
        tree.insert(1.04, 4);
        tree.insert(1.05, 5);
        tree.insert(1.0, 0);
        tree.delete(1.03);
        tree.delete(1.04);
        tree.delete(1.0);
        String ans = printTreeDouble(tree);
        String expected = "[[1.01, 1.02, 1.05]]" ;
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestDouble.java: borrowFromLeftAndMergeOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
       // [1.01,0.02,1.05]
    }
    @Test
    public void borrowFromRightEven(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] basicDeleteEven: Created B Plus tree");
        tree.insert(1.0, 1);
        tree.insert(4.1, 4);
        tree.insert(7.0, 7);
        tree.insert(10.0, 10);
        tree.insert(17.9, 17);
        tree.insert(21.0, 21);
        tree.insert(31.0, 31);
        tree.insert(25.0, 25);
        tree.insert(19.0, 19);
        tree.insert(20.0, 20);
        tree.insert(28.0, 28);
        tree.insert(42.0, 42);
        tree.delete(21.0);
        tree.delete(31.0);
        tree.delete(20.0);
        String ans = printTreeDouble(tree);
        // String expected = "[[25.0]]\n[[4.1, 17.9]][[28.0, 42.0]]\n[[1.0, 4.1]][[7.0, 10.0]][[17.9, 19.0]][[25.0]][[28.0]][[42.0]]" ;
         String expected = "[[25.0]]\n[[7.0, 17.9]][[28.0, 42.0]]\n[[1.0, 4.1]][[7.0, 10.0]][[17.9, 19.0]][[25.0]][[28.0]][[42.0]]" ;
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestDouble.java: borrowFromRightEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // [25.0]
       // [4.1,17.9]  [28.0,42.0]
       // [1.0 ,4.1] [7.0,10.0] [17.9 ,19.0] [25.0] [28.0] [42.0]

    }

    @Test
    public void borrowFromLeftEven(){
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] borrowFromLeftEven: Created B Plus tree");
        tree.insert(1.1, 1);
        tree.insert(4.1, 4);
        tree.insert(7.1, 7);
        tree.insert(10.1, 10);
        tree.insert(17.1, 17);
        tree.insert(21.1, 21);
        tree.insert(31.1, 31);
        tree.insert(25.1, 25);
        tree.insert(19.1, 19);
        tree.insert(20.1, 20);
        tree.insert(28.1, 28);
        tree.insert(42.1, 42);
        tree.delete(21.1);
        tree.delete(31.1);
        tree.delete(20.1);
        tree.delete(7.1);
        tree.delete(10.1);
        String ans = printTreeDouble(tree);
        String expected = "[[25.1]]\n[[7.1, 17.1]][[28.1, 42.1]]\n[[1.1, 4.1]][[17.1, 19.1]][[25.1]][[28.1]][[42.1]]" ;
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteTestDouble.java: borrowFromLeftEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // [25.1]
       // [7.1,17.1]  [28.1,42.1]
       // [1.1] [4.1] [17.1,19.1] [25.1] [28.1] [42.1]
    }
    @Test
    public void searchAfterDeleteEven() { // No split
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<Double> tree = new  BPlusTreeIndex<>(Double.class, "age");
        System.out.println("[LOG] searchAfterDeleteEven: Created B Plus tree");
        tree.insert(0.1, 1);
        tree.delete(0.1);
        List<Integer> ans =  tree.search(0.1);
        List<Integer> expected = new ArrayList<>();
        Collections.sort(ans);
        Collections.sort(expected);
        
        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] DeleteTestDouble.java: searchAfterDeleteEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }
    
}
