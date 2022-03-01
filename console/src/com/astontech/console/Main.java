package com.astontech.console;
import com.astontech.bo.BODirectory;
import com.astontech.bo.BOFile;
import com.astontech.dao.DirectoryDAO;
import com.astontech.dao.FileDAO;
import com.astontech.dao.mysql.DirectoryDAOImplementation;
import com.astontech.dao.mysql.FileDAOImplementation;
import org.apache.log4j.Logger;
import common.helpers.Helpers;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import com.astontech.dao.mysql.DatabaseMenu;

public class Main {

    final static Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        FinalAssessment();
    }

    private static void FinalAssessment() {
        File dir = QueryForDir();
        RecursiveSearch(dir);
        int choice = RePromptUser();
        ChooseOption(choice);
    }
    private static void ChooseOption(int choice) {
        BODirectory directory = new BODirectory();
        switch (choice) {
            case 1:
                directory = DatabaseMenu.GetDirectory(DatabaseMenu.GET_DIR_MOST_FILES);
                System.out.println("Directory Name: " + directory.getDirName());
                System.out.println("Size(MB): " + directory.getDirSize());
                System.out.println("Number Of Files: " + directory.getNumberOfFiles());
                System.out.println("Path: " + directory.getPath());
                DatabaseMenu.ClearDB();
                break;

            case 2:
                directory = DatabaseMenu.GetDirectory(DatabaseMenu.GET_LARGEST_DIR);
                System.out.println("Directory Name: " + directory.getDirName());
                System.out.println("Size(MB): " + directory.getDirSize());
                System.out.println("Number Of Files: " + directory.getNumberOfFiles());
                System.out.println("Path: " + directory.getPath());
                DatabaseMenu.ClearDB();
                break;

            case 3:
                List<BOFile> boFileList = DatabaseMenu.GetFiveLargestFiles();

                System.out.println("5 Largest Files: ");

                for (BOFile boFile : boFileList) {
                    System.out.println("=================");
                    System.out.println("File Name: " + boFile.getFileName());
                    System.out.println("File Type: " + boFile.getFileType());
                    System.out.println("File Size: " + boFile.getFileSize());
                    System.out.println("File Path: " + boFile.getPath());
                }
                DatabaseMenu.ClearDB();
                break;

            case 4:
                Scanner sc = new Scanner(System.in);
                System.out.println("Please enter file type");
                String type = sc.next();
                List<BOFile> boFileList1 = DatabaseMenu.GetAllFilesByType(type);

                for (BOFile boFile : boFileList1) {
                    System.out.println("=================");
                    System.out.println("File Name: " + boFile.getFileName());
                    System.out.println("File Type: " + boFile.getFileType());
                    System.out.println("File Size: " + boFile.getFileSize());
                    System.out.println("File Path: " + boFile.getPath());
                }
                DatabaseMenu.ClearDB();
                break;

            case 5:
                DatabaseMenu.ClearDB();
                System.out.println("Database Successfully Cleared");
                FinalAssessment();
                break;

            case 6:
                return;
        }
    }
    private static void Prompts() {
        System.out.println("Please select from the following options:");
        System.out.println("1: Display directory with most files");
        System.out.println("2: Display directory largest in size");
        System.out.println("3: Display 5 largest files in size");
        System.out.println("4: Display all files of a certain type");
        System.out.println("5: Clear the db and start over");
        System.out.println("6: Exit");
    }
    private static int RePromptUser() {
        Scanner sc = new Scanner(System.in);
        int number = 0;
        do {
            Prompts();
            while (!sc.hasNextInt()) {
                System.out.println("Please enter a valid number 1-6");
                sc.next();
            }
            number = sc.nextInt();
        } while (number < 1 || number > 6);
        System.out.println(number);
        return number;
    }
    private static void InsertDirectory(File file) {
        BODirectory boDirectory = new BODirectory();
        // Directory Name, Directory Size(MB), # files in Directory, Directory Path
        boDirectory.setDirName(file.getName());
        boDirectory.setDirSize(Helpers.getSizeMb(file));
        boDirectory.setNumberOfFiles(file.list().length);
        boDirectory.setPath(file.getPath());
        DirectoryDAO directoryDAO = new DirectoryDAOImplementation();
        int id = directoryDAO.insertDirectory(boDirectory);
    }
    private static void InsertFile(File file) {
        BOFile boFile = new BOFile();
        // file name, file type, file size, file path
        boFile.setFileName(file.getName());
        boFile.setFileType(Helpers.getExtension(file));
        boFile.setFileSize((int) file.length());
        boFile.setPath(file.getPath());
        boFile.setDirPath(file.getParentFile().getPath());
        FileDAO fileDAO = new FileDAOImplementation();
        int id = fileDAO.insertFile(boFile);
    }
    private static void RecursiveSearch(File dir) {

        File[] files = dir.listFiles();
        for(File file : files) {
            if(file.isDirectory()) {
                InsertDirectory(file);
                RecursiveSearch(file);
            } else {
                InsertFile(file);

            }
        }
    }
    private static File QueryForDir() {
        System.out.println("Please Enter A Directory");
        Scanner sc = new Scanner(System.in);
        File dir = new File(sc.next());
        return dir;
    }

    //region TEST METHODS
    private static Connection Test() {
        String dbHost = "localhost";
        String dbName = "final_assessment";
        String dbUser =  "root";       // other user is "consoleUser";
        String dbPass = "password";
        String useSSL = "false";
        String procBod = "true";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            logger.error("MYSQL Driver Not Found! " + ex);
            return null;
        }

        logger.info("MySQL Driver Registered.");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":3306/" + dbName + "?useSSL-" + useSSL + "&noAccessToProcedureBod=" + procBod, dbUser, dbPass);
        } catch (SQLException ex){
            logger.error("Connection failed!" + ex);
            return null;
        }

        if(connection != null) {
            logger.info("Successfully connected to MySQL database");
            return connection;
        } else {
            logger.info("Connection failed!");
            return null;
        }
    }
    //endregion
}
