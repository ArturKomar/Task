import java.io.*;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;

public class PrintStatisticNum implements Printinfo {
    private final LinkedList<Long> numInt;
    private final LinkedList<Double> numFloat;

    public PrintStatisticNum(LinkedList<Long> numInt, LinkedList<Double> numFloat){
        this.numInt = numInt;
        this.numFloat = numFloat;
    }

    private double minimum = -1000000;
    private double maximum = 1000000;
    private double amount = 0;
    private double average = 0;
    private int size = 0;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getMinimum() {
        return minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public LinkedList<Long> getNumInt() {
        return numInt;
    }

    public LinkedList<Double> getNumFloat() {
        return numFloat;
    }
    public String getIntRegex() {
        return "^[+-]?\\d+$";
    }
    public String getFloatRegex() {
        return "[+-]?\\d+\\.\\d+([eE][+-]?\\d+)?";
    }

    public void Information(File data, String type, String choose) {

        try (BufferedReader br = new BufferedReader(new FileReader(data))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    numInt.add(Long.parseLong(line.trim()));
                    setSize(numInt.size());
                } catch (NumberFormatException e) {
                    try {
                        numFloat.add(Double.parseDouble(line.trim()));
                        setSize(numFloat.size());
                    } catch (NumberFormatException ex) {
                        System.err.println("Ошибка преобразования строки: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }

        if (choose.equals("-s")) {
            printShortStatistic();
        } else if (choose.equals("-f")) {
            if (numInt.isEmpty() && numFloat.isEmpty()) {
                System.out.printf("%s: Файл не содержит целых чисел и чисел с плавающей точкой", type);
            } else {
                if (!numInt.isEmpty()) {
                    this.minimum = Collections.min(numInt);
                    setMinimum(minimum);
                    this.maximum = Collections.max(numInt);
                    setMaximum(maximum);
                    this.amount = numInt.stream().mapToInt(Long::intValue).sum();
                    setAmount(amount);
                    this.average = (amount/size);
                    setAverage(average);

                }
                if (!numFloat.isEmpty()) {
                    this.minimum = Collections.min(numFloat);
                    setMinimum(minimum);
                    this.maximum = Collections.max(numFloat);
                    setMaximum(maximum);
                    this.amount = numFloat.stream().mapToDouble(Double::doubleValue).sum();
                    setAmount(amount);
                    this.average = (amount/size);
                    setAverage(average);

                }
                printFullStatistic();
            }
        } else {
            System.out.println("Данной опции не существует");
        }
    }
    public void printFullStatistic() {
        DecimalFormat decimalFormat = new DecimalFormat("#.################");
        System.out.println("\tПолная статистика\t");
        System.out.println("Минимальное значение: " + decimalFormat.format(getMinimum()));
        System.out.println("Максимальное значение: " + decimalFormat.format(getMaximum()));
        System.out.println("Сумма: " + decimalFormat.format(getAmount()));
        System.out.println("Среднее: " + decimalFormat.format(getAmount()/getSize()));
        System.out.println();
    }

    public void printShortStatistic(){
        System.out.println("Краткая информация");
        System.out.println("Количество элементов целого типа: " + getNumInt().size());
        System.out.println("Количество элементов типа с плавающей точкой: " + getNumFloat().size());
    }
    public void addToFileToInt(File filePath, String content) {
        if (content.matches(getIntRegex())){
            try (FileWriter writer = new FileWriter(filePath, true)) {
                try {
                    writer.append(content).append('\n').flush();
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            } catch (IOException e) {
                System.out.println("Ошибка при записи в файл: " + e.getMessage());
            }
        }
    }
    public void addToFileToFloat(File filePath, String content) {
        if (content.matches(getFloatRegex())){
            try (FileWriter writer = new FileWriter(filePath, true)) {
                try {
                    writer.append(content).append('\n').flush();
                } catch (NumberFormatException e) {
                    System.out.println(e.getMessage());
                }
            } catch (IOException e) {
                System.out.println("Ошибка при записи в файл: " + e.getMessage());
            }
        }

    }

}