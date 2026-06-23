/*

package:gg.vivado.boards.board

<position>
  <simplex>
    <component presence="REQUIRED" value="NMTOKEN" />
    <id presence="IMPLIED" value="NMTOKEN" />
    <name presence="IMPLIED" value="NMTOKEN" />
  </simplex>
  <complex>
    <address array="address" />
  </complex>
</position>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Position = extends Schema
    String:component
    String:id
    String:name
    ArrayList<Address>:address = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Position extends Schema {

public String component = null;
public String id = null;
public String name = null;
public ArrayList<Address> address = new ArrayList<>();

}

