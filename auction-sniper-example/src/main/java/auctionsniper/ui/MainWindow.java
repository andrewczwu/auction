package auctionsniper.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;


public class MainWindow extends JFrame { 
  public static final String APPLICATION_TITLE = "Auction Sniper";
  public static final String STATUS_JOINING = "JOINING";
  public static final String STATUS_LOST = "LOST";
  public static final String STATUS_BIDDING = "BIDDING";
  public static final String SNIPER_STATUS_NAME = "sniper status";
  
  private final JLabel sniperStatus = createLabel(STATUS_JOINING);
  private static final String SNIPERS_TABLE_NAME = "Snipers Table";
  
  public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
  public static final String NEW_ITEM_ID_NAME = "item id";
  public static final String JOIN_BUTTON_NAME = "join button";
  public static final String NEW_ITEM_STOP_PRICE_NAME = "stop price";
  

  public MainWindow(){ 
	    super(MainWindow.APPLICATION_TITLE); 
	    setName(MainWindow.MAIN_WINDOW_NAME);
	    add(sniperStatus);
	    pack();
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	    setVisible(true); 
	  } 
  
  private static JLabel createLabel(String initialText) {
	  JLabel result = new JLabel(initialText);
	  result.setName(SNIPER_STATUS_NAME);
	  result.setBorder(new LineBorder(Color.black));
	  return result;
  }
  
  public void showStatus(String status) {
	  sniperStatus.setText(status);
  }
}
