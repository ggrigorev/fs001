/*

package:gg.vivado.boards.board

<compatible_connector>
  <simplex>
    <board_vendor presence="REQUIRED" value="CDATA" />
    <board_name presence="REQUIRED" value="CDATA" />
    <board_version presence="REQUIRED" value="CDATA" />
    <connector_name presence="REQUIRED" value="CDATA" />
  </simplex>
</compatible_connector>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:CompatibleConnector = extends Schema
    String:board_vendor
    String:board_name
    String:board_version
    String:connector_name

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class CompatibleConnector extends Schema {

public String board_vendor = null;
public String board_name = null;
public String board_version = null;
public String connector_name = null;

}

