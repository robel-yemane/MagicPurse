import java.util.regex.Pattern;

/**
 * Created by robelyemane on 25/07/2017.
 *
 * Interview test
 */

public class MagicPurse {

  static int numberOfWays = 0;

  public static void main(String[] args) {
    int amount = 0;
    //check if user input is in correct denomination format, warn and display correct one if not.
    if (args.length > 0) {
      for (String arg : args) {
        amount = getAmount(arg);
        if (amount == -1) {
          System.out.println("Amount specified (" + amount + ") is not a valid "
              + "denomination. Please specify denomination in the following format:\n\"#/#/#\", \"#/#\" and \"#d\""
              + " or \"-\" for any blank value.\n");
          System.exit(1);
        }
        System.out.println("this is the amount to be calculated: " + amount);
      }

    } else {
      System.out.println("No arguments passed, Exiting.");
    }

    int[] denominations = {300, 240, 120, 60, 30, 10, 5}; // coin denominations

    //counter to keep track of each denomination for the amount in hand
    int[] count = new int[denominations.length];

    int startIndex = 0;

    getCombination(denominations, count, startIndex, amount);
    System.out.println("Combinations =  " + numberOfWays);
  }


  /**
   * Get the amount of money from user input in pre-decimalisation notation to a single number.
   *
   * @param amount from user input. This is the amount of money to be calculated
   * @return the number representation of the string input
   */
  public static int getAmount(String amount) {

    String psd_pattern = "(\\d)+\\/(\\d+|-)\\/(\\d+|-)";
    String sd_pattern = "^\\d+\\/[\\d+-]$";
    String d_pattern = "^\\d+d$";
    int p, s, d;
    int tot = 0;

    // pound/shilling/denaris => (\d)+\/(\d+|-)\/(\d+|-) or \d+\/[\d+|-]\/[\d+|-]
    //shilling/denaris    ~ ^\d+\/[\d+-]$
    //denaris                 => ^\d+d$

    if (Pattern.matches(psd_pattern, amount)) {

      String[] denoms = amount.split("/");
      p = Integer.parseInt(denoms[0]);
      tot += p * 240;
      if (!("-").equals(denoms[1])) {
        s = Integer.parseInt(denoms[1]);
        tot += s * 120;
      }
      if (!("-").equals(denoms[2])) {
        d = Integer.parseInt(denoms[2]);
        tot += d * 10;
      }

    } else if (Pattern.matches(sd_pattern, amount)) {
      String[] denoms = amount.split("/");
      s = Integer.parseInt(denoms[0]);
      tot += s * 120;
      if (!("-").equals(denoms[1])) {
        d = Integer.parseInt(denoms[1]);
        tot += d * 10;
      }

    } else if (Pattern.matches(d_pattern, amount)) {
      String str = amount.substring(0, amount.length() - 1);
      d = Integer.parseInt(str);
      tot = d * 10;
      //        System.out.format("You have denaris = %d%n ", d);
      //        System.out.println("tot =" + tot);
    } else {
      return -1;
    }

    return tot;
  }

  /**
   *
   * @param denominations
   * @param count
   * @param startIndex
   * @param totalAmount
   */
  public static void getCombination(int[] denominations, int[] count, int startIndex,
      int totalAmount) {

    if (startIndex
        >= denominations.length) // we have processed all coins : //decide if we should proceed or not by tracking start index
    {

      for (int i = 0; i < denominations.length; i++)

      {          // format the print out as  "amount=?*25 + ?*10..."
        System.out.print("" + count[i] + "*" + denominations[i] + "+");
      }
      System.out.println();

      int allDenominations = getAllDenominations(count);
      System.out.println("Total Denominations qty: " + allDenominations);
      if (allDenominations % 2 == 0) {

        numberOfWays++;

      }
      System.out.print("\n");
      return;
    }

    // otherwise we need to proceed
    //but notice if startIndex is the last one, we need to check if it
    // can be divided by the smallest coin
    //if so, this is a good combination, otherwise this is not possible combo thus discard

    if (startIndex == denominations.length - 1) { //i.e startIndex = 6

      if (totalAmount % denominations[startIndex] == 0)
      // if the amount is divisible by the smallest coin
      {

        count[startIndex] = totalAmount / denominations[startIndex];
        //set the count of coins at the current index

        //proceed to recursive call
        //(when it's divisible recursive function is sent from HERE********///
        getCombination(denominations, count, startIndex + 1,
            0); //notice startindex++ and remining amojunt =0

      }


    } else { // we still have option to choose 0-N larger coins

      //AFTER COMPLETING one full iteration and amount is divisible by denomination, why does it return here?????
      int specialValue = totalAmount / denominations[startIndex];
      for (int i = 0; i <= specialValue; i++) {

        //for every cycle in a loop, we choose an arbitrary number of larger coins and proceed next
        count[startIndex] = i;
        int anotherSpecialValue = totalAmount - denominations[startIndex] * i;
        getCombination(denominations, count, startIndex + 1, anotherSpecialValue);
        //notice we need to update the remaining amount

      }

    }

  }

  static int getAllDenominations(int[] combArray) {
    int sumOfAllDenominations = 0;

    for (int i : combArray
        ) {
      sumOfAllDenominations += i;

    }
    return sumOfAllDenominations;
  }
}
