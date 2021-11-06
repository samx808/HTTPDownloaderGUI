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
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
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
		shell.setSize(700, 110);
		shell.setText("HTTP Downloader");
		shell.addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent disposeEvent) {
				System.exit(0);
				
			}
		});
		shell.setLayout(new GridLayout(3, false));
		
		Label lblUrlToDownload = new Label(shell, SWT.NONE);
		lblUrlToDownload.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lblUrlToDownload.setText("URL to Download from:");
		
		textURL = new Text(shell, SWT.BORDER);
		GridData gd_textURL = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_textURL.widthHint = 153;
		textURL.setLayoutData(gd_textURL);
		textURL.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		textURL.addTraverseListener(new TraverseListener()
		  {
		    @Override
		    public void keyTraversed(final TraverseEvent event)
		    {
		      if (event.detail == SWT.TRAVERSE_RETURN)
		        { 
		    	  try {
	        		  URLStr = textURL.getText();
	        		  LocalDirStr = textLocalDir.getText(); 
					downloader();
				} catch (IOException e1) {
					e1.printStackTrace();
					}		        
		        }
		    }
		  });
		
		Label lbllocaldir = new Label(shell, SWT.NONE);
		lbllocaldir.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		lbllocaldir.setText("File Name to save:");
		
		textLocalDir = new Text(shell, SWT.BORDER);
		textLocalDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		textLocalDir.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		textLocalDir.addTraverseListener(new TraverseListener()
		  {
		    @Override
		    public void keyTraversed(final TraverseEvent event)
		    {
		      if (event.detail == SWT.TRAVERSE_RETURN)
		        { 
		    	  try {
	        		  URLStr = textURL.getText();
	        		  LocalDirStr = textLocalDir.getText(); 
					downloader();
				} catch (IOException e1) {
					e1.printStackTrace();
					}		        
		        }
		    }
		  });
		
		Button btnDownload = new Button(shell, SWT.NONE);
		GridData gd_btnDownload = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDownload.widthHint = 97;
		btnDownload.setLayoutData(gd_btnDownload);
		btnDownload.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnDownload.setText("Download");
		btnDownload.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
		          switch (e.type) {
		          case SWT.Selection: 
		        	  try {
		        		  URLStr = textURL.getText();
		        		  LocalDirStr = textLocalDir.getText(); 
						downloader();
					} catch (IOException e1) {
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
