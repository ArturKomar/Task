import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import static java.io.File.separator;

public class Main {
    public static void main(String[] args) throws IOException {
        String choose;
        String content;
        boolean option = true;
        Scanner scanner = new Scanner(System.in);
        File in1 = new File("C:" + separator + "in1.txt");
        File in2 = new File("C:" + separator + "in2.txt");
        String intFilePath = "integer.txt";
        String floatFilePath = "float.txt";
        String stringFilePath = "string.txt";

        FileManager fileManager = new FileManager(intFilePath, floatFilePath, stringFilePath);
        fileManager.sortFile(String.valueOf(in1));
        fileManager.sortFile(String.valueOf(in2));
        fileManager.closeStreams();

        PrintStatisticStr printStatisticStr = new PrintStatisticStr(new LinkedList<>());
        PrintStatisticNum printStatisticNum = new PrintStatisticNum(new LinkedList<>(), new LinkedList<>());


        while (option) {
            System.out.println("""  
                    Введите информацию которую хотите получить  
                    -s : Краткая информация  -f : Полная информация  
                    -a : Добавление данных в файл -p : Задать префикс и путь к файлу""");
            choose = scanner.nextLine();
            switch (choose) {
                case "-s":
                    printStatisticNum.Information(new File(intFilePath), "int", "-s");
                    printStatisticNum.Information(new File(floatFilePath), "float", "-s");
                    printStatisticStr.Information(new File(stringFilePath), "string", "-s");
                    break;
                case "-f":
                    printStatisticNum.Information(new File(intFilePath), "int", "-f");
                    printStatisticNum.Information(new File(floatFilePath), "float", "-f");
                    printStatisticStr.Information(new File(stringFilePath), "string", "-f");
                    break;
                case "-a":
                    System.out.println("Введите данные в файл");
                    content = scanner.nextLine();
                    printStatisticNum.addToFileToInt(new File(intFilePath), content);
                    printStatisticNum.addToFileToFloat(new File(floatFilePath), content);
                    printStatisticStr.addToFile(new File(stringFilePath), content);
                    break;
                case "-p":
                    System.out.print("Введите префикс файла: ");
                    String filePrefix = scanner.nextLine();
                    FileManager prefix = new FileManager(filePrefix + "integer.txt",
                            filePrefix + "float.txt",
                            filePrefix + "string.txt");
                    prefix.sortFile(String.valueOf(in1));
                    prefix.sortFile(String.valueOf(in2));
                    System.out.println("File sorted successfully!");
                    break;
                case "-o":
                    System.out.print("Введите путь для выходных файлов: ");
                    String outputPath = scanner.nextLine();
                    intFilePath = outputPath + "/integer.txt";
                    floatFilePath = outputPath + "/float.txt";
                    stringFilePath = outputPath + "/string.txt";
                    FileManager outpath = new FileManager(intFilePath, floatFilePath, stringFilePath);
                    outpath.sortFile(String.valueOf(in1));
                    outpath.sortFile(String.valueOf(in2));
                    System.out.println("Файл был перемещен!");
                    break;
                default:
                    System.out.println("Неверная опция");
            }
            System.out.println("""
                                        
                    Для завершения программы введите << exit >>
                    Для продолжение нажмите любой символ""");
            String close = scanner.nextLine();
            if (close.equalsIgnoreCase("exit")) {
                option = false;
            } else {
                option = true;
            }
        }
    }
}