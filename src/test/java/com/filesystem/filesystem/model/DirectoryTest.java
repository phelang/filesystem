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

        Assert.assertEquals("home", home.getHomeDirectory().getName());
    }

    @Test
    public void testThatRootDirectoryExistTraversingFromSubDirectories() {

        Directory<String> user = home
                .addDirectory(new Directory<>("henie"));

        // movies directory, path:home/movies
        Directory<String> movies = new Directory<>("movies");
        user.addDirectory(movies);

        // action sub directory of movies, path:home/movies/action
        Directory<String> action = new Directory<>("action");
        movies.addDirectory(action);


        // Get home directory from path:home/movies
        Assert.assertEquals("home", movies.getHomeDirectory().getName());

        // Get home directory from path:home/movies/action
        Assert.assertNotEquals("action", action.getHomeDirectory().getName());

        Assert.assertEquals("home", action.getHomeDirectory().getName());

    }

    @Test
    public void testThatSubDirectoryIsCreated() {

        Directory<String> user = home
                .addDirectory(new Directory<>("henie"));

        user.addDirectory(new Directory<>("Movies"));
        user.addDirectory(new Directory<>("Music"));
        user.addDirectory(new Directory<>("Games"));

        // home sub directories
        Assert.assertEquals(1, home.getSubDirectories().size());

        // user parent directory
        Assert.assertEquals(home, user.getParent());

        // sub directories size
        Assert.assertEquals(3, user.getSubDirectories().size());
    }

    @Test
    public void testThatADirectoryIsFound() {

        Directory<String> subDir = home
                .addDirectory(new Directory<>("henie"));

        subDir.addDirectory(new Directory<>("Movies"));
        subDir.addDirectory(new Directory<>("Music"));
        subDir.addDirectory(new Directory<>("Games"));


        Directory<String> find = subDir.searchDirectory(subDir, "Music");

        Assert.assertTrue(find.getParent().getName().equals("henie"));
        Assert.assertEquals("Music",find.getName());
    }

    @Test
    public void testThatDirectoryIsDeleted() {

        Directory<String> subDir = home
                .addDirectory(new Directory<>("henie"));

        subDir.addDirectory(new Directory<>("Movies"));
        subDir.addDirectory(new Directory<>("Music"));
        subDir.addDirectory(new Directory<>("Games"));

        Directory<String> find = subDir.searchDirectory(subDir, "Games");
        Directory<String> deletedDir = find.deleteDirectory();

        Assert.assertNull(subDir.searchDirectory(subDir, deletedDir.getName()));

    }

    @Test
    public void testThatDirectoryIsUpdated() {

        Directory<String> subDir = home
                .addDirectory(new Directory<>("henie"));

        subDir.addDirectory(new Directory<>("Movies"));
        subDir.addDirectory(new Directory<>("Music"));
        subDir.addDirectory(new Directory<>("Games"));

        Directory<String> findForUpdate = subDir.searchDirectory(subDir, "Games");
        Directory<String> updatedDir = findForUpdate.updateDirectory("Hobbies");


        /**
         * findForUpdate directory was updated hence it should not exist as 'Games' because the name was updated to 'Hobbies'
         *
         * NOTE: Because java objects points to object reference by default
         * the values of objects findForUpdate and updateDir will point
         * to the same object reference hence we use liral values insted of getName() method
         */
        Assert.assertNull(subDir.searchDirectory(subDir, "Games"));
        Assert.assertEquals("Hobbies", updatedDir.getName());

        Directory<String> findUpdated = subDir.searchDirectory(subDir, "Hobbies");

        Assert.assertEquals("Hobbies", findUpdated.getName());
    }
}
