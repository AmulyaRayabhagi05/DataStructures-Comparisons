public class HashTables<T> {
    // I defined these collision resolution methods.
    private static final int CHAINING = 0;
    private static final int QUADRATIC_PROBING = 1;
    
    // I created this Node class for chaining.
    private class Node {
        T data;
        Node next;
        
        Node(T data) {
            this.data = data;
        }
    }
    
    // I created this EntryState enum for quadratic probing.
    private enum EntryState { EMPTY, OCCUPIED, DELETED }
    
    // I created this Entry class for quadratic probing.
    private class Entry {
        T data;
        EntryState state;
        
        Entry() {
            this.state = EntryState.EMPTY;
        }
    }
    
    private Node[] chainTable;
    private Entry[] quadTable;
    private int size;
    private int capacity;
    private int collisionMethod;
    
    @SuppressWarnings("unchecked") // I suppressed these unchecked cast warnings because of generic array creation.
    public HashTables(int capacity, int collisionMethod) {
        this.capacity = capacity;
        this.size = 0;
        this.collisionMethod = collisionMethod;
        
        if (collisionMethod == CHAINING) {
            chainTable = (Node[]) new HashTables.Node[capacity]; // I made a Node array here.
        } else {
            quadTable = (Entry[]) new HashTables.Entry[capacity]; // I made an Entry array here.
            for (int i = 0; i < capacity; i++) {
                quadTable[i] = new Entry();
            }
        }
    }
    
    public void insert(T data) {
        if (collisionMethod == CHAINING) {
            insertChaining(data);
        } else {
            insertQuadratic(data);
        }
    }
    
    private void insertChaining(T data) {
        int index = getIndex(data);
        
        // I checked if the data already exists.
        Node current = chainTable[index];
        while (current != null) {
            if (current.data.equals(data)) {
                return; // Element already exists
            }
            current = current.next;
        }
        
        // I inserted the new node at the beginning of the chain.
        Node newNode = new Node(data);
        newNode.next = chainTable[index];
        chainTable[index] = newNode;
        size++;
    }
    
    private void insertQuadratic(T data) {
        if (size >= capacity / 2) {
            // I resized the table because the load factor was exceeded.
            resize(capacity * 2);
        }
        
        int index = getIndex(data);
        int i = 1;
        int quadraticProbe = 0;
        
        while (quadTable[index].state == EntryState.OCCUPIED && !quadTable[index].data.equals(data)) {
            // I used quadratic probing to resolve the collision.
            quadraticProbe = i * i;
            index = (getIndex(data) + quadraticProbe) % capacity;
            i++;
            
            // I resized the table if I went through the entire table.
            if (i >= capacity) {
                resize(capacity * 2);
                // I reset the probing after resizing.
                index = getIndex(data);
                i = 1;
            }
        }
        
        // I checked if the element already exists.
        if (quadTable[index].state == EntryState.OCCUPIED && quadTable[index].data.equals(data)) {
            return;
        }
        
        // I inserted the element.
        quadTable[index].data = data;
        quadTable[index].state = EntryState.OCCUPIED;
        size++;
    }
    
    @SuppressWarnings("unchecked") // I suppressed these unchecked cast warnings because of generic array creation.
    private void resize(int newCapacity) {
        if (collisionMethod == CHAINING) {
            Node[] oldTable = chainTable;
            int oldCapacity = capacity;
            
            // I created the new table.
            capacity = newCapacity;
            chainTable = (Node[]) new HashTables.Node[capacity]; // I made a Node array here.
            size = 0;
            
            // I reinserted all elements.
            for (int i = 0; i < oldCapacity; i++) {
                Node current = oldTable[i];
                while (current != null) {
                    insertChaining(current.data);
                    current = current.next;
                }
            }
        } else {
            Entry[] oldTable = quadTable;
            int oldCapacity = capacity;
            
            // I created the new table.
            capacity = newCapacity;
            quadTable = (Entry[]) new HashTables.Entry[capacity]; // I made an Entry array here.
            for (int i = 0; i < capacity; i++) {
                quadTable[i] = new Entry();
            }
            size = 0;
            
            // I reinserted all elements.
            for (int i = 0; i < oldCapacity; i++) {
                if (oldTable[i].state == EntryState.OCCUPIED) {
                    insertQuadratic(oldTable[i].data);
                }
            }
        }
    }
    
    public void delete(T data) {
        if (collisionMethod == CHAINING) {
            deleteChaining(data);
        } else {
            deleteQuadratic(data);
        }
    }
    
    private void deleteChaining(T data) {
        int index = getIndex(data);
        
        // I checked if the list is empty.
        if (chainTable[index] == null) {
            return;
        }
        
        // I checked if the first node is the one to delete.
        if (chainTable[index].data.equals(data)) {
            chainTable[index] = chainTable[index].next;
            size--;
            return;
        }
        
        // I searched for the node to delete.
        Node current = chainTable[index];
        while (current.next != null && !current.next.data.equals(data)) {
            current = current.next;
        }
        
        // I deleted the node if I found it.
        if (current.next != null) {
            current.next = current.next.next;
            size--;
        }
    }
    
    private void deleteQuadratic(T data) {
        int index = findIndexQuadratic(data);
        
        if (index != -1) {
            quadTable[index].state = EntryState.DELETED;
            size--;
        }
    }
    
    public boolean contains(T data) {
        if (collisionMethod == CHAINING) {
            return containsChaining(data);
        } else {
            return containsQuadratic(data);
        }
    }
    
    private boolean containsChaining(T data) {
        int index = getIndex(data);
        
        Node current = chainTable[index];
        while (current != null) {
            if (current.data.equals(data)) {
                return true;
            }
            current = current.next;
        }
        
        return false;
    }
    
    private boolean containsQuadratic(T data) {
        return findIndexQuadratic(data) != -1;
    }
    
    private int findIndexQuadratic(T data) {
        int index = getIndex(data);
        int i = 1;
        int quadraticProbe = 0;
        
        while (quadTable[index].state != EntryState.EMPTY) {
            if (quadTable[index].state == EntryState.OCCUPIED && quadTable[index].data.equals(data)) {
                return index;
            }
            
            // I used quadratic probing to find the index.
            quadraticProbe = i * i;
            index = (getIndex(data) + quadraticProbe) % capacity;
            i++;
            
            // I stopped searching if I went through the entire table.
            if (i >= capacity) {
                break;
            }
        }
        
        return -1;
    }
    
    private int getIndex(T data) {
        // I got the positive hash code.
        int hashCode = data.hashCode() & 0x7fffffff;
        return hashCode % capacity;
    }
}
