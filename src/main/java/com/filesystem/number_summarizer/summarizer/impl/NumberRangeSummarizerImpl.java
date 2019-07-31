package com.filesystem.number_summarizer.summarizer.impl;

import com.filesystem.number_summarizer.summarizer.NumberRangeSummarizer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Qhu
 *
 * Implements NumberRangeSummarizer Interface. Accepts comma delimited list of numbers,
 * and groups the numbers into a range when they are sequential.
 *
 */

@Component
public class NumberRangeSummarizerImpl implements NumberRangeSummarizer {

    private int indexIncrement = 0;
    private boolean isSequential;
    private int currentNumber = 0;
    private int nextNumber = 0;

    @Override
    public final Collection<Integer> collect(String input) {
        return Arrays.stream(input.split("[,]"))
                .filter(val -> val.matches("^[0-9]*$"))
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public final String summarizeCollection(Collection<Integer> input) {

        ArrayList<Integer> list = new ArrayList<>(input);
        String str = list.get(0).toString();

        return summarize(list, indexIncrement, str);
    }

    private String summarize(ArrayList<Integer> list, int i, String str){
        currentNumber = (list.get(i) + 1);
        nextNumber = list.get(i + 1);

        if(currentNumber == nextNumber){
            i++;
            isSequential = true;
        }
        else if(isSequential) {
            str += "-" + list.get(i);
            i++;
            str += ("," + list.get(i));
            isSequential = false;
        }
        else {
            isSequential = false;
            i++;
            str += "," + list.get(i);
        }
        return (i <= list.size() - 2 ? summarize(list, i, str) : str) ;
    }


    /* MAYBE OR NOT - Unnecessary repition create a separate file implementation for utils */
    private String[] splitInput(String input) {

        return input.split("[,]");
    }

    private boolean validate(String str) {
        return str.matches("^[0-9]*$");
    }
}