/*

package:gg.vivado.boards.board

<connection>
  <simplex>
    <component1 presence="REQUIRED" value="NMTOKEN" />
    <component2 presence="REQUIRED" value="NMTOKEN" />
    <name presence="REQUIRED" value="NMTOKEN" />
  </simplex>
  <complex>
    <connection_map array="connection_map" />
  </complex>
</connection>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Connection = extends Schema
    String:component1
    String:component2
    String:name
    ArrayList<ConnectionMap>:connection_map = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Connection extends Schema {

public String component1 = null;
public String component2 = null;
public String name = null;
public ArrayList<ConnectionMap> connection_map = new ArrayList<>();

}

