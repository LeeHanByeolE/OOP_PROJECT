package modeling;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import util.EventHandling;
import util.OptionHandling;

public class MenuBar extends JMenuBar{
	MindPane mindMap;
	AttributePane attribute;
	JMenuItem Apply;
	JMenuItem Change;
	
	MenuBar(MindPane mindMap, AttributePane attribute)
	{
		super();
		this.mindMap = mindMap;
		this.attribute = attribute;
		OptionHandling oe = new OptionHandling(this, this.mindMap, this.attribute);
		
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		
		JMenuItem New = new JMenuItem("New");
		JMenuItem Open = new JMenuItem("Open");
		JMenuItem Save = new JMenuItem("Save");
		JMenuItem SaveAs = new JMenuItem("Save As");
		JMenuItem Quit = new JMenuItem("Quit");
		Apply = new JMenuItem("Apply");
		Change = new JMenuItem("Change");
		
		fileMenu.add(New);
		fileMenu.add(Open);
		fileMenu.add(Save);
		fileMenu.add(SaveAs);
		fileMenu.add(Quit);
		editMenu.add(Apply);
		editMenu.add(Change);
		
		add(fileMenu);
		add(editMenu);
		New.addActionListener(oe);
		Open.addActionListener(oe);
		Save.addActionListener(oe);
		SaveAs.addActionListener(oe);
		Quit.addActionListener(oe);
	}
	public void addEvent(EventHandling event)
	//툴바의 적용과 변경버튼은 이미 작성해놓은 이벤트를 활용
	{
		Apply.addActionListener(event);
		Change.addActionListener(event);
	}
}
