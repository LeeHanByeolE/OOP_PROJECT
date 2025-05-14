package util;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.JOptionPane;

import modeling.AttributePane;
import modeling.MindPane;
import modeling.TextPane;
import util.MouseActionHandling;

class Make{ // 텍스트를 받아 트리를 구성
	
	TextPane Text;
	MindPane mindMap;
	AttributePane attribute;
	
	MouseActionHandling me; 
	Focus focus = new Focus();
	Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
	//기본 마우스 이벤트와 포커스이벤트와 테두리
	
	Make(TextPane Tt, MindPane mindMap, AttributePane attribute){
		Text = Tt; this.mindMap = mindMap; this.attribute = attribute;
		me = new MouseActionHandling(mindMap,attribute);
	}
	
	TreeNode MakeNode() throws Exception {
		
		
		JLabel Sto = null;
		int Level = 0;
		int oldLevel = 0;
		String Cpy = Text.getText(); //모든 텍스트 복사해 옴
		
		if(Cpy.charAt(0) == ' ')
			throw new Exception();
		
		StringTokenizer st = new StringTokenizer(Cpy,"\n");
		JLabel Jroot = new JLabel(st.nextToken());
		TreeNode root = new TreeNode(Jroot); //루트 노드를 텍스트 에어리어에서 가장 위에 있는 것으로 만듬.
		int Num = st.countTokens();
		TreeNode CurrentNode = root;
		
		//node에 관한 기본설정
		defaultSetting(Jroot);
			
		for(int i = 0; i < Num; i++) { // 텍스트의 /t의 개수로 깊이 탐색
			Level = 0;
			String A = st.nextToken();
			char[] CpyA = A.toCharArray(); //String을 char형 배열로 변환
			for(int j = 0; j < CpyA.length; j++) {
				if(CpyA[j]=='\t') {
					Level++;
				}
			}
			A = String.valueOf(CpyA); //char형 배열을 String로 변환
			A = A.trim(); // 공백 제거
			Sto = new JLabel(A);
			Sto.setHorizontalAlignment(JLabel.CENTER); //JLabel 텍스트 가운데정렬
			//node에 관한 기본설정
			defaultSetting(Sto);
			
			
			//노드 삽입
			if(oldLevel == (Level-1)) {
				CurrentNode = CurrentNode.MakeChild(Sto);
			}
			else if(oldLevel == Level) {
				CurrentNode = CurrentNode.MakeBChild();
				CurrentNode = CurrentNode.MakeChild(Sto);
			}
			else {
				for(int a=0; a<(oldLevel-Level)+1; a++) {
					CurrentNode = CurrentNode.MakeBChild();
				}
				CurrentNode = CurrentNode.MakeChild(Sto);
			}
			oldLevel = Level;
		}
		return root;
	}
	
	private void defaultSetting(JLabel node)
	{
		node.addMouseListener(me);
		node.addMouseMotionListener(me);
		node.addFocusListener(focus);
		node.setFocusable(true);
		node.setOpaque(true);
		node.setBackground(Color.orange);
		node.setBorder(border);
	}
}

class Focus extends FocusAdapter {

	public void focusGained(FocusEvent e)
	//focus를 받게되면 테두리를 굵게
	{
		Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
		JComponent comp = (JComponent)e.getSource();
		comp.setBorder(border);
		EventHandling.current = (JLabel)comp;
		//지금 가리키는 것을 전달
	}
	public void focusLost(FocusEvent e)
	//focus에 벗어나면 원래대로
	{
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		JComponent comp = (JComponent)e.getSource();
		comp.setBorder(border);
	}
}

public class Tree { // 노드 내용 구성 및  MindPane UI
	static TreeNode root;
	MindPane mindMap;
	
	Tree(TextPane text, MindPane mindMap, AttributePane attribute)
	{
		Make tree = new Make(text,mindMap,attribute);
		this.mindMap = mindMap;
		try {
			root = tree.MakeNode();
			new TreeUI(root);
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "다시 입력해주세요","경고",JOptionPane.WARNING_MESSAGE);
		}
	}
	public static TreeNode getRoot()
	//root를 반환하는 메소드
	{ return root; }
	public static void setRoot(TreeNode _root)
	//파일을 open했을시 루트를 설정하는 메소드
	{ root = _root; }
	
	class TreeUI{ // 트리를 UI로 띄우기
		RememberXY[] R; // 각각 JLabel의  X, Y, Angle 저장 
		final int initX;
		final int initY;
		final int initW;
		final int initH;
		
		TreeUI(TreeNode Root){ //생성자
			this.R = new RememberXY[Root.children.size()];
			initW = 80;
			initH = 40;
			initX = mindMap.getWidth() / 2 - initW / 2;
			initY = mindMap.getHeight() / 2 - initH / 2;
			Root.data.setBounds(initX, initY, initW, initH);
			Root.data.setHorizontalAlignment(JLabel.CENTER);
			mindMap.add(Root.data);
			
			int Num = Root.children.size();
			RememberXY[] R = new RememberXY[Num];
			R = inXY(initX , initY, R, 0);
			
			TOUI(initX, initY, Root, mindMap, 0);
		}
		
		void TOUI(int x, int y, TreeNode Node, MindPane f, int Angle) { // UI를 띄우는 재귀 메소드
			int PrevAngle = Angle; //부모 노드의 Angle 저장
			int Num = Node.children.size();
			RememberXY[] R = new RememberXY[Num];
			R = inXY(x , y , R, PrevAngle); // 자식 노드 내용 구성
			if(Num == 0) { //자식 노드가 없을 때
				f.add(Node.data);
				Node.data.setBounds(x, y, initW, initH);
			}
			else { //자식 노드가 있을 때
				for(int i=0; i<Num; i++) {
					f.add(Node.children.get(i).data);
					Node.children.get(i).data.setBounds(R[i].x,R[i].y,initW,initH);
					TOUI(R[i].x,R[i].y,Node.children.get(i),f ,R[i].Angle);
				}
			}
			f.repaint();
		}
	
		RememberXY[] inXY(int x, int y , RememberXY[] xy, int RememberAngle) { // 노드의 X, Y, Angle 값 구성
			int Distance = 120; // MindPane UI상 거리 조절
			if(x==initX && y<initY) { // X가 부모노드와 같고, Y가 부모노드보다 클 때
				for(int i=0; i<xy.length; i++) {
					RememberXY Temp = new RememberXY(0,0,0);
					Temp.x = (int)(x+(Math.sin(Math.toRadians(270+180/xy.length*(i+0.5)))*Distance));
					Temp.y = (int)(y-(Math.cos(Math.toRadians(270+180/xy.length*(i+0.5)))*Distance));
					Temp.Angle = (int)(270+180/xy.length*(i+0.5));
					xy[i] = Temp;
				}
			}
			else if(x==initX && y>initY) { // X가 부모노드와 같고, Y가 부모노드보다 작을 때
				for(int i=0; i<xy.length; i++) {
					RememberXY Temp = new RememberXY(0,0,0);
					Temp.x = (int)(x+(Math.sin(Math.toRadians(90+180/xy.length*(i+0.5)))*Distance));
					Temp.y = (int)(y-(Math.cos(Math.toRadians(90+180/xy.length*(i+0.5)))*Distance));
					Temp.Angle = (int)(90+180/xy.length*(i+0.5));
					xy[i] = Temp;
				}
			}
			else if(x>initX && y==initY) { // X가 부모노드보다 작고, Y가 부모노드와 같을 때
				for(int i=0; i<xy.length; i++) {
					RememberXY Temp = new RememberXY(0,0,0);
					Temp.x = (int)(x+(Math.sin(Math.toRadians(0+180/xy.length*(i+0.5)))*Distance));
					Temp.y = (int)(y-(Math.cos(Math.toRadians(0+180/xy.length*(i+0.5)))*Distance));
					Temp.Angle = (int)(0+180/xy.length*(i+0.5));
					xy[i] = Temp;
				}
			}
			else if(x<initX && y==initY) { // X가 부모노드보다 크고, Y가 부모노드와 같을 때
				for(int i=0; i<xy.length; i++) {
					RememberXY Temp = new RememberXY(0,0,0);
					Temp.x = (int)(x+(Math.sin(Math.toRadians(180+180/xy.length*(i+0.5)))*Distance));
					Temp.y = (int)(y-(Math.cos(Math.toRadians(180+180/xy.length*(i+0.5)))*Distance));
					Temp.Angle = (int)(180+180/xy.length*(i+0.5));
					xy[i] = Temp;
				}
			}
			else if(x==initX && y==initY) { // 초기 부모노드로부터 자식 노드 위치 지정
				for(int i=0; i<xy.length; i++) {
					RememberXY Temp = new RememberXY(0,0,0);
					Temp.x = (int)(x+(Math.sin(Math.toRadians(360/xy.length*i))*Distance));
					Temp.y = (int)(y-(Math.cos(Math.toRadians(360/xy.length*i))*Distance));
					Temp.Angle = 360/xy.length*i;
					xy[i] = Temp;
				}
			}
			else { // 그 외의 위치에 있을 때
				for(int i=0; i<xy.length; i++) {
					RememberXY Temp = new RememberXY(0,0,0);
					Temp.x = (int)(x+(Math.sin(Math.toRadians((360-(180-90-RememberAngle))+180/xy.length*(i+0.5)))*Distance));
					Temp.y = (int)(y-(Math.cos(Math.toRadians((360-(180-90-RememberAngle))+180/xy.length*(i+0.5)))*Distance));
					Temp.Angle = (int)((360-(180-90-RememberAngle))+180/xy.length*(i+0.5));
					xy[i] = Temp;
				}
			}
				
			return xy;
		}
	}
	class RememberXY{ //X, Y, Angle값을 저장
		int x;
		int y;
		int Angle;
		RememberXY(int x, int y, int Angle){
			this.x = x;
			this.y = y;
			this.Angle = Angle;
		}
	}
}
