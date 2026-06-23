/*

package:gg.vivado.boards.board

<parameter>
  <simplex>
    <name presence="REQUIRED" value="NMTOKEN" />
    <readonly presence="IMPLIED" value="NMTOKEN" />
    <value presence="IMPLIED" value="CDATA" />
    <value_max presence="IMPLIED" value="NMTOKEN" />
    <value_min presence="IMPLIED" value="NMTOKEN" />
    <value_type presence="IMPLIED" value="NMTOKEN" />
    <range presence="IMPLIED" value="CDATA" />
  </simplex>
  <complex>
    <PCDATA />
  </complex>
</parameter>

    import:gg.base.text = Text
    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Parameter = extends Schema
    String:name
    String:readonly
    String:value
    String:value_max
    String:value_min
    String:value_type
    String:range
    Text:PCDATA = new Text()

*/

package gg.vivado.boards.board;

import gg.base.text.Text;
import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Parameter extends Schema {

public String name = null;
public String readonly = null;
public String value = null;
public String value_max = null;
public String value_min = null;
public String value_type = null;
public String range = null;
public Text PCDATA = new Text();

}

