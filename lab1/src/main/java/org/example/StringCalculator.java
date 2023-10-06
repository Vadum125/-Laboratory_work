package org.example;

import java.util.ArrayList;

public class StringCalculator {
    public static void main(String[] args){
        StringCalculator calculator = new StringCalculator();
        String string="-1";
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
                Integer.parseInt(number);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        ArrayList<Integer> negative_sp = new ArrayList<>();
        for(String number : numbers_mas){
            if(Integer.parseInt(number)<0){
                negative_sp.add(Integer.parseInt(number));
            }
        }
        if(!negative_sp.isEmpty()){
            String message = "Недозволені від’ємні числа: " + negative_sp;
            throw new IllegalArgumentException(message);
        }

        for (String number : numbers_mas) {
            if(Integer.parseInt(number)<=1000) {
                suma += Integer.parseInt(number);
            }
        }
        return suma;
    }
}