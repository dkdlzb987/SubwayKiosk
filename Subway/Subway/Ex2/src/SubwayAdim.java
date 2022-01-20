import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class SubwayAdim extends JFrame implements ActionListener, MouseListener {
   JButton menuch, menuad, saleshis, menupix;
   DefaultTableModel model;
   JTable tbl;
   // JTable menu;
   JButton menudel, btnImage;// 전역으로 빼야 나중에 액셔에서 사용가능
   int selectedRow = -1;
   boolean blLogin = false; // 로그인 시 값 지정(boolean이 참,거짓 뿐이여서 사용함)

   public SubwayAdim() {
      new login(this, "로그인", true);
      if (blLogin == false) { // 로그인 창 닫으면 종료되도록 함
         JOptionPane.showMessageDialog(this, "로그인되지 않아 종료합니다.");
      } else { // 로그인이 되면 원래 admin 작업진행
         this.setDefaultCloseOperation(3);
         this.setSize(1500, 750);
         this.setTitle("관리자전용");
         this.setLayout(null);
         this.getContentPane().setBackground(Color.WHITE);

         String[] header = { "제품명", "15CM 가격", "30CM 가격", "이미지 명" };
         String[][] contents = {};// JTable 생성시 공간은 반드시 확보해야함
         model = new DefaultTableModel(contents, header);
         tbl = new JTable(model);
         tbl.setRowHeight(20);
         tbl.setFont(new Font("굴림", Font.BOLD, 18));
         tbl.addMouseListener(this);
         JTableHeader header1 = tbl.getTableHeader();
         header1.setBackground(new Color(1, 152, 71));
         header1.setForeground(Color.WHITE);
         header1.setFont(new Font("굴림", Font.BOLD, 12));

         JScrollPane sp = new JScrollPane(tbl);
         sp.getViewport().setBackground(Color.WHITE);
         sp.setBounds(40, 60, 1410, 500);

         menuCall();
         menuch = new JButton("메뉴추가");
         menuch.setFont(new Font("굴림", Font.BOLD, 30));
         menuch.setBounds(40, 600, 310, 80);
         menuch.addActionListener(this);
         menuch.setBackground(new Color(1, 152, 71));
         menuch.setForeground(Color.WHITE);

         menudel = new JButton("메뉴삭제");
         menudel.setBounds(410, 600, 310, 80);
         menudel.setFont(new Font("굴림", Font.BOLD, 30));
         menudel.addActionListener(this);
         menudel.setBackground(new Color(1, 152, 71));
         menudel.setForeground(Color.WHITE);

         menupix = new JButton("메뉴수정");
         menupix.setBounds(780, 600, 310, 80);
         menupix.setFont(new Font("굴림", Font.BOLD, 30));
         menupix.addActionListener(this);
         menupix.setBackground(new Color(1, 152, 71));
         menupix.setForeground(Color.WHITE);

         saleshis = new JButton("판매내역");
         saleshis.addActionListener(this);
         saleshis.setBounds(1150, 600, 300, 80);
         saleshis.setFont(new Font("굴림", Font.BOLD, 30));
         saleshis.setBackground(new Color(1, 152, 71));
         saleshis.setForeground(Color.WHITE);

         btnImage = new JButton("선택한 행 이미지 파일명 변경하기");
         btnImage.setBounds(1200, 560, 250, 30);
         btnImage.addActionListener(this);
         btnImage.setBackground(new Color(1, 152, 71));
         btnImage.setForeground(Color.WHITE);
         btnImage.setFont(new Font("굴림", Font.BOLD, 12));

         this.add(saleshis);
         this.add(menudel);
         this.add(menuch);
         this.add(menupix);
         this.add(btnImage);
         this.add(sp);
         this.setVisible(true);
         this.add(sp);
         this.setVisible(true);
      }
   }

   void menuCall() {
      File f = new File("subway.txt");
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
         TableColumnModel tcm = tbl.getColumnModel();
         for (int i = 0; i < tcm.getColumnCount(); i++) {
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

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == menudel) {
         model.removeRow(selectedRow);
         JOptionPane.showMessageDialog(this, "삭제되었습니다.");

         File f = new File("subway.txt");
         FileWriter fw = null;
         PrintWriter pw = null;
         try {
            fw = new FileWriter(f);
            pw = new PrintWriter(fw);
            for (int i = 0; i < tbl.getRowCount(); i++) {// 모든 줄 읽어오기 - 한줄
               // 읽어와서 한칸씩 다시
               // 읽어오기
               for (int j = 0; j < tbl.getColumnCount(); j++) {
                  pw.print(tbl.getValueAt(i, j));
                  if (j < (tbl.getColumnCount() - 1)) {
                     pw.print("/");
                  }
               }
               pw.println();
            }

         } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         } finally {
            if (pw != null) {
               pw.close();
            }
         }
      } else if (e.getSource() == menuch) {
         new menuManager(this, "메뉴추가", true);
      } else if (e.getSource() == saleshis) {
         new salesManager(this, "매출관리", true);
      } else if (e.getSource() == menupix) {
         File f = new File("subway.txt");
         FileWriter fw = null;
         PrintWriter pw = null;
         try {
            fw = new FileWriter(f);
            pw = new PrintWriter(fw);

            for (int i = 0; i < tbl.getRowCount(); i++) {
               for (int j = 0; j < tbl.getColumnCount(); j++) {
                  pw.print(tbl.getValueAt(i, j));
                  if (j < (tbl.getColumnCount() - 1)) {
                     pw.print("/");
                  }
               }
               pw.println();
            }
         } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         } finally {
            if (pw != null) {
               pw.close();
            }
         }
      } else if (e.getSource() == btnImage) {
         if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "행을 먼저 선택해주세요");
         } else {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("image File(jpg,jpeg,png)", "jpg", "jpeg", "png"));
            chooser.setCurrentDirectory(new File(".\\"));
            int result = chooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
               System.out.println(selectedRow + ":" + chooser.getSelectedFile().getName());
               tbl.setValueAt(chooser.getSelectedFile().getName(), selectedRow, 3);
               JOptionPane.showMessageDialog(this, "수정을 눌러주셔야 적용됩니다.");
            } else {
               JOptionPane.showMessageDialog(this, "파일이 선택되지 않았습니다.");
            }

         }
      }

   }

   @Override
   public void mouseClicked(MouseEvent e) {
      selectedRow = tbl.getSelectedRow();
   }

   @Override
   public void mouseEntered(MouseEvent e) {   }

   @Override
   public void mouseExited(MouseEvent e) {   }

   @Override
   public void mousePressed(MouseEvent e) {   }

   @Override
   public void mouseReleased(MouseEvent e) {   }

   public static void main(String[] args) {
      new SubwayAdim();
   }
}

class salesManager extends JDialog implements ActionListener {
   SubwayAdim je;
   DefaultTableModel modelSales;
   JLabel total, fileName, lblcard, lblcash;
   JButton end, callFile;
   JTable tbMenu;
   String[] header = { "메뉴", "수량", "카드", "현금" };
   String[][] contents = {};
   int sumSales = 0, sumCard = 0, sumCash = 0;

   public salesManager(SubwayAdim je, String title, boolean modal) {
      super(je, title, modal);
      this.je = je;
      this.setSize(500, 500);

      fileName = new JLabel("오늘");
      fileName.setFont(new Font("굴림", Font.BOLD, 20));

      modelSales = new DefaultTableModel(contents, header);
      tbMenu = new JTable(modelSales);
      JScrollPane spMenu = new JScrollPane(tbMenu);
      spMenu.getViewport().setBackground(Color.WHITE);

      JPanel bottom = new JPanel(new GridLayout(0, 2));
      bottom.setBackground(Color.WHITE);
      lblcard = new JLabel("카드 : 0원");
      lblcard.setFont(new Font("굴림", Font.BOLD, 20));
      lblcash = new JLabel("현금 : 0원");
      lblcash.setFont(new Font("굴림", Font.BOLD, 20));
      total = new JLabel("합계 : 0원");
      total.setFont(new Font("굴림", Font.BOLD, 20));
      JPanel bottom2 = new JPanel();
      bottom2.setBackground(Color.WHITE);
      end = new JButton("마감");
      end.setBackground(new Color(1,152,71));
      end.setForeground(Color.WHITE);
      callFile = new JButton("불러오기");
      callFile.setBackground(new Color(1,152,71));
      callFile.setForeground(Color.WHITE);
      bottom2.add(end);
      bottom2.add(callFile);
      end.addActionListener(this);
      callFile.addActionListener(this);

      bottom.add(lblcard);
      bottom.add(lblcash);
      bottom.add(total);
      bottom.add(bottom2);

      this.add(fileName, "North");
      this.add(spMenu);
      this.add(bottom, "South");
      showsales();
      this.setVisible(true);
   }

   void showsales() {
      File f = new File("sales.txt");
      FileReader fr = null;
      BufferedReader br = null;

      try {
         fr = new FileReader(f);
         br = new BufferedReader(fr);
         String l = null;
         while ((l = br.readLine()) != null) {
            modelSales.addRow(l.split("/"));
            sumCard += Integer.parseInt(l.split("/")[2]);
            sumCash += Integer.parseInt(l.split("/")[3]);
            sumSales = sumCard + sumCash;
         }
         lblcard.setText("카드 : " + sumCard + " 원");
         lblcash.setText("현금 : " + sumCash + " 원");
         total.setText("합계 : " + sumSales + " 원");
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   void salesEnd(){
	   if (tbMenu.getRowCount() == 0) {
           JOptionPane.showMessageDialog(this, "판매내역이 없습니다.");
        } else if (fileName.getText().equals("오늘")) {
           SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
           String format_time1 = format1.format(System.currentTimeMillis());
           File f = new File("sales/" + format_time1 + ".txt");
           FileWriter fw = null;
           PrintWriter pw = null;

           File fSales = new File("sales.txt");
           FileWriter fwSales = null;
           PrintWriter pwSales = null;
           try {
              fw = new FileWriter(f);
              pw = new PrintWriter(fw);
              fwSales = new FileWriter(fSales);
              pwSales = new PrintWriter(fwSales);
              for (int i = 0; i < tbMenu.getRowCount(); i++) {
                 for (int j = 0; j < tbMenu.getColumnCount(); j++) {
                    pw.print(tbMenu.getValueAt(i, j));
                    if (j < (tbMenu.getColumnCount() - 1)) {
                       pw.print("/");
                    }
                 }
                 pw.println();
              }
              pwSales.print("");
              modelSales.setNumRows(0);
              lblcard.setText("카드 : 원");
              lblcash.setText("현금 : 원");
              total.setText("합계 : 원");
              fileName.setText("마감하셨습니다.");
              JOptionPane.showMessageDialog(this, "마감했습니다.");
           } catch (IOException e1) {
              // TODO Auto-generated catch block
              e1.printStackTrace();
           } finally {
              if (pw != null) {
                 pw.close();
              }
              if (pwSales != null) {
                 pwSales.close();
              }
           }
        } else {
           JOptionPane.showMessageDialog(this, "당일마감 내용이 아닙니다.");
        }
   }
   
   void callFile(){
	   sumSales = 0;
       sumCard = 0;
       sumCash = 0;
       modelSales.setNumRows(0);
       JFileChooser chooser = new JFileChooser();
       chooser.setFileFilter(new FileNameExtensionFilter("TXT File", "txt"));
       chooser.setCurrentDirectory(new File(".\\sales"));
       int result = chooser.showOpenDialog(this);

       if (result == JFileChooser.APPROVE_OPTION) {
          String l = chooser.getSelectedFile().getName();
          File f = new File(".\\sales\\" + l);
          FileReader fr = null;
          BufferedReader br = null;
          try {
             fr = new FileReader(f);
             br = new BufferedReader(fr);
             String str = null;
             while ((str = br.readLine()) != null) {
                modelSales.addRow(str.split("/"));
                sumCard += Integer.parseInt(str.split("/")[2]);
                sumCash += Integer.parseInt(str.split("/")[3]);
                sumSales = sumCard + sumCash;
             }
             System.out.println(sumCard +":"+sumCash);
             lblcard.setText("카드 : " + sumCard + " 원");
             lblcash.setText("현금 : " + sumCash + " 원");
             total.setText("합계 : " + sumSales + " 원");
             fileName.setText(l);
          } catch (FileNotFoundException e1) {
             // TODO Auto-generated catch block
             e1.printStackTrace();
          } catch (IOException e1) {
             // TODO Auto-generated catch block
             e1.printStackTrace();
          } finally {
             try {
                if (br != null) {
                   br.close();
                }
             } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
             }
          }
       } else {
          JOptionPane.showMessageDialog(this, "파일이 선택되지 않았습니다.");
       }
   }
   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == end) {
    	  salesEnd();
      } else if (e.getSource() == callFile) {
    	  callFile();
      }
   }
}

class menuManager extends JDialog implements ActionListener {
   JTextField tfIng, tfMenu, tfPrice15, tfPrice30;
   JButton btnchange, btnAdd, btnImageAdd;
   SubwayAdim je;

   public menuManager(SubwayAdim je, String title, boolean modal) {

      super(je, title, modal);
      this.je = je;
      this.setSize(300, 400);
      this.setBackground(Color.WHITE);
      JPanel top = new JPanel(new GridLayout(0, 2));
      top.setBackground(Color.WHITE);
      JLabel img = new JLabel("이미지");
      img.setHorizontalAlignment(JLabel.CENTER);
      JLabel menuName = new JLabel("메뉴명");
      menuName.setHorizontalAlignment(JLabel.CENTER);
      JLabel price15 = new JLabel("15Cm가격");
      price15.setHorizontalAlignment(JLabel.CENTER);
      JLabel price30 = new JLabel("30Cm가격");
      price30.setHorizontalAlignment(JLabel.CENTER);

      tfIng = new JTextField();
      tfMenu = new JTextField();
      tfPrice15 = new JTextField();
      tfPrice30 = new JTextField();

      JPanel pnlBottom = new JPanel(new FlowLayout());
      pnlBottom.setBackground(Color.WHITE);
      btnAdd = new JButton("추가");
      btnAdd.setBackground(new Color(1,152,71));
      btnAdd.setForeground(Color.WHITE);
      btnAdd.addActionListener(this);
      btnImageAdd = new JButton("이미지파일찾기");
      btnImageAdd.setForeground(Color.WHITE);
      btnImageAdd.setBackground(new Color(1,152,71));
      btnImageAdd.addActionListener(this);

      pnlBottom.add(btnImageAdd);
      pnlBottom.add(btnAdd);

      top.add(img);
      top.add(tfIng);
      top.add(menuName);
      top.add(tfMenu);
      top.add(price15);
      top.add(tfPrice15);
      top.add(price30);
      top.add(tfPrice30);
      this.add(top);
      this.add(pnlBottom, "South");

      this.setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == btnImageAdd) {
         JFileChooser chooser = new JFileChooser();
         chooser.setFileFilter(new FileNameExtensionFilter("image File(jpg,jpeg,png)", "jpg", "jpeg", "png"));
         chooser.setCurrentDirectory(new File(".\\"));
         int result = chooser.showOpenDialog(this);

         if (result == JFileChooser.APPROVE_OPTION) {
            tfIng.setText(chooser.getSelectedFile().getName());
            String l = chooser.getSelectedFile().getName();
            tfMenu.setText(l.substring(0, l.lastIndexOf(".")));
         }
      } else if (e.getSource() == btnAdd) {
         File f = new File("subway.txt");
         FileReader fr = null;
         BufferedReader br = null;
         FileWriter fw = null;
         PrintWriter pw = null;

         try {
            fr = new FileReader(f);
            br = new BufferedReader(fr);
            fw = new FileWriter(f, true);
            pw = new PrintWriter(fw);
            String[] addMenu = new String[4];
            int equal = 0;

            if (e.getSource() == btnAdd) {
               String l = null;
               while ((l = br.readLine()) != null) {

                  if (l.split("/")[0].equals(tfMenu.getText())) {
                     JOptionPane.showMessageDialog(this, "이미 존재하는 메뉴입니다.");
                     equal++;
                     break;
                  }
               }
               if (equal == 0) {
                  addMenu[0] = tfMenu.getText();
                  addMenu[1] = tfPrice15.getText();
                  addMenu[2] = tfPrice30.getText();
                  addMenu[3] = tfIng.getText();
                  pw.println(tfMenu.getText() + "/" + tfPrice15.getText() + "/" + tfPrice30.getText() + "/"
                        + tfIng.getText() + "/");
                  je.model.addRow(addMenu);
               }
               this.dispose();
            }

         } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         } finally {
            if (pw != null)
               pw.close();
            if (br != null)
               try {
                  br.close();
               } catch (IOException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               }
         }
      }
   }
}

class login extends JDialog implements ActionListener, KeyListener { // 로그인 페이지
   JButton btnLogin;
   JTextField tfID;
   JPasswordField tfPass;
   SubwayAdim je;

   public login(SubwayAdim je, String title, boolean modal) {
      super(je, title, modal);
      this.je = je;
      this.setSize(500, 300);
      this.setLayout(null);
      setContentPane(new JLabel(new ImageIcon("sw.png")));
      JLabel lblId = new JLabel("아이디");
      lblId.setBounds(150, 100, 100, 20);
      JLabel lblPass = new JLabel("비밀번호");
      lblPass.setBounds(150, 130, 100, 20);
      tfID = new JTextField(20);
      tfID.setBounds(250, 100, 100, 20);
      tfPass = new JPasswordField(20);
      tfPass.setBounds(250, 130, 100, 20);
      tfPass.addKeyListener(this);

      btnLogin = new JButton("로그인");
      btnLogin.setBounds(200, 170, 80, 30);
      btnLogin.addActionListener(this);
      

      this.add(lblId);
      this.add(lblPass);
      this.add(tfID);
      this.add(tfPass);
      this.add(btnLogin);
      this.setVisible(true);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      String s = new String(tfPass.getPassword()); // JPasswordField 라 그냥가져오면
      // 암호화되어서 문자열로 변환
      if (e.getSource() == btnLogin) {
         if (tfID.getText().equals("admin") && s.equals("123")) {
            JOptionPane.showMessageDialog(this, "로그인 되셨습니다.");
            je.blLogin = true;
            this.dispose();
         } else if (tfID.getText().equals("admin")) {
            JOptionPane.showMessageDialog(this, "비밀번호를 확인해주세요.");
         } else if (s.equals("123")) {
            JOptionPane.showMessageDialog(this, "아이디를 확인해주세요.");
         } else {
            JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 확인해주세요.");
         }
      }
   }

   @Override
   public void keyPressed(KeyEvent e) {

      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
         Toolkit.getDefaultToolkit().beep();
         btnLogin.doClick();
      }
   }

   @Override
   public void keyReleased(KeyEvent e) {}

   @Override
   public void keyTyped(KeyEvent e) {}
}