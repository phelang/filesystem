package com.filesystem.springConfig;

import com.filesystem.number_summarizer.summarizer.NumberRangeSummarizer;
import com.filesystem.number_summarizer.summarizer.impl.NumberRangeSummarizerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc   //<mvc:annotation-driven />
@Configuration
@ComponentScan({
        "com.filesystem.springConfig", "com.filesystem.number_summarizer.service",
        "com.filesystem.controller"})
public class RootConfig extends WebMvcConfigurerAdapter {

    @Bean(name = "numberRangeSummarizer")
    public NumberRangeSummarizer numberRangeSummarizer(){
        return new NumberRangeSummarizerImpl();
    }
}