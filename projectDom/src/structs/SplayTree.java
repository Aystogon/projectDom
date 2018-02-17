package structs;


public class SplayTree<K extends Comparable<K>, V>  {
    /**
     * Definition of a splay node.
     */
    private class SplayNode {
        private K key;
        private V value;
        private SplayNode left, right;   
        
        public SplayNode() { }
        public SplayNode(K key, V value) {
            this.key   = key;
            this.value = value;
        }
        
        public K getKey() { return key; }
        public V getValue() { return value; }
        public void setValue(V value) { this.value = value; }
        public SplayNode getLeft() { return left; }
        public void setLeft(SplayNode left) { this.left = left; }
        public SplayNode getRight() { return right; }
        public void setRight(SplayNode right) { this.right = right; }
        public String toString() { return value.toString(); }
    }

    private SplayNode root;
    private int count;
    
    public SplayTree() { }
    public SplayTree(K key, V value) {
    	root = new SplayNode(key, value);
    	count++;
    }
    
    /**
     * Returns the amount of elements associated with 
     * the the current tree.
     * @return The size of the tree.
     */
    public int size() {
        return count;
    }
    
    /**
     * Returns the value associated with the given key.
     * If the return value is null, this indicates no such 
     * key has been found to be associated with a value.
     * @param key the key assumed to be associated with a value.
     * @return the element associated with the key.
     */
    public V access(K key) {
        root = splay(root, key);
        int comparison = key.compareTo(root.key);
        if (comparison == 0) { 
        	return root.value;
        } else {
        	return null;
        }
    }
    
    /**
     * Inserts the given key value pair to the splay tree by 
     * maping the associated key to the value.
     * @param key associated the given value.
     * @param value associated with the given key.
     */
    public void insert(K key, V value) {
    	
    	SplayNode newNode = new SplayNode(key, value);
    	count++;
    	
        if (root == null) {
            root = newNode;
            return;
        }
        
        root = splay(root, key);

        int comparison = key.compareTo(root.key);
        
        if (comparison < 0) {
            newNode.left = root.left;
            newNode.right = root;
            root.left = null;
            root = newNode;
        } else if (comparison > 0) {
            newNode.right = root.right;
            newNode.left = root;
            root.right = null;
            root = newNode;
        } else {
            root.setValue(value);
        }
    }


    /** 
     * This splays the key, then does a slightly modified <b>Hibbard deletion</b> on
     * the root (if it is the node to be deleted; if it is not, the key was 
     * not in the tree). The modification is that rather than swapping the
     * root (call it node A) with its successor, it's successor (call it Node B)
     * is moved to the root position by splaying for the deletion key in A's 
     * right subtree. Finally, A's right child is made the new root's right 
     * child.
     * 
     * @param key associated with the assumed value.
     */
    public V delete(K key) {
        if (root == null) { return new SplayNode().getValue(); }
        
        root = splay(root, key);
        SplayNode temp = root;
        
        int comparison1 = key.compareTo(root.getKey());
        
        if (comparison1 == 0) {
        	if (root.getLeft() == null) {
                root = root.getRight();
            } else {
                SplayNode x = root.getRight();
                root = root.getLeft();
                splay(root, key);
                root.setRight(x);
            }
        }
        count--;
        return temp.getValue();
    }
    /**
     * Using the current root, splay. If a node with the given key exists, splay it 
     * to the root of the tree. Else the last node along the path is splayed to the root.
     * @param root current node to check.
     * @param key associated value to compare nodes with.
     * @return the node associated with the key, or closest to the key.
     */
    private SplayNode splay(SplayNode root, K key) {
        if (root == null) { return null; }

        int comparison1 = key.compareTo(root.getKey()); // Check the value of root key with given key.

        // If given key less then root, check left node
        if (comparison1 < 0) {
            if (root.getLeft() == null) { return root; } // if left is null return the parent.
            
            int comparison2 = key.compareTo(root.getLeft().getKey()); // check current root key with left child key.
            
            if (comparison2 < 0) {
            	root.getLeft().setLeft(splay(root.getLeft().getLeft(), key));
                root = rotateRight(root);
                
            } else if (comparison2 > 0) {
            	root.getLeft().setRight(splay(root.getLeft().getRight(), key));
            	if (root.getLeft().getRight() != null) {
            		root.setLeft(rotateLeft(root.getLeft()));
            	}
            }
            
            if (root.getLeft() == null) {
            	return root;
            } else {
            	return rotateRight(root);
            }
            
            // The given key is greater than the current root key.
        } else if (comparison1 > 0) { 
        	
            if (root.getRight() == null) { return root; }

            int comparison2 = key.compareTo(root.getRight().getKey());
            
            if (comparison2 < 0) {
            	root.getRight().setLeft(splay(root.getRight().getLeft(), key));
            	
            	if (root.getRight().getLeft() != null) {
            		root.setRight(rotateRight(root.getRight()));
            	}
            	
            } else if (comparison2 > 0) {
            	root.getRight().setRight(splay(root.getRight().getRight(), key));
                root = rotateLeft(root);
            }
            
            if (root.getRight() == null) {
            	return root;
            } else {
            	return rotateLeft(root);
            }
        } else {
        	return root;
        }
    }
    /**
     * Performs splay right rotation. where the given node is sent
     * to be the right child and the left child of the current node
     * will become the parent node/root.
     * @param currentNode node to rotate.
     * @return the left node of the given node.
     */
    private SplayNode rotateRight(SplayNode currentNode) {
        SplayNode temp = currentNode.left;
        currentNode.left = temp.right;
        temp.right = currentNode;
        return temp;
    }
    /**
     * Performs splay left rotation. where the given node is sent
     * to be the left child and the right child of the current node
     * will become the parent node/root.
     * @param currentNode node to rotate.
     * @return the right node of given node.
     */
    private SplayNode rotateLeft(SplayNode currentNode) {
        SplayNode temp = currentNode.right;
        currentNode.right = temp.left;
        temp.left = currentNode;
        return temp;
    }
    /**
     * Returns the in-order-traversal of the 
     * current tree if such a traversal exists.
     */
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	inOrderTraversal(root, sb);
    	return sb.toString();
    }
    /**
     * In order traversal.
     * @param currentNode the current node to traverse to.
     * @param sb stringbuilder to append information to.
     */
    private void inOrderTraversal (SplayNode currentNode, StringBuilder sb) {
    	if (currentNode != null) {
    		sb.append("[" + currentNode.toString() + "]");
    		inOrderTraversal(currentNode.getLeft(), sb);
    		inOrderTraversal(currentNode.getRight(), sb);
    	}
    }
}