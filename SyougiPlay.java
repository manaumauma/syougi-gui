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

//�ΐ풆�̏����Ղ̐ݒ�

class SyougiPlay extends JFrame implements MouseListener {

	private static int maxPlayer = 2; // �v���C���[���i2 �j
	private static int myNumber; // �����̏���
	private static int currentTurn; // ���݂̔�
	private static String name[] = new String[3]; // �v���C���[�̖��O
	private static String font;
	private static final int N = 9; // ��ӂ̌�
	private static SyougiSection S[][] = new SyougiSection[N + 2][N + 2]; // �Ֆʂ̊e���̔z��
	private static JPanel playinfo = new JPanel();
	private static JLabel info[] = new JLabel[4]; // �ΐ��v
	private static int score[] = new int[3]; // �X�R�A(�΂̐�)
	private static ImageIcon stone[] = new ImageIcon[3]; // �Ֆʂ̐΂̉摜
	private static ImageIcon infostone[] = new ImageIcon[2]; // �ΐ��v�̐΂̉摜
	private static ImageIcon backImage[] = new ImageIcon[1]; // �w�i�̉摜
	private static Color playerColor[] = new Color[5]; // �v���C���[�J���[
	private static boolean SurPlayer[] = new boolean[5]; // �������������l
	private static boolean finish = false; // �I��

	/*
	 * ----------------------------------------�R���X�g���N�^--------------------------
	 * --------------
	 */
	SyougiPlay(String title, String name[], int n, String font) {

		/* MacOS�ł��܂��`�悳���悤�� */
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
		}

		/* �^�C�g����o�^ */
		setTitle(title);

		/* ���� */
		this.name = name;
		this.myNumber = n;
		this.font = font;

		/* �A�C�R�� */
		setIconImage(new ImageIcon(SyougiPlay.class.getResource("image/icon.jpg")).getImage());

		/* ��ʂ̑傫�� */
		setSize(50 * N + 300, 50 * N + 50);
		setResizable(false);

		/* ��ʈʒu(�^��) */
		setLocationRelativeTo(null);

		/* �E�B���h�E�����������v���O�������I������悤�ɂ��� */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* �p�l�����R���e�i�� */
		JPanel basePanel = new JPanel();
		basePanel.setLayout(null);
		Container c = getContentPane();
		c.add(basePanel);

		/* �����F1 �Ƌ��F2 */
		/* �Ֆʂ̐΂̉摜�ǂݍ��� */
		stone[0] = new ImageIcon(SyougiPlay.class.getResource("image/white.jpg"));
		stone[1] = new ImageIcon(SyougiPlay.class.getResource("image/s_hu.jpg"));// �t
		stone[2] = new ImageIcon(SyougiPlay.class.getResource("image/s_to.jpg"));

		/* �ΐ��v�̐΂̉摜�ǂݍ��� */
		infostone[0] = new ImageIcon(SyougiPlay.class.getResource("image/s_hu1.jpg"));
		infostone[1] = new ImageIcon(SyougiPlay.class.getResource("image/s_to.jpg"));

		/* �w�i�̉摜�ǂݍ��� */
		backImage[0] = new ImageIcon(SyougiPlay.class.getResource("image/background.png"));

		/* �v���C���[�J���[�̐ݒ� */
		playerColor[0] = Color.BLACK; // �Ֆʂɋ߂���
		playerColor[1] = Color.BLUE;
		playerColor[2] = Color.RED;
		playerColor[3] = Color.WHITE;
		playerColor[4] = new Color(255, 170, 22); // ���ҕ\���̐F(�I�����W�F)

		/* �Ֆʂ̊e���𐶐� */
		for (int i = 0; i <= N + 1; i++) {
			for (int j = 0; j <= N + 1; j++) {
				S[i][j] = new SyougiSection(i, j);
				if (i == 0 || j == 0 || i == N + 1 || j == N + 1)
					continue; // ����̎��ۂ͎g��Ȃ����
				S[i][j].setBounds((j - 1) * 50 + 10, (i - 1) * 50 + 10, 50, 50); // �T�C�Y�E�ʒu�̐ݒ�
				S[i][j].addMouseListener(this);
				S[i][j].setOpaque(true); // �񓧖�
				basePanel.add(S[i][j]); // �x�[�X�̃p�l���ɒǉ�
			}
		}

		/* �ΐ��v */
		CompoundBorder border = new CompoundBorder(new BevelBorder(BevelBorder.RAISED),
				new BevelBorder(BevelBorder.LOWERED)); // �����{�[�_����ъO���{�[�_(�����Ă���E���ڂ�)

		// �����̖��O
		info[0] = new JLabel(name[myNumber], JLabel.CENTER);
		info[0].setFont(new Font(font, Font.BOLD, 26));
		info[0].setForeground(playerColor[myNumber]);

		// �ԁE���s����
		info[3] = new JLabel();
		info[3].setFont(new Font(font, Font.BOLD, 23));

		playinfo.setBackground(playerColor[3]);
		playinfo.setOpaque(true);
		playinfo.setBounds(15 + N * 50, 10, 270, 105);
		playinfo.setBorder(border);
		playinfo.setLayout(new BorderLayout());
		playinfo.add(info[0], BorderLayout.NORTH); // ���O�̗����㕔�ɔz�u
		playinfo.add(info[3], BorderLayout.CENTER);
		basePanel.add(playinfo); // ���x���̒ǉ�

		// �X�R�A�\��
		for (int i = 1; i <= maxPlayer; i++) {
			info[i] = new JLabel("�~" + score[i] + ":" + name[i], infostone[i - 1], JLabel.LEFT);
			info[i].setFont(new Font(font, Font.BOLD, 23));
			info[i].setForeground(playerColor[i]);
			info[i].setBackground(playerColor[3]);
			info[i].setOpaque(true);
			info[i].setBounds(15 + N * 50, 65 + i * 55, 270, 50);
			info[i].setBorder(border);
			basePanel.add(info[i]);
		}

		/* �����{�^�� */
		JButton Surrender = new JButton("����");
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
		basePanel.add(Surrender); // �����{�^���̒ǉ�

		/* �w�i */
		JLabel background = new JLabel();
		background.setBounds(0, 0, 300 + N * 50, 50 * N + 50);
		background.setIcon(backImage[0]);
		basePanel.add(background);

		/* �΂̏����z�u */
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

		/* 1P����J�n */
		currentTurn = 1;
		// all_put_check();
	}

	/*
	 * --------------------------------------------�ȉ��A���\�b�h----------------------
	 * -------------------------
	 */

	/* �͂��ݏ����̃v���[���[����ł����� */
	/* S[p][q]����S[i][j]�ɋ�u���邩�ۂ� �c�Ɖ��݈̂ړ��\ */
	static boolean put_enable(int p, int q, int i, int j) {
		int m;
		if (p == i || q == j) {
			// -------------����ׂ��Ȃ��悤�ɂ���--------------
			if (q == j) {
				for (m = i + 1; m < p; m++) {// �{�P���Ă�͎̂����̋����Ȃ��悤��(�Ԃ��l���邩��)
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

		for (m = 1; i <= 9; m++) { // ���̏ꍇ(ex.�u���v�u�Ɓv�u���v�u�Ɓv�u���v�Łu�Ɓv�񖇂Ƃ�ꍇ�Ȃ�)
			for (n = 2; n <= 8; n++) {
				if (S[m][n - 1].player_is(currentTurn) && S[m][n + 1].player_is(currentTurn)) { // �Ƃ�܂P�̋���
					if (S[m][n].get_player() == 3 - currentTurn) {
						S[m][n].setRolloverIcon(null);
						score[currentTurn]++;
					}
				}
			}
		}

		/* �X�R�A�\���X�V */
		for (m = 1; m <= maxPlayer; m++) {
			info[m].setText("�~" + score[m] + ":" + name[m]);
		}

		/* �S�����܂�����Q�[���I�� */
		if (score[0] == -N * N) {
			GameFinish(false);
			return;
		}

		/* �S�āA����N���̐΂Ŗ��܂�����Q�[���I�� */
		for (int k = 1; k <= maxPlayer; k++) {
			if (-score[0] == score[k]) {
				GameFinish(false);
				return;
			}
		}

		changeTurn();// ���̃^�[��
	}

	/* ----------���̃^�[����---------- */
	static void changeTurn() {
		/* �����A�����̃^�[����������v���C���̐F��߂� */
		if (currentTurn == myNumber) {
			playinfo.setBackground(playerColor[0]);
			playinfo.setOpaque(true);
		}
		/* �����̃^�[���̃X�R�A�̐F�����ɖ߂� */
		info[currentTurn].setBackground(playerColor[0]);
		info[currentTurn].setOpaque(true);

		/* ���̐l�֏��Ԃ��� */
		if (currentTurn == maxPlayer) {
			currentTurn = 1;
		} else {
			currentTurn++;
		}
	}

	/* ----------�Q�[���I��---------- */
	static void GameFinish(boolean Surrender) {
		int max = 0, max_count = 0;
		int winner = 0;
		String str = null;

		if (Surrender) {
			/* �����{�^���������ꂽ�Ƃ� */
			winner = currentTurn == 1 ? 2 : 1;
			max_count = 1;
		} else {
			/* �I�������̋L�� */
		}

		changeTurn(); // �ΐ�󋵂̐F�����Z�b�g

		/* �����͎����̐F�ɕς��� */
		info[3].setForeground(Color.WHITE);

		/* ���҂�\�� */
		/* ���҂̃X�R�A�̐F��ς��� */
		info[winner].setBackground(playerColor[3]);
		info[winner].setOpaque(true);

		/* ���҂̃^�[�����̐F��ς��� */
		if (winner == myNumber) {
			playinfo.setBackground(playerColor[3]);
			playinfo.setOpaque(true);
		}

		str = "���҂� " + name[winner] + " �ł�";
		if (Surrender) {
			str = "�����{�^���������ꂽ�̂ŁA" + str;
		}
		info[3].setText("<html>" + str + "</html>");

		finish = true;
	}

	/*
	 * ----------------------------------------�ȉ��A�}�E�X�C�x���g����---------------------
	 * ----------------------
	 */

	/* ----------�u����ʒu�ɃN���b�N���ꂽ��---------- */
	public void mouseClicked(MouseEvent e1, MouseEvent e2) {
		if (currentTurn != myNumber || finish)
			return;
		SyougiSection theSection = (SyougiSection) e1.getComponent();// �N���b�N�����I�u�W�F�N�g�𓾂�D�^���Ⴄ�̂ŃL���X�g����
		int p = theSection.I();
		int q = theSection.J();

		if (theSection.get_player() == currentTurn && theSection.has_stone()) {
			SyougiSection theSection2 = (SyougiSection) e2.getComponent();
			int i = theSection.I();
			int j = theSection.J();
			if (put_enable(p, q, i, j)) {
				theSection.setIcon(null);
				theSection.player_change(0); // 1�ł�2�ł��Ȃ��B
				SyougiClient.SendData(i, j);
			}
		}
	}

	/*----------�u����ʒu�Ƀ}�E�X�����������A���̐΂�\�����Ă�����----------*/

	public void mouseEntered(MouseEvent e) {
		if (currentTurn != myNumber || finish)
			return;
		SyougiSection theSection = (SyougiSection) e.getComponent();

		if (theSection.has_stone()) {
			theSection.setIcon(stone[currentTurn]);
		}
	}

	/* ----------�u����ʒu����}�E�X���o�Ă��������A�΂������Č��̏�Ԃɖ߂�---------- */
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
