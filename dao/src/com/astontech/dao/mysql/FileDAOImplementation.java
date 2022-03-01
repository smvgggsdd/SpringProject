package com.astontech.dao.mysql;

import com.astontech.bo.BOFile;
import com.astontech.dao.FileDAO;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileDAOImplementation extends MySQL implements FileDAO {
    @Override
    public BOFile getFileById(int fileId) {
        Connect();
        BOFile file = null;
        try {
            String sp = "{call GetFile(?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, GET_BY_ID);
            cStmt.setInt(2, fileId);
            ResultSet rs = cStmt.executeQuery();

            if(rs.next()) {
                file = HydrateObject(rs);
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return file;
    }

    @Override
    public List<BOFile> getFileList() {
        Connect();
        List<BOFile> fileList = new ArrayList<>();
        try {
            String sp = "{call GetFile(?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, GET_COLLECTION);
            cStmt.setInt(2, 0);
            ResultSet rs = cStmt.executeQuery();
            while(rs.next()) {
                fileList.add(HydrateObject(rs));
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }

        return fileList;
    }

    @Override
    public int insertFile(BOFile file) {
        Connect();
        int id = 0;
        try {
            //call ExecuteFile(QueryId, FileId, FileName, FileType, FileSize, Path, DirId)
            String sp = "{call ExecuteFile(?,?,?,?,?,?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, INSERT);
            cStmt.setInt(2, 0);
            cStmt.setString(3, file.getFileName());
            cStmt.setString(4, file.getFileType());
            cStmt.setInt(5, file.getFileSize());
            cStmt.setString(6, file.getPath());
            cStmt.setString(7, file.getDirPath());

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
    public boolean updateFile(BOFile file) {
        Connect();
        int id = 0;
        try {
            //call ExecuteFile(QueryId, FileId, FileName, FileType, FileSize, Path, DirId)
            String sp = "{call ExecuteFile(?,?,?,?,?,?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, UPDATE);
            cStmt.setInt(2, file.getFileId());
            cStmt.setString(3, file.getFileName());
            cStmt.setString(4, file.getFileType());
            cStmt.setInt(5, file.getFileSize());
            cStmt.setString(6, file.getPath());
            cStmt.setString(7, file.getDirPath());

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
    public boolean deleteFile(int fileId) {
        Connect();
        int id = 0;
        try {
            //call ExecuteFile(QueryId, FileId, FileName, FileType, FileSize, Path, DirId)
            String sp = "{call ExecuteFile(?,?,?,?,?,?,?)}";
            CallableStatement cStmt = connection.prepareCall(sp);

            cStmt.setInt(1, DELETE);
            cStmt.setInt(2, fileId);
            cStmt.setString(3, "");
            cStmt.setString(4, "");
            cStmt.setInt(5, 0);
            cStmt.setString(6, "");
            cStmt.setInt(7, 0);

            ResultSet rs = cStmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException sqlEx) {
            logger.error(sqlEx);
        }
        return id > 0;
    }

    public static BOFile HydrateObject(ResultSet rs) throws SQLException {
        /*
            SELECT  fileId,         Index1  Int
                    fileName,       Index2  String
                    fileType,       Index3  String
                    fileSize,       Index4  Int
                    path,           Index5  String
                    dirId           Index6  Int
         */
        BOFile file = new BOFile();
        file.setFileId(rs.getInt(1));
        file.setFileName(rs.getString(2));
        file.setFileType(rs.getString(3));
        file.setFileSize(rs.getInt(4));
        file.setPath(rs.getString(5));
        file.setDirPath(rs.getString(6));

        return file;
    }
}
