# Assignment-02: Data Structures and Alogrithms
<h3>Amulya Prasad Rayabhagi</h3>
<h3>axr220242</h3>
<h3> CS3345.005 </h3>

# Data Structures Performance Analysis Report

## Introduction

This document provides a performance analysis of four data structure implementations:
- AVL Tree
- Splay Tree 
- Hash Table with Chaining
- Hash Table with Quadratic Probing


## Implementation Overview

### AVL Tree
The AVL tree implementation has self-balancing capabilities through height-balanced rotations, maintaining logarithmic height even in worst-case scenarios. The implementation handles four rotation cases: Left-Left, Right-Right, Left-Right, and Right-Left.

### Splay Tree
The splay tree implementation uses splaying operations to bring frequently accessed elements closer to the root. This implementation includes Zig, Zig-Zig, and Zig-Zag rotations for efficient access patterns.

### Hash Tables
Two collision resolution strategies were implemented:
1. **Chaining**: Uses linked lists to handle collisions
2. **Quadratic Probing**: Uses quadratic function to find next available slot

## Theoretical Time Complexity

| Operation | AVL Tree | Splay Tree | Hash Table (Chaining) | Hash Table (Quadratic) |
|-----------|----------|------------|----------------------|------------------------|
| Insertion | O(log n) | O(log n) amortized | O(1) average, O(n) worst | O(1) average, O(n) worst |
| Deletion  | O(log n) | O(log n) amortized | O(1) average, O(n) worst | O(1) average, O(n) worst |
| Search    | O(log n) | O(log n) amortized | O(1) average, O(n) worst | O(1) average, O(n) worst |

## Experimental Setup

Performance testing was conducted using the following parameters:
- Data sizes: 1,000, 10,000, and 100,000 elements
- Operations: Insert, Delete, Search
- Access pattern: Random
- Each test was repeated 10 times and the average execution time was recorded

## Performance Results

### Insertion Performance (Average time in milliseconds)

| Data Size | AVL Tree | Splay Tree | Hash Table (Chaining) | Hash Table (Quadratic) |
|-----------|----------|------------|----------------------|------------------------|
| 1,000     |5.478     | 2.271      | 1.272                | 1.025                  |
| 10,000    |4.994     | 4.117      | 20.972               | 3.545                  |
| 100,000   | 59.139   | 79.407     |4319.512              | 26.830                 |

### Deletion Performance (Average time in milliseconds)

| Data Size | AVL Tree | Splay Tree | Hash Table (Chaining) | Hash Table (Quadratic) |
|-----------|----------|------------|----------------------|------------------------|
| 1,000     |1.995     | 1.151      | 0.847                | 0.296                  |
| 10,000    | 4.709    | 9.425      | 15.200               | 0.733                  |
| 100,000   | 70.128   | 46.287      | 1837.973                | 9.491                  |

### Search Performance (Average time in milliseconds)

| Data Size | AVL Tree | Splay Tree | Hash Table (Chaining) | Hash Table (Quadratic) |
|-----------|----------|------------|----------------------|------------------------|
| 1,000     | 1.357    | 0.266      | 0.802                | 0.208                  |
| 10,000    | 2.709    | 2.649      | 19.810               | 0.627                  |
| 100,000   | 37.834   | 56.651     | 4708.166             | 12.516                 |

## Memory Usage
### Insertion Memory (Average in kB)

| Data Size | AVL Tree | Splay Tree | Hash Table (Chaining) | Hash Table (Quadratic) |
|-----------|----------|------------|----------------------|------------------------|
| 1,000     | 49.02   | 41.45      | 41.68                | 0.00                    |
| 10,000    | 492.33  | 417.88     | 418.60               | 0.00                    |
| 100,000   | 0.00    | 0.00       | 0.00                 | 0.00                    |

### Deletion Memory (Average in kB)

| Data Size | AVL Tree | Splay Tree | Hash Table (Chaining) | Hash Table (Quadratic) |
|-----------|----------|------------|----------------------|------------------------|
| 1,000     | 0        | 0          | 0                    | 0                      |
| 10,000    | 0        | 0          | 0                    | 0                      |
| 100,000   | 0        | 0          | 0                    | 0                      |

### Search Memory (Average in kB)
| Data Size | AVL Tree | Splay Tree | Hash Table (Chaining) | Hash Table (Quadratic) |
|-----------|----------|------------|----------------------|------------------------|
| 1,000     | 0        | 0          | 0                    | 0                      |
| 10,000    | 0        | 0          | 0                    | 0                      |
| 100,000   | 0        | 0          | 0                    | 0                      |

## Analysis and Discussion

### AVL Tree
- **Strengths**: Consistent O(log n) performance for all operations regardless of input pattern
- **Weaknesses**: Higher overhead for maintaining balance during insertions and deletions
- **Best use case**: Applications requiring guaranteed worst-case performance and ordered data traversal

### Splay Tree
- **Strengths**: Excellent performance for localized access patterns due to self-adjustment
- **Weaknesses**: Inconsistent performance with random access patterns
- **Best use case**: Applications with high temporal locality of reference (recently accessed items are likely to be accessed again)

### Hash Table with Chaining
- **Strengths**: Excellent average-case performance, simple implementation
- **Weaknesses**: Performance degrades with high collision rates
- **Best use case**: General-purpose applications where ordering is not required

### Hash Table with Quadratic Probing
- **Strengths**: Better memory locality than chaining
- **Weaknesses**: Susceptible to clustering and requires careful load factor management
- **Best use case**: Memory-constrained environments where space efficiency is important

## Performance Visualization

```
Insertion Time (ms) vs Data Size
^
|                                                    * AVL Tree
|                                                  *
|                                                *
|                                              *  
|                                            *    
|                                          *      
|                                        *        
|                                      *          * Splay Tree
|                                   *           *
|                                *            *
|                             *             *
|                          *              *
|                       *               *
|                    *                *
|                 *                 *
|              *                  *
|           *                   *             * HT-Chaining  * HT-Quadratic
|        *                    *            *               *
|     *                     *          *               *
|  *                     *         *               *
+---------------------------------------------------------->
   1K                10K               100K              1M
```



## Conclusion

