package in.ac.iitd.db362.index.bplustree;

import in.ac.iitd.db362.index.Index;
import in.ac.iitd.db362.parser.QueryNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Starter code for BPlusTree Implementation
 * @param <T> The type of the key.
 */
public class BPlusTreeIndex<T> implements Index<T> {

    protected static final Logger logger = LogManager.getLogger();

    private final Class<T> type;

    // Note: Do not rename this variable; the test cases will set this when testing. You can however initialize it with a
    // different value for testing your code.
    public static int ORDER = 4;

    // The attribute being indexed
    private String attribute;

    // Our Values are all integers (rowIds)
    private Node<T, Integer> root;
    private final int order; // Maximum children per node

    /** Constructor to initialize the B+ Tree with a given order */
    public BPlusTreeIndex(Class<T> type, String attribute) {
        this.type = type;
        this.attribute = attribute;
        this.order = ORDER;
        this.root = new Node<>();
        this.root.isLeaf = true;
    }


    private T castValue(Object value, Class<T> targetType) {

        if (targetType.equals(Integer.class)) {
            return targetType.cast(Integer.valueOf((String) value));
        } else if (targetType.equals(Double.class)) {
            return targetType.cast(Double.valueOf((String) value));
        }else if(targetType.equals(LocalDate.class)){
            return targetType.cast(LocalDate.parse((String) value));
        }
        //return string if the type is of string
        return targetType.cast(value);
    }


    @Override
    public List<Integer> evaluate(QueryNode node) {
        logger.info("Evaluating predicate using B+ Tree index on attribute " + attribute + " for operator " + node.operator);

        List<Integer> result = new ArrayList<>();

        T value = null;
        value = castValue(node.value, this.type);

        //Assuming other operators would be somehow implemented at query Evaluator step
        switch (node.operator) {
            case EQUALS:
                result = search(value);
                break;

            case LT:
                result = rangeQuery(null, false, value, false);
                break;

            case GT:
                result = rangeQuery(value, false, null, false);
                break;

            case RANGE:
                T secondValue = null;
                secondValue = castValue(node.secondValue, this.type);
                result = rangeQuery(value, false, secondValue, false);
                break;

            default:
                logger.warn("operator not supported on B+ tree" + node.operator);
        }
        return result;
    }

    @Override
    public void insert(T key, int rowId) {

        if(root.keys == null && root.values ==null && root.children == null && root.isLeaf){
            //init the root keys and values attributes - moving this from the constructor
            root.keys = new ArrayList<>();
            root.values = new ArrayList<>();
            root.children=new ArrayList<>();
        }

        Node<T, Integer> currNode = root;
        Node<T, Integer> parentNode = null;

        // Traversing the tree to find the correct leaf node to insert at
        while (!currNode.isLeaf()) {
            parentNode = currNode;
            int offset = 0;
            while (offset < currNode.keys.size() && ((Comparable<T>) key).compareTo(currNode.keys.get(offset)) >= 0) {
                offset++;
            }
            currNode = currNode.getChild(offset);
        }

        //at this point we are at the correct leaf node
        //find the index to insert at in the  leaf node
        int offset = 0;
        while (offset < currNode.keys.size() && ((Comparable<T>) key).compareTo(currNode.keys.get(offset)) > 0) {
            offset++;
        }

        //now we know where to insert the key,value in the leaf node
        //check if the current key is duplicate(i.e already present)
        if(offset < currNode.keys.size() && ((Comparable<T>) key).compareTo(currNode.keys.get(offset)) == 0){
            handleDuplicateKey(currNode,offset,key,rowId);
        }
        else{
            currNode.keys.add(offset, key);
            currNode.values.add(offset, rowId);

            // Split the node if overflow occurs
            if (currNode.keys.size() >= order) {
                splitNode(currNode, parentNode);
            }
        }
    }

    private void splitNode(Node<T, Integer> node, Node<T, Integer> parent) {

        int midIndex = node.keys.size() / 2;
        Node<T, Integer> newNode = createNode(node.isLeaf());

        if(node.isLeaf()){
            //moving right half of the keys and values to the new node
            newNode.keys.addAll(node.keys.subList(midIndex, node.keys.size()));
            newNode.values.addAll(node.values.subList(midIndex, node.values.size()));

            //moving overflow nodes for duplicate keys
            for (int i = midIndex; i < node.keys.size(); i++) {
                if (i < node.children.size() && node.children.get(i) != null) {
                    // Adjust index for the new node
                    int newIndex = i - midIndex;

                    // Ensure the new node's children list has enough capacity
                    while (newNode.children.size() <= newIndex) {
                        newNode.children.add(null);
                    }

                    // Move the overflow node to the new node
                    newNode.children.set(newIndex, node.children.get(i));
                    node.children.set(i, null);
                }
            }

            //removing right half the keys and values from the original node
            node.keys.subList(midIndex, node.keys.size()).clear();
            node.values.subList(midIndex, node.values.size()).clear();

            //setting the pointers for leaf node
            newNode.next = node.next;
            node.next = newNode;

        }
        else{
            //for non leaf node(we need to remove this mid-index later since we do not require this on leaf node)
            newNode.keys.addAll(node.keys.subList(midIndex, node.keys.size()));

            node.keys.subList(midIndex, node.keys.size()).clear();

            newNode.children.addAll(node.children.subList(midIndex + 1, node.children.size()));
            node.children.subList(midIndex + 1, node.children.size()).clear();
        }

        // Inserting the middle key into the parent node (if exists) or create a new root if no parent
        if (parent == null) {
            Node<T, Integer> newRoot = createNode(false);
//            newRoot.isLeaf = false;
            newRoot.keys.add(newNode.keys.get(0));
            newRoot.children.add(node);
            newRoot.children.add(newNode);
            root = newRoot;
        }
        else {
            //find the appropriate place to insert the index in the parent node
            int insertPosition = 0;
            while (insertPosition < parent.keys.size() && ((Comparable<T>) newNode.keys.get(0)).compareTo(parent.keys.get(insertPosition)) > 0) {
                insertPosition++;
            }
            parent.keys.add(insertPosition, newNode.keys.get(0));
            parent.children.add(insertPosition + 1, newNode);// new node will go to the next index


            // Check for parent overflow
            if (parent.keys.size() >= order) {
                //find the parent of parent
                Node<T, Integer> grandparent = findParent(root, parent);
                splitNode(parent, grandparent);
            }
        }
        //if it's a non leaf node we need to remove the middle key which was shifted to the parent
        if(!newNode.isLeaf){
            //how to remove the key from the zeroth index
            newNode.keys.remove(0);
        }
    }

    /**
     * Handle duplicate key by using children attribute of leaf nodes
     */
    private void handleDuplicateKey(Node<T, Integer> node, int offset, T key, int rowId) {

        while (node.children.size() <= offset) {
            node.children.add(null);
        }

        // If there's no overflow node yet for this key
        if (node.children.get(offset) == null) {
            // Create the first overflow node
            Node<T, Integer> overflowNode = createNode(true);
            overflowNode.keys.add(key);
            overflowNode.values.add(rowId);
            node.children.set(offset, overflowNode);
            return;
        }


        Node<T, Integer> currentOverflow = node.children.get(offset);


        while (currentOverflow.next != null && currentOverflow.keys.size() >= order - 1) {
            currentOverflow = currentOverflow.next;
        }

        if (currentOverflow.keys.size() >= order - 1) {
            Node<T, Integer> newOverflow = createNode(true);
            newOverflow.keys.add(key);
            newOverflow.values.add(rowId);
            currentOverflow.next = newOverflow;
        } else {
            // Add to existing overflow node
            currentOverflow.keys.add(key);
            currentOverflow.values.add(rowId);
        }
    }


    @Override
    public boolean delete(T key) {
        Node<T, Integer> currNode = root;
        Node<T, Integer> parentNode = null;
        int parentIndex = -1;
        boolean deleted = false;

        // Traverse down to find the leaf node containing the key
        while (!currNode.isLeaf()) {
            parentNode = currNode;
            int offset = 0;
            while (offset < currNode.keys.size() && ((Comparable<T>) key).compareTo(currNode.keys.get(offset)) >= 0) {
                offset++;
            }
            parentIndex = offset;
            currNode = currNode.getChild(offset);
        }

        //at this point current nodes holds the leaf node where we have to attempt the deletion
        int offset = 0;
        while (offset < currNode.keys.size() && ((Comparable<T>) key).compareTo(currNode.keys.get(offset)) > 0) {
            offset++;
        }

        if (offset < currNode.keys.size() && ((Comparable<T>) key).compareTo(currNode.keys.get(offset)) == 0) {
            // Remove the key and value at the current offset
            currNode.keys.remove(offset);
            currNode.values.remove(offset);

            // Remove all duplicates stored in child overflow nodes
            Node<T, Integer> duplicateNode = currNode.children.size() > offset ? currNode.children.get(offset) : null;
            while (duplicateNode != null) {
                duplicateNode.keys.clear();
                duplicateNode.values.clear();
                if(duplicateNode.next != null)
                    duplicateNode = duplicateNode.next;
                else
                    break;
            }
            if (currNode.children.size() > offset) {
                currNode.children.set(offset, null); // Clear the reference to the duplicate chain
            }

            deleted = true; // Mark deletion as successful
        }


        // Handle underflow if necessary
        if (deleted && parentNode != null && parentNode.keys.size() < (order - 1) / 2) {
            handleUnderflow(currNode, parentNode, parentIndex);
        }

        return deleted;
    }


    private void handleUnderflow(Node<T, Integer> node, Node<T, Integer> parent, int parentIndex) {
        if (parent == null) {
            // If root is empty and has only one child, make that child the new root
            if (node.keys.isEmpty() && !node.isLeaf() && node.children.size() == 1) {
                root = node.children.get(0);
            }
            return;
        }

        Node<T, Integer> leftSibling = (parentIndex > 0) ? parent.getChild(parentIndex - 1) : null;
        Node<T, Integer> rightSibling = (parentIndex < parent.children.size() - 1) ? parent.getChild(parentIndex + 1) : null;

        if (leftSibling != null && leftSibling.keys.size() > (order - 1) / 2) {
            // Borrow from left sibling
            node.keys.add(0, parent.keys.get(parentIndex - 1));
            node.values.add(0, leftSibling.values.remove(leftSibling.values.size() - 1));
            parent.keys.set(parentIndex - 1, leftSibling.keys.remove(leftSibling.keys.size() - 1));
        } else if (rightSibling != null && rightSibling.keys.size() > (order - 1) / 2) {
            // Borrow from right sibling
            node.keys.add(parent.keys.get(parentIndex));
            node.values.add(rightSibling.values.remove(0));
            parent.keys.set(parentIndex, rightSibling.keys.remove(0));
        } else {
            // Merge with sibling
            if (leftSibling != null) {
                leftSibling.keys.addAll(node.keys);
                leftSibling.values.addAll(node.values);
                parent.keys.remove(parentIndex - 1);
                parent.children.remove(parentIndex);
            } else if (rightSibling != null) {
                node.keys.addAll(rightSibling.keys);
                node.values.addAll(rightSibling.values);
                parent.keys.remove(parentIndex);
                parent.children.remove(parentIndex + 1);
            }

            // Check if parent underflow
            if (parent.keys.size() < (order - 1) / 2) {
                Node<T, Integer> grandparent = findParent(root, parent);
                handleUnderflow(parent, grandparent, findParentIndex(grandparent, parent));
            }
        }
    }


    private int findParentIndex(Node<T, Integer> grandparent, Node<T, Integer> parent) {
        if (grandparent == null) {
            return -1;
        }
        for (int i = 0; i < grandparent.children.size(); i++) {
            if (grandparent.getChild(i) == parent) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public List<Integer> search(T key) {

        Node<T, Integer> current = root;

        // Check if root has an uninitialized keys array and return an empty array if true
        if (current == null || current.keys == null) {
            return new ArrayList<>();
        }

        // Traverse to the correct leaf node
        while (current != null && !current.isLeaf) {
            int offset = 0;
            while (offset < current.keys.size() && ((Comparable<T>) key).compareTo(current.keys.get(offset)) >= 0) {
                offset++;
            }
            current = current.getChild(offset); // Navigate down the tree
        }

        // At this point, we are at the correct leaf node.
        List<Integer> result = new ArrayList<>();
        while (current != null) {
            for (int i = 0; i < current.keys.size(); i++) {
                if (((Comparable<T>) key).compareTo(current.keys.get(i)) == 0) {
                    result.add(current.values.get(i));

                    // Handle duplicate keys through overflow nodes
                    Node<T, Integer> overflowNode = current.children.size() > i ? current.children.get(i) : null;
                    while (overflowNode != null) {
                        result.addAll(overflowNode.values);
                        overflowNode = overflowNode.next;
                    }
                } else if (((Comparable<T>) key).compareTo(current.keys.get(i)) < 0) {
                    return result; // Exit early since the keys are sorted
                }
            }
            current = current.getNext();

        }

        return result;
    }

    /**
     * Function that evaluates a range query and returns a list of rowIds.
     * e.g., 50 < x <=75, then function can be called as rangeQuery(50, false, 75, true)
     * @param startKey
     * @param startInclusive
     * @param endKey
     * @param endInclusive
     * @return all rowIds that satisfy the range predicate
     */
     public List<Integer> rangeQuery(T startKey, boolean startInclusive, T endKey, boolean endInclusive) {
        List<Integer> result = new ArrayList<>();
        Node<T, Integer> current = root;

        // Check if root has an uninitialized keys array and return an empty array if true
        if (current == null || current.keys == null) {
            return new ArrayList<>();
        }
        // Traverse to the leftmost leaf node that might contain the startKey
        while (current != null && !current.isLeaf) {
            int offset = 0;
            while ((offset < current.keys.size() ) && (startKey!=null) && (((Comparable<T>) startKey).compareTo(current.keys.get(offset)) >= 0)) {
                offset++;
            }
            current = current.getChild(offset);
        }

        // Traverse the leaf nodes and collect all rowIds within the range
        while (current != null) {
            for (int i = 0; i < current.keys.size(); i++) {
                T key = current.keys.get(i);
                boolean withinStartBound = startInclusive
                        ? (startKey==null) || (((Comparable<T>) key).compareTo(startKey) >= 0)
                        : (startKey==null) || (((Comparable<T>) key).compareTo(startKey) > 0);
                boolean withinEndBound = endInclusive
                        ? (endKey==null) || (((Comparable<T>) key).compareTo(endKey) <= 0)
                        : (endKey==null) || (((Comparable<T>) key).compareTo(endKey) < 0);

                if (withinStartBound && withinEndBound) {
                    result.add(current.values.get(i));

                    // Handle duplicate keys through overflow nodes
                    Node<T, Integer> overflowNode = current.children.size() > i ? current.children.get(i) : null;
//                    Node<T, Integer> overflowNode =  current.children.get(i);

                    while (overflowNode != null) {
                        result.addAll(overflowNode.values);
                        overflowNode = overflowNode.next;
                    }
                } else if (!withinEndBound) {
                    return result; // Exit early if we've passed the range
                }
            }
            current = current.getNext(); // Move to the next leaf node

        }

        return result;
    }


    /**Helper functions implemented**/
    private Node<T, Integer> findParent(Node<T, Integer> current, Node<T, Integer> target) {
        if (current == null || current.isLeaf) return null; // Base case

        for (int i = 0; i < current.children.size(); i++) {
            Node<T, Integer> child = current.getChild(i);
            if (child == target) return current; // Found parent
            Node<T, Integer> possibleParent = findParent(child, target);
            if (possibleParent != null) return possibleParent;
        }
        return null;
    }
    public static <K,V> Node<K,V> createNode(boolean isLeaf){
        Node<K,V> node = new Node<>();
        node.keys = new ArrayList<K>();
        if(isLeaf){
            node.values = new ArrayList<V>();
            node.children=new ArrayList<Node<K,V>>(); //init children for leaf node for duplicate key handling
            node.isLeaf = true;

        }
        else{
            node.children=new ArrayList<Node<K,V>>();
            node.isLeaf =false;
        }
        return node;
    }

    /**
     * Traverse leaf nodes and collect all keys in sorted order
     * @return all Keys
     */
    public List<T> getAllKeys() {
        List<T> allKeys = new ArrayList<>();
        Node<T, Integer> current = root;

        if(current == null || current.keys == null)
            return allKeys;

        // Traverse to the leftmost leaf node
        while (current != null && !current.isLeaf) {
            current = current.getChild(0);
        }

        // Traversing through the chained leaf nodes in B+ trees
        while (current != null) {
            allKeys.addAll(current.keys);
            // Handle duplicate keys by traversing the children overflow nodes
            for (int i = 0; i < current.children.size(); i++) {
                Node<T, Integer> overflowNode = current.children.get(i);
                while (overflowNode != null) {
                    allKeys.addAll(overflowNode.keys);
                    overflowNode = overflowNode.next;
                }
            }
            current = current.next;
        }

        allKeys.sort(null); // Sort the allKeys list in ascending order
        return allKeys;
    }

    /**
     * Compute tree height by traversing from root to leaf
     * @return Height of the b+ tree
     */
    public int getHeight() {

        int height = 0;
        Node<T, Integer> current = root;

        while (current != null) {
            height++;
            if (!current.isLeaf) {
                current = current.getChild(0); // Traverse to the first child (left-most path)
            } else {
                break;
            }
        }
        return height-1;
    }

    /**
     * Funtion that returns the order of the BPlusTree
     * Note: Do not remove this function!
     * @return
     */
    public int getOrder() {
        return order;
    }


    public String getAttribute() {
        return attribute;
    }

    public Node<T, Integer> getRoot() {
        return root;
    }


    @Override
    public String prettyName() {
        return "B+Tree Index";
    }



    //***************************************test code added****************************************************

    /**
     * Recursive method to visualize the B+ Tree structure.
     * Traverses the tree level by level and outputs the keys in each node.
     *
     * @return A string representation of the B+ Tree structure.
     */
    public String visualize() {
        if (root == null) {
            return "Tree is empty";
        }
        StringBuilder result = new StringBuilder();
        visualizeNode(root, result, 0);
        return result.toString();
    }

    /**
     * Helper function to recursively traverse and collect node details.
     *
     * @param node  The current node being traversed.
     * @param result A StringBuilder to collect the visualization output.
     * @param level  The level of the current node in the tree.
     */
    private void visualizeNode(Node<T, Integer> node, StringBuilder result, int level) {
        if (node == null) {
            return;
        }

        // Indent based on the depth level of the tree
        result.append("Level ").append(level).append(": ").append(node.keys).append("\n");

        // If the node is not a leaf, continue traversing its children
        if (!node.isLeaf) {
            for (Node<T, Integer> child : node.children) {
                if (child != null) {
                    visualizeNode(child, result, level + 1); // Recursively handle child nodes
                }
            }
        }
    }

}
