package util;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;

import modeling.AttributePane;
import modeling.MindPane;

public class MouseActionHandling extends MouseAdapter{

	AttributePane attribute;
	MindPane mindMap;
	JLabel current;
	Point pos;
	boolean isDragged = false;
	int offX, offY;
	int cursor;
	
	public MouseActionHandling(MindPane mindMap, AttributePane attribute) 
	{
		this.mindMap = mindMap;
		this.attribute = attribute;
	}
	

	public void mouseMoved(MouseEvent e)
	{
		Object source = e.getSource();
		if(source.getClass() == JLabel.class) 
		{
			current = (JLabel)source;
			if(e.getY() <= 3 && e.getX() <= current.getWidth())
			//JLabel의 북쪽부분
			{
				current.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
				cursor = Cursor.N_RESIZE_CURSOR;
			}
			else if(e.getY() >= current.getHeight()-3 && e.getX() <= current.getWidth())
			//JLabel의 남쪽부분
			{
				current.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
				cursor = Cursor.S_RESIZE_CURSOR;
			}
			else if(e.getX() <= 3 && e.getY() <= current.getHeight())
			//JLabel의 동쪽부분
			{
				current.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
				cursor = Cursor.W_RESIZE_CURSOR;
			}
			else if(e.getX() >= current.getWidth()-3 && e.getY() <= current.getHeight())
			//JLabel의 서쪽부분
			{
				current.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
				cursor = Cursor.E_RESIZE_CURSOR;
			}
			else
			//어느경우도 해당되지 않을때
			{
				current.setCursor(Cursor.getDefaultCursor());
				cursor = Cursor.DEFAULT_CURSOR;
			}
		}
	}
	public void mouseEntered(MouseEvent e)
	{}
	public void mouseExited(MouseEvent e)
	{}
	public void mouseClicked(MouseEvent e)
	{
		Object source = e.getSource();
		if(source.getClass() == JLabel.class)
		//JLabel을 클릭했을때 attributePane에 반영
		{
			current = (JLabel)e.getComponent();
			getInfo();
		}
	}
	public void mousePressed(MouseEvent e)
	{
		Object source = e.getSource();
		if(source.getClass() == JLabel.class)
		//눌렀을때 드래그를 true로 설정하고 좌표를 가져옴
		{	
			current = (JLabel)e.getComponent();///
			current.requestFocusInWindow();
			offX = e.getX() - current.getX();
			offY = e.getY() - current.getY();
			isDragged = true;
			pos = e.getPoint();
		}
	}
	public void mouseDragged(MouseEvent e)
	{
		if(isDragged)
		//커서별로 하는일을 지정 사이즈조정 및 위치조정
		{
			int x = current.getX();
			int y = current.getY();	
			int w = current.getWidth();
			int h = current.getHeight();

			int dx = e.getX() - pos.x;
			int dy = e.getY() - pos.y;
			switch(cursor)
			{
			case Cursor.N_RESIZE_CURSOR:
				current.setBounds(x,y+dy,w,h-dy);
				break;
			case Cursor.S_RESIZE_CURSOR:
				current.setBounds(x, y, w, h+dy);
				pos = e.getPoint();
				break;
			case Cursor.W_RESIZE_CURSOR:			
				current.setBounds(x+dx, y, w-dx, h);
				break;
			case Cursor.E_RESIZE_CURSOR:
				current.setBounds(x,y,w+dx,h);
				pos = e.getPoint();
				break;
			default:
				//current.setLocation(e.getX() - offX, e.getY() - offY);
				e.getComponent().setLocation(e.getX() + e.getComponent().getX() - pos.x,
							e.getY() + e.getComponent().getY() - pos.y);
				break;
			}
			getInfo();
		}
		mindMap.repaint();
	}
	public void mouseReleased(MouseEvent e)
	//마우스를 땠다면 드래그를 false로 변경
	{
		isDragged = false;
	}
	
	private void getInfo()
	//attributePane에 반영해주는 메소드
	{
		attribute.set_Text(current.getText());
		attribute.set_X(current.getX());
		attribute.set_Y(current.getY());
		attribute.set_W(current.getWidth());
		attribute.set_H(current.getHeight());
		attribute.set_Color(current.getBackground());
	}
}