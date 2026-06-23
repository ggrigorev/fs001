/*

package:gg.vivado.boards.board

<net>
  <simplex>
    <index presence="REQUIRED" value="NMTOKEN" />
    <max_delay presence="REQUIRED" value="NMTOKEN" />
    <min_delay presence="REQUIRED" value="NMTOKEN" />
    <typical_delay presence="REQUIRED" value="NMTOKEN" />
  </simplex>
</net>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Net = extends Schema
    String:index
    String:max_delay
    String:min_delay
    String:typical_delay

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Net extends Schema {

public String index = null;
public String max_delay = null;
public String min_delay = null;
public String typical_delay = null;

}

