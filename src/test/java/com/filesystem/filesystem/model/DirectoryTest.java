package com.filesystem.filesystem.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DirectoryTest {

    private Directory<String> home;

    @Before
    public void setUp() throws Exception {
        home = new Directory<>("home");
    }

    @Test
    public void testThatRootDirectoryIsCreated() {

    }

    @Test
    public void testThatSubDirectoryIsCreated() {

        Directory<? extends String> subDir = home
                .addDirectory(new Directory<>("henie"));

        Assert.assertEquals(1, home.getSubDirectories().size());

        // Assert.assertEquals(home.getSubDirectories()); Get the created child and assert name
    }
}