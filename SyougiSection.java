package software;

import javax.swing.ImageIcon;
import javax.swing.JButton;

class SyougiSection extends JButton {

	private int positionI, positionJ; // ���W
	private int player; // ���̋�̃v���C���[(0�Ȃ��)

	/* �R���X�g���N�^ */
	SyougiSection(int i, int j) {
		this.setIcon(new ImageIcon(SyougiSection.class.getResource("image/white.jpg")));
		this.player = 0;
		this.positionI = i;
		this.positionJ = j;
	}

	/* �ȉ��A�A�N�Z�T */

	boolean player_is(int n) {
		return this.player == n;
	}

	boolean has_stone() {
		return this.player != 0;
	}

	void player_change(int n) {
		this.player = n;
	}

	int get_player() {
		return this.player;
	}

	int I() {
		return this.positionI;
	}

	int J() {
		return this.positionJ;
	}

}
