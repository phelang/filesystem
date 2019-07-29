package com.filesystem.filesystem.service;


import com.filesystem.filesystem.model.Directory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileSystemServiceTest {

    @Autowired
    private FileSystemService service;

    @Test
    public void testThatNewDirectoryIsCreated() {

        String home = service.addDirectory("home", "hanlie");
        String movies = service.addDirectory("hanlie", "Movies");
        String music = service.addDirectory("hanlie", "Music");
        String games = service.addDirectory("hanlie", "Games");


        Assert.assertEquals("success", home);
        Assert.assertEquals("success", movies);
        Assert.assertEquals("success", music);
        Assert.assertEquals("success", games);

        // Test that directories are created
        Directory<String> root = service.getRoot();

        Assert.assertEquals("Root directory ", 1, root.getSubDirectories().size());
        Assert.assertEquals("Sub directoris of hanlie", 3, root.getSubDirectories().get(0).getSubDirectories().size());
    }

    @Test
    public void testThatSearchDirectoryIsFound() {

        String home = service.addDirectory("home", "hanlie");
        String movies = service.addDirectory("hanlie", "Movies");
        String music = service.addDirectory("hanlie", "Music");
        String games = service.addDirectory("hanlie", "Games");


        Directory<String> findDirectory = service.searchDirectory("Music");

        Assert.assertEquals("Music",findDirectory.getName());
        Assert.assertEquals("home/hanlie/Music", findDirectory.getPath());
        Assert.assertEquals("hanlie", findDirectory.getParent().getName());
    }

    @Test
    public void testThatDirectoryIsDeleted() {

        String home = service.addDirectory("home", "hanlie");
        String movies = service.addDirectory("hanlie", "Movies");
        String music = service.addDirectory("hanlie", "Music");
        String games = service.addDirectory("hanlie", "Games");

        String response = service.deleteDirectory("Games");

        Assert.assertNull("Games", service.searchDirectory("Games"));
    }

    @Test
    public void testThatDirectoryIsUpdate() {

        String home = service.addDirectory("home", "hanlie");
        String movies = service.addDirectory("hanlie", "Movies");
        String music = service.addDirectory("hanlie", "Music");
        String games = service.addDirectory("hanlie", "Games");


        Directory<String> updatedDirectory = service.updateDirectory("Games", "Hobbies");

        Assert.assertNull(service.searchDirectory("Games"));
        Assert.assertEquals("Hobbies", updatedDirectory.getName());
    }

    @Test
    public void testThatDirectoryIsMoved() {

        String home = service.addDirectory("home", "hanlie");

        String movies = service.addDirectory("hanlie", "Movies");
        String action = service.addDirectory("Movies", "Action");

        String music = service.addDirectory("hanlie", "Music");
        String classical = service.addDirectory("Music", "Classical");
        String rock = service.addDirectory("Music", "Rock");


        /** process not allowed moving to sub directories */
        boolean isMoved = service.moveDirectory("Movies", "Action");

        Assert.assertFalse("Move : home/hanlie/movies -> home/hanlie/Movies/Action", isMoved);

        /** Move directory to parent directories */

        String toWatch = service.addDirectory("hanlie", "To Watch Movies");


        isMoved = service.moveDirectory("Movies", "To Watch Movies");

        Assert.assertTrue("home/hanlie/movies -> home/hanlie/To Watch Movies", isMoved);

    }
}
