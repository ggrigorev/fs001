/*

package:gg.vivado.boards.board

<component_modes>
  <complex>
    <component_mode array="component_mode" />
  </complex>
</component_modes>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:ComponentModes = extends Schema
    ArrayList<ComponentMode>:component_mode = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class ComponentModes extends Schema {

public ArrayList<ComponentMode> component_mode = new ArrayList<>();

}

