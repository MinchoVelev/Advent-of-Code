package y2022.utils;

import java.util.function.Predicate;

public class FileSystem {
    FileSystemNode root;
    FileSystemNode currDir;
    // Map<String, FileSystemNode> dirMap;

    public FileSystem() {
        // dirMap = new HashMap<>();
        root = new FileSystemNode("/", true, 0l, null);
        root.parent = root;
        currDir = root;
    }

    public long totalSize() {
        return root.size;
    }

    public void addDir(String dirName) {
        currDir.subnodes.add(new FileSystemNode(dirName, true, 0l, currDir));
    }

    public void cd(String path) {
        if ("/".equals(path)) {
            currDir = root;
        } else if ("..".equals(path)) {
            currDir = currDir.parent;
        } else {
            for (FileSystemNode n : currDir.subnodes) {
                if (n.name.equals(path) && n.directory) {
                    currDir = n;
                    return;
                }
            }
            System.err.println("Curr dir: " + currDir.name);
            System.err.print(currDir.subnodes);
            throw new RuntimeException("No such directory " + path);
        }
    }

    public void addFile(String fileName, long fileSize) {
        currDir.subnodes.add(new FileSystemNode(fileName, false, fileSize, currDir));
        FileSystemNode tmp = currDir;
        boolean rootUpdated = false;
        do {
            tmp.size += fileSize;
            tmp = tmp.parent;
            if (tmp.parent == tmp) {
                tmp.size += fileSize;
                rootUpdated = true;
            }
        } while (!rootUpdated);
    }

    public void printAllDirs() {
        printIfDir(root, 0);
    }

    class Counter {
        long sum = 0l;
        FileSystemNode min = root;
    }

    public long findDirsBySize(Predicate<Long> p) {
        Counter c = new Counter();
        findDirBySize(root, p, c);
        System.out.println("Total size of matching " + c.sum);
        System.out.println("Smallest dir " + c.min.name + " with size " + c.min.size);
        return c.sum;
    }

    private void findDirBySize(FileSystemNode node, Predicate<Long> p, Counter c) {
        if (!node.directory) {
            return;
        }
        if (p.test(node.size)) {
            // System.out.println(node.size + "\t" + node.name);
            c.sum += node.size;
            if (node.size < c.min.size) {
                c.min = node;
            }
        }

        for (FileSystemNode n : node.subnodes) {
            findDirBySize(n, p, c);
        }
    }

    private void printIfDir(FileSystemNode node, int depth) {
        if (!node.directory) {
            return;
        }
        System.out.print(node.size + "\t\t");
        for (int i = depth; i >= 0; i--) {
            System.out.print("  ");
        }
        System.out.println(node.name);

        for (FileSystemNode n : node.subnodes) {
            printIfDir(n, depth + 1);
        }
    }

}
