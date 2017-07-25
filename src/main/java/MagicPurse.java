import java.util.regex.Pattern;

/**
 * Created by robelyemane on 25/07/2017.
 */

public class MagicPurse {

    public static void main(String[] args) {
        System.out.println("Starting Magic Purse app...");

        if (args.length > 0) {

            String psd_pattern = "(\\d)+\\/(\\d+|-)\\/(\\d+|-)";
            String sd_pattern = "^\\d+\\/[\\d+-]$";
            String d_pattern = "^\\d+d$";

            //regexes
            // pound/shilling/denaris => (\d)+\/(\d+|-)\/(\d+|-) or \d+\/[\d+|-]\/[\d+|-]
            //shilling/denaris    ~ ^\d+\/[\d+-]$
            //denaris                 => ^\d+d$

            for (String arg : args) {
                int p, s = 0, d = 0;
                // get £/s/d
                if (Pattern.matches(psd_pattern, arg)) {

                    String[] denoms = arg.split("/");
                    p = Integer.parseInt(denoms[0]);
                    if (!("-").equals(denoms[1]))
                        s = Integer.parseInt(denoms[1]);
                    if (!("-").equals(denoms[2])) {
                        d = Integer.parseInt(denoms[2]);
                    }

                    System.out.format("You have £ = %d, Shillings = %d , denaris = %d%n ", p, s, d);
                    // get s/d
                } else if (Pattern.matches(sd_pattern, arg)) {
                    String[] denoms = arg.split("/");
                    s = Integer.parseInt(denoms[0]);
                    if (!("-").equals(denoms[1])) {
                        d = Integer.parseInt(denoms[1]);
                    }
                    System.out.format("You have Shillings = %d , denaris = %d%n ", s, d);
                    // get d
                } else if (Pattern.matches(d_pattern, arg)) {
                    String str = arg.substring(0, arg.length() - 1);
                    d = Integer.parseInt(str);
                    System.out.format("You have denaris = %d%n ", d);
                } else {
                    System.out.println("Unexpected denomination: " + arg);
                }

            }

        } else {
            System.out.println("No arguments passed, Exiting.");
            System.exit(1);
        }

    }
}