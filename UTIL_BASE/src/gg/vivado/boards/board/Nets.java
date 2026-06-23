/*

package:gg.vivado.boards.board

<nets>
  <complex>
    <net array="net" />
  </complex>
</nets>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Nets = extends Schema
    ArrayList<Net>:net = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Nets extends Schema {

public ArrayList<Net> net = new ArrayList<>();

}

