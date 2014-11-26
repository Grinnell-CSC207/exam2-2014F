import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Random linear structures.
 *
 * @author CSC 207 2014F
 * @author Randy
 * @author Linnea
 */
public class RandomLinearStructure<T>
  implements LinearStructure<T>
{
  // +--------+----------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The array that holds the elements.
   */
  T[] values;

  /**
   * The number of values stored in the structure.
   */
  int size;

  /**
   * A random number generator (otherwise this wouldn't be random).
   */
  Random random;

  /**
   * The last location that we peeked (to ensure that peek and get
   * stay in synch.  Set to -1 if we have not peeked recently.
   */
  int peekedAt;


  // +--------------+----------------------------------------------------
  // | Constructors |
  // +--------------+

  @SuppressWarnings({ "unchecked" })
  public RandomLinearStructure(int capacity) 
    throws Exception
  {
    if (capacity <= 0)
      {
        throw new Exception("Capacity must be positive.");
      } // if (capacity <= 0)
    this.values = (T[]) new Object[capacity];
    this.size = 0;
    this.random = new Random();
    this.peekedAt = -1;
  } // RandomLinearStructure(int)

  // +-------------------------+-----------------------------------------
  // | LinearStructure Methods |
  // +-------------------------+
   
  public void put(T val)
    throws Exception
  {
    if (this.isFull())
      throw new Exception("Full");
    this.values[this.size++] = val;
  } // put(T val)

  public T get()
    throws Exception
  {
    if (this.isEmpty())
      throw new Exception("Empty");
    // Determine where to grab the value from
    int pos = (this.peekedAt >= 0) ? this.peekedAt : random.nextInt(this.size);
    // Grab that value 
    T result = this.values[pos];
    // Replace it with the last value
    this.values[pos] = this.values[--this.size];
    // It's good practice to clear out cells you are not using
    this.values[this.size] = null;
    // Any peeked value is invalid
    this.peekedAt = -1;
    // And we're done
    return result;
  } // get()

  public T peek()
    throws Exception
  {
    if (this.isEmpty())
      throw new Exception("Empty");
    // If we haven't peeked recently, pick where to peek
    if (this.peekedAt < 0)
      {
        this.peekedAt = random.nextInt(this.size);
      }
    // And we're done.
    return this.values[this.peekedAt];
  }// peek()

  public boolean isEmpty()
  {
    return this.size <= 0;
  } // isEmpty

  /**
   * Determine if the structure is full.
   */
  public boolean isFull()
  {
    return this.size >= this.values.length;
  } // isFull()

  /**
   * Get an iterator that returns all of the elements in some order.
   */
  public Iterator<T> iterator()
  {
    return new Iterator<T>() {
      int pos = 0;

      public T next()
      {
        if (pos >= size)
          throw new NoSuchElementException();
        return values[this.pos++];
      } // next()

      public boolean hasNext()
      {
        return (pos < size);
      } // hasNext()

      public void remove()
      {
        throw new UnsupportedOperationException();
      } // remove()
    }; // new Iterator<T>
  } // iterator()
} // class RandomLinearStructure
