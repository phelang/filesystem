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
    public void testThatHomeDirectoryIsCreated() {

        Assert.assertEquals("home", home.getHomeDirectory().getName());

        Assert.assertEquals("home", home.getName());
        Assert.assertEquals("home", home.getPath());
        Assert.assertEquals(null ,home.getParent());

    }

    @Test
    public void testPathOfDirectories() {

        Directory<String> user = home
                .addDirectory(new Directory<>("hanlie"));

        Directory<String> movies = new Directory<>("Movies");
        Directory<String> action = new Directory<>("Action");

        user.addDirectory(movies);
        movies.addDirectory(action);

        Assert.assertEquals("home", home.getPath());
        Assert.assertEquals("home/hanlie", user.getPath());
        Assert.assertEquals("home/hanlie/Movies", movies.getPath());
        Assert.assertEquals("home/hanlie/Movies/Action", action.getPath());

    }

    @Test
    public void testThatHomeDirectoryExistFromAnyGivenSubDirectory() {

        Directory<String> user = home
                .addDirectory(new Directory<>("henie"));

        Directory<String> movies = new Directory<>("Movies");
        Directory<String> action = new Directory<>("Action");

        user.addDirectory(movies);
        movies.addDirectory(action);

        Assert.assertEquals("home", action.getHomeDirectory().getName());
    }

    @Test
    public void testThatSubDirectoriesAreCreated() {

        Directory<String> user = home
                .addDirectory(new Directory<>("hanlie"));

        user.addDirectory(new Directory<>("Movies"));
        user.addDirectory(new Directory<>("Music"));
        user.addDirectory(new Directory<>("Games"));

        // user directory
        Assert.assertEquals(home, user.getParent());
        Assert.assertEquals(1, home.getSubDirectories().size());

        // user sub directories
        Assert.assertEquals(3, user.getSubDirectories().size());
    }

    @Test
    public void testThatSearchDirectoryIsFound() {

        Directory<String> user = home
                .addDirectory(new Directory<>("hanlie"));

        user.addDirectory(new Directory<>("Movies"));
        user.addDirectory(new Directory<>("Music"));
        user.addDirectory(new Directory<>("Games"));


        Directory<String> findDirectory = user.searchDirectory(user, "Music");

        Assert.assertEquals("Music",findDirectory.getName());
        Assert.assertEquals("home/hanlie/Music", findDirectory.getPath());
        Assert.assertEquals("hanlie", findDirectory.getParent().getName());
    }

    @Test
    public void testThatDirectoryIsDeleted() {

        Directory<String> user = home
                .addDirectory(new Directory<>("hanlie"));

        user.addDirectory(new Directory<>("Movies"));
        user.addDirectory(new Directory<>("Music"));
        user.addDirectory(new Directory<>("Games"));

        Directory<String> findDirectory = user.searchDirectory(user, "Games");
        Directory<String> deletedDir = findDirectory.deleteDirectory();

        Assert.assertNull(user.searchDirectory(user, deletedDir.getName()));
    }

    @Test
    public void testThatDirectoryIsUpdated() {

        Directory<String> user = home
                .addDirectory(new Directory<>("hanlie"));

        user.addDirectory(new Directory<>("Movies"));
        user.addDirectory(new Directory<>("Music"));
        user.addDirectory(new Directory<>("Games"));

        Directory<String> findToUpdateDirectory = user.searchDirectory(user, "Games");
        Directory<String> updatedDirectory = findToUpdateDirectory.updateDirectory("Hobbies");


        /**
         * NOTE: Because java objects points to object reference by default
         * the values of objects findDirectory and updateDirectory will point
         * to the same object reference hence
         */
        Assert.assertNull(user.searchDirectory(user, "Games"));
        Assert.assertEquals("Hobbies", updatedDirectory.getName());
    }
}
