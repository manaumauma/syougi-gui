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

		int b_x = Integer.parseInt(be_x) - 1; // 表示の座標の数字と実際の配列の数字は違う

		int b_y = Integer.parseInt(be_y) - 1;

		int a_x = Integer.parseInt(af_x) - 1;

		int a_y = Integer.parseInt(af_y) - 1;

		if ((0 <= b_x && b_x <= 8) && (0 <= b_y && b_y <= 8) && (0 <= a_y && a_y <= 8) && (0 <= a_y && a_y <= 8)
				&& ((b_x == a_x) || (b_y == a_y))) { // 碁盤内の座標でないといけない&縦移動と横移動のみ

			// -------------駒を跨がないようにする--------------

			if (a_y == b_y) {// 縦

				for (i = b_x + 1; i < a_x; i++) {// ＋１してるのは自分の駒が入らないように(間を考えるから)

					if (masu[i][a_y].equals("歩") || masu[i][a_y].equals("と")) {

						return 3;

					}

				}

				for (i = a_x + 1; i < b_x; i++) {

					if (masu[i][a_y].equals("歩") || masu[i][a_y].equals("と")) {

						return 3;

					}

				}

			} else {// 横

				for (j = b_y + 1; j < a_y; j++) {

					if (masu[a_x][j].equals("歩") || masu[a_x][j].equals("と")) {

						return 3;

					}

				}

				for (j = a_y + 1; j < b_y; j++) {

					if (masu[a_x][j].equals("歩") || masu[a_x][j].equals("と")) {

						return 3;

					}

				}

			}

			// ----------------------------------------------

			if (masu[b_x][b_y].equals(player_piece) && masu[a_x][a_y].equals("・")) { // 正しい自分の駒の選択をし、移動先がかならず「・」な場合

				masu[b_x][b_y] = "・";

				masu[a_x][a_y] = player_piece;

				// ------------------横一つ取り------------------

				for (i = 0; i < 9; i++) { // 横の場合(ex.「歩」「と」「歩」「と」「歩」で「と」二枚とる場合など)

					for (j = 1; j < 8; j++) {

						if (masu[i][j - 1].equals(player_piece) && masu[i][j + 1].equals(player_piece)) { // とりま１つの駒取り

							if ((player_piece.equals("歩") && masu[i][j].equals("と"))
									|| (player_piece.equals("と") && masu[i][j].equals("歩"))) {

								masu[i][j] = "・";

								fight_count[0]++;

								print_result("ーーーー駒を取りました。ーーーー"); // player_pieceのプレイヤーが加算されるようになってる

							}

						}

					}

				}

				// --------------------------------------------

				// -----------------横で複数どり----------------

				for (i = 0; i < 9; i++) {

					for (j = 1; j < 8; j++) {

						for (k = j; k < 9; k++) {

							if (masu[i][j - 1].equals(player_piece) && masu[i][k].equals(player_piece)) {// 歩.....歩
																											// の歩探し

								for (l = j; l < k; l++) {// 歩と歩の間の...の部分

									if (masu[i][l].equals("と") && !(player_piece.equals("と"))
											|| (masu[i][l].equals("歩") && !(player_piece.equals("歩")))) {

										between_count++;

									} // 間に何駒あるか数えてる

									if (between_count == (k - j)) {// 間にある駒が全てplayer_pieceではない場合(次の行のif文も含め)

										if (masu[i][l].equals("と") && !(player_piece.equals("と"))
												|| (masu[i][l].equals("歩") && !(player_piece.equals("歩")))) {

											for (l = j; l < k; l++) {

												masu[i][l] = "・";

											}

											print_result("ーーーー駒を取りました。ーーーー");

											fight_count[0] += between_count;

										}

									}

								}

							}

						}

					}

				}

				// -------------------------------------------

				// ------------------横で複数どり(四隅)-----------------

				// for(i=0;i<9;i++){

				// for(j=1;j<8;j++){

				// if((masu[0][0].equals("と") && !(player_piece.equals("と")) ||
				// (masu[0][0].equals("歩") && !(player_piece.equals("歩"))){}

				// }

				// }

				// ----------------------------------------------

				// -------------------縦一つ取り-----------------------

				for (i = 1; i < 8; i++) {

					for (j = 0; j < 9; j++) {

						if (masu[i - 1][j].equals(player_piece) && masu[i + 1][j].equals(player_piece)) {

							if ((player_piece.equals("歩") && masu[i][j].equals("と"))
									|| (player_piece.equals("と") && masu[i][j].equals("歩"))) {

								masu[i][j] = "・";

								fight_count[0]++;

								print_result("ーーーー駒を取りました。ーーーー");

							}

						}

					}

				}

				// ---------------------------------------------

				// ------------------四隅取り---------------------

				if (player_piece.equals("歩")) { // わざわざこう書かないと動かしたプレイヤーに持ち駒がいかない

					if (masu[0][0].equals("と") && masu[0][1].equals(player_piece) && masu[1][0].equals(player_piece)) {

						masu[0][0] = "・";

						fight_count[0]++;

						print_result("ーーーー駒を取りました。ーーーー");

					} else if (masu[0][8].equals("と") && masu[0][7].equals(player_piece)
							&& masu[1][7].equals(player_piece)) {

						masu[0][8] = "・";

						fight_count[0]++;

						print_result("ーーーー駒を取りました。ーーーー");

					} else if (masu[8][0].equals("と") && masu[7][0].equals(player_piece)
							&& masu[7][1].equals(player_piece)) {

						masu[8][0] = "・";

						fight_count[0]++;

						print_result("ーーーー駒を取りました。ーーーー");

					} else if (masu[8][8].equals("と") && masu[8][7].equals(player_piece)
							&& masu[7][8].equals(player_piece)) {

						masu[8][8] = "・";

						fight_count[0]++;

						print_result("ーーーー駒を取りました。ーーーー");

					}

				} else if (player_piece.equals("と")) { // わざわざこう書かないと動かしたプレイヤーに持ち駒がいかない

					if (masu[0][0].equals("歩") && masu[0][1].equals(player_piece) && masu[1][0].equals(player_piece)) {

						masu[0][0] = "・";

						fight_count[0]++;

						print_result("ーーーー駒を取りました。ーーーー");

					} else if (masu[0][8].equals("歩") && masu[0][7].equals(player_piece)
							&& masu[1][7].equals(player_piece)) {

						masu[0][8] = "・";

						fight_count[0]++;

						print_result("ーーーー駒を取りました。ーーーー");

					} else if (masu[8][0].equals("歩") && masu[7][0].equals(player_piece)
							&& masu[7][1].equals(player_piece)) {

						masu[8][0] = "・";

						fight_count[0]++;

						print_result("ーーーー駒を取りました。ーーーー");

					} else if (masu[8][8].equals("歩") && masu[8][7].equals(player_piece)
							&& masu[7][8].equals(player_piece)) {

						masu[8][8] = "・";

						fight_count[0]++;

						print_result("ーーーー駒を取りました。ーーーー");

					}

				}

				// ---------------------------------------------

				if (player_piece.equals("歩")) {

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

				return 3; // 間違えた座標を打った場合、もう一度。

			}

		} else {

			return 3;

		}

	}

}