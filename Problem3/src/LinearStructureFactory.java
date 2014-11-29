/**
 * Factories for building linear structures.
 */
public interface LinearStructureFactory<T>
{
  // +---------+---------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Build a linear structure.
   */
  public LinearStructure<T> build();
} // LinearStructureFactory<T>
