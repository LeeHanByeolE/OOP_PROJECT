package modeling;

import javax.swing.JButton;
import javax.swing.JToolBar;

import util.EventHandling;
import util.OptionHandling;
public class ToolBar extends JToolBar{

	MindPane mindMap;
	AttributePane attribute;
	JButton applyButton = new JButton("Apply");
	JButton changeButton = new JButton("Change");
	
	ToolBar(MindPane mindMap, AttributePane attribute)
	{
		super();
		this.mindMap = mindMap;
		this.attribute = attribute;
		OptionHandling oe = new OptionHandling(this, this.mindMap, this.attribute);
		
		JButton newButton = new JButton("New");
		JButton openButton = new JButton("Open");
		JButton saveButton = new JButton("Save");
		JButton saveasButton = new JButton("Save As");
		JButton quitButton = new JButton("Quit");
		
		
		newButton.addActionListener(oe);
		openButton.addActionListener(oe);
		saveButton.addActionListener(oe);
		saveasButton.addActionListener(oe);
		quitButton.addActionListener(oe);

		
		add(newButton);
		add(openButton);
		add(saveButton);
		add(saveasButton);
		add(quitButton);
		add(applyButton);
		add(changeButton);
		
	
	}
	public void addEvent(EventHandling event)
	//이미 구현된 event 재활용
	{ 		
		applyButton.addActionListener(event);
		changeButton.addActionListener(event);
		
	}
}
