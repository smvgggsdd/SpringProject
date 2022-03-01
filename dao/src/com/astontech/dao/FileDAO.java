package com.astontech.dao;
import com.astontech.bo.BOFile;

import java.util.List;

public interface FileDAO {
    //notes:    GET METHODS
    public BOFile getFileById(int fileId);
    public List<BOFile> getFileList();

    //notes:    EXECUTE METHODS
    public int insertFile(BOFile file);
    public boolean updateFile(BOFile file);
    public boolean deleteFile(int fileId);
}
