package br.com.javacia.merge.util.swing;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.StringReader;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import br.com.javacia.merge.util.Difference;
import br.com.javacia.merge.util.DifferenceInspector;
import br.com.javacia.merge.util.inspector.JavaCodeDifferenceInspector;
import br.com.javacia.merge.util.inspector.KeyPropertiesDifferenceInspector;
import br.com.javacia.merge.util.inspector.PropertiesDifferenceInspector;

public class MergeUtil extends JFrame {

	private static final long serialVersionUID = 1L;
	private JSplitPane jSplitVerticalFiles;
	private JMenuBar jMenuBarPrincipal;
	private JMenu jMenuFile;
	private JMenuItem jMenuItemJavaCode;
	private JMenuItem jMenuItemJavaCodeNewCode;
	private JMenuItem jMenuItemProperties;
	private JMenuItem jMenuItemPropertiesNewCode;
	private JMenuItem jMenuItemKeyProperties;
	private JMenuItem jMenuItemXml;
	private JScrollPane jScrollPaneLocal;
	private JScrollPane jScrollPaneRemote;
	private JScrollPane jScrollPaneResult;
	private JSplitPane jSplitHorizonalFiles;
	private JTextArea textAreaLocal;
	private JTextArea textAreaRemote;
	private JTextArea textAreaResult;
	
	public MergeUtil() {
		initComponents();
	}

	private void initComponents() {
		jSplitVerticalFiles = new JSplitPane();
		jSplitHorizonalFiles = new JSplitPane();
		jScrollPaneLocal = new JScrollPane();
		textAreaLocal = new JTextArea();
		jScrollPaneRemote = new JScrollPane();
		textAreaRemote = new JTextArea();
		jScrollPaneResult = new JScrollPane();
		textAreaResult = new JTextArea();
		jMenuBarPrincipal = new JMenuBar();
		jMenuFile = new JMenu();
		jMenuItemJavaCode = new JMenuItem();
		jMenuItemJavaCodeNewCode = new JMenuItem();
		jMenuItemProperties = new JMenuItem();
		jMenuItemPropertiesNewCode = new JMenuItem();
		jMenuItemKeyProperties = new JMenuItem();
		jMenuItemXml = new JMenuItem();
		
		setDefaultCloseOperation(3);
		jSplitVerticalFiles.setOrientation(0);
		jSplitHorizonalFiles.setOrientation(1);
		textAreaLocal.setColumns(20);
		textAreaLocal.setRows(5);
		jScrollPaneLocal.setViewportView(textAreaLocal);
		jSplitHorizonalFiles.setLeftComponent(jScrollPaneLocal);
		textAreaRemote.setColumns(20);
		textAreaRemote.setRows(5);
		jScrollPaneRemote.setViewportView(textAreaRemote);
		jSplitHorizonalFiles.setRightComponent(jScrollPaneRemote);
		jSplitVerticalFiles.setTopComponent(jSplitHorizonalFiles);
		textAreaResult.setColumns(20);
		textAreaResult.setRows(5);
		jScrollPaneResult.setViewportView(textAreaResult);
		jSplitVerticalFiles.setRightComponent(jScrollPaneResult);
		jMenuFile.setText("Differences analytics");
		
		jMenuItemJavaCode.setText("JavaCode Conflit");
		jMenuItemJavaCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jMenuItemJavaCodeActionPerformed(evt, false);
			}
		});
		jMenuFile.add(jMenuItemJavaCode);
		
		jMenuItemJavaCodeNewCode.setText("JavaCode New");
		jMenuItemJavaCodeNewCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jMenuItemJavaCodeActionPerformed(evt, true);
			}
		});
		jMenuFile.add(jMenuItemJavaCodeNewCode);
		
		jMenuItemProperties.setText("Properties Conflit");
		jMenuItemProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jMenuItemPropertiesActionPerformed(evt, false);
			}
		});
		jMenuFile.add(jMenuItemProperties);
		
		jMenuItemPropertiesNewCode.setText("Properties New");
		jMenuItemPropertiesNewCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jMenuItemPropertiesActionPerformed(evt, true);
			}
		});
		jMenuFile.add(jMenuItemPropertiesNewCode);
		
		jMenuItemKeyProperties.setText("Key Properties New");
		jMenuItemKeyProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jMenuItemKeyPropertiesActionPerformed(evt, true);
			}
		});
		jMenuFile.add(jMenuItemKeyProperties);
		
		jMenuItemXml.setText("Xml");
		//jMenuFile.add(jMenuItemXml);
		
		jMenuBarPrincipal.add(jMenuFile);
		
		setJMenuBar(jMenuBarPrincipal);
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jSplitVerticalFiles, -1, 473, 32767));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jSplitVerticalFiles, -1, 313, 32767));
		pack();
		
		setExtendedState(getExtendedState() | 6);
		jSplitHorizonalFiles.setResizeWeight(0.5D);
		jSplitVerticalFiles.setDividerLocation(jSplitVerticalFiles.getSize().height - jSplitVerticalFiles.getInsets().bottom - jSplitVerticalFiles.getDividerSize());
	}

	public static void main(String args[]) {
			
		LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo info : installedLookAndFeels) {
			if ("Nimbus".equals(info.getName())) {
				try {
					UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				MergeUtil mergeUtil = new MergeUtil();
				mergeUtil.setVisible(true);				
			}
		});
	}

	private void jMenuItemJavaCodeActionPerformed(ActionEvent evt, boolean newCode) {
		DifferenceInspector differenceInspector = new JavaCodeDifferenceInspector(new StringReader(textAreaLocal.getText()), new StringReader(textAreaRemote.getText()));
		List<Difference> differences = differenceInspector.findDifferences();
		printDifferences(differences, newCode);
	}

	private void jMenuItemPropertiesActionPerformed(ActionEvent evt, boolean newCode) {
		DifferenceInspector differenceInspector = new PropertiesDifferenceInspector(new StringReader(textAreaLocal.getText()), new StringReader(textAreaRemote.getText()));
		List<Difference> differences = differenceInspector.findDifferences();
		printDifferences(differences, newCode);
	}
	
	private void jMenuItemKeyPropertiesActionPerformed(ActionEvent evt, boolean newCode) {
		DifferenceInspector differenceInspector = new KeyPropertiesDifferenceInspector(new StringReader(textAreaLocal.getText()), new StringReader(textAreaRemote.getText()));
		List<Difference> differences = differenceInspector.findDifferences();
		printDifferencesKeyProperties(differences);
	}
	

	private void printDifferencesKeyProperties(List<Difference> differences) {
		StringBuilder builder = new StringBuilder();
		
		for (Difference difference : differences) {
			builder.append(difference.getName()).append("=").append(difference.getValueLocal()).append("\r\n");
		}

		textAreaResult.setText(builder.toString());
	}

	private void printDifferences(List<Difference> differences, boolean newCode) {
		StringBuilder builder = new StringBuilder();
		int count = 0;
	
		for (Difference difference : differences) {
			if (newCode != difference.isConflict()) {
				builder.append(++count).append(" - ").append(difference.toString()).append("\r\n");
				builder.append("Local : ").append("\r\n");
				builder.append("\t").append(difference.getValueLocal());
				if (difference.getValueRemote() != null) {
					builder.append("\r\n");
					builder.append("Remote : ").append("\r\n");
					builder.append("\t").append(difference.getValueRemote());
				}
				builder.append("\r\n\r\n");
			}
		}

		textAreaResult.setText(builder.toString());
	}



}