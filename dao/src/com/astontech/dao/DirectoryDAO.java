package com.astontech.dao;
import com.astontech.bo.BODirectory;

import java.util.List;

public interface DirectoryDAO {
    //notes:    GET METHODS
    public BODirectory getDirectoryById(String path);
    public List<BODirectory> getDirectoryList();

    //notes:    EXECUTE METHODS
    public int insertDirectory(BODirectory directory);
    public boolean updateDirectory(BODirectory directory);
    public boolean deleteDirectory(String path);
}
