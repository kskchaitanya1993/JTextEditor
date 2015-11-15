import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
 
public class JTextEditor extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	static JFrame frame = new JFrame();
	public JTextPane tp ;
	public StyledDocument doc ;
	public Font[] allf = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
	public Font initf = new Font("Courier New", 0, 17);
    
	public JTextEditor() {
		tp = new JTextPane();
		tp.setBackground(new Color(40,40,35));
        tp.setForeground(Color.WHITE);
        tp.setCaretColor(Color.WHITE);
        doc = tp.getStyledDocument();
        tp.setFont(initf);
        tp.setDragEnabled(true);
        tp.setTransferHandler(new StyleTransferHandler());
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(tp);
        TextLineNumber tln = new TextLineNumber(tp);
		tln.setMinimumDisplayDigits(2);
		tln.setForeground(new Color(80,80,80));
		tln.setCurrentLineForeground(new Color(250,0,0));
		scrollPane.setRowHeaderView(tln);
        add(scrollPane);
        
    }
 
    
    public JMenuBar getMenuBar () {
        
    	JMenuBar menuBar = new JMenuBar();
        Font mif = (Font) UIManager.get("MenuItem.font");
		Font mif2 = new Font(mif.toString(),1,13);
		UIManager.put("MenuItem.font", mif2);
		UIManager.put("Menu.font", mif2);
		
		
		//File Menu
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);
		JMenuItem New = new JMenuItem("New");
		New.setMnemonic(KeyEvent.VK_N);
		New.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        file.add(New);
		New.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tp.setText("");
				frame.setTitle("New Text Document");
			}
		});
		
		JMenuItem open = new JMenuItem("Open");
		open.setMnemonic(KeyEvent.VK_O);
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        file.add(open);
		open.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 JFileChooser chooser = new JFileChooser();
				 FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT/JAVA FILES", "txt", "text","java");
				 chooser.setFileFilter(filter);
			      if (chooser.showOpenDialog(JTextEditor.this) !=
			          JFileChooser.APPROVE_OPTION)
			        return;
			      File file = chooser.getSelectedFile();
			      if (file == null)
			        return;

			      FileReader reader = null;
			      try {
			    	frame.setTitle(file.getName());
			        reader = new FileReader(file);
			        tp.read(reader, null);
			      }
			      catch (IOException ex) {
			        JOptionPane.showMessageDialog(JTextEditor.this,
			        "File Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
			      }
			      finally {
			        if (reader != null) {
			          try {
			            reader.close();
			          } catch (IOException x) {}
			        }
			      }
			}
		});
		
		JMenuItem save = new JMenuItem("Save");
		save.setMnemonic(KeyEvent.VK_S);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        file.add(save);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT", "txt", "text",".txt");
				chooser.setFileFilter(filter);
				FileNameExtensionFilter filter2 = new FileNameExtensionFilter("JAVA", "java", ".java");
				chooser.setFileFilter(filter2);
			      if (chooser.showSaveDialog(JTextEditor.this) !=
			          JFileChooser.APPROVE_OPTION)
			        return;
			      File file = chooser.getSelectedFile();
			      if (file == null)
			        return;
			      FileWriter writer = null;
			      try {
			    	if(chooser.getFileFilter()== filter){
			        writer = new FileWriter(file+".txt");
			        tp.write(writer);
			        frame.setTitle(file.getName()+".txt");
			    	}
			    	else if (chooser.getFileFilter() == filter2){
			    		writer = new FileWriter(file+".java");
			    		tp.write(writer);
			    		frame.setTitle(file.getName()+".java");
				    }
			    }
			      catch (IOException ex) {
			        JOptionPane.showMessageDialog(JTextEditor.this,
			        "File Not Saved", "ERROR", JOptionPane.ERROR_MESSAGE);
			      }
			      finally {
			        if (writer != null) {
			          try {
			            writer.close();
			          } catch (IOException x) {}
			        }
			      }
			}
		});
		
		file.addSeparator();
		
		JMenuItem quit = new JMenuItem("Quit");
		quit.setMnemonic(KeyEvent.VK_Q);
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        file.add(quit);
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		
		//Edit Menu
        JMenu edit = new JMenu("Edit");
        edit.setMnemonic(KeyEvent.VK_E);
        
        JMenuItem cut = new JMenuItem(new DefaultEditorKit.CutAction());
        cut.setText("Cut"); 
        cut.setMnemonic(KeyEvent.VK_T);
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        edit.add(cut);
        
        JMenuItem copy = new JMenuItem(new DefaultEditorKit.CopyAction());
        copy.setText("Copy");
        copy.setMnemonic(KeyEvent.VK_C);
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        edit.add(copy);
        
        JMenuItem paste = new JMenuItem(new DefaultEditorKit.PasteAction());
        paste.setText("Paste");
        paste.setMnemonic(KeyEvent.VK_P);
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        edit.add(paste);
        
        edit.addSeparator();
        
        CompoundUndoManager um = new CompoundUndoManager(tp);
		JMenuItem undo = new JMenuItem("Undo");
		undo.setAction(um.getUndoAction());
		edit.add(undo);
		JMenuItem redo = new JMenuItem("Redo");
		redo.setAction(um.getRedoAction());
		edit.add(redo);
		edit.addSeparator();
		JMenuItem selectAll = new JMenuItem(tp.getActionMap().get(DefaultEditorKit.selectAllAction));
		selectAll.setText("Select All");
		selectAll.setMnemonic(KeyEvent.VK_S);
		selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        edit.add(selectAll);
		JMenuItem find = new JMenuItem("Find");
		find.setMnemonic(KeyEvent.VK_F);
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        edit.add(find);
		find.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new FindReplace(tp);
			}
		});
		JMenuItem replace = new JMenuItem("Replace");
		replace.setMnemonic(KeyEvent.VK_R);
		replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        edit.add(replace);
		replace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new ReplaceText(tp);
			}
		});
		menuBar.add(edit);
		
		
		//Style Menu
		JMenu style = new JMenu("Style");
		style.setMnemonic(KeyEvent.VK_S);
		menuBar.add(style);
		StayOpenCheckBoxMenuItem bold = new StayOpenCheckBoxMenuItem(new StyledEditorKit.BoldAction());
		bold.setText("Bold");
		bold.setMnemonic(KeyEvent.VK_B);
		bold.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
        style.add(bold);
		StayOpenCheckBoxMenuItem underline = new StayOpenCheckBoxMenuItem(new StyledEditorKit.UnderlineAction());
		underline.setText("Underline");
		underline.setMnemonic(KeyEvent.VK_U);
		underline.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
        style.add(underline);
		StayOpenCheckBoxMenuItem italic = new StayOpenCheckBoxMenuItem(new StyledEditorKit.ItalicAction());
		italic.setText("Italic");
		italic.setMnemonic(KeyEvent.VK_I);
		italic.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
        style.add(italic);
		
        
		//Font Menu
		JMenu font = new JMenu("Font");
		menuBar.add(font);
		MenuScroller.setScrollerFor(font,15, allf.length);
		ActionListener fontListen =new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			    int start = tp.getSelectionStart();
			    int end = tp.getSelectionEnd();
			    String newfont = arg0.getActionCommand().toString();
			    Style style = doc.addStyle("font",null);
			    StyleConstants.setFontFamily(style, newfont);
			    doc.setCharacterAttributes(start, end - start, style, false);
			}
		};
		for(int i =0;i<allf.length;i++){
			String s = allf[i].getFontName();
			allf[i]= new Font(s, 0, 18);
			UIManager.put("MenuItem.font",allf[i]);
			JMenuItem menufont=new JMenuItem(allf[i].getFontName());
			font.add(menufont);
			menufont.addActionListener(fontListen);
		}
	
		UIManager.put("MenuItem.font",mif2);
		
		
		//Size Menu
		JMenu size = new JMenu("Size");
		menuBar.add(size);
		MenuScroller.setScrollerFor(size,5);
		ActionListener sizeListen = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			    int start = tp.getSelectionStart();
			    int end = tp.getSelectionEnd();
			    
			    int fontsize = Integer.parseInt(arg0.getActionCommand().toString());
			    Style style = doc.addStyle("size",null);
			    StyleConstants.setFontSize(style, fontsize);
			    doc.setCharacterAttributes(start, end - start, style, false);
			}
		};
		for(int i=2;i<50;i=i+2){
			JMenuItem fsize = new JMenuItem(""+i+"");
			size.add(fsize);
			fsize.addActionListener(sizeListen);
		}
        
		//Color Menu
		JMenu color = new JMenu("Color");
		color.setMnemonic(KeyEvent.VK_C);
		menuBar.add(color);
		
		JMenuItem Red = new JMenuItem("Red");
		Red.setMnemonic(KeyEvent.VK_R);
		color.add(Red);
		JMenuItem Green = new JMenuItem("Green");
		Green.setMnemonic(KeyEvent.VK_G);
		color.add(Green);
		JMenuItem Blue = new JMenuItem("Blue");
		Blue.setMnemonic(KeyEvent.VK_B);
		color.add(Blue);
		JMenuItem Black = new JMenuItem("Black");
		Black.setMnemonic(KeyEvent.VK_K);
		color.add(Black);
		JMenuItem White = new JMenuItem("White");
		White.setMnemonic(KeyEvent.VK_W);
		color.add(White);
		
		ActionListener colorListen = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			    int start = tp.getSelectionStart();
			    int end = tp.getSelectionEnd();
			    
			    Style style = doc.addStyle("color", null);
			    if( arg0.getActionCommand()=="Red"){
			    	StyleConstants.setForeground(style, Color.red);
			    }
			    else if( arg0.getActionCommand()=="Green"){
			    	StyleConstants.setForeground(style, Color.green);
			    }
			    else if( arg0.getActionCommand()=="Blue"){
			    	StyleConstants.setForeground(style, Color.blue);
				    
			    }
			    else if( arg0.getActionCommand()=="Black"){
			    	StyleConstants.setForeground(style, Color.black);
			    }
			    else{
			    	StyleConstants.setForeground(style, Color.white);
			    }
			    
			   doc.setCharacterAttributes(start, end - start, style, false);
			}
		};
		
		Red.addActionListener(colorListen);
		Green.addActionListener(colorListen);
		Blue.addActionListener(colorListen);
		Black.addActionListener(colorListen);
		White.addActionListener(colorListen);
		
        return menuBar;
    }
 
    public static void build(){
    	
    	JTextEditor test = new JTextEditor();
        frame.setTitle("New Text Document");
        frame.setJMenuBar(test.getMenuBar());
        frame.setContentPane(test);
        frame.setSize(1000,800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    public static void main(String[] args) {
    	build();
    }
}
 
