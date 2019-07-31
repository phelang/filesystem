package com.filesystem.springConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Controller
public class ViewResolverMapping {
    @RequestMapping(method = RequestMethod.GET, value = "/") /* index.html */
    public String index(){  /* Home page WEB-INF/views/index.html */
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/number-summarizer") /* footer.html */
    public String footer(){  /* Home page WEB-INF/views/index.html */
        return "numberRangeSummarizer";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/file-system") /* topnavigation.html */
    public String topNavigation(){  /* Home page WEB-INF/views/index.html */
        return "fileSystem";
    }
}
