package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class LoadFileDialogController {

	private Stage dialogStage;
	private String fileString = "";
	
	private static File lastFile = null;
	
	@FXML
	private TextField pathField;
	
	private AutoSpellingTextArea textBox;

	public LoadFileDialogController() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	/**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
	/**
	 * Sets reference to TextArea
	 * 
	 */
	public void setTextArea(AutoSpellingTextArea textBox) {
		this.textBox = textBox;
	}
	
	@FXML
	private void handleBrowse() {
		FileChooser chooser = new FileChooser();
		
		configureFileChooser(chooser);
		
		// TEST SELECTING NEW FILE/OPENING CHOOSER AND CLOSING w/o selecting
		File file = chooser.showOpenDialog(dialogStage);
		
		if(file != null) {
			fileString = getStringFromFile(file);
			pathField.setText(file.getAbsolutePath());
			
			// last file to be used as initial directory
			lastFile = file;
		}
		
	}
	
	// set up file chooser
	private void configureFileChooser(FileChooser fc) {
		fc.setTitle("Choose Text File...");
		
		// set initial directory of file chooser
		if(lastFile != null) {
			fc.setInitialDirectory(lastFile.getParentFile());
		}
		
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
	}
	
	
	private String getStringFromFile(File file) {
		try {
			BufferedReader buf = new BufferedReader(new FileReader(file));
			String str;
			StringBuilder sb = new StringBuilder();
	
			while( (str = buf.readLine())!=null) {
				sb.append(str).append('\n');
			}
			buf.close();
			return sb.toString();
		}
		catch(FileNotFoundException ex) {
			// file not found
		}
		catch(IOException ex) {
		
		}
		
		return "";
	}
	
	@FXML
	private void handleAppend() {
		
		//append fileString if file was selected
		appendTextAndClose();
		// close dialog
	}
	
	@FXML
	private void handleReplace() {
		// clear text area and append fileString if file was selected
		textBox.clear();
		appendTextAndClose();
		// close dialog
	}
	
	private void appendTextAndClose() {
		textBox.appendText(fileString);
		dialogStage.close();
	}
	
}
