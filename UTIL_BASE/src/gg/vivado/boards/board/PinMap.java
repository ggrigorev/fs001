/*

package:gg.vivado.boards.board

<pin_map>
  <simplex>
    <component_pin presence="REQUIRED" value="NMTOKEN" />
    <port_index presence="REQUIRED" value="NMTOKEN" />
  </simplex>
</pin_map>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:PinMap = extends Schema
    String:component_pin
    String:port_index

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class PinMap extends Schema {

public String component_pin = null;
public String port_index = null;

}

