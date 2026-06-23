/*

package:gg.vivado.boards.board

<jumper>
  <simplex>
    <default_value presence="IMPLIED" value="NMTOKEN" />
    <name presence="REQUIRED" value="NMTOKEN" />
  </simplex>
  <complex>
    <PCDATA array="PCDATA" />
    <description array="description" />
  </complex>
</jumper>

    import:gg.base.text = Text
    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Jumper = extends Schema
    String:default_value
    String:name
    Text:PCDATA = new Text()
    ArrayList<Description>:description = new ArrayList<>()

*/

package gg.vivado.boards.board;

import gg.base.text.Text;
import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Jumper extends Schema {

public String default_value = null;
public String name = null;
public Text PCDATA = new Text();
public ArrayList<Description> description = new ArrayList<>();

}

