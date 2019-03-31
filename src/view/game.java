package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class game extends JFrame{

	int[] value = new int[9] ; 
	JButton[] jb = new JButton[9] ; 
		
	public game () {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		this.setSize(400,400);
		this.setLayout(new GridLayout(3,3));
		this.setTitle("井字棋");
		
		for ( int i=0 ; i<9 ; i++ ) {
			value[i] = 0 ; 
		}
		for ( int i=0 ; i<9 ; i++ ) {
			jb[i] = new JButton("") ;
			jb[i].addActionListener(new JBClick());
			this.add(jb[i]);
		}
		this.setVisible(true);
	}
	
	
	//设置按钮点击事件，点击后用ai计算下一步
	private class JBClick implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			for (int i=0 ; i<9 ; i++){
				if (e.getSource() == jb[i]){
					value[i]=1 ;
					System.out.println("第"+i+"个按钮点击了");
					jb[i].setEnabled(false);
					jb[i].setText("X");
					ai() ; 
				}
			}
		}
	}

	//AI计算
	private void ai() {
		int locat = 0 ;
		int max = -16 ;
		int end = 0 ;
		
		//AI下棋子，计算第一步扩张
		for(int i=0 ; i<9 ; i++){
			if (value[i]==1 || value[i]==2) 
				continue ;
			else {
				value[i]=2 ;
				if(ifAiWin()){
					System.out.println("AI下第"+locat+"赢了");
					value[i] = 2 ;
					jb[i].setEnabled(false);
					jb[i].setText("0");
					showAiWin();
					return ;
				}
				int back_max = cal_whoWin_first() ;
				//从第一步扩展选取最大的
				if (back_max>max){
					max=back_max ; 
					locat=i ;
				}
				
				value[i]=0 ;
			}
		}
		
		for(int x=0;x<9;x++){
			if (value[x]!=0) end++ ;
		}
		System.out.println(end);
		if ( end==9 ){
			showDoWin() ;
			return ;
		}
		if(max==-16){
			showHuWin() ;
			return ;
		}
		
		
		System.out.println("AI下第"+locat+"是最好的"+max);
		//确定哪一步，然后下子
		value[locat] = 2 ;
		jb[locat].setEnabled(false);
		jb[locat].setText("0");


	}

	//第一步扩展
	private int cal_whoWin_first() {
		int min = 16 ;
		int locat = 0 ;

		//求第二步扩展
		//人下棋子，计算第二步扩张
		for(int i=0 ; i<9 ; i++){
			if (value[i]==1 || value[i]==2) 
				continue ;
			else {
				value[i]=1 ;
				int back_min = cal_whoWin_secend() ;
				//从第二步扩展选最小的
				if (back_min<min){
					min=back_min ; 
					locat=i ;
				}
				value[i]=0 ;
			}
		}
		
		System.out.println("人下第"+locat+"是最差的"+min);
		return min ;
	}


	//第二步扩展
	private int cal_whoWin_secend() {
		int aiWin = 0 ;
		int huWin = 0 ; 
		
		if (value[0]==1 && value[1]==1 && value[2]==1) return -16 ;
		if (value[3]==1 && value[4]==1 && value[5]==1) return -16 ;
		if (value[6]==1 && value[7]==1 && value[8]==1) return -16 ;
		if (value[0]==1 && value[3]==1 && value[6]==1) return -16 ;
		if (value[1]==1 && value[4]==1 && value[7]==1) return -16 ;
		if (value[2]==1 && value[5]==1 && value[8]==1) return -16 ;
		if (value[0]==1 && value[4]==1 && value[8]==1) return -16 ;
		if (value[2]==1 && value[4]==1 && value[6]==1) return -16 ;
		
		if (value[0]==2 && value[1]==2 && value[2]==2) return 16 ;
		if (value[3]==2 && value[4]==2 && value[5]==2) return 16 ;
		if (value[6]==2 && value[7]==2 && value[8]==2) return 16 ;
		if (value[0]==2 && value[3]==2 && value[6]==2) return 16 ;
		if (value[1]==2 && value[4]==2 && value[7]==2) return 16 ;
		if (value[2]==2 && value[5]==2 && value[8]==2) return 16 ;
		if (value[0]==2 && value[4]==2 && value[8]==2) return 16 ;
		if (value[2]==2 && value[4]==2 && value[6]==2) return 16 ;
		
		if (value[0]!=1 && value[1]!=1 && value[2]!=1) aiWin++ ;
		if (value[3]!=1 && value[4]!=1 && value[5]!=1) aiWin++ ;
		if (value[6]!=1 && value[7]!=1 && value[8]!=1) aiWin++ ;
		if (value[0]!=1 && value[3]!=1 && value[6]!=1) aiWin++ ;
		if (value[1]!=1 && value[4]!=1 && value[7]!=1) aiWin++ ;
		if (value[2]!=1 && value[5]!=1 && value[8]!=1) aiWin++ ;
		if (value[0]!=1 && value[4]!=1 && value[8]!=1) aiWin++ ;
		if (value[2]!=1 && value[4]!=1 && value[6]!=1) aiWin++ ;
		
		
		if (value[0]!=2 && value[1]!=2 && value[2]!=2) huWin++ ;
		if (value[3]!=2 && value[4]!=2 && value[5]!=2) huWin++ ;
		if (value[6]!=2 && value[7]!=2 && value[8]!=2) huWin++ ;
		if (value[0]!=2 && value[3]!=2 && value[6]!=2) huWin++ ;
		if (value[1]!=2 && value[4]!=2 && value[7]!=2) huWin++ ;
		if (value[2]!=2 && value[5]!=2 && value[8]!=2) huWin++ ;
		if (value[0]!=2 && value[4]!=2 && value[8]!=2) huWin++ ;
		if (value[2]!=2 && value[4]!=2 && value[6]!=2) huWin++ ;
		
		return aiWin-huWin ;
	}


	private boolean ifAiWin () {
		if (value[0]==2 && value[1]==2 && value[2]==2) return true ;
		if (value[3]==2 && value[4]==2 && value[5]==2) return true ;
		if (value[6]==2 && value[7]==2 && value[8]==2) return true ;
		if (value[0]==2 && value[3]==2 && value[6]==2) return true ;
		if (value[1]==2 && value[4]==2 && value[7]==2) return true ;
		if (value[2]==2 && value[5]==2 && value[8]==2) return true ;
		if (value[0]==2 && value[4]==2 && value[8]==2) return true ;
		if (value[2]==2 && value[4]==2 && value[6]==2) return true ;
		return false;
	}
	
	private void showAiWin() {
		JButton jb = new JButton ("退出吧") ;
		JDialog win = new JDialog() ;
		win.setTitle("你输了");
		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		win.add(jb);
		win.setSize(220,260);
		win.setVisible(true);
		
	}
	
	private void showHuWin() {
		JButton jb = new JButton ("你厉害咯") ;
		JDialog win = new JDialog() ;
		win.setTitle("竟然让你赢了");
		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		win.add(jb);
		win.setSize(220,260);
		win.setVisible(true);
	}
	
	private void showDoWin () {
		JButton jb = new JButton ("哦咯") ;
		JDialog win = new JDialog() ;
		win.setTitle("打平了");
		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		win.add(jb);
		win.setSize(220,260);
		win.setVisible(true);
	}
	
	public static void main (String[] args){
		new game() ;
	}

}
