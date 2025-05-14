package util;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;

import modeling.TextPane;
import modeling.ToolBar;
import modeling.AttributePane;
import modeling.GUI;
import modeling.MindPane;

public class EventHandling implements ActionListener {

	TextPane text;
	AttributePane attribute;
	MindPane mindMap;
	static JLabel current = null;
	
	public EventHandling(TextPane text, MindPane mindMap, AttributePane attribute) {
		this.text = text; 
		this.mindMap = mindMap; 
		this.attribute = attribute;
	}

	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if(source.getClass() == JButton.class)
		{
			String buttonText = ((JButton)source).getText();
			
			if(buttonText.equals("적용") || buttonText.equals("Apply"))
			//Text Button & ToolBar Apply Button
			{
				mindMap.removeAll();
				mindMap.repaint();
				Tree tree = new Tree(text, mindMap, attribute);
				mindMap.isChange();
				mindMap.drawReady();
			}
			else if(buttonText.equals("변경") || buttonText.equals("Change"))
			//Attribute Button & ToolBar Change Button
			{
				try
				{	
					int x = attribute.get_X();
					int y = attribute.get_Y();
					int w = attribute.get_W();
					int h = attribute.get_H();
					String color = attribute.get_Color();
					Color c = Color.decode(color);
		
					current.setBounds(x,y,w,h);
					current.setBackground(c);
					
				} catch(Exception E) {}
				mindMap.repaint();
			}
		}
		else if(source.getClass() == JMenuItem.class)
		//MenuItem Apply & Change
		{
			String menuText = ((JMenuItem)e.getSource()).getText();
			
			if(menuText.equals("Apply"))
			//Text Button
			{
				mindMap.removeAll();
				mindMap.repaint();
				Tree tree = new Tree(text, mindMap, attribute);
				mindMap.isChange();
				mindMap.drawReady();
			}
			else if(menuText.equals("Change"))
			//Attribute Button
			{
				try
				{	
					int x = attribute.get_X();
					int y = attribute.get_Y();
					int w = attribute.get_W();
					int h = attribute.get_H();
					String color = attribute.get_Color();
					Color c = Color.decode(color);
		
					current.setBounds(x,y,w,h);
					current.setBackground(c);
					
				} catch(Exception E) {}
				mindMap.repaint();
			}
		}
	}
}
