package com.heller.os;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;

public class CleanMavenLocalRepo {

    private static final long ONE_MIN = 60 * 1000;
    private static final long ONE_DAY = 24 * 60 * ONE_MIN;

    private static final long CLEAN_INTERVAL = 15 * ONE_DAY;

    private static final Set<String> KUAISHOU_ROOT_POM = new HashSet<>();

    // .m2/repository/com/kuaishou/infra/boot/ks-boot-root-pom/
    // .m2/repository/kuaishou/kuaishou-root-pom/
    static {
        KUAISHOU_ROOT_POM.add("kuaishou-root-pom");
        KUAISHOU_ROOT_POM.add("ks-boot-root-pom");
    }

    @Test
    public void doClean() {
        String mavenLocalRepoBasePath = "/Users/YX/.m2/repository";
        String[] sudDirPaths = new String[] {"acfun", "kuaishou", "com/kuaishou"};
        Set<String> subDirs = new HashSet<>();
        Arrays.stream(sudDirPaths)
                .forEach(sudDirPath -> subDirs.add(mavenLocalRepoBasePath + "/" + sudDirPath));
        cleanMavenLocalRepo(subDirs);
    }

    public void cleanMavenLocalRepo(Collection<String> subDirs) {
        Set<File> scanDirs = subDirs.stream()
                .map(File::new)
                .filter(item -> item.exists() && item.isDirectory())
                .collect(Collectors.toSet());
        scanDirs.forEach(this::doCleanDir);
    }

    private void doCleanDir(File subDir) {
        if (isDirOnlyContainsFile(subDir)) {
            // 找出自己的兄弟目录
            File parentFile = subDir.getParentFile();
            System.out.println("扫描到坐标：" + parentFile.getAbsolutePath());
            File[] siblings = parentFile.listFiles();
            List<File> releaseList = new ArrayList<>();
            List<File> snapshotList = new ArrayList<>();
            Arrays.stream(siblings)
                    .forEach(sibling -> {
                        if (!sibling.isDirectory()) {
                            return;
                        }
                        if (sibling.getName().toLowerCase().contains("-snapshot")) {
                            snapshotList.add(sibling);
                        } else {
                            releaseList.add(sibling);
                        }
                    });
            // 按修改时间倒序排列，保留最新的版本
            Comparator<File> comparator = Comparator.comparingLong(File::lastModified);
            releaseList.sort(comparator);
            snapshotList.sort(comparator);

            if (releaseList.size() > 1) {
                System.out.println("releaseList: " + Arrays.toString(releaseList.toArray()));
            }
            if (snapshotList.size() > 0) {
                System.out.println("snapshotList: " + Arrays.toString(snapshotList.toArray()));
            }

            for (int i = 0; i < releaseList.size() - 1; i++) {
                File file = releaseList.get(i);
                deleteFile(file);
            }
            for (int i = 0; i < snapshotList.size() - 1; i++) {
                File file = snapshotList.get(i);
                deleteFile(file);
            }
            snapshotList.forEach(snapshot -> {
                Set<File> files = ArrayUtil.isEmpty(snapshot.listFiles()) ? new HashSet<>() :
                                  Arrays.stream(snapshot.listFiles())
                                          .filter(File::isFile)
                                          .collect(Collectors.toSet());
                List<File> metaInfo =
                        files.stream().filter(file -> file.getName().contains("_remote.repositories"))
                                .collect(Collectors.toList());
                if (CollUtil.isNotEmpty(metaInfo)) {
                    long lastModified = metaInfo.get(0).lastModified();
                    Date lastModifiedDay = DateUtil.beginOfDay(DateUtil.date(lastModified).toJdkDate()).toJdkDate();
                    files.forEach(file -> {
                        Date modifiedDay =
                                DateUtil.beginOfDay(DateUtil.date(file.lastModified()).toJdkDate()).toJdkDate();
                        if (modifiedDay.compareTo(lastModifiedDay) < 0) {
                            System.out.println("删除快照文件：" + file.getAbsolutePath());
                            file.delete();
                        }
                    });
                }
            });
        } else {
            File[] dirs = subDir.listFiles();
            if (dirs != null && dirs.length > 0) {
                Arrays.stream(dirs)
                        .filter(item -> item.exists() && item.isDirectory())
                        .forEach(this::doCleanDir);
            }
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
        if (!dirFile.exists() || isProtectedDir(dirFile)) {
            return false;
        }

        if (dirFile.isFile()) {
            // 下载下来还没有一天时间的文件，不删除
            if (System.currentTimeMillis() - dirFile.lastModified() < CLEAN_INTERVAL) {
                System.out.println("文件下载下来时间较短，暂不删除：" + dirFile.getAbsolutePath());
                return false;
            }

            //    System.out.println("删除文件：" + dirFile.getAbsolutePath());
            return dirFile.delete();
        } else {
            for (File file : dirFile.listFiles()) {
                deleteFile(file);
            }
        }

        if (dirFile.isDirectory() && ArrayUtil.isEmpty(dirFile.list())) {
            System.out.println("删除文件夹：" + dirFile.getAbsolutePath());
        }
        return dirFile.delete();
    }

    public static boolean isProtectedDir(File dirFile) {
        String absolutePath = dirFile.getAbsolutePath();
        for (String name : KUAISHOU_ROOT_POM) {
            if (absolutePath.contains(name)
                    && absolutePath.toLowerCase().contains("-snapshot")) {
                return true;
            }
        }
        return false;
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
