import java.io.PrintWriter;

import java.math.BigInteger;

import java.util.Random;

/**
 * A quick experiment with primality testing.
 */
public class Experiment
{
  // +----------+--------------------------------------------------------
  // | Settings |
  // +----------+

  /**
   * The number of prime experiments to run.
   */
  static final int PRIME_EXPERIMENTS = 5;

  /**
   * The number of composite experiments to run.
   */
  static final int COMPOSITE_EXPERIMENTS = 5;

  /**
   * The bit length of the values we're testing.
   */
  static final int BITLEN = 100;

  // +------+------------------------------------------------------------
  // | Main |
  // +------+

  public static void main(String[] args)
  {
    PrintWriter pen = new PrintWriter(System.out, true);
    Random random = new Random();

    // A few expected primes.
    for (int i = 0; i < PRIME_EXPERIMENTS; i++)
      {
        BigInteger candidate = BigInteger.probablePrime(BITLEN, random);
        pen.println("probablyPrime(p=" + candidate + "): "
                    + Utils.probablyPrime(candidate));
      } // for i

    // A few expected composites.
    for (int i = 0; i < COMPOSITE_EXPERIMENTS; i++)
      {
        BigInteger candidate1 = BigInteger.probablePrime(BITLEN / 2, random);
        BigInteger candidate2 =
            BigInteger.probablePrime(BITLEN / 2 + 1, random);
        BigInteger candidate = candidate1.multiply(candidate2);
        pen.println("probablyPrime(c=" + candidate + "): "
                    + Utils.probablyPrime(candidate));
      } // for i
  } // main(String[])

} // class Experiment
