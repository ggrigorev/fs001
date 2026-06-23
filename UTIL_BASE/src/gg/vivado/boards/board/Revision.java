/*

package:gg.vivado.boards.board

<revision>
  <simplex>
    <id presence="REQUIRED" value="NMTOKEN" />
  </simplex>
  <complex>
    <PCDATA />
  </complex>
</revision>

    import:gg.base.text = Text
    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Revision = extends Schema
    String:id
    Text:PCDATA = new Text()

*/

package gg.vivado.boards.board;

import gg.base.text.Text;
import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Revision extends Schema {

public String id = null;
public Text PCDATA = new Text();

}

