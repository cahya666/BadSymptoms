package com.sanjaya.badsymptoms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cahya on 06/06/2015.
 */
public class UniqueLineReader extends BufferedReader {
    Set<String> lines = new HashSet<String>();

    public UniqueLineReader(Reader arg0) {
        super(arg0);
    }

    @Override
    public String readLine() throws IOException {
        String uniqueLine;
        while (lines.add(uniqueLine = super.readLine()) == false); //read until encountering a unique line
        return uniqueLine;
    }

}
