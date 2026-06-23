/*

package:gg.vivado.boards.board

<data_property>
  <simplex>
    <name presence="REQUIRED" value="NMTOKEN" />
    <value presence="REQUIRED" value="CDATA" />
  </simplex>
</data_property>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:DataProperty = extends Schema
    String:name
    String:value

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class DataProperty extends Schema {

public String name = null;
public String value = null;

}

