/*

package:gg.vivado.boards.board

<connections>
  <complex>
    <connection array="connection" />
  </complex>
</connections>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Connections = extends Schema
    ArrayList<Connection>:connection = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Connections extends Schema {

public ArrayList<Connection> connection = new ArrayList<>();

}

