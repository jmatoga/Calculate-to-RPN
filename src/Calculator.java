public class Calculator {
    public static String[] INFtoONP(String[] arr, boolean showCalculations)
    {
        int sizeOfStack = 0;
        int sizeOfOutArr = 0;
        String[] stack = new String[arr.length];
        String[] outArr = new String[arr.length];

        for (int i = 0; i < arr.length; i++)
        {
            if(showCalculations)
                System.out.println(arr[i]);

            if (arr[i] != null && Character.isDigit(arr[i].charAt(0)) && Double.parseDouble(arr[i]) >= 0.0) // liczba >=0
            {
                if(showCalculations)
                    System.out.println("Dodaję na wyjście " + arr[i]);

                sizeOfOutArr++;
                outArr[sizeOfOutArr - 1] = arr[i];

            }
            else if (arr[i] != null && (arr[i].equals("+") || arr[i].equals("-") || arr[i].equals("*") || arr[i].equals("/") || arr[i].equals("^")))
            {
                if (sizeOfStack > 0)
                    //  System.out.println("WARN!! " + sizeOfStack + " " + kolejnosc1(line.charAt(i)) + " " + kolejnosc(stack[sizeOfStack - 1]));

                while (sizeOfStack > 0 && getPriority(arr[i]) <= getPriority(stack[sizeOfStack - 1])) // -1
                {
                    sizeOfStack--;
                    sizeOfOutArr++;

                    if(showCalculations)
                        System.out.println("Usuwam ze stosu i dodaję na wyjście " + stack[sizeOfStack]);

                    outArr[sizeOfOutArr - 1] = stack[sizeOfStack];
                    stack[sizeOfStack] = "";
                }

                if(showCalculations)
                    System.out.println("Dodaję na stos " + arr[i]);

                sizeOfStack++;
                stack[sizeOfStack - 1] = arr[i];
            }
            else if (arr[i] != null && arr[i].equals("("))
            {
                if(showCalculations)
                    System.out.println("Dodaję na stos " + arr[i]);

                sizeOfStack++;
                stack[sizeOfStack - 1] = arr[i];
            }
            else if (arr[i] != null && arr[i].equals(")"))
            {
                while (sizeOfStack > 0 && !stack[sizeOfStack - 1].equals("("))
                {
                    if(showCalculations)
                        System.out.println("Usuwam ze stosu i dodaję na wyjście " + stack[sizeOfStack - 1]);

                    if (stack[sizeOfStack - 1].equals("("))
                        break;

                    sizeOfStack--;
                    sizeOfOutArr++;
                    outArr[sizeOfOutArr - 1] = stack[sizeOfStack];
                    stack[sizeOfStack] = "";
                }

                if (sizeOfStack > 0)
                {
                    if(showCalculations)
                        System.out.println("Usuwam ze stosu " + stack[sizeOfStack-1]);

                    sizeOfStack--;
                    stack[sizeOfStack] = "";
                }
            }

            if(showCalculations)
            {
                System.out.print("Stos: ");

                for (int j = 0; j < sizeOfStack; j++)
                    System.out.printf(stack[j]);

                System.out.print("     Wyjście: ");

                for (int j = 0; j < sizeOfOutArr; j++)
                    System.out.printf(outArr[j] + " ");

                System.out.println("\n-----------------------------------------------------");
            }
        }

        while (sizeOfStack > 0)
        {
            if (stack[sizeOfStack].equals("(") || stack[sizeOfStack].equals(")"))
                throw new RuntimeException("BŁĄD!!! zła kolejność nawiasów {" + sizeOfStack + "," + stack[sizeOfStack] + "}");

            sizeOfStack--;
            sizeOfOutArr++;
            outArr[sizeOfOutArr - 1] = stack[sizeOfStack];
            stack[sizeOfStack] = "";
        }
        return outArr;
    }

    private static int getPriority(String znak)
    {
        switch (znak) {
            case "(" -> {
                return 0;
            }
            case "+", "-", ")" -> {
                return 1;
            }
            case "*", "/" -> {
                return 2;
            }
            case "^" -> {
                return 3;
            }
            default -> {
                throw new RuntimeException("BŁĄD!!! W Funkcji getPriority podano zly znak {" + znak + "}");
            }
        }
    }

    public static void showArr(String[] arr, int sizeOfArr)
    {
        for(int i=0; i<sizeOfArr; i++)
        {
            if(arr[i] != null) // za duża tablica po zamianie na onp (bo się usuwają nawiasy np)
                System.out.printf(arr[i] + " ");
        }
        System.out.print("\n");
    }

    public static double calculateONP(String[] arr,int sizeOfArr, boolean showCalculations)
    {
        int sizeOfStack = 0;
        double[] stack = new double[sizeOfArr];

        if (showCalculations)
            System.out.println("\nObliczanie wyrażenia w ONP:\n");

        for (int i = 0; i < sizeOfArr; i++)
        {
            if (showCalculations && arr[i] != null)
                System.out.println(arr[i]);

            if (arr[i] != null && Character.isDigit(arr[i].charAt(0)) && Double.parseDouble(arr[i]) >= 0.0) // liczba >=0
            {
                if (showCalculations)
                    System.out.println("Dodaję na stos " + arr[i]);

                sizeOfStack++;
                stack[sizeOfStack - 1] = Double.parseDouble(arr[i]);
            }
            else if (arr[i] != null && (arr[i].equals("+") || arr[i].equals("-") || arr[i].equals("*") || arr[i].equals("/") || arr[i].equals("^")))
            {
                double tempResult=0;

                sizeOfStack--;
                double FirstInt = stack[sizeOfStack];
                sizeOfStack--;
                double SecondInt = stack[sizeOfStack];

                switch(arr[i])
                {
                    case "+" -> {
                        tempResult = FirstInt + SecondInt;
                    }
                    case "-" -> {
                        tempResult = SecondInt - FirstInt;
                    }
                    case "*" -> {
                        tempResult = (FirstInt * SecondInt);
                    }
                    case "/" -> {
                        tempResult = SecondInt / FirstInt;
                    }
                    case "^" -> {
                        tempResult = Math.pow(SecondInt, FirstInt);
                    }
                }

                if (showCalculations)
                    System.out.println("Operacja: " + FirstInt + " " + arr[i] + " " + SecondInt + " = " + tempResult);

                sizeOfStack++;
                stack[sizeOfStack - 1] = tempResult;
            }
        }
        return stack[sizeOfStack-1];
    }
}
