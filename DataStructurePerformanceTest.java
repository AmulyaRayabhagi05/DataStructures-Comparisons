import java.util.Random;
import java.text.DecimalFormat;

public class DataStructurePerformanceTest {
    private static final int[] DATA_SIZES = {1000, 10000, 100000};
    private static final int HASH_TABLE_INITIAL_CAPACITY = 16;
    private static final int CHAINING = 0;
    private static final int QUADRATIC_PROBING = 1;
    private static final Random RANDOM = new Random(42); // I made it the same seed in order to repeat the same test case but i can make it completely random as well
    private static final DecimalFormat df = new DecimalFormat("0.000");
    
    public static void main(String[] args) {
        // Test insertion
        System.out.println("Insertion Performance Comparison (Time in milliseconds)");
        System.out.println("Data Structure\t\t\t1,000 Elements\t\t10,000 Elements\t\t100,000 Elements");
        System.out.println("----------------------------------------------------------------------------------------");
        
        double[][] insertionTimes = new double[4][3];
        
        // here i test avl insertion 
        for (int i = 0; i < DATA_SIZES.length; i++) {
            insertionTimes[0][i] = testAVLTreeInsertion(DATA_SIZES[i]);
        }
        
        // here i do the splay tree insertion
        for (int i = 0; i < DATA_SIZES.length; i++) {
            insertionTimes[1][i] = testSplayTreeInsertion(DATA_SIZES[i]);
        }
        
        // and this is the hash table chainging version 
        for (int i = 0; i < DATA_SIZES.length; i++) {
            insertionTimes[2][i] = testHashTableInsertion(DATA_SIZES[i], CHAINING);
        }
        
        // This is the hash table quadratic version
        for (int i = 0; i < DATA_SIZES.length; i++) {
            insertionTimes[3][i] = testHashTableInsertion(DATA_SIZES[i], QUADRATIC_PROBING);
        }
        
        // this just prints insertion 
        System.out.printf("AVL Tree                      \t%s ms\t\t\t%s ms\t\t\t%s ms\t\t%n", 
                df.format(insertionTimes[0][0]), df.format(insertionTimes[0][1]), df.format(insertionTimes[0][2]));
        System.out.printf("Splay Tree                    \t%s ms\t\t\t%s ms\t\t\t%s ms\t\t%n", 
                df.format(insertionTimes[1][0]), df.format(insertionTimes[1][1]), df.format(insertionTimes[1][2]));
        System.out.printf("Hash Table (Chaining)         \t%s ms\t\t\t%s ms\t\t\t%s ms\t\t%n", 
                df.format(insertionTimes[2][0]), df.format(insertionTimes[2][1]), df.format(insertionTimes[2][2]));
        System.out.printf("Hash Table (Quadratic Probing)\t%s ms\t\t\t%s ms\t\t\t%s ms\t\t%n", 
                df.format(insertionTimes[3][0]), df.format(insertionTimes[3][1]), df.format(insertionTimes[3][2]));
        
        System.out.println("\n");
        
        // this tests the search
        System.out.println("Search Performance Comparison (Time in milliseconds)");
        System.out.println("Data Structure\t\t\t1,000 Elements\t\t10,000 Elements\t\t100,000 Elements");
        System.out.println("----------------------------------------------------------------------------------------");
        
        double[][] searchTimes = new double[4][3];
        
        // this creates datasets for search operations
        Integer[][] datasets = new Integer[3][];
        for (int i = 0; i < DATA_SIZES.length; i++) {
            datasets[i] = generateRandomDataset(DATA_SIZES[i]);
        }
        
        // This test the avl search
        for (int i = 0; i < DATA_SIZES.length; i++) {
            AVLTree<Integer> avlTree = new AVLTree<>();
            for (Integer value : datasets[i]) {
                avlTree.insert(value);
            }
            searchTimes[0][i] = testAVLTreeSearch(avlTree, datasets[i]);
        }
        
        // This test the splay tree search
        for (int i = 0; i < DATA_SIZES.length; i++) {
            SplayTree<Integer> splayTree = new SplayTree<>();
            for (Integer value : datasets[i]) {
                splayTree.insert(value);
            }
            searchTimes[1][i] = testSplayTreeSearch(splayTree, datasets[i]);
        }
        
        // This test the hash but the chaining search
        for (int i = 0; i < DATA_SIZES.length; i++) {
            HashTables<Integer> hashTable = new HashTables<>(HASH_TABLE_INITIAL_CAPACITY, CHAINING);
            for (Integer value : datasets[i]) {
                hashTable.insert(value);
            }
            searchTimes[2][i] = testHashTableSearch(hashTable, datasets[i]);
        }
        
        // This test the hash quadratic search
        for (int i = 0; i < DATA_SIZES.length; i++) {
            HashTables<Integer> hashTable = new HashTables<>(HASH_TABLE_INITIAL_CAPACITY, QUADRATIC_PROBING);
            for (Integer value : datasets[i]) {
                hashTable.insert(value);
            }
            searchTimes[3][i] = testHashTableSearch(hashTable, datasets[i]);
        }
        
        // Print search results
        System.out.printf("AVL Tree                      \t%s ms\t\t\t%s ms\t\t\t%s ms\t\t%n", 
                df.format(searchTimes[0][0]), df.format(searchTimes[0][1]), df.format(searchTimes[0][2]));
        System.out.printf("Splay Tree                    \t%s ms\t\t\t%s ms\t\t\t%s ms\t\t%n", 
                df.format(searchTimes[1][0]), df.format(searchTimes[1][1]), df.format(searchTimes[1][2]));
        System.out.printf("Hash Table (Chaining)         \t%s ms\t\t\t%s ms\t\t\t%s ms\t\t%n", 
                df.format(searchTimes[2][0]), df.format(searchTimes[2][1]), df.format(searchTimes[2][2]));
        System.out.printf("Hash Table (Quadratic Probing)\t%s ms\t\t\t%s ms\t\t\t%s ms\t\t%n", 
                df.format(searchTimes[3][0]), df.format(searchTimes[3][1]), df.format(searchTimes[3][2]));
        
        System.out.println("\n");
        
        // this test the deletion portion
        System.out.println("Deletion Performance Comparison (Time in milliseconds)");
        System.out.println("Data Structure\t\t\t1,000 Elements\t\t10,000 Elements\t\t100,000 Elements");
        System.out.println("----------------------------------------------------------------------------------------");
        
        double[][] deletionTimes = new double[4][3];
        
        // This does the avl deletion
        for (int i = 0; i < DATA_SIZES.length; i++) {
            AVLTree<Integer> avlTree = new AVLTree<>();
            for (Integer value : datasets[i]) {
                avlTree.insert(value);
            }
            deletionTimes[0][i] = testAVLTreeDeletion(avlTree, datasets[i]);
        }
        
        // This does the splay deletion 
        for (int i = 0; i < DATA_SIZES.length; i++) {
            SplayTree<Integer> splayTree = new SplayTree<>();
            for (Integer value : datasets[i]) {
                splayTree.insert(value);
            }
            deletionTimes[1][i] = testSplayTreeDeletion(splayTree, datasets[i]);
        }
        
        // this does the hash chainging deleteion
        for (int i = 0; i < DATA_SIZES.length; i++) {
            HashTables<Integer> hashTable = new HashTables<>(HASH_TABLE_INITIAL_CAPACITY, CHAINING);
            for (Integer value : datasets[i]) {
                hashTable.insert(value);
            }
            deletionTimes[2][i] = testHashTableDeletion(hashTable, datasets[i]);
        }
        
        // This does the probiing deletion 
        for (int i = 0; i < DATA_SIZES.length; i++) {
            HashTables<Integer> hashTable = new HashTables<>(HASH_TABLE_INITIAL_CAPACITY, QUADRATIC_PROBING);
            for (Integer value : datasets[i]) {
                hashTable.insert(value);
            }
            deletionTimes[3][i] = testHashTableDeletion(hashTable, datasets[i]);
        }
        
        // Print deletion results
        System.out.printf("AVL Tree                      \t%s ms\t\t\t%s ms\t\t\t%s ms\t\t%n", 
                df.format(deletionTimes[0][0]), df.format(deletionTimes[0][1]), df.format(deletionTimes[0][2]));
        System.out.printf("Splay Tree                    \t%s ms\t\t\t%s ms\t\t\t%s ms\t\t%n", 
                df.format(deletionTimes[1][0]), df.format(deletionTimes[1][1]), df.format(deletionTimes[1][2]));
        System.out.printf("Hash Table (Chaining)         \t%s ms\t\t\t%s ms\t\t\t%s ms\t\t%n", 
                df.format(deletionTimes[2][0]), df.format(deletionTimes[2][1]), df.format(deletionTimes[2][2]));
        System.out.printf("Hash Table (Quadratic Probing)\t%s ms\t\t\t%s ms\t\t\t%s ms\t\t%n", 
                df.format(deletionTimes[3][0]), df.format(deletionTimes[3][1]), df.format(deletionTimes[3][2]));
        
        System.out.println("\n");
        
        // checks the memory ussage 
        System.out.println("Memory Usage During Insertion (in KB)");
        System.out.println("Data Structure\t\t\t1,000 Elements\t\t10,000 Elements\t\t100,000 Elements");
        System.out.println("----------------------------------------------------------------------------------------");
        
        double[][] insertionMemory = new double[4][3];
        
        // this is the  memory usage for AVL Tree insertion
        for (int i = 0; i < DATA_SIZES.length; i++) {
            insertionMemory[0][i] = testAVLTreeInsertionMemory(DATA_SIZES[i]);
        }
        
        // and this is the  memory usage for Splay Tree insertion
        for (int i = 0; i < DATA_SIZES.length; i++) {
            insertionMemory[1][i] = testSplayTreeInsertionMemory(DATA_SIZES[i]);
        }
        
        // and this is the  memory usage for Hash Table with Chaining insertion
        for (int i = 0; i < DATA_SIZES.length; i++) {
            insertionMemory[2][i] = testHashTableInsertionMemory(DATA_SIZES[i], CHAINING);
        }
        
        // and this is the  memory usage for Hash Table with Quadratic Probing insertion
        for (int i = 0; i < DATA_SIZES.length; i++) {
            insertionMemory[3][i] = testHashTableInsertionMemory(DATA_SIZES[i], QUADRATIC_PROBING);
        }
        
        // Print insertion memory usage results
        System.out.printf("AVL Tree                      \t%.2f KB\t\t\t%.2f KB\t\t\t%.2f KB\t\t%n", 
                insertionMemory[0][0], insertionMemory[0][1], insertionMemory[0][2]);
        System.out.printf("Splay Tree                    \t%.2f KB\t\t\t%.2f KB\t\t\t%.2f KB\t\t%n", 
                insertionMemory[1][0], insertionMemory[1][1], insertionMemory[1][2]);
        System.out.printf("Hash Table (Chaining)         \t%.2f KB\t\t\t%.2f KB\t\t\t%.2f KB\t\t%n", 
                insertionMemory[2][0], insertionMemory[2][1], insertionMemory[2][2]);
        System.out.printf("Hash Table (Quadratic Probing)\t%.2f KB\t\t\t%.2f KB\t\t\t%.2f KB\t\t%n", 
                insertionMemory[3][0], insertionMemory[3][1], insertionMemory[3][2]);
        
        System.out.println("\n");
        
        // this is for the memory usage for search operations
        System.out.println("Memory Usage During Search (in KB)");
        System.out.println("Data Structure\t\t\t1,000 Elements\t\t10,000 Elements\t\t100,000 Elements");
        System.out.println("----------------------------------------------------------------------------------------");
        
        double[][] searchMemory = new double[4][3];
        
        // this is for the memory usage for AVL Tree search
        for (int i = 0; i < DATA_SIZES.length; i++) {
            AVLTree<Integer> avlTree = new AVLTree<>();
            for (Integer value : datasets[i]) {
                avlTree.insert(value);
            }
            searchMemory[0][i] = testAVLTreeSearchMemory(avlTree, datasets[i]);
        }
        
        // this is for the  memory usage for Splay Tree search
        for (int i = 0; i < DATA_SIZES.length; i++) {
            SplayTree<Integer> splayTree = new SplayTree<>();
            for (Integer value : datasets[i]) {
                splayTree.insert(value);
            }
            searchMemory[1][i] = testSplayTreeSearchMemory(splayTree, datasets[i]);
        }
        
        // this is for the memory usage for Hash Table with Chaining search
        for (int i = 0; i < DATA_SIZES.length; i++) {
            HashTables<Integer> hashTable = new HashTables<>(HASH_TABLE_INITIAL_CAPACITY, CHAINING);
            for (Integer value : datasets[i]) {
                hashTable.insert(value);
            }
            searchMemory[2][i] = testHashTableSearchMemory(hashTable, datasets[i]);
        }
        
        // this is for the memory usage for Hash Table with Quadratic Probing search
        for (int i = 0; i < DATA_SIZES.length; i++) {
            HashTables<Integer> hashTable = new HashTables<>(HASH_TABLE_INITIAL_CAPACITY, QUADRATIC_PROBING);
            for (Integer value : datasets[i]) {
                hashTable.insert(value);
            }
            searchMemory[3][i] = testHashTableSearchMemory(hashTable, datasets[i]);
        }
        
        // Print search memory usage results
        System.out.printf("AVL Tree                      \t%.2f KB\t\t\t%.2f KB\t\t\t%.2f KB\t\t%n", 
                searchMemory[0][0], searchMemory[0][1], searchMemory[0][2]);
        System.out.printf("Splay Tree                    \t%.2f KB\t\t\t%.2f KB\t\t\t%.2f KB\t\t%n", 
                searchMemory[1][0], searchMemory[1][1], searchMemory[1][2]);
        System.out.printf("Hash Table (Chaining)         \t%.2f KB\t\t\t%.2f KB\t\t\t%.2f KB\t\t%n", 
                searchMemory[2][0], searchMemory[2][1], searchMemory[2][2]);
        System.out.printf("Hash Table (Quadratic Probing)\t%.2f KB\t\t\t%.2f KB\t\t\t%.2f KB\t\t%n", 
                searchMemory[3][0], searchMemory[3][1], searchMemory[3][2]);
        
        System.out.println("\n");
        
        // this does the memory usage for deletion operations
        System.out.println("Memory Usage During Deletion (in KB)");
        System.out.println("Data Structure\t\t\t1,000 Elements\t\t10,000 Elements\t\t100,000 Elements");
        System.out.println("----------------------------------------------------------------------------------------");
        
        double[][] deletionMemory = new double[4][3];
        
        // this is for the memory usage for AVL Tree deletion
        for (int i = 0; i < DATA_SIZES.length; i++) {
            AVLTree<Integer> avlTree = new AVLTree<>();
            for (Integer value : datasets[i]) {
                avlTree.insert(value);
            }
            deletionMemory[0][i] = testAVLTreeDeletionMemory(avlTree, datasets[i]);
        }
        
        // this is for the memory usage for Splay Tree deletion
        for (int i = 0; i < DATA_SIZES.length; i++) {
            SplayTree<Integer> splayTree = new SplayTree<>();
            for (Integer value : datasets[i]) {
                splayTree.insert(value);
            }
            deletionMemory[1][i] = testSplayTreeDeletionMemory(splayTree, datasets[i]);
        }
        
        // this is the  memory usage for Hash Table with Chaining deletion
        for (int i = 0; i < DATA_SIZES.length; i++) {
            HashTables<Integer> hashTable = new HashTables<>(HASH_TABLE_INITIAL_CAPACITY, CHAINING);
            for (Integer value : datasets[i]) {
                hashTable.insert(value);
            }
            deletionMemory[2][i] = testHashTableDeletionMemory(hashTable, datasets[i]);
        }
        
        // this is the memory usage for Hash Table with Quadratic Probing deletion
        for (int i = 0; i < DATA_SIZES.length; i++) {
            HashTables<Integer> hashTable = new HashTables<>(HASH_TABLE_INITIAL_CAPACITY, QUADRATIC_PROBING);
            for (Integer value : datasets[i]) {
                hashTable.insert(value);
            }
            deletionMemory[3][i] = testHashTableDeletionMemory(hashTable, datasets[i]);
        }
        
        // Print deletion memory usage results
        System.out.printf("AVL Tree                      \t%.2f KB\t\t\t%.2f KB\t\t\t%.2f KB\t\t%n", 
                deletionMemory[0][0], deletionMemory[0][1], deletionMemory[0][2]);
        System.out.printf("Splay Tree                    \t%.2f KB\t\t\t%.2f KB\t\t\t%.2f KB\t\t%n", 
                deletionMemory[1][0], deletionMemory[1][1], deletionMemory[1][2]);
        System.out.printf("Hash Table (Chaining)         \t%.2f KB\t\t\t%.2f KB\t\t\t%.2f KB\t\t%n", 
                deletionMemory[2][0], deletionMemory[2][1], deletionMemory[2][2]);
        System.out.printf("Hash Table (Quadratic Probing)\t%.2f KB\t\t\t%.2f KB\t\t\t%.2f KB\t\t%n", 
                deletionMemory[3][0], deletionMemory[3][1], deletionMemory[3][2]);
    }
    
    // this is for the memory   methods for insertion operations
    private static double testAVLTreeInsertionMemory(int size) {
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // this asks garbage collection
        
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        
        AVLTree<Integer> avlTree = new AVLTree<>();
        Integer[] dataset = generateRandomDataset(size);
        for (Integer value : dataset) {
            avlTree.insert(value);
        }
        
        System.gc(); // this asks garbage collection
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        
        return Math.max(0, afterMemory - beforeMemory) / 1024.0; // this converts  to KB
    }
    
    private static double testSplayTreeInsertionMemory(int size) {
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // this again asks for garbage collection
        
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        
        SplayTree<Integer> splayTree = new SplayTree<>();
        Integer[] dataset = generateRandomDataset(size);
        for (Integer value : dataset) {
            splayTree.insert(value);
        }
        
        System.gc(); // you get at this point, this also ask for the  garbage collection
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        
        return Math.max(0, afterMemory - beforeMemory) / 1024.0; // this converts  to KB
    }
    
    private static double testHashTableInsertionMemory(int size, int collisionMethod) {
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // this creates a  garbage collection
        
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        
        HashTables<Integer> hashTable = new HashTables<>(HASH_TABLE_INITIAL_CAPACITY, collisionMethod);
        Integer[] dataset = generateRandomDataset(size);
        for (Integer value : dataset) {
            hashTable.insert(value);
        }
        
        System.gc(); // this does the same: garbage collection
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        
        return Math.max(0, afterMemory - beforeMemory) / 1024.0; // this makes it into  KB
    }
    
    // Memory measurement methods for search operations
    private static double testAVLTreeSearchMemory(AVLTree<Integer> avlTree, Integer[] dataset) {
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // this creates a garbage collection
        
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        
        for (Integer value : dataset) {
            avlTree.contains(value);
        }
        
        System.gc(); // this creates a garbage collection
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        
        return Math.max(0, afterMemory - beforeMemory) / 1024.0; // this is made into to KB
    }
    
    private static double testSplayTreeSearchMemory(SplayTree<Integer> splayTree, Integer[] dataset) {
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // this creates garbage collection
        
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        
        for (Integer value : dataset) {
            splayTree.contains(value);
        }
        
        System.gc(); // again this creates a  garbage collection
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        
        return Math.max(0, afterMemory - beforeMemory) / 1024.0; // this makes it into to KB
    }
    
    private static double testHashTableSearchMemory(HashTables<Integer> hashTable, Integer[] dataset) {
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // creates a  garbage collection
        
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        
        for (Integer value : dataset) {
            hashTable.contains(value);
        }
        
        System.gc(); // again asks garbage collection
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        
        return Math.max(0, afterMemory - beforeMemory) / 1024.0; // changes it into  to KB
    }
    
    // these are the   methods for deletion operations
    private static double testAVLTreeDeletionMemory(AVLTree<Integer> avlTree, Integer[] dataset) {
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // this creates garbage collection
        
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        
        for (Integer value : dataset) {
            avlTree.delete(value);
        }
        
        System.gc(); // again makes garabage collection
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        
        return Math.max(0, afterMemory - beforeMemory) / 1024.0; // this converts  to KB
    }
    
    private static double testSplayTreeDeletionMemory(SplayTree<Integer> splayTree, Integer[] dataset) {
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // asks garbage collection
        
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        
        for (Integer value : dataset) {
            splayTree.delete(value);
        }
        
        System.gc(); // asks garbage collection
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        
        return Math.max(0, afterMemory - beforeMemory) / 1024.0; // this converts to KB
    }
    
    private static double testHashTableDeletionMemory(HashTables<Integer> hashTable, Integer[] dataset) {
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // asks garbage collection
        
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        
        for (Integer value : dataset) {
            hashTable.delete(value);
        }
        
        System.gc(); // asks garbage collection
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        
        return Math.max(0, afterMemory - beforeMemory) / 1024.0; // this converts to KB
    }
    // this helps in testing teh different methods 

    private static Integer[] generateRandomDataset(int size) {
        Integer[] dataset = new Integer[size];
        for (int i = 0; i < size; i++) {
            dataset[i] = RANDOM.nextInt(size * 10); // this is the range from 0 to the size to the 10th power 
        }
        return dataset;
    }
    
    // tests the avl methods
    
    private static double testAVLTreeInsertion(int size) {
        AVLTree<Integer> avlTree = new AVLTree<>();
        Integer[] dataset = generateRandomDataset(size);
        
        long startTime = System.nanoTime();
        for (Integer value : dataset) {
            avlTree.insert(value);
        }
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / 1_000_000.0; // this converts to milliseconds
    }
    
    private static double testAVLTreeSearch(AVLTree<Integer> avlTree, Integer[] dataset) {
        long startTime = System.nanoTime();
        for (Integer value : dataset) {
            avlTree.contains(value);
        }
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / 1_000_000.0; // this converts to milliseconds
    }
    
    private static double testAVLTreeDeletion(AVLTree<Integer> avlTree, Integer[] dataset) {
        long startTime = System.nanoTime();
        for (Integer value : dataset) {
            avlTree.delete(value);
        }
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / 1_000_000.0; // this converts  to milliseconds
    }
    
    private static double testAVLTreeMemory(Integer[] dataset) {
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // asks garbage collection
        
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        
        AVLTree<Integer> avlTree = new AVLTree<>();
        for (Integer value : dataset) {
            avlTree.insert(value);
        }
        
        System.gc(); // asks garbage collection
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        
        return Math.max(0, afterMemory - beforeMemory) / 1024.0; // this converts  to KB
    }
    
    // this is for  Splay Tree testing 
    
    private static double testSplayTreeInsertion(int size) {
        SplayTree<Integer> splayTree = new SplayTree<>();
        Integer[] dataset = generateRandomDataset(size);
        
        long startTime = System.nanoTime();
        for (Integer value : dataset) {
            splayTree.insert(value);
        }
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / 1_000_000.0; // this converts to milliseconds
    }
    
    private static double testSplayTreeSearch(SplayTree<Integer> splayTree, Integer[] dataset) {
        long startTime = System.nanoTime();
        for (Integer value : dataset) {
            splayTree.contains(value);
        }
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / 1_000_000.0; // this converts  to milliseconds
    }
    
    private static double testSplayTreeDeletion(SplayTree<Integer> splayTree, Integer[] dataset) {
        long startTime = System.nanoTime();
        for (Integer value : dataset) {
            splayTree.delete(value);
        }
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / 1_000_000.0; // this Convert to milliseconds
    }
    
    private static double testSplayTreeMemory(Integer[] dataset) {
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // asks garbage collection
        
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        
        SplayTree<Integer> splayTree = new SplayTree<>();
        for (Integer value : dataset) {
            splayTree.insert(value);
        }
        
        System.gc(); // asks garbage collection
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        
        return Math.max(0, afterMemory - beforeMemory) / 1024.0; // this is made to to KB
    }
    
    // tests the hash tables
    
    private static double testHashTableInsertion(int size, int collisionMethod) {
        HashTables<Integer> hashTable = new HashTables<>(HASH_TABLE_INITIAL_CAPACITY, collisionMethod);
        Integer[] dataset = generateRandomDataset(size);
        
        long startTime = System.nanoTime();
        for (Integer value : dataset) {
            hashTable.insert(value);
        }
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / 1_000_000.0; // this one is converted  to milliseconds
    }
    
    private static double testHashTableSearch(HashTables<Integer> hashTable, Integer[] dataset) {
        long startTime = System.nanoTime();
        for (Integer value : dataset) {
            hashTable.contains(value);
        }
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / 1_000_000.0; // this is one is converted to milliseconds
    }
    
    private static double testHashTableDeletion(HashTables<Integer> hashTable, Integer[] dataset) {
        long startTime = System.nanoTime();
        for (Integer value : dataset) {
            hashTable.delete(value);
        }
        long endTime = System.nanoTime();
        
        return (endTime - startTime) / 1_000_000.0; // this one is for converting to miliseconds
    }
    
    private static double testHashTableMemory(Integer[] dataset, int collisionMethod) {
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // Request garbage collection
        
        long beforeMemory = runtime.totalMemory() - runtime.freeMemory();
        
        HashTables<Integer> hashTable = new HashTables<>(HASH_TABLE_INITIAL_CAPACITY, collisionMethod);
        for (Integer value : dataset) {
            hashTable.insert(value);
        }
        
        System.gc(); // Request garbage collection
        long afterMemory = runtime.totalMemory() - runtime.freeMemory();
        
        return Math.max(0, afterMemory - beforeMemory) / 1024.0; // I converted here to kb
    }
}
