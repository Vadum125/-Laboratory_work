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

        if(numbers.isEmpty()) {
            return 0;
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