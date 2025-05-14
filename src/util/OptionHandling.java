package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

import java.io.*;

import modeling.AttributePane;
import modeling.MenuBar;
import modeling.MindPane;
import modeling.TextPane;
import modeling.ToolBar;
public class OptionHandling implements ActionListener{

	ToolBar tool;
	MenuBar menu;
	MindPane mindMap;
	AttributePane attribute;
	String currentFile = null;
	
	final JFileChooser fileChooser = new JFileChooser();
	
	public OptionHandling(ToolBar tool, MindPane mindMap, AttributePane attribute)
	{ this.tool = tool; this.mindMap = mindMap; this.attribute = attribute; }
	
	public OptionHandling(MenuBar menu, MindPane mindMap, AttributePane attribute)
	{ this.menu = menu; this.mindMap = mindMap; this.attribute = attribute; }
	
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if(source.getClass() == JMenuItem.class)
		{
			String menuText = ((JMenuItem)e.getSource()).getText();
			
			if(menuText.equals("New"))
			{
				New();
			}
			else if(menuText.equals("Open"))
			{
				Open();
			}
			else if(menuText.equals("Save"))
			{
				Save();
			}
			else if(menuText.equals("Save As"))
			{
				Saveas();
			}
			else if(menuText.equals("Quit"))
			{
				Quit();
			}			
		}
		else if(source.getClass() == JButton.class)
		//ToolBar buttons
		{
			String buttonText = ((JButton)e.getSource()).getText();
			
			if(buttonText.equals("New"))
			{
				New();
			}
			else if(buttonText.equals("Open"))
			{
				Open();
			}
			else if(buttonText.equals("Save"))
			{
				Save();
			}
			else if(buttonText.equals("Save As"))
			{
				Saveas();
			}
			else if(buttonText.equals("Quit"))
			{
				Quit();
			}
		}
	}
	
	private void New() //done
	{
		if(mindMap.Changed() == true)
		//���� mindMap���� ������ �ٲ���ٸ�
		{
			int option = JOptionPane.showOptionDialog(null, "���� �Ͻðڽ��ϱ�?", "���� �˸�", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.WARNING_MESSAGE,null,null,null);
			switch(option)
			{
			case 0: //Yes
				Save();
				break;
			case 1: //No
				break;
			case 2: //Cancle
				return;
			}
		}
		/*
		 * new�� �⺻������ ���ν����ؾ��ϴ°��̴�
		 * mindMap, text�� �����ϰ�
		 * new������� �����Ұ��̾����� �ٲ�Ծ��ٰ� ���
		 * mindMap�ȿ� �ƹ��͵������� ���� �ȱ׷��� �ȴٰ� ���
		 */
		TextPane.clear();
		mindMap.endChange();
		mindMap.drawNotReady();
		mindMap.removeAll();
		mindMap.repaint();
		mindMap.revalidate();
	}
	private void Save() //done
	{
		if(currentFile == null) Saveas();
		//������ �����ߴ� ��ΰ� ���ٸ� ���� �����ߵ� �� �ٸ��̸����� ������ ȣ���ؾ���
		else
		{
			SaveJson jsonfile = new SaveJson();
			String content = jsonfile.getJson();
			FileWriter fw = null;
			try
			{
				fw = new FileWriter(currentFile);
				fw.write(content);
				fw.flush();
			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage());
			} finally {
	            try {
	                if(fw != null) fw.close();
	            } catch(IOException e) {
	            	JOptionPane.showMessageDialog(null, e.getMessage());
	            }
	        }
		}
		//������ �����Ƿ� �߰����� �ʴ��̻� �ٲ�°� ��
		mindMap.endChange();
	}
	private void Saveas()
	{
		SaveJson jsonFile = new SaveJson();
		fileChooser.setDialogTitle("�ٸ� �̸����� ����");
		fileChooser.setFileFilter(new FileNameExtensionFilter("json����","json"));
		fileChooser.setMultiSelectionEnabled(false);
		
		int value = fileChooser.showSaveDialog(null);
		if(value == JFileChooser.APPROVE_OPTION)
		{
			String content = jsonFile.getJson();
			File file = fileChooser.getSelectedFile();
			FileWriter fw = null;
			try 
			{
				fw = new FileWriter(file.getPath());
				fw.write(content);
				fw.flush();
				
			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(null, e.getMessage());
			} finally {
	            try {
	                if(fw != null)
	                //������ �ݰ� ������ ��� ����
	                { fw.close(); currentFile = file.getPath(); }
	            } catch(IOException e) {
	            	JOptionPane.showMessageDialog(null, e.getMessage());
	            }
	        }
		}
		//������ �����Ƿ� �߰����� �ʴ� �̻� �ٲ�°� ��
		mindMap.endChange();
	}
	private void Open()
	{
		if(mindMap.Changed() == true)
		//���� mindMap�� �ٲ�����ִ� ���¶�� �켱 �����Ұ��̳İ� ������ߵ�
		{
			int option = JOptionPane.showOptionDialog(null, "���� �Ͻðڽ��ϱ�?", "���� �˸�", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE,null,null,null);
			switch(option)
			{
			case 0: //Yes ������ ����
				Save();
				break;
			case 1: //No ������ϰ� ����
				break;
			case 2: //Cancle
				return;
			}
		}
		
		TextPane.clear();
		mindMap.drawNotReady();
		mindMap.removeAll();
		mindMap.repaint();
		mindMap.revalidate();
		
		fileChooser.setDialogTitle("����");
		fileChooser.setFileFilter(new FileNameExtensionFilter("json����","json"));
		fileChooser.setMultiSelectionEnabled(false);
		int result = fileChooser.showOpenDialog(null);
		if(result == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File file = fileChooser.getSelectedFile();
				BufferedReader br = new BufferedReader(new FileReader(file.getPath()));
				ParseJson json = new ParseJson(br.readLine(), this.mindMap, this.attribute);
				currentFile = file.getPath();
			} catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "Error!");
			}
			mindMap.drawReady();
			mindMap.endChange();
			mindMap.repaint();
			mindMap.revalidate();
		}
		
	}
	private void Quit()
	{
		if(mindMap.Changed() == true)
		{
			int option = JOptionPane.showOptionDialog(null, "���� �Ͻðڽ��ϱ�?", "���� �˸�", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE,null,null,null);
			switch(option)
			{
			case 0: //Yes
				Save();
			case 1: //No
				break;
			case 2: //Cancle
				return;
			}
		}
		System.exit(0);
	}
}
