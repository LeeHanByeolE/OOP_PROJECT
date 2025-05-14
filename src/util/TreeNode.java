package util;

import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JLabel;

public class TreeNode implements Iterable<TreeNode>{ // 노드
	JLabel data; // 노드의 데이터 값
	TreeNode parent = null; // 조상을 가리켜야 함.
	LinkedList<TreeNode> children; // 자손의 수는 정해지지않았음
	
	TreeNode(JLabel A){ // 생성자
		this.data = A;
		this.children = new LinkedList<TreeNode>();
	}
	
	TreeNode MakeChild(JLabel child){ // 부모 노드에 자식 노드를 붙이는 메소드
		TreeNode childNode = new TreeNode(child);
		childNode.parent = this;
		this.children.add(childNode);
		return childNode;
	}
	
	TreeNode MakeBChild(){ // 부모 노드로 이동하는 메소드
		return this.parent;
	}
		
	@Override
	public Iterator<TreeNode> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public JLabel getData() {
	//JLabel를 가져오는 메소드
		return this.data;
	}
	public LinkedList getChildren()
	//child를 가져오는 메소드
	{
		return this.children;
	}
}