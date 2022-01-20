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
   String[] header = {"메뉴","가격","수량","길이"};
   String[][] contents={   }; 
   JTable orders;
   DefaultTableModel model;
   int selectedRow = -1, sum = 0, countNum=0;
   JLabel lblsum, mainlbl;
   
   public SubwayOrder() {
      this.setDefaultCloseOperation(3);
      this.setSize(1500,1000);
      this.setTitle("주문");
      this.setLayout(null);
      this.getContentPane().setBackground(Color.WHITE);
      
       //상단 이미지 생성 및 크기 설정
      ImageIcon mainIcon = new ImageIcon("subway.jpg");
      mainlbl = new JLabel(mainIcon);
      mainlbl.addMouseListener(this);
      mainlbl.setBounds(0, 0, 1500, 150); 
      
      //메뉴 조회공간
      pnlMenu = new JPanel(new GridLayout(0,3));
      JScrollPane spMenu = new JScrollPane(pnlMenu);
      spMenu.getVerticalScrollBar().setUnitIncrement(150);  //스크롤 속도 조절
      spMenu.setBounds(10,180,730,750);
      // 버튼 만드는 클레스 넣을 예정
      buttonAdd();
      
      // 선택된 메뉴 조회
      model = new DefaultTableModel(contents,header);
      orders = new JTable(model);
      orders.setFont(new Font("굴림",Font.BOLD,18));
      orders.addMouseListener(this);
      JScrollPane orderPane = new JScrollPane(orders);
      orders.setRowHeight(40);
      orderPane.getViewport().setBackground(Color.WHITE);
      orderPane.setBounds(970, 180, 500, 380); 
      
      //합계
      lblsum = new JLabel("합계 :  원");
      lblsum.setBounds(1280, 570, 200, 20); 
      lblsum.setFont(new Font("굴림",Font.BOLD , 23));
      lblsum.addMouseListener(this);
      //버튼들
      cookieSet = new JButton("<HTML>"+"쿠"+"<br>"+"키"+"<br>"+"세"+"<br>"+"트"+"<br>",new ImageIcon("co.png"));
      cookieSet.setVerticalTextPosition(JButton.BOTTOM);  // 텍스트 아래로
      cookieSet.setHorizontalTextPosition(JButton.CENTER);
      cookieSet.setFont(new Font("HY견고딕",Font.BOLD , 40));
      cookieSet.setBounds(755, 180, 200, 365);
      cookieSet.setBackground(new Color(1,152,71));
      cookieSet.setForeground(Color.WHITE);
      cookieSet.addActionListener(this);
      wedgeSet = new JButton("<HTML>"+"웨"+"<br>"+"지"+"<br>"+"세"+"<br>"+"트",new ImageIcon("we.png"));
      wedgeSet.setVerticalTextPosition(JButton.BOTTOM);  // 텍스트 아래로
      wedgeSet.setHorizontalTextPosition(JButton.CENTER);
      wedgeSet.setFont(new Font("HY견고딕",Font.BOLD , 40));
      wedgeSet.setBounds(755, 565, 200, 365);
      wedgeSet.setBackground(new Color(1,152,71));
      wedgeSet.setForeground(Color.WHITE);
      wedgeSet.addActionListener(this);
      selCancel = new JButton("선택취소");
      selCancel.setFont(new Font("HY견고딕",Font.BOLD , 40));
      selCancel.setBounds(970, 610, 500, 100);
      selCancel.setBackground(new Color(1,152,71));
      selCancel.setForeground(Color.WHITE);
      selCancel.addActionListener(this);
      cancel = new JButton("주문취소");
      cancel.setBounds(970, 720, 500, 100);
      cancel.setFont(new Font("HY견고딕",Font.BOLD , 40));
      cancel.setBackground(new Color(1,152,71));
      cancel.setForeground(Color.WHITE);
      cancel.addActionListener(this);
      order = new JButton("결제");
      order.setBounds(970, 830, 500, 100);
      order.setFont(new Font("HY견고딕",Font.BOLD , 40));
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
            menuBtn[i] = new JButton(l.split("/")[1]+" 원", new ImageIcon(l.split("/")[3]+""));
            menuBtn[i].setVerticalTextPosition(JButton.BOTTOM);  // 텍스트 아래로
            menuBtn[i].setHorizontalTextPosition(JButton.CENTER); // 텍스트 가운데
            menuBtn[i].setBackground(Color.WHITE);
            menuBtn[i].setSize(300,300);
            menuBtn[i].setFont(new Font("고딕",Font.BOLD,30));
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
	   lblsum.setText("합계 : "+sum+" 원");
   }
   
   boolean selectSetCheck(){  //세트가 센드위치보다 많은지 확인한다
	   int setNum=0,menuNum=0;
 	   for(int i = 0 ; i <orders.getRowCount() ;i++){
 	  		if("쿠키세트".equals(orders.getValueAt(i, 0)) || "웨지세트".equals(orders.getValueAt(i, 0))){
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
   void set(String setName, String setPrice){  //메뉴명과 가격을 받아서 작업 진행
	   if(selectSetCheck() == true){
 		  JOptionPane.showMessageDialog(this,"세트메뉴가 더 많아집니다.\n주문내역확인해주세요.");
 	  }else{
 		  String[] set = new String[4];
     	  set[0] = setName;
     	  set[1] = setPrice;
     	  set[2] = "1";
     	  set[3] = " ";
     	  int setRow=-1;
     	  for(int i=0; i<orders.getRowCount(); i++){  // 몇 번째에 쿠키세트가 있는지 확인
     		  if(set[0].equals(orders.getValueAt(i, 0)+"")){
     			  setRow=i;
     		  }
     	  }
     	  if(setRow >= 0){  //같은게 있으면 값 변경
     		  int count = Integer.parseInt(orders.getValueAt(setRow, 2)+"");
     		  count++;
     		  orders.setValueAt(count, setRow, 2);
     	  }else{  //없으면 추가
     		  model.addRow(set);
     	  }
     	  total();
 	  }
   }
   @Override
   public void actionPerformed(ActionEvent e) {
      if(e.getSource() == order){
    	  if(orders.getRowCount() <= 0){
    		  JOptionPane.showMessageDialog(this,"주문내역을 확인해 주세요");
    	  }else {
    		  new PayPopupSub(this,"결제 팝업",true);
    	  }
      }else if(e.getSource() == selCancel){
    	  if(selectedRow == -1 || orders.getRowCount()==0){
              JOptionPane.showMessageDialog(this, "선택된 행이 없음");
           }else{
              model.removeRow(selectedRow);
              total();
           }
      }else if(e.getSource() == cancel){
    	  model.setNumRows(0);
    	  sum=0;
    	  lblsum.setText("합계 :  원");
      }else if(e.getSource() == cookieSet){  //쿠키세트 추가 버튼
    		set("쿠키세트","1900");
      }else if(e.getSource() == wedgeSet){
    		set("웨지세트","2400");
      }else{
         String strMenu = null;
         for(int i = 0 ; i<menuBtn.length;i++){
            if(e.getSource() == menuBtn[i]){
               strMenu = menuBtn[i].getIcon()+"";
            }
         }
         new MenuPopupSub(this,"메뉴",true,strMenu);
      }
   }
   @Override
   public void mouseClicked(MouseEvent e) {
	selectedRow = orders.getSelectedRow();
	if(e.getSource() == mainlbl  && e.getClickCount()==2){ //상표를 더블클릭하면 발생
		new SubwayKitchen();
	}
	if(e.getSource() == lblsum && e.getClickCount()==2){  //합계를 더블클릭하면 어드민으로
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
         JRadioButton take = new JRadioButton("포장",true);
         take.setBackground(Color.WHITE);
         take.setFont(new Font("굴림",Font.BOLD , 20));
         JRadioButton here = new JRadioButton("매장");
         here.setFont(new Font("굴림",Font.BOLD , 20));
         here.setBackground(Color.WHITE);
         group = new ButtonGroup();
         group.add(take);
         group.add(here);
         inoutPnl.add(take);
         inoutPnl.add(here);
         
         JPanel payPnl = new JPanel();
         payPnl.setBackground(Color.WHITE);
         JRadioButton cash = new JRadioButton("현금",true);
         cash.setBackground(Color.WHITE);
         cash.setFont(new Font("굴림",Font.BOLD , 20));
         JRadioButton card = new JRadioButton("카드");
         card.setBackground(Color.WHITE);
         card.setFont(new Font("굴림",Font.BOLD , 20));
         group2 = new ButtonGroup();
         group2.add(cash);
         group2.add(card);
         payPnl.add(cash);
         payPnl.add(card);
         
         JPanel orderPnl = new JPanel();
         orderPnl.setBackground(Color.WHITE);
         order = new JButton("주문");
         order.setFont(new Font("굴림",Font.BOLD , 20));
         order.setForeground(Color.WHITE);
         order.setBackground(new Color(1,152,71));
         back = new JButton("뒤로가기");
         back.setFont(new Font("굴림",Font.BOLD , 20));
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
   	   pack = value; //현금 카드 구분하기 위함
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
   			   if(value.equals("현금")){
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
   		   JOptionPane.showMessageDialog(this,"주문이 완료 되었습니다.");
   		   je.sum=0;
   		   je.lblsum.setText("합계 :  원");
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
    	  while(enums.hasMoreElements()) {            // hasMoreElements() Enum에 더 꺼낼 개체가 있는지 체크한다. 없으며 false 반환
    	      AbstractButton ab = enums.nextElement();    // 제네릭스가 AbstractButton 이니까 당연히 AbstractButton으로 받아야함
    	      jb = (JRadioButton)ab;         // 형변환. 물론 윗줄과 이줄을 합쳐서 바로 형변환 해서 받아도 된다.
    	   
    	      if(jb.isSelected())                         // 받아낸 라디오버튼의 체크 상태를 확인한다. 체크되었을경우 true 반환.
    	    	  value = jb.getText().trim(); //getText() 메소드로 문자열 받아낸다.
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
	
      public MenuPopupSub(SubwayOrder je,String title, boolean modal, String data) {  // data는 이미지 파일명
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
         JLabel lbl = new JLabel("개수");
         lbl.setFont(new Font("굴림",Font.BOLD , 20));
         numModel = new SpinnerNumberModel(1,1,20,1);
         JSpinner sp = new JSpinner(numModel);
         sp.setFont(new Font("굴림",Font.BOLD , 20));
         numPnl.setBounds(100, 250, 250, 50);
         numPnl.add(lbl);
         numPnl.add(sp); 
         
         JPanel cmPnl = new JPanel();
         cmPnl.setBackground(Color.WHITE);
         small = new JRadioButton("15cm",true);
         small.setBackground(Color.WHITE);
         small.setFont(new Font("굴림",Font.BOLD , 20));
         big = new JRadioButton("30cm");
         big.setBackground(Color.WHITE);
         big.setFont(new Font("굴림",Font.BOLD , 20));
         group3 = new ButtonGroup();
         group3.add(small);
         group3.add(big);
         cmPnl.add(small);
         cmPnl.add(big);
         cmPnl.setBounds(100, 320, 250, 50);
        
         JPanel orderPnl2 = new JPanel();
         orderPnl2.setBackground(Color.WHITE);
         order2 = new JButton("주문");
         order2.setFont(new Font("굴림",Font.BOLD , 20));
         order2.setBackground(new Color(1,152,71));
         order2.setForeground(Color.WHITE);
         back2 = new JButton("뒤로가기");
         back2.setFont(new Font("굴림",Font.BOLD , 20));
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
     	 findGroup();  //그룹 레디어스 값 찾기
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
    	  while(enums.hasMoreElements()) {            // hasMoreElements() Enum에 더 꺼낼 개체가 있는지 체크한다. 없으며 false 반환
    	      AbstractButton ab = enums.nextElement();    // 제네릭스가 AbstractButton 이니까 당연히 AbstractButton으로 받아야함
    	      jb = (JRadioButton)ab;         // 형변환. 물론 윗줄과 이줄을 합쳐서 바로 형변환 해서 받아도 된다.
    	   
    	      if(jb.isSelected())                         // 받아낸 라디오버튼의 체크 상태를 확인한다. 체크되었을경우 true 반환.
    	    	  lenght = jb.getText().trim(); //getText() 메소드로 문자열 받아낸다.
    	  }
      }
}