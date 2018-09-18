package com.tictac.utils;

import com.tictac.exception.CoordinatesOutOfBoundsException;
import com.tictac.exception.InvalidCoordinateValueException;
import com.tictac.model.Board;

import java.util.List;

public class StringUtils {

    public static boolean isEmpty(String value){
        return value == null
                || value.trim().equals("");
    }

    public static String getValueFromFileLine(String value){
        if(!StringUtils.isEmpty(value)){
            String [] spplitedValue = value.split(":");
            return spplitedValue[1].trim();
        }
        return null;
    }

    public static int[] getPlayCoordinates(Board board, String coordinates){
        if(!isEmpty(coordinates)){
            try{
                int [] result = new int[2];
                String [] auxArray = coordinates.split(",");
                result[0] = Integer.parseInt(auxArray[0]);
                result[1] = Integer.parseInt(auxArray[1]);
                if(result[0] <= (board.getBoardSize() - 1) && result[1] <= (board.getBoardSize() - 1)){
                    return result;
                }else{
                    throw new CoordinatesOutOfBoundsException();
                }
            }
            catch (CoordinatesOutOfBoundsException e){
                throw new CoordinatesOutOfBoundsException();
            }catch (Exception e){
                throw new InvalidCoordinateValueException();
            }
        }
        return null;
    }

    public static boolean checkSameValues(List<String> values){

        String valueCheck = "";

        for(String value : values){
            if(isEmpty(valueCheck)){
                valueCheck = value.trim();
            }else if(!valueCheck.equals(value.trim())){
                return false;
            }
        }
        return true;

    }

}
