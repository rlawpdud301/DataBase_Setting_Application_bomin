package database_setting_application.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;

import database_setting_application.service.ExportService;
import database_setting_application.service.ImportService;
import database_setting_application.service.InitService;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class SettingUi extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton btnInit;
	private InitService initService;
	private ImportService importService;
	private ExportService exportService;
	private JButton btnExport;
	private JButton btnImport;
	
	/**
	 * Create the frame.
	 */
	public SettingUi() {
		initService = new InitService();
		importService = new ImportService();
		exportService = new ExportService();
		initComponents();
	}
	private void initComponents() {
		setTitle("DataBase Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 454, 143);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 10, 0));
		
		JPanel btnPannel = new JPanel();
		btnPannel.setBorder(new TitledBorder(null, "\uB370\uC774\uD130\uBCA0\uC774\uC2A4 \uAD00\uB9AC \uBA54\uB274", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		contentPane.add(btnPannel);
		btnPannel.setLayout(new GridLayout(1, 0, 10, 0));
		
		btnInit = new JButton("초기화");
		btnInit.addActionListener(this);
		btnPannel.add(btnInit);
		
		/*btnExport = new JButton("백업");
		btnExport.addActionListener(this);
		btnPannel.add(btnExport);
		
		btnImport = new JButton("복원");
		btnImport.addActionListener(this);
		btnPannel.add(btnImport);*/
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnInit) {
			do_btnInit_actionPerformed(e);
		}
		if (e.getSource() == btnExport) {
			do_btnExport_actionPerformed(e);
		}
		if (e.getSource() == btnInit) {
			do_btnInit_actionPerformed(e);
		}
		if (e.getSource() == btnImport) {
			do_btnImport_actionPerformed(e);
		}
	}
	
	
	private void do_btnImport_actionPerformed(ActionEvent e) {
		try {
			importService.service(filePath("DataFiles 디렉터리 선택", true));
			JOptionPane.showMessageDialog(null, e.getActionCommand() + " 완료");
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	// 초기화 버튼
	protected void do_btnInit_actionPerformed(ActionEvent e) {
		try {
			initService.service(filePath("SQL File 디렉터리 선택", true));
			JOptionPane.showMessageDialog(null, e.getActionCommand() + " 완료");
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
		}

	}

	private String filePath(String dialogTitle, boolean isOpen) throws Exception {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(dialogTitle);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int ret = -1;
		if (isOpen) {
			ret = chooser.showOpenDialog(null);
		} else {
			ret = chooser.showSaveDialog(null);
		}
		if (ret != JFileChooser.APPROVE_OPTION) {
			throw new Exception("파일을 선택하지 않았습니다.");
		}
		return chooser.getSelectedFile().getPath();
	}
	
	//백업
	protected void do_btnExport_actionPerformed(ActionEvent e) {
		try {
			exportService.service(filePath("백업 디렉터리 선택", false));
			JOptionPane.showMessageDialog(null, e.getActionCommand() + " 완료");
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);
		}
	}
}
