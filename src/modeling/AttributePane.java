package modeling;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import util.EventHandling;

public class AttributePane extends JPanel {

	JTextField textField;
	JTextField xField;
	JTextField yField;
	JTextField wField;
	JTextField hField;
	JTextField colorField;
	String fontType;
	JButton attributeButton;
	
	AttributePane()
	{
		super(new BorderLayout());
		
		fontType = "����";
		
		JPanel temp1 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0)); //�ؽ�Ʈ
		JPanel temp2 = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0)); //X��ǥ
		JPanel temp3 = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0)); //Y��ǥ
		JPanel temp4 = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0)); //W
		JPanel temp5 = new JPanel(new FlowLayout(FlowLayout.LEFT,20,0)); //H
		JPanel temp6 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0)); //����
		JPanel temp = new JPanel();
		attributeButton = new JButton("����");
		attributeButton.setFont(new Font(fontType,Font.BOLD,20));
		
		JLabel textLabel = new JLabel("TEXT:"); textLabel.setFont(new Font(fontType,Font.BOLD,20));
		JLabel xLabel = new JLabel("X:"); xLabel.setFont(new Font(fontType,Font.BOLD,20));
		JLabel yLabel = new JLabel("Y:"); yLabel.setFont(new Font(fontType,Font.BOLD,20));
		JLabel wLabel = new JLabel("W:"); wLabel.setFont(new Font(fontType,Font.BOLD,20));
		JLabel hLabel = new JLabel("H:"); hLabel.setFont(new Font(fontType,Font.BOLD,20));
		JLabel colorLabel = new JLabel("Color:"); colorLabel.setFont(new Font(fontType,Font.BOLD,20));
		
		textField = new JTextField(8); textField.setFont(new Font(fontType,Font.BOLD,20));
		xField = new JTextField(5); xField.setFont(new Font(fontType,Font.BOLD,20));
		yField = new JTextField(5); yField.setFont(new Font(fontType,Font.BOLD,20));
		wField = new JTextField(5); wField.setFont(new Font(fontType,Font.BOLD,20));
		hField = new JTextField(5); hField.setFont(new Font(fontType,Font.BOLD,20));
		colorField = new JTextField(6); colorField.setFont(new Font(fontType,Font.BOLD,20));
		textField.setEditable(false);
		
		
		temp1.add(textLabel); temp1.add(textField);
		temp2.add(xLabel); temp2.add(xField);
		temp3.add(yLabel); temp3.add(yField);
		temp4.add(wLabel); temp4.add(wField);
		temp5.add(hLabel); temp5.add(hField);
		temp6.add(colorLabel); temp6.add(colorField);
		
		temp.setLayout(new BoxLayout(temp,BoxLayout.Y_AXIS));
		temp.add(temp1);
		temp.add(temp2);
		temp.add(temp3);
		temp.add(temp4);
		temp.add(temp5);
		temp.add(temp6);
		/*
		 * (1 <= �� <= 6)
		 * temp�гο� BoxLayout�� �̿��Ͽ� �� tempi���� ��ĭ�� �����ϰ� �ø�
		 * ���� tempi�� FlowLayout�� �̿��� info�� inputĭ�� ��ġ
		 * ��ü�г��� BorderLayout����  temp�ǰ� button�� �ø�
		 */
		add(temp, "Center");
		add(attributeButton, "South");
		
		colorField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)
			{
				if(get_Color().equals("")) return;
				Color pick = JColorChooser.showDialog(null, "Color", Color.WHITE);
				if(pick != null)
					set_Color(pick);
			}
		});
	}
	
	public void addEvent(EventHandling e)
	//��ư�� �̺�Ʈ �߰�
	{ attributeButton.addActionListener(e); }
	
	//JLabel�� �Ӽ��� ǥ�����ִ� �޼ҵ��
	public void set_Text(String text)
	{ textField.setText(text); }
	public void set_X(int X)
	{ xField.setText(X+""); }
	public void set_Y(int Y)
	{ yField.setText(Y+""); }
	public void set_W(int W)
	{ wField.setText(W+""); }
	public void set_H(int H)
	{ hField.setText(H+""); }
	public void set_Color(Color c)
	{	//color���� 16������ ��ȯ
		String hexString = Integer.toHexString(c.getRGB() & 0xffffff);
		if(hexString.length() < 6)
		{
			hexString = "000000".substring(0,6 - hexString.length()) + hexString;
		}
		colorField.setText("#" + hexString);
	}
	
	//JLabel�� ������ �������ִ� �޼ҵ��
	public String get_Text()
	{
		return textField.getText();
	}
	public int get_X()
	{ return Integer.parseInt(xField.getText()); }
	public int get_Y()
	{ return Integer.parseInt(yField.getText()); }
	public int get_W()
	{ return Integer.parseInt(wField.getText()); }
	public int get_H()
	{ return Integer.parseInt(hField.getText()); }
	public String get_Color()
	{ return colorField.getText(); }
}
