public class SplayTree<T extends Comparable<T>> {
    // I created this Node class to represent nodes in the Splay Tree.
    private class Node {
        T data;
        Node left;
        Node right;
        
        Node(T data) {
            this.data = data;
        }
    }
    
    private Node root;
    
    public void insert(T data) {
        if (root == null) {
            // I created a new root node if the tree is empty.
            root = new Node(data);
            return;
        }
        
        // I splayed the tree to bring the inserted node or its parent to the root.
        root = splay(root, data);
        
        int compareResult = data.compareTo(root.data);
        
        if (compareResult == 0) {
            // I handled duplicate data by returning without insertion.
            return;
        }
        
        Node newNode = new Node(data);
        
        if (compareResult < 0) {
            // I set the new node as the root and linked the old root as its right child.
            newNode.right = root;
            newNode.left = root.left;
            root.left = null;
        } else {
            // I set the new node as the root and linked the old root as its left child.
            newNode.left = root;
            newNode.right = root.right;
            root.right = null;
        }
        
        root = newNode;
    }
    
    public void delete(T data) {
        if (root == null) return;
        
        // I splayed the tree to bring the node to be deleted to the root.
        root = splay(root, data);
        
        if (data.compareTo(root.data) != 0) {
            // I returned if the key was not found.
            return;
        }
        
        // I handled the deletion when the key is found at the root.
        if (root.left == null) {
            root = root.right;
        } else {
            Node temp = root.right;
            root = root.left;
            // I found the maximum element in the left subtree.
            root = splay(root, data);
            // I linked the right subtree as the right child of the new root.
            root.right = temp;
        }
    }
    
    public boolean contains(T data) {
        if (root == null) return false;
        
        // I splayed the tree to bring the searched node or its parent to the root.
        root = splay(root, data);
        return data.compareTo(root.data) == 0;
    }
    
    private Node splay(Node root, T data) {
        if (root == null || data.compareTo(root.data) == 0) {
            return root;
        }
        
        int compareResult = data.compareTo(root.data);
        
        if (compareResult < 0) {
            // I handled the case where the key is in the left subtree.
            if (root.left == null) {
                return root;
            }
            
            int leftCompare = data.compareTo(root.left.data);
            
            if (leftCompare < 0) {
                // I performed Zig-Zig (left-left) rotation.
                root.left.left = splay(root.left.left, data);
                root = rightRotate(root);
            } else if (leftCompare > 0) {
                // I performed Zig-Zag (left-right) rotation.
                root.left.right = splay(root.left.right, data);
                if (root.left.right != null) {
                    root.left = leftRotate(root.left);
                }
            }
            
            return (root.left == null) ? root : rightRotate(root);
        } else {
            // I handled the case where the key is in the right subtree.
            if (root.right == null) {
                return root;
            }
            
            int rightCompare = data.compareTo(root.right.data);
            
            if (rightCompare > 0) {
                // I performed Zag-Zag (right-right) rotation.
                root.right.right = splay(root.right.right, data);
                root = leftRotate(root);
            } else if (rightCompare < 0) {
                // I performed Zag-Zig (right-left) rotation.
                root.right.left = splay(root.right.left, data);
                if (root.right.left != null) {
                    root.right = rightRotate(root.right);
                }
            }
            
            return (root.right == null) ? root : leftRotate(root);
        }
    }
    
    private Node rightRotate(Node y) {
        // I performed right rotation.
        Node x = y.left;
        y.left = x.right;
        x.right = y;
        return x;
    }
    
    private Node leftRotate(Node x) {
        // I performed left rotation.
        Node y = x.right;
        x.right = y.left;
        y.left = x;
        return y;
    }
}
