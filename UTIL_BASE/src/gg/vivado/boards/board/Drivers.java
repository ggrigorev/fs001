/*

package:gg.vivado.boards.board

<drivers>
  <complex>
    <driver array="driver" />
  </complex>
</drivers>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Drivers = extends Schema
    ArrayList<Driver>:driver = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Drivers extends Schema {

public ArrayList<Driver> driver = new ArrayList<>();

}

