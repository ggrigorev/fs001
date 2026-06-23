/*

package:gg.vivado.boards.board

<address>
  <simplex>
    <high presence="REQUIRED" value="NMTOKEN" />
    <low presence="REQUIRED" value="NMTOKEN" />
  </simplex>
</address>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Address = extends Schema
    String:high
    String:low

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Address extends Schema {

public String high = null;
public String low = null;

}

