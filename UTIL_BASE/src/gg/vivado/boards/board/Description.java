/*

package:gg.vivado.boards.board

<description>
  <complex>
    <PCDATA />
  </complex>
</description>

    import:gg.base.text = Text
    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Description = extends Schema
    Text:PCDATA = new Text()

*/

package gg.vivado.boards.board;

import gg.base.text.Text;
import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Description extends Schema {

public Text PCDATA = new Text();

}

