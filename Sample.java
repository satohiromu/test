import java.awt.*;
import java.awt.BorderLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;


public class Sample extends JFrame implements ActionListener, CaretListener{

	JTextField textField;
	JTextArea textArea;
	JDialog searchDialog;
	Matcher matcher = null;
	Pattern p = null;
	int caretPosition = 0;
	String updateCheck = "";
	//boolean exprCheck = false;
	int patternFlags = Pattern.LITERAL;

	JCheckBox exprHandle;
	JCheckBox upperOrLowerHandle;

	Sample(){
		setSize(640, 480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		textField = new JTextField(20);
//		JButton button = new JButton("search");
//		JButton button2 = new JButton("cancel");
		textArea = new JTextArea(50, 50);
		textArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

		textArea.addCaretListener(this);

		textField.setText("aaaaa");
		textArea.setText("aaaaa asdfg aaaaa\nbbbbb\nccccc\nddddd\neeeee");

		searchDialog = new JDialog(this, "Search", true);
		searchDialog.setBounds(250,100,300,200);

	
		JLabel label = new JLabel("���������� : ");
		JButton button = new JButton("����");
		JButton button2 = new JButton("�L�����Z��");
		exprHandle = new JCheckBox("���K�\�����g��");
		upperOrLowerHandle = new JCheckBox("�啶���E�������𓯈ꎋ");

		button.setActionCommand("run");
		button2.setActionCommand("cancel");
		//exprHandle.setActionCommand("expr");
		
		// �������s�C�x���g
		button.addActionListener(this);

		// �������~�C�x���g�n���h���[
		button2.addActionListener(this);

		// ���K�\���g�p���菈��
		exprHandle.addActionListener(new myExprCheck());
		
		//�啶���E�������̋�ʏ����\��
		upperOrLowerHandle.addActionListener(new myUpperOrLowerCheck());

		
		//searchDialog.getContentPane().add(textField);
		//searchDialog.getContentPane().add(button);
		//searchDialog.getContentPane().add(button2);
		

		panel1.add(textField);
		panel1.add(button);
		panel1.add(button2);
		panel1.add(exprHandle);
		panel1.add(upperOrLowerHandle);
		
		searchDialog.getContentPane().add(panel1);

		
		panel2.add(textArea);

		Container content = getContentPane();
		content.add(panel2, BorderLayout.CENTER);

	}

	// ���j���[�쐬
	public void setMenu(){
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Edit");
		//fileMenu.setMnemonic(KeyEvent.VK_F);
		
		// new
		JMenuItem fileMenuItemNew = new JMenuItem("Search");
		//fileMenuItemNew.setMnemonic(KeyEvent.VK_N);
		//fileMenuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		fileMenuItemNew.setActionCommand("search");
		fileMenuItemNew.addActionListener(this);

		// add menuItem
		fileMenu.add(fileMenuItemNew);
		menuBar.add(fileMenu);
	
		this.setJMenuBar(menuBar);
	}

	public static void main(String[] args){

		Sample sa = new Sample();
		sa.setMenu();
		sa.setVisible(true);

	}

	// �L�����b�g�̌��ݏꏊ��ݒ�
	public void caretUpdate(CaretEvent event){
		caretPosition = (int)event.getDot();
	//	String scope = Integer.toString(event.getMark());
	//	System.out.println("caret : " + caretPosition);
	//	System.out.println("scope : " + scope);

	}

	public void actionPerformed(ActionEvent event){
		String cmd = event.getActionCommand();
		
		//�������s���̏���
		if(cmd.equals("run")){
			String textF = textField.getText();
			String textA = textArea.getText();
			
			if(!updateCheck.equals(textF)){
				matcher = null;
			}

			if(matcher == null){
				p = Pattern.compile(textF, patternFlags);
				matcher = p.matcher(textA);
				
				matchCheck(matcher);
			}else{
				matchCheck(matcher);
			}
			updateCheck = textF;
		}else if(cmd.equals("search")){ // �����_�C�A���O�̕\��
			searchDialog.setVisible(true);
		}else if(cmd.equals("cancel")){ // �����_�C�A���O�̔�\��
			// �b�菉�����Ή�
			//	matcher = null;
			searchDialog.setVisible(false);
		}
	}

	public void matchCheck(Matcher m){
		if(m.find(caretPosition)){
			int start = m.start();
			int end = m.end();

			textArea.requestFocusInWindow(); // �t�H�[�J�X���e�L�X�g�t�B�[���h�ɒu���Ȃ��Ă͑ʖڂ�����
			textArea.select(start, end);
		}else{
			caretPosition = 0;
			if(m.find(caretPosition)){
				int start = m.start();
				int end = m.end();

				textArea.requestFocusInWindow();
				textArea.select(start, end);
			}else{

				//m.reset();
				//matcher = null;
				System.out.println("not match");

				//caretPosition = 0;
			}
		}
	}

	// ���K�\���g�p�`�F�b�N
	public class myExprCheck implements ActionListener{
		public void actionPerformed(ActionEvent event){
			if(exprHandle.isSelected()){ // ���K�\�����g��
				patternFlags = patternFlags -  Pattern.LITERAL;
			}else{ // ���K�\�����g��Ȃ�
				patternFlags = patternFlags +  Pattern.LITERAL;
			}
			matcher = null;

			//System.out.println("exprHandle patternFlags : " + patternFlags);
		}
	}

	// �啶���E��������ʃ`�F�b�N
	public class myUpperOrLowerCheck implements ActionListener{
		public void actionPerformed(ActionEvent event){
			
			if(upperOrLowerHandle.isSelected()){ // �啶���������𓯈ꎋ
				patternFlags = patternFlags +  Pattern.CASE_INSENSITIVE;
			}else{ // �啶�������������
				patternFlags = patternFlags -  Pattern.CASE_INSENSITIVE;
			}
			matcher = null;

			//System.out.println("upperOrLowerHandle patternFlags : " + patternFlags);
		}
	}
}