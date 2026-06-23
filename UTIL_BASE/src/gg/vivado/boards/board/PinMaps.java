/*

package:gg.vivado.boards.board

<pin_maps>
  <complex>
    <pin_map array="pin_map" />
  </complex>
</pin_maps>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:PinMaps = extends Schema
    ArrayList<PinMap>:pin_map = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class PinMaps extends Schema {

public ArrayList<PinMap> pin_map = new ArrayList<>();

}

