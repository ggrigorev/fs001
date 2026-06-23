/*

package:gg.vivado.boards.board

<file_version>
  <complex>
    <PCDATA />
  </complex>
</file_version>

    import:gg.base.text = Text
    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:FileVersion = extends Schema
    Text:PCDATA = new Text()

*/

package gg.vivado.boards.board;

import gg.base.text.Text;
import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class FileVersion extends Schema {

public Text PCDATA = new Text();

}

