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
   JButton menudel, btnImage;// �������� ���� ���߿� �׼ſ��� ��밡��
   int selectedRow = -1;
   boolean blLogin = false; // �α��� �� �� ����(boolean�� ��,���� ���̿��� �����)

   public SubwayAdim() {
      new login(this, "�α���", true);
      if (blLogin == false) { // �α��� â ������ ����ǵ��� ��
         JOptionPane.showMessageDialog(this, "�α��ε��� �ʾ� �����մϴ�.");
      } else { // �α����� �Ǹ� ���� admin �۾�����
         this.setDefaultCloseOperation(3);
         this.setSize(1500, 750);
         this.setTitle("����������");
         this.setLayout(null);
         this.getContentPane().setBackground(Color.WHITE);

         String[] header = { "��ǰ��", "15CM ����", "30CM ����", "�̹��� ��" };
         String[][] contents = {};// JTable ������ ������ �ݵ�� Ȯ���ؾ���
         model = new DefaultTableModel(contents, header);
         tbl = new JTable(model);
         tbl.setRowHeight(20);
         tbl.setFont(new Font("����", Font.BOLD, 18));
         tbl.addMouseListener(this);
         JTableHeader header1 = tbl.getTableHeader();
         header1.setBackground(new Color(1, 152, 71));
         header1.setForeground(Color.WHITE);
         header1.setFont(new Font("����", Font.BOLD, 12));

         JScrollPane sp = new JScrollPane(tbl);
         sp.getViewport().setBackground(Color.WHITE);
         sp.setBounds(40, 60, 1410, 500);

         menuCall();
         menuch = new JButton("�޴��߰�");
         menuch.setFont(new Font("����", Font.BOLD, 30));
         menuch.setBounds(40, 600, 310, 80);
         menuch.addActionListener(this);
         menuch.setBackground(new Color(1, 152, 71));
         menuch.setForeground(Color.WHITE);

         menudel = new JButton("�޴�����");
         menudel.setBounds(410, 600, 310, 80);
         menudel.setFont(new Font("����", Font.BOLD, 30));
         menudel.addActionListener(this);
         menudel.setBackground(new Color(1, 152, 71));
         menudel.setForeground(Color.WHITE);

         menupix = new JButton("�޴�����");
         menupix.setBounds(780, 600, 310, 80);
         menupix.setFont(new Font("����", Font.BOLD, 30));
         menupix.addActionListener(this);
         menupix.setBackground(new Color(1, 152, 71));
         menupix.setForeground(Color.WHITE);

         saleshis = new JButton("�Ǹų���");
         saleshis.addActionListener(this);
         saleshis.setBounds(1150, 600, 300, 80);
         saleshis.setFont(new Font("����", Font.BOLD, 30));
         saleshis.setBackground(new Color(1, 152, 71));
         saleshis.setForeground(Color.WHITE);

         btnImage = new JButton("������ �� �̹��� ���ϸ� �����ϱ�");
         btnImage.setBounds(1200, 560, 250, 30);
         btnImage.addActionListener(this);
         btnImage.setBackground(new Color(1, 152, 71));
         btnImage.setForeground(Color.WHITE);
         btnImage.setFont(new Font("����", Font.BOLD, 12));

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
         JOptionPane.showMessageDialog(this, "�����Ǿ����ϴ�.");

         File f = new File("subway.txt");
         FileWriter fw = null;
         PrintWriter pw = null;
         try {
            fw = new FileWriter(f);
            pw = new PrintWriter(fw);
            for (int i = 0; i < tbl.getRowCount(); i++) {// ��� �� �о���� - ����
               // �о�ͼ� ��ĭ�� �ٽ�
               // �о����
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
         new menuManager(this, "�޴��߰�", true);
      } else if (e.getSource() == saleshis) {
         new salesManager(this, "�������", true);
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
            JOptionPane.showMessageDialog(this, "���� ���� �������ּ���");
         } else {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("image File(jpg,jpeg,png)", "jpg", "jpeg", "png"));
            chooser.setCurrentDirectory(new File(".\\"));
            int result = chooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
               System.out.println(selectedRow + ":" + chooser.getSelectedFile().getName());
               tbl.setValueAt(chooser.getSelectedFile().getName(), selectedRow, 3);
               JOptionPane.showMessageDialog(this, "������ �����ּž� ����˴ϴ�.");
            } else {
               JOptionPane.showMessageDialog(this, "������ ���õ��� �ʾҽ��ϴ�.");
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
   String[] header = { "�޴�", "����", "ī��", "����" };
   String[][] contents = {};
   int sumSales = 0, sumCard = 0, sumCash = 0;

   public salesManager(SubwayAdim je, String title, boolean modal) {
      super(je, title, modal);
      this.je = je;
      this.setSize(500, 500);

      fileName = new JLabel("����");
      fileName.setFont(new Font("����", Font.BOLD, 20));

      modelSales = new DefaultTableModel(contents, header);
      tbMenu = new JTable(modelSales);
      JScrollPane spMenu = new JScrollPane(tbMenu);
      spMenu.getViewport().setBackground(Color.WHITE);

      JPanel bottom = new JPanel(new GridLayout(0, 2));
      bottom.setBackground(Color.WHITE);
      lblcard = new JLabel("ī�� : 0��");
      lblcard.setFont(new Font("����", Font.BOLD, 20));
      lblcash = new JLabel("���� : 0��");
      lblcash.setFont(new Font("����", Font.BOLD, 20));
      total = new JLabel("�հ� : 0��");
      total.setFont(new Font("����", Font.BOLD, 20));
      JPanel bottom2 = new JPanel();
      bottom2.setBackground(Color.WHITE);
      end = new JButton("����");
      end.setBackground(new Color(1,152,71));
      end.setForeground(Color.WHITE);
      callFile = new JButton("�ҷ�����");
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
         lblcard.setText("ī�� : " + sumCard + " ��");
         lblcash.setText("���� : " + sumCash + " ��");
         total.setText("�հ� : " + sumSales + " ��");
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
           JOptionPane.showMessageDialog(this, "�Ǹų����� �����ϴ�.");
        } else if (fileName.getText().equals("����")) {
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
              lblcard.setText("ī�� : ��");
              lblcash.setText("���� : ��");
              total.setText("�հ� : ��");
              fileName.setText("�����ϼ̽��ϴ�.");
              JOptionPane.showMessageDialog(this, "�����߽��ϴ�.");
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
           JOptionPane.showMessageDialog(this, "���ϸ��� ������ �ƴմϴ�.");
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
             lblcard.setText("ī�� : " + sumCard + " ��");
             lblcash.setText("���� : " + sumCash + " ��");
             total.setText("�հ� : " + sumSales + " ��");
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
          JOptionPane.showMessageDialog(this, "������ ���õ��� �ʾҽ��ϴ�.");
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
      JLabel img = new JLabel("�̹���");
      img.setHorizontalAlignment(JLabel.CENTER);
      JLabel menuName = new JLabel("�޴���");
      menuName.setHorizontalAlignment(JLabel.CENTER);
      JLabel price15 = new JLabel("15Cm����");
      price15.setHorizontalAlignment(JLabel.CENTER);
      JLabel price30 = new JLabel("30Cm����");
      price30.setHorizontalAlignment(JLabel.CENTER);

      tfIng = new JTextField();
      tfMenu = new JTextField();
      tfPrice15 = new JTextField();
      tfPrice30 = new JTextField();

      JPanel pnlBottom = new JPanel(new FlowLayout());
      pnlBottom.setBackground(Color.WHITE);
      btnAdd = new JButton("�߰�");
      btnAdd.setBackground(new Color(1,152,71));
      btnAdd.setForeground(Color.WHITE);
      btnAdd.addActionListener(this);
      btnImageAdd = new JButton("�̹�������ã��");
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
                     JOptionPane.showMessageDialog(this, "�̹� �����ϴ� �޴��Դϴ�.");
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

class login extends JDialog implements ActionListener, KeyListener { // �α��� ������
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
      JLabel lblId = new JLabel("���̵�");
      lblId.setBounds(150, 100, 100, 20);
      JLabel lblPass = new JLabel("��й�ȣ");
      lblPass.setBounds(150, 130, 100, 20);
      tfID = new JTextField(20);
      tfID.setBounds(250, 100, 100, 20);
      tfPass = new JPasswordField(20);
      tfPass.setBounds(250, 130, 100, 20);
      tfPass.addKeyListener(this);

      btnLogin = new JButton("�α���");
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
      String s = new String(tfPass.getPassword()); // JPasswordField �� �׳ɰ�������
      // ��ȣȭ�Ǿ ���ڿ��� ��ȯ
      if (e.getSource() == btnLogin) {
         if (tfID.getText().equals("admin") && s.equals("123")) {
            JOptionPane.showMessageDialog(this, "�α��� �Ǽ̽��ϴ�.");
            je.blLogin = true;
            this.dispose();
         } else if (tfID.getText().equals("admin")) {
            JOptionPane.showMessageDialog(this, "��й�ȣ�� Ȯ�����ּ���.");
         } else if (s.equals("123")) {
            JOptionPane.showMessageDialog(this, "���̵� Ȯ�����ּ���.");
         } else {
            JOptionPane.showMessageDialog(this, "���̵�� ��й�ȣ�� Ȯ�����ּ���.");
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