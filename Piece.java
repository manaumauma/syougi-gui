package software;

public class Piece extends SyougiServer {

	public static void print_result(String a) {

		System.out.println();

		System.out.println(a);

	}

	static int move(String masu[][], String be_x, String be_y, String af_x, String af_y, String player_piece,
			int fight_count[]) {

		int i, j, k, l;

		int between_count = 0;

		int b_x = Integer.parseInt(be_x) - 1; // �\���̍��W�̐����Ǝ��ۂ̔z��̐����͈Ⴄ

		int b_y = Integer.parseInt(be_y) - 1;

		int a_x = Integer.parseInt(af_x) - 1;

		int a_y = Integer.parseInt(af_y) - 1;

		if ((0 <= b_x && b_x <= 8) && (0 <= b_y && b_y <= 8) && (0 <= a_y && a_y <= 8) && (0 <= a_y && a_y <= 8)
				&& ((b_x == a_x) || (b_y == a_y))) { // ��Փ��̍��W�łȂ��Ƃ����Ȃ�&�c�ړ��Ɖ��ړ��̂�

			// -------------����ׂ��Ȃ��悤�ɂ���--------------

			if (a_y == b_y) {// �c

				for (i = b_x + 1; i < a_x; i++) {// �{�P���Ă�͎̂����̋����Ȃ��悤��(�Ԃ��l���邩��)

					if (masu[i][a_y].equals("��") || masu[i][a_y].equals("��")) {

						return 3;

					}

				}

				for (i = a_x + 1; i < b_x; i++) {

					if (masu[i][a_y].equals("��") || masu[i][a_y].equals("��")) {

						return 3;

					}

				}

			} else {// ��

				for (j = b_y + 1; j < a_y; j++) {

					if (masu[a_x][j].equals("��") || masu[a_x][j].equals("��")) {

						return 3;

					}

				}

				for (j = a_y + 1; j < b_y; j++) {

					if (masu[a_x][j].equals("��") || masu[a_x][j].equals("��")) {

						return 3;

					}

				}

			}

			// ----------------------------------------------

			if (masu[b_x][b_y].equals(player_piece) && masu[a_x][a_y].equals("�E")) { // �����������̋�̑I�������A�ړ��悪���Ȃ炸�u�E�v�ȏꍇ

				masu[b_x][b_y] = "�E";

				masu[a_x][a_y] = player_piece;

				// ------------------������------------------

				for (i = 0; i < 9; i++) { // ���̏ꍇ(ex.�u���v�u�Ɓv�u���v�u�Ɓv�u���v�Łu�Ɓv�񖇂Ƃ�ꍇ�Ȃ�)

					for (j = 1; j < 8; j++) {

						if (masu[i][j - 1].equals(player_piece) && masu[i][j + 1].equals(player_piece)) { // �Ƃ�܂P�̋���

							if ((player_piece.equals("��") && masu[i][j].equals("��"))
									|| (player_piece.equals("��") && masu[i][j].equals("��"))) {

								masu[i][j] = "�E";

								fight_count[0]++;

								print_result("�[�[�[�[������܂����B�[�[�[�["); // player_piece�̃v���C���[�����Z�����悤�ɂȂ��Ă�

							}

						}

					}

				}

				// --------------------------------------------

				// -----------------���ŕ����ǂ�----------------

				for (i = 0; i < 9; i++) {

					for (j = 1; j < 8; j++) {

						for (k = j; k < 9; k++) {

							if (masu[i][j - 1].equals(player_piece) && masu[i][k].equals(player_piece)) {// ��.....��
																											// �̕��T��

								for (l = j; l < k; l++) {// ���ƕ��̊Ԃ�...�̕���

									if (masu[i][l].equals("��") && !(player_piece.equals("��"))
											|| (masu[i][l].equals("��") && !(player_piece.equals("��")))) {

										between_count++;

									} // �Ԃɉ���邩�����Ă�

									if (between_count == (k - j)) {// �Ԃɂ����S��player_piece�ł͂Ȃ��ꍇ(���̍s��if�����܂�)

										if (masu[i][l].equals("��") && !(player_piece.equals("��"))
												|| (masu[i][l].equals("��") && !(player_piece.equals("��")))) {

											for (l = j; l < k; l++) {

												masu[i][l] = "�E";

											}

											print_result("�[�[�[�[������܂����B�[�[�[�[");

											fight_count[0] += between_count;

										}

									}

								}

							}

						}

					}

				}

				// -------------------------------------------

				// ------------------���ŕ����ǂ�(�l��)-----------------

				// for(i=0;i<9;i++){

				// for(j=1;j<8;j++){

				// if((masu[0][0].equals("��") && !(player_piece.equals("��")) ||
				// (masu[0][0].equals("��") && !(player_piece.equals("��"))){}

				// }

				// }

				// ----------------------------------------------

				// -------------------�c����-----------------------

				for (i = 1; i < 8; i++) {

					for (j = 0; j < 9; j++) {

						if (masu[i - 1][j].equals(player_piece) && masu[i + 1][j].equals(player_piece)) {

							if ((player_piece.equals("��") && masu[i][j].equals("��"))
									|| (player_piece.equals("��") && masu[i][j].equals("��"))) {

								masu[i][j] = "�E";

								fight_count[0]++;

								print_result("�[�[�[�[������܂����B�[�[�[�[");

							}

						}

					}

				}

				// ---------------------------------------------

				// ------------------�l�����---------------------

				if (player_piece.equals("��")) { // �킴�킴���������Ȃ��Ɠ��������v���C���[�Ɏ���������Ȃ�

					if (masu[0][0].equals("��") && masu[0][1].equals(player_piece) && masu[1][0].equals(player_piece)) {

						masu[0][0] = "�E";

						fight_count[0]++;

						print_result("�[�[�[�[������܂����B�[�[�[�[");

					} else if (masu[0][8].equals("��") && masu[0][7].equals(player_piece)
							&& masu[1][7].equals(player_piece)) {

						masu[0][8] = "�E";

						fight_count[0]++;

						print_result("�[�[�[�[������܂����B�[�[�[�[");

					} else if (masu[8][0].equals("��") && masu[7][0].equals(player_piece)
							&& masu[7][1].equals(player_piece)) {

						masu[8][0] = "�E";

						fight_count[0]++;

						print_result("�[�[�[�[������܂����B�[�[�[�[");

					} else if (masu[8][8].equals("��") && masu[8][7].equals(player_piece)
							&& masu[7][8].equals(player_piece)) {

						masu[8][8] = "�E";

						fight_count[0]++;

						print_result("�[�[�[�[������܂����B�[�[�[�[");

					}

				} else if (player_piece.equals("��")) { // �킴�킴���������Ȃ��Ɠ��������v���C���[�Ɏ���������Ȃ�

					if (masu[0][0].equals("��") && masu[0][1].equals(player_piece) && masu[1][0].equals(player_piece)) {

						masu[0][0] = "�E";

						fight_count[0]++;

						print_result("�[�[�[�[������܂����B�[�[�[�[");

					} else if (masu[0][8].equals("��") && masu[0][7].equals(player_piece)
							&& masu[1][7].equals(player_piece)) {

						masu[0][8] = "�E";

						fight_count[0]++;

						print_result("�[�[�[�[������܂����B�[�[�[�[");

					} else if (masu[8][0].equals("��") && masu[7][0].equals(player_piece)
							&& masu[7][1].equals(player_piece)) {

						masu[8][0] = "�E";

						fight_count[0]++;

						print_result("�[�[�[�[������܂����B�[�[�[�[");

					} else if (masu[8][8].equals("��") && masu[8][7].equals(player_piece)
							&& masu[7][8].equals(player_piece)) {

						masu[8][8] = "�E";

						fight_count[0]++;

						print_result("�[�[�[�[������܂����B�[�[�[�[");

					}

				}

				// ---------------------------------------------

				if (player_piece.equals("��")) {

					if (fight_count[0] >= 5) {

						return 0;

					}

				} else {

					if (fight_count[0] >= 5) {

						return 1;

					}

				}

				return 2;

			} else {

				return 3; // �ԈႦ�����W��ł����ꍇ�A������x�B

			}

		} else {

			return 3;

		}

	}

}