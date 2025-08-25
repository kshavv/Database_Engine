package in.ac.iitd.db362.BplusTree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import in.ac.iitd.db362.index.bplustree.BPlusTreeIndex;

class BPlusTreeVisualizerTest {

    @Test
    void testVisualization() {
        // Create a B+ Tree instance
        BPlusTreeIndex<String> bPlusTree = new BPlusTreeIndex<>(String.class, "testAttribute");
        bPlusTree.insert("1", 1);
        bPlusTree.insert("2", 2);
        bPlusTree.insert("3", 3);
        bPlusTree.insert("4", 4);
        bPlusTree.insert("5", 5);
        bPlusTree.insert("6", 6);
        bPlusTree.insert("7", 7);
        bPlusTree.insert("8", 8);
        bPlusTree.insert("9", 1);
        bPlusTree.insert("10", 2);

        // Keys with duplicates
        bPlusTree.insert("11", 3);
        bPlusTree.insert("11", 13);
        bPlusTree.insert("11", 113);
        bPlusTree.insert("11", 1113);
        bPlusTree.insert("11", 11113);

        bPlusTree.insert("12", 14);
        bPlusTree.insert("12", 114);
        bPlusTree.insert("12", 1114);

        bPlusTree.insert("13", 4);
        bPlusTree.insert("14", 41);
        bPlusTree.insert("15", 42);
        bPlusTree.insert("16", 43);

        bPlusTree.getHeight();
        bPlusTree.getAllKeys();

        String visualization = bPlusTree.visualize();
        System.out.println(visualization);

        System.out.println(bPlusTree.getHeight());

        // Example assertions (based on expected structure)
        //assertTrue(visualization.contains("Level 0: [10, 20]")); // Example of root keys
        //assertTrue(visualization.contains("Level 1: [5, 6, 7]")); // Example of child keys
    }

}