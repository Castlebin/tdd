package com.heller.os;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class CleanMavenLocalRepo {

    @Test
    public void doClean() {
        String mavenLocalRepoBasePath = "/Users/YX/.m2/repository";
        String[] sudDirPaths = new String[]{"acfun", "kuaishou", "com/kuaishou"};
        Set<String> subDirs = new HashSet<>();
        Arrays.stream(sudDirPaths)
                .forEach(sudDirPath -> subDirs.add(mavenLocalRepoBasePath + "/" + sudDirPath));
        cleanMavenLocalRepo(subDirs);
    }

    public void cleanMavenLocalRepo(Iterable<String> subDirs) {
        subDirs.forEach(this::doCleanDir);
    }

    private void doCleanDir(String subDir) {
        File subDirFile = new File(subDir);
        if (!subDirFile.exists() || !subDirFile.isDirectory()) {
            System.out.println("文件不存在或者不是文件夹：" + subDir);
            return;
        }
        if (isDirOnlyContainsFile(subDirFile)) {
            // 找出自己的兄弟目录
            File parentFile = subDirFile.getParentFile();
        //    System.out.println("坐标：" + parentFile.getAbsolutePath());
            File[] siblings = parentFile.listFiles();
            List<File> releaseList = new ArrayList<>();
            List<File> snapshotList = new ArrayList<>();
            Arrays.stream(siblings)
                    .forEach(sibling -> {
                        if (sibling.getName().toLowerCase().contains("-snapshot")) {
                            snapshotList.add(sibling);
                        } else {
                            releaseList.add(sibling);
                        }
                    });
            // 按修改时间倒序排列，保留最新的版本
            Comparator<File> comparator = (f1, f2) -> (int)(f2.lastModified()- f1.lastModified());
            releaseList.sort(comparator);
            snapshotList.sort(comparator);
            for (int i = 1; i < releaseList.size(); i++) {
                File file = releaseList.get(i);
                deleteFile(file);
                System.out.println("删除文件：" + file.getAbsolutePath());
            }
            for (int i = 1; i < snapshotList.size(); i++) {
                File file = snapshotList.get(i);
                deleteFile(file);
                System.out.println("删除文件：" + file.getAbsolutePath());
            }
        } else if (subDirFile.exists()) {
            File[] dirs = subDirFile.listFiles();
            Arrays.stream(dirs).forEach(dir -> doCleanDir(dir.getAbsolutePath()));
        }
    }

    /**
     * 先根遍历序递归删除文件夹
     *
     * @param dirFile 要被删除的文件或者目录
     * @return 删除成功返回true, 否则返回false
     */
    public static boolean deleteFile(File dirFile) {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {
            for (File file : dirFile.listFiles()) {
                deleteFile(file);
            }
        }

        return dirFile.delete();
    }

    /**
     * 是否是一个只包含文件的文件夹
     */
    private boolean isDirOnlyContainsFile(File path) {
        if (!path.exists()) {
            return false;
        }
        if (!path.isDirectory()) {
            return false;
        }
        File[] subFiles = path.listFiles();
        for (File subFile : subFiles) {
            if (subFile.isDirectory()) {
                return false;
            }
        }
        return true;
    }

}