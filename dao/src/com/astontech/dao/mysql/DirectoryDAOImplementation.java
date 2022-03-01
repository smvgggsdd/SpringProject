package com.astontech.dao.mysql;
import com.astontech.bo.BODirectory;
import com.astontech.dao.DirectoryDAO;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DirectoryDAOImplementation extends MySQL implements DirectoryDAO {
    @Override
    public BODirectory getDirectoryById(String path){
        Connect();
        BODirectory directory = null;
        try {
            String sp = "{call GetDirectory(?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, GET_BY_ID);
            cStmt.setString(2, path);
            ResultSet rs = cStmt.executeQuery();

            if (rs.next()) {
                directory = HydrateObject(rs);
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return directory;
    }

    @Override
    public List<BODirectory> getDirectoryList() {
        Connect();
        List<BODirectory> directoryList = new ArrayList<BODirectory>();
        try {
            String sp = "{call GetDirectory(?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, GET_COLLECTION);
            cStmt.setString(2, "");
            ResultSet rs = cStmt.executeQuery();

            if (rs.next()) {
                directoryList.add(HydrateObject(rs));
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return directoryList;
    }

    @Override
    public int insertDirectory(BODirectory directory) {
        Connect();
        int id = 0;
        try {
            //call ExecuteDirectory(QueryId, vDirId, vDirName, vDirSize, vNumberOfFiles, vPath)
            String sp = "{call ExecuteDirectory(?,?,?,?,?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, INSERT);
            cStmt.setInt(2, 0);
            cStmt.setString(3, directory.getDirName());
            cStmt.setFloat(4, directory.getDirSize());
            cStmt.setInt(5, directory.getNumberOfFiles());
            cStmt.setString(6, directory.getPath());

            ResultSet rs = cStmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return id;
    }

    @Override
    public boolean updateDirectory(BODirectory directory) {
        Connect();
        int id = 0;
        try {
            String sp = "{call ExecuteDirectory(?,?,?,?,?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, UPDATE);
            cStmt.setInt(2, 0);
            cStmt.setString(3, directory.getDirName());
            cStmt.setFloat(4, directory.getDirSize());
            cStmt.setInt(5, directory.getNumberOfFiles());
            cStmt.setString(6, directory.getPath());

            ResultSet rs = cStmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return id > 0;
    }

    @Override
    public boolean deleteDirectory(String path) {
        Connect();
        int id = 0;
        try {
            String sp = "{call ExecuteDirectory(?,?,?,?,?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, DELETE);
            cStmt.setInt(2, 0);
            cStmt.setString(3, "");
            cStmt.setFloat(4, 0);
            cStmt.setInt(5, 0);
            cStmt.setString(6, path);

            ResultSet rs = cStmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return id > 0;
    }

    public static BODirectory HydrateObject(ResultSet rs) throws SQLException {
        /*
            SELECT  a.dirId,            index1 int
                    a.dirName,          index2 String
                    a.dirSize,          index3 float
                    a.numberOfFiles,    index4 String
                    a.path              index5 String
         */
        BODirectory directory = new BODirectory();
        directory.setDirName(rs.getString(1));
        directory.setDirSize(rs.getFloat(2));
        directory.setNumberOfFiles(rs.getInt(3));
        directory.setPath(rs.getString(4));

        return directory;
    }
}
