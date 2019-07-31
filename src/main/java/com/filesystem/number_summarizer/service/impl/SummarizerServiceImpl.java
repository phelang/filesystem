package com.filesystem.number_summarizer.service.impl;

import com.filesystem.number_summarizer.service.SummarizerService;
import com.filesystem.number_summarizer.summarizer.NumberRangeSummarizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SummarizerServiceImpl implements SummarizerService {

    @Autowired
    private NumberRangeSummarizer numberRangeSummarizer;

    @Override
    public String summarizeCollection(String input) {
        return numberRangeSummarizer.summarizeCollection(numberRangeSummarizer.collect(input));

    }
}
