package com.tictac.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<String> readDataFromFile() throws IOException {
        List<String> listString = new ArrayList<String>();
        BufferedReader buffered = new BufferedReader(new FileReader(ConstsEnum.FILE_PATH.getValue()));
        String line = buffered.readLine();

        while(line != null){
            if(!StringUtils.isEmpty(line)){
                listString.add(line);
            }
            line = buffered.readLine();
        }
        return listString;
    }

}
