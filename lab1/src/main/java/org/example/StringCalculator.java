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
            numbers=numbers.replace(delimiter, "=");
        }

        if (numbers.length()>5  && numbers.charAt(0) == '/' && numbers.charAt(1) == '/' && numbers.charAt(2) == '[' && numbers.charAt(3) != ']'){
            //ArrayList<String> delimiters = new ArrayList<>();
            int variable=0;
            numbers = numbers.substring(1);
            numbers = numbers.substring(1);

            while ( variable!=1) {
                StringBuilder delimiterBuilder = new StringBuilder();
                int index = numbers.indexOf(']');
                if(numbers.charAt(index+1)!='['){
                    variable=1;
                }
                if (index == -1) {
                    throw new IllegalArgumentException("Помилка");
                }
                int i = 1;
                while (i < index) {
                    delimiterBuilder.append(numbers.charAt(i));
                    i++;
                }
                String delimiter = delimiterBuilder.toString();

                numbers = numbers.replace("[" + delimiter + "]", "");
                numbers = numbers.replace(delimiter, "=");
            }
            if (numbers.charAt(0)=='\n') {
                numbers = numbers.substring(1);
            }else {
                throw new IllegalArgumentException("Помилка");
            }

        }

        numbers = numbers.replace("\n", "=");
        numbers = numbers.replace(",", "=");

        String[] numbers_mas=numbers.split("=");

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
        int suma=0;
        for (String number : numbers_mas) {
            if(Integer.parseInt(number)<=1000) {
                suma += Integer.parseInt(number);
            }
        }
        return suma;
    }
}