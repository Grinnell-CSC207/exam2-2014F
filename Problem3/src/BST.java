import java.io.PrintWriter;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Dictionaries implemented as binary search trees.
 * 
 * @author Samuel A. Rebelsky
 * @author Rafe
 * @author Acta
 * @author Your Name Here
 */
public class BST<K, V>
    implements Dictionary<K, V>
{

  // +-------+-----------------------------------------------------------
  // | Notes |
  // +-------+

  /*
     This code is closely based on code by Samuel A. Rebelsky from
     exam 2 of the fall 2013 section of Grinnell's CSC 207.  That
     code, in turn, is likely based on code from the Tao of Java
     laboratory on binary search trees.  The latest version of 
     that code is available at 
       <https://github.com/Grinnell-CSC207/bst>.

     This code was extended first for the spring 2014 section of
     CSC 207 and then again again for the fall 2014 section.

     We implement dictionaries using binary search trees. Each node
     in the search tree contains a key, a value, and links to
     (potentially null) left and right subtrees. The left subtree
     contains entries with keys smaller than the key of the node.
     The right subtree contains entries with keys larger than the
     key of the node.

     We support generalized traversal with the <code>iterator(Traversal)</code>
     and <code>values(Traversal)</code> methods.  Iterators keep track of
     the linear structure they use for iteration as well as the order in
     which to add parts of the node to the linear structure.
   */

  // +---------+---------------------------------------------------------
  // | Globals |
  // +---------+

  /**
   * A factory for queues.
   */
  static final LinearStructureFactory<Object> QUEUE_FACTORY =
      () -> new BuiltinQueue<Object>();

  /**
   * A factory for stacks.
   */
  static final LinearStructureFactory<Object> STACK_FACTORY =
      () -> new BuiltinStack<Object>();

  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The root of the tree.
   */
  BSTNode<K, V> root;

  /**
   * The comparator used to give the ordering.
   */
  Comparator<K> order;

  /**
   * The current traversal policy.
   */
  Traversal policy;

  /** 
   * A map from traversal strategies to linear structure factories.
   * This should be uniform for all objects, but for type reasons,
   * we have a separate one for each object.
   */
  EnumMap<Traversal, LinearStructureFactory<Object>> traversalStructures;

  /** 
   * A map from traversal strategies to iterator helpers
   * This should be uniform for all objects, but for type reasons,
   * we have a separate one for each object.
   */
  EnumMap<Traversal, IteratorHelper<K, V>> traversalHelpers;

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new BST
   */
  public BST(Comparator<K> order)
  {
    // Fill in the primary fields.
    this.root = null;
    this.order = order;
    this.policy = Traversal.DEPTH_FIRST_PREORDER_LEFT_TO_RIGHT;

    // Set up the traversal stuff.
    setup();
  } // BST

  // +-----------+-------------------------------------------------------
  // | Observers |
  // +-----------+

  public V get(K key)
    throws Exception
  {
    BSTNode<K, V> node = this.find(this.root, key);
    if (node == null)
      {
        throw new NoSuchElementException();
      } // if not found
    return node.value;
  } // get(K)

  public boolean containsKey(K key)
  {
    BSTNode<K, V> node = this.find(this.root, key);
    return node != null;
  } // containsKey(K)

  /**
   * Dump a simple textual representation of the tree.
   */
  public void dump(PrintWriter pen)
  {
    dump(pen, this.root, "");
  } // dump(PrintWriter)

  // +----------+--------------------------------------------------------
  // | Mutators |
  // +----------+

  /**
   * Set the traversal policy to use.
   */
  public void setTraversal(Traversal policy)
  {
    this.policy = policy;
  } // setTraversal

  public void set(K key, V value)
  {
    this.root = insert(this.root, key, value);
  } // set

  public void remove(K key)
  {
    this.root = remove(this.root, key);
  } // remove(K)

  public void clear()
  {
    // I love garbage collection. In C, we'd have to individually
    // free all of the nodes.
    this.root = null;
  } // clear

  // +-----------+-------------------------------------------------------
  // | Iterators |
  // +-----------+

  public Iterable<K> keys()
  {
    return new Iterable<K>()
      {
        public Iterator<K> iterator()
        {
          return BST.this.keysIterator();
        } // iterator()
      }; // new Iterable<K>
  } // keys

  public Iterator<K> keysIterator()
  {
    // STUB
    return new Iterator<K>()
      {
        public K next()
          throws NoSuchElementException
        {
          throw new NoSuchElementException();
        } // next()

        public boolean hasNext()
        {
          return false;
        } // hasNext

        public void remove()
          throws UnsupportedOperationException,
            IllegalStateException
        {
          throw new UnsupportedOperationException();
        } // remove
      }; // new Iterator<K>
  } // keysIterator()

  /**
   * Create an iterable that creates iterators that follow a particular
   * traversal policy.
   */
  public Iterable<V> values(final Traversal policy)
  {
    return new Iterable<V>()
      {
        public Iterator<V> iterator()
        {
          return BST.this.iterator(policy);
        }
      };
  } // values(Traversal)

  /**
   * Create a values iterator that follows the current traversal policy.
   */
  public Iterator<V> iterator()
  {
    return iterator(this.policy);
  } // iterator()

  /**
   * Create a values iterator that follows a specific traversal policy.
   */
  public Iterator<V> iterator(final Traversal policy)
  {
    // STUB
    return new Iterator<V>()
      {
        // +--------+----------------------------------------------------
        // | Fields |
        // +--------+

        /**
         * The linear structure that stores the remaining nodes and
         * values to process.
         */
        LinearStructure<Object> remaining;

        /**
         * The thing that extracts the parts of the node and adds
         * them to the linear structure.
         */
        IteratorHelper helper;

        // +---------+---------------------------------------------------
        // | Methods |
        // +---------+

        public V next()
          throws NoSuchElementException
        {
          throw new NoSuchElementException();
        } // next()

        public boolean hasNext()
        {
          return false;
        } // hasNext

        public void remove()
          throws UnsupportedOperationException,
            IllegalStateException
        {
          throw new UnsupportedOperationException();
        } // remove
      }; // new Iterator<V>
  } // iterator()

  // +-----------------+-------------------------------------------------
  // | Local Utilities |
  // +-----------------+

  /**
   * Print a simple representation of a BST using pen, indenting the BST the
   * specified amount.
   */
  void dump(PrintWriter pen, BSTNode<K, V> tree, String indent)
  {
    if (tree == null)
      {
        // Special case: For the empty tree, we just print a special
        // symbol
        pen.println(indent + "<>");
      }
    else
      {
        // Normal case: Print the key/value pair and the subtrees
        pen.println(indent + "[" + tree.key + ":" + tree.value + "]");
        dump(pen, tree.smaller, indent + "    ");
        dump(pen, tree.larger, indent + "    ");
      } // if it's a real node
  } // dump(PrintWriter, BSTNode<K,V>, String)

  /**
   * Find the node in a BST with the given key.
   * 
   * @return the node containing the given key, or null if no such node
   *         exists.
   */
  BSTNode<K, V> find(BSTNode<K, V> tree, K key)
  {
    // Special case: Empty tree. Give up
    if (tree == null)
      {
        return null;
      } // if (tree == null)
    else
      {
        int tmp = order.compare(key, tree.key);
        if (tmp == 0)
          {
            return tree;
          }
        else if (tmp < 0)
          {
            return this.find(tree.smaller, key);
          }
        else
          {
            return this.find(tree.larger, key);
          } // if the key is larger than the key at the node
      } // if the tree is nonempty
  } // find(BSTNode<K,V>, K, V)

  /**
   * Insert a key/value pair in the tree.
   * 
   * @return newtree, the updated tree
   */
  BSTNode<K, V> insert(BSTNode<K, V> tree, K key, V value)
  {
    // Special case: Empty tree. Build a new node.
    if (tree == null)
      {
        return new BSTNode<K, V>(key, value);
      } // if (tree == null)
    else
      {
        int tmp = order.compare(key, tree.key);
        if (tmp == 0)
          {
            tree.value = value;
            return tree;
          }
        else if (tmp < 0)
          {
            tree.smaller = insert(tree.smaller, key, value);
            return tree;
          }
        else
          {
            tree.larger = insert(tree.larger, key, value);
            return tree;
          } // if the key is larger than the key at the node
      } // if the tree is nonempty
  } // insert(BSTNode<K,V>, K, V)

  /**
   * Remove the element with the specified key, assuming that the
   * element appears in the tree.  Returns the modified tree.
   */
  BSTNode<K, V> remove(BSTNode<K, V> tree, K key)
  {
    // You can't remove from the empty tree.
    if (tree == null)
      return tree;
    // Determine the relationship of the key to the root of the subtree.
    int tmp = order.compare(key, tree.key);
    // If we've found the item to remove, ...
    if (tmp == 0)
      {
        // STUB.  
        return null;
      } // if we've found the item to remove
    else
      {
        // STUB.
        return tree;
      } // if (tmp != 0)
  } // remove(BSTNode<K,V>, K)

  // +---------+---------------------------------------------------------
  // | Helpers |
  // +---------+

  /**
   * Set up the globals.
   */
  private void setup()
  {
    traversalStructures =
        new EnumMap<Traversal, LinearStructureFactory<Object>>(Traversal.class);
    traversalStructures.put(Traversal.DEPTH_FIRST_PREORDER_LEFT_TO_RIGHT,
                            STACK_FACTORY);
    traversalHelpers =
        new EnumMap<Traversal, IteratorHelper<K, V>>(Traversal.class);
    traversalHelpers.put(Traversal.DEPTH_FIRST_PREORDER_LEFT_TO_RIGHT,
                         (remaining, node) ->
                           {
                             remaining.put(node.larger);
                             remaining.put(node.smaller);
                             remaining.put(node.value);
                           });
  } // setup()

} // BST<K,V>

/**
 * Nodes in a linked dictionary.
 */
class BSTNode<K, V>
{
  // +--------+--------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The key in the key/value pair.
   */
  K key;

  /**
   * The value in the key/value pair.
   */
  V value;

  /**
   * The left subtree, which should contain the smaller values.
   */
  BSTNode<K, V> smaller;

  /**
   * The right subtree, which should contain the larger values.
   */
  BSTNode<K, V> larger;

  // +--------------+--------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new node. (We set smaller and larger by using the fields.)
   */
  public BSTNode(K key, V value)
  {
    this.key = key;
    this.value = value;
    this.smaller = null;
    this.larger = null;
  } // BSTNode(K,V)
} // class BSTNode

/**
 * Things that help iterators.
 */
interface IteratorHelper<K, V>
{
  /**
   * Pull the pieces out of the node and put them in the structure.  
   *
   * @exception Exception if there is no room left in the structure.
   */
  public void unpack0(LinearStructure<Object> remaining, BSTNode<K, V> node)
    throws Exception;

  /**
   * Pull the pieces out of the node and put them in the structure.
   * If the structure fills, don't worry about it and don't signal
   * anything to the client.
   */
  public default void unpack(LinearStructure<Object> remaining,
                             BSTNode<K, V> node)
  {
    try
      {
        this.unpack0(remaining, node);
      } // try
    catch (Exception e)
      {
        // Do nothing
      } // catch
  } // unpack(LinearStructure<Object>, BSTNode<K,V>)
} // interface IteratorHelper
