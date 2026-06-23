/*

package:gg.vivado.boards.board

<driver_files>
  <complex>
    <file array="file" />
  </complex>
</driver_files>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:DriverFiles = extends Schema
    ArrayList<XFile>:x_file = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class DriverFiles extends Schema {

public ArrayList<XFile> x_file = new ArrayList<>();

}

