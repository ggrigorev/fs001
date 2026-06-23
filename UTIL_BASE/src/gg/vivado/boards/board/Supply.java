/*

package:gg.vivado.boards.board

<supply>
  <simplex>
    <name presence="REQUIRED" value="NMTOKEN" />
  </simplex>
</supply>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Supply = extends Schema
    String:name

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Supply extends Schema {

public String name = null;

}

