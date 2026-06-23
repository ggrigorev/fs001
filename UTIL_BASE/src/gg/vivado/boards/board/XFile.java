/*

package:gg.vivado.boards.board

<file>
  <simplex>
    <type presence="REQUIRED" value="NMTOKEN" />
  </simplex>
  <complex>
    <PCDATA />
  </complex>
</file>

    import:gg.base.text = Text
    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:XFile = extends Schema
    String:type
    Text:PCDATA = new Text()

*/

package gg.vivado.boards.board;

import gg.base.text.Text;
import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class XFile extends Schema {

public String type = null;
public Text PCDATA = new Text();

}

