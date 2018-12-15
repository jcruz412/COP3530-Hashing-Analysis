
// Midsquare Chaining Hash Table
import java.util.ArrayList;
import java.util.Random;
import java.util.*;

// A node of chains
class HashNode<K, V>
{
    K key;
    V value;

    // Reference to next node
    HashNode<K, V> next;

    // Constructor
    public HashNode(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
}

// Class to represent entire hash table
class Map<K, V>
{
    // bucketArray is used to store array of chains
    private ArrayList<HashNode<K, V>> bucketArray;

    // Current capacity of array list
    private int numBuckets;

    // Current size of array list
    private int size;

    // Number of collisions
    private int collision;


    // Constructor (Initializes capacity, size and
    // empty chains.
    public Map(int size)
    {
        bucketArray = new ArrayList<>();
        numBuckets = size;
        size = 0;
        collision = 0;

        // Create empty chains
        for (int i = 0; i < numBuckets; i++)
            bucketArray.add(null);
    }

    public int size() { return size; }
    public boolean isEmpty() { return size() == 0; }

    // This implements hash function to find index
    // for a key
    private int getBucketIndex(K key)
    {


        int cond = 0;


        int hashCode = key.hashCode();

        if(numBuckets==40){
            cond = 16;

        }else if(numBuckets==80||numBuckets==120){ // Checks how many bits to get rid of according to hash table size
            cond = 32;
        }

        int hashSquared = hashCode*hashCode;
        int squareDivide = hashSquared/cond;
        //int test = squareDivide%cond;
        int index = squareDivide % numBuckets;

        // add midsquare hashing
        return index;
    }

    // Method to remove a given key


    // Adds a key value pair to hash
    public void add(K key, V value)
    {
        // Find head of chain for given key
        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);

        if(head!=null){
            collision++;
        }

        // Check if key is already present
        while (head != null)
        {

            if (head.key.equals(key))
            {
                head.value = value;
                return;
            }
            head = head.next;
        }

        // Insert key in chain
        size++;
        head = bucketArray.get(bucketIndex);
        HashNode<K, V> newNode = new HashNode<K, V>(key, value);
        newNode.next = head;
        bucketArray.set(bucketIndex, newNode);

        double loadFactor = (1.0*size)/numBuckets;

        System.out.println("Load Factor: " + loadFactor);
        System.out.println("Collision: " + collision);


    }

    // Driver method to test Map class
    public static void main(String[] args)
    {
        Scanner scn = new Scanner(System.in);

        Random rand = new Random();

        HashSet<Integer> used = new HashSet<>();

        System.out.println("Input table size: ");
        int tableSize = scn.nextInt();

        Map<Integer, Integer>map = new Map<>(tableSize);

        int bound = tableSize*3;

        for(int n=0; n<map.numBuckets;n++){

            int randVal = rand.nextInt(bound);
            while(used.contains(randVal)){
                randVal = rand.nextInt(bound);
            }
            used.add(randVal);
            map.add(randVal, 5);
        }
    }
}