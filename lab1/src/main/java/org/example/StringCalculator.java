package org.example;

public class StringCalculator {
    public static void main(String[] args){
        StringCalculator calculator = new StringCalculator();
        String string="1,h,5,5";
        try {
            int result = calculator.add(string);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    public int add(String numbers){

        if(numbers.isEmpty() || (numbers.length() == 4 && numbers.charAt(0) == '/' && numbers.charAt(1) == '/' && numbers.charAt(3) == '\n')) {
            return 0;
        }

        if (numbers.length()>4  && numbers.charAt(0) == '/' && numbers.charAt(1) == '/' && numbers.charAt(3) == '\n'){
            String delimiter = String.valueOf(numbers.charAt(2));
            numbers=numbers.replace("//"+delimiter+"\n", "");
            numbers=numbers.replace(delimiter, ",");
        }

        numbers = numbers.replace("\n", ",");
        int suma=0;
        String[] numbers_mas=numbers.split(",");
        for (String number : numbers_mas) {
            try {
                suma += Integer.parseInt(number);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return suma;
    }
}