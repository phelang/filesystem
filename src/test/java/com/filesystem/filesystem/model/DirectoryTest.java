package com.filesystem.filesystem.model;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

        String appender1 = " ";
        home.printDirectoryTree(home, appender1);

        System.out.println();
        JSONObject json = toJSON(home);
        System.out.println(json.toString());

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


        String appender1 = " ";
        home.printDirectoryTree(home, appender1);

        System.out.println();
        JSONObject json = toJSON(home);
        System.out.println(json.toString());
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

        String appender1 = " ";
        home.printDirectoryTree(home, appender1);

        System.out.println();
        JSONObject json = toJSON(home);
        System.out.println(json.toString());

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

        String appender1 = " ";
        home.printDirectoryTree(home, appender1);

        System.out.println();
        JSONObject json = toJSON(home);
        System.out.println(json.toString());
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

        String appender1 = " ";
        home.printDirectoryTree(home, appender1);

        System.out.println();
        JSONObject json = toJSON(home);
        System.out.println(json.toString());
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

        String appender1 = " ";
        home.printDirectoryTree(home, appender1);

        System.out.println();
        JSONObject json = toJSON(home);
        System.out.println(json.toString());
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

        String appender1 = " ";
        home.printDirectoryTree(home, appender1);

        System.out.println();
        JSONObject json = toJSON(home);
        System.out.println(json.toString());
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
         * Check the number of sub directories in home/hanlie/To WatchMovies
         * AND
         * Check that movies EXIST in 'home/hanlie/To Watch Movies' with path 'home/hanlie/To Watch Movies/Movies'
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
                "home/hanlie/To Watch Movies/Movies",
                toWatchMovies
                        .getSubDirectories().get(0)
                        .getPath());

        Assert.assertEquals(
                "home/hanlie/To Watch Movies/Movies/Action",
                "Action",
                toWatchMovies
                        .getSubDirectories().get(0)
                        .getSubDirectories().get(0)
                        .getName());
        Assert.assertEquals(
                "home/hanlie/To Watch Movies/Movies/Action",
                toWatchMovies
                        .getSubDirectories().get(0)
                        .getSubDirectories().get(0)
                        .getPath());

        String appender1 = " ";
        home.printDirectoryTree(home, appender1);

        System.out.println();
        JSONObject json = toJSON(home);
        System.out.println(json.toString());

    }

    // TO:DO
    @Test
    public void testThatDirectoryIsCopied() {

        Directory<String> users = home
                .addDirectory(new Directory<>("users"));

        /**
         * Users
         */
        Directory<String> hanlie = new Directory<>("hanlie");
        Directory<String> phelang = new Directory<>("phelang");

        users.addDirectory(hanlie);
        users.addDirectory(phelang);

        /**
         *  Hanlie Directories
         */
        Directory<String> moviesH = new Directory<>("Movies");
        Directory<String> actionH = new Directory<>("Action");
        Directory<String> comedyH = new Directory<>("Comedy");


        Directory<String> sci_fiH = new Directory<>("Sci-Fi & Fantasy");
        Directory<String> action_romanceH = new Directory<>("Action Romance");
        Directory<String> action_comedyH = new Directory<>("Action Comedy");

        hanlie.addDirectory(moviesH);

        moviesH.addDirectory(actionH);
        moviesH.addDirectory(comedyH);

        actionH.addDirectory(sci_fiH);
        actionH.addDirectory(action_romanceH);
        actionH.addDirectory(action_comedyH);

        /**
         * Phelang Directories
         */

        Directory<String> moviesP = new Directory<>("Movies");
        Directory<String> actionP = new Directory<>("Action");

        Directory<String> musicP = new Directory<>("Music");
        Directory<String> classicalP = new Directory<>("Classical");
        Directory<String> rockP = new Directory<>("Rock");

        phelang.addDirectory(moviesP);
        phelang.addDirectory(musicP);

        moviesP.addDirectory(actionP);

        musicP.addDirectory(classicalP);
        musicP.addDirectory(rockP);
    }

    public JSONObject toJSON(Directory<String> directory) {
        try {
            JSONObject json = new JSONObject();
            json.put("name", directory.getName());

            List subDirectories = new ArrayList<JSONObject>();

            List<Directory<String>> directories = directory.getSubDirectories();

            for (Directory<String> subdir : directories) {

                if (isSubDirectoryOf(directory,subdir)) {
                    subDirectories.add(toJSON(subdir));
                }
            }
            json.put("directories", subDirectories);

            return json;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private boolean isSubDirectoryOf(Directory<String> parentDirectory, Directory<String> subDirectory){
        if(subDirectory.getParent().equals(parentDirectory)){
            return true;
        } else {
            return false;
        }
    }
}
