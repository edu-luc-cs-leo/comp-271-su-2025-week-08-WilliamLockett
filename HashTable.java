/**
 * A simple hasht table is an array of linked lists. In its simplest form, a
 * linked list is represented by its first node. Typically we label this node as
 * "head". Here, however, we'll know it's the first node of the list because it
 * will be placed in an array element. For example, if we have 4 linked lists,
 * we know that the "head" of the third one can be found in position [2] of the
 * underlying array.
 */
public class HashTable<E extends Comparable<E>> {

    /**
     * Underlying array of nodes. Each non empty element of this array is the first
     * node of a linked list.
     */
    private Node<E>[] underlying;

    /** Counts how many places in the underlying array are occupied */
    private int usage;

    /** Counts how many nodes are stored in this hashtable */
    private int totalNodes;

    /** Tracks underlying array's load factor */
    private double loadFactor;

    /**
     * Default size for the underlying array.
     */
    private static final int DEFAULT_SIZE = 4;

    /** Default load factor threshold for resizing */
    private static double LOAD_FACTOR_THRESHOLD = 0.75;

    /**
     * Basic constructor with user-specified size. If size is absurd, the
     * constructor will revert to the default size.
     */
    public HashTable(int size) {
        if (size <= 0)
            size = DEFAULT_SIZE;
        this.underlying = new Node[size];
        this.usage = 0;
        this.totalNodes = 0;
        this.loadFactor = 0.0;
    } // basic constructor

    /** Default constructor, passes defauilt size to basic constructor */
    public HashTable() {
        this(DEFAULT_SIZE);
    } // default constructor

    /**
     * Adds a node, with the specified content, to a linked list in the underlying
     * array.
     * 
     * @param content E The content of a new node, to be placed in the array.
     */
    public void add(E content) {
        // Create the new node to add to the hashtable
        Node<E> newNode = new Node<E>(content);
        // Use the hashcode for the new node's contents to find where to place it in the
        // underlying array. Use abs in case hashcode < 0.
        int position = Math.abs(content.hashCode()) % this.underlying.length;
        // Check if selected position is already in use
        if (this.underlying[position] == null) {
            // Selected position not in use. Place the new node here and update the usage of
            // the underlying array.
            this.underlying[position] = newNode;
            this.usage += 1;
        } else {
            // Selected position in use. We will append its contents to the new node first,
            // then place the new node in the selected position. Effectively the new node
            // becomes the first node of the existing linked list in this position.
            newNode.setNext(this.underlying[position]);
            this.underlying[position] = newNode;
        }
        // Update the number of nodes
        this.totalNodes += 1;
    } // method add

    /**
     * Searches the underlying array of linked lists for the target value. If the
     * target value is stored in the underlying array, the position of its
     * corresponding linked list can be obtained immediately through the target's
     * hashcode. The linked list must then be traversed to determine if a node with
     * similar content and the target value is present or not.
     * 
     * @param target E value to searc for
     * @return true if target value is present in one of the linked lists of the
     *         underlying array; false otherwise.
     */
    public boolean contains(E target) {
        // take the absoloute value of the index with hash code
        int position = Math.abs(target.hashCode()) % this.underlying.length;
        Node<E> current = this.underlying[position];
        while (current != null) { // while current has something in it
            /*
             * if the current = the target
             * then return true
             */
            if (current.getContent().equals(target)){
                return true;
            }
            // move to the next node
            current = current.getNext();
        }
        //no target found then return false
        return false;
    } // method contains

    /** Constants for toString */
    private static final String LINKED_LIST_HEADER = "\n[ %2d ]: ";
    private static final String EMPTY_LIST_MESSAGE = "null";
    private static final String ARRAY_INFORMATION = "Underlying array usage / length: %d/%d";
    private static final String NODES_INFORMATION = "\nTotal number of nodes: %d";
    private static final String NODE_CONTENT = "%s --> ";

    /** String representationf for the object */
    public String toString() {
        // Initialize the StringBuilder object with basic info
        StringBuilder sb = new StringBuilder(
                String.format(ARRAY_INFORMATION,
                        this.usage, this.underlying.length));
        sb.append(String.format(NODES_INFORMATION, this.totalNodes));
        // Iterate the array
        for (int i = 0; i < underlying.length; i++) {
            sb.append(String.format(LINKED_LIST_HEADER, i));
            Node head = this.underlying[i];
            if (head == null) {
                // message that this position is empty
                sb.append(EMPTY_LIST_MESSAGE);
            } else {
                // traverse the linked list, displaying its elements
                Node cursor = head;
                while (cursor != null) {
                    // update sb
                    sb.append(String.format(NODE_CONTENT, cursor));
                    // move to the next node of the ll
                    cursor = cursor.getNext();
                } // done traversing the linked list
            } // done checking the current position of the underlying array
        } // done iterating the underlying array
        return sb.toString();
    } // method toString

} // class HashTable
