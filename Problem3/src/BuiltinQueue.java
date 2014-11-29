import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Queues implemented with the standard Java Queues.
 * (I'd call this an adapter class, but it's implemented by delegating
 * all of the methods.)
 *
 * @author Samuel A. Rebelsky
 */
public class BuiltinQueue<T>
    implements LinearStructure<T>
{
  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The underlying priority queue.
   */
  java.util.ArrayDeque<T> base;

  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new queue.
   */
  public BuiltinQueue()
  {
    this.base = new java.util.ArrayDeque<T>();
  } // BuiltinQueue(int capacity)

  // +----------------+--------------------------------------------------
  // |  Queue Methods |
  // +----------------+

  @Override
  public boolean isEmpty()
  {
    return base.isEmpty();
  } // isEmpty()

  @Override
  public boolean isFull()
  {
    // It looks like the standard ones don't fill
    return false;
  } // isFull()

  @Override
  public void put(T val)
    throws Exception
  {
    base.addLast(val);
  } // put(T)

  @Override
  public T get()
    throws Exception
  {
    return base.removeFirst();
  } // get(T)

  @Override
  public T peek()
    throws Exception
  {
    return (T) base.peekFirst();
  } // peek()

  @Override
  public Iterator<T> iterator()
  {
    return base.iterator();
  } // iterator()
} // class BuiltinQueue<T>
