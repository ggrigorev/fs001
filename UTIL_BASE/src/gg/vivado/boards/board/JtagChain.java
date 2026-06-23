/*

package:gg.vivado.boards.board

<jtag_chain>
  <simplex>
    <name presence="REQUIRED" value="NMTOKEN" />
  </simplex>
  <complex>
    <position />
  </complex>
</jtag_chain>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:JtagChain = extends Schema
    String:name
    Position:position

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class JtagChain extends Schema {

public String name = null;
public Position position = null;

}

