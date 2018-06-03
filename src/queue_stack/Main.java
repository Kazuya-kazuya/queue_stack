package queue_stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Main {

	public static void main(String[] args) throws IOException {
		Main program = new Main();
		program.start();
	}

	private void start() throws IOException {
		//キューとスタックどちらか入力して各メソッドへ
		while(true) {
			System.out.println("stackとqueueどちらかを使いますか");
			String choiceMethod = input();
			if(choiceMethod.equals("queue")) {
				queueMethod();
				continue;
			}
			if(choiceMethod.equals("stack")) {
				stackMethod();
				continue;
			}
			break;
		}
	}

	private void queueMethod() throws IOException {
		//芯を変える回数を入力してその回数分先頭の芯の表示と
		//変更を行う
		ColorPencil cp = new ColorPencil();
		System.out.println("使用回数を入力してください。");
		String timesStr = input();
		int times = Integer.parseInt(timesStr);
		for(int i = 0; i < times; i++) {
			//先頭の芯の表示
			cp.usePencil();
			//芯の変更
			cp.changeColor();
		}
	}

	private void stackMethod() throws IOException {
		//逆ポーランド記法の計算式を入力して結果を出力
		PostfixNotation pn = new PostfixNotation();
		List<String> calcList = new ArrayList<String>();
		//入力
		while(true) {
			System.out.println("計算要素を入力してください");
			String str = input();
			if(pn.isNum(str) || pn.isOperator(str)) {
				calcList.add(str);
				continue;
			}
			break;
		}
		//計算
		int result = pn.calculate(calcList);
		System.out.println(result);
	}

	public String input() throws IOException {
		//コンソール入力
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		return br.readLine();
	}
}

enum Color {
	//色鉛筆の芯
	red("赤色"),
	blue("青色"),
	yellow("黄色"),
	green("緑色"),
	white("白色"),
	black("黒色");

	private String color;

	private Color(String color) {
		this.color = color;
	}

	public String getColor() {
		return this.color;
	}
}

class ColorPencil {
	//ロケット色鉛筆クラス
	private Queue<Color> colorQueue = new ArrayDeque<Color>();

	public ColorPencil() {
		//各色追加
		this.colorQueue.add(Color.red);
		this.colorQueue.add(Color.blue);
		this.colorQueue.add(Color.yellow);
		this.colorQueue.add(Color.green);
		this.colorQueue.add(Color.white);
		this.colorQueue.add(Color.black);
	}

	//先頭の芯の表示
	public void usePencil() {
		Color topColor;
		topColor = this.colorQueue.element();
		System.out.println(topColor.getColor());
	}

	//先頭の芯の変更
	public void changeColor() {
		Color topColor;
		topColor = this.colorQueue.remove();
		this.colorQueue.add(topColor);
	}
}

enum Operators {
	//演算子の各処理
	plus(){
		public int calculate(int num1, int num2) {
			return num1 + num2;
		}
	},
	minus(){
		public int calculate(int num1, int num2) {
			return num1 - num2;
		}
	},
	mul(){
		public int calculate(int num1, int num2) {
			return num1 * num2;
		}
	},
	div(){
		public int calculate(int num1, int num2) {
			return num1 / num2;
		}
	};

	public abstract int calculate(int num1, int num2);
}

class PostfixNotation {
	//逆ポーランド記法の計算クラス
	private Stack<Integer> elements;

	public PostfixNotation() {
		super();
	}

	//逆ポーランド記法の計算
	public int calculate(List<String> listElements) throws IOException {
		elements = new Stack<Integer>();
		//先頭から順に処理
		for(String element : listElements) {
			//数字ならスタックに追加
			if(isNum(element)) {
				int num = Integer.parseInt(element);
				this.elements.push(num);
				continue;
			}
			//演算子ならスタックから2つ数値をとって計算し
			//計算結果をスタックに追加
			if(isOperator(element)) {
				Operators operator = Operators.valueOf(element);
				int postNum = this.elements.pop();
				int preNum = this.elements.pop();
				int calcResult = operator.calculate(preNum, postNum);
				this.elements.push(calcResult);
				continue;
			}
			throw new IOException();
		}
		//要素がなくなればスタックの先頭を出力
		int result = this.elements.pop();
		return result;
	}

	public boolean isNum(String string) {
		//数値かどうかチェック
		try {
			Integer.parseInt(string);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}

	public boolean isOperator(String string) {
		//演算子かどうかチェック
		if(string.equals("plus")) return true;
		if(string.equals("minus")) return true;
		if(string.equals("mul")) return true;
		if(string.equals("div")) return true;
		return false;
	}
}