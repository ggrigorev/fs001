/*

package:gg.vivado.boards.board

<images>
  <complex>
    <image array="image" />
  </complex>
</images>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Images = extends Schema
    ArrayList<Image>:image = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Images extends Schema {

public ArrayList<Image> image = new ArrayList<>();

}

