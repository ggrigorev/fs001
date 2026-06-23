/*

package:gg.vivado.boards.board

<associated_board_interface>
  <simplex>
    <name presence="REQUIRED" value="NMTOKEN" />
    <order presence="IMPLIED" value="NMTOKEN" />
  </simplex>
</associated_board_interface>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:AssociatedBoardInterface = extends Schema
    String:name
    String:order

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class AssociatedBoardInterface extends Schema {

public String name = null;
public String order = null;

}

