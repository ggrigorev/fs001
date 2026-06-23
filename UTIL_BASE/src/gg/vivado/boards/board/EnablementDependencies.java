/*

package:gg.vivado.boards.board

<enablement_dependencies>
  <complex>
    <jumpers array="jumpers" />
    <parameters array="parameters" />
  </complex>
</enablement_dependencies>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:EnablementDependencies = extends Schema
    ArrayList<Jumpers>:jumpers = new ArrayList<>()
    ArrayList<Parameters>:parameters = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class EnablementDependencies extends Schema {

public ArrayList<Jumpers> jumpers = new ArrayList<>();
public ArrayList<Parameters> parameters = new ArrayList<>();

}

