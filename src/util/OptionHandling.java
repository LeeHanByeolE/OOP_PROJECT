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
		//만약 mindMap안의 내용이 바뀌었다면
		{
			int option = JOptionPane.showOptionDialog(null, "저장 하시겠습니까?", "변경 알림", JOptionPane.YES_NO_CANCEL_OPTION,
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
		 * new는 기본적으로 새로시작해야하는것이니
		 * mindMap, text를 깨끗하게
		 * new했을경우 저장할것이없으니 바뀐게없다고 명시
		 * mindMap안에 아무것도없으니 선을 안그려도 된다고 명시
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
		//파일을 저장했던 경로가 없다면 새로 만들어야됨 즉 다른이름으로 저장을 호출해야함
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
		//저장을 했으므로 추가하지 않는이상 바뀌는건 끝
		mindMap.endChange();
	}
	private void Saveas()
	{
		SaveJson jsonFile = new SaveJson();
		fileChooser.setDialogTitle("다른 이름으로 저장");
		fileChooser.setFileFilter(new FileNameExtensionFilter("json파일","json"));
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
	                //파일을 닫고 파일의 경로 저장
	                { fw.close(); currentFile = file.getPath(); }
	            } catch(IOException e) {
	            	JOptionPane.showMessageDialog(null, e.getMessage());
	            }
	        }
		}
		//저장을 했으므로 추가하지 않는 이상 바뀌는건 끝
		mindMap.endChange();
	}
	private void Open()
	{
		if(mindMap.Changed() == true)
		//만약 mindMap이 바뀌어져있는 상태라면 우선 저장할것이냐고 물어봐야됨
		{
			int option = JOptionPane.showOptionDialog(null, "저장 하시겠습니까?", "변경 알림", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE,null,null,null);
			switch(option)
			{
			case 0: //Yes 저장후 오픈
				Save();
				break;
			case 1: //No 저장안하고 오픈
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
		
		fileChooser.setDialogTitle("열기");
		fileChooser.setFileFilter(new FileNameExtensionFilter("json파일","json"));
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
			int option = JOptionPane.showOptionDialog(null, "저장 하시겠습니까?", "변경 알림", JOptionPane.YES_NO_CANCEL_OPTION,
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
