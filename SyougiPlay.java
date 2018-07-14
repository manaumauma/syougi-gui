package software;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

//対戦中の将棋盤の設定

class SyougiPlay extends JFrame implements MouseListener {

	private static int maxPlayer = 2; // プレイヤー数（2 ）
	private static int myNumber; // 自分の順番
	private static int currentTurn; // 現在の番
	private static String name[] = new String[3]; // プレイヤーの名前
	private static String font;
	private static final int N = 9; // 一辺の個数
	private static SyougiSection S[][] = new SyougiSection[N + 2][N + 2]; // 盤面の各区画の配列
	private static JPanel playinfo = new JPanel();
	private static JLabel info[] = new JLabel[4]; // 対戦情况
	private static int score[] = new int[3]; // スコア(石の数)
	private static ImageIcon stone[] = new ImageIcon[3]; // 盤面の石の画像
	private static ImageIcon infostone[] = new ImageIcon[2]; // 対戦情况の石の画像
	private static ImageIcon backImage[] = new ImageIcon[1]; // 背景の画像
	private static Color playerColor[] = new Color[5]; // プレイヤーカラー
	private static boolean SurPlayer[] = new boolean[5]; // 投了を押した人
	private static boolean finish = false; // 終了

	/*
	 * ----------------------------------------コンストラクタ--------------------------
	 * --------------
	 */
	SyougiPlay(String title, String name[], int n, String font) {

		/* MacOSでうまく描画されるように */
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
		}

		/* タイトルを登録 */
		setTitle(title);

		/* 引数 */
		this.name = name;
		this.myNumber = n;
		this.font = font;

		/* アイコン */
		setIconImage(new ImageIcon(SyougiPlay.class.getResource("image/icon.jpg")).getImage());

		/* 画面の大きさ */
		setSize(50 * N + 300, 50 * N + 50);
		setResizable(false);

		/* 画面位置(真ん中) */
		setLocationRelativeTo(null);

		/* ウィンドウを消した時プログラムが終了するようにする */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* パネルをコンテナへ */
		JPanel basePanel = new JPanel();
		basePanel.setLayout(null);
		Container c = getContentPane();
		c.add(basePanel);

		/* 歩兵：1 と金：2 */
		/* 盤面の石の画像読み込み */
		stone[0] = new ImageIcon(SyougiPlay.class.getResource("image/white.jpg"));
		stone[1] = new ImageIcon(SyougiPlay.class.getResource("image/s_hu.jpg"));// 逆
		stone[2] = new ImageIcon(SyougiPlay.class.getResource("image/s_to.jpg"));

		/* 対戦情况の石の画像読み込み */
		infostone[0] = new ImageIcon(SyougiPlay.class.getResource("image/s_hu1.jpg"));
		infostone[1] = new ImageIcon(SyougiPlay.class.getResource("image/s_to.jpg"));

		/* 背景の画像読み込み */
		backImage[0] = new ImageIcon(SyougiPlay.class.getResource("image/background.png"));

		/* プレイヤーカラーの設定 */
		playerColor[0] = Color.BLACK; // 盤面に近い白
		playerColor[1] = Color.BLUE;
		playerColor[2] = Color.RED;
		playerColor[3] = Color.WHITE;
		playerColor[4] = new Color(255, 170, 22); // 勝者表示の色(オレンジ色)

		/* 盤面の各区画を生成 */
		for (int i = 0; i <= N + 1; i++) {
			for (int j = 0; j <= N + 1; j++) {
				S[i][j] = new SyougiSection(i, j);
				if (i == 0 || j == 0 || i == N + 1 || j == N + 1)
					continue; // 周りの実際は使わない区画
				S[i][j].setBounds((j - 1) * 50 + 10, (i - 1) * 50 + 10, 50, 50); // サイズ・位置の設定
				S[i][j].addMouseListener(this);
				S[i][j].setOpaque(true); // 非透明
				basePanel.add(S[i][j]); // ベースのパネルに追加
			}
		}

		/* 対戦情况 */
		CompoundBorder border = new CompoundBorder(new BevelBorder(BevelBorder.RAISED),
				new BevelBorder(BevelBorder.LOWERED)); // 内側ボーダおよび外側ボーダ(浮いている・くぼみ)

		// 自分の名前
		info[0] = new JLabel(name[myNumber], JLabel.CENTER);
		info[0].setFont(new Font(font, Font.BOLD, 26));
		info[0].setForeground(playerColor[myNumber]);

		// 番・勝敗結果
		info[3] = new JLabel();
		info[3].setFont(new Font(font, Font.BOLD, 23));

		playinfo.setBackground(playerColor[3]);
		playinfo.setOpaque(true);
		playinfo.setBounds(15 + N * 50, 10, 270, 105);
		playinfo.setBorder(border);
		playinfo.setLayout(new BorderLayout());
		playinfo.add(info[0], BorderLayout.NORTH); // 名前の欄を上部に配置
		playinfo.add(info[3], BorderLayout.CENTER);
		basePanel.add(playinfo); // ラベルの追加

		// スコア表示
		for (int i = 1; i <= maxPlayer; i++) {
			info[i] = new JLabel("×" + score[i] + ":" + name[i], infostone[i - 1], JLabel.LEFT);
			info[i].setFont(new Font(font, Font.BOLD, 23));
			info[i].setForeground(playerColor[i]);
			info[i].setBackground(playerColor[3]);
			info[i].setOpaque(true);
			info[i].setBounds(15 + N * 50, 65 + i * 55, 270, 50);
			info[i].setBorder(border);
			basePanel.add(info[i]);
		}

		/* 投了ボタン */
		JButton Surrender = new JButton("投了");
		Surrender.setFont(new Font(font, Font.BOLD, 23));
		Surrender.setForeground(Color.BLUE);
		Surrender.setBackground(playerColor[0]);
		Surrender.setBounds(225 + N * 50, (N - 1) * 50, 60, 60);
		Surrender.setBorder(border);
		Surrender.setOpaque(true);
		Surrender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentTurn != myNumber || finish)
					return;
				SyougiClient.SendData(0, 0);
				GameFinish(true);
			}
		});
		basePanel.add(Surrender); // 投了ボタンの追加

		/* 背景 */
		JLabel background = new JLabel();
		background.setBounds(0, 0, 300 + N * 50, 50 * N + 50);
		background.setIcon(backImage[0]);
		basePanel.add(background);

		/* 石の初期配置 */
		currentTurn = 1;
		for (int i = 1; i <= N; i++) {
			S[1][i].setIcon(stone[currentTurn]);
			S[1][i].player_change(currentTurn);
		}
		currentTurn = 2;
		for (int i = 1; i <= N; i++) {
			S[N][i].setIcon(stone[currentTurn]);
			S[N][i].player_change(currentTurn);
		}

		/* 1Pから開始 */
		currentTurn = 1;
		// all_put_check();
	}

	/*
	 * --------------------------------------------以下、メソッド----------------------
	 * -------------------------
	 */

	/* はさみ将棋のプレールールを打ち込む */
	/* S[p][q]からS[i][j]に駒が置けるか否か 縦と横のみ移動可能 */
	static boolean put_enable(int p, int q, int i, int j) {
		int m;
		if (p == i || q == j) {
			// -------------駒を跨がないようにする--------------
			if (q == j) {
				for (m = i + 1; m < p; m++) {// ＋１してるのは自分の駒が入らないように(間を考えるから)
					if (S[m][q].has_stone()) {
						return false;
					}
				}
				for (m = p + 1; m < i; m++) {
					if (S[m][q].has_stone()) {
						return false;
					}
				}
				return true;
			} else {
				for (m = j + 1; m < q; m++) {
					if (S[p][m].has_stone()) {
						return false;
					}
				}
				for (m = q + 1; m < j; m++) {
					if (S[p][m].has_stone()) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	static void put_stone(int i, int j) {
		int m, n;
		if (i == 0 && j == 0) {
			GameFinish(true);
			return;
		}
		S[i][j].player_change(currentTurn);
		S[i][j].setIcon(null);
		S[i][j].setIcon(stone[currentTurn]);

		for (m = 1; i <= 9; m++) { // 横の場合(ex.「歩」「と」「歩」「と」「歩」で「と」二枚とる場合など)
			for (n = 2; n <= 8; n++) {
				if (S[m][n - 1].player_is(currentTurn) && S[m][n + 1].player_is(currentTurn)) { // とりま１つの駒取り
					if (S[m][n].get_player() == 3 - currentTurn) {
						S[m][n].setRolloverIcon(null);
						score[currentTurn]++;
					}
				}
			}
		}

		/* スコア表示更新 */
		for (m = 1; m <= maxPlayer; m++) {
			info[m].setText("×" + score[m] + ":" + name[m]);
		}

		/* 全部埋まったらゲーム終了 */
		if (score[0] == -N * N) {
			GameFinish(false);
			return;
		}

		/* 全て、ある誰かの石で埋まったらゲーム終了 */
		for (int k = 1; k <= maxPlayer; k++) {
			if (-score[0] == score[k]) {
				GameFinish(false);
				return;
			}
		}

		changeTurn();// 次のターン
	}

	/* ----------次のターンへ---------- */
	static void changeTurn() {
		/* つい今、自分のターンだったらプレイ情報の色を戻す */
		if (currentTurn == myNumber) {
			playinfo.setBackground(playerColor[0]);
			playinfo.setOpaque(true);
		}
		/* つい今のターンのスコアの色を元に戻す */
		info[currentTurn].setBackground(playerColor[0]);
		info[currentTurn].setOpaque(true);

		/* 次の人へ順番を回す */
		if (currentTurn == maxPlayer) {
			currentTurn = 1;
		} else {
			currentTurn++;
		}
	}

	/* ----------ゲーム終了---------- */
	static void GameFinish(boolean Surrender) {
		int max = 0, max_count = 0;
		int winner = 0;
		String str = null;

		if (Surrender) {
			/* 投了ボタンが押されたとき */
			winner = currentTurn == 1 ? 2 : 1;
			max_count = 1;
		} else {
			/* 終了条件の記入 */
		}

		changeTurn(); // 対戦状況の色をリセット

		/* 文字は自分の色に変える */
		info[3].setForeground(Color.WHITE);

		/* 勝者を表示 */
		/* 勝者のスコアの色を変える */
		info[winner].setBackground(playerColor[3]);
		info[winner].setOpaque(true);

		/* 勝者のターン情報の色を変える */
		if (winner == myNumber) {
			playinfo.setBackground(playerColor[3]);
			playinfo.setOpaque(true);
		}

		str = "勝者は " + name[winner] + " です";
		if (Surrender) {
			str = "投了ボタンが押されたので、" + str;
		}
		info[3].setText("<html>" + str + "</html>");

		finish = true;
	}

	/*
	 * ----------------------------------------以下、マウスイベント処理---------------------
	 * ----------------------
	 */

	/* ----------置ける位置にクリックされた時---------- */
	public void mouseClicked(MouseEvent e1, MouseEvent e2) {
		if (currentTurn != myNumber || finish)
			return;
		SyougiSection theSection = (SyougiSection) e1.getComponent();// クリックしたオブジェクトを得る．型が違うのでキャストする
		int p = theSection.I();
		int q = theSection.J();

		if (theSection.get_player() == currentTurn && theSection.has_stone()) {
			SyougiSection theSection2 = (SyougiSection) e2.getComponent();
			int i = theSection.I();
			int j = theSection.J();
			if (put_enable(p, q, i, j)) {
				theSection.setIcon(null);
				theSection.player_change(0); // 1でも2でもない。
				SyougiClient.SendData(i, j);
			}
		}
	}

	/*----------置ける位置にマウスが入った時、その石を表示してあげる----------*/

	public void mouseEntered(MouseEvent e) {
		if (currentTurn != myNumber || finish)
			return;
		SyougiSection theSection = (SyougiSection) e.getComponent();

		if (theSection.has_stone()) {
			theSection.setIcon(stone[currentTurn]);
		}
	}

	/* ----------置ける位置からマウスが出ていった時、石を消して元の状態に戻す---------- */
	public void mouseExited(MouseEvent e) {
		if (currentTurn != myNumber || finish)
			return;
		SyougiSection theSection = (SyougiSection) e.getComponent();

		if (theSection.has_stone()) {
			theSection.setIcon(null);
			theSection.setIcon(stone[0]);
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
