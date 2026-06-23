/*

package:gg.vivado.boards.board

<pins>
  <complex>
    <pin array="pin" />
  </complex>
</pins>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Pins = extends Schema
    ArrayList<Pin>:pin = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Pins extends Schema {

public ArrayList<Pin> pin = new ArrayList<>();

}

