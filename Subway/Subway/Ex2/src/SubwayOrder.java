import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class SubwayOrder extends JFrame implements ActionListener, MouseListener{
   
   ImageIcon iconMenu = new ImageIcon("subway.jpg");
   JButton selCancel,cancel,order,btnNext, cookieSet, wedgeSet;
   JTable menu;
   DefaultTableModel modelMenu;
   JButton[] menuBtn = new JButton[60];
   JPanel pnlMenu;
   String[] header = {"�޴�","����","����","����"};
   String[][] contents={   }; 
   JTable orders;
   DefaultTableModel model;
   int selectedRow = -1, sum = 0, countNum=0;
   JLabel lblsum, mainlbl;
   
   public SubwayOrder() {
      this.setDefaultCloseOperation(3);
      this.setSize(1500,1000);
      this.setTitle("�ֹ�");
      this.setLayout(null);
      this.getContentPane().setBackground(Color.WHITE);
      
       //��� �̹��� ���� �� ũ�� ����
      ImageIcon mainIcon = new ImageIcon("subway.jpg");
      mainlbl = new JLabel(mainIcon);
      mainlbl.addMouseListener(this);
      mainlbl.setBounds(0, 0, 1500, 150); 
      
      //�޴� ��ȸ����
      pnlMenu = new JPanel(new GridLayout(0,3));
      JScrollPane spMenu = new JScrollPane(pnlMenu);
      spMenu.getVerticalScrollBar().setUnitIncrement(150);  //��ũ�� �ӵ� ����
      spMenu.setBounds(10,180,730,750);
      // ��ư ����� Ŭ���� ���� ����
      buttonAdd();
      
      // ���õ� �޴� ��ȸ
      model = new DefaultTableModel(contents,header);
      orders = new JTable(model);
      orders.setFont(new Font("����",Font.BOLD,18));
      orders.addMouseListener(this);
      JScrollPane orderPane = new JScrollPane(orders);
      orders.setRowHeight(40);
      orderPane.getViewport().setBackground(Color.WHITE);
      orderPane.setBounds(970, 180, 500, 380); 
      
      //�հ�
      lblsum = new JLabel("�հ� :  ��");
      lblsum.setBounds(1280, 570, 200, 20); 
      lblsum.setFont(new Font("����",Font.BOLD , 23));
      lblsum.addMouseListener(this);
      //��ư��
      cookieSet = new JButton("<HTML>"+"��"+"<br>"+"Ű"+"<br>"+"��"+"<br>"+"Ʈ"+"<br>",new ImageIcon("co.png"));
      cookieSet.setVerticalTextPosition(JButton.BOTTOM);  // �ؽ�Ʈ �Ʒ���
      cookieSet.setHorizontalTextPosition(JButton.CENTER);
      cookieSet.setFont(new Font("HY�߰��",Font.BOLD , 40));
      cookieSet.setBounds(755, 180, 200, 365);
      cookieSet.setBackground(new Color(1,152,71));
      cookieSet.setForeground(Color.WHITE);
      cookieSet.addActionListener(this);
      wedgeSet = new JButton("<HTML>"+"��"+"<br>"+"��"+"<br>"+"��"+"<br>"+"Ʈ",new ImageIcon("we.png"));
      wedgeSet.setVerticalTextPosition(JButton.BOTTOM);  // �ؽ�Ʈ �Ʒ���
      wedgeSet.setHorizontalTextPosition(JButton.CENTER);
      wedgeSet.setFont(new Font("HY�߰��",Font.BOLD , 40));
      wedgeSet.setBounds(755, 565, 200, 365);
      wedgeSet.setBackground(new Color(1,152,71));
      wedgeSet.setForeground(Color.WHITE);
      wedgeSet.addActionListener(this);
      selCancel = new JButton("�������");
      selCancel.setFont(new Font("HY�߰��",Font.BOLD , 40));
      selCancel.setBounds(970, 610, 500, 100);
      selCancel.setBackground(new Color(1,152,71));
      selCancel.setForeground(Color.WHITE);
      selCancel.addActionListener(this);
      cancel = new JButton("�ֹ����");
      cancel.setBounds(970, 720, 500, 100);
      cancel.setFont(new Font("HY�߰��",Font.BOLD , 40));
      cancel.setBackground(new Color(1,152,71));
      cancel.setForeground(Color.WHITE);
      cancel.addActionListener(this);
      order = new JButton("����");
      order.setBounds(970, 830, 500, 100);
      order.setFont(new Font("HY�߰��",Font.BOLD , 40));
      order.setBackground(new Color(1,152,71));
      order.setForeground(Color.WHITE);
      order.addActionListener(this);
      
      this.add(mainlbl);
      this.add(orderPane);
      this.add(spMenu);
      this.add(lblsum);
      this.add(selCancel);
      this.add(cancel);
      this.add(order);
      this.add(cookieSet);
      this.add(wedgeSet);
      this.setVisible(true);
   }
   
   void buttonAdd(){
      File f = new File("subway.txt");
      FileReader fr = null;
      BufferedReader br = null;
      try {
         fr = new FileReader(f);
         br = new BufferedReader(fr);
         String l ;
         int i = 0;
         while((l = br.readLine()) != null){
            menuBtn[i] = new JButton(l.split("/")[1]+" ��", new ImageIcon(l.split("/")[3]+""));
            menuBtn[i].setVerticalTextPosition(JButton.BOTTOM);  // �ؽ�Ʈ �Ʒ���
            menuBtn[i].setHorizontalTextPosition(JButton.CENTER); // �ؽ�Ʈ ���
            menuBtn[i].setBackground(Color.WHITE);
            menuBtn[i].setSize(300,300);
            menuBtn[i].setFont(new Font("���",Font.BOLD,30));
            menuBtn[i].addActionListener(this);
            pnlMenu.add(menuBtn[i]);
            i++;
         }
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }finally{
    	  if(br!=null){
    		  try {
    			  if(br!=null){
    				  br.close();
    			  }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	  }
      }
   }
   void total(){
	   int sum=0;
	   for(int i =0; i<orders.getRowCount() ; i++){
		   sum = sum + (Integer.parseInt(orders.getValueAt(i, 1)+"")* Integer.parseInt(orders.getValueAt(i, 2)+""));
	   }
	   lblsum.setText("�հ� : "+sum+" ��");
   }
   
   boolean selectSetCheck(){  //��Ʈ�� ������ġ���� ������ Ȯ���Ѵ�
	   int setNum=0,menuNum=0;
 	   for(int i = 0 ; i <orders.getRowCount() ;i++){
 	  		if("��Ű��Ʈ".equals(orders.getValueAt(i, 0)) || "������Ʈ".equals(orders.getValueAt(i, 0))){
 	  			setNum = setNum + Integer.parseInt(orders.getValueAt(i, 2)+"");
 	  		}else{
 	  			menuNum = menuNum + Integer.parseInt(orders.getValueAt(i, 2)+"");;
 	  		}
 	   }
 	   if(setNum==0 && menuNum ==0){
 		  return true;
 	   }else if(setNum >= menuNum){
 		  return true;
 	   }else{
 		  return false;
 	  }
   }
   void set(String setName, String setPrice){  //�޴���� ������ �޾Ƽ� �۾� ����
	   if(selectSetCheck() == true){
 		  JOptionPane.showMessageDialog(this,"��Ʈ�޴��� �� �������ϴ�.\n�ֹ�����Ȯ�����ּ���.");
 	  }else{
 		  String[] set = new String[4];
     	  set[0] = setName;
     	  set[1] = setPrice;
     	  set[2] = "1";
     	  set[3] = " ";
     	  int setRow=-1;
     	  for(int i=0; i<orders.getRowCount(); i++){  // �� ��°�� ��Ű��Ʈ�� �ִ��� Ȯ��
     		  if(set[0].equals(orders.getValueAt(i, 0)+"")){
     			  setRow=i;
     		  }
     	  }
     	  if(setRow >= 0){  //������ ������ �� ����
     		  int count = Integer.parseInt(orders.getValueAt(setRow, 2)+"");
     		  count++;
     		  orders.setValueAt(count, setRow, 2);
     	  }else{  //������ �߰�
     		  model.addRow(set);
     	  }
     	  total();
 	  }
   }
   @Override
   public void actionPerformed(ActionEvent e) {
      if(e.getSource() == order){
    	  if(orders.getRowCount() <= 0){
    		  JOptionPane.showMessageDialog(this,"�ֹ������� Ȯ���� �ּ���");
    	  }else {
    		  new PayPopupSub(this,"���� �˾�",true);
    	  }
      }else if(e.getSource() == selCancel){
    	  if(selectedRow == -1 || orders.getRowCount()==0){
              JOptionPane.showMessageDialog(this, "���õ� ���� ����");
           }else{
              model.removeRow(selectedRow);
              total();
           }
      }else if(e.getSource() == cancel){
    	  model.setNumRows(0);
    	  sum=0;
    	  lblsum.setText("�հ� :  ��");
      }else if(e.getSource() == cookieSet){  //��Ű��Ʈ �߰� ��ư
    		set("��Ű��Ʈ","1900");
      }else if(e.getSource() == wedgeSet){
    		set("������Ʈ","2400");
      }else{
         String strMenu = null;
         for(int i = 0 ; i<menuBtn.length;i++){
            if(e.getSource() == menuBtn[i]){
               strMenu = menuBtn[i].getIcon()+"";
            }
         }
         new MenuPopupSub(this,"�޴�",true,strMenu);
      }
   }
   @Override
   public void mouseClicked(MouseEvent e) {
	selectedRow = orders.getSelectedRow();
	if(e.getSource() == mainlbl  && e.getClickCount()==2){ //��ǥ�� ����Ŭ���ϸ� �߻�
		new SubwayKitchen();
	}
	if(e.getSource() == lblsum && e.getClickCount()==2){  //�հ踦 ����Ŭ���ϸ� ��������
		new SubwayAdim();
	}
   }
   @Override
   public void mouseEntered(MouseEvent e) {}
   @Override
   public void mouseExited(MouseEvent e) {}
   @Override
   public void mousePressed(MouseEvent e) {}
   @Override
   public void mouseReleased(MouseEvent e) {}
   public static void main(String[] args) {
	      new SubwayOrder();
	   }
}

class PayPopupSub extends JDialog implements ActionListener{
      
	JButton back, order;
	SubwayOrder je;
	Enumeration<AbstractButton> enums;
	JRadioButton jb;
	String value=null ,pack;
	ButtonGroup group,group2;
	
      public PayPopupSub(SubwayOrder je,String title, boolean modal) {
         super(je,title,modal);
         this.je = je;
         
         setLayout(new GridLayout(0,1));
         JPanel inoutPnl = new JPanel();
         inoutPnl.setBackground(Color.WHITE);
         JRadioButton take = new JRadioButton("����",true);
         take.setBackground(Color.WHITE);
         take.setFont(new Font("����",Font.BOLD , 20));
         JRadioButton here = new JRadioButton("����");
         here.setFont(new Font("����",Font.BOLD , 20));
         here.setBackground(Color.WHITE);
         group = new ButtonGroup();
         group.add(take);
         group.add(here);
         inoutPnl.add(take);
         inoutPnl.add(here);
         
         JPanel payPnl = new JPanel();
         payPnl.setBackground(Color.WHITE);
         JRadioButton cash = new JRadioButton("����",true);
         cash.setBackground(Color.WHITE);
         cash.setFont(new Font("����",Font.BOLD , 20));
         JRadioButton card = new JRadioButton("ī��");
         card.setBackground(Color.WHITE);
         card.setFont(new Font("����",Font.BOLD , 20));
         group2 = new ButtonGroup();
         group2.add(cash);
         group2.add(card);
         payPnl.add(cash);
         payPnl.add(card);
         
         JPanel orderPnl = new JPanel();
         orderPnl.setBackground(Color.WHITE);
         order = new JButton("�ֹ�");
         order.setFont(new Font("����",Font.BOLD , 20));
         order.setForeground(Color.WHITE);
         order.setBackground(new Color(1,152,71));
         back = new JButton("�ڷΰ���");
         back.setFont(new Font("����",Font.BOLD , 20));
         back.setForeground(Color.WHITE);
         back.setBackground(new Color(1,152,71));
         order.addActionListener(this);
         back.addActionListener(this);
         orderPnl.add(order);
         orderPnl.add(back);
         
         this.add(inoutPnl);
         this.add(payPnl);
         this.add(orderPnl);

         this.setSize(300,300);
         this.setVisible(true);
      }
      
      @Override
      public void actionPerformed(ActionEvent e) {
    	  if(e.getSource() == back){
              this.dispose();
           }else if( e.getSource() == order){
        	   order();
           }
      }
      void order(){
       enums = group.getElements();
   	   findGroup();
   	   pack = value; //���� ī�� �����ϱ� ����
   	   enums = group2.getElements();
   	   findGroup();

   	   File f = new File("order.txt");
   	   FileReader fr = null;
   	   BufferedReader br = null;
   	   FileWriter fw = null;
   	   PrintWriter pw = null;
   	   
   	   try {
   		   fr = new FileReader(f);
   		   br = new BufferedReader(fr);
   		   String l = null;
   		   while((l=br.readLine())!=null){
       		   je.countNum = Integer.parseInt(l.split("/")[0]);
   		   }
   			   je.countNum++;
   		   fw = new FileWriter(f,true);
   		   pw = new PrintWriter(fw);
   		   for(int i=0;i<je.orders.getRowCount();i++){
   			   int x = (Integer.parseInt(je.orders.getValueAt(i, 1)+"") * (Integer.parseInt(je.orders.getValueAt(i, 2)+"")));
   			   if(value.equals("����")){
   				   pw.println(je.countNum+ "/" + je.orders.getValueAt(i, 0)+"/" + 
	                		   je.orders.getValueAt(i, 3)+"/" + je.orders.getValueAt(i, 2)+"/"+
	                		   pack + "/" + "0" +"/" + x);
   			   }else{
   				   pw.println(je.countNum+ "/" + je.orders.getValueAt(i, 0)+"/" + 
	                		   je.orders.getValueAt(i, 3)+"/" + je.orders.getValueAt(i, 2)+"/"+
	                		   pack + "/" +x +"/" + "0");
   			   		}
   		   	}	
   		   this.dispose();
   		   je.model.setNumRows(0);
   		   JOptionPane.showMessageDialog(this,"�ֹ��� �Ϸ� �Ǿ����ϴ�.");
   		   je.sum=0;
   		   je.lblsum.setText("�հ� :  ��");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			if(pw != null){
				pw.close();
				}
			}
      }
      void findGroup(){
    	  while(enums.hasMoreElements()) {            // hasMoreElements() Enum�� �� ���� ��ü�� �ִ��� üũ�Ѵ�. ������ false ��ȯ
    	      AbstractButton ab = enums.nextElement();    // ���׸����� AbstractButton �̴ϱ� �翬�� AbstractButton���� �޾ƾ���
    	      jb = (JRadioButton)ab;         // ����ȯ. ���� ���ٰ� ������ ���ļ� �ٷ� ����ȯ �ؼ� �޾Ƶ� �ȴ�.
    	   
    	      if(jb.isSelected())                         // �޾Ƴ� ������ư�� üũ ���¸� Ȯ���Ѵ�. üũ�Ǿ������ true ��ȯ.
    	    	  value = jb.getText().trim(); //getText() �޼ҵ�� ���ڿ� �޾Ƴ���.
    	  }
      }
}

class MenuPopupSub extends JDialog implements ActionListener{

	JButton back2,order2;
	SubwayOrder je;
	SpinnerNumberModel numModel;
	JRadioButton small,big;
	ButtonGroup group3;
	String data,lenght;
	Enumeration<AbstractButton> enums;
	JRadioButton jb;
	int price=0;
	
      public MenuPopupSub(SubwayOrder je,String title, boolean modal, String data) {  // data�� �̹��� ���ϸ�
         super(je,title,modal);
         this.je = je;
         this.setSize(470,500);
         this.setLayout(null);
         this.getContentPane().setBackground(Color.WHITE);
         this.data = data+"";
         
         JPanel imgPnl = new JPanel();
         imgPnl.setBackground(Color.WHITE);
         ImageIcon imgIcon = new ImageIcon(data);
         JLabel imglbl = new JLabel(imgIcon);
         imgPnl.setBounds(10, 10, 430, 230);
         imgPnl.add(imglbl);
         
         JPanel numPnl = new JPanel();
         numPnl.setBackground(Color.WHITE);
         JLabel lbl = new JLabel("����");
         lbl.setFont(new Font("����",Font.BOLD , 20));
         numModel = new SpinnerNumberModel(1,1,20,1);
         JSpinner sp = new JSpinner(numModel);
         sp.setFont(new Font("����",Font.BOLD , 20));
         numPnl.setBounds(100, 250, 250, 50);
         numPnl.add(lbl);
         numPnl.add(sp); 
         
         JPanel cmPnl = new JPanel();
         cmPnl.setBackground(Color.WHITE);
         small = new JRadioButton("15cm",true);
         small.setBackground(Color.WHITE);
         small.setFont(new Font("����",Font.BOLD , 20));
         big = new JRadioButton("30cm");
         big.setBackground(Color.WHITE);
         big.setFont(new Font("����",Font.BOLD , 20));
         group3 = new ButtonGroup();
         group3.add(small);
         group3.add(big);
         cmPnl.add(small);
         cmPnl.add(big);
         cmPnl.setBounds(100, 320, 250, 50);
        
         JPanel orderPnl2 = new JPanel();
         orderPnl2.setBackground(Color.WHITE);
         order2 = new JButton("�ֹ�");
         order2.setFont(new Font("����",Font.BOLD , 20));
         order2.setBackground(new Color(1,152,71));
         order2.setForeground(Color.WHITE);
         back2 = new JButton("�ڷΰ���");
         back2.setFont(new Font("����",Font.BOLD , 20));
         order2.addActionListener(this);
         back2.addActionListener(this);
         back2.setForeground(Color.WHITE);
         back2.setBackground(new Color(1,152,71));
         orderPnl2.add(order2);
         orderPnl2.add(back2);
         orderPnl2.setBounds(110, 400, 250, 50);
         
         this.add(imgPnl);
         this.add(numPnl);
         this.add(cmPnl);
         this.add(orderPnl2);
         this.setVisible(true);
      }

      @Override
      public void actionPerformed(ActionEvent e) {
         if(e.getSource() == back2){
            this.dispose();
         }else if(e.getSource() == order2){
        	 order2();
         }
      }
      
      void order2(){
    	 enums = group3.getElements();
     	 findGroup();  //�׷� ���� �� ã��
     	 String menu = null;
     	 int countMenu=0;
     	 for(int i =0; i<je.orders.getRowCount();i++){
     		menu = data.substring(0,data.lastIndexOf("."));
     		 if(menu.equals(je.orders.getValueAt(i, 0)+"") && 
     				 lenght.equals(je.orders.getValueAt(i, 3)+"")){
     			 countMenu = Integer.parseInt(je.orders.getValueAt(i, 2)+"") + 
     					 Integer.parseInt(numModel.getNumber()+"");
     			 je.orders.setValueAt(countMenu, i, 2);
     			 break;
     		 }
     	 }
     	 if(countMenu == 0){
     		File f = new File("subway.txt");
            FileReader fr = null;
            BufferedReader br = null;
            try {
   				fr = new FileReader(f);
   				br = new BufferedReader(fr);
   				String l = null;
   				while((l=br.readLine()) != null){
   					if(l.split("/")[3].equals(this.data)){
   						
   						String[] order = new String[4];
   						order[0] = l.split("/")[0];
   						if(lenght.equals("15cm")){
   							order[1] = l.split("/")[1];
   							price = Integer.parseInt(l.split("/")[1]);
   						}else {
   							order[1] = l.split("/")[2];
   							price = Integer.parseInt(l.split("/")[2]);
   						}
   						order[2] = numModel.getNumber()+"";
   						order[3] = lenght;
   						
   						je.model.addRow(order);
   						
   						DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
   					    dtcr.setHorizontalAlignment(SwingConstants.CENTER);
   					    TableColumnModel tcm =je.orders.getColumnModel();
   					    for(int i = 0 ; i<tcm.getColumnCount(); i++){
   					    	tcm.getColumn(i).setCellRenderer(dtcr);
   					    }
   					}
   				}
   				je.total();
   			} catch (FileNotFoundException e1) {
   				// TODO Auto-generated catch block
   				e1.printStackTrace();
   			} catch (IOException e1) {
   				// TODO Auto-generated catch block
   				e1.printStackTrace();
   			}
     	 }
     	this.dispose();
      }
      void findGroup(){
    	  while(enums.hasMoreElements()) {            // hasMoreElements() Enum�� �� ���� ��ü�� �ִ��� üũ�Ѵ�. ������ false ��ȯ
    	      AbstractButton ab = enums.nextElement();    // ���׸����� AbstractButton �̴ϱ� �翬�� AbstractButton���� �޾ƾ���
    	      jb = (JRadioButton)ab;         // ����ȯ. ���� ���ٰ� ������ ���ļ� �ٷ� ����ȯ �ؼ� �޾Ƶ� �ȴ�.
    	   
    	      if(jb.isSelected())                         // �޾Ƴ� ������ư�� üũ ���¸� Ȯ���Ѵ�. üũ�Ǿ������ true ��ȯ.
    	    	  lenght = jb.getText().trim(); //getText() �޼ҵ�� ���ڿ� �޾Ƴ���.
    	  }
      }
}