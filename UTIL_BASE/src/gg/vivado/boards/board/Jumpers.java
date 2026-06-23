/*

package:gg.vivado.boards.board

<jumpers>
  <complex>
    <jumper array="jumper" />
  </complex>
</jumpers>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Jumpers = extends Schema
    ArrayList<Jumper>:jumper = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Jumpers extends Schema {

public ArrayList<Jumper> jumper = new ArrayList<>();

}

