package com.example.SafeStep_be.config.liquibase;

import liquibase.resource.Resource;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;

public class LiquibaseResourceComparator implements Comparator<Object> {

    @Override
    public int compare(Object obj1, Object obj2) {
        String path1 = (obj1 instanceof Resource) ? ((Resource) obj1).getPath()
                : (obj1 instanceof String) ? (String) obj1
                : obj1.toString();
        String path2 = (obj2 instanceof Resource) ? ((Resource) obj2).getPath()
                : (obj2 instanceof String) ? (String) obj2
                : obj2.toString();

        int group1 = path1.contains("create") ? 1 : (path1.contains("insert") ? 2 : 3);
        int group2 = path2.contains("create") ? 1 : (path2.contains("insert") ? 2 : 3);

        if (group1 != group2) {
            return Integer.compare(group1, group2);
        }
        try {
            if (obj1 instanceof Resource && obj2 instanceof Resource) {
                URI uri1 = ((Resource) obj1).getUri();
                URI uri2 = ((Resource) obj2).getUri();
                if ("file".equals(uri1.getScheme()) && "file".equals(uri2.getScheme())) {
                    Path filePath1 = Paths.get(uri1);
                    Path filePath2 = Paths.get(uri2);
                    BasicFileAttributes attr1 = Files.readAttributes(filePath1, BasicFileAttributes.class);
                    BasicFileAttributes attr2 = Files.readAttributes(filePath2, BasicFileAttributes.class);
                    return attr1.creationTime().compareTo(attr2.creationTime());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return path1.compareTo(path2);
    }
}
