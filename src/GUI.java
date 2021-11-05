import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class GUI {
	private static Text textURL;
	private static Text textLocalDir;
	private static String URLStr;
	private static String LocalDirStr;
	

	
	public GUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell();
		shell.setSize(900, 345);
		shell.setText("HTTP Downloader");
		shell.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent disposeEvent) {
				System.exit(0);
				
			}
		});
		
		Label lblUrlToDownload = new Label(shell, SWT.NONE);
		lblUrlToDownload.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblUrlToDownload.setBounds(10, 200, 171, 21);
		lblUrlToDownload.setText("URL to Download from:");
		
		textURL = new Text(shell, SWT.BORDER);
		textURL.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		textURL.setBounds(187, 200, 650, 32);
		
		Button btnDownload = new Button(shell, SWT.NONE);
		btnDownload.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnDownload.setBounds(743, 259, 94, 31);
		btnDownload.setText("Download");
		
		Label lbllocaldir = new Label(shell, SWT.NONE);
		lbllocaldir.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lbllocaldir.setBounds(10, 261, 171, 21);
		lbllocaldir.setText("File Name to save:");
		
		textLocalDir = new Text(shell, SWT.BORDER);
		textLocalDir.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		textLocalDir.setBounds(187, 258, 550, 32);
		btnDownload.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
		          switch (e.type) {
		          case SWT.Selection:
		        	  try {
		        		  URLStr = textURL.getText();
		        		  LocalDirStr = textLocalDir.getText(); 
						downloader();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		          }
		      }
		});
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		new GUI();
		
	}
	
	public static void downloader() throws IOException {
				
		try {	
						
				URL website = new URL(URLStr);
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream(LocalDirStr);
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				fos.close();
				JFrame frame = new JFrame();
				JOptionPane.showMessageDialog(frame, "Success!");
		}
		catch (FileNotFoundException e) {

			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Invalid Filename");
			frame.dispose();
		}
			
			
		catch (MalformedURLException e) {

	  		JFrame frame = new JFrame();
	  		JOptionPane.showMessageDialog(frame, "Invalid URL");
	  		frame.dispose();  		
		}
	}
}
