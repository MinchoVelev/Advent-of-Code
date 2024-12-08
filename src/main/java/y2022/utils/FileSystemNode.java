package y2022.utils;

import java.util.LinkedList;
import java.util.List;


public class FileSystemNode {
    String name;
    boolean directory;
    long size;
    FileSystemNode parent;
    List<FileSystemNode> subnodes;


    FileSystemNode(String name, boolean directory, long size, FileSystemNode parent){
        this.name = name;
        this.directory = directory;
        this.size = size;
        this.parent = parent;
        if(directory){
            subnodes = new LinkedList<>();
        }
    }
    @Override
    public String toString() {
        return name;
    }
}
