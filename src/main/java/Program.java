import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/*1. Написать функцию, создающую резервную копию всех файлов в директории(без поддиректорий) во вновь созданную папку ./backup
2. Доработайте класс Tree и метод print который мы разработали на семинаре. Ваш метод должен распечатать полноценное дерево директорий
 и файлов относительно корневой директории.
3***. Предположить, что числа в исходном массиве из 9 элементов имеют диапазон[0, 3], и представляют собой,
 например, состояния ячеек поля для игры в крестикинолики, где 0 – это пустое поле, 1 – это поле с крестиком,
 2 – это поле с ноликом,
3 – резервное значение. Такое предположение позволит хранить в одном числе типа int всё поле 3х3. Записать в файл 9 значений так, чтобы они заняли три байта
*/

public class Program {

    private static final Random random = new Random();
    private static final int char_bound_l = 65;
    private static final int char_bound_h = 90;
    private static final String to_search = "GEEKBRAINS";

    public static void main(String[] args) throws IOException {

        String str = generateSymbol(15);
        System.out.println(generateSymbol(15));

        writefilecontent("sample004", 3);
        System.out.println(searchInFile("sample004", to_search));
        writefilecontent2("sample002", 3, 5);
        System.out.println(searchInFile("sample002", to_search));
        cocatenate("sample004", "sample002", "sample01_out.txt");
        System.out.println(searchInFile("sample01_out.txt", to_search));
        Tree.print(new File("."), "", true);
        String[] fileNames = new String[3];
        for (int i = 0; i < fileNames.length; i++) {
            fileNames[i] = "file_" + i + ".txt";
            writefilecontent2(fileNames[i], 100, 4);
            System.out.printf("Файл %s создан. \n", fileNames[i]);

        }

        List<String> result = searchMath(fileNames, to_search);
        for (String s : result) {
            System.out.printf("Файл %s содержит искомое слово '%s'\n", s, to_search);
        }
        backupfile(new File("."), "backup");


    }

    private static String generateSymbol(int amount) {
        StringBuilder stringbuilder = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            stringbuilder.append((char) random.nextInt(char_bound_l, char_bound_h));
        }
        return stringbuilder.toString();

    }

    private static void writefilecontent(String filename, int length) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            fileOutputStream.write(generateSymbol(length).getBytes());
        }
       /* FileOutputStream fileOutputStream = new FileOutputStream(filename);
        fileOutputStream.write(generateSymbol(length).getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();*/

    }

    private static void writefilecontent2(String filename, int length, int words) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            for (int i = 0; i < words; i++) {
                if (random.nextInt(5) == 5 / 2) {
                    fileOutputStream.write(to_search.getBytes());

                } else {
                    fileOutputStream.write(generateSymbol(length).getBytes());
                }
                fileOutputStream.write(' ');
            }
        }
       /* FileOutputStream fileOutputStream = new FileOutputStream(filename);
        fileOutputStream.write(generateSymbol(length).getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();*/

    }

    private static void cocatenate(String fileIn1, String fileIn2, String fileOut) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileOut)) {
            int c;
            try (FileInputStream fileInputStream = new FileInputStream(fileIn1)) {
                while ((c = fileInputStream.read()) != -1) {
                    fileOutputStream.write(c);

                }
            }
            try (FileInputStream fileInputStream = new FileInputStream(fileIn2)) {
                while ((c = fileInputStream.read()) != -1) {
                    fileOutputStream.write(c);
                }

            }
        }

    }

    private static boolean searchInFile(String filename, String search) throws IOException {

        try (FileInputStream fileInputStream = new FileInputStream(filename)) {
            byte[] searchData = search.getBytes();
            int c;
            int i = 0;
            while ((c = fileInputStream.read()) != -1) {
                if (c == searchData[i]) {
                    i++;
                } else {
                    i = 0;
                    if (c == searchData[i]) {
                        i++;
                        continue;
                    }
                }
                if (searchData.length == i) {
                    return true;
                }


            }
            return false;
        }

    }

    private static List<String> searchMath(String[] files, String search) throws IOException {
        List<String> list = new ArrayList<>();
        File path = new File(new File(".").getCanonicalPath());
        File[] dir = path.listFiles();
        for (int i = 0; i < dir.length; i++) {
            if (dir[i].isDirectory())
                continue;
            for (int j = 0; j < files.length; j++) {
                if (dir[i].getName().equals(files[j])) {
                    if (searchInFile(dir[i].getName(), search)) {
                        list.add(dir[i].getName());
                        break;

                    }
                }

            }

        }
        return list;

    }

    public static void backupfile(File file, String dir) throws IOException {

        File path = new File("." + "/" + dir);
        path.mkdirs();
        File[] files = file.listFiles();


        for (int i = 0; i < files.length; i++) {

            if (!files[i].isDirectory()) {


                try (FileOutputStream fileOutputStream = new FileOutputStream(path.getName() + "/" + files[i])) {
                    int c;
                    try (FileInputStream fileInputStream = new FileInputStream(files[i])) {
                        while ((c = fileInputStream.read()) != -1) {
                            fileOutputStream.write(c);

                        }
                    }

                }
            }
        }
    }


}






