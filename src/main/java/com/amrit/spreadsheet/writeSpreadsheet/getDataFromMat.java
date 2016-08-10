package com.amrit.spreadsheet.writeSpreadsheet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLChar;
import com.jmatio.types.MLDouble;
import com.jmatio.types.MLStructure;

public class getDataFromMat {
	
    public List<List<Object>> getDataFromMatlab()
    {
    	List<Object> rowData = new ArrayList<Object>();
    	List<List<Object>> data = new ArrayList<List<Object>>();
    	
    	File fisNew = new File("C:/Users/agi/Dev/personal/java/data/matlab.mat");
		MatFileReader readerNew = null;
		File fisOld = new File("C:/Users/agi/Dev/personal/java/data/matlab1.mat");
		MatFileReader readerOld = null;
		try {
			readerNew = new MatFileReader(fisNew);
			readerOld = new MatFileReader(fisOld);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, MLArray> mapNew = readerNew.getContent();
		Map<String, MLArray> mapOld = readerOld.getContent();
		
		MLStructure valNew = (MLStructure) mapNew.get("slidingValues");
		MLStructure valOld = (MLStructure) mapOld.get("slidingValues");
		
		Collection<String> fieldsNew = valNew.getFieldNames();
		Collection<String> fieldsOld = valOld.getFieldNames();
		String[] arrayOfFieldNew = fieldsNew.toArray(new String[fieldsNew.size()]);
		String[] arrayOfFieldOld = fieldsOld.toArray(new String[fieldsOld.size()]);

		for (int i = 0; i < fieldsNew.size(); i++) {
			String str = arrayOfFieldNew[i];
			MLStructure structNew = (MLStructure) valNew.getField(str);
			MLStructure structOld = (MLStructure) valOld.getField(str);
			MLStructure microEventsTransitionsNew = (MLStructure) structNew.getField("microEventsTransitions");
			MLStructure microEventsTransitionsOld = (MLStructure) structOld.getField("microEventsTransitions");
			int sizeOfMicroEvents = microEventsTransitionsNew.getSize();
			for (int j = 0; j < sizeOfMicroEvents; j++) {
				try {
				rowData = new ArrayList<Object>();
				rowData.add(str);
				MLArray windowIndexArrayNew = microEventsTransitionsNew.getField("windowIndex",j);
				MLArray windowIndexArrayOld = microEventsTransitionsOld.getField("windowIndex",j);
				double windowIndexNew = ((MLDouble) windowIndexArrayNew).get(0);
				double windowIndexOld = ((MLDouble) windowIndexArrayOld).get(0);
//				System.out.println(windowIndexNew + "\t" +  windowIndexOld);
				rowData.add(windowIndexNew);
				rowData.add(windowIndexOld);
				rowData.add(" ");
				MLArray correctedRelativeHorodateInMsNew = microEventsTransitionsNew.getField("correctedRelativeHorodateInMs",j);
				MLArray correctedRelativeHorodateInMsOld = microEventsTransitionsOld.getField("correctedRelativeHorodateInMs",j);
				rowData.add(((MLDouble) correctedRelativeHorodateInMsNew).get(0));
				rowData.add(((MLDouble) correctedRelativeHorodateInMsOld).get(0));
				rowData.add(" ");
				MLArray codeNew = microEventsTransitionsNew.getField("code",j);
				MLArray codeOld = microEventsTransitionsOld.getField("code",j);
				rowData.add(((MLChar) codeNew).getString(0));
				rowData.add(((MLChar) codeOld).getString(0));
				rowData.add(" ");
				MLArray valueNew = microEventsTransitionsNew.getField("value",j);
				MLArray valueOld = microEventsTransitionsOld.getField("value",j);
				rowData.add(((MLChar) valueNew).getString(0));
				rowData.add(((MLChar) valueOld).getString(0));
				rowData.add(" ");
				data.add (rowData);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		return data;
    }
}
