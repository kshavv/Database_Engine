package in.ac.iitd.db362.bplustree;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import in.ac.iitd.db362.index.bplustree.BPlusTreeIndex;
import in.ac.iitd.db362.index.bplustree.Node;
public class DeleteLocalDateTest {
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
    public void basicDeleteOdd() {
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<LocalDate> tree = new BPlusTreeIndex<>(LocalDate.class, "name"); // Degree: 3
        tree.insert(LocalDate.of(2000, 1, 1), 1);
        tree.insert(LocalDate.of(2001, 2, 2), 2);
        tree.insert(LocalDate.of(2002, 3, 3), 3);
        tree.insert(LocalDate.of(2003, 4, 4), 4);
        tree.delete(LocalDate.of(2003, 4, 4));
        String ans  = printTreeLocalDate(tree);
        // String expected = "[[2000-01-01, 2001-02-02]]\n" + "[[2000-01-01]][[2001-02-02]][[2002-03-03]]";
        String expected = "[[2001-02-02, 2002-03-03]]\n" + "[[2000-01-01]][[2001-02-02]][[2002-03-03]]";
        
        //  [2000-01-01, 2001-02-02]
        //  [2000-01-01] [2001-02-02] [2002-03-03] 

        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            // print file name and testcase name
            System.out.println("[LOG] DeleteLocalDateTest.java: basicDeleteOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
    }

    @Test
    public void deleteRootOdd() {
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<LocalDate> tree = new BPlusTreeIndex<>(LocalDate.class, "name"); 
        // Degree: 3
        tree.insert(LocalDate.of(2000, 1, 1), 1);
        tree.insert(LocalDate.of(2000, 2, 1), 2);
        tree.insert(LocalDate.of(2000, 3, 1), 3);
        tree.delete(LocalDate.of(2000, 2, 1));
        String ans  = printTreeLocalDate(tree);
        String expected = "[[2000-03-01]]\n" +"[[2000-01-01]][[2000-03-01]]";


        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteLocalDateTest.java: deleteRootOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        assertTrue(result);
        // Expected Output: [2000-01-01]
        //                 [2000-01-01] [2000-03-01]
    }
    
    @Test
    public void deleteAllEven() {
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<LocalDate> tree = new BPlusTreeIndex<>(LocalDate.class, "name"); // Degree: 3
        tree.insert(LocalDate.of(2000, 1, 1), 1);
        tree.insert(LocalDate.of(2001, 2, 2), 2);
        tree.insert(LocalDate.of(2002, 3, 3), 3);
        tree.insert(LocalDate.of(2003, 4, 4), 4);
        tree.delete(LocalDate.of(2002, 3, 3));
        tree.delete(LocalDate.of(2003, 4, 4));
        tree.delete(LocalDate.of(2001, 2, 2));
        tree.delete(LocalDate.of(2000, 1, 1));
        String ans  = printTreeLocalDate(tree);
        String expected = "";
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteLocalDateTest.java: deleteAllEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        // Expected Output: [] (Empty tree)
    }

    @Test
    public void borrowFromRightOdd() {
        Integer order = 3;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<LocalDate> tree = new BPlusTreeIndex<>(LocalDate.class, "name"); // Degree: 3
        tree.insert(LocalDate.of(2000, 1, 1), 1);
        tree.insert(LocalDate.of(2001, 2, 2), 2);
        tree.insert(LocalDate.of(2002, 3, 3), 3);
        tree.insert(LocalDate.of(2003, 4, 4), 4);
        tree.insert(LocalDate.of(2004, 5, 5), 5);
        tree.delete(LocalDate.of(2000, 1, 1));
        String ans  = printTreeLocalDate(tree);
        String expected = "[[2002-03-03, 2003-04-04]]\n" + "[[2001-02-02]][[2002-03-03]][[2003-04-04, 2004-05-05]]";
        
        ans = ans.replace(" ", "");
        expected = expected.replace(" ", "");
        boolean result = ans.contains(expected);
        if(!result) {
            System.out.println("[LOG] DeleteLocalDateTest.java: borrowFromRightOdd");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans: " + ans);
        }
        
        assertTrue(result);
        // Expected Output: [2002-03-03, 2003-04-04]
        //                 [[2001-02-02]][[2002-03-03]][[2003-04-04, 2004-05-05]]
    }

    @Test
    public void searchAfterDeleteEven() { // No split
        Integer order = 4;
        BPlusTreeIndex.ORDER = order;

        BPlusTreeIndex<LocalDate> tree = new BPlusTreeIndex<>(LocalDate.class, "dateOfBirth");
        System.out.println("[LOG] searchAfterDeleteEven: Created B Plus tree");

        LocalDate date = LocalDate.of(2023, 3, 31);  
        tree.insert(date, 1);
        tree.delete(date);
        
        List<Integer> ans = tree.search(date);
        List<Integer> expected = new ArrayList<>();

        boolean result = ans.equals(expected); 
        if (!result) {
            System.out.println("[LOG] DeleteLocalDateTest.java: searchAfterDeleteEven");
            System.out.println("[LOG] Expected: " + expected);
            System.out.println("[LOG] Ans  : " + ans);
        }
        assertTrue(result);  
    }
    
    
}
