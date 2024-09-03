import java.io.*;
import java.util.LinkedList;

public class PrintStatisticStr implements Printinfo{
    private final LinkedList<String> Str;
    public PrintStatisticStr(LinkedList<String> Str){
        this.Str = Str;
    }
    private double minimumLength;
    private double maximumLength;
    private long lineCount = 0;
    private String nameShort;
    private String nameLong;
    private long totalLength = 0;

    public long getSize() {
        return lineCount;
    }

    public void setLineCount(long count) {
        this.lineCount = count;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }


    public double getMinimumLength() {
        return minimumLength;
    }

    public double getMaximumLength() {
        return maximumLength;
    }

    public void setMinimumLength(double minimumLength) {
        this.minimumLength = minimumLength;
    }

    public void setMaximumLength(double maximumLength) {
        this.maximumLength = maximumLength;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameLong(String nameLong) {
        this.nameLong = nameLong;
    }

    public String getNameLong() {
        return nameLong;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }
    public String getStringRegex() {
        return "^(?![+-]?\\d)(?!\\n)[^\\d ].*$";
    }

    @Override
    public void Information(File data, String type, String choose) {
        try (BufferedReader br = new BufferedReader(new FileReader(data))) {
            String line;
            while ((line = br.readLine()) != null) {
                Str.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        if (Str.isEmpty()) {
            return;
        }
        this.nameShort = findShortestString(Str);
        setNameShort(nameShort);
        this.nameLong = findLongestString(Str);
        setNameLong(nameLong);
        this.totalLength = calculateTotalLength(Str);
        setTotalLength(totalLength);
        this.maximumLength = MaxLength(Str);
        setMaximumLength(maximumLength);
        this.minimumLength = MinLength(Str);
        setMinimumLength(minimumLength);
        setLineCount(Str.size());
        if(choose.equals("-s")){
            printShortStringResult();
        } else if (choose.equals("-f")) {
            printFullStringResult();
        }

    }
    private static String findShortestString(LinkedList<String> stringArray) {
        if (stringArray.isEmpty()) {
            return "Список строк пуст";
        }
        String shortestString = stringArray.get(0);
        for (String str : stringArray) {
            if (str.length() < shortestString.length()) {
                shortestString = str;
            }
        }
        return shortestString;
    }


    private static String findLongestString(LinkedList<String> stringArray) {
        if (stringArray.isEmpty()) {
            return "Список строк пуст";
        }
        String longestString = stringArray.get(0);
        for (String str : stringArray) {
            if (str.length() > longestString.length()) {
                longestString = str;
            }
        }
        return longestString;
    }


    private static int calculateTotalLength(LinkedList<String> stringArray) {
        int totalLength = 0;
        for (String str : stringArray) {
            totalLength += str.length();
        }
        return totalLength;
    }
    private static int MinLength(LinkedList<String> minEl){
        if(minEl.isEmpty()){
            System.out.println("Список пуст");
        }
        String minString = null;
        int minLength = Integer.MAX_VALUE;
        for (String s : minEl) {
            if (s.length() < minLength) {
                minString = s;
                minLength = s.length();
            }
        }
        return minLength;
    }
    private static int MaxLength(LinkedList<String> maxEl){
        if(maxEl.isEmpty()){
            System.out.println("Список пуст");
        }
        String minString = null;
        int maxLength = Integer.MIN_VALUE;
        for (String s : maxEl) {
            if (s.length() > maxLength) {
                minString = s;

                maxLength = s.length();
            }
        }
        return maxLength;
    }
    public void printFullStringResult(){
        System.out.println("\tПолная информация\t");
        System.out.println("Самая короткая строка: " + getNameShort() + "\tДлина короткой строки: " + getMinimumLength());
        System.out.println("Самая длинная строка: " + getNameLong() + "\tДлина длинной строки: " + getMaximumLength());
        System.out.println("Общий размер всех строк: " + getTotalLength());
        System.out.println("Количество слов в файле: " + getSize());
        System.out.println();
    }

    public void printShortStringResult(){
        System.out.println("Количество элементов типа String: " + getSize());
        System.out.println("Количетсво всех элементов: " + getTotalLength());
    }

    public void addToFile(File filePath, String content) {
        if (content.matches(getStringRegex()))
            try (FileWriter writer = new FileWriter(filePath, true)) {
                writer.append(content).append('\n').flush();
            } catch (IOException e) {
                System.out.println("Ошибка при записи в файл: " + e.getMessage());
            }
    }
}

