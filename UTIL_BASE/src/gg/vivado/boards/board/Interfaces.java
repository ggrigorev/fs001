/*

package:gg.vivado.boards.board

<interfaces>
  <complex>
    <interface array="interface" />
  </complex>
</interfaces>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Interfaces = extends Schema
    ArrayList<XInterface>:x_interface = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Interfaces extends Schema {

public ArrayList<XInterface> x_interface = new ArrayList<>();

}

