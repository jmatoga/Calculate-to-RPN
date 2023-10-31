import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        //System.out.printf("Chcesz podać równanie zapisane w postaci infiksowej? true - TAK, false - NIE, równanie w ONP\nTwój wybór >> ");
        //boolean ifInfix = in.nextBoolean();

        System.out.printf("Podaj rówanie/a algebraiczne, każde musi być zakończone znakiem = i spacją (np. (3+12)*8/2= 2*(11-8)= (31+1)*8/2= 3*(11-9)/4= itp.)\nTwoje równanie >> ");
        //String line = "32.2+4*2/(1-5)^2= 5+(1+2)*4-3= 12+2*(3*4+10/5)=";  // 13,11,15 //3+4*2/(1-5)^2= 5+(1+2)*4-3= 12+2*(3*4+10/5)=
        String line = in.nextLine();

        int sizeOfTempArr = 0;
        int indexOfLastFoundEqualSign = line.indexOf("=");
        int indexToShow = 0;

        String[] tempArr = new String[line.indexOf("=")];

        System.out.printf("Czy chcesz zobaczyć wszystkie obliczenia na raz? true - TAK, false - NIE\nTwój wybór >> ");
        boolean showCalculationsAll = in.nextBoolean();
        boolean showCalculationsSpecific = false;
        if (!showCalculationsAll) {
            System.out.printf("Czy chcesz zobaczyć obliczenia jakiegoś konkretnego wyrażenia? true - TAK, false - NIE\nTwój wybór >> ");
            showCalculationsSpecific = in.nextBoolean();
        }

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '=') {
                boolean showCalculations = false;
                i++;

                if (showCalculationsSpecific) {
                    System.out.println("Czy chcesz zobaczyć obliczenia wyrażenia " + line.substring(indexToShow, i - 1) + "? true - TAK, false - NIE\nTwój wybór >> ");
                    showCalculations = in.nextBoolean();
                } else if (showCalculationsAll)
                    showCalculations = true;

                String[] tempOutArr = Calculator.INFtoONP(tempArr, showCalculations); // INF na ONP
                System.out.println("Wyrażenie algebraiczne " + line.substring(indexToShow, i - 1) + " zamienione na ONP wygląda następująco: "); // line.substring(i-indexOfLastFoundEqualSign-1,i-1

                indexToShow = i + 1;

                Calculator.showArr(tempOutArr, sizeOfTempArr);
                System.out.println("Wynik: " + Calculator.calculateONP(tempOutArr, sizeOfTempArr, showCalculations) + "\n"); // obliczanie

                if (i != line.length()) {
                    tempArr = new String[line.indexOf("=", indexOfLastFoundEqualSign + 1) - indexOfLastFoundEqualSign - 2]; //znajdujemy index kolejnego "=" i od tego odejmujemy index ostatnio znalezionego "=" i -2(spacja i "=")
                    indexOfLastFoundEqualSign = line.indexOf("=", indexOfLastFoundEqualSign + 1);
                    sizeOfTempArr = 0;
                }
            } else {
                sizeOfTempArr++;

                if (Character.isDigit(line.charAt(i))) {
                    int j = 0; // zmienna pomocnicza do przejśćia do końca stringa w elemencie tablicy (liczba >0 albo double)
                    tempArr[sizeOfTempArr - 1] = "";

                    while (Character.isDigit(line.charAt(i + j)) || line.charAt(i + j) == '.')
                    {
                        tempArr[sizeOfTempArr - 1] += line.substring(i + j, i + j + 1);
                        j++;
                    }
                    i = i + j - 1;

                } else
                    tempArr[sizeOfTempArr - 1] = line.substring(i, i + 1);
            }
        }
    }
}