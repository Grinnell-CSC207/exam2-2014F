import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Stacks implemented with the standard Java deques.
 *
 * @author Samuel A. Rebelsky
 */
public class BuiltinStack<T>
    implements LinearStructure<T>
{
  // +-------+-----------------------------------------------------------
  // | Notes |
  // +-------+

  /*
    My reading of java.util.Dequeu is that we push at the front (and
    pop from the front), rather than at the end.
   */

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
   * Create a new stack.
   */
  public BuiltinStack()
  {
    this.base = new java.util.ArrayDeque<T>();
  } // BuiltinStack(int capacity)

  // +----------------+--------------------------------------------------
  // |  Stack Methods |
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
    base.push(val);
  } // put(T)

  @Override
  public T get()
    throws Exception
  {
    return base.pop();
  } // get(T)

  @Override
  public T peek()
    throws Exception
  {
    return (T) base.peek();
  } // peek()

  @Override
  public Iterator<T> iterator()
  {
    return base.iterator();
  } // iterator()
} // class BuiltinStack<T>
