import java.io.File;


public class Tree {


    public static void print(File file, String indent, boolean islast) {
        System.out.print(indent);
        if (islast) {
            System.out.print("└");
            indent += " ";
        } else {
            System.out.print("├");
            indent += "│";

        }
        System.out.println(file.getName());
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        int subFile = 0;
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isDirectory()) {
                subFile++;
            }
        }
        int subdfilcount = 0;
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isDirectory()) {
                print(files[i], indent, subdfilcount == --subFile);

            }
        }

        int subDirTotal = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                subDirTotal++;
            }
        }
        int subdircount = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                print(files[i], indent, subdircount == subDirTotal - 1);
                subdircount++;
            }
        }

    }


}
