import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Калькулятор. Для выхода введите exit");

        while (true) {
            InputStream inputStream = System.in;
            Reader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String string = bufferedReader.readLine(); //читаем строку с клавиатуры
            if (string.equals("exit"))
                break;
            System.out.println(calc(string)) ;
        }

    }

    public static String calc(String input) {

        boolean roman;
        int index1 = input.indexOf('+');
        int index2 = input.indexOf('-');
        int index3 = input.indexOf('*');
        int index4 = input.indexOf('/');
        int index = index1 + index2 + index3 + index4 + 3;

        if (index<0)
            throw new UnsupportedOperationException("Данные введены неверно!");

        String string1 = input.substring(0, index).trim();
        String string2 = input.substring(index + 1).trim();
        int number1;
        int number2;
        int answer = 0;

        roman = (isRoman(string1) && isRoman(string2));

        if ( (isRoman(string1) && !isRoman(string2) )||  (!isRoman(string1) && isRoman(string2) ) )
            throw new UnsupportedOperationException("Одно число римское, другое арабское!");

        try {
            if (roman) {
                number1 = romanToArabic(string1);
                number2 = romanToArabic(string2);
            } else {
                number1 = Integer.parseInt(string1);
                number2 = Integer.parseInt(string2);
            }
        }
        catch(Exception e){
            throw new UnsupportedOperationException("Данные введены неверно!");
        }


        if (number1>10 || number2>10)
            throw new UnsupportedOperationException("Числа не должны быть больше 10!");

        if (index1 > 0)
            answer = number1 + number2;
        else if (index2 > 0) {
            answer = number1 - number2;
        } else if (index3 > 0) {
            answer = number1 * number2;
        } else if (index4 > 0) {
            answer = number1 / number2;
        }
        
        if (roman)
            return arabicToRoman(answer);
        else {
            return String.valueOf(answer);
        }

    }

    private static boolean isRoman(String s) {
        boolean result = s.matches("(X|IX|IV|V?I{0,3})");
        if (result) {
            return true;
        } else {
            return false;
        }
    }

    private static int romanToArabic(String s) {
        int[] intervals = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        String[] numerals = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        for (int i = 0; i < intervals.length; i++) {
            if (numerals[i].equals(s))
                return intervals[i];
        }
        return 0;
    }

    private static String arabicToRoman(int num)
    {
        if (num<1) {
            throw new UnsupportedOperationException("Итоговое римское число меньше единицы!");
        }
        String[] keys = new String[] { "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        int[] vals = new int[] {  100, 90, 50, 40, 10, 9, 5, 4, 1 };

        StringBuilder ret = new StringBuilder();
        int ind = 0;

        while(ind < keys.length)
        {
            while(num >= vals[ind])
            {
                var d = num / vals[ind];
                num = num % vals[ind];
                for(int i=0; i<d; i++)
                    ret.append(keys[ind]);
            }
            ind++;
        }
        return ret.toString();
    }

}
