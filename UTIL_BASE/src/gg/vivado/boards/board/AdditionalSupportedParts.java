/*

package:gg.vivado.boards.board

<additional_supported_parts>
  <complex>
    <supported_part array="supported_part" />
  </complex>
</additional_supported_parts>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:AdditionalSupportedParts = extends Schema
    ArrayList<SupportedPart>:supported_part = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class AdditionalSupportedParts extends Schema {

public ArrayList<SupportedPart> supported_part = new ArrayList<>();

}

