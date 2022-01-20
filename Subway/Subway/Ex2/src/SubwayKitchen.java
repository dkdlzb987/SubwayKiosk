import java.awt.Color;
import java.awt.Font;
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
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class SubwayKitchen extends JFrame implements MouseListener, ActionListener {
   JButton CookingCompleted, orderCancel, recall;
   BufferedReader br;
   JTable kitchen;
   DefaultTableModel model;
   int selectedRow = -1;

   public SubwayKitchen() {
      this.setDefaultCloseOperation(3);
      this.setSize(1500, 750);
      this.setTitle("주방 전용");
      this.getContentPane().setBackground(Color.WHITE);
      this.setLayout(null);
      
      Timer timer = new Timer();
      TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			orderCall();
			
		}
      };
      timer.schedule(task, 5000, 5000);

      String header[] = { "COUNT", "메뉴","길이", "수량", "포장", "카드", "현금" };
      String contents[][] = {};
      model = new DefaultTableModel(contents, header);
      kitchen = new JTable(model);
      kitchen.setRowHeight(33);
      kitchen.setFont(new Font("굴림",Font.BOLD,18));
      kitchen.addMouseListener(this);
      JScrollPane kitchenPane = new JScrollPane(kitchen);
      kitchenPane.getViewport().setBackground(Color.WHITE);
      kitchenPane.setBounds(40, 60, 1410, 500); // 메뉴 조회공간 및 스크롤

      CookingCompleted = new JButton("조리완료");// 조리완료 버튼
      CookingCompleted.setFont(new Font("굴림", Font.BOLD, 40));
      CookingCompleted.setBounds(340, 600, 310, 80);
      CookingCompleted.addActionListener(this);
      CookingCompleted.setBackground(new Color(1,152,71));
      CookingCompleted.setForeground(Color.WHITE);
      CookingCompleted.setHorizontalAlignment(JLabel.CENTER);

      orderCancel = new JButton("주문취소");// 주문취소 버튼
      orderCancel.setFont(new Font("굴림", Font.BOLD, 40));
      orderCancel.setBounds(850, 600, 300, 80);
      orderCancel.addActionListener(this);
      orderCancel.setBackground(new Color(1,152,71));
      orderCancel.setForeground(Color.WHITE);
      orderCancel.setHorizontalAlignment(JLabel.CENTER);
      
      recall = new JButton("새로고침");
      recall.setBounds(1350, 560, 100, 30);
      recall.setBackground(new Color(1,152,71));
      recall.setForeground(Color.WHITE);
      recall.addActionListener(this);

      orderCall();
      this.add(kitchenPane);
      this.add(CookingCompleted);
      this.add(orderCancel);
      this.add(recall);
      this.setVisible(true);
   }

   @Override
   public void mouseEntered(MouseEvent e) {}
   @Override
   public void mouseExited(MouseEvent e) {}
   @Override
   public void mousePressed(MouseEvent e) {}
   @Override
   public void mouseReleased(MouseEvent e) {}
   @Override
   public void mouseClicked(MouseEvent e) {
      selectedRow = kitchen.getSelectedRow();// 선택한 줄 번호 가져오기
   }
   
   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == CookingCompleted) {
    	  cookCompleted();
      } else if (e.getSource() == orderCancel) {
    	  orderCancel();
      }else if(e.getSource() == recall){ //연동실행했을때 새로운값 입력 후 누르면 다시 불러옴
    	  orderCall();
      }
   }
   public static void main(String[] args) {
	      new SubwayKitchen();
	   }

   void orderCall() {
	  model.setNumRows(0);
      File f = new File("order.txt");
      FileReader fr = null;
      BufferedReader br = null;
      try {
         fr = new FileReader(f);
         br = new BufferedReader(fr);
         String l = null;
         while ((l = br.readLine()) != null) {
            String[] str = l.split("/");
            model.addRow(str);
         }
         DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		 dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		 TableColumnModel tcm =kitchen.getColumnModel();
		 for(int i = 0 ; i<tcm.getColumnCount(); i++){
		  	tcm.getColumn(i).setCellRenderer(dtcr);
		 }

      } catch (FileNotFoundException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      } catch (IOException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      } finally {
         if (br != null) {
            try {
               br.close();
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
         }
      }
   }
   	void orderWrite(){
   		File fOrder = new File("order.txt");
   		FileWriter fw = null;
   		PrintWriter pw = null;
   		try {
   			fw = new FileWriter(fOrder);
   			pw = new PrintWriter(fw);
   			for(int i=0;i<kitchen.getRowCount();i++){//모든 줄 읽어오기 - 한줄 읽어와서 한칸씩 다시 읽어오기
   				for(int j=0;j<kitchen.getColumnCount();j++){
   					pw.print(kitchen.getValueAt(i, j));
   					if(j<(kitchen.getColumnCount()-1)){
   						pw.print("/");
   					}
   				}   
           pw.println();
        }
		
   		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			if(pw !=null){
			pw.close();
			}
		}
   	}
   	void cookCompleted(){
   		if(selectedRow < 0 ){
  		  JOptionPane.showMessageDialog(this, "행을 선택해주세요.");
  	  }else{
  		  File f = new File("sales.txt");
      	  FileWriter fw = null;
      	  PrintWriter pw = null;
      	  int count=0, startSelectedRow = 0;
      	  try {
              fw = new FileWriter(f,true);
              pw = new PrintWriter(fw);
              for (int i = 0; i < kitchen.getRowCount(); i++) {  //중복 ID 시작점 찾기
              	if(kitchen.getValueAt(i, 0).equals(kitchen.getValueAt(selectedRow, 0))){
              		startSelectedRow = i;
              		break;
              	}
              }
              for (int i = 0; i < kitchen.getRowCount(); i++) {
              	if(kitchen.getValueAt(i, 0).equals(kitchen.getValueAt(selectedRow, 0))){  //중복 ID 갯수 찾기
              		pw.println(kitchen.getValueAt(i,1) +"("+ kitchen.getValueAt(i,2) + ")" + "/"+ kitchen.getValueAt(i,3)+"/"+ kitchen.getValueAt(i,5)+"/"+ kitchen.getValueAt(i,6));
              		count++;
              	}
              }
              for(int i = startSelectedRow ; i<(startSelectedRow+count) ; i++){ //시작지점부터 삭제하면 다음아이디는 시작지점 row값을 가진다
              	model.removeRow(startSelectedRow);
              }
              orderWrite();
           } catch (IOException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
           } finally {
              if (pw != null) {
                 pw.close();
              }
           }
  	  }
   	}
   	void orderCancel(){
   		if(selectedRow < 0 ){
  		  JOptionPane.showMessageDialog(this, "행을 선택해주세요.");
  	  }else{
  		  File f = new File("order.txt");
  		  FileWriter fw = null;
  		  PrintWriter pw = null;
  		  
  		  try {
			fw = new FileWriter(f);
			pw = new PrintWriter(fw);
			int count=0, startSelectedRow = 0;
	  		for (int i = 0; i < kitchen.getRowCount(); i++) {  //중복 ID 시작점 찾기
	            if(kitchen.getValueAt(i, 0).equals(kitchen.getValueAt(selectedRow, 0))){
	            	startSelectedRow = i;
	            	break;
	            }
	         }
	         for (int i = 0; i < kitchen.getRowCount(); i++) {
	            if(kitchen.getValueAt(i, 0).equals(kitchen.getValueAt(selectedRow, 0))){  //중복 ID 갯수 찾기
	            	count++;
	            }
	         }
	         for(int i = startSelectedRow ; i<(startSelectedRow+count) ; i++){ //시작지점부터 삭제하면 다음아이디는 시작지점 row값을 가진다
	            model.removeRow(startSelectedRow);
	         }
	         for(int i=0;i<kitchen.getRowCount();i++){
	        	 pw.println(kitchen.getValueAt(i,0) +"/"+kitchen.getValueAt(i,1) +"/"+kitchen.getValueAt(i,2) +"/"
	        			 +kitchen.getValueAt(i,3) +"/"+kitchen.getValueAt(i,4) +"/"+kitchen.getValueAt(i,5) +"/"
	        			 +kitchen.getValueAt(i,6));
	         }
		  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }finally{
			  if(pw !=null){
				  pw.close();
			  }
		  }
  	  }
   	}
}