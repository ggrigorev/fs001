/*

package:gg.vivado.boards.board

<components>
  <complex>
    <component array="component" />
  </complex>
</components>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Components = extends Schema
    ArrayList<Component>:component = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Components extends Schema {

public ArrayList<Component> component = new ArrayList<>();

}

