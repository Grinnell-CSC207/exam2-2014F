import java.math.BigInteger;

import java.util.Random;

/**
 * Utils.java
 *   Assorted utilities.
 */
public class Utils
{
  // +-----------+-------------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The number of repetitions we do for a standard primality test.
   */
  static final int STANDARD_PRIME_TESTS = 100;

  // +---------+---------------------------------------------------------
  // | Globals |
  // +---------+

  /**
   * A random-number generator, since we use them a lot.
   */
  static final Random random = new Random();

  // +----------------+--------------------------------------------------
  // | Static Methods |
  // +----------------+

  /**
   * Compute x^n mod m.
   */
  public static BigInteger expmod(BigInteger x, BigInteger n, BigInteger m)
  {
    // STUB
    return BigInteger.ONE;
  } // expmod(BigInteger)

  /**
   * Determine whether p is likely to be prime.
   */
  public static boolean probablyPrime(BigInteger p)
  {
    // Generate i
    BigInteger i = (new BigInteger(p.bitLength(), random)).remainder(p);
    // Compute i^(p-1) mod p.  
    // STUB
    BigInteger tmp = expmod(i, BigInteger.TEN, p);
    // Determine whether or not we have 1.
    return tmp.equals(BigInteger.ONE);
  } // probablyPrime
} // Utils
