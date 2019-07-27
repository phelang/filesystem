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
    public void testDirectoryLevel() {

        Directory<String> user = home
                .addDirectory(new Directory<>("hanlie"));

        Directory<String> movies = new Directory<>("Movies");
        Directory<String> action = new Directory<>("Action");

        user.addDirectory(movies);
        movies.addDirectory(action);

        Assert.assertEquals("home", 0, home.getLevel());
        Assert.assertEquals("home/user", 1, user.getLevel());
        Assert.assertEquals("home/user/Movies", 2, movies.getLevel());
        Assert.assertEquals("home/user/Movies/Action", 3, action.getLevel());

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

    @Test
    public void testThatDirectoryIsMoved() {

        Directory<String> user = home
                .addDirectory(new Directory<>("hanlie"));

        /**
         *  Hanlie Directories
         */
        Directory<String> movies = new Directory<>("Movies");
        Directory<String> action = new Directory<>("Action");

        Directory<String> music = new Directory<>("Music");
        Directory<String> classical = new Directory<>("Classical");
        Directory<String> rock = new Directory<>("Rock");

        user.addDirectory(movies);
        user.addDirectory(music);

        movies.addDirectory(action);

        music.addDirectory(classical);
        music.addDirectory(rock);


        /**
         * Move Movies directory to sub directory Action
         *
         * Process not allowed
         */

        boolean isMoved = movies.moveDirectory(action);

        Assert.assertFalse("Move : home/hanlie/movies -> home/hanlie/Movies/Action", isMoved);


        /*******************************************************
         * *****************************************************
         * Create a new directory that Movies will be moved To *
         * *****************************************************
         *******************************************************/

        Directory<String> toWatchMovies = new Directory<>("To Watch Movies");
        user.addDirectory(toWatchMovies); // add directory to user

        isMoved = movies.moveDirectory(toWatchMovies);

        Assert.assertTrue("home/hanlie/movies -> home/hanlie/To Watch Movies", isMoved);

        /**
         * Check that movies NO LONGER exist in home/hanlie/movies
         * AND
         * Check that movies EXIST in home/hanlie/To Watch Movies
         * AND
         * Check that the sub directories of movies exist
         */

        Assert.assertEquals(1, toWatchMovies.getSubDirectories().size());

        Assert.assertEquals(
                "home/hanlie/To Watch Movies/Movies",
                "Movies",
                toWatchMovies
                        .getSubDirectories().get(0)
                        .getName());

        Assert.assertEquals(
                "home/hanlie/To Watch Movies/Movies/Action",
                "Action",
                toWatchMovies
                        .getSubDirectories().get(0)
                        .getSubDirectories().get(0)
                        .getName());
    }
}
