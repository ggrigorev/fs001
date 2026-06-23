/*

package:gg.vivado.boards.board

<preferred_ip>
  <simplex>
    <library presence="IMPLIED" value="NMTOKEN" />
    <name presence="REQUIRED" value="NMTOKEN" />
    <order presence="IMPLIED" value="NMTOKEN" />
    <vendor presence="REQUIRED" value="NMTOKEN" />
  </simplex>
</preferred_ip>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:PreferredIp = extends Schema
    String:library
    String:name
    String:order
    String:vendor

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class PreferredIp extends Schema {

public String library = null;
public String name = null;
public String order = null;
public String vendor = null;

}

