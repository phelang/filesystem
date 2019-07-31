package com.filesystem.controller.moveDirectory_rq_rs;

public class MoveDirectoryRequest {

    private String moveDirectoryName;
    private String toDirectoryName;

    public  MoveDirectoryRequest(){

    }

    public MoveDirectoryRequest(String moveDirectoryName, String toDirectoryName) {
        this.moveDirectoryName = moveDirectoryName;
        this.toDirectoryName = toDirectoryName;
    }

    public String getMoveDirectoryName() {
        return moveDirectoryName;
    }

    public void setMoveDirectoryName(String moveDirectoryName) {
        this.moveDirectoryName = moveDirectoryName;
    }

    public String getToDirectoryName() {
        return toDirectoryName;
    }

    public void setToDirectoryName(String toDirectoryName) {
        this.toDirectoryName = toDirectoryName;
    }
}
