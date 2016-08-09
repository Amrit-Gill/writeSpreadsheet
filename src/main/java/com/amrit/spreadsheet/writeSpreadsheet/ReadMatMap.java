package com.amrit.spreadsheet.writeSpreadsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.jmatio.io.MatFileReader;
import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;
import com.jmatio.types.MLNumericArray;
import com.jmatio.types.MLStructure;


public class ReadMatMap {
	
	public static void main( String[] args )
    {
    	File fis = new File("C:/Users/agi/Downloads/matlab2.mat");
		MatFileReader reader = null;
		try {
			reader = new MatFileReader(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, MLArray> map = reader.getContent();
		
		MLStructure val = (MLStructure) map.get("ans");
		
		Collection<String> fields = val.getFieldNames();
		
		String[] arrayOfField = fields.toArray(new String[10]);

		String str = arrayOfField[0];
		MLArray struct = val.getField(str);

		System.out.println(struct.contentToString());


    }

}
