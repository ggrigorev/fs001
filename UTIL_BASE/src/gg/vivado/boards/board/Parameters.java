/*

package:gg.vivado.boards.board

<parameters>
  <complex>
    <parameter array="parameter" />
  </complex>
</parameters>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Parameters = extends Schema
    ArrayList<Parameter>:parameter = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Parameters extends Schema {

public ArrayList<Parameter> parameter = new ArrayList<>();

}

