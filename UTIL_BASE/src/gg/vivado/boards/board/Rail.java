/*

package:gg.vivado.boards.board

<rail>
  <simplex>
    <name presence="REQUIRED" value="NMTOKEN" />
    <phased_power_source presence="IMPLIED" value="CDATA" />
  </simplex>
</rail>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Rail = extends Schema
    String:name
    String:phased_power_source

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Rail extends Schema {

public String name = null;
public String phased_power_source = null;

}

