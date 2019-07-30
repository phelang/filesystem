package com.filesystem.filesystem.model;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FileTest {

    private Directory<String> home;

    @Before
    public void setUp() throws Exception {
        home = new Directory<>("home");
    }

    @Test
    public void testThatFileIsCreated() {

        Directory<String> user = home
                .addDirectory(new Directory<>("hanlie"));

        Directory<String> movies= new Directory<>("Movies");
        user.addDirectory(movies);

        home.createFile(movies, "movie1.mov", ".mov");
        home.createFile(movies, "movie2.mov", ".mov");
        home.createFile(movies, "movie3.mov", ".mov");


        Assert.assertEquals(3, movies.getFiles().size());

        String appender1 = " ";
        home.printDirectoryTree(home, appender1);

        System.out.println();
        JSONObject json = toJSON(home);
        System.out.println(json.toString());
    }

    @Test
    public void testThatFileIsFound() {

        Directory<String> user = home
                .addDirectory(new Directory<>("hanlie"));

        Directory<String> movies= new Directory<>("Movies");

        user.addDirectory(movies);

        home.createFile(movies, "movie1.mov", ".mov");
        home.createFile(movies, "movie2.mov", ".mov");
        home.createFile(movies, "movie3.mov", ".mov");

        File file = home.searchFile(home, "movie1.mov");


        Assert.assertNotNull(file);
        Assert.assertEquals("PATh", "home/hanlie/Movies/movie1.mov", file.getPath());

        String appender = " ";
        home.printDirectoryTree(home, appender);

    }

    @Test
    public void testThatFileIsDeleted() {

        Directory<String> user = home
                .addDirectory(new Directory<>("hanlie"));

        Directory<String> movies = new Directory<>("Movies");
        Directory<String> music = new Directory<>("Music");
        Directory<String> games = new Directory<>("Games");

        user.addDirectory(movies);
        user.addDirectory(music);
        user.addDirectory(games);

        home.createFile(movies, "movie1.mov", ".mov");
        home.createFile(movies, "movie2.mov", ".mov");
        home.createFile(movies, "movie3.mov", ".mov");

        home.createFile(games, "game1.exe", ".exe");
        home.createFile(games, "game2.exe", ".exe");

        File file1 = home.searchFile(home, "movie3.mov");
        File file2 = home.searchFile(home, "game2.exe");

        String appender = " ";
        home.printDirectoryTree(home, appender);

        Directory<String> findMovies = home.searchDirectory(home, "Movies");
        Directory<String> findGames = home.searchDirectory(home, "Games");

        Assert.assertTrue(home.deleteFile(findMovies, file1));

        Assert.assertTrue(home.deleteFile(findGames, file2));

        System.out.println();
        String appender2 = " ";
        home.printDirectoryTree(home, appender2);

    }

    @Test
    public void testThatFileIsUpdated() {

        Directory<String> user = home
                .addDirectory(new Directory<>("hanlie"));

        Directory<String> movies = new Directory<>("Movies");
        Directory<String> music = new Directory<>("Music");
        Directory<String> games = new Directory<>("Games");

        user.addDirectory(movies);
        user.addDirectory(music);
        user.addDirectory(games);

        home.createFile(movies, "movie1.mov", ".mov");
        home.createFile(movies, "movie2.mov", ".mov");
        home.createFile(movies, "movie3.mov", ".mov");

        File file1 = home.searchFile(home, "movie3.mov");

        String appender = " ";
        home.printDirectoryTree(home, appender);

        Directory<String> findMovies = home.searchDirectory(home, "Movies");
        String newName = "action_thriller.mov";
        File fileUpdated = file1;
        fileUpdated.setName(newName);
        fileUpdated.setPath(findMovies.getPath() + "/" + newName);

        Assert.assertEquals("action_thriller.mov", home.updateFile(findMovies, file1, fileUpdated).getName());

        String appender2 = " ";
        home.printDirectoryTree(home, appender2);
    }

    @Test
    public void testThatFileIsMoved() {

        Directory<String> user = home
                .addDirectory(new Directory<>("hanlie"));

        Directory<String> movies = new Directory<>("Movies");
        Directory<String> music = new Directory<>("Action");
        Directory<String> games = new Directory<>("Comedy");

        user.addDirectory(movies);
        user.addDirectory(music);
        user.addDirectory(games);

        home.createFile(movies, "movie1.mov", ".mov");
        home.createFile(movies, "movie2.mov", ".mov");
        home.createFile(movies, "movie3.mov", ".mov");

        File movie1 = home.searchFile(home, "movie1.mov");
        File movie2 = home.searchFile(home, "movie2.mov");

        // move file movie1.mov and movie2.mov to action directory
        Directory<String> movie1from = home.searchDirectory(home, movie1.getParentName());
        Directory<String> movie2from = home.searchDirectory(home, movie2.getParentName());

        Directory<String> action = home.searchDirectory(home, "Action");


        home.moveFile(movie1from, action, movie1);

        home.moveFile(movie1from, action, movie2);

        Assert.assertNull(movie1from.searchFile(movie1from, "movie1.mov"));
        Assert.assertNull(movie2from.searchFile(movie2from, "movie2.mov"));

        String appender2 = " ";
        home.printDirectoryTree(home, appender2);
    }

    public JSONObject toJSON(Directory<String> directory) {
        try {
            JSONObject json = new JSONObject();
            json.put("name", directory.getName());

            List subDirectories = new ArrayList<JSONObject>();

            List files = new ArrayList<JSONObject>();

            List<Directory<String>> directories = directory.getSubDirectories();

            for (Directory<String> subdir : directories) {

                if (isSubDirectoryOf(directory,subdir)) {
                    for(File f: subdir.getFiles()){
                        files.add(f.getName());
                    }
                    subDirectories.add(toJSON(subdir));
                }
            }
            json.put("directories", subDirectories);
            json.put("files", files);

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
