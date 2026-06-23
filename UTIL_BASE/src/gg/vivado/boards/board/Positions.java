/*

package:gg.vivado.boards.board

<positions>
  <complex>
    <position array="position" />
  </complex>
</positions>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Positions = extends Schema
    ArrayList<Position>:position = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Positions extends Schema {

public ArrayList<Position> position = new ArrayList<>();

}

