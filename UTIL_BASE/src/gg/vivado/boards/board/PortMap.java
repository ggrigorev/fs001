/*

package:gg.vivado.boards.board

<port_map>
  <simplex>
    <dir presence="REQUIRED" ENUMERATION="in:inout:out" />
    <left presence="IMPLIED" value="NMTOKEN" />
    <logical_port presence="REQUIRED" value="NMTOKEN" />
    <name presence="IMPLIED" value="NMTOKEN" />
    <physical_port presence="REQUIRED" value="NMTOKEN" />
    <right presence="IMPLIED" value="NMTOKEN" />
  </simplex>
  <complex>
    <pin_maps repeat="?" />
  </complex>
</port_map>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:PortMap = extends Schema
    String:dir
    String:left
    String:logical_port
    String:name
    String:physical_port
    String:right
    PinMaps:pin_maps

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class PortMap extends Schema {

public String dir = null;
public String left = null;
public String logical_port = null;
public String name = null;
public String physical_port = null;
public String right = null;
public PinMaps pin_maps = null;

}

