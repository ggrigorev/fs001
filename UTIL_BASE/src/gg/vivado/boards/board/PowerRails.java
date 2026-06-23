/*

package:gg.vivado.boards.board

<power_rails>
  <complex>
    <power_rail array="power_rail" />
  </complex>
</power_rails>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:PowerRails = extends Schema
    ArrayList<PowerRail>:power_rail = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class PowerRails extends Schema {

public ArrayList<PowerRail> power_rail = new ArrayList<>();

}

