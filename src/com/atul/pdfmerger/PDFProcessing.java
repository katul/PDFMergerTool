package com.atul.pdfmerger;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;


public class PDFProcessing {
	
	PDFMergerUtility PDFmerger = new PDFMergerUtility();
	@SuppressWarnings("deprecation")
	public boolean merge(File file1, File file2) throws IOException{
		
		PDFmerger.addSource(file1);
	    PDFmerger.addSource(file2);
	    
	    String mergedDoc = file1.getParent()+"/merged.pdf";
	    PDFmerger.setDestinationFileName(mergedDoc);
	    PDFmerger.mergeDocuments();
	    return true;
	}
	
	public void openPDF(File pdfFile) throws IOException{
		if(pdfFile.exists()){
			if(Desktop.isDesktopSupported()){
				Desktop.getDesktop().open(pdfFile);
			}
		}
	}

	public boolean mergeAllFiles(List<String> pdfFiles) throws IOException {
		PDFMergerUtility PDFmerger = new PDFMergerUtility();
		File file;
		String parent = "";
		for(String pdfFile : pdfFiles){
			file = new File(pdfFile);
			PDFmerger.addSource(file);
			parent = file.getParent();
		}
		String mergedDoc = parent+"/merged.pdf";
	    PDFmerger.setDestinationFileName(mergedDoc);
	    PDFmerger.mergeDocuments();
	    return true;
	}

}
