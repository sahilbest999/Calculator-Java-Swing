import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.script.*;
import java.io.*;
import com.formdev.flatlaf.*;

class AWT_Instance extends JFrame{
	JMenuBar menuBar = new JMenuBar();
	JMenu About = new JMenu("About");
	
    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints layoutCns = new GridBagConstraints();
    JTextField taa = new JTextField();
//    Font button_font = customFont("Fonts//PressStart2P-Regular.ttf",0,20);
    Font button_font = customFont(".//Fonts//ModecoTrial-Regular.otf",0,20);
    Font taa_font = customFont(".//Fonts//ModecoTrial-Regular.otf",0,20);
    ScriptEngineManager SEM = new ScriptEngineManager();
    ScriptEngine jsEngine = SEM.getEngineByExtension("js");
    
//    KeyListener keyListener = new KeyListener() {
//        
//        public void keyPressed(KeyEvent event) {
//            System.out.println("Pressed");
//        }
//        
//        public void keyReleased(KeyEvent event) {
//        	System.out.println("Released");
//        }
//        
//        public void keyTyped(KeyEvent event) {
//        	System.out.println("Typed");
//        }
//	};
	
    
    void change_theme(String THEME_NAME) {
    	try {
            UIManager.setLookAndFeel(THEME_NAME);
            SwingUtilities.updateComponentTreeUI(this);
            this.pack();
        } catch( Exception ex ) {System.err.println( "Failed to initialize LaF" );}
    	
    }
    
    void themeConfigure() {
    	 Action changeThemeAction = new AbstractAction() {
    	    	public void actionPerformed(ActionEvent e) {	    		
    	    		switch(e.getActionCommand()) {
    	    		case "Light":change_theme("com.formdev.flatlaf.FlatLightLaf");break;  	    		
    	    		case "Dark":change_theme("com.formdev.flatlaf.FlatDarkLaf");break;    	        		
    	    		case "IntelliJ":change_theme("com.formdev.flatlaf.FlatIntelliJLaf");break;
    	    		case "Darcula":change_theme("com.formdev.flatlaf.FlatDarculaLaf");break;
    	    		}}};
 
    	JMenu themeMenu = new JMenu("Themes");
    	
    	JCheckBoxMenuItem theme1 = new JCheckBoxMenuItem("Light");
    	JCheckBoxMenuItem theme2 = new JCheckBoxMenuItem("Dark");
    	JCheckBoxMenuItem theme3 = new JCheckBoxMenuItem("IntelliJ");
    	JCheckBoxMenuItem theme4 = new JCheckBoxMenuItem("Darcula");
    	
    	ButtonGroup themeBtnGrp = new ButtonGroup();
    	
    	
    	theme1.setAccelerator(KeyStroke.getKeyStroke("F1"));
    	theme2.setAccelerator(KeyStroke.getKeyStroke("F2"));
    	theme3.setAccelerator(KeyStroke.getKeyStroke("F3"));
    	theme4.setAccelerator(KeyStroke.getKeyStroke("F4"));
    	
    	theme1.addActionListener(changeThemeAction);
    	theme2.addActionListener(changeThemeAction);
    	theme3.addActionListener(changeThemeAction);
    	theme4.addActionListener(changeThemeAction);
    	
    	theme4.setSelected(true);
    
    	themeBtnGrp.add(theme1);
    	themeBtnGrp.add(theme2);
    	themeBtnGrp.add(theme3);
    	themeBtnGrp.add(theme4);
    	
    	themeMenu.add(theme1);
    	themeMenu.add(theme2);
    	themeMenu.add(theme3);
    	themeMenu.add(theme4);
    	menuBar.add(themeMenu);
    }
    
    boolean check_expression_validity() {
    	if(taa.getText().trim().equalsIgnoreCase("Invalid Expression")) {
    		taa.setText("");
    		taa.setForeground(Color.GRAY);
    		return false;
    	}
    	return true;
    }
    
    void about() {
    	About.setFocusable(false);
    	JOptionPane DialogBox = new JOptionPane("About ME");
    	
    	About.addMenuListener(new MenuListener(){
   		 public void menuSelected(MenuEvent e) {
   			 int OK_BTN_CALL = JOptionPane.showOptionDialog(null, "Hello, My Name is Sahil Shahane\n\nContact Me : linktr.ee/sahilbest999", "About Me", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, null, null);
   			}

		@Override
		public void menuCanceled(MenuEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void menuDeselected(MenuEvent arg0) {
			// TODO Auto-generated method stub
			
		}
    	});
    			
    			 
    	menuBar.add(About);
    }
    
    AWT_Instance(){

    	taa.setFont(taa_font);
    	
  
//      this.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent windowEvent){System.exit(0);}});  //Depricated Method
    	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	this.setJMenuBar(menuBar);
        this.setTitle("Calculator");
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(".//icon//icon.png"));
        themeConfigure();
        about();
        CCA_Buttons(); //CCA - Create, Configure , Add
        
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }

    Font customFont(String FONT_LOCATION,int type,int Size){
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(FONT_LOCATION)).deriveFont((float)Size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return customFont;
        } catch (Exception e) {
            System.out.println("Could Not Load Custom Font");
            return new Font("Arial",type,Size);
        }
    }

    void CCA_Buttons(){
    	 Action numberAction = new AbstractAction(){
             public void actionPerformed(ActionEvent e){  
             		check_expression_validity();
         			taa.replaceSelection(e.getActionCommand());
             }
     };

     Action performAction = new AbstractAction(){
         public void actionPerformed(ActionEvent e){
         		if(check_expression_validity()==true)
         		taa.replaceSelection(e.getActionCommand());
         }
     };
     
     Action clear_btnAction = new AbstractAction(){
         public void actionPerformed(ActionEvent e){
             taa.setText("");
         }
     };
     
     Action equal_btnAction = new AbstractAction(){
         public void actionPerformed(ActionEvent e){
         if(check_expression_validity()==true) {
            String expression = taa.getText();
            String Script = "eval("+expression+")";

            try{taa.setText(jsEngine.eval(Script).toString());}
            catch(Exception exception){taa.setText("Invalid Expression");taa.setForeground(Color.RED);}
            
         }else if(check_expression_validity()==false){
         	taa.setText("");
         }
         }};
         
        JButton bc = new JButton("C");
        JButton b1 = new JButton("1");
        JButton b2 = new JButton("2");
        JButton b3 = new JButton("3");
        JButton b4 = new JButton("4");
        JButton b5 = new JButton("5");
        JButton b6 = new JButton("6");
        JButton b7 = new JButton("7");
        JButton b8 = new JButton("8");
        JButton b9 = new JButton("9");
        JButton b0 = new JButton("0");
        JButton ba = new JButton("+");
        JButton bs = new JButton("-");
        JButton bd = new JButton("/");
        JButton bm = new JButton("*");
        JButton be = new JButton("=");
        JButton bdot = new JButton(".");

        JPanel panel = new JPanel();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        
        b1.setFont(button_font);
        b2.setFont(button_font);
        b3.setFont(button_font);
        b4.setFont(button_font);
        b5.setFont(button_font);
        b6.setFont(button_font);
        b7.setFont(button_font);
        b8.setFont(button_font);
        b9.setFont(button_font);
        b0.setFont(button_font);
        bc.setFont(button_font);
        ba.setFont(button_font);
        bs.setFont(button_font);
        bm.setFont(button_font);
        bd.setFont(button_font);
        be.setFont(button_font);
        bdot.setFont(button_font);

        b1.addActionListener(numberAction);  
        b2.addActionListener(numberAction);
        b3.addActionListener(numberAction);
        b4.addActionListener(numberAction);
        b5.addActionListener(numberAction);
        b6.addActionListener(numberAction);
        b7.addActionListener(numberAction);
        b8.addActionListener(numberAction);
        b9.addActionListener(numberAction);
        b0.addActionListener(numberAction);
        bdot.addActionListener(numberAction);
        
        ba.addActionListener(performAction);
        bs.addActionListener(performAction);
        bm.addActionListener(performAction);
        bd.addActionListener(performAction);
        bc.addActionListener(clear_btnAction);
        be.addActionListener(equal_btnAction);
        
        panel.setLayout(layout);
        panel.setMaximumSize(new Dimension(this.getWidth(),this.getHeight()));
 
        layoutCns.ipadx = (int)(width * 0.02);
        layoutCns.ipady = (int)(height * 0.04);

        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 0;
        layoutCns.gridy = 0;
        layoutCns.gridwidth = 4;
        panel.add(taa ,layoutCns);

        layoutCns.gridwidth = 1;

        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 0;
        layoutCns.gridy = 1;
        panel.add(bc,layoutCns);

        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 1;
        layoutCns.gridy = 1;
        panel.add(bd,layoutCns);
        
        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 2;
        layoutCns.gridy = 1;
        panel.add(bm,layoutCns);

        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 3;
        layoutCns.gridy = 1;
        panel.add(bs,layoutCns);

        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 0;
        layoutCns.gridy = 2;
        panel.add(b1,layoutCns);

        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 1;
        layoutCns.gridy = 2;
        panel.add(b2,layoutCns);

        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 2;
        layoutCns.gridy = 2;
        panel.add(b3,layoutCns);

        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 0;
        layoutCns.gridy = 3;
        panel.add(b4,layoutCns);

        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 1;
        layoutCns.gridy = 3;
        panel.add(b5,layoutCns);

        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 2;
        layoutCns.gridy = 3;
        panel.add(b6,layoutCns);

        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 0;
        layoutCns.gridy = 4;
        panel.add(b7,layoutCns);

        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 1;
        layoutCns.gridy = 4;
        panel.add(b8,layoutCns);

        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 2;
        layoutCns.gridy = 4;
        panel.add(b9,layoutCns);

        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 0;
        layoutCns.gridy = 5;
        layoutCns.gridwidth = 2;
        panel.add(b0,layoutCns);

        layoutCns.fill = GridBagConstraints.HORIZONTAL;
        layoutCns.gridx = 2;
        layoutCns.gridy = 5;
        layoutCns.gridwidth = 1;
        panel.add(bdot,layoutCns);

        layoutCns.fill = GridBagConstraints.VERTICAL;
        layoutCns.gridheight = 2;
        layoutCns.gridx = 3;
        layoutCns.gridy = 2;
        panel.add(ba,layoutCns);

        layoutCns.fill = GridBagConstraints.VERTICAL;
        layoutCns.gridwidth = 2;
        layoutCns.gridx = 3;
        layoutCns.gridy = 4;
        panel.add(be,layoutCns);
        
        this.setContentPane(panel);
    }

}



public class Calculator{
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarculaLaf");
            UIManager.put( "Component.hideMnemonics", false );
            UIManager.put( "ScrollBar.showButtons", true );
            UIManager.put( "ScrollBar.thumbArc", 999 );
            UIManager.put( "ScrollBar.thumbInsets", new Insets( 2, 2, 2, 2 ) );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        new AWT_Instance();
    }
}

