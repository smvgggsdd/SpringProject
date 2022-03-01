package common.helpers;

import java.io.File;

public class Helpers {
    //region HELPER METHODS
    public static String getExtension(File file) {
        String fileName = file.getName();
        String extension = "";
        int i = fileName.lastIndexOf('.');
        extension = fileName.substring(i+1);

        return extension;
    }

    public static float getSize(File dir) {
        float length = 0;
        File[] files = dir.listFiles();

        int count = files.length;

        for (int i = 0; i < count; i++) {
            if (files[i].isFile()) {
                length += files[i].length();
            }
            else {
                length += getSize(files[i]);
            }
        }
        return length;
    }

    public static float getSizeMb(File dir) {
        float i = getSize(dir);
        return i / 1000000;
    }
    //endregion
    //region ALTERNATE METHODS
    // Next method is alternate getSizeMb, gets size of directory which is always 4096 bytes
    /*public static int getSizeMb(File file) {
        System.out.println(file);
        System.out.println(file.length());
        int length = (int) file.length();
        return length / 1000000;
    }*/
    //endregion
}
