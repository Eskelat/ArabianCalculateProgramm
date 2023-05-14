import java.beans.PropertyEditorManager;
import java.util.Scanner;

public class Calculator
{
    //msg rules
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System. out. println("Input numbers (1-10 or I-X)" +
                "\nand symbols '+', '-', '*', '/'." +
                "\nand space between. Like: '1 + 1' or 'I + I':");


        //input
        String input = scanner.nextLine().trim();
        String[] credits = input.split(" ");

        //limit symbols
        if (credits.length != 3)
        {
            throw new IllegalArgumentException("ERROR. More than 1 operation");
        }
        int a, b;

        //checkinput
        try
        {
            a = parser(credits[0]);
            b = parser(credits[2]);
        } catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException("ERROR. Not Arabic or Roman numbers");
        }

        //check Arabic+Arabic or Roman+Roman
        if ((isArab(credits[0]) && isRom(credits[2])) || (isRom(credits[0]) && isArab(credits[2])))
        {
            throw new IllegalArgumentException("ERROR. Use Arabic + Arabic or Roman + Roman");
        }

        //checking in 1-10
        if (a < 1 || a > 10 || b < 1 || b > 10)
        {
            throw new IllegalArgumentException("ERROR.Numbers NOT in 1 and 10 queue");
        }

        //OPERATIONS!!
        int result;
        switch (credits[1])
        {
            case "+": result = a + b; break;
            case "-": result = a - b; break;
            case "*": result = a * b; break;
            case "/": result = a / b; break;
            default: throw new IllegalArgumentException("ERROR. use only '+', '-', '*', '/'");
        }

        //if using romans, result must be >0, else - output
        if (isRom(credits[0]) || isRom(credits[2]))
        {
            if (result < 1)
            {
                throw new IllegalArgumentException("ERROR. NOT positive RESULT if Romans");
            }
            System.out.println(toRom(result));
        }
        else
        {
            System.out.println(result);
        }
    }


    //----------------------------------------------------------------------------------------
    //parser
    private static int parser(String s)
    {
        try
        {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException e)
        {
            int result = 0;
            for (int i = 0; i < s.length(); i++)
            {
                int value = romanToArab(s.charAt(i));
                if (i + 1 < s.length() && value < romanToArab(s.charAt(i + 1)))
                {
                    result -= value;
                }
                else
                {
                    result += value;
                }
            }
            if (result == 0)
            {
                throw new IllegalArgumentException("ERROR. Incorrect Number");
            }
            return result;
        }
    }

    //for roman nums
    private static int romanToArab(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: throw new IllegalArgumentException("ERROR. Incorrect Roman");
        }
    }
    //----------------------------------------------------------------------------------------------
    //isArabic
    private static boolean isArab(String s)
    {
        try {
            Integer.valueOf(s);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    //isRoman
    private static boolean isRom(String s)
    {
        String str = "IIIVIIIX"; // all numbers 1-10 here. if i need 1-11 its look like "IIIVIIIXI" etc
        return str.contains(s);
    }
    //----------------------------------------------------------------------------------------------
    //Check if credits Roman - result Roman
    //iz ethernet
    private static String toRom(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Roman CANT zero or negative numbers");
        }
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] romanValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < romanValues.length; i++) {
            while (n >= romanValues[i]) {
                sb.append(romanSymbols[i]);
                n -= romanValues[i];
            }
        }
        return sb.toString();
    }
}

