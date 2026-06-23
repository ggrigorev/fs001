/*

package:gg.vivado.boards.board

<port_maps>
  <complex>
    <port_map array="port_map" />
  </complex>
</port_maps>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:PortMaps = extends Schema
    ArrayList<PortMap>:port_map = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class PortMaps extends Schema {

public ArrayList<PortMap> port_map = new ArrayList<>();

}

