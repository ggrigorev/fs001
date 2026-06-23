/*

package:gg.vivado.boards.board

<supported_part>
  <simplex>
    <part_name presence="IMPLIED" value="CDATA" />
  </simplex>
</supported_part>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:SupportedPart = extends Schema
    String:part_name

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class SupportedPart extends Schema {

public String part_name = null;

}

