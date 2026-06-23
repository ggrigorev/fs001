/*

package:gg.vivado.boards.board

<compatible_connectors>
  <complex>
    <compatible_connector array="compatible_connector" />
  </complex>
</compatible_connectors>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:CompatibleConnectors = extends Schema
    ArrayList<CompatibleConnector>:compatible_connector = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class CompatibleConnectors extends Schema {

public ArrayList<CompatibleConnector> compatible_connector = new ArrayList<>();

}

