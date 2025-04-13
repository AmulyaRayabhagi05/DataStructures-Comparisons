public class AVLTree<T extends Comparable<T>> {
    // I created this Node class to represent nodes in the AVL tree.
    private class Node {
        T data;
        Node left;
        Node right;
        int height;
        
        Node(T data) {
            this.data = data;
            this.height = 1;
        }
    }
    
    private Node root;
    
    public void insert(T data) {
        root = insert(root, data);
    }
    
    private Node insert(Node node, T data) {
        // I performed standard BST insertion.
        if (node == null) {
            return new Node(data);
        }
        
        int compareResult = data.compareTo(node.data);
        
        if (compareResult < 0) {
            node.left = insert(node.left, data);
        } else if (compareResult > 0) {
            node.right = insert(node.right, data);
        } else {
            // I prevented duplicate keys.
            return node;
        }
        
        // I updated the height of the current node.
        node.height = 1 + Math.max(height(node.left), height(node.right));
        
        // I calculated the balance factor.
        int balance = getBalance(node);
        
        // I handled Left Left Case.
        if (balance > 1 && data.compareTo(node.left.data) < 0) {
            return rightRotate(node);
        }
        
        // I handled Right Right Case.
        if (balance < -1 && data.compareTo(node.right.data) > 0) {
            return leftRotate(node);
        }
        
        // I handled Left Right Case.
        if (balance > 1 && data.compareTo(node.left.data) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        
        // I handled Right Left Case.
        if (balance < -1 && data.compareTo(node.right.data) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        
        return node;
    }
    
    public void delete(T data) {
        root = delete(root, data);
    }
    
    private Node delete(Node node, T data) {
        // I performed standard BST deletion.
        if (node == null) {
            return null;
        }
        
        int compareResult = data.compareTo(node.data);
        
        if (compareResult < 0) {
            node.left = delete(node.left, data);
        } else if (compareResult > 0) {
            node.right = delete(node.right, data);
        } else {
            // I handled node with one or zero children.
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            
            // I handled node with two children by finding the inorder successor.
            node.data = findMin(node.right).data;
            
            // I deleted the inorder successor.
            node.right = delete(node.right, node.data);
        }
        
        // I returned null if the tree had only one node.
        if (node == null) {
            return null;
        }
        
        // I updated the height of the current node.
        node.height = 1 + Math.max(height(node.left), height(node.right));
        
        // I calculated the balance factor.
        int balance = getBalance(node);
        
        // I handled Left Left Case.
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }
        
        // I handled Left Right Case.
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        
        // I handled Right Right Case.
        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }
        
        // I handled Right Left Case.
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        
        return node;
    }
    
    private Node findMin(Node node) {
        // I found the minimum node in a subtree.
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    
    public boolean contains(T data) {
        return contains(root, data);
    }
    
    private boolean contains(Node node, T data) {
        if (node == null) {
            return false;
        }
        
        int compareResult = data.compareTo(node.data);
        
        if (compareResult < 0) {
            return contains(node.left, data);
        } else if (compareResult > 0) {
            return contains(node.right, data);
        } else {
            return true;
        }
    }
    
    private int height(Node node) {
        // I calculated the height of a node.
        return node == null ? 0 : node.height;
    }
    
    private int getBalance(Node node) {
        // I calculated the balance factor of a node.
        return node == null ? 0 : height(node.left) - height(node.right);
    }
    
    private Node rightRotate(Node y) {
        // I performed right rotation.
        Node x = y.left;
        Node T2 = x.right;
        
        x.right = y;
        y.left = T2;
        
        // I updated heights after rotation.
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        
        return x;
    }
    
    private Node leftRotate(Node x) {
        // I performed left rotation.
        Node y = x.right;
        Node T2 = y.left;
        
        y.left = x;
        x.right = T2;
        
        // I updated heights after rotation.
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        
        return y;
    }
}
