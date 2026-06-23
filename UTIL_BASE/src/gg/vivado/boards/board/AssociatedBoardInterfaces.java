/*

package:gg.vivado.boards.board

<associated_board_interfaces>
  <complex>
    <associated_board_interface array="associated_board_interface" />
  </complex>
</associated_board_interfaces>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:AssociatedBoardInterfaces = extends Schema
    ArrayList<AssociatedBoardInterface>:associated_board_interface = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class AssociatedBoardInterfaces extends Schema {

public ArrayList<AssociatedBoardInterface> associated_board_interface = new ArrayList<>();

}

