package y2022;

import java.util.Scanner;

import y2022.utils.FileSystem;
import y2022.utils.IOUtils;

public class Dec7 {
    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        try (Scanner scanner = IOUtils.getScanner("Dec7.input", Dec7.class)) {
            do{
                String line = scanner.nextLine();
                //System.out.println(line);
                if(line.startsWith("$ cd ")){
                    fs.cd(line.substring(5));
                }else if(line.startsWith("$ ls")){
                    // do nothing
                }else{
                    String[] parts = line.split(" ");

                    if("dir".equals(parts[0])){
                        fs.addDir(parts[1]);
                    }else{
                        fs.addFile(parts[1], Long.parseLong(parts[0]));
                    }
                }
            }
            while(scanner.hasNextLine());
        }
        //fs.printAllDirs();
        System.out.println("Part 1: ");
        fs.findDirsBySize(l -> l <= 100000l);

        System.out.println("");
        System.out.println("--------------------");
        System.out.println("");
        System.out.println("Part 2: ");
        
        long totalSizeOfDirs = fs.totalSize();
        long fsMax = 70000000l;
        long free = fsMax - totalSizeOfDirs;
        long deficit = 30000000l - free;

        fs.findDirsBySize(l -> l >= deficit);
        System.out.println("");
        System.out.println("Size of all dirs " + totalSizeOfDirs);
        System.out.println("Deficit is " + deficit);
        System.out.println("Free space is " + free);

    }
}
