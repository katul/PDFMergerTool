package com.atul.pdfmerger;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

import java.awt.Color;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

public class PDFMerger {

	private JFrame frame;
	private JTextField txtField1;
	private JTextField txtField2;
	private JTextField textField3;
	private JTextArea textArea;
	
	private List<String> pdfFiles;

	/**
	 * Launch the application.
	 */
	PDFProcessing pdf = new PDFProcessing(); 
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PDFMerger window = new PDFMerger();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PDFMerger() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 20));
		frame.setResizable(false);
		frame.getContentPane().setEnabled(false);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBounds(550, 150, 900, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("PDF Merger Tool");
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel.setBounds(273, 63, 350, 65);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel msg = new JLabel("");
		msg.setFont(new Font("Tahoma", Font.PLAIN, 20));
		msg.setForeground(Color.RED);
		msg.setHorizontalAlignment(SwingConstants.CENTER);
		msg.setBounds(233, 443, 486, 39);
		frame.getContentPane().add(msg);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.CYAN);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBackground(Color.CYAN);
		separator.setBounds(450, 160, 2, 250);
		frame.getContentPane().add(separator);
		
		JLabel multipleFiles = new JLabel("Merge Multiple files");
		multipleFiles.setForeground(Color.CYAN);
		multipleFiles.setFont(new Font("Tahoma", Font.PLAIN, 24));
		multipleFiles.setBounds(144, 186, 361, 29);
		frame.getContentPane().add(multipleFiles);
		
		textField3 = new JTextField();
		textField3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField3.setEditable(false);
		textField3.setColumns(10);
		textField3.setBounds(34, 309, 230, 30);
		frame.getContentPane().add(textField3);
		
		textArea = new JTextArea();
		textArea.setTabSize(11);
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.DARK_GRAY);
		textArea.setColumns(10);
		textArea.setBounds(34, 355, 380, 127);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
	    
	    textArea.setEditable(false);

	   
		frame.getContentPane().add(textArea);
		
		JButton btnSelectFolder = new JButton("Select Folder");
		btnSelectFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean checkSection = checkOtherSection(txtField1.getText()+txtField2.getText());
				if(checkSection){
					msg.setText("");
					JFileChooser jchooser = new JFileChooser();
					jchooser.setCurrentDirectory(new java.io.File("."));
					jchooser.setDialogTitle("Select Folder having PDF files");
					jchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				    int r = jchooser.showOpenDialog(null);
					if (r == JFileChooser.APPROVE_OPTION) {
						textField3.setText(jchooser.getSelectedFile().getAbsolutePath());
						pdfFiles = listOfPDFFiles(textField3.getText());
						int num = 0;
						textArea.setText("Number of PDFs found : "+pdfFiles.size()+"\n");
						if(pdfFiles.size() > 0){
							for(String file : pdfFiles){
								num++;
								if(num < 6){
									textArea.append(file+"\n");
								}else{
									textArea.append("...");
								}
							}
						}
					}else{
						textField3.setText("");
						textArea.setText("");
					}
				}else{
					msg.setForeground(Color.RED);
					msg.setText("Values Selected in Merge Individual Section. Abort!");
				}
			}
		});
		btnSelectFolder.setBounds(301, 309, 134, 29);
		frame.getContentPane().add(btnSelectFolder);
		
		JLabel singleFiles = new JLabel("Merge Individual Files");
		singleFiles.setForeground(Color.CYAN);
		singleFiles.setFont(new Font("Tahoma", Font.PLAIN, 24));
		singleFiles.setBounds(533, 186, 361, 29);
		frame.getContentPane().add(singleFiles);
		
		txtField1 = new JTextField();
		txtField1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtField1.setForeground(Color.BLACK);
		txtField1.setEditable(false);
		txtField1.setBounds(489, 268, 230, 29);
		frame.getContentPane().add(txtField1);
		txtField1.setColumns(10);
		
		txtField2 = new JTextField();
		txtField2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtField2.setEditable(false);
		txtField2.setColumns(10);
		txtField2.setBounds(489, 351, 230, 29);
		frame.getContentPane().add(txtField2);
		
		JButton btnAddFile1 = new JButton("Add File 1");
		btnAddFile1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean checkSection = checkOtherSection(textField3.getText());
				if(checkSection){
					msg.setText("");
					JFileChooser jchooser = new JFileChooser();
					jchooser.setFileFilter(new FileFilter() {
						
						@Override
						public String getDescription() {
							return "PDF Files (*.pdf)";
						}
						
						@Override
						public boolean accept(File f) {
							if (f.isDirectory()) {
						           return true;
						       } else {
						           String filename = f.getName().toLowerCase();
						           return filename.endsWith(".pdf") || filename.endsWith(".PDF") ;
						       }
						}
					});
					int r = jchooser.showOpenDialog(null);
					if (r == JFileChooser.APPROVE_OPTION) 
		                // set the label to the path of the selected file 
						txtField1.setText(jchooser.getSelectedFile().getAbsolutePath()); 
		            // if the user cancelled the operation 
		            else
		            	txtField1.setText(""); 
				}else{
					msg.setForeground(Color.RED);
					msg.setText("Values Selected in Merge Multiple Section. Abort!");
				}
			}
		});
		btnAddFile1.setBounds(752, 268, 115, 29);
		frame.getContentPane().add(btnAddFile1);
		
		JButton btnAddFile2 = new JButton("Add File 2");
		btnAddFile2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean checkSection = checkOtherSection(textField3.getText());
				if(checkSection){
					msg.setText("");
					JFileChooser jchooser = new JFileChooser();
					jchooser.setFileFilter(new FileFilter() {
						
						@Override
						public String getDescription() {
							return "PDF Files (*.pdf)";
						}
						
						@Override
						public boolean accept(File f) {
							if (f.isDirectory()) {
						           return true;
						       } else {
						           String filename = f.getName().toLowerCase();
						           return filename.endsWith(".pdf");
						       }
						}
					});	
					int r = jchooser.showOpenDialog(null);
					if (r == JFileChooser.APPROVE_OPTION) 
						txtField2.setText(jchooser.getSelectedFile().getAbsolutePath()); 
		            else
		            	txtField2.setText(""); 
				}else{
					msg.setForeground(Color.RED);
					msg.setText("Values Selected in Merge Multiple Section. Abort!");
				}
			}
		});
		btnAddFile2.setBounds(752, 351, 115, 29);
		frame.getContentPane().add(btnAddFile2);
		
		
		JButton btnMerge = new JButton("Merge");
		btnMerge.setForeground(Color.BLACK);
		btnMerge.setBackground(Color.GREEN);
		btnMerge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = "";
				File file1 = new File(txtField1.getText());
				File file2 = new File(txtField2.getText());
				String selectedFolder = textField3.getText();
				if(selectedFolder.isEmpty()){
					if(file1.exists() && file2.exists() ){
						try {
							boolean status = pdf.merge(file1,file2);
							if(status){
								msg.setForeground(Color.GREEN);
								message="Merge Complete!!!";
								pdf.openPDF(new File(file1.getParent()+"/merged.pdf"));
							}else{
								msg.setForeground(Color.RED);
								message="Error occured! PDF Merge Aborted";
							}
						
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}else{
						msg.setForeground(Color.RED);
						message="Please select 2 PDF files";
					}
				}else{
					
					if(pdfFiles.size() >= 2){
						boolean status;
						try {
							status = pdf.mergeAllFiles(pdfFiles);
							
							if(status){
								msg.setForeground(Color.GREEN);
								message="Merge Complete!!!";
								pdf.openPDF(new File(selectedFolder+"/merged.pdf"));
							}else{
								message="Error occured! PDF Merge Aborted";
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else{
						msg.setForeground(Color.RED);
						message="Atleast 2 PDF files are required for Merge!";
					}
				}
				
				msg.setText(message);
			}
		});
		btnMerge.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnMerge.setBounds(273, 509, 134, 72);
		frame.getContentPane().add(btnMerge);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtField1.setText("");
				txtField2.setText("");
				textField3.setText("");
				textArea.setText("");
				msg.setText("");
			}
		});
		btnClear.setForeground(Color.BLACK);
		btnClear.setBackground(Color.RED);
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnClear.setBounds(489, 509, 134, 72);
		frame.getContentPane().add(btnClear);
		
		
	}

	protected List<String> listOfPDFFiles(String directoryPath) {
		List<String> lf = new ArrayList<String>();
		String[] fileNames = null;
		File f = new File(directoryPath);
		if(f.exists()){
			fileNames = f.list();
		}
		for(String fileName : fileNames){
			if(fileName.toLowerCase().endsWith(".pdf")){
				lf.add(directoryPath+"/"+fileName);
			}
		}
		return lf;
	}

	protected boolean checkOtherSection(String checkValues) {
		if(checkValues.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
}
