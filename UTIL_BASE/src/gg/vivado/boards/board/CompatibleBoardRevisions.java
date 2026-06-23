/*

package:gg.vivado.boards.board

<compatible_board_revisions>
  <complex>
    <revision array="revision" />
  </complex>
</compatible_board_revisions>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:CompatibleBoardRevisions = extends Schema
    ArrayList<Revision>:revision = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class CompatibleBoardRevisions extends Schema {

public ArrayList<Revision> revision = new ArrayList<>();

}

