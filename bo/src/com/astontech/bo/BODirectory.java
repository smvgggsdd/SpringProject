package com.astontech.bo;

public class BODirectory {

    //region PROPERTIES
    private String dirName;
    private float dirSize;
    private int numberOfFiles;
    private String path;
    //endregion

    //region GETTERS / SETTERS

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public float getDirSize() {
        return dirSize;
    }

    public void setDirSize(float dirSize) {
        this.dirSize = dirSize;
    }

    public int getNumberOfFiles() {
        return numberOfFiles;
    }

    public void setNumberOfFiles(int numberOfFiles) {
        this.numberOfFiles = numberOfFiles;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    //endregion
}
