/*

package:gg.vivado.boards.board

<jtag_chains>
  <complex>
    <jtag_chain />
  </complex>
</jtag_chains>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:JtagChains = extends Schema
    JtagChain:jtag_chain

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class JtagChains extends Schema {

public JtagChain jtag_chain = null;

}

