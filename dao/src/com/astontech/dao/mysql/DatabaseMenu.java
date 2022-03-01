package com.astontech.dao.mysql;

import com.astontech.bo.BODirectory;
import com.astontech.bo.BOFile;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMenu extends MySQL{

    //region PROPERTIES
    public static final int GET_DIR_MOST_FILES = 10;
    public static final int GET_LARGEST_DIR = 20;
    public static final int GET_LARGEST_FILES = 30;
    public static final int GET_ALL_FILES_BY_TYPE = 40;
    public static final int CLEAR_DB = 50;
    //endregion

    //region METHODS
    public static BODirectory GetDirectory(int QueryId) {
        Connect();
        BODirectory boDirectory = null;
        try {
            String sp = "{call ExecuteAssessment(?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, QueryId);
            cStmt.setString(2, "");

            ResultSet rs = cStmt.executeQuery();

            if (rs.next()) {
                boDirectory = HydrateDirectory(rs);
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return boDirectory;
    }

    public static List<BOFile> GetFiveLargestFiles() {
        List<BOFile> boFiles = new ArrayList<>();
        try {
            String sp = "{call ExecuteAssessment(?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, GET_LARGEST_FILES);
            cStmt.setString(2, "");
            ResultSet rs = cStmt.executeQuery();

            while (rs.next()) {
                boFiles.add(HydrateFile(rs));
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return boFiles;
    }


    public static List<BOFile> GetAllFilesByType(String type) {
        List<BOFile> boFiles = new ArrayList<>();
        try {
            String sp = "{call ExecuteAssessment(?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, GET_ALL_FILES_BY_TYPE);
            cStmt.setString(2, type);
            ResultSet rs = cStmt.executeQuery();

            while (rs.next()) {
                boFiles.add(HydrateFile(rs));
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return boFiles;
    }

    public static void ClearDB() {
        Connect();
        try {
            String sp = "{call ExecuteAssessment(?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, CLEAR_DB);
            cStmt.setString(2, "");
            cStmt.executeQuery();
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return;
    }

    public static BODirectory HydrateDirectory(ResultSet rs) throws SQLException {
        /*
            SELECT      dirId,              Index1  int
                        dirName,            Index2  String
                        dirSize,            Index3  Float
                        numberOfFiles,      Index4  int
                        path                Index5  String
         */
        BODirectory directory = new BODirectory();
        directory.setDirName(rs.getString(1));
        directory.setDirSize(rs.getFloat(2));
        directory.setNumberOfFiles(rs.getInt(3));
        directory.setPath(rs.getString(4));

        return directory;
    }

    public static BOFile HydrateFile(ResultSet rs) throws SQLException {
        /*
            SELECT      fileId              Index1  int,
                        fileName            Index2  String
                        fileType            Index3  String
                        fileSize            Index4  Int
                        path                Index5  String
                        dirPath             Index6  String     MIGHT WANT TO CHANGE
         */
        BOFile boFile = new BOFile();
        boFile.setFileId(rs.getInt(1));
        boFile.setFileName(rs.getString(2));
        boFile.setFileType(rs.getString(3));
        boFile.setFileSize(rs.getInt(4));
        boFile.setPath(rs.getString(5));
        boFile.setDirPath(rs.getString(6));
        return boFile;
    }
    //endregion
}
