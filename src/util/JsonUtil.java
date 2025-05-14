package util;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import modeling.AttributePane;
import modeling.MindPane;
import modeling.TextPane;

class SaveJson {
//JSON파일을 생성해주는 클래스
	
	TreeNode root;
	JSONObject jsonObj;
	public SaveJson()
	{
		root = Tree.getRoot();
		if(root == null);
		else
		{
			jsonObj = createJson(root);
		}
	}
	JSONObject createJson(TreeNode node)
	{
		JSONObject json = new JSONObject();
		JSONObject infoJson = new JSONObject();
	
		infoJson.put("name", node.getData().getText());
		infoJson.put("posX", node.getData().getX());
		infoJson.put("posY", node.getData().getY());
		infoJson.put("Width", node.getData().getWidth());
		infoJson.put("Height", node.getData().getHeight());
		infoJson.put("Color", getHexColor(node.getData().getBackground()));
		
		
		
		JSONArray children = new JSONArray();
		
		for(int i = 0; i < node.children.size(); ++i)
		{
			children.add(createJson(node.children.get(i)));
		}
	
		if(children.size() != 0)
			infoJson.put("children", children);
		
		json.put("JLabel", infoJson);
		return json;
	}
	String getHexColor(Color c)
	{
		String hexString = Integer.toHexString(c.getRGB() & 0xffffff);
		if(hexString.length() < 6)
		{
			hexString = "000000".substring(0,6 - hexString.length()) + hexString;
		}
		return hexString;
	}
	public String getJson()
	{ return jsonObj.toJSONString(); }
	
	
}

class ParseJson {
//JSON을 파싱해주는 클래스
	TreeNode root, current, prev;
	String jsonInfo;
	MindPane mindMap;
	AttributePane attribute;
	boolean hasRoot = false;
	
	MouseActionHandling me;
	Focus focus = new Focus();
	Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		
	JSONParser jsonParser = new JSONParser();
	JSONObject jsonObject;
	
	
	public ParseJson(String jsonInfo, MindPane mindMap, AttributePane attribute)
	{
		this.jsonInfo = jsonInfo;
		this.mindMap = mindMap;
		this.attribute = attribute;
		me = new MouseActionHandling(this.mindMap,this.attribute);
		JSONObject obj = null;
		try 
		{
			jsonObject = (JSONObject) jsonParser.parse(jsonInfo);
			obj = (JSONObject) jsonObject.get("JLabel");
		} catch (ParseException e) 
		{
			return;
		}
		//need add action
		makeRoot(obj);
		makeTree(root, obj);
		Tree.setRoot(root);
		TextPane.fill(root);
		this.mindMap.repaint();	
	}
	void makeRoot(JSONObject rootObj)
	{
		JLabel Jroot = new JLabel((String) rootObj.get("name"));
		String color = "#" + (String) rootObj.get("Color");
		Color c = Color.decode(color);
		
		defaultSetting(Jroot);
		Jroot.setBounds((int) ((long)rootObj.get("posX")),
						(int) ((long)rootObj.get("posY")),
						(int) ((long)rootObj.get("Width")),
						(int) ((long)rootObj.get("Height")));		
		Jroot.setBackground(c);
		
		mindMap.add(Jroot);
		root = new TreeNode(Jroot);
		mindMap.repaint();
		//루트노드 설정 끝
	}
	void makeTree(TreeNode node, JSONObject jsonObj)
	{
		JSONArray children = null;
		try
		{
			children = (JSONArray) jsonObj.get("children");
		} catch(Exception e) {}
		if(children == null) return;
		
		for(int i = 0; i < children.size(); ++i)
		{
			JSONObject childInfo = (JSONObject) children.get(i);
			childInfo = (JSONObject) childInfo.get("JLabel");
			JLabel label = new JLabel((String) childInfo.get("name"));
			String color = "#" + (String) childInfo.get("Color");
			Color c = Color.decode(color);
			
			defaultSetting(label);
			label.setBounds((int) ((long)childInfo.get("posX")),
							(int) ((long)childInfo.get("posY")),
							(int) ((long)childInfo.get("Width")),
							(int) ((long)childInfo.get("Height")));
			label.setBackground(c);

			mindMap.add(label);

			TreeNode child = node.MakeChild(label);
			makeTree(child, childInfo);
		}
	}
	
	private void defaultSetting(JLabel node)
	{
		node.addMouseListener(me);
		node.addMouseMotionListener(me);
		node.addFocusListener(focus);
		node.setFocusable(true);
		node.setOpaque(true);
		node.setBorder(border);
	}
}
