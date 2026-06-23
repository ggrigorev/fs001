/*

package:gg.vivado.boards.board

<image>
  <simplex>
    <display_name presence="REQUIRED" value="CDATA" />
    <name presence="REQUIRED" value="NMTOKEN" />
    <sub_type presence="REQUIRED" value="NMTOKEN" />
    <resolution presence="IMPLIED" value="NMTOKEN" />
  </simplex>
  <complex>
    <description />
  </complex>
</image>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Image = extends Schema
    String:display_name
    String:name
    String:sub_type
    String:resolution
    Description:description

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Image extends Schema {

public String display_name = null;
public String name = null;
public String sub_type = null;
public String resolution = null;
public Description description = null;

}

